package pv168.freelancer.data;


import pv168.freelancer.model.WorkDone;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class WorkDao {

    private final DataSource dataSource;

    public WorkDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initWorkTypeTable();
        initWorkDoneTable();
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
            try (var rs = st1.getGeneratedKeys();
                    var st2 = connection.prepareStatement(
                    "INSERT INTO WORK_DONE (WT_ID, WORK_START, WORK_END, DESCRIPTION) VALUES (?, ?, ?, ?)")) {
                if (rs.next()) {
                    st2.setDouble(1, rs.getLong(1));
                } else {
                    throw new RuntimeException("insert to WORK_TYPE returned no keys");
                }
                st2.setTimestamp(2, Timestamp.valueOf(workDone.getWorkStart()));
                st2.setTimestamp(3, Timestamp.valueOf(workDone.getWorkEnd()));
                st2.setString(4, workDone.getDescription());
            } catch (SQLException e) {
                throw new RuntimeException("Failed to store Work Type" + workDone.getWorkType(), e);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to store Work Done" + workDone, ex);
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
