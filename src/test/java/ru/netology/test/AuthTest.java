package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.getVerificationCodeForUser;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    @DisplayName("Should clean SQL after login")
    public static void cleanDataBase() throws SQLException {
        DataHelper.cleanDb();
    }

    @Test
    @DisplayName("Should login successfully with provided data")
    void shouldEnterWhenValidData() throws SQLException {
        val loginPage = new LoginPage();
        val login = "vasya";
        val password = "qwerty123";
        val verificationPage = loginPage.validAuth(login, password);
        val verificationCode = getVerificationCodeForUser(login);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.dashboardPage();
    }

    @Test
    @DisplayName("Should not login if username is invalid")
    void shouldNotEnterWhenInvalidLogin() {
        val loginPage = new LoginPage();
        val login = "petya";
        val password = "qwerty123";
        loginPage.invalidAuth(login, password);
    }

    @Test
    @DisplayName("Should not login if password is invalid")
    void shouldNotEnterWhenInvalidPassword() {
        val loginPage = new LoginPage();
        val login = "vasya";
        val password = "423fsf3f3";
        loginPage.invalidAuth(login, password);
    }

    @Test
    @DisplayName("Should not login if verification code is invalid")
    void shouldNotEnterWhenInvalidCode() {
        val loginPage = new LoginPage();
        val login = "vasya";
        val password = "qwerty123";
        val verificationPage = loginPage.validAuth(login, password);
        val verificationCode = "765756756756756";
        verificationPage.invalidVerify(verificationCode);
    }

    @Test
    @DisplayName("Should not login if password incorrect 3 times in a row")
    void shouldNotEnterWhenInvalidPasswordThreeTimes() {
        val loginPage = new LoginPage();
        val login = "vasya";
        val password = "fsdf43f4323f34gf3";
        loginPage.invalidAuth(login, password);
        loginPage.clearPasswordField();
        loginPage.sendInvalidPasswordSecondTime(password);
        loginPage.clearPasswordField();
        loginPage.sendInvalidPasswordThirdTime(password);
    }
}
