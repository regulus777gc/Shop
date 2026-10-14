package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Order;
import model.OrderItem;

public class OrderDAO {
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

     //注文情報の保存（注文者情報・明細一覧を同一トランザクションで保存、失敗時はロールバック）
    public boolean create(Order order) {
        Connection conn = null;

        String insertOrderSql = "INSERT INTO ORDERS(ORDER_NO, NAME, EMAIL, ZIP, ADDRESS, SUBTOTAL, SHIPPING, TOTAL) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        String insertItemSql  = "INSERT INTO ORDER_ITEMS(ORDER_NO, PRODUCT_NAME, UNIT_PRICE, QUANTITY) VALUES(?, ?, ?, ?)";

        try {
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            // トランザクションの開始
            conn.setAutoCommit(false);

            // ORDERSテーブルへ注文者情報と金額情報を挿入
            try (PreparedStatement pStmtOrder = conn.prepareStatement(insertOrderSql)) {
                Customer customer = order.getCustomer();

                pStmtOrder.setString(1, order.getOrderNo());
                pStmtOrder.setString(2, customer != null ? customer.getName() : "");
                pStmtOrder.setString(3, customer != null ? customer.getEmail() : "");
                pStmtOrder.setString(4, customer != null ? customer.getZip() : "");
                pStmtOrder.setString(5, customer != null ? customer.getAddress() : "");
                pStmtOrder.setInt(6, order.getSubtotal());
                pStmtOrder.setInt(7, order.getShipping());
                pStmtOrder.setInt(8, order.getTotal());

                int result = pStmtOrder.executeUpdate();
                if (result != 1) {
                    throw new SQLException("ORDERSテーブルへの挿入に失敗しました");
                }
            }

            // 2. ORDER_ITEMS テーブルへ注文明細を挿入
            try (PreparedStatement pStmtItem = conn.prepareStatement(insertItemSql)) {
                for (OrderItem item : order.getItems()) {
                    pStmtItem.setString(1, order.getOrderNo());
                    pStmtItem.setString(2, item.getProductName());
                    pStmtItem.setInt(3, item.getUnitPrice());
                    pStmtItem.setInt(4, item.getQuantity());

                    pStmtItem.executeUpdate();
                }
            }

            // すべて成功した場合にコミット実行
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // エラーが発生した場合は全処理を取り消し
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * メールアドレスから注文履歴・検索結果を取得する
     */
    public List<Order> findByEmail(String email) {
        List<Order> orderList = new ArrayList<>();

        String selectOrderSql = "SELECT ORDER_NO, NAME, EMAIL, ZIP, ADDRESS, SUBTOTAL, SHIPPING, TOTAL, CREATED_AT FROM ORDERS WHERE EMAIL = ? ORDER BY CREATED_AT DESC";
        String selectItemsSql = "SELECT PRODUCT_NAME, UNIT_PRICE, QUANTITY FROM ORDER_ITEMS WHERE ORDER_NO = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmtOrder = conn.prepareStatement(selectOrderSql)) {

            pStmtOrder.setString(1, email);

            try (ResultSet rsOrder = pStmtOrder.executeQuery()) {
                while (rsOrder.next()) {
                    String orderNo = rsOrder.getString("ORDER_NO");

                    // Customerオブジェクトの復元
                    Customer customer = new Customer();
                    customer.setName(rsOrder.getString("NAME"));
                    customer.setEmail(rsOrder.getString("EMAIL"));
                    customer.setZip(rsOrder.getString("ZIP"));
                    customer.setAddress(rsOrder.getString("ADDRESS"));

                    int subtotal = rsOrder.getInt("SUBTOTAL");
                    int shipping = rsOrder.getInt("SHIPPING");
                    int total = rsOrder.getInt("TOTAL");
                    String createdAt = rsOrder.getString("CREATED_AT");

                    // 注文明細の取得
                    List<OrderItem> itemList = new ArrayList<>();
                    try (PreparedStatement pStmtItem = conn.prepareStatement(selectItemsSql)) {
                        pStmtItem.setString(1, orderNo);
                        try (ResultSet rsItem = pStmtItem.executeQuery()) {
                            while (rsItem.next()) {
                                String productName = rsItem.getString("PRODUCT_NAME");
                                int unitPrice = rsItem.getInt("UNIT_PRICE");
                                int quantity = rsItem.getInt("QUANTITY");

                                OrderItem item = new OrderItem(productName, unitPrice, quantity);
                                itemList.add(item);
                            }
                        }
                    }

                    Order order = new Order(orderNo, customer, subtotal, shipping, total, createdAt, itemList);
                    orderList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return orderList;
    }
}