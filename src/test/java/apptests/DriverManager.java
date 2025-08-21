package apptests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    public static AndroidDriver driver;

    public static void init() throws Exception {
        String appUrl = System.getProperty("APP_URL");
        String deviceName = System.getProperty("DEVICE_NAME", "Samsung Galaxy S22 Ultra");
        String platformVersion = System.getProperty("PLATFORM_VERSION", "12.0");
        String automationName = System.getProperty("AUTOMATION_NAME", "UiAutomator2");
        String appiumUrl = System.getProperty("APPIUM_URL");

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(deviceName)
                .setPlatformName("Android")
                .setPlatformVersion(platformVersion)
                .setAutomationName(automationName);

        if (appiumUrl.contains("browserstack")) {
            HashMap<String, Object> bstackOptions = new HashMap<>();
            bstackOptions.put("projectName", "Automation CI");
            bstackOptions.put("buildName", "GitHub Actions Run");
            bstackOptions.put("sessionName", "Cucumber Tests");
            options.setCapability("bstack:options", bstackOptions);
            options.setCapability("appium:app", appUrl);
        } else {
            options.setApp(appUrl);
        }

        driver = new AndroidDriver(new URL(appiumUrl), options);
    }
}
