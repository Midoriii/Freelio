package pv168.freelancer.services;

import pv168.freelancer.data.WorkDoneDao;
import pv168.freelancer.model.WorkDone;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A card for cardLayout in MainWindow class, contains profit calculation functionality.
 *
 * @author xparoul
 */
public class ProfitCalculator {
    private LocalDate startDate;
    private LocalDate endDate;
    private final WorkDoneDao workDoneDao;

    public ProfitCalculator(LocalDate startDate, LocalDate endDate, WorkDoneDao workDoneDao) {
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);
        this.workDoneDao = Objects.requireNonNull(workDoneDao);
    }

    public BigDecimal calculateProfit() {
        List<WorkDone> worksDone = workDoneDao.findAllWorksDone();
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
