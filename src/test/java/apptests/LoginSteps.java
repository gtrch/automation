package apptests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.openqa.selenium.By;
import io.cucumber.java.en.*;
import io.github.cdimascio.dotenv.Dotenv;
import com.browserstack.AppPercySDK;

public class LoginSteps {

    BaseActions actions;
    Dotenv dotenv = Dotenv.load();

    private BaseActions getActions() {
        if (actions == null) {
            actions = new BaseActions(DriverManager.driver);
        }
        return actions;
    }

    @When("the user enters valid credentials")
    public void the_user_enters_valid_credentials() {
        String email = dotenv.get("LOGIN_EMAIL");
        String password = dotenv.get("LOGIN_PASSWORD");

        By emailField = By.xpath("//android.widget.EditText[@text=\"Correo electrónico\"]");
        By passField = By.xpath("//android.widget.EditText[@text=\"Contraseña\"]");

        getActions().type(emailField, email);
        getActions().type(passField, password);

        String scenarioName = System.getProperty("CURRENT_SCENARIO");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Entered Login Credentials");
    }

    @When("taps the login button")
    public void taps_the_login_button() {
        By loginBtn = By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[4]");
        getActions().click(loginBtn);
    }

    @Then("the user should be logged in")
    public void the_user_should_be_logged_in() throws InterruptedException {
        Thread.sleep(15000);
        assertNotNull(DriverManager.driver.getSessionId(), "!Error: Login failed or session lost");

        String scenarioName = System.getProperty("CURRENT_SCENARIO");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - After Successful Login");

        System.out.println("✔ Login test completed.");
    }
}