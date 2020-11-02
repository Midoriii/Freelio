package pv168.freelancer.model;

import java.util.Date;

public class WorkDone {
    private Date workStart;
    private Date workEnd;
    private WorkType workType;

    public WorkDone(Date workStart, Date workEnd, WorkType workType) {
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.workType = workType;
    }

    public Date getWorkStart() {
        return workStart;
    }

    public void setWorkStart(Date workStart) {
        this.workStart = workStart;
    }

    public Date getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(Date workEnd) {
        this.workEnd = workEnd;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }
}
