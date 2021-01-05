package pv168.freelancer.data;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.*;
import pv168.freelancer.model.WorkType;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkTypeDaoTest {

    private static EmbeddedDataSource dataSource;
    private WorkTypeDao workTypeDao;

    @BeforeAll
    static void initTestDataSource() {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:work-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createWorkDao() throws SQLException {
        workTypeDao = new WorkTypeDao(dataSource);
        try (var connection = dataSource.getConnection(); var st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM APP.WORK_TYPE");
        }
    }

    @AfterEach
    void cleanUp() {
        workTypeDao.dropTable();
    }


    @Test
    void createWorkType() {
        WorkType workType = new WorkType("Moderating Discord", new BigDecimal(30), "Tough job");

        workTypeDao.createWorkType(workType);

        assertThat(workType.getId())
                .isNotNull();
    }

    @Test
    void deleteWorkType() {
        WorkType type1 = new WorkType("Moderating Discord", new BigDecimal(30), "Tough job");
        WorkType type2 = new WorkType("Feeding pigeons", new BigDecimal(0), "Fresh air");

        workTypeDao.createWorkType(type1);
        workTypeDao.createWorkType(type2);

        assertThat(workTypeDao.findAllWorkTypes().size()).isEqualTo(2);

        workTypeDao.deleteWorkType(type1);

        assertThat(workTypeDao.findAllWorkTypes().size()).isEqualTo(1);
    }

    @Test
    void updateWorkType() {
        String originalDescription = "Tough job";
        String newDescription = "Fresh air";

        WorkType workType = new WorkType("Feeding pigeons", new BigDecimal(0), originalDescription);

        workTypeDao.createWorkType(workType);
        workType.setDescription(newDescription);
        workTypeDao.updateWorkType(workType);

        assertThat(findWorkTypeById(workType.getId()).getDescription()).isEqualTo(newDescription);
    }

    private WorkType findWorkTypeById(long id) {
        return workTypeDao.findAllWorkTypes().stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No WorkType with id " + id + " found"));
    }
}
