package filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 全リクエスト共通の前処理。
 * 1. 文字コードを UTF-8 にする（日本語のフォーム入力が文字化けしないように、パラメータを読む前に必ず行う）
 * 2. JSP から使う ${ctx}（コンテキストパス）を用意する
 * 3. CSRF 対策: セッションにトークンを持たせ、POST ではフォームのトークンと一致しなければ拒否する
 */
@WebFilter("/*")
public class CommonFilter implements Filter {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("ctx", req.getContextPath());

        // CSS などの静的ファイルにはセッションもトークンも要らない
        if (req.getRequestURI().startsWith(req.getContextPath() + "/static/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(true);
        String token = (String) session.getAttribute("csrf");
        if (token == null) {
            token = newToken();
            session.setAttribute("csrf", token);
        }

        if ("POST".equalsIgnoreCase(req.getMethod())) {
            String sent = req.getParameter("_csrf");
            if (sent == null || !MessageDigest.isEqual(
                    sent.getBytes(StandardCharsets.UTF_8), token.getBytes(StandardCharsets.UTF_8))) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "不正なリクエストです");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private static String newToken() {
        byte[] bytes = new byte[16];
        RANDOM.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
