package pv168.freelancer.data;

import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * --Description here--
 *
 * xkopeck5
 */
public class TestDataGenerator {

    private static final List<WorkType> WORK_TYPES = List.of(
            new WorkType("UI Design", new BigDecimal(5), "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam."),
            new WorkType("Training Pokemon", new BigDecimal(100),  "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt."),
            new WorkType("UX Lecturing", new BigDecimal(60), "Sed convallis magna eu sem."),
            new WorkType("Recording Despacito 2", new BigDecimal(42), "Nulla non lectus sed nisl molestie malesuada."),
            new WorkType("Updating Windows", new BigDecimal(666),  "In rutrum."),
            new WorkType("Making coffee", new BigDecimal(420), "Fusce dui leo, imperdiet in, aliquam sit amet, feugiat eu, orci."),
            new WorkType("Repairing coffee machine", new BigDecimal(99), "Morbi imperdiet, mauris ac auctor dictum, nisl ligula egestas nulla, et sollicitudin sem purus in lacus. Maecenas sollicitudin.")
    );

    private static final List<String> DESCRIPTIONS = List.of(
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam.",
            "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.",
            "Sed convallis magna eu sem.",
            "Nulla non lectus sed nisl molestie malesuada.",
            "In rutrum.",
            "Fusce dui leo, imperdiet in, aliquam sit amet, feugiat eu, orci.",
            "Duis risus.",
            "Proin mattis lacinia justo. Nunc auctor.",
            "Morbi imperdiet, mauris ac auctor dictum, nisl ligula egestas nulla, et sollicitudin sem purus in lacus. Maecenas sollicitudin. ",
            "Etiam dui sem, fermentum vitae, sagittis id, malesuada in, quam.");

    private static final List<String> NOTES = List.of(
            "Praesent in mauris.",
            "Etiam ligula.",
            "Phasellus et lorem.",
            "Donec iaculis gravida nulla.",
            "Nullam justo enim.",
            "Aenean fermentum risus id tortor.",
            "Duis risus.",
            "Phasellus enim erat.",
            "Sed ac dolor.",
            "Maecenas aliquet.");

    private static final LocalDate MIN_WORK = LocalDate.of(2017, JANUARY, 1);
    private static final LocalDate MAX_WORK = LocalDate.of(2020, DECEMBER, 31);

    private final Random random = new Random(129867358486L);


    public List<WorkType> getWorkTypes() {
        return WORK_TYPES;
    }

    public LocalDateTime getRandomDateTime() {
        LocalDate date = selectRandomLocalDate(MIN_WORK, MAX_WORK);
        double hour = Math.random() * 23;
        return date.atTime( (int) hour, 0);
    }

    public LocalDateTime getRandomDateTime(LocalDateTime start) {
        LocalDate date = selectRandomLocalDate(start.toLocalDate(), MAX_WORK);
        double hour;
        if (start.toLocalDate() == date) {
            hour = Math.random() * (24 - start.getHour()) + start.getHour();
        }
        else {
            hour = Math.random() * 24;
        }

        return date.atTime( (int) hour, 0);
    }

    public WorkDone createTestWorkDone() {
        LocalDateTime workStart = getRandomDateTime();
        LocalDateTime workEnd = getRandomDateTime(workStart);
        WorkType workType = selectRandom(WORK_TYPES);
        String note = selectRandom(NOTES);
        return new WorkDone( workStart, workEnd, workType, note);
    }

    public List<WorkDone> createTestData(int count) {
        return Stream
                .generate(this::createTestWorkDone)
                .limit(count)
                .collect(Collectors.toList());
    }

//    public WorkType[] createWorkTypeTestData(int count) {
//        WorkType[] arr = new WorkType[count];
//        for (int i = 0; i <count; i++) {
//            arr[i] = createTestWorkType();
//        }
//        return arr;
//    }

    private <T> T selectRandom(List<T> data) {
        int index = random.nextInt(data.size());
        return data.get(index);
    }

    private LocalDate selectRandomLocalDate(LocalDate min, LocalDate max) {
        int maxDays = Math.toIntExact(DAYS.between(min, max) + 1);
        int days = random.nextInt(maxDays);
        return min.plusDays(days);
    }

}
