package composition;

import inheritance.entities.Car;
import jakarta.persistence.*;

@Entity
@Table(name = "plate_numbers")
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @OneToOne(targetEntity = Car.class, mappedBy = "plateNumber")
    private Car car;

    public PlateNumber() {
    }

    public PlateNumber(String number) {
        this.number = number;
    }

}
