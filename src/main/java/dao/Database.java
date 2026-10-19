package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** H2データベースへの接続と、テーブル作成・初期データ投入。 */
public final class Database {

    private static final String[] SCHEMA = {
        "CREATE TABLE IF NOT EXISTS products ("
            + " id INT PRIMARY KEY,"
            + " name VARCHAR(100) NOT NULL,"
            + " category VARCHAR(20) NOT NULL,"
            + " kind VARCHAR(20) NOT NULL,"
            + " tone INT NOT NULL,"
            + " price INT NOT NULL CHECK (price >= 0),"
            + " spec VARCHAR(100) NOT NULL,"
            + " description VARCHAR(500) NOT NULL,"
            + " specs VARCHAR(1000) NOT NULL)",

        "CREATE TABLE IF NOT EXISTS orders ("
            + " id INT AUTO_INCREMENT PRIMARY KEY,"
            + " order_no VARCHAR(30) NOT NULL UNIQUE,"
            + " name VARCHAR(100) NOT NULL,"
            + " email VARCHAR(200) NOT NULL,"
            + " zip VARCHAR(8) NOT NULL,"
            + " address VARCHAR(300) NOT NULL,"
            + " subtotal INT NOT NULL,"
            + " shipping INT NOT NULL,"
            + " total INT NOT NULL,"
            + " created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)",

        // 注文した時点の商品名と価格をコピーして持つ（あとで商品の価格が変わっても注文は変わらない）
        "CREATE TABLE IF NOT EXISTS order_items ("
            + " id INT AUTO_INCREMENT PRIMARY KEY,"
            + " order_id INT NOT NULL,"
            + " product_id INT NOT NULL,"
            + " product_name VARCHAR(100) NOT NULL,"
            + " unit_price INT NOT NULL,"
            + " quantity INT NOT NULL CHECK (quantity > 0),"
            + " FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE)"
    };

    private static String url;

    private Database() {
    }

    /** アプリ起動時に1回だけ呼ぶ。テーブルがなければ作り、商品が空なら初期データを入れる。 */
    public static synchronized void init(String jdbcUrl) {
        url = jdbcUrl;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("H2のドライバが見つかりません（pom.xml の依存関係を確認してください）", e);
        }

        try (Connection c = getConnection()) {
            c.setAutoCommit(false);
            try (Statement st = c.createStatement()) {
                for (String sql : SCHEMA) {
                    st.execute(sql);
                }
                try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM products")) {
                    rs.next();
                    if (rs.getInt(1) == 0) {
                        ProductDAO.insertAll(c, SeedData.products());
                    }
                }
                c.commit();
            } catch (SQLException | RuntimeException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DataAccessException("データベースの初期化に失敗しました: " + url, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (url == null) {
            throw new IllegalStateException("Database.init() が呼ばれていません");
        }
        return DriverManager.getConnection(url, "sa", "");
    }
}
