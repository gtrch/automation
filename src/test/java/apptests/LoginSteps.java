package apptests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.openqa.selenium.By;
import io.cucumber.java.en.*;
import io.github.cdimascio.dotenv.Dotenv;
import com.browserstack.AppPercySDK;
import io.appium.java_client.AppiumBy;

public class LoginSteps {
    private BaseActions actions;

    private BaseActions getActions() {
        if (actions == null) {
            actions = new BaseActions(DriverManager.driver);
        }
        return actions;
    }

    @When("the user enters valid credentials")
    public void the_user_enters_valid_credentials() {
        String email = System.getenv("LOGIN_EMAIL");
        String password = System.getenv("LOGIN_PASSWORD");

        By emailField = By.xpath("//android.widget.EditText[@text=\"Correo electrónico\"]");
        By passField = By.xpath("//android.widget.EditText[@text=\"Contraseña\"]");

        getActions().type(emailField, email);
        getActions().type(passField, password);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "Login");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Entered Login Credentials");
    }

    @And("taps the login button")
    public void taps_the_login_button() {
        By loginBtn = AppiumBy.xpath("//android.widget.Button[@text=\"Iniciar sesión\"]");
        getActions().click(loginBtn);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "Login");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Login Button Tapped");
    }

    @Then("the user should be logged in")
    public void the_user_should_be_logged_in() {
        By homeScreenElement = AppiumBy.xpath("//android.widget.TextView[@text=\"Inicio\"]");
        getActions().waitForVisible(homeScreenElement, 30);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "Login");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Home Screen");
    }
}
