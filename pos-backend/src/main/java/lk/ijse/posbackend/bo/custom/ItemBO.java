package lk.ijse.posbackend.bo.custom;

import lk.ijse.posbackend.bo.SuperBO;
import lk.ijse.posbackend.dto.ItemDTO;

import java.util.List;

public interface ItemBO extends SuperBO {
    boolean saveItem(ItemDTO dto) throws Exception;

    boolean updateItem(ItemDTO dto) throws Exception;

    boolean deleteItem(String id) throws Exception;

    List<ItemDTO> getAllItems() throws  Exception;
}
