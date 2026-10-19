package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ProductDAO;          // ← ① 追加
import model.Cart;
import model.Product;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductDAO productDAO = new ProductDAO();   // ← ② 追加

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Cart cart = Views.cartOf(req);
        Integer productId = Views.intParam(req, "productId");
        if (productId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch (Views.param(req, "action")) {
            case "add": {
                Product product = productDAO.findById(productId);
                if (product == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                cart.add(product);
                Views.flash(req, "カートに入れました。");
                break;
            }
            case "update": {
                Integer quantity = Views.intParam(req, "quantity");
                if (quantity == null) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                cart.setQuantity(productId, quantity);
                break;
            }
            case "remove":
                cart.remove(productId);
                Views.flash(req, "カートから削除しました。");
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }
        Views.redirect(req, resp, "/cart");
    }
}