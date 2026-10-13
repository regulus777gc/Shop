package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // DAOから商品一覧を取得する
        ProductDao productDao = new ProductDao();
        List<Product> productList = productDao.findAll();
        
        // リクエストスコープに結果をセットする
        request.setAttribute("products", productList);
        
        // 3. View (JSP) へ転送する
        request.getRequestDispatcher("/WEB-INF/views/products/list.jsp").forward(request, response);
    }
}