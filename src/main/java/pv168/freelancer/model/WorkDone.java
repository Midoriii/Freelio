package pv168.freelancer.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class WorkDone {
    private LocalDateTime workStart;
    private LocalDateTime workEnd;
    private WorkType workType;
    private String description;

    public WorkDone(){}

    public WorkDone(LocalDateTime workStart, LocalDateTime workEnd, WorkType workType, String note) {
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.workType = workType;
        this.description = note;
    }

    public LocalDateTime getWorkStart() {
        return workStart;
    }

    public void setWorkStart(LocalDateTime workStart) {
        this.workStart = workStart;
    }

    public LocalDateTime getWorkEnd() {
        return workEnd;
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

    public double calculatePay() {
        return calculateHours() * workType.getHourlyRate();
    }
}
