package ru.netology.data;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor
public class DataHelper {
    private static final String url = "jdbc:mysql://192.168.99.100:3306/app";
    private static final String user = "app";
    private static final String password = "pass";

    @Value
    public static class VerificationCode {
        String code;
    }

    public static String getVerificationCodeForUser(String login) throws SQLException {
        String userId = null;
        val searchForId = "SELECT id FROM users WHERE login = ?;";
        try (
                val conn = DriverManager.getConnection(url, user, password);
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
                val conn = DriverManager.getConnection(url, user, password);
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
                val conn = DriverManager.getConnection(url, user, password)
        ) {
            runner.update(conn, codes);
            runner.update(conn, cards);
            runner.update(conn, users);
        }
    }
}
