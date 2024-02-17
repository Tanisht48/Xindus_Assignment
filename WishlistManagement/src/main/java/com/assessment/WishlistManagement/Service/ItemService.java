package com.assessment.WishlistManagement.Service;

import com.assessment.WishlistManagement.CustomException.ItemNotFoundException;
import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Model.Item;
import com.assessment.WishlistManagement.Model.dto.ItemDTO;
import com.assessment.WishlistManagement.Model.dto.ResponseItemDto;
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
    public List<ResponseItemDto> getWishlistItems(String username) {

        Employee employee = employeeService.findByEmail(username);
        Optional<List<Item>> itemList = iItemRepository.findByEmployee_Id(employee.getId());
        List<ResponseItemDto> responseItemList = new ArrayList<>();
        if(itemList.isPresent())
        {
            for(Item i : itemList.get())
                responseItemList.add(new ResponseItemDto(i.getItemId(),i.getItemName(), i.getItemPrice()));
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

    public void deleteItem(Long id) {

        Optional<Item> item = iItemRepository.findByItemId(id);

        if(item.isPresent())
        {
            iItemRepository.delete(item.get());
        }
        else
        {
            throw new ItemNotFoundException("Item not Found");
        }
    }
}
