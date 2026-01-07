<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>

    <form method="POST" action="${pageContext.request.contextPath}/Users">
        <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
            <a href="${pageContext.request.contextPath}/AddUser" class="btn btn-primary btn-lg">Add User</a>
        </c:if>

        <button class="btn btn-secondary btn-lg" type="submit">Invoice</button>

        <div class="container text-center mt-3">
            <div class="row border-bottom font-weight-bold py-2">
                <div class="col-1">Select</div>
                <div class="col">Username</div>
                <div class="col">Email</div>
            </div>

            <c:forEach var="user" items="${users}">
                <div class="row border-bottom py-2">
                    <div class="col-1">
                        <input type="checkbox" name="user_ids" value="${user.id}" />
                    </div>
                    <div class="col">
                            ${user.username}
                    </div>
                    <div class="col">
                            ${user.email}
                    </div>
                </div>
            </c:forEach>
        </div>
    </form>

    <c:if test="${not empty invoices}">
        <h2 class="mt-5">Invoices</h2>
        <c:forEach var="username" items="${invoices}" varStatus="status">
            ${status.index + 1}. ${username} <br/>
        </c:forEach>
    </c:if>
</t:pageTemplate>