package pv168.freelancer.data;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.*;
import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


final class WorkDoneDaoTest {

    private static EmbeddedDataSource dataSource;
    private WorkDoneDao workDoneDao;
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
        workDoneDao = new WorkDoneDao(dataSource);
        try (var connection = dataSource.getConnection(); var st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM APP.WORK_DONE");
        }
    }

    @AfterEach
    void cleanUp() {
        workDoneDao.dropTable();
        workTypeDao.dropTable();
    }


    @Test
    void createWork() {
        WorkType workType = new WorkType("Moderating Discord", 30, "Tough job");
        WorkDone workDone = new WorkDone(LocalDateTime.of(2020, 11, 23, 22, 18, 54),
                LocalDateTime.of(2020, 11, 23, 22, 18, 55), workType, "String note");

        workTypeDao.createWorkType(workType);
        workDoneDao.createWorkDone(workDone);

        assertThat(workDone.getId())
                .isNotNull();
        assertThat(workDoneDao.findAllWorksDone())
                .usingElementComparatorIgnoringFields("workType")
                .containsExactly(workDone);
    }

    @Test
    void createWorkWithNullWT() {
        WorkDone workDone = new WorkDone(LocalDateTime.of(2020, 11, 23, 22, 18, 54),
                LocalDateTime.of(2020, 11, 23, 22, 18, 55), null, "String note");
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> workDoneDao.createWorkDone(workDone));
    }

    @Test
    void createWorkWithExistingId() {
        WorkType workType = new WorkType("Moderating Discord", 30, "Tough job");
        workTypeDao.createWorkType(workType);
        WorkDone workDone = new WorkDone(LocalDateTime.of(2020, 11, 23, 22, 18, 54),
                LocalDateTime.of(2020, 11, 23, 22, 18, 55), workType, "String note");
        workDone.setId(123L);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> workDoneDao.createWorkDone(workDone));
    }

    @Test
    void findAllEmpty() {
        assertThat(workDoneDao.findAllWorksDone())
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


        workTypeDao.createWorkType(type1);
        workTypeDao.createWorkType(type2);

        workDoneDao.createWorkDone(mod1);
        workDoneDao.createWorkDone(mod2);
        workDoneDao.createWorkDone(feeding);

        assertThat(workDoneDao.findAllWorksDone())
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


        workTypeDao.createWorkType(type1);
        workTypeDao.createWorkType(type2);

        workDoneDao.createWorkDone(mod1);
        workDoneDao.createWorkDone(feeding);

        workDoneDao.deleteWorkDone(mod1);

        assertThat(workDoneDao.findAllWorksDone())
                .usingElementComparatorIgnoringFields("workType")
                .containsExactly(feeding);
    }

    @Test
    void deleteWithNullId() {
        WorkType wt = new WorkType("Delivering pizza", 25, "By car");
        WorkDone wd = new WorkDone(LocalDateTime.of(2019, 5, 4, 3, 2, 1),
                LocalDateTime.of(2019, 6, 5, 4, 3, 2), wt, "4 pizzas delivered");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> workDoneDao.deleteWorkDone(wd));
    }

    @Test
    void updateWorkDone() {
        String originalDescription = "original description";
        String newDescription = "new desription";

        WorkType workType = new WorkType("Moderating Discord", 30, "Tough job");
        WorkDone workDone = new WorkDone(LocalDateTime.of(2020, 11, 23, 22, 18, 54),
                LocalDateTime.of(2020, 11, 23, 22, 18, 55), workType, originalDescription);

        workTypeDao.createWorkType(workType);
        workDoneDao.createWorkDone(workDone);

        workDone.setDescription(newDescription);
        workDoneDao.updateWorkDone(workDone);

        assertThat(findWorkDoneById(workDone.getId()).getDescription()).isEqualTo(newDescription);
    }

    @Test
    void createWorkType() {
        WorkType workType = new WorkType("Moderating Discord", 30, "Tough job");

        workTypeDao.createWorkType(workType);

        assertThat(workType.getId())
                .isNotNull();
    }

    @Test
    void deleteWorkType() {
        WorkType type1 = new WorkType("Moderating Discord", 30, "Tough job");
        WorkType type2 = new WorkType("Feeding pigeons", 0, "Fresh air");

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

        WorkType workType = new WorkType("Feeding pigeons", 0, originalDescription);

        workTypeDao.createWorkType(workType);
        workType.setDescription(newDescription);
        workTypeDao.updateWorkType(workType);

        assertThat(findWorkTypeById(workType.getId()).getDescription()).isEqualTo(newDescription);
    }

    private WorkDone findWorkDoneById(long id) {
        return workDoneDao.findAllWorksDone().stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No WorkDone with id " + id + " found"));
    }

    private WorkType findWorkTypeById(long id) {
        return workTypeDao.findAllWorkTypes().stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No WorkType with id " + id + " found"));
    }
}