package pv168.freelancer.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;
import pv168.freelancer.services.ProfitCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

final class ProfitCalculatorTest {
    private static LocalDate start;
    private static LocalDate end;
    private static List<WorkDone> worksDone = new ArrayList<>();

    @BeforeAll
    static void initTestDataSource() {
        WorkType type1 = new WorkType("Moderating Discord", new BigDecimal(50.222), "Tough job");
        WorkType type2 = new WorkType("Feeding pigeons", new BigDecimal(1), "Fresh air");

        worksDone.add(new WorkDone(LocalDateTime.of(2021, 1, 1, 8, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0), type1, "1"));
        worksDone.add(new WorkDone(LocalDateTime.of(2021, 1, 2, 8, 0, 0),
                LocalDateTime.of(2021, 1, 2, 16, 0, 0), type1, "2"));
        worksDone.add(new WorkDone(LocalDateTime.of(2021, 1, 3, 8, 0, 0),
                LocalDateTime.of(2021, 1, 3, 20, 0, 0), type2, "3"));
    }

    @Test
    void calculateForAll() {
        start = LocalDate.of(2021, 1, 1);
        end = LocalDate.of(2021, 1, 3);

        ProfitCalculator calculator = new ProfitCalculator(start, end, worksDone);
        BigDecimal expected = (new BigDecimal(50.222).multiply(new BigDecimal(16))).add(new BigDecimal(12));

        assertThat(calculator.calculateProfit()).isEqualByComparingTo(expected);
    }

    @Test
    void calculateForMiddleWork() {
        start = LocalDate.of(2021, 1, 2);
        end = LocalDate.of(2021, 1, 2);

        ProfitCalculator calculator = new ProfitCalculator(start, end, worksDone);
        BigDecimal expected = new BigDecimal(50.222).multiply(new BigDecimal(8));

        assertThat(calculator.calculateProfit()).isEqualByComparingTo(expected);
    }

    @Test
    void calculateFromMiddleToFarFromRange() {
        start = LocalDate.of(2021, 1, 2);
        end = LocalDate.of(2021, 6, 5);

        ProfitCalculator calculator = new ProfitCalculator(start, end, worksDone);
        BigDecimal expected = (new BigDecimal(50.222).multiply(new BigDecimal(8))).add(new BigDecimal(12));

        assertThat(calculator.calculateProfit()).isEqualByComparingTo(expected);
    }

    @Test
    void calculateForInvalidRange() {
        start = LocalDate.of(2021, 1, 3);
        end = LocalDate.of(2021, 1, 1);

        ProfitCalculator calculator = new ProfitCalculator(start, end, worksDone);
        BigDecimal expected = new BigDecimal(0);

        assertThat(calculator.calculateProfit()).isEqualByComparingTo(expected);
    }
}
