package com.assessment.WishlistManagement.Service;

import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Model.Item;
import com.assessment.WishlistManagement.Model.dto.ItemDTO;
import com.assessment.WishlistManagement.Repository.IItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private  EmployeeService employeeService;
    @Autowired
    private IItemRepository iItemRepository;
    public List<ItemDTO> getWishlistItems(String username) {

        Employee employee = employeeService.findByEmail(username);
        Optional<List<Item>> itemList = iItemRepository.findByFkEmployeeId(employee.getId());
        List<ItemDTO> responseItemList = new ArrayList<>();
        if(itemList.isPresent())
        {
            for(Item i : itemList.get())
                responseItemList.add(new ItemDTO(i.getItemName(), i.getItemPrice()));
        }
        return responseItemList;
    }

    public Optional<Item> addItem(String username,ItemDTO itemDTO) {
        Employee employee = employeeService.findByEmail(username);

        if(employee!=null){
            Item item = new Item(itemDTO.getName(),itemDTO.getPrice(),employee);
            return Optional.of(iItemRepository.save(item));
        }
        else {
          return  Optional.empty();
        }
    }
}
