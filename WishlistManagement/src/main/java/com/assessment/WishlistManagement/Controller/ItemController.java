package com.assessment.WishlistManagement.Controller;

import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Model.Item;
import com.assessment.WishlistManagement.Model.dto.ItemDTO;
import com.assessment.WishlistManagement.Service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @GetMapping("/wishlist")
    public ResponseEntity<List<ItemDTO>> getALLItems()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Use the username to fetch the user's wishlist items from the service
        List<ItemDTO> items = itemService.getWishlistItems(username);

        return ResponseEntity.ok(items);
    }

    @PostMapping("/wishlist")
    public ResponseEntity<String> addAnItem(@Valid @RequestBody ItemDTO itemDTO)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        try {
            Optional<Item> item = itemService.addItem(username,itemDTO);
            if (item.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong..");
            } else {
                return ResponseEntity.ok("Registered Successfully");
            }
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
    }
}
