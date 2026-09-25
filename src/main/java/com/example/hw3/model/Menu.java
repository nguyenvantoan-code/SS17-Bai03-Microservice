package com.example.hw3.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable {
    private Long id;
    
    @JsonProperty("dish_name")
    private String dishName;
    
    private Integer price;
}
