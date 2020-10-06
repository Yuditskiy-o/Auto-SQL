package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.pages.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SqlHelper.*;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    @DisplayName("Should clean SQL after login")
    public static void cleanDataBase() throws SQLException {
        cleanDb();
    }

    @Test
    @DisplayName("Should login successfully with provided data")
    void shouldEnterWhenValidData() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = getAuthInfo();
        loginPage.stepsForAuth(authInfo);
        val verificationPage = loginPage.validAuth();
        val verificationCode = getVerificationCodeForUser(authInfo);
        verificationPage.stepsForVerify(verificationCode);
        val dashboardPage = verificationPage.validVerify();
        dashboardPage.dashboardPage();
    }

    @Test
    @DisplayName("Should not login if username is invalid")
    void shouldNotEnterWhenInvalidLogin() {
        val loginPage = new LoginPage();
        val authInfo = getInvalidLoginForAuth();
        loginPage.stepsForAuth(authInfo);
        loginPage.invalidAuth();
    }

    @Test
    @DisplayName("Should not login if password is invalid")
    void shouldNotEnterWhenInvalidPassword() {
        val loginPage = new LoginPage();
        val authInfo = getInvalidPasswordForAuth();
        loginPage.stepsForAuth(authInfo);
        loginPage.invalidAuth();
    }

    @Test
    @DisplayName("Should not login if verification code is invalid")
    void shouldNotEnterWhenInvalidCode() {
        val loginPage = new LoginPage();
        val authInfo = getAuthInfo();
        loginPage.stepsForAuth(authInfo);
        val verificationPage = loginPage.validAuth();
        val verificationCode = getInvalidVerificationCode();
        verificationPage.stepsForVerify(verificationCode);
        verificationPage.invalidVerify();
    }

    @Test
    @DisplayName("Should not login if password incorrect 3 times in a row")
    void shouldNotEnterWhenInvalidPasswordThreeTimes() {
        val loginPage = new LoginPage();
        val authInfo = getInvalidPasswordForAuth();
        loginPage.stepsForAuth(authInfo);
        loginPage.invalidAuth();
        val invalidPassword = invalidPassword();
        loginPage.clearPasswordField();
        loginPage.sendInvalidPasswordSecondTime(invalidPassword);
        loginPage.clearPasswordField();
        loginPage.sendInvalidPasswordThirdTime(invalidPassword);
    }
}
