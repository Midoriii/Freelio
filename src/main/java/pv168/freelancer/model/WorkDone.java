package pv168.freelancer.model;

import java.time.LocalDateTime;

public class WorkDone {
    private LocalDateTime workStart;
    private LocalDateTime workEnd;
    private WorkType workType;
    private String note;

    public WorkDone(){}

    public WorkDone(LocalDateTime workStart, LocalDateTime workEnd, WorkType workType, String note) {
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.workType = workType;
        this.note = note;
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

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }
}
