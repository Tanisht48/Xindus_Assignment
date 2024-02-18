package com.assessment.WishlistManagement.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Entity class representing an item in the wishlist.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    @NotEmpty
    private String itemName;
    @NotNull
    private Double itemPrice;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "fk_employee_id")
    private Employee employee;

    /**
     * Constructor with parameters.
     *
     * @param itemName  the name of the item
     * @param itemPrice the price of the item
     * @param employee  the employee associated with the item
     */
    public Item(String itemName,Double itemPrice,Employee employee)
    {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.employee = employee;
    }

}
