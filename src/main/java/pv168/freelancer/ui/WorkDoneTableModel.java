package pv168.freelancer.ui;

import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;
import pv168.freelancer.ui.utils.AbstractEntityTableModel;
import pv168.freelancer.ui.utils.Column;

import java.time.LocalDateTime;
import java.util.List;

public class WorkDoneTableModel extends AbstractEntityTableModel<WorkDone> {

    private static final List<Column<?, WorkDone>> COLUMNS = List.of(
            Column.build("Work Type", WorkType.class, WorkDone::getWorkType),
            Column.build("From", String.class, WorkDone::getFormattedWorkStart),
            Column.build("To", String.class, WorkDone::getFormattedWorkEnd),
            Column.build("Hours", Long.class, WorkDone::calculateHours),
            Column.build("Income", Double.class, WorkDone::calculatePay),
            Column.build("Description", String.class, WorkDone::getDescription)
            );

    //Column.build("Expected Pay", Long.class, WorkDone::calculatePay),

    private final List<WorkDone> worksDone;
    //private final WorkDoneDao workDoneDaoDao;

    public WorkDoneTableModel(List<WorkDone> workDoneList) {
        super(COLUMNS);
        this.worksDone = workDoneList;
    }

    @Override
    public int getRowCount() {
        return worksDone.size();
    }

    @Override
    protected WorkDone getEntity(int rowIndex) {
        return worksDone.get(rowIndex);
    }

    public void deleteRow(int rowIndex) {
        worksDone.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(WorkDone workDone) {
        int newRowIndex = worksDone.size();
        worksDone.add(workDone);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }
}
