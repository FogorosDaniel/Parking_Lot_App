package org.example.parkinglot.servlets.users;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.parkinglot.common.UserDto;
import org.example.parkinglot.ejb.InvoiceBean;
import org.example.parkinglot.ejb.UsersBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"READ_USERS"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_USERS"})})
@WebServlet(name = "Users", value = "/Users")
public class Users extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Inject
    InvoiceBean invoiceBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);

        // DEBUG: Vedem cate ID-uri sunt in memorie
        System.out.println("ID-uri in memorie: " + invoiceBean.getUserIds().size());

        if (!invoiceBean.getUserIds().isEmpty()) {
            Collection<String> usernames = usersBean.findUsernamesByUserIds(invoiceBean.getUserIds());
            request.setAttribute("invoices", usernames);

            // DEBUG: Vedem daca a gasit nume
            System.out.println("Am trimis catre JSP lista cu marime: " + usernames.size());
        }

        request.getRequestDispatcher("/WEB-INF/pages/users/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Citim ce ai bifat
        String[] userIdsAsString = request.getParameterValues("user_ids");

        if (userIdsAsString != null) {
            List<Long> userIds = new ArrayList<>();
            for (String userIdAsString : userIdsAsString) {
                userIds.add(Long.parseLong(userIdAsString));
            }
            // Salvam in Bean
            invoiceBean.getUserIds().addAll(userIds);
        }

        response.sendRedirect(request.getContextPath() + "/Users");
    }
}