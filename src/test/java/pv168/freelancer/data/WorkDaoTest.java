package pv168.freelancer.data;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.*;
import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


final class WorkDaoTest {

    private static EmbeddedDataSource dataSource;
    private WorkDao workDao;

    @BeforeAll
    static void initTestDataSource() {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:work-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createWorkDao() throws SQLException {
        workDao = new WorkDao(dataSource, new TestDataGenerator());
        try (var connection = dataSource.getConnection();
             var st1 = connection.createStatement();
             var st2 = connection.createStatement()) {
            st1.executeUpdate("DELETE FROM APP.WORK_DONE");
            st2.executeUpdate("DELETE FROM APP.WORK_TYPE");
        }
    }

    @AfterEach
    void cleanUp() {
        workDao.dropTable();
    }

    @Test
    void createWork() {
        WorkType workType = new WorkType("Moderating Discord", 30, "Tough job");
        WorkDone workDone = new WorkDone(LocalDateTime.of(2020, 11, 23, 22, 18, 54),
                LocalDateTime.of(2020, 11, 23, 22, 18, 55), workType, "String note");

        workDao.createWorkDone(workDone);

        assertThat(workDone.getId())
                .isNotNull();
        assertThat(workDao.findAllWorksDone())
                .usingElementComparatorIgnoringFields("workType")
                .containsExactly(workDone);
    }

    @Test
    void createWorkWithNullWT() {
        WorkDone workDone = new WorkDone(LocalDateTime.of(2020, 11, 23, 22, 18, 54),
                LocalDateTime.of(2020, 11, 23, 22, 18, 55), null, "String note");
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> workDao.createWorkDone(workDone));
    }

    @Test
    void createWorkWithExistingId() {
        WorkType workType = new WorkType("Moderating Discord", 30, "Tough job");
        WorkDone workDone = new WorkDone(LocalDateTime.of(2020, 11, 23, 22, 18, 54),
                LocalDateTime.of(2020, 11, 23, 22, 18, 55), workType, "String note");
        workDone.setId(123L);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> workDao.createWorkDone(workDone));
    }

    @Test
    void findAllEmpty() {
        assertThat(workDao.findAllWorksDone())
                .isEmpty();
    }

    @Test
    void findAll() {
        WorkType type1 = new WorkType("Moderating Discord", 30, "Tough job");
        WorkType type2 = new WorkType("Feeding pigeons", 0, "Fresh air");
        WorkDone mod1 = new WorkDone(LocalDateTime.of(2020, 11, 23, 22, 18, 54),
                LocalDateTime.of(2020, 11, 23, 22, 18, 55), type1, "String note");
        WorkDone mod2 = new WorkDone(LocalDateTime.of(2018, 1, 3, 2, 1, 4),
                LocalDateTime.of(2018, 1, 20, 20, 10, 50), type1, "note 2");
        WorkDone feeding = new WorkDone(LocalDateTime.of(2019, 5, 4, 3, 2, 1),
                LocalDateTime.of(2019, 6, 5, 4, 3, 2), type2, "another note");


        workDao.createWorkDone(mod1);
        workDao.createWorkDone(mod2);
        workDao.createWorkDone(feeding);

        assertThat(workDao.findAllWorksDone())
                .usingElementComparatorIgnoringFields("workType")
                .containsExactlyInAnyOrder(mod1, mod2, feeding);
    }

    @Test
    void delete() {
        WorkType type1 = new WorkType("Moderating Discord", 30, "Tough job");
        WorkType type2 = new WorkType("Feeding pigeons", 0, "Fresh air");
        WorkDone mod1 = new WorkDone(LocalDateTime.of(2020, 11, 23, 22, 18, 54),
                LocalDateTime.of(2020, 11, 23, 22, 18, 55), type1, "String note");

        WorkDone feeding = new WorkDone(LocalDateTime.of(2019, 5, 4, 3, 2, 1),
                LocalDateTime.of(2019, 6, 5, 4, 3, 2), type2, "another note");


        workDao.createWorkDone(mod1);
        workDao.createWorkDone(feeding);

        workDao.deleteWorkDone(mod1);

        assertThat(workDao.findAllWorksDone())
                .usingElementComparatorIgnoringFields("workType")
                .containsExactly(feeding);
    }

    @Test
    void deleteWithNullId() {
        WorkType wt = new WorkType("Delivering pizza", 25, "By car");
        WorkDone wd = new WorkDone(LocalDateTime.of(2019, 5, 4, 3, 2, 1),
                LocalDateTime.of(2019, 6, 5, 4, 3, 2), wt, "4 pizzas delivered");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> workDao.deleteWorkDone(wd));
    }


}