package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.servlets.DefaultServlet.SortManager.Order;

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

     //注文情報の保存
    public boolean create(Order order) {
        Connection conn = null;

        String insertOrderSql = "INSERT INTO ORDERS(USER_ID, TOTAL_PRICE) VALUES(?, ?)";
        String insertItemSql  = "INSERT INTO ORDER_ITEMS(ORDER_ID, PRODUCT_ID, QUANTITY, PRICE) VALUES(?, ?, ?, ?)";

        try {
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            // トランザクションの開始
            conn.setAutoCommit(false);

            // ORDERSテーブルに挿入
            int generatedOrderId = -1;
            try (PreparedStatement pStmtOrder = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                pStmtOrder.setString(1, order.getUserId());
                pStmtOrder.setInt(2, order.getTotalPrice());

                int result = pStmtOrder.executeUpdate();
                if (result != 1) {
                    throw new SQLException("ORDERSテーブルへの挿入に失敗しました");
                }

                // 発行されたORDER_IDの取得
                try (ResultSet rs = pStmtOrder.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedOrderId = rs.getInt(1);
                    } else {
                        throw new SQLException("ORDER_IDの取得に失敗しました");
                    }
                }
            }

            // ORDER_ITEMSテーブルへ挿入
            try (PreparedStatement pStmtItem = conn.prepareStatement(insertItemSql)) {
                for (OrderItem item : order.getItemList()) {
                    pStmtItem.setInt(1, generatedOrderId);
                    pStmtItem.setInt(2, item.getProductId());
                    pStmtItem.setInt(3, item.getQuantity());
                    pStmtItem.setInt(4, item.getPrice());

                    pStmtItem.executeUpdate();
                }
            }

            // すべての処理が成功した場合にコミット実行
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // 処理途中でエラーが発生した場合は全部取り消す
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

     //ユーザーIDに紐づく注文履歴一覧の取得
    public List<Order> findByUserId(String userId) {
        List<Order> orderList = new ArrayList<>();

        String selectOrderSql = "SELECT ORDER_ID, TOTAL_PRICE, ORDER_DATE FROM ORDERS WHERE USER_ID = ? ORDER BY ORDER_ID DESC";
        String selectItemsSql = "SELECT ITEM_ID, PRODUCT_ID, QUANTITY, PRICE FROM ORDER_ITEMS WHERE ORDER_ID = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmtOrder = conn.prepareStatement(selectOrderSql)) {

            pStmtOrder.setString(1, userId);

            try (ResultSet rsOrder = pStmtOrder.executeQuery()) {
                while (rsOrder.next()) {
                    int orderId = rsOrder.getInt("ORDER_ID");
                    int totalPrice = rsOrder.getInt("TOTAL_PRICE");
                    String orderDate = rsOrder.getString("ORDER_DATE");

                    // 該当注文の明細一覧を取得
                    List<OrderItem> itemList = new ArrayList<>();
                    try (PreparedStatement pStmtItem = conn.prepareStatement(selectItemsSql)) {
                        pStmtItem.setInt(1, orderId);
                        try (ResultSet rsItem = pStmtItem.executeQuery()) {
                            while (rsItem.next()) {
                                OrderItem item = new OrderItem();
                                item.setItemId(rsItem.getInt("ITEM_ID"));
                                item.setOrderId(orderId);
                                item.setProductId(rsItem.getInt("PRODUCT_ID"));
                                item.setQuantity(rsItem.getInt("QUANTITY"));
                                item.setPrice(rsItem.getInt("PRICE"));
                                itemList.add(item);
                            }
                        }
                    }

                    Order order = new Order();
                    order.setOrderId(orderId);
                    order.setUserId(userId);
                    order.setTotalPrice(totalPrice);
                    order.setOrderDate(orderDate);
                    order.setItemList(itemList);

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