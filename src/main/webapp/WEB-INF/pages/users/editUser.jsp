<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Edit User">
  <h1>Edit User</h1>
  <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/EditUser">

    <div class="mb-3">
      <label>Username (cannot be changed)</label>
      <input type="text" class="form-control" value="${user.username}" disabled>
    </div>

    <div class="mb-3">
      <label for="email">Email</label>
      <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
    </div>

    <div class="mb-3">
      <label for="password">Password (leave empty to keep current)</label>
      <input type="password" class="form-control" id="password" name="password">
    </div>

    <input type="hidden" name="user_id" value="${user.id}" />
    <button class="btn btn-primary btn-lg" type="submit">Save Changes</button>
  </form>
</t:pageTemplate>