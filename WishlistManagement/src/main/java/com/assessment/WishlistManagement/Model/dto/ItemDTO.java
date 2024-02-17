package com.assessment.WishlistManagement.Model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    @NotEmpty
    private String Name;
    @NotEmpty
    private Double Price;
}
