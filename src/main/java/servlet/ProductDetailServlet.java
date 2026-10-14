package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product")
public class ProductDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // リクエストパラメータから商品のIDを取得する
        String idStr = request.getParameter("id");
        
        try {
            int id = Integer.parseInt(idStr);
            
            // DAOから指定されたIDの商品情報を取得する
            ProductDao productDao = new ProductDao();
            Product product = productDao.findById(id);
            
            if (product != null) {
                // 商品をリクエストスコープにセット
                request.setAttribute("product", product);
                request.getRequestDispatcher("/WEB-INF/views/products/detail.jsp").forward(request, response);
            } else {
                // 商品が見つからない場合エラーを出力
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "商品が見つかりませんでした。");
            }
            
        } catch (NumberFormatException e) {
            // IDが不正な場合
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効な商品IDです。");
        }
    }
}