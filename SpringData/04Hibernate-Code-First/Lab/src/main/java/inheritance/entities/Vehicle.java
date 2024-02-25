package inheritance.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)    //Third Strategy - "Join" strategy
//@Inheritance(strategy = InheritanceType.JOINED)   //Second Strategy - a table per concrete entity class
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  //First Strategy - a single table per class
public abstract class Vehicle extends IdType{
    @Basic
    private String model;
    @Basic
    private BigDecimal price;
    @Column(name = "fuel_type")
    private String fuelType;

    protected Vehicle() {
    }

    protected Vehicle(String type) {
        super.type = type;
    }

    protected Vehicle(String type, String model, BigDecimal price, String fuelType) {
        this.type = type;
        this.model = model;
        this.price = price;
        this.fuelType = fuelType;
    }
}
