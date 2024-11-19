package com.project.digital_food_menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.digital_food_menu.food.Food;
import com.project.digital_food_menu.food.FoodRepository;
import com.project.digital_food_menu.food.FoodRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FoodRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveFoodSuccessfully() throws Exception {

        FoodRequestDTO foodRequest = new FoodRequestDTO("Burger", "burger.jpg", 20);

        mockMvc.perform(post("/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foodRequest)))
                .andExpect(status().isOk());

        Food savedFood = repository.findAll().get(0);
        assertThat(savedFood.getTitle()).isEqualTo("Burger");
    }

    @Test
    void shouldReturnAllFoods() throws Exception {
        repository.save(new Food(null, "Pizza", "pizza.jpg", 25));
        repository.save(new Food(null, "Pasta", "pasta.jpg", 20));

        mockMvc.perform(get("/food"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Pizza"))
                .andExpect(jsonPath("$[1].title").value("Pasta"));
    }
}
