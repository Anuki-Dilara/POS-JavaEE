package lk.ijse.posbackend.bo;

import lk.ijse.posbackend.bo.custom.impl.CustomerBoImpl;
import lk.ijse.posbackend.bo.custom.impl.ItemBoImpl;
import lk.ijse.posbackend.bo.custom.impl.OrderBOImpl;
import lk.ijse.posbackend.bo.custom.impl.UserBoImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}

    public static BOFactory getInstance(){
        return boFactory== null? boFactory= new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,
        ITEM,
        ORDER,
        USER
    }

    public SuperBO getBo(BOTypes type){
        switch (type){
            case CUSTOMER: return new CustomerBoImpl();
            case ITEM:return new ItemBoImpl();
            case ORDER:return new OrderBOImpl();
            case USER:return new UserBoImpl();
            default:return null;
        }
    }
}
