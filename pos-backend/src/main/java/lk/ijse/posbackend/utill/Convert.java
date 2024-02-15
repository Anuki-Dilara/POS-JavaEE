package lk.ijse.posbackend.utill;

import lk.ijse.posbackend.dto.CustomerDTO;
import lk.ijse.posbackend.dto.ItemDTO;
import lk.ijse.posbackend.dto.OrderDTO;
import lk.ijse.posbackend.dto.UserDTO;
import lk.ijse.posbackend.entity.Customer;
import lk.ijse.posbackend.entity.Item;
import lk.ijse.posbackend.entity.OrderEntity;
import lk.ijse.posbackend.entity.User;

import java.util.ArrayList;
import java.util.List;


public class Convert {

    public static Customer customerDTOToEntity(CustomerDTO dto){
        return new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary());
    }
    public static CustomerDTO customerEntityToDto(Customer customer){
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getSalary());
    }
    public static Item itemDTOToEntity(ItemDTO dto){
        return new Item(dto.getId(), dto.getName(), dto.getPrice(), dto.getQty());
    }
    public static ItemDTO itemEntityToDto(Item item){
        return new ItemDTO(item.getId(), item.getName(), item.getPrice(), item.getQty());
    }
    public static User userDTOToEntity(UserDTO dto){
        return new User(dto.getEmail(), dto.getUsername(), dto.getPassword());
    }
    public static UserDTO userEntityToDto(User user){
        return new UserDTO(user.getEmail(), user.getUsername(), user.getPassword());
    }


    public static OrderEntity orderDetailDTOToEntity(OrderDTO dto){
        System.out.println(dto);
        List<Item> items = new ArrayList<>();
        for (ItemDTO i : dto.getItems()) {
            items.add(itemDTOToEntity(i));
        }
        return new OrderEntity(dto.getId(), dto.getDate(), customerDTOToEntity(dto.getCustomer()), items, dto.getDiscount(), dto.getTotal());
    }
    public static OrderDTO orderDetailEntityToDto(OrderEntity od){
        List<ItemDTO> items = new ArrayList<>();
        for (Item i : od.getItems()) {
            items.add(itemEntityToDto(i));
        }
        return new OrderDTO(od.getId(), od.getDate(), customerEntityToDto(od.getCustomer()), items, od.getDiscount(), od.getTotal());
    }
}
