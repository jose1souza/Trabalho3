<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>Empresas - Facebook CRUD</title>
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
	</head>

	<body>

		<div class="container">
			<div class="row mt-3">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<a class="btn btn-secondary" href="${pageContext.request.contextPath}/"> Home </a>
				</div>
				<div class="col-md-2"></div>
			</div>
			<div class="row mt-3">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<h2>Empresas</h2>
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">Id</th>
								<th scope="col">Nome</th>
								<th scope="col">Cargo</th>
								<th scope="col">Início</th>
								<th scope="col">Saída</th>
								<th scope="col">Funcionário</th>
								<th scope="col">Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="company" items="${companies_list}">
								<tr>
									<th scope="row">${company.getId()}</th>
									<td>${company.getName()}</td>
									<td>${company.getRole()}</td>
									<td>${company.getStart()}</td>
									<td>${company.getEnd()}</td>
									<td>${company.getUser().getName()}</td>
									<td>
										<a href="${pageContext.request.contextPath}/companies/update?companyId=${company.getId()}"
											class="bi bi-pencil-square"></a>
										<a href="${pageContext.request.contextPath}/companies/delete?companyId=${company.getId()}"
											class="bi bi-trash"></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<a href="${pageContext.request.contextPath}/companies/update" class="btn btn-primary"> Cadastrar
						Empresas </a>
				</div>
				<div class="col-md-2"></div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>

	</html>