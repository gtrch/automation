package apptests;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) throws Exception {
        DriverManager.init();

        if (scenario != null && scenario.getName() != null) {
            System.setProperty("CURRENT_SCENARIO", scenario.getName());
        } else {
            System.setProperty("CURRENT_SCENARIO", "Unnamed Scenario");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (DriverManager.driver != null) {
            DriverManager.driver.quit();
            DriverManager.driver = null;
            System.out.println("--- Driver session closed ---");
        }

        if (scenario.isFailed()) {
            try {
                String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());

                File reportDir = new File("reports");
                if (!reportDir.exists()) {
                    reportDir.mkdirs();
                }

                File originalReport = new File("reports/cucumber-report.html");
                File renamedReport = new File("reports/cucumber-report-" + timestamp + ".html");

                if (originalReport.exists()) {
                    boolean renamed = originalReport.renameTo(renamedReport);
                    if (renamed) {
                        Runtime.getRuntime().exec("xdg-open " + renamedReport.getPath());
                    } else {
                        System.err.println("Failed to rename report file.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}