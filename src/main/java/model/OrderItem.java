package model;

/** Model: 注文の明細1行。注文した時点の商品名と価格を持つ。 */
public class OrderItem {
    private final String productName;
    private final int unitPrice;
    private final int quantity;

    public OrderItem(String productName, int unitPrice, int quantity) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getProductName() { return productName; }
    public int getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public int getSubtotal() { return unitPrice * quantity; }
}
