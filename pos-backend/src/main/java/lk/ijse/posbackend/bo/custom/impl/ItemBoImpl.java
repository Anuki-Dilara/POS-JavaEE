package lk.ijse.posbackend.bo.custom.impl;

import lk.ijse.posbackend.bo.custom.ItemBO;
import lk.ijse.posbackend.dto.CustomerDTO;
import lk.ijse.posbackend.dto.ItemDTO;
import lk.ijse.posbackend.entity.Customer;
import lk.ijse.posbackend.entity.Item;
import lk.ijse.posbackend.service.ServiceFactory;
import lk.ijse.posbackend.service.custom.Impl.ItemServiceImpl;
import lk.ijse.posbackend.service.custom.ItemService;
import lk.ijse.posbackend.utill.Convert;
import lk.ijse.posbackend.utill.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBO {
    private ItemService itemService= (ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceFactory.Servicetypes.ITEM);

    @Override
    public boolean saveItem(ItemDTO dto) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        if (itemService.save(Convert.itemDTOToEntity(dto), session)){
            transaction.commit();
            session.close();
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        if (itemService.Update(Convert.itemDTOToEntity(dto), session)){
            transaction.commit();
            session.close();
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public boolean deleteItem(String id) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        if (itemService.delete(id , session)){
            transaction.commit();
            session.close();
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public List<ItemDTO> getAllItems() throws Exception {
        List<Item> all = itemService.getAll(HibernateFactoryConfig.getInstance().getSession());
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : all){
            itemDTOS.add(Convert.itemEntityToDto(item));
        }
        return itemDTOS;
    }
}
