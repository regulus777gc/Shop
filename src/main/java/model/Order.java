package model;

import java.util.List;

/** Model: 注文。 */
public class Order {
    private final String orderNo;
    private final Customer customer;
    private final int subtotal;
    private final int shipping;
    private final int total;
    private final String createdAt;
    private final List<OrderItem> items;

    public Order(String orderNo, Customer customer, int subtotal, int shipping, int total,
                 String createdAt, List<OrderItem> items) {
        this.orderNo = orderNo;
        this.customer = customer;
        this.subtotal = subtotal;
        this.shipping = shipping;
        this.total = total;
        this.createdAt = createdAt;
        this.items = items;
    }

    public String getOrderNo() { return orderNo; }
    public Customer getCustomer() { return customer; }
    public int getSubtotal() { return subtotal; }
    public int getShipping() { return shipping; }
    public int getTotal() { return total; }
    public String getCreatedAt() { return createdAt; }
    public List<OrderItem> getItems() { return items; }
}
