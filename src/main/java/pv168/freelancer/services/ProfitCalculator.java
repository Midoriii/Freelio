package pv168.freelancer.services;

import pv168.freelancer.model.WorkDone;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A card for cardLayout in MainWindow class, contains profit calculation functionality.
 *
 * @author xparoul
 */
public class ProfitCalculator {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<WorkDone> worksDone;

    public ProfitCalculator(LocalDate startDate, LocalDate endDate, List<WorkDone> worksDone) {
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);
        this.worksDone = Objects.requireNonNull(worksDone);
    }

    public BigDecimal calculateProfit() {
        BigDecimal profit = new BigDecimal(0);

        for (WorkDone workDone : worksDone) {
            if (!workDone.getWorkStart().toLocalDate().isBefore(startDate) &&
                    !workDone.getWorkEnd().toLocalDate().isAfter(endDate)) {
                profit = profit.add(workDone.calculatePay());
            }
        }

        return profit;
    }

}
