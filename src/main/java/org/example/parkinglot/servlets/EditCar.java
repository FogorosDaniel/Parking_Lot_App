package org.example.parkinglot.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.parkinglot.common.CarDto;
import org.example.parkinglot.common.UserDto;
import org.example.parkinglot.ejb.CarsBean;
import org.example.parkinglot.ejb.UsersBean;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditCar", value = "/EditCar")
public class EditCar extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Luam lista de useri (pentru dropdown)
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);

        // 2. Luam ID-ul masinii din URL (ex: EditCar?id=5)
        Long carId = Long.parseLong(request.getParameter("id"));

        // 3. Cautam datele masinii ca sa le punem in formular
        CarDto car = carsBean.findById(carId);
        request.setAttribute("car", car);

        request.getRequestDispatcher("/WEB-INF/pages/editCar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. Luam datele din formular
            String licensePlate = request.getParameter("license_plate");
            String parkingSpot = request.getParameter("parking_spot");
            Long userId = Long.parseLong(request.getParameter("owner_id"));
            Long carId = Long.parseLong(request.getParameter("car_id")); // ID-ul ascuns

            // 2. Actualizam masina
            carsBean.updateCar(carId, licensePlate, parkingSpot, userId);

            // 3. Redirect la lista
            response.sendRedirect(request.getContextPath() + "/Cars");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Eroare la editare: " + e.getMessage());
        }
    }
}