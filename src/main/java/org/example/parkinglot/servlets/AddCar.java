package org.example.parkinglot.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.parkinglot.common.UserDto;
import org.example.parkinglot.ejb.CarsBean;
import org.example.parkinglot.ejb.UsersBean;

import java.io.IOException;
import java.util.List;

@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"}))
@WebServlet(name = "AddCar", value = "/AddCar")
public class AddCar extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/addCar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String licensePlate = request.getParameter("license_plate");
            String parkingSpot = request.getParameter("parking_spot");
            String ownerIdString = request.getParameter("owner_id");

            if (ownerIdString == null || ownerIdString.isEmpty()) {
                throw new Exception("Nu ai selectat niciun proprietar!");
            }

            Long userId = Long.parseLong(ownerIdString);

            carsBean.createCar(licensePlate, parkingSpot, userId);

            response.sendRedirect(request.getContextPath() + "/Cars");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Eroare la adaugare: " + e.getMessage());
        }
    }
}