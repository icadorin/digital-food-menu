package com.project.digital_food_menu.food;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FoodRepositoryTest {

    @Autowired
    private FoodRepository repository;

    @Test
    void shouldSaveFoodSuccessfully() {
        Food food = new Food(null, "Pizza", "pizza.jpg", 25);

        Food savedFood = repository.save(food);

        assertThat(savedFood.getId()).isNotNull();
        assertThat(savedFood.getTitle()).isEqualTo("Pizza");
        assertThat(savedFood.getPrice()).isEqualTo(25);
    }

    @Test
    void shouldFindFoodById() {
        Food food = new Food(null, "Pasta", "pasta.jpg", 15);
        Food savedFood = repository.save(food);

        Optional<Food> foundFood = repository.findById(savedFood.getId());

        assertThat(foundFood).isPresent();
        assertThat(foundFood.get().getTitle()).isEqualTo("Pasta");
    }

    @Test
    void shouldReturnEmptyWhenFoodNotFound() {

        Optional<Food> foundFood = repository.findById(999L);

        assertThat(foundFood).isEmpty();
    }
}
