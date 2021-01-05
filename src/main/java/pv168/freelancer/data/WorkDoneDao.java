package pv168.freelancer.data;

import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * --Description here--
 *
 * @author
 */
public class WorkDoneDao {

    private final DataSource dataSource;

    public WorkDoneDao(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
        initWorkDoneTable();
    }

    public void createWorkDone(WorkDone workDone) {
        if (workDone.getId() != null) {
            throw new IllegalArgumentException("work done id must not be initialized");
        }
        if (workDone.getWorkType() == null) {
            throw new IllegalArgumentException("work type attribute must not be null");
        }

        Long workTypeID = workDone.getWorkType().getId();
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
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
            throw new DataAccessException("Failed to store Work Done" + workDone, e);
        }
    }

    public void deleteWorkDone(Long ID) {
        if (ID == null) {
            throw new IllegalArgumentException("Work done has null ID");
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "DELETE FROM WORK_DONE " +
                             "WHERE ID = ?")) {
            st.setLong(1, ID);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to delete work done with ID" + ID.toString(), ex);
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
            if (st.executeUpdate() == 0) {
                throw new DataAccessException("Failed to update non-existing WorkDone: " + workDone);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to update WorkDone " + workDone, ex);
        }
    }

    public List<WorkDone> findAllWorksDone() {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "SELECT WORK_DONE.ID WD_ID, WT_ID, WORK_START, WORK_END, WORK_DONE.DESCRIPTION WD_DESC, " +
                             "WORK_TYPE.NAME WT_NAME, HOURLY_RATE WT_HOURLY_RATE, WORK_TYPE.DESCRIPTION WT_DESC " +
                             "FROM WORK_DONE LEFT OUTER JOIN WORK_TYPE ON WORK_DONE.WT_ID = WORK_TYPE.ID")) {

            List<WorkDone> worksDone = new ArrayList<>();

            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    WorkDone workDone = new WorkDone(
                            rs.getTimestamp("WORK_START").toLocalDateTime(),
                            rs.getTimestamp("WORK_END").toLocalDateTime(),
                            new WorkType(rs.getLong("WT_ID"),
                                    rs.getString("WT_NAME"),
                                    rs.getBigDecimal("WT_HOURLY_RATE"),
                                    rs.getString("WT_DESC")),
                            rs.getString("WD_DESC"));
                    workDone.setId(rs.getLong("WD_ID"));
                    worksDone.add(workDone);
                }
            }
            return worksDone;
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to load all works done", ex);
        }
    }

    private void initWorkDoneTable() {
        if (!workDoneTableExists("APP", "WORK_DONE")) {
            createWorkDoneTable();
        }
    }

    private boolean workDoneTableExists(String schemaName, String tableName) {
        try (var connection = dataSource.getConnection();
             var rs = connection.getMetaData().getTables(null, schemaName, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to detect if the table " + schemaName + "." + tableName + " exists", ex);
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
            throw new DataAccessException("Failed to create WORK_DONE table", ex);
        }
    }

    public void dropTable() {
        try (Connection connection = dataSource.getConnection();
             var st = connection.createStatement()) {

             st.executeUpdate("DROP TABLE APP.WORK_DONE");
        } catch (SQLException e) {
            throw new DataAccessException("failed to drop WORK_DONE table", e);
        }
    }

}
