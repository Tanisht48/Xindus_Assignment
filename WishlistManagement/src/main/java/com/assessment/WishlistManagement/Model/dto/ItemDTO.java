package com.assessment.WishlistManagement.Model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO class representing employee data transferred between client and server.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    @NotEmpty
    private String name;
    @NotNull
    private Double price;
}
