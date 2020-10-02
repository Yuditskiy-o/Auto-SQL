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
import static ru.netology.data.DataHelper.*;

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
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        val verificationCode = getVerificationCodeForVasya();
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.dashboardPage();
    }

    @Test
    @DisplayName("Should not login if username is invalid")
    void shouldNotEnterWhenInvalidLogin() {
        val loginPage = new LoginPage();
        val authInfo = getInvalidLoginForAuth();
        loginPage.invalidAuth(authInfo);
    }

    @Test
    @DisplayName("Should not login if password is invalid")
    void shouldNotEnterWhenInvalidPassword() {
        val loginPage = new LoginPage();
        val authInfo = getInvalidPasswordForAuth();
        loginPage.invalidAuth(authInfo);
    }

    @Test
    @DisplayName("Should not login if verification code is invalid")
    void shouldNotEnterWhenInvalidCode() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        val verificationCode = DataHelper.getInvalidVerificationCode();
        verificationPage.invalidVerify(verificationCode);
    }
}
