package pv168.freelancer.data;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;

/**
 * A simple class whose responsibility is to provide a check, whether the given table
 * exists in the given schema.
 *
 * @author xbenes2
 */
public class TableExistenceChecker {

    private final DataSource dataSource;

    public TableExistenceChecker(DataSource source){
        dataSource = Objects.requireNonNull(source);
    }

    public boolean tableExists(String schemaName, String tableName) {
        try (var connection = dataSource.getConnection();
             var rs = connection.getMetaData().getTables(null, schemaName, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to detect if the table " + schemaName + "." + tableName + " exists", ex);
        }
    }
}
