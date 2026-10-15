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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        Integer latestOrderId = (Integer) session.getAttribute("latestOrderId");
        
        if (latestOrderId == null) {
            // 注文情報がない（直接URLを叩かれた、またはセッションが切れた等）場合は商品一覧へリダイレクト
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }
        
        // URLパラメータ等で指定されたID、またはセッションの最新注文IDを使用する
        int orderId = latestOrderId;
        
        try {
            // データベース（DAO）から注文情報を取得する
            OrderDAO orderDAO = new OrderDAO();
            Order order = orderDAO.findById(orderId);
            
            if (order != null) {
                // リクエストスコープに注文情報をセットする
                request.setAttribute("order", order);
                
                // 一度表示したら再リロードや不正な再アクセスを防ぐため、セッションの注文IDをクリアする（お好みで実装）
                session.removeAttribute("latestOrderId");
                
                // 注文完了画面（View）へ転送する
                request.getRequestDispatcher("/WEB-INF/views/orders/complete.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "注文情報が見つかりませんでした。");
            }
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "システムエラーが発生しました。");
        }
    }
}