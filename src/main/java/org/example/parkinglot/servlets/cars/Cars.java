package org.example.parkinglot.servlets.cars;

import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.parkinglot.common.CarDto;
import org.example.parkinglot.ejb.CarsBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@DeclareRoles({"READ_CARS", "WRITE_CARS", "READ_USERS", "WRITE_USERS"})
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"READ_CARS"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_CARS"})}
)

@WebServlet(name = "Cars", value = "/Cars")
public class Cars extends HttpServlet {

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CarDto> cars = carsBean.findAllCars();
        request.setAttribute("cars", cars);
        int totalParkingSpots = 10;
        Long occupiedSpots = carsBean.getCarsCount();
        request.setAttribute("numberOfFreeParkingSpots", totalParkingSpots - occupiedSpots);        request.getRequestDispatcher("/WEB-INF/pages/cars/cars.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Luam ID-urile masinilor bifate (vin ca un sir de String-uri)
        String[] carIdsAsString = request.getParameterValues("car_ids");

        if (carIdsAsString != null) {
            // 2. Le convertim in numere (Long)
            List<Long> carIds = new ArrayList<>();
            for (String carIdAsString : carIdsAsString) {
                carIds.add(Long.parseLong(carIdAsString));
            }

            // 3. Stergem masinile folosind EJB-ul
            carsBean.deleteCarsByIds(carIds);
        }

        // 4. Reimprospatam pagina
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}