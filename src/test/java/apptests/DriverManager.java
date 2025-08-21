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

    private static String readVar(String key, Dotenv dotenv) {
        String v = System.getenv(key);
        if (v == null || v.isBlank()) v = dotenv != null ? dotenv.get(key) : null;
        return v;
    }

    public static void init() throws Exception {
        Dotenv dotenv = null;
        try { dotenv = Dotenv.configure().ignoreIfMissing().load(); } catch (Exception ignored) {}

        String appiumUrl       = readVar("APPIUM_URL", dotenv);
        String apkPath         = readVar("APK_PATH", dotenv);
        String deviceName      = readVar("DEVICE_NAME", dotenv);
        String platformName    = readVar("PLATFORM_NAME", dotenv);
        String platformVersion = readVar("PLATFORM_VERSION", dotenv);
        String automationName  = readVar("AUTOMATION_NAME", dotenv);
        String avdName         = readVar("AVD_NAME", dotenv);
        String user            = readVar("BROWSERSTACK_USERNAME", dotenv);
        String key             = readVar("BROWSERSTACK_ACCESS_KEY", dotenv);

        if (appiumUrl != null && appiumUrl.contains("browserstack.com")) {
            // --------------------------
            // BrowserStack configuration
            // --------------------------
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("app", apkPath);

            Map<String, Object> bstackOptions = new HashMap<>();
            bstackOptions.put("userName", user);
            bstackOptions.put("accessKey", key);
            bstackOptions.put("deviceName", deviceName);
            bstackOptions.put("platformVersion", platformVersion);
            bstackOptions.put("platformName", platformName);

            caps.setCapability("bstack:options", bstackOptions);

            driver = new AndroidDriver(new URL(appiumUrl), caps);
        } else {

            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName(deviceName)
                    .setPlatformName(platformName)
                    .setPlatformVersion(platformVersion)
                    .setAutomationName(automationName)
                    .setNewCommandTimeout(java.time.Duration.ofSeconds(200))
                    .setFullReset(true)
                    .setNoReset(false)
                    .setApp(apkPath);

            if (avdName != null && !avdName.isBlank()) {
                options.setAvd(avdName).setAvdLaunchTimeout(java.time.Duration.ofMillis(200000));
            }

            driver = new AndroidDriver(new URL(appiumUrl), options);
        }
    }
}