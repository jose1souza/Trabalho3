<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<title>Gestão de Posts</title>
			<!-- Bootstrap CSS -->
			<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
		</head>

		<body>
			<div class="container">
				<div class="row mt-3">
					<div class="col-md-2"></div>

					<div class="col-md-8">
						<h1>Cadastro de Post</h1>

						<form action="${pageContext.request.contextPath}/posts/save" method="GET" class="">

							<input type="hidden" id="post_id_id" name="post_id" value="${post.getId()}">

							<div class="mb-3">
								<label for="post_content_id" class="form-label"> Conteúdo
								</label> <input id="post_content_id" type="text" name="post_content"
									class="form-control" value="${post.getContent()}">
							</div>

							<div class="mb-3">
								<label for="post_date_id" class="form-label"> Data </label> <input id="post_date_id"
									type="date" name="post_date" class="form-control" value="${post.getPostDate()}">
							</div>

							<div class="mb-3">
								<label for="post_user_id" class="form-label">Escolha um
									Usuário:</label> <select name="post_user" id="post_user_id" class="form-control">
									<option value="">Selecione um usuário...</option>
									<c:forEach var="user" items="${usuarios}">
										<option value="${user.id}" <c:if
											test="${post != null && post.user != null && post.user.id == user.id}">
											selected</c:if>>
											${user.name}</option>
									</c:forEach>
								</select>
							</div>

							<button type="submit" class="btn btn-primary">Cadastrar</button>
						</form>
					</div>

					<div class="col-md-2"></div>
				</div>
			</div>
		</body>

		</html>