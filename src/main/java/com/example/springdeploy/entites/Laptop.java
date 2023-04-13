package com.example.springdeploy.entites;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "laptops")
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mark;
    private String code;
    private String characteristics;
    private double price;
    private LocalDate realeaseDate;
    private boolean online;

    public Laptop() {
    }

    public Laptop(Long id, String mark, String code, String characteristics, double price, LocalDate realeaseDate, boolean online) {
        this.id = id;
        this.mark = mark;
        this.code = code;
        this.characteristics = characteristics;
        this.price = price;
        this.realeaseDate = realeaseDate;
        this.online = online;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getRealeaseDate() {
        return realeaseDate;
    }

    public void setRealeaseDate(LocalDate realeaseDate) {
        this.realeaseDate = realeaseDate;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", code='" + code + '\'' +
                ", characteristics='" + characteristics + '\'' +
                ", price=" + price +
                ", realeaseDate=" + realeaseDate +
                ", online=" + online +
                '}';
    }
}
