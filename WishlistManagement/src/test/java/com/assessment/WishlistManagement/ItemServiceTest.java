package com.assessment.WishlistManagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.assessment.WishlistManagement.CustomException.ItemNotFoundException;
import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Model.Item;
import com.assessment.WishlistManagement.Model.dto.ItemDTO;
import com.assessment.WishlistManagement.Model.dto.ResponseItemDto;
import com.assessment.WishlistManagement.Repository.IItemRepository;
import com.assessment.WishlistManagement.Service.EmployeeService;
import com.assessment.WishlistManagement.Service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ItemServiceTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private IItemRepository iItemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetWishlistItems() {
        String username = "test@example.com";
        Employee employee = new Employee();
        employee.setId("3175a88a-153f-410e-b451-caf7f723f9ed");

        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Item1", 10.0, employee));

        when(employeeService.findByEmail(username)).thenReturn(employee);
        when(iItemRepository.findByEmployee_Id(employee.getId())).thenReturn(Optional.of(itemList));

        List<ResponseItemDto> result = itemService.getWishlistItems(username);

        assertEquals(1, result.size());
        assertEquals("Item1", result.get(0).getName());
        assertEquals(10.0, result.get(0).getPrice());
    }

    @Test
    void testAddItem() {
        String username = "test@example.com";
        Employee employee = new Employee();
        employee.setId("3175a88a-153f-410e-b451-caf7f723f9ed");

        ItemDTO itemDTO = new ItemDTO("Item1", 10.0);

        when(employeeService.findByEmail(username)).thenReturn(employee);
        when(iItemRepository.save(any(Item.class))).thenAnswer(invocation -> {
            Item savedItem = invocation.getArgument(0);
            savedItem.setItemId(1L);
            return savedItem;
        });

        Optional<Item> result = itemService.addItem(username, itemDTO);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getItemId());
        assertEquals("Item1", result.get().getItemName());
        assertEquals(10.0, result.get().getItemPrice());
    }

    @Test
    void testDeleteItem() {
        Long itemId = 1L;
        Item item = new Item();
        item.setItemId(itemId);

        when(iItemRepository.findByItemId(itemId)).thenReturn(Optional.of(item));

        assertDoesNotThrow(() -> itemService.deleteItem(itemId));
    }

    @Test
    void testDeleteItem_ItemNotFound() {
        Long itemId = 1L;

        when(iItemRepository.findByItemId(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(itemId));
    }
}
