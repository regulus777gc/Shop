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

/**
 * Servlet implementation class ProductListServlet
 */
@WebServlet("/ProductListServlet")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final ProductDAO productDao = new ProductDAO();
    public ProductListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String category = Views.param(request,"category");
		String q = Views.param(request,"q");
		String sort = Views.param(request, "sort");
		
	      List<Product> products = productDao.search(category, q, sort);

	        request.setAttribute("products", products);
	        request.setAttribute("categories", Product.CATEGORIES);
	        request.setAttribute("category", category);
	        request.setAttribute("q", q);
	        request.setAttribute("sort", sort.isEmpty() ? "def" : sort);
	        Views.forward(request, response, "products/list");
	    
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}
}
