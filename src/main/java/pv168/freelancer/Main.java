package pv168.freelancer;

import pv168.freelancer.data.WorkDoneDao;
import pv168.freelancer.data.WorkTypeDao;
import pv168.freelancer.ui.MainWindow;

import javax.sql.DataSource;
import java.awt.*;

import org.apache.derby.jdbc.EmbeddedDataSource;

public class Main {

    public static void main(String[] args) {
        var dataSource = createDataSource();
        var workTypeDao = new WorkTypeDao(dataSource);
        var workDoneDao = new WorkDoneDao(dataSource);
        EventQueue.invokeLater(() -> new MainWindow(workDoneDao, workTypeDao).show());
    }

    private static DataSource createDataSource() {
        String dbPath = System.getProperty("user.home") + "/timesheet-for-freelancer";
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName(dbPath);
        dataSource.setCreateDatabase("create");
        return dataSource;
    }
}
