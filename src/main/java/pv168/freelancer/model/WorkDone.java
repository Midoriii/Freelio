package pv168.freelancer.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * --Description here--
 *
 * @author xbenes2
 */
public class WorkDone {
    private Long id;
    private LocalDateTime workStart;
    private LocalDateTime workEnd;
    private WorkType workType;
    private String description;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm  dd.MM.yyyy");

    public WorkDone(LocalDateTime workStart, LocalDateTime workEnd, WorkType workType, String description) {
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.workType = workType;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getWorkStart() {
        return workStart;
    }

    public String getFormattedWorkStart(){
        return workStart.format(formatter);
    }

    public void setWorkStart(LocalDateTime workStart) {
        this.workStart = workStart;
    }

    public LocalDateTime getWorkEnd() {
        return workEnd;
    }

    public String getFormattedWorkEnd(){
        return workEnd.format(formatter);
    }

    public void setWorkEnd(LocalDateTime workEnd) {
        this.workEnd = workEnd;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public long calculateHours() {
        return LocalDateTime.from(workStart).until(workEnd, ChronoUnit.HOURS);
    }

    public BigDecimal calculatePay() {
        return workType.getHourlyRate().multiply(new BigDecimal(calculateHours()));
        //return (workType.getHourlyRate().multiply(new BigDecimal(calculateHours())).multiply(new BigDecimal(100))).divide(new BigDecimal(100)).round(new MathContext(2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkDone)) return false;
        WorkDone workDone = (WorkDone) o;
        return getId().equals(workDone.getId()) &&
                Objects.equals(getWorkStart(), workDone.getWorkStart()) &&
                Objects.equals(getWorkEnd(), workDone.getWorkEnd()) &&
                Objects.equals(getWorkType(), workDone.getWorkType()) &&
                Objects.equals(getDescription(), workDone.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWorkStart(), getWorkEnd(), getWorkType(), getDescription());
    }
}