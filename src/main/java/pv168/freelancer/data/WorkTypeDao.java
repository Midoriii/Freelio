package pv168.freelancer.data;

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
public class WorkTypeDao {

    private final DataSource dataSource;
    private final TableExistenceChecker existenceChecker;

    public WorkTypeDao(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
        existenceChecker = new TableExistenceChecker(dataSource);
        initWorkTypeTable();
    }

    private void initWorkTypeTable() {
        if (!existenceChecker.tableExists("APP", "WORK_TYPE")) {
            createWorkTypeTable();
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
            throw new DataAccessException("Failed to create WORK_TYPE table", ex);
        }
    }

    public void createWorkType(WorkType workType) {
        if (workType.getId() != null) throw new IllegalArgumentException("WorkType already has ID: " + workType);
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO WORK_TYPE (NAME, HOURLY_RATE, DESCRIPTION) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, workType.getName());
            st.setBigDecimal(2, workType.getHourlyRate());
            st.setString(3, workType.getDescription());
            st.executeUpdate();
            try (var rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    workType.setId(rs.getLong(1));
                } else {
                    throw new DataAccessException("Failed to fetch generated key: no key returned for WorkType: " + workType);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to store WorkType " + workType, ex);
        }
    }

    public void deleteWorkType(Long ID) {
        if (ID == null) throw new IllegalArgumentException("WorkType has null ID");
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "DELETE FROM WORK_TYPE WHERE ID = ?")) {
            st.setLong(1, ID);
            if (st.executeUpdate() == 0){
                throw new DataAccessException("Failed to delete non-existing WorkType with ID: " + ID.toString());
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to delete WorkType with ID " + ID.toString(), ex);
        }
    }

    public void updateWorkType(WorkType workType) {
        if (workType.getId() == null) throw new IllegalArgumentException("WorkType has null ID");
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE WORK_TYPE SET NAME = ?, HOURLY_RATE = ?, DESCRIPTION = ? WHERE ID = ?")) {
            st.setString(1, workType.getName());
            st.setBigDecimal(2, workType.getHourlyRate());
            st.setString(3, workType.getDescription());
            st.setLong(4, workType.getId());
            if (st.executeUpdate() == 0){
                throw new DataAccessException("Failed to update non-existing WorkType: " + workType);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to update WorkType " + workType, ex);
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
                            rs.getBigDecimal("HOURLY_RATE"),
                            rs.getString("DESCRIPTION"));

                    workTypes.add(workType);
                }
            }
            return workTypes;
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to load all workTypes", ex);
        }
    }

    public void dropTable() {
        try (Connection connection = dataSource.getConnection();
             var st = connection.createStatement()) {
            st.executeUpdate("DROP TABLE APP.WORK_TYPE");

        } catch (SQLException e) {
            throw new DataAccessException("failed to drop WORK_TYPE table", e);
        }
    }
}

