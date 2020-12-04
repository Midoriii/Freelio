package pv168.freelancer.ui.tablemodels;

import java.util.Objects;
import java.util.function.Function;

public class Column<T, E> {

    private final String columnName;
    private final Class<T> columnClass;
    private final Function<E, T> valueGetter;

    private Column(String columnName, Class<T> columnClass, Function<E, T> valueGetter) {
        this.columnName = Objects.requireNonNull(columnName, "columnName");
        this.columnClass = Objects.requireNonNull(columnClass, "columnClass");
        this.valueGetter = Objects.requireNonNull(valueGetter, "valueGetter");
    }

    public static <T, E> Column<T, E> build(String columnName, Class<T> columnClass, Function<E, T> valueGetter) {
        return new Column<>(columnName, columnClass, valueGetter);
    }

    String getColumnName() {
        return columnName;
    }

    Class<T> getColumnClass() {
        return columnClass;
    }

    Object getValue(E entity) {
        return valueGetter.apply(entity);
    }
}
