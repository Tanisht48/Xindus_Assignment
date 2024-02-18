package com.assessment.WishlistManagement.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO class representing employee data transferred between client and server.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseItemDto {
    private Long id;
    private String name;
    private Double price;

}
