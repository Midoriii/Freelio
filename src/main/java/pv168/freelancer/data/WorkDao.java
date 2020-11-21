package pv168.freelancer.data;

import javax.sql.DataSource;
import java.sql.SQLException;

public class WorkDao {

    private final DataSource dataSource;

    public WorkDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    private void initTable() {
        if (!tableExists("APP", "WORK_DONE")) {
            createTable();
        }
    }

    private boolean tableExists(String schemaName, String tableName) {
        try (var connection = dataSource.getConnection();
             var rs = connection.getMetaData().getTables(null, schemaName, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to detect if the table " + schemaName + "." + tableName + " exist", ex);
        }
    }

    private void createTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE APP.WORK_DONE (" +
                    "WORK_START TIMESTAMP," +
                    "WORK_END TIMESTAMP," +
                    "WORK_TYPE VARCHAR(50)," +
                    "DESCRIPTION VARCHAR(200)" +
                    ")");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to create WORK table", ex);
        }
    }

}
