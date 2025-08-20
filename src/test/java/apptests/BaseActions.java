package apptests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import java.time.Duration;


public class BaseActions {

    protected WebDriver driver;

    public BaseActions(WebDriver driver) {
        this.driver = driver;
    }

    private static final Logger logger = Logger.getLogger(BaseActions.class.getName());

    public void click(By locator) {
        waitForVisible(locator, 20);
        driver.findElement(locator).click();
    }

    public void type(By locator, String text) {
        waitForVisible(locator, 20);
        try {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            System.out.println("Stale element. Retrying...");
            waitForVisible(locator, 10);
            driver.findElement(locator).sendKeys(text);
        }
    }

    public void waitForVisible(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean isVisible(By locator, int timeoutSeconds) {
        try {
            waitForVisible(locator, timeoutSeconds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void acceptLocationPermissionIfVisible() {
        By locationBtn = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]");

        if (isVisible(locationBtn, 25)) {
            driver.findElement(locationBtn).click();
        } else {
            System.out.println("!Error: Location permission not shown. Continuing...");
        }
    }

}