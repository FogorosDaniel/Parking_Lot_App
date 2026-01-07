package org.example.parkinglot.ejb;

import org.example.parkinglot.common.CarDto;
import org.example.parkinglot.common.CarPhotoDto;
import org.example.parkinglot.entities.Car;
import org.example.parkinglot.entities.CarPhoto;
import org.example.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> dto = new ArrayList<>();
        for (Car car : cars) {
            dto.add(new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername()));
        }
        return dto;
    }

    // 1. Metoda pentru ADAUGAREA unei masini (Add Car)
    public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOG.info("createCar");

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userId);
        car.setOwner(user);

        entityManager.persist(car);
    }

    // 2. Metoda pentru GASIREA unei masini dupa ID (necesar pentru Edit Car)
    public CarDto findById(Long carId) {
        Car car = entityManager.find(Car.class, carId);
        return new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername());
    }

    // 3. Metoda pentru ACTUALIZAREA unei masini (Edit Car)
    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId) {
        LOG.info("updateCar");

        Car car = entityManager.find(Car.class, carId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        // Schimbam proprietarul
        User user = entityManager.find(User.class, userId);
        car.setOwner(user);
    }

    // 4. Metoda pentru STERGEREA masinilor (Delete Car)
    public void deleteCarsByIds(Collection<Long> carIds) {
        LOG.info("deleteCarsByIds");
        for (Long carId : carIds) {
            Car car = entityManager.find(Car.class, carId);
            entityManager.remove(car);
        }
    }

    // Metoda pentru a adauga poza
    public void addPhotoToCar(Long carId, String filename, String fileType, byte[] fileContent) {
        LOG.info("addPhotoToCar");
        CarPhoto photo = new CarPhoto();
        photo.setFilename(filename);
        photo.setFileType(fileType);
        photo.setFileContent(fileContent);

        Car car = entityManager.find(Car.class, carId);
        if (car.getPhoto() != null) {
            entityManager.remove(car.getPhoto());
        }
        car.setPhoto(photo);
        photo.setCar(car);
        entityManager.persist(photo);
    }

    // Metoda pentru a gasi poza
    public CarPhotoDto findPhotoByCarId(Long carId) {
        List<CarPhoto> photos = entityManager.createQuery("SELECT p FROM CarPhoto p where p.car.id = :id", CarPhoto.class)
                .setParameter("id", carId)
                .getResultList();
        if (photos.isEmpty()) {
            return null;
        }
        CarPhoto photo = photos.get(0);
        return new CarPhotoDto(photo.getId(), photo.getFilename(), photo.getFileType(), photo.getFileContent());
    }

    public Long getCarsCount() {
        LOG.info("getCarsCount");
        try {
            return entityManager.createQuery("SELECT COUNT(c) FROM Car c", Long.class).getSingleResult();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

}