package apptests;

import io.cucumber.java.Before;
import org.openqa.selenium.By;
import io.cucumber.java.en.*;
import java.io.*;
import com.browserstack.AppPercySDK;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class SignUpSteps {
    private BaseActions actions;

    private BaseActions getActions() {
        if (actions == null) {
            actions = new BaseActions(DriverManager.driver);
        }
        return actions;
    }

    @When("the user navigates to sign up")
    public void the_user_navigates_to_sign_up() {
        By signUpBtn = AppiumBy.xpath("//android.widget.Button[@text=\"Crear cuenta\"]");
        getActions().click(signUpBtn);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "Signup");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Navigated to Sign Up");
    }

    @And("enters new user information")
    public void enters_new_user_information() {
        String newEmail = "user" + System.currentTimeMillis() + "@example.com";
        String newPassword = "Test@12345";

        By emailField = By.xpath("//android.widget.EditText[@text=\"Correo electrónico\"]");
        By passField = By.xpath("//android.widget.EditText[@text=\"Contraseña\"]");

        getActions().type(emailField, newEmail);
        getActions().type(passField, newPassword);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "Signup");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Entered Signup Info");
    }

    @And("submits the signup form")
    public void submits_the_signup_form() {
        By submitBtn = AppiumBy.xpath("//android.widget.Button[@text=\"Registrarse\"]");
        getActions().click(submitBtn);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "Signup");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Submitted Signup Form");
    }

    @Then("a confirmation message should be displayed")
    public void a_confirmation_message_should_be_displayed() {
        By confirmationMsg = AppiumBy.xpath("//android.widget.TextView[contains(@text,'Bienvenido')]");
        getActions().waitForVisible(confirmationMsg, 30);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "Signup");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Signup Confirmation");
    }
}
