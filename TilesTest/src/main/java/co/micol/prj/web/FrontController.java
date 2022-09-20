package co.micol.prj.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.main;
import co.micol.prj.command.About;
import co.micol.prj.command.Admin;
import co.micol.prj.command.Contact;
import co.micol.prj.command.ShopSingle;
import co.micol.prj.command.shop;
import co.micol.prj.common.Command;


@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// map에 명령집단 담기 위해
	private HashMap<String, Command> map = new HashMap<String, Command>();
    
    public FrontController() {
        super();
    }
	
	public void init(ServletConfig config) throws ServletException {
		// 명령집단 저장하는 곳
		map.put("/main.do", new main());
		map.put("/about.do", new About());
		map.put("/shop.do", new shop());
		map.put("/shop-single.do", new ShopSingle());
		map.put("/contact.do", new Contact());
		map.put("/admin.do", new Admin());
	}


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 실제수행할 서비스
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String page = uri.substring(contextPath.length());
		
		Command command = map.get(page);
		String viewPage = command.exec(request, response);
		if(!viewPage.endsWith(".do")) {
			if(viewPage.startsWith("ajax:")) {
				// ajax 처리
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().append(viewPage.substring(5));
				return;
			} else { 
				if(viewPage.startsWith("no:")) //tiles안태울때
					viewPage = "/WEB-INF/views/" + viewPage.substring(3) + ".jsp"; 
				else {
					viewPage = viewPage + ".tiles"; //tiles layout 사용
					RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
					dispatcher.forward(request, response);
				}
			}
		} else { //.do일때
			response.sendRedirect(viewPage);
		}
	}
		

}
