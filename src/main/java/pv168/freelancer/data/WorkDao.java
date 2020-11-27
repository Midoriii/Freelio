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

    public void create(WorkDone workDone) {
        try (var connection = dataSource.getConnection();
             var st1 = connection.prepareStatement(
                     "INSERT INTO WORK_TYPE (NAME, HOURLY_RATE, DESCRIPTION) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            st1.setString(1, workDone.getWorkType().getName());
            st1.setDouble(2, workDone.getWorkType().getHourlyRate());
            st1.setString(3, workDone.getWorkType().getDescription());
            st1.executeUpdate();
            var rs1 = st1.getGeneratedKeys();
            rs1.next();
            long workTypeID = rs1.getLong(1);
            workDone.getWorkType().setId(workTypeID);

            insertWorkDone(workDone, connection, workTypeID);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to insert to database", ex);
        }
    }

    private void insertWorkDone(WorkDone workDone, Connection connection, long workTypeID) {
        try (var st2 = connection.prepareStatement(
                     "INSERT INTO WORK_DONE (WT_ID, WORK_START, WORK_END, DESCRIPTION) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            st2.setLong(1, workTypeID);
            st2.setTimestamp(2, Timestamp.valueOf(workDone.getWorkStart()));
            st2.setTimestamp(3, Timestamp.valueOf(workDone.getWorkEnd()));
            st2.setString(4, workDone.getDescription());
            st2.executeUpdate();
            var rs2 = st2.getGeneratedKeys();
            rs2.next();
            workDone.setId(rs2.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to store Work Done" + workDone, e);
        }
    }

    public void delete(WorkDone workDone) {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "DELETE FROM WORK_DONE " +
                             "WHERE ID = ?")) {
            st.setLong(1, workDone.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to store employee " + workDone, ex);
        }
    }

    private WorkType getWorkType(Connection connection, long workTypeID) {
        try (var st2 = connection.prepareStatement(
                "SELECT ID, NAME, HOURLY_RATE, DESCRIPTION FROM WORK_TYPE WHERE ID = ?")) {
            st2.setLong(1, workTypeID);
            try (var rs2 = st2.executeQuery()) {
                rs2.next();
                return new WorkType(
                        workTypeID,
                        rs2.getString("NAME"),
                        rs2.getDouble("HOURLY_RATE"),
                        rs2.getString("DESCRIPTION")
                );
            }
        } catch (Exception e1) {
            throw new RuntimeException();
        }
    }


    public List<WorkDone> findAll() {
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
            testDataGenerator.createTestData(10).forEach(this::create);
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
                    "HOURLY_RATE FLOAT(30)," +
                    "DESCRIPTION varchar(200)" +
                    ")");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to create WORK_TYPE table", ex);
        }
    }
}
