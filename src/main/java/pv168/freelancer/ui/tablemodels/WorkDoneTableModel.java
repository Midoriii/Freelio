package pv168.freelancer.ui.tablemodels;

import pv168.freelancer.data.WorkDoneDao;
import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;
import pv168.freelancer.ui.utils.I18N;

import javax.swing.*;
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
        new DeleteWorker(worksDone.get(rowIndex).getId(), rowIndex).execute();

    }

    public void addRow(WorkDone workDone) {
        new CreateWorker(workDone).execute();
    }

    public void editRow(int rowIndex, WorkDone workDone) {
        new UpdateWorker(workDone, rowIndex).execute();
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

    private class CreateWorker extends SwingWorker<WorkDone, Void> {

        private WorkDone workDone;

        public CreateWorker(WorkDone workDone) {
            this.workDone = workDone;
        }
        @Override
        protected WorkDone doInBackground() {

            workDao.createWorkDone(workDone);
            return workDone;
        }

        @Override
        protected void done() {
            worksDone.add(workDone);
            fireTableRowsInserted(worksDone.size() - 1, worksDone.size() - 1);
        }
    }

    private class UpdateWorker extends SwingWorker<WorkDone, Void> {

        private WorkDone workDone;
        private int rowIndex;

        public UpdateWorker(WorkDone workDone, int rowIndex) {

            this.workDone = workDone;
            this.rowIndex = rowIndex;
        }
        @Override
        protected WorkDone doInBackground() {
            workDao.updateWorkDone(workDone);
            return workDone;
        }

        @Override
        protected void done() {
            worksDone.remove(rowIndex);
            worksDone.add(rowIndex, workDone);
            fireTableRowsInserted(worksDone.size() - 1, worksDone.size() - 1);
        }
    }

    private class DeleteWorker extends SwingWorker<Long, Void> {

        private Long ID;
        private int index;

        public DeleteWorker(Long ID, int index) {

            this.ID = ID;
            this.index = index;
        }
        @Override
        protected Long doInBackground() {
            workDao.deleteWorkDone(ID);
            return ID;
        }

        @Override
        protected void done() {
            worksDone.remove(index);
            fireTableRowsDeleted(index, index);
            fireTableDataChanged();
        }
    }
}
