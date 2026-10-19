package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {"/posts", "/posts/delete", "/posts/save"})
public class PostsController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		//TODO: Log - apagar ao final
		String contextPath = req.getContextPath();
		String action = req.getRequestURI().substring(contextPath.length());
		System.out.println("URL: " + action);
		
		switch (action) {
		case "/posts": {
			loadPosts(req);
			
			RequestDispatcher rd = req.getRequestDispatcher("/posts.jsp");
			rd.forward(req, resp);
			break;
		}
		case "/posts/save": {
			loadUsers(req);
			RequestDispatcher rd = req.getRequestDispatcher("/posts_form.jsp");
			rd.forward(req, resp);
			break;
		}
		case "/posts/delete": {
			deletePost(req);
			resp.sendRedirect(contextPath + "/posts");
			break;
		}
		default:
			String message = String.format("URL não suportada: '%s'.", action);
			
			System.out.println(message);
			throw new IllegalArgumentException(message);
		}
	}
	
	private void deletePost(HttpServletRequest req) {
		String postIdStr = req.getParameter("postId");

		int postId = Integer.parseInt(postIdStr);
		Post postToBeDeleted = new Post(postId);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		try {
			dao.delete(postToBeDeleted);
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
	}
	
	private void loadPosts(HttpServletRequest req) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		
		List<Post> posts = List.of();
		try {
			posts = dao.listAll();
		} catch (ModelException me) {
			// TODO Log no servidor
			me.printStackTrace();
		}
		
		req.setAttribute("posts_list", posts);
	}
	
	private void loadUsers(HttpServletRequest req) {
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		List<User> users = List.of();
		
		try {
			users = dao.listAll();
		} catch (ModelException me) {
			me.printStackTrace();
		}
		
		req.setAttribute("usuarios", users);
	}
}
