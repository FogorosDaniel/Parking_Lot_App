package org.example.parkinglot.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.parkinglot.common.UserDto;
import org.example.parkinglot.ejb.CarsBean;
import org.example.parkinglot.ejb.UsersBean;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddCar", value = "/AddCar")
public class AddCar extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Inject
    CarsBean carsBean; // <--- ACEASTA ESTE LINIA CARE LIPSEA!

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/addCar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. Preluam datele
            String licensePlate = request.getParameter("license_plate");
            String parkingSpot = request.getParameter("parking_spot");
            String ownerIdString = request.getParameter("owner_id");

            // VERIFICARE: Daca nu s-a ales niciun owner, dam eroare manuala
            if (ownerIdString == null || ownerIdString.isEmpty()) {
                throw new Exception("Nu ai selectat niciun proprietar (Owner ID este gol)!");
            }

            Long userId = Long.parseLong(ownerIdString);

            // 2. Apelam EJB-ul
            carsBean.createCar(licensePlate, parkingSpot, userId);

            // 3. Redirect
            response.sendRedirect(request.getContextPath() + "/Cars");

        } catch (Exception e) {
            // Afisam eroarea pe ecran daca ceva merge prost
            response.setContentType("text/html");
            response.getWriter().println("<h1>A aparut o eroare!</h1>");
            response.getWriter().println("<p>Mesaj eroare: " + e.getMessage() + "</p>");
            response.getWriter().println("<pre>");
            e.printStackTrace(response.getWriter());
            response.getWriter().println("</pre>");
        }
    }
}