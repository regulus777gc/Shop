package controller;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.OrderDAO;
import model.Cart;
import model.CartItem;
import model.Customer;
import model.Order;
import model.OrderItem;


@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter ORDER_NO_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final SecureRandom RANDOM = new SecureRandom();

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Cart cart = Views.cartOf(req);
        if (cart.isEmpty()) {
            Views.flash(req, "カートが空です。");
            Views.redirect(req, resp, "/cart");
            return;
        }
        req.setAttribute("cart", cart);
        req.setAttribute("customer", new Customer());
        Views.forward(req, resp, "orders/new");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Cart cart = Views.cartOf(req);
        if (cart.isEmpty()) {
            Views.flash(req, "カートが空です。");
            Views.redirect(req, resp, "/cart");
            return;
        }

        Customer customer = new Customer();
        customer.setName(Views.param(req, "name"));
        customer.setEmail(Views.param(req, "email"));
        customer.setZip(Views.param(req, "zip"));
        customer.setAddress(Views.param(req, "address"));

        Map<String, String> errors = customer.validate();
        if (!errors.isEmpty()) {
            showForm(req, resp, cart, customer, errors, null);
            return;
        }

        // 注文明細は「注文した時点の商品名と価格」をコピーして持つ
        List<OrderItem> items = new ArrayList<>();
        for (CartItem ci : cart.getItems()) {
            items.add(new OrderItem(ci.getProduct().getName(), ci.getProduct().getPrice(), ci.getQuantity()));
        }

        String orderNo = newOrderNo();
        // createdAt は DB 側で入るので、保存時は null で良い
        Order order = new Order(orderNo, customer,
                cart.getSubtotal(), cart.getShipping(), cart.getTotal(), null, items);

        if (!orderDAO.create(order)) {
            showForm(req, resp, cart, customer, errors,
                    "注文の保存に失敗しました。時間をおいてもう一度お試しください。");
            return;
        }

        cart.clear();
        req.getSession().setAttribute("latestOrderNo", orderNo);
        Views.redirect(req, resp, "/order/complete");
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Cart cart,
                          Customer customer, Map<String, String> errors, String message)
            throws ServletException, IOException {
        req.setAttribute("cart", cart);
        req.setAttribute("customer", customer);
        req.setAttribute("errors", errors);
        req.setAttribute("message", message);
        Views.forward(req, resp, "orders/new");
    }

    private static String newOrderNo() {
        return LocalDateTime.now().format(ORDER_NO_TIME) + "-" + String.format("%04d", RANDOM.nextInt(10000));
    }
}