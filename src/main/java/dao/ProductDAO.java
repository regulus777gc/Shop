package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductDAO {
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
     * 商品一覧を取得する
     */
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT PRODUCT_ID, NAME, CATEGORY, KIND, TONE, PRICE, SPEC, DESCRIPTION FROM PRODUCTS ORDER BY PRODUCT_ID";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql);
             ResultSet rs = pStmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("PRODUCT_ID");
                List<Product.Spec> specs = findSpecsByProductId(conn, id);

                Product product = createProductFromRs(rs, specs);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return productList;
    }

     //検索
    public List<Product> findByKeyword(String keyword) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT PRODUCT_ID, NAME, CATEGORY, KIND, TONE, PRICE, SPEC, DESCRIPTION "
                   + "FROM PRODUCTS WHERE NAME LIKE ? OR DESCRIPTION LIKE ? OR CATEGORY LIKE ? "
                   + "ORDER BY PRODUCT_ID";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pStmt.setString(1, searchPattern);
            pStmt.setString(2, searchPattern);
            pStmt.setString(3, searchPattern);

            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("PRODUCT_ID");
                    List<Product.Spec> specs = findSpecsByProductId(conn, id);

                    Product product = createProductFromRs(rs, specs);
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return productList;
    }

     //単一商品の取得
    public Product findById(int productId) {
        Product product = null;
        String sql = "SELECT PRODUCT_ID, NAME, CATEGORY, KIND, TONE, PRICE, SPEC, DESCRIPTION FROM PRODUCTS WHERE PRODUCT_ID = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setInt(1, productId);

            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    List<Product.Spec> specs = findSpecsByProductId(conn, productId);
                    product = createProductFromRs(rs, specs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return product;
    }

    private List<Product.Spec> findSpecsByProductId(Connection conn, int productId) throws SQLException {
        List<Product.Spec> specs = new ArrayList<>();
        String sql = "SELECT SPEC_LABEL, SPEC_VALUE FROM PRODUCT_SPECS WHERE PRODUCT_ID = ?";

        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, productId);
            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    String label = rs.getString("SPEC_LABEL");
                    String value = rs.getString("SPEC_VALUE");
                    specs.add(new Product.Spec(label, value));
                }
            }
        }
        return specs;
    }

    /**
     * ResultSet の現在行から Product インスタンスを生成する共通ヘルパーメソッド
     */
    private Product createProductFromRs(ResultSet rs, List<Product.Spec> specs) throws SQLException {
        int id = rs.getInt("PRODUCT_ID");
        String name = rs.getString("NAME");
        String category = rs.getString("CATEGORY");
        String kind = rs.getString("KIND");
        int tone = rs.getInt("TONE");
        int price = rs.getInt("PRICE");
        String spec = rs.getString("SPEC");
        String description = rs.getString("DESCRIPTION");

        return new Product(id, name, category, kind, tone, price, spec, description, specs);
    }
}