package inheritance.entities;

import jakarta.persistence.*;

@MappedSuperclass
public class IdType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Third Strategy - "Join" strategy
//  @GeneratedValue(strategy = GenerationType.IDENTITY) //Second Strategy - a table per concrete entity class
//  @GeneratedValue(strategy = GenerationType.TABLE)    //First Strategy - a single table per class
    protected long id;
    @Basic
    protected String type;
}
