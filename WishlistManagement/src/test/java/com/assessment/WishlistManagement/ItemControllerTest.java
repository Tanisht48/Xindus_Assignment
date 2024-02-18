package com.assessment.WishlistManagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.assessment.WishlistManagement.Controller.ItemController;
import com.assessment.WishlistManagement.Model.Item;
import com.assessment.WishlistManagement.Model.dto.ItemDTO;
import com.assessment.WishlistManagement.Model.dto.ResponseItemDto;
import com.assessment.WishlistManagement.Service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllItems_Success() {
        List<ResponseItemDto> items = new ArrayList<>();
        when(itemService.getWishlistItems(anyString())).thenReturn(items);

        ResponseEntity<List<ResponseItemDto>> response = itemController.getALLItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(items, response.getBody());
    }

    @Test
    void testAddAnItem_Success() {
        ItemDTO itemDTO = new ItemDTO("ItemName", 700.00);
        Object principal = new User("username", "password", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication((Authentication) principal);
        when(itemService.addItem(anyString(), any(ItemDTO.class))).thenReturn(Optional.of(new Item()));

        ResponseEntity<String> response = itemController.addAnItem(itemDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(" Item Added Successfully", response.getBody());
    }

    @Test
    void testAddAnItem_InternalServerError() {
        ItemDTO itemDTO = new ItemDTO("ItemName", 676.90);
        Object principal = new User("username", "password", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication((Authentication) principal);
        when(itemService.addItem(anyString(), any(ItemDTO.class))).thenReturn(Optional.empty());

        ResponseEntity<String> response = itemController.addAnItem(itemDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Something went wrong..", response.getBody());
    }

    @Test
    void testAddAnItem_BadRequest() {
        ItemDTO itemDTO = new ItemDTO("ItemName", 432.54);
        Object principal = new User("username", "password", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication((Authentication) principal);
        when(itemService.addItem(anyString(), any(ItemDTO.class))).thenThrow(DataIntegrityViolationException.class);

        ResponseEntity<String> response = itemController.addAnItem(itemDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Item already exists", response.getBody());
    }

    @Test
    void testDeleteItem_Success() {
        ResponseEntity<String> response = itemController.deleteItem(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Item deleted successfully", response.getBody());
    }

    @Test
    void testDeleteItem_InternalServerError() {
        doThrow(Exception.class).when(itemService).deleteItem(anyLong());

        ResponseEntity<String> response = itemController.deleteItem(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error occurred while deleting item", response.getBody());
    }
}
