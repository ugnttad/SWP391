/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import dal.ProductDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Products;

/**
 *
 * @author hotaru
 */
public class index extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int product = new ProductDAO().TotalProduct();
        String price = new ProductDAO().TotalOrderDetail();
        int report = new ProductDAO().CountReport();
        int order = new ProductDAO().CountOrder();
        List<Products> listTop5 = new ProductDAO().listTop5();
        
        request.setAttribute("product", product);
        request.setAttribute("price", price);
        request.setAttribute("report", report);
        request.setAttribute("order", order);
        request.setAttribute("listTop10", listTop5);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Logout admin
        Cookie loginCookie = null;
        //Lấy cookies cho account muốn logout
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("emailCookie")) {
                    loginCookie = cookie;
                    break;
                }
            }
        }

        //Nếu tồn tại cookie đó thì destroy nó
        if (loginCookie != null) {
            loginCookie.setMaxAge(0);
            response.addCookie(loginCookie);
        }

        //Lấy session hiện tại, (false) có ý nghĩa là nếu lấy session ra null thì ko tạo lại sesion
        HttpSession session = request.getSession(false);
        //vô hiệu hóa session hiện tại
        if (session != null) {
            session.invalidate();
        }
        
        String encodedURL = response.encodeRedirectURL(request.getContextPath() + "/auth.jsp");
        response.sendRedirect(encodedURL);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
