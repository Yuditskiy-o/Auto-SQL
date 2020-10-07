package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {

    public static String getVerificationCodeForUser(DataHelper.AuthInfo authInfo) throws SQLException {
        val login = authInfo.getLogin();
        String userId = null;
        val searchForId = "SELECT id FROM users WHERE login = ?;";
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://192.168.99.100:3306/app", "app", "pass"
                );
                val idStmt = conn.prepareStatement(searchForId)
        ) {
            idStmt.setString(1, login);
            try (val rs = idStmt.executeQuery()) {
                if (rs.next()) {
                    // выборка значения по индексу столбца (нумерация с 1)
                    userId = rs.getString("id");
                }
            }
        }

        val verificationCode = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1;";
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://192.168.99.100:3306/app", "app", "pass"
                );
                val codeStmt = conn.prepareStatement(verificationCode)
        ) {
            codeStmt.setString(1, userId);
            try (val rs = codeStmt.executeQuery()) {
                if (rs.next()) {
                    // выборка значения по индексу столбца (нумерация с 1)
                    return rs.getString("code");
                }
            }
        }
        return null;
    }

    public static void cleanDb() throws SQLException {
        val runner = new QueryRunner();
        val codes = "DELETE FROM auth_codes";
        val cards = "DELETE FROM cards";
        val users = "DELETE FROM users";

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://192.168.99.100:3306/app", "app", "pass"
                )
        ) {
            runner.update(conn, codes);
            runner.update(conn, cards);
            runner.update(conn, users);
        }
    }
}
