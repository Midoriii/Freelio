package pv168.freelancer.ui.tablemodels;

import pv168.freelancer.data.WorkDoneDao;
import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;
import pv168.freelancer.ui.utils.I18N;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * --Description here--
 *
 * @author
 */
public class WorkDoneTableModel extends AbstractEntityTableModel<WorkDone> {

    private static final I18N I18N = new I18N(WorkDoneTableModel.class);

    private static final List<Column<?, WorkDone>> COLUMNS = List.of(
            Column.build(I18N.getString("workType"), WorkType.class, WorkDone::getWorkType),
            Column.build(I18N.getString("from"), String.class, WorkDone::getFormattedWorkStart),
            Column.build(I18N.getString("to"), String.class, WorkDone::getFormattedWorkEnd),
            Column.build(I18N.getString("hours"), Long.class, WorkDone::calculateHours),
            Column.build(I18N.getString("income"), BigDecimal.class, WorkDone::calculatePayRounded),
            Column.build(I18N.getString("note"), String.class, WorkDone::getDescription)
    );

    private final List<WorkDone> worksDone;
    private final WorkDoneDao workDao;

    public WorkDoneTableModel(WorkDoneDao workDao) {
        super(COLUMNS);
        this.workDao = workDao;
        this.worksDone = new ArrayList<>(workDao.findAllWorksDone());
    }

    @Override
    public int getRowCount() {
        return worksDone.size();
    }

    @Override
    public WorkDone getEntity(int rowIndex) {
        return worksDone.get(rowIndex);
    }

    public void deleteRow(int rowIndex) {
        workDao.deleteWorkDone(worksDone.get(rowIndex).getId());
        worksDone.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(WorkDone workDone) {
        int newRowIndex = worksDone.size();
        workDao.createWorkDone(workDone);
        worksDone.add(workDone);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void editRow(int rowIndex, WorkDone workDone) {
        worksDone.remove(rowIndex);
        worksDone.add(rowIndex, workDone);
        workDao.updateWorkDone(workDone);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public int workTypeCount(long workTypeID) {
        int count = 0;
        for (WorkDone work : worksDone) {
            if (work.getWorkType().getId() == workTypeID) {
                count++;
            }
        }
        return count;
    }
}
