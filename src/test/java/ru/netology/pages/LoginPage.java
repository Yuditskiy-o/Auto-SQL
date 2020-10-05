package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public VerificationPage validAuth(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        return new VerificationPage();
    }

    public void invalidAuth(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        errorNotification.shouldBe(Condition.visible);
    }

    public void sendInvalidPasswordSecondTime(String password) {
        passwordField.setValue(password);
        loginButton.click();
    }

    public void sendInvalidPasswordThirdTime(String password) {
        passwordField.setValue(password);
        loginButton.click();
        loginButton.shouldBe(Condition.disabled);
    }
}
