package dao;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import model.User;

/** DAO: users テーブル。パスワードのハッシュ化（PBKDF2）もこのクラスの中で行う。 */
public class UserDAO {

    // 保存形式: pbkdf2_sha256$反復回数$salt(Base64)$hash(Base64)
    private static final String PREFIX = "pbkdf2_sha256";
    private static final int ITERATIONS = 600_000;
    private static final SecureRandom RANDOM = new SecureRandom();
    // ユーザーが存在しないときにも同じだけ時間をかけるためのダミー（RANDOM より後に宣言すること）
    private static final String DUMMY = hash("dummy-password");

    /**
     * メールアドレスとパスワードを確認する。
     * 一致すれば User、一致しなければ null。DBエラーは DataAccessException。
     */
    public User authenticate(String email, String password) {
        String sql = "SELECT id, name, email, password_hash FROM users WHERE LOWER(email) = ?";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email.toLowerCase(Locale.ROOT));

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    verify(password, DUMMY);
                    return null;
                }
                if (!verify(password, rs.getString("password_hash"))) {
                    return null;
                }
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("ユーザーの検索に失敗しました", e);
        }
    }

    /** パスワードを保存用の文字列にする（Database のデモユーザー作成でも使う）。 */
    static String hash(String password) {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        byte[] key = derive(password, salt, ITERATIONS, 256);
        Base64.Encoder enc = Base64.getEncoder();
        return PREFIX + "$" + ITERATIONS + "$" + enc.encodeToString(salt) + "$" + enc.encodeToString(key);
    }

    private static boolean verify(String password, String stored) {
        if (stored == null) {
            return false;
        }
        String[] p = stored.split("\\$");
        if (p.length != 4 || !PREFIX.equals(p[0])) {
            return false;
        }
        try {
            byte[] salt = Base64.getDecoder().decode(p[2]);
            byte[] expected = Base64.getDecoder().decode(p[3]);
            byte[] actual = derive(password, salt, Integer.parseInt(p[1]), expected.length * 8);
            return MessageDigest.isEqual(expected, actual); // 定数時間で比較
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] derive(String password, byte[] salt, int iterations, int bits) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, bits);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("パスワードのハッシュ化に失敗しました", e);
        } finally {
            spec.clearPassword();
        }
    }
}