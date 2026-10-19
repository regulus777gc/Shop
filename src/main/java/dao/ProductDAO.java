package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Product;
import model.Product.Spec;

public class ProductDAO {

    private static final String COLUMNS =
        "PRODUCT_ID, NAME, CATEGORY, KIND, TONE, PRICE, SPEC, DESCRIPTION";

    // 並べ替えは許可リスト方式
    private static final Map<String, String> SORTS = Map.of(
        "def", "PRODUCT_ID",
        "low", "PRICE ASC, PRODUCT_ID",
        "high", "PRICE DESC, PRODUCT_ID");

    public List<Product> search(String category, String q, String sort) {
        StringBuilder sql = new StringBuilder("SELECT " + COLUMNS + " FROM PRODUCTS WHERE 1 = 1");
        List<String> params = new ArrayList<>();

        if (category != null && Product.CATEGORIES.contains(category)) {
            sql.append(" AND CATEGORY = ?");
            params.add(category);
        }
        if (q != null && !q.isEmpty()) {
            sql.append(" AND NAME LIKE ? ESCAPE '\\'");
            params.add("%" + escapeLike(q) + "%");
        }
        String order = SORTS.get(sort == null ? "def" : sort);
        sql.append(" ORDER BY ").append(order != null ? order : SORTS.get("def"));

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<Product> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(map(rs, new ArrayList<>()));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DataAccessException("商品の検索に失敗しました", e);
        }
    }

    /*。見つからなければ null。 */
    public Product findById(int id) {
        String sql = "SELECT " + COLUMNS + " FROM PRODUCTS WHERE PRODUCT_ID = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs, findSpecs(c, id)) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("商品の取得に失敗しました", e);
        }
    }

    private List<Spec> findSpecs(Connection c, int productId) throws SQLException {
        List<Spec> specs = new ArrayList<>();
        String sql = "SELECT SPEC_LABEL, SPEC_VALUE FROM PRODUCT_SPECS WHERE PRODUCT_ID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    specs.add(new Spec(rs.getString("SPEC_LABEL"), rs.getString("SPEC_VALUE")));
                }
            }
        }
        return specs;
    }

    private static Product map(ResultSet rs, List<Spec> specs) throws SQLException {
        return new Product(
            rs.getInt("PRODUCT_ID"),
            rs.getString("NAME"),
            rs.getString("CATEGORY"),
            rs.getString("KIND"),
            rs.getInt("TONE"),
            rs.getInt("PRICE"),
            rs.getString("SPEC"),
            rs.getString("DESCRIPTION"),
            specs);
    }

    private static String escapeLike(String s) {
        return s.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }
}