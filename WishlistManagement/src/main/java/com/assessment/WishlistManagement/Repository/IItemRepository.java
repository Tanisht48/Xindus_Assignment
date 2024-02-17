package com.assessment.WishlistManagement.Repository;

import com.assessment.WishlistManagement.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IItemRepository extends JpaRepository<Item,Long> {

    Optional<List<Item>> findByEmployee_Id(String id);

    Optional<Item> findByItemId(Long id);
}
