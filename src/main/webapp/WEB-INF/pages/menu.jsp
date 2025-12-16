<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Parking Lot</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/About">About</a>
                    </li>

                    <c:if test="${pageContext.request.isUserInRole('READ_CARS')}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Cars">Cars</a>
                        </li>
                    </c:if>

                    <c:if test="${pageContext.request.isUserInRole('READ_USERS')}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Users">Users</a>
                        </li>
                    </c:if>
                </ul>

                <div class="d-flex text-white">
                    <c:choose>
                        <%-- Daca NU este logat userul, afisam butonul de Login --%>
                        <c:when test="${pageContext.request.getRemoteUser() == null}">
                            <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/Login">Login</a>
                        </c:when>
                        <%-- Daca ESTE logat, afisam numele si butonul de Logout --%>
                        <c:otherwise>
                            <span class="me-3 my-auto">Hello, ${pageContext.request.getRemoteUser()}!</span>
                            <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/Logout">Logout</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </nav>
</header>