package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[name=code]");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement error = $("[data-test-id=error-notification]");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void stepsForVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
    }

    public DashboardPage validVerify() {
        return new DashboardPage();
    }

    public void invalidVerify() {
        error.shouldBe(visible);
    }
}
