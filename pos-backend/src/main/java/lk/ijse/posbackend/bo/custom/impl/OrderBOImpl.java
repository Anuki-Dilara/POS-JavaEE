package lk.ijse.posbackend.bo.custom.impl;

import lk.ijse.posbackend.bo.custom.OrderBO;
import lk.ijse.posbackend.dto.OrderDTO;
import lk.ijse.posbackend.entity.OrderEntity;
import lk.ijse.posbackend.service.ServiceFactory;
import lk.ijse.posbackend.service.custom.OrderService;
import lk.ijse.posbackend.utill.Convert;
import lk.ijse.posbackend.utill.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    private OrderService orderService = (OrderService) ServiceFactory.getInstance().getService(ServiceFactory.Servicetypes.ORDER);

    @Override
    public boolean saveOrder(OrderDTO dto) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        if (orderService.save(Convert.orderDetailDTOToEntity(dto), session)){
            transaction.commit();
            session.close();
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public List<OrderDTO> getAllOrders() throws Exception {
        List<OrderEntity> all = orderService.getAll(HibernateFactoryConfig.getInstance().getSession());
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (OrderEntity order : all){
            orderDTOS.add(Convert.orderDetailEntityToDto(order));
        }
        return orderDTOS;
    }

    @Override
    public boolean updateOrder(OrderDTO dto) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        if (orderService.Update(Convert.orderDetailDTOToEntity(dto), session)){
            transaction.commit();
            session.close();
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public void updateOrderQtyByOrderID(String id, int qty) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        if (orderService.updateOrderQtyByOrderID(id ,qty , session)){
            transaction.commit();
            session.close();
        }
        session.close();
    }
}
