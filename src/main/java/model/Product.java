package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Model: 商品。 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final List<String> CATEGORIES = List.of("豆", "器具", "カップ", "消耗品");

    /** 詳細ページのスペック表の1行（ラベルと値）。 */
    public static class Spec implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String label;
        private final String value;

        public Spec(String label, String value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() { return label; }
        public String getValue() { return value; }
    }

    private final int id;
    private final String name;
    private final String category;
    private final String kind;   // アイコンの種類（beans / dripper / kettle / mug / filter / scale）
    private final int tone;      // 背景色の番号（1〜4）
    private final int price;     // 税込価格（円）
    private final String spec;
    private final String description;
    private final List<Spec> specs;

    public Product(int id, String name, String category, String kind, int tone, int price,
                   String spec, String description, List<Spec> specs) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.kind = kind;
        this.tone = tone;
        this.price = price;
        this.spec = spec;
        this.description = description;
        this.specs = new ArrayList<>(specs);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getKind() { return kind; }
    public int getTone() { return tone; }
    public int getPrice() { return price; }
    public String getSpec() { return spec; }
    public String getDescription() { return description; }
    public List<Spec> getSpecs() { return specs; }
}
