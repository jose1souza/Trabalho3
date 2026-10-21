<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<title>Gestão de Empresas</title>
			<!-- Bootstrap CSS -->
			<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
		</head>

		<body>
			<div class="container">
				<div class="row mt-3">
					<div class="col-md-2"></div>

					<div class="col-md-8">
						<c:if test="${empty company.getId()}">
							<h1>Cadastro de Empresa</h1>
						</c:if>
						<c:if test="${not empty company.getId()}">
							<h1>Edição de Empresa</h1>
						</c:if>

						<form action="${pageContext.request.contextPath}/companies/save" method="GET" class="">

							<input type="hidden" id="company_id_id" name="company_id" value="${company.getId()}">

							<div class="mb-3">
								<label for="company_name_id" class="form-label"> Nome </label>
								<input id="company_name_id" type="text" name="company_name" class="form-control"
									value="${company.getName()}" required>
							</div>

							<div class="mb-3">
								<label for="company_role_id" class="form-label"> Cargo </label>
								<input id="company_role_id" type="text" name="company_role" class="form-control"
									value="${company.getRole()}" required>
							</div>

							<div class="mb-3">
								<label for="company_start_id" class="form-label"> Data Início </label>
								<input id="company_start_id" type="date" name="company_start" class="form-control"
									value="${company.getStart()}" required>
							</div>

							<div class="mb-3">
								<label for="company_end_id" class="form-label"> Data Saída </label>
								<input id="company_end_id" type="date" name="company_end" class="form-control"
									value="${company.getEnd()}">
							</div>

							<div class="mb-3">
								<label for="company_user_id" class="form-label">Escolha um Usuário
									(Funcionário):</label>
								<select name="company_user" id="company_user_id" class="form-control" required>
									<option value="">Selecione um usuário...</option>
									<c:forEach var="user" items="${usuarios}">
										<option value="${user.id}" <c:if
											test="${company != null && company.user != null && company.user.id == user.id}">
											selected</c:if>>
											${user.name}
										</option>
									</c:forEach>
								</select>
							</div>

							<button type="submit" class="btn btn-primary">
								<c:if test="${empty company.getId()}">Cadastrar</c:if>
								<c:if test="${not empty company.getId()}">Atualizar</c:if>
							</button>
						</form>
					</div>

					<div class="col-md-2"></div>
				</div>
			</div>
		</body>

		</html>