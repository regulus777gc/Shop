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

/** DAO: products テーブルの読み書き。SQL はこのクラスにだけ書く。 */
public class ProductDAO {

    private static final String COLUMNS =
        "id, name, category, kind, tone, price, spec, description, specs";

    // 並べ替えは許可リスト方式（画面から来た文字列をSQLに直接入れない）
    private static final Map<String, String> SORTS = Map.of(
        "def", "id",
        "low", "price ASC, id",
        "high", "price DESC, id");

    /** カテゴリ・商品名・並べ替えで検索する。category と q は null か空なら無視する。 */
    public List<Product> search(String category, String q, String sort) {
        StringBuilder sql = new StringBuilder("SELECT " + COLUMNS + " FROM products WHERE 1 = 1");
        List<String> params = new ArrayList<>();

        if (category != null && Product.CATEGORIES.contains(category)) {
            sql.append(" AND category = ?");
            params.add(category);
        }
        if (q != null && !q.isEmpty()) {
            sql.append(" AND name LIKE ? ESCAPE '\\'");
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
                    list.add(map(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DataAccessException("商品の検索に失敗しました", e);
        }
    }

    /** 見つからなければ null。 */
    public Product findById(int id) {
        String sql = "SELECT " + COLUMNS + " FROM products WHERE id = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("商品の取得に失敗しました", e);
        }
    }

    /** 初期データ投入用。呼び出し側の接続・トランザクションの中で実行する。 */
    static void insertAll(Connection c, List<Product> products) throws SQLException {
        String sql = "INSERT INTO products (" + COLUMNS + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (Product p : products) {
                ps.setInt(1, p.getId());
                ps.setString(2, p.getName());
                ps.setString(3, p.getCategory());
                ps.setString(4, p.getKind());
                ps.setInt(5, p.getTone());
                ps.setInt(6, p.getPrice());
                ps.setString(7, p.getSpec());
                ps.setString(8, p.getDescription());
                ps.setString(9, encodeSpecs(p.getSpecs()));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private static Product map(ResultSet rs) throws SQLException {
        return new Product(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("category"),
            rs.getString("kind"),
            rs.getInt("tone"),
            rs.getInt("price"),
            rs.getString("spec"),
            rs.getString("description"),
            decodeSpecs(rs.getString("specs")));
    }

    // スペック表は「ラベル<TAB>値」を改行でつないだ1つの文字列として保存する
    private static String encodeSpecs(List<Spec> specs) {
        StringBuilder sb = new StringBuilder();
        for (Spec s : specs) {
            if (sb.length() > 0) {
                sb.append('\n');
            }
            sb.append(s.getLabel()).append('\t').append(s.getValue());
        }
        return sb.toString();
    }

    private static List<Spec> decodeSpecs(String text) {
        List<Spec> list = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return list;
        }
        for (String line : text.split("\n")) {
            int tab = line.indexOf('\t');
            if (tab > 0) {
                list.add(new Spec(line.substring(0, tab), line.substring(tab + 1)));
            }
        }
        return list;
    }

    private static String escapeLike(String s) {
        return s.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }
}
