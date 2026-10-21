package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.User;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;
import model.dao.UserDAO;

@WebServlet(urlPatterns = { "/companies", "/companies/delete", "/companies/save", "/companies/update" })
public class CompaniesController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String contextPath = req.getContextPath();
		String action = req.getRequestURI().substring(contextPath.length());
		System.out.println("URL: " + action);

		switch (action) {
		case "/companies": {
			loadCompanies(req);

			RequestDispatcher rd = req.getRequestDispatcher("/companies.jsp");
			rd.forward(req, resp);
			break;
		}
		case "/companies/save": {
			loadUsers(req);
			saveOrUpdateCompany(req);

			resp.sendRedirect(contextPath + "/companies");
			break;
		}
		case "/companies/update": {
			loadUsers(req);
			if (req.getParameter("companyId") != null) {
				loadCompany(req);
			}

			RequestDispatcher rd = req.getRequestDispatcher("/companies_form.jsp");
			rd.forward(req, resp);
			break;
		}
		case "/companies/delete": {
			deleteCompany(req);
			resp.sendRedirect(contextPath + "/companies");
			break;
		}
		default:
			String message = String.format("URL não suportada: '%s'.", action);

			System.out.println(message);
			throw new IllegalArgumentException(message);
		}
	}

	private void deleteCompany(HttpServletRequest req) {
		String companyIdStr = req.getParameter("companyId");

		int companyId = Integer.parseInt(companyIdStr);
		Company companyToBeDeleted = new Company(companyId);

		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		try {
			dao.delete(companyToBeDeleted);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void loadCompany(HttpServletRequest req) {
		String companyIdStr = req.getParameter("companyId");
		int companyId = Integer.parseInt(companyIdStr);

		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);

		Company companyToBeUpdated = null;
		try {
			companyToBeUpdated = dao.findById(companyId);
		} catch (ModelException e) {
			e.printStackTrace();
		}

		req.setAttribute("company", companyToBeUpdated);
	}

	private void loadCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);

		List<Company> companies = List.of();
		try {
			companies = dao.listAll();
		} catch (ModelException me) {
			me.printStackTrace();
		}

		req.setAttribute("companies_list", companies);
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

	private void saveOrUpdateCompany(HttpServletRequest req) {
		String companyIdStr = req.getParameter("company_id");
		boolean isUpdate = false;

		Company company = null;
		if ((companyIdStr == null) || companyIdStr.isBlank()) {
			company = new Company();
		} else {
			company = new Company(Integer.parseInt(companyIdStr));
			isUpdate = true;
		}

		String name = req.getParameter("company_name");
		String role = req.getParameter("company_role");
		String startStr = req.getParameter("company_start");
		String endStr = req.getParameter("company_end");
		String userIdStr = req.getParameter("company_user");

		company.setName(name);
		company.setRole(role);

		if (startStr != null && !startStr.isBlank()) {
			java.time.LocalDate localDate = java.time.LocalDate.parse(startStr);
			java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
			company.setStart(sqlDate);
		}

		if (endStr != null && !endStr.isBlank()) {
			java.time.LocalDate localDate = java.time.LocalDate.parse(endStr);
			java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
			company.setEnd(sqlDate);
		} else {
			company.setEnd(null);
		}

		if (userIdStr != null && !userIdStr.isBlank()) {
			int userId = Integer.parseInt(userIdStr);
			User user = new User(userId);
			company.setUser(user);
		}

		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);

		try {
			if (isUpdate)
				dao.update(company);
			else
				dao.save(company);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}
}
