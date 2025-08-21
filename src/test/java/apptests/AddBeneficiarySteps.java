package apptests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import io.cucumber.java.en.*;
import io.github.cdimascio.dotenv.Dotenv;
import com.browserstack.AppPercySDK;

import java.util.Random;
import io.appium.java_client.AppiumBy;

public class AddBeneficiarySteps {
    private BaseActions actions;

    private BaseActions getActions() {
        if (actions == null) {
            actions = new BaseActions(DriverManager.driver);
        }
        return actions;
    }

    @And("location permission is granted")
    public void location_permission_is_granted() {
        getActions().acceptLocationPermissionIfVisible();

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "AddBeneficiary");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Location Permission");
    }

    @When("the user navigates to Add Beneficiary")
    public void the_user_navigates_to_add_beneficiary() {
        By addBeneficiaryBtn = AppiumBy.xpath("//android.widget.Button[@text=\"Añadir beneficiario\"]");
        getActions().click(addBeneficiaryBtn);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "AddBeneficiary");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Navigated to Add Beneficiary");
    }

    @And("fills in beneficiary details")
    public void fills_in_beneficiary_details() {
        String name = "Beneficiary " + System.currentTimeMillis();
        String phone = "555" + (int)(Math.random() * 10000);

        By nameField = By.xpath("//android.widget.EditText[@text=\"Nombre\"]");
        By phoneField = By.xpath("//android.widget.EditText[@text=\"Teléfono\"]");

        getActions().type(nameField, name);
        getActions().type(phoneField, phone);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "AddBeneficiary");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Entered Beneficiary Details");
    }

    @And("saves the beneficiary")
    public void saves_the_beneficiary() {
        By saveBtn = AppiumBy.xpath("//android.widget.Button[@text=\"Guardar\"]");
        getActions().click(saveBtn);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "AddBeneficiary");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Saved Beneficiary");
    }

    @Then("the new beneficiary should appear in the list")
    public void the_new_beneficiary_should_appear_in_the_list() {
        By beneficiaryList = AppiumBy.xpath("//android.widget.TextView[contains(@text,'Beneficiary')]");
        getActions().waitForVisible(beneficiaryList, 30);

        String scenarioName = System.getProperty("CURRENT_SCENARIO", "AddBeneficiary");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Beneficiary Listed");
    }
}
