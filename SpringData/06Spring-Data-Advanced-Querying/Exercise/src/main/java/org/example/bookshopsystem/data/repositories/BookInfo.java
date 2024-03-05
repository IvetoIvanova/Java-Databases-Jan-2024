package org.example.bookshopsystem.data.repositories;

import org.example.bookshopsystem.data.entities.enums.AgeRestriction;
import org.example.bookshopsystem.data.entities.enums.EditionType;

import java.math.BigDecimal;

public interface BookInfo {

    String getTitle();

    EditionType getEditionType();

    AgeRestriction getAgeRestriction();

    BigDecimal getPrice();
}
