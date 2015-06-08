package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Controller
 */
@MultipartConfig
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPetition(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPetition(request, response);
	}
	
	private void doPetition(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session = request.getSession();
		if(session.isNew())
			session.setMaxInactiveInterval(30*60);
		
		//session.setAttribute("admin_logged", true);
		
		ControllerParams controllerParams = ControllerParams.FromRequest(request);
		controllerParams.set("session", request.getSession());
		controllerParams.set("ip", request.getRemoteAddr());
		ControllerResponse controllerResponse = ApplicationController.getInstance()
				.execute(request.getParameter("action"), controllerParams);
		
		try {
			response.getOutputStream().println(controllerResponse.toString());
		} catch (IOException e) {}
	}

}
