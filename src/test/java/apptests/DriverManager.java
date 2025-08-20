package apptests;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.AndroidDriver;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.Duration;
import java.net.URL;
import java.net.URI;

public class DriverManager {
    public static AndroidDriver driver;

    public static void init() throws Exception {
        if (driver != null) {
            driver.quit();
            driver = null;
        }

        Dotenv dotenv = Dotenv.load();
        String appiumUrl = dotenv.get("APPIUM_URL");
        String apkPath = dotenv.get("APK_PATH");
        String deviceName = dotenv.get("DEVICE_NAME");
        String platformName = dotenv.get("PLATFORM_NAME");
        String platformVersion = dotenv.get("PLATFORM_VERSION");
        String automationName = dotenv.get("AUTOMATION_NAME");
        String avdName = dotenv.get("AVD_NAME");

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(deviceName)
                .setPlatformName(platformName)
                .setPlatformVersion(platformVersion)
                .setAutomationName(automationName)
                .setAvd(avdName)
                .setAvdLaunchTimeout(Duration.ofMillis(200000))
                .setNewCommandTimeout(Duration.ofSeconds(200000))
                .setFullReset(true)
                .setNoReset(false)
                .setApp(apkPath);

        URL url = URI.create(appiumUrl).toURL();
        driver = new AndroidDriver(url, options);
    }
}