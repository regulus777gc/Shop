package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.Product;

@WebServlet("/products") 
public class ProductListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    private final ProductDAO productDAO = new ProductDAO();

    public ProductListServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String category = Views.param(request, "category");
        String q = Views.param(request, "q");
        String sort = Views.param(request, "sort");
        
        List<Product> products = productDAO.search(category, q, sort);

        request.setAttribute("products", products);
        request.setAttribute("categories", Product.CATEGORIES);
        request.setAttribute("category", category);
        request.setAttribute("q", q);
        
        String sortValue = (sort == null || sort.isEmpty()) ? "def" : sort;
        request.setAttribute("sort", sortValue);
        
        // Viewへ転送
        Views.forward(request, response, "products/list");
        
    }
}