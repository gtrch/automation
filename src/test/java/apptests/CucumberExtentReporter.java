package apptests;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CucumberExtentReporter {

    public static void main(String[] args) throws Exception {
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String outputDir = "reports";
        String filePath = outputDir + "/visual-dashboard-" + timestamp + ".html";

        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        ExtentSparkReporter spark = new ExtentSparkReporter(filePath);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);

        JSONParser parser = new JSONParser();
        JSONArray features = (JSONArray) parser.parse(new FileReader("target/cucumber-report.json"));

        for (Object f : features) {
            JSONObject feature = (JSONObject) f;
            String featureName = (String) feature.get("name");
            ExtentTest featureTest = extent.createTest("Feature: " + featureName);

            JSONArray scenarios = (JSONArray) feature.get("elements");
            for (Object s : scenarios) {
                JSONObject scenario = (JSONObject) s;
                String scenarioName = (String) scenario.get("name");
                ExtentTest scenarioTest = featureTest.createNode("Scenario: " + scenarioName);

                JSONArray steps = (JSONArray) scenario.get("steps");
                for (Object stepObj : steps) {
                    JSONObject step = (JSONObject) stepObj;
                    String stepName = (String) step.get("name");
                    JSONObject result = (JSONObject) step.get("result");
                    String status = (String) result.get("status");

                    switch (status) {
                        case "passed":
                            scenarioTest.pass(stepName);
                            break;
                        case "failed":
                            scenarioTest.fail(stepName);
                            break;
                        case "skipped":
                            scenarioTest.skip(stepName);
                            break;
                        default:
                            scenarioTest.info(stepName);
                    }
                }
            }
        }

        extent.flush();
        System.out.println("✔ Dashboard saved at: " + filePath);

        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            Runtime.getRuntime().exec(new String[]{"xdg-open", filePath});
        } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", filePath});
        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            Runtime.getRuntime().exec(new String[]{"open", filePath});
        }
    }
}