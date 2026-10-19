package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /*セッションにログイン中のユーザーを入れるときのキー。 */
    public static final String SESSION_KEY = "loginUser";

    /*長すぎるパスワードでハッシュ計算を重くされるのを防ぐ。 */
    private static final int MAX_PASSWORD_LENGTH = 128;

    private static final String DEFAULT_NEXT = "/products";

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (isLoggedIn(req)) {
            Views.redirect(req, resp, DEFAULT_NEXT);
            return;
        }
        showForm(req, resp, "", safeNext(Views.param(req, "next")), null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = Views.param(req, "email");
        String password = req.getParameter("password");
        if (password == null) {
            password = "";
        }
        String next = safeNext(Views.param(req, "next"));

        if (email.isEmpty() || password.isEmpty()) {
            showForm(req, resp, email, next, "メールアドレスとパスワードを入力してください。");
            return;
        }

        User user = (password.length() <= MAX_PASSWORD_LENGTH) ? userDAO.authenticate(email, password) : null;
        if (user == null) {
            showForm(req, resp, email, next, "メールアドレスまたはパスワードが正しくありません。");
            return;
        }

        // ログイン成功時にセッションIDを変える
        req.changeSessionId();
        HttpSession session = req.getSession();
        // CSRFトークンも作り直す（CommonFilter が次のリクエストで新しく発行する）
        session.removeAttribute("csrf");
        session.setAttribute(SESSION_KEY, user);

        Views.redirect(req, resp, next);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp,
                          String email, String next, String error)
            throws ServletException, IOException {
        req.setAttribute("email", email);
        req.setAttribute("next", next);
        req.setAttribute("error", error);
        Views.forward(req, resp, "login");
    }

    private static boolean isLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute(SESSION_KEY) != null;
    }

    static String safeNext(String next) {
        if (next == null || next.isEmpty() || !next.startsWith("/") || next.startsWith("//")) {
            return DEFAULT_NEXT;
        }
        for (int i = 0; i < next.length(); i++) {
            char c = next.charAt(i);
            if (c < 0x20 || c == '\\' || c == 0x7f) {
                return DEFAULT_NEXT;
            }
        }
        return next;
    }
}