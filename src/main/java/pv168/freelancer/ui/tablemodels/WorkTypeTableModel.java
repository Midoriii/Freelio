package pv168.freelancer.ui.tablemodels;

import pv168.freelancer.data.WorkTypeDao;
import pv168.freelancer.model.WorkType;
import pv168.freelancer.ui.utils.I18N;

import java.util.List;

/**
 * --Description here--
 *
 * @author
 */
public class WorkTypeTableModel extends AbstractEntityTableModel<WorkType> {

    private static final pv168.freelancer.ui.utils.I18N I18N = new I18N(WorkTypeTableModel.class);

    private static final List<Column<?, WorkType>> COLUMNS = List.of(
            Column.build(I18N.getString("id"), Long.class, WorkType::getId),
            Column.build(I18N.getString("name"), String.class, WorkType::getName),
            Column.build(I18N.getString("rate"), double.class, WorkType::getHourlyRate),
            Column.build(I18N.getString("description"), String.class, WorkType::getDescription)
    );

    private final List<WorkType> workTypes;
    private final WorkTypeDao workTypeDao;

    public WorkTypeTableModel(List<WorkType> workTypeList, WorkTypeDao workDao) {
        super(COLUMNS);
        this.workTypeDao = workDao;
        this.workTypes = workDao.findAllWorkTypes();
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
        workTypeDao.deleteWorkType(workTypes.get(rowIndex));
        workTypes.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(WorkType workType) {
        int newRowIndex = workTypes.size();
        workTypeDao.createWorkType(workType);
        workTypes.add(workType);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void editRow(int rowIndex, WorkType workType) {
        workTypes.remove(rowIndex);
        workTypes.add(rowIndex, workType);
        workTypeDao.updateWorkType(workType);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

}

