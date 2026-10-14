package model;

import java.io.Serializable;

/** Model: カートの1行（商品と数量）。 */
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    void setQuantity(int quantity) { this.quantity = quantity; }

    public int getSubtotal() {
        return product.getPrice() * quantity;
    }
}
