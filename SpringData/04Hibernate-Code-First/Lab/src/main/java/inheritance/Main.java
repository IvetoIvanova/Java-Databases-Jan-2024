package inheritance;

import composition.PlateNumber;
import inheritance.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory mainFactory = Persistence.createEntityManagerFactory("main");
        EntityManager entityManager = mainFactory.createEntityManager();

        entityManager.getTransaction().begin();

        PlateNumber plateNum = new PlateNumber("CA1234BH");

        Vehicle car = new Car("VW", BigDecimal.TEN, "Diesel", 5, plateNum);
        Vehicle bike = new Bike("Yamaha", BigDecimal.TWO, "None");
        Vehicle plane = new Plane("Boeing", BigDecimal.TEN, "AviationTurbineFuel", 100);
        Vehicle truck = new Truck("Scania", BigDecimal.ONE, "Methane", 9000);

        entityManager.persist(plateNum);
        entityManager.persist(car);
        entityManager.persist(bike);
        entityManager.persist(plane);
        entityManager.persist(truck);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
