package ru.job4j.passport.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"series", "number"})})
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0, message = "Id must not be negative")
    private int id;
    @Min(value = 0, message = "Passport series should not be less than 0")
    @Max(value = 9999, message = "Passport series should not be greater than 9999")
    @NotNull(message = "Passport series cannot be null")
    private int series;
    @Min(value = 0, message = "Passport number should not be less than 0")
    @Max(value = 999999, message = "Passport number should not be greater than 999999")
    @NotNull(message = "Passport number cannot be null")
    private int number;
    @NotBlank(message = "Name must be not empty")
    private String name;
    @NotBlank(message = "Surname must be not empty")
    private String surName;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull(message = "Passport expire date cannot be null")
    private Calendar expires;

    public static Passport of(int series, int number, String name,
                              String surName, Calendar expires) {
        Passport newPassport = new Passport();
        newPassport.series = series;
        newPassport.number = number;
        newPassport.name = name;
        newPassport.surName = surName;
        newPassport.expires = expires;
        return newPassport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Calendar getExpires() {
        return expires;
    }

    public void setExpires(Calendar expires) {
        this.expires = expires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passport passport = (Passport) o;
        return id == passport.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Passport { " + "id=" + id + ", series=" + series
                + ", number=" + number + ", name='" + name
                + "', surName='" + surName + "', expires=" + expires + " }";
    }

}