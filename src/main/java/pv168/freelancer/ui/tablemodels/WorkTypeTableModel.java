package pv168.freelancer.ui.tablemodels;

import pv168.freelancer.data.WorkTypeDao;
import pv168.freelancer.model.WorkType;
import pv168.freelancer.ui.utils.I18N;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * --Description here--
 *
 * @author
 */
public class WorkTypeTableModel extends AbstractEntityTableModel<WorkType>  {


    private static final pv168.freelancer.ui.utils.I18N I18N = new I18N(WorkTypeTableModel.class);

    private static final List<Column<?, WorkType>> COLUMNS = List.of(
            Column.build(I18N.getString("id"), Long.class, WorkType::getId),
            Column.build(I18N.getString("name"), String.class, WorkType::getName),
            Column.build(I18N.getString("rate"), BigDecimal.class, WorkType::getHourlyRate),
            Column.build(I18N.getString("description"), String.class, WorkType::getDescription)
    );

    private final List<WorkType> workTypes;
    private final WorkTypeDao workTypeDao;

    public WorkTypeTableModel(WorkTypeDao workDao) {
        super(COLUMNS);
        this.workTypeDao = workDao;
        this.workTypes = new ArrayList<>(workDao.findAllWorkTypes());
    }


    @Override
    public int getRowCount() {
        return workTypes.size();
    }

    @Override
    public WorkType getEntity(int rowIndex) {
        return workTypes.get(rowIndex);
    }

    public void deleteRow(int rowIndex) {
        new DeleteWorker(workTypes.get(rowIndex).getId(), rowIndex).execute();

    }

    public void addRow(WorkType workType) {
        new CreateWorker(workType).execute();
    }

    public void editRow(int rowIndex, WorkType workType) {
        new UpdateWorker(workType, rowIndex).execute();
    }

    private class CreateWorker extends SwingWorker<WorkType, Void> {

        private WorkType workType;

        public CreateWorker(WorkType workType) {
            this.workType = workType;
        }
        @Override
        protected WorkType doInBackground() {

            workTypeDao.createWorkType(workType);
            return workType;
        }

        @Override
        protected void done() {
            workTypes.add(workType);
            fireTableRowsInserted(workTypes.size(), workTypes.size());
        }
    }

    private class UpdateWorker extends SwingWorker<WorkType, Void> {

        private WorkType workType;
        private int rowIndex;

        public UpdateWorker(WorkType workType, int rowIndex) {

            this.workType = workType;
            this.rowIndex = rowIndex;
        }
        @Override
        protected WorkType doInBackground() {
            workTypeDao.updateWorkType(workType);
            return workType;
        }

        @Override
        protected void done() {
            workTypes.remove(rowIndex);
            workTypes.add(rowIndex, workType);
            fireTableRowsInserted(workTypes.size(), workTypes.size());
            //fireTableDataChanged();
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
            workTypeDao.deleteWorkType(ID);
            return ID;
        }

        @Override
        protected void done() {
            workTypes.remove(index);
            fireTableRowsDeleted(index, index);
            fireTableDataChanged();
        }
    }

}

