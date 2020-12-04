package pv168.freelancer.ui.tablemodels;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * --Description here--
 *
 * @author
 */
public abstract class AbstractEntityTableModel<E> extends AbstractTableModel {

    private final List<Column<?, E>> columns;

    protected AbstractEntityTableModel(List<Column<?, E>> columns) {
        this.columns = columns;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getColumn(columnIndex).getValue(getEntity(rowIndex));
    }

    public abstract E getEntity(int rowIndex);

    @Override
    public String getColumnName(int columnIndex) {
        return getColumn(columnIndex).getColumnName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getColumn(columnIndex).getColumnClass();
    }

    private Column<?, E> getColumn(int columnIndex) {
        return columns.get(columnIndex);
    }
}
