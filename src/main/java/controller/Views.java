package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Cart;

/** Controller 共通の小さな道具。 */
final class Views {

    private Views() {
    }

    /** /WEB-INF/views/<name>.jsp に処理を渡す。WEB-INF の中なので、URLで直接開かれることはない。 */
    static void forward(HttpServletRequest req, HttpServletResponse resp, String name)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + name + ".jsp").forward(req, resp);
    }

    static void redirect(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        resp.sendRedirect(req.getContextPath() + path);
    }

    /** 次に表示する画面で1回だけ出すメッセージ。 */
    static void flash(HttpServletRequest req, String message) {
        req.getSession().setAttribute("flash", message);
    }

    /** セッションのカートを返す。まだなければ作る。 */
    static Cart cartOf(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    static String param(HttpServletRequest req, String name) {
        String value = req.getParameter(name);
        return value == null ? "" : value.trim();
    }

    /** 整数に直す。数字でなければ null。 */
    static Integer intParam(HttpServletRequest req, String name) {
        try {
            return Integer.valueOf(param(req, name));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
