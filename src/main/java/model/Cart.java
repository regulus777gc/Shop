package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Model: カート。HttpSession に保存して使う。
 * 小計・送料・合計の計算ルールはここに集めている（画面やサーブレットには書かない）。
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int MAX_QUANTITY = 99;
    public static final int FREE_SHIPPING_FROM = 5000; // 税込でこの金額以上なら送料無料
    public static final int SHIPPING_FEE = 550;

    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    /** 商品を1つ追加する。すでに入っていれば数量を1増やす。 */
    public void add(Product product) {
        CartItem item = items.get(product.getId());
        if (item == null) {
            items.put(product.getId(), new CartItem(product, 1));
        } else {
            item.setQuantity(clamp(item.getQuantity() + 1));
        }
    }

    /** 数量を変える。0以下なら削除する。 */
    public void setQuantity(int productId, int quantity) {
        CartItem item = items.get(productId);
        if (item == null) {
            return;
        }
        if (quantity <= 0) {
            items.remove(productId);
        } else {
            item.setQuantity(clamp(quantity));
        }
    }

    public void remove(int productId) {
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    private static int clamp(int quantity) {
        return Math.max(1, Math.min(quantity, MAX_QUANTITY));
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    /** 商品の個数の合計（ヘッダーのバッジに出す数）。 */
    public int getCount() {
        int count = 0;
        for (CartItem item : items.values()) {
            count += item.getQuantity();
        }
        return count;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getSubtotal() {
        int sum = 0;
        for (CartItem item : items.values()) {
            sum += item.getSubtotal();
        }
        return sum;
    }

    public int getShipping() {
        int subtotal = getSubtotal();
        return (subtotal == 0 || subtotal >= FREE_SHIPPING_FROM) ? 0 : SHIPPING_FEE;
    }

    public int getTotal() {
        return getSubtotal() + getShipping();
    }

    /** 送料無料まであといくらか。カートが空、または無料の対象なら 0。 */
    public int getRemainingForFree() {
        int subtotal = getSubtotal();
        return subtotal == 0 ? 0 : Math.max(0, FREE_SHIPPING_FROM - subtotal);
    }
}
