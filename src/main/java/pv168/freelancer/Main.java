package pv168.freelancer;

import pv168.freelancer.data.TestDataGenerator;
import pv168.freelancer.data.WorkDao;
import pv168.freelancer.ui.MainWindow;

import javax.sql.DataSource;
import java.awt.*;

import org.apache.derby.jdbc.EmbeddedDataSource;

public class Main {

    public static void main(String[] args) {
        TestDataGenerator testDataGenerator = new TestDataGenerator();
        WorkDao workDao = new WorkDao(createDataSource(), testDataGenerator);
        EventQueue.invokeLater(() -> new MainWindow(workDao).show());
    }

    private static DataSource createDataSource() {
        String dbPath = System.getProperty("user.home") + "/timesheet-for-freelancer";
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName(dbPath);
        dataSource.setCreateDatabase("create");
        return dataSource;
    }
}
