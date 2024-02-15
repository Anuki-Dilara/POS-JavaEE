package lk.ijse.posbackend.service.custom;


import lk.ijse.posbackend.entity.OrderEntity;
import lk.ijse.posbackend.service.Crud;
import org.hibernate.Session;

public interface OrderService extends Crud<OrderEntity> {
    boolean updateOrderQtyByOrderID(String id , int qty, Session session) throws Exception;
}
