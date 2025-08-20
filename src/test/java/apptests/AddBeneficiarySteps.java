package apptests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import io.cucumber.java.en.*;
import io.github.cdimascio.dotenv.Dotenv;
import com.browserstack.AppPercySDK;

import java.util.Random;

public class AddBeneficiarySteps {

    BaseActions actions;
    Dotenv dotenv = Dotenv.load();
    Faker faker = new Faker();

    private BaseActions getActions() {
        if (actions == null) {
            actions = new BaseActions(DriverManager.driver);
        }
        return actions;
    }

    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = "test+" + firstName.toLowerCase() + lastName.toLowerCase() + "@redchapina.com";
    String phone = "55555" + (10000 + new Random().nextInt(89999));

    @Given("location permission is granted")
    public void location_permission_is_granted() {
        getActions().acceptLocationPermissionIfVisible();
    }

    @Given("the user is logged in and starts the Add Beneficiary flow")
    public void the_user_is_logged_in_and_starts_the_flow() {
        String loginEmail = dotenv.get("LOGIN_EMAIL");
        String loginPassword = dotenv.get("LOGIN_PASSWORD");

        By emailField = By.xpath("//android.widget.EditText[@text=\"Correo electrónico\"]");
        By passField = By.xpath("//android.widget.EditText[@text=\"Contraseña\"]");
        By loginBtn = By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[4]");

        getActions().type(emailField, loginEmail);
        getActions().type(passField, loginPassword);
        getActions().click(loginBtn);

        try {
            Thread.sleep(10000);
            assertNotNull(DriverManager.driver.getSessionId(), "!Error: Login failed or session lost");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getActions().click(By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[4]/android.view.ViewGroup[1]/android.view.ViewGroup"));
        getActions().click(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]"));
        getActions().click(By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]"));
    }

    @When("the user fills in the beneficiary form")
    public void the_user_fills_in_the_form() {
        getActions().type(By.xpath("//android.widget.EditText[@text=\"Primer nombre\"]"), firstName);
        getActions().type(By.xpath("//android.widget.EditText[@text=\"Primer apellido\"]"), lastName);

        getActions().click(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[5]/android.view.ViewGroup/android.view.ViewGroup"));
        getActions().click(By.xpath("//android.widget.TextView[@text=\"Amistad\"]"));

        getActions().click(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[6]"));

        getActions().click(By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup"));
        getActions().click(By.xpath("//android.widget.TextView[@text=\"Estados Unidos\"]"));
        getActions().click(By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup"));
        getActions().click(By.xpath("//android.widget.TextView[@text=\"Alaska\"]"));
        getActions().click(By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup/android.view.ViewGroup"));
        getActions().click(By.xpath("//android.widget.TextView[@text=\"Adak\"]"));

        getActions().type(By.xpath("//android.widget.EditText[@text=\"Dirección\"]"), "123 Main St");
        getActions().click(By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[3]/android.view.ViewGroup"));
        getActions().type(By.xpath("//android.widget.EditText[@text=\"Correo electrónico\"]"), email);
        getActions().type(By.xpath("//android.widget.EditText[@text=\"Número de celular\"]"), phone);
        getActions().click(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup"));
        getActions().click(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup[2]"));

        String scenarioName = System.getProperty("CURRENT_SCENARIO");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Filled Add Beneficiary Form");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}
    }
}