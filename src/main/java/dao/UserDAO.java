package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class UserDAO {
    // ProductDAO / OrderDAO と同じ接続先。設定を1か所にまとめるときは、3つのDAOをまとめて直す。
    private final String JDBC_URL = "jdbc:h2:tcp://10.23.104.249/~/User";
    private final String DB_USER = "sa";
    private final String DB_PASS = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
    }

    /**
     * メールアドレスとパスワードを確認する。
     * 一致すれば User、一致しなければ null。
     * DBエラーは null にせず例外を投げる（「ログイン失敗」と「システム障害」を区別するため。500画面になる）。
     */
    public User authenticate(String email, String password) {
        String sql = "SELECT USER_ID, NAME, EMAIL, PASSWORD_HASH FROM USERS WHERE LOWER(EMAIL) = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setString(1, email.toLowerCase(Locale.ROOT));

            try (ResultSet rs = pStmt.executeQuery()) {
                if (!rs.next()) {
                    PasswordHasher.verifyDummy(password);
                    return null;
                }
                if (!PasswordHasher.verify(password, rs.getString("PASSWORD_HASH"))) {
                    return null;
                }
                return new User(rs.getInt("USER_ID"), rs.getString("NAME"), rs.getString("EMAIL"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("ユーザーの検索に失敗しました", e);
        }
    }
}