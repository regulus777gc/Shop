package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Model: 注文者（お届け先）。入力チェックもここで行う。 */
public class Customer {
    private static final Pattern ZIP = Pattern.compile("^(\\d{3})-?(\\d{4})$");
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private String name = "";
    private String email = "";
    private String zip = "";
    private String address = "";

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getZip() { return zip; }
    public String getAddress() { return address; }

    public void setName(String name) { this.name = trim(name); }
    public void setEmail(String email) { this.email = trim(email); }
    public void setZip(String zip) { this.zip = trim(zip); }
    public void setAddress(String address) { this.address = trim(address); }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    /**
     * 入力をチェックする。問題がなければ空のMapを返す。
     * 郵便番号は「870-0000」の形に整える。
     */
    public Map<String, String> validate() {
        Map<String, String> errors = new LinkedHashMap<>();

        if (name.isEmpty()) {
            errors.put("name", "お名前を入力してください。");
        }
        if (!EMAIL.matcher(email).matches()) {
            errors.put("email", "メールアドレスの形式で入力してください。");
        }
        Matcher m = ZIP.matcher(zip);
        if (m.matches()) {
            zip = m.group(1) + "-" + m.group(2);
        } else {
            errors.put("zip", "郵便番号は7桁の数字で入力してください（例: 870-0000）。");
        }
        if (address.isEmpty()) {
            errors.put("address", "住所を入力してください。");
        }
        return errors;
    }
}
