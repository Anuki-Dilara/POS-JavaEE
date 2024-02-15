package lk.ijse.posbackend.bo.custom;

import lk.ijse.posbackend.bo.SuperBO;
import lk.ijse.posbackend.dto.OrderDTO;

import java.util.List;

public interface OrderBO extends SuperBO {
    boolean saveOrder(OrderDTO dto) throws Exception;
    List<OrderDTO> getAllOrders() throws  Exception;
    boolean updateOrder(OrderDTO dto) throws Exception;
    void updateOrderQtyByOrderID(String id, int qty) throws Exception;
}
