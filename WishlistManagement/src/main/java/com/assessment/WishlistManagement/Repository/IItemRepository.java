package com.assessment.WishlistManagement.Repository;

import com.assessment.WishlistManagement.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepository extends JpaRepository<Item,Long> {

}
