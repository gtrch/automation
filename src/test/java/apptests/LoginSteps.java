package apptests;

import io.cucumber.java.Before;
import org.openqa.selenium.By;
import io.cucumber.java.en.*;
import java.io.*;
import com.browserstack.AppPercySDK;

public class SignUpSteps {
    BaseActions actions = new BaseActions(DriverManager.driver);

    @Before
    public void setUp() throws Exception {
        DriverManager.init();
        actions = new BaseActions(DriverManager.driver);
    }

    private synchronized int getNextCounter() {
        String filePath = "counter.txt";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("500");
                }
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            int current = Integer.parseInt(reader.readLine().trim());
            reader.close();

            FileWriter writer = new FileWriter(file, false);
            writer.write(String.valueOf(current + 1));
            writer.close();

            return current;
        } catch (IOException e) {
            throw new RuntimeException("Error handling counter file", e);
        }
    }

    @When("the user navigates to sign up")
    public void the_user_navigates_to_sign_up() throws InterruptedException {
        By signUpBtn = By.xpath("//android.widget.TextView[@text=\"¿No tienes una cuenta? Registrate\"]");
        Thread.sleep(3000);
        if (actions.isVisible(signUpBtn, 10)) {
            actions.click(signUpBtn);
            Thread.sleep(2000);
        } else {
            System.out.println("Sign up button not found.");
        }
    }

    @And("enters new user information")
    public void enters_new_user_information() {
        int counter = getNextCounter();
        String email = "qa4+" + counter + "@redchapina.com";

        actions.type(By.xpath("//android.widget.EditText[@text=\"Correo electrónico\"]"), email);
        actions.type(By.xpath("//android.widget.EditText[@text=\"Número de celular\"]"), "30309046");
        actions.type(By.xpath("//android.widget.EditText[@text=\"Contraseña\"]"), "Test1234!");
        actions.type(By.xpath("//android.widget.EditText[@text=\"Confirmar contraseña\"]"), "Test1234!");

        String scenarioName = System.getProperty("CURRENT_SCENARIO");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Filled Sign Up Form");
    }

    @And("submits the signup form")
    public void submits_the_signup_form() {
        By confirmBtn = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[5]/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup");
        actions.click(confirmBtn);
    }

    @And("taps the sign up button")
    public void taps_the_sign_up_button() {
        By signupBtn = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[6]");
        actions.click(signupBtn);
    }

    @Then("a confirmation message should be displayed")
    public void a_confirmation_message_should_be_displayed() {
        boolean alertVisible = actions.isVisible(By.xpath("//android.widget.TextView[@resource-id=\"android:id/alertTitle\"]"), 15);
        boolean messageVisible = actions.isVisible(By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]"), 15);
        boolean okBtnVisible = actions.isVisible(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"), 30);

        assert(alertVisible && messageVisible && okBtnVisible);

        String scenarioName = System.getProperty("CURRENT_SCENARIO");
        AppPercySDK.screenshot(DriverManager.driver, scenarioName + " - Sign Up Confirmation");

        System.out.println("✔ Sign up test completed, confirmation shown.");
    }
}