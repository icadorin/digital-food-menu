package com.project.digital_food_menu.food;

import jakarta.persistence.*;

@Table(name = "foods")
@Entity(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String image;
    private Integer price;

    public Food() {
    }

    public Food(Long id, String title, String image, Integer price) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
    }

    public Food(FoodRequestDTO data) {
        this.image = data.image();
        this.title = data.title();
        this.price = data.price();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
