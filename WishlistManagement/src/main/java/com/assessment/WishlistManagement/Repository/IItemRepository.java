package com.assessment.WishlistManagement.Repository;

import com.assessment.WishlistManagement.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing item entities.
 */
public interface IItemRepository extends JpaRepository<Item,Long> {

    /**
     * Finds items by employee ID.
     *
     * @param id the ID of the employee whose items to find
     * @return an optional list of items belonging to the employee, or an empty optional if not found
     */
    Optional<List<Item>> findByEmployee_Id(String id);

    /**
     * Finds an item by ID.
     *
     * @param id the ID of the item to find
     * @return the found Item object, or an empty optional if not found
     */
    Optional<Item> findByItemId(Long id);
}
