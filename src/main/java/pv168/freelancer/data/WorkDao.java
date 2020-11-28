package pv168.freelancer.data;

import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkDao {

    private final DataSource dataSource;

    public WorkDao(DataSource dataSource, TestDataGenerator testDataGenerator) {
        this.dataSource = dataSource;
        initWorkTypeTable();
        initWorkDoneTable(testDataGenerator);
    }

    public void createWorkDone(WorkDone workDone) {
        if (workDone.getId() != null) {
            throw new IllegalArgumentException("work done id must not be initialized");
        }
        if (workDone.getWorkType() == null) {
            throw new IllegalArgumentException("work type attribute must not be null");
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO WORK_TYPE (NAME, HOURLY_RATE, DESCRIPTION) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, workDone.getWorkType().getName());
            st.setDouble(2, workDone.getWorkType().getHourlyRate());
            st.setString(3, workDone.getWorkType().getDescription());
            st.executeUpdate();
            var rs = st.getGeneratedKeys();
            rs.next();
            Long workTypeID = rs.getLong(1);
            workDone.getWorkType().setId(workTypeID);

            insertWorkDone(workDone, connection, workTypeID);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to insert to database", ex);
        }
    }

    private void insertWorkDone(WorkDone workDone, Connection connection, Long workTypeID) {
        try (var st = connection.prepareStatement(
                     "INSERT INTO WORK_DONE (WT_ID, WORK_START, WORK_END, DESCRIPTION) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            st.setLong(1, workTypeID);
            st.setTimestamp(2, Timestamp.valueOf(workDone.getWorkStart()));
            st.setTimestamp(3, Timestamp.valueOf(workDone.getWorkEnd()));
            st.setString(4, workDone.getDescription());
            st.executeUpdate();
            var rs = st.getGeneratedKeys();
            rs.next();
            workDone.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to store Work Done" + workDone, e);
        }
    }

    public void deleteWorkDone(WorkDone workDone) {
        if (workDone.getId() == null) {
            throw new IllegalArgumentException("Work done has null ID");
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "DELETE FROM WORK_DONE " +
                             "WHERE ID = ?")) {
            st.setLong(1, workDone.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to delete work done " + workDone, ex);
        }
    }

    public void updateWorkDone(WorkDone workDone) {
        if (workDone.getId() == null) throw new IllegalArgumentException("WorkDone has null ID");
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE WORK_DONE SET WT_ID = ?, WORK_START = ?, WORK_END = ?, DESCRIPTION = ? WHERE ID = ?")) {
            st.setLong(1, workDone.getWorkType().getId());
            st.setTimestamp(2, Timestamp.valueOf(workDone.getWorkStart()));
            st.setTimestamp(3, Timestamp.valueOf(workDone.getWorkEnd()));
            st.setString(4, workDone.getDescription());
            st.setLong(5, workDone.getId());
            if (st.executeUpdate() == 0){
                throw new RuntimeException("Failed to update non-existing WorkDone: " + workDone);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update WorkDone " + workDone, ex);
        }
    }

    private WorkType getWorkType(Connection connection, Long workTypeID) {
        try (var st = connection.prepareStatement(
                "SELECT ID, NAME, HOURLY_RATE, DESCRIPTION FROM WORK_TYPE WHERE ID = ?")) {
            st.setLong(1, workTypeID);
            try (var rs = st.executeQuery()) {
                rs.next();
                return new WorkType(
                        workTypeID,
                        rs.getString("NAME"),
                        rs.getDouble("HOURLY_RATE"),
                        rs.getString("DESCRIPTION")
                );
            }
        } catch (Exception e1) {
            throw new RuntimeException();
        }
    }

    public List<WorkDone> findAllWorksDone() {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "SELECT ID, WT_ID, WORK_START, WORK_END, DESCRIPTION FROM WORK_DONE")) {

            List<WorkDone> worksDone = new ArrayList<>();

            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    WorkType workType = getWorkType(connection, rs.getLong("WT_ID"));
                    WorkDone workDone = new WorkDone(
                            rs.getTimestamp("WORK_START").toLocalDateTime(),
                            rs.getTimestamp("WORK_END").toLocalDateTime(),
                            workType,
                            rs.getString("DESCRIPTION"));
                    workDone.setId(rs.getLong("ID"));

                    worksDone.add(workDone);
                }
            }
            return worksDone;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to load all works done", ex);
        }
    }

    private void initWorkDoneTable(TestDataGenerator testDataGenerator) {
        if (!workDoneTableExists("APP", "WORK_DONE")) {
            createWorkDoneTable();
            testDataGenerator.createTestData(10).forEach(this::createWorkDone);
        }
    }

    private boolean workDoneTableExists(String schemaName, String tableName) {
        try (var connection = dataSource.getConnection();
             var rs = connection.getMetaData().getTables(null, schemaName, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to detect if the table " + schemaName + "." + tableName + " exists", ex);
        }
    }

    private void createWorkDoneTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE APP.WORK_DONE (" +
                    "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                    "WT_ID BIGINT REFERENCES WORK_TYPE(ID)," +
                    "WORK_START TIMESTAMP," +
                    "WORK_END TIMESTAMP," +
                    "DESCRIPTION VARCHAR(200)" +
                    ")");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to create WORK_DONE table", ex);
        }
    }

    private void initWorkTypeTable() {
        if (!workTypeTableExists("APP", "WORK_TYPE")) {
            createWorkTypeTable();
        }
    }

    private boolean workTypeTableExists(String schemaName, String tableName) {
        try (var connection = dataSource.getConnection();
             var rs = connection.getMetaData().getTables(null, schemaName, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to detect if the table " + schemaName + "." + tableName + " exists", ex);
        }
    }

    private void createWorkTypeTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE APP.WORK_TYPE (" +
                    "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                    "NAME varchar(100)," +
                    "HOURLY_RATE DOUBLE," +
                    "DESCRIPTION varchar(200)" +
                    ")");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to create WORK_TYPE table", ex);
        }
    }

    public void createWorkType(WorkType workType) {
        if (workType.getId() != null) throw new IllegalArgumentException("WorkType already has ID: " + workType);
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO WORK_TYPE (NAME, HOURLY_RATE, DESCRIPTION) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, workType.getName());
            st.setDouble(2, workType.getHourlyRate());
            st.setString(3, workType.getDescription());
            st.executeUpdate();
            try (var rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    workType.setId(rs.getLong(1));
                } else {
                    throw new RuntimeException("Failed to fetch generated key: no key returned for WorkType: " + workType);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to store WorkType " + workType, ex);
        }
    }

    public void deleteWorkType(WorkType workType) {
        if (workType.getId() == null) throw new IllegalArgumentException("WorkType has null ID");
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "DELETE FROM WORK_TYPE WHERE ID = ?")) {
            st.setLong(1, workType.getId());
            if (st.executeUpdate() == 0){
                throw new RuntimeException("Failed to delete non-existing WorkType: " + workType);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to delete employee " + workType, ex);
        }
    }

    public void updateWorkType(WorkType workType) {
        if (workType.getId() == null) throw new IllegalArgumentException("WorkType has null ID");
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE WORK_TYPE SET NAME = ?, HOURLY_RATE = ?, DESCRIPTION = ? WHERE ID = ?")) {
            st.setString(1, workType.getName());
            st.setDouble(2, workType.getHourlyRate());
            st.setString(3, workType.getDescription());
            st.setLong(4, workType.getId());
            if (st.executeUpdate() == 0){
                throw new RuntimeException("Failed to update non-existing WorkType: " + workType);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update WorkType " + workType, ex);
        }
    }

    public List<WorkType> findAllWorkTypes() {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "SELECT ID, NAME, HOURLY_RATE, DESCRIPTION FROM WORK_TYPE")) {

            List<WorkType> workTypes = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    WorkType workType = new WorkType(
                            rs.getLong("ID"),
                            rs.getString("NAME"),
                            rs.getDouble("HOURLY_RATE"),
                            rs.getString("DESCRIPTION"));

                    workTypes.add(workType);
                }
            }
            return workTypes;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to load all workTypes", ex);
        }
    }








    public void dropTable() {
        try (Connection connection = dataSource.getConnection();
             var st1 = connection.createStatement();
             var st2 = connection.createStatement()) {
            st1.executeUpdate("DROP TABLE APP.WORK_DONE");
            st2.executeUpdate("DROP TABLE APP.WORK_TYPE");
        } catch (SQLException e) {
            throw new RuntimeException("failed to drop tables", e);
        }
    }
}
