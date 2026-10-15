package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.OrderDAO;
import model.Order;

@WebServlet("/order/complete")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // CheckoutServlet が保存した注文番号
        String latestOrderNo = (String) session.getAttribute("latestOrderNo");

        if (latestOrderNo == null) {
            // 注文情報がない場合は商品一覧へリダイレクト
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        Order order = orderDAO.findByOrderNo(latestOrderNo);

        if (order == null) {
            // 見つからない、またはDBエラー
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "注文情報が見つかりませんでした。");
            return;
        }

        request.setAttribute("order", order);

        // 一度だけ表示したい場合は、次の1行のコメントを外す
        // session.removeAttribute("latestOrderNo");

        request.getRequestDispatcher("/WEB-INF/views/orders/complete.jsp").forward(request, response);
    }
}