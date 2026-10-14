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

    // JDBCドライバのロード
    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
    }

     //商品一覧リストを取得する
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT PRODUCT_ID, NAME, PRICE, STOCK FROM PRODUCTS ORDER BY PRODUCT_ID";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql);
             ResultSet rs = pStmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("PRODUCT_ID");
                String name = rs.getString("NAME");
                int price = rs.getInt("PRICE");
                int stock = rs.getInt("STOCK");

                Product product = new Product(id, name, price, stock);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return productList;
    }

    //キーワード検索
    public List<Product> findByKeyword(String keyword) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT PRODUCT_ID, NAME, PRICE, STOCK FROM PRODUCTS WHERE NAME LIKE ? ORDER BY PRODUCT_ID";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            // キーワード検索用
            pStmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("PRODUCT_ID");
                    String name = rs.getString("NAME");
                    int price = rs.getInt("PRICE");
                    int stock = rs.getInt("STOCK");

                    Product product = new Product(id, name, price, stock);
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
        String sql = "SELECT PRODUCT_ID, NAME, PRICE, STOCK FROM PRODUCTS WHERE PRODUCT_ID = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setInt(1, productId);

            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("NAME");
                    int price = rs.getInt("PRICE");
                    int stock = rs.getInt("STOCK");

                    product = new Product(productId, name, price, stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return product;
    }
}