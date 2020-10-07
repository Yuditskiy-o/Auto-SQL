package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public void stepsForAuth(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public VerificationPage validAuth() {
        return new VerificationPage();
    }

    public void invalidAuth() {
        errorNotification.shouldBe(Condition.visible);
    }

    public void sendInvalidPassword(String password) {
        passwordField.setValue(password);
        loginButton.click();
    }

    public void loginButtonShouldBeDisabled() {
        loginButton.shouldBe(Condition.disabled);
    }

    public void clearPasswordField() {
        passwordField.doubleClick().sendKeys(Keys.DELETE);
    }
}
