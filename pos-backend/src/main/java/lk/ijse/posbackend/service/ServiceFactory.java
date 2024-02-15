package lk.ijse.posbackend.service;


import lk.ijse.posbackend.service.custom.Impl.CustomerServiceImpl;
import lk.ijse.posbackend.service.custom.Impl.ItemServiceImpl;
import lk.ijse.posbackend.service.custom.Impl.OrderServiceImpl;
import lk.ijse.posbackend.service.custom.Impl.UserServiceImpl;

public class ServiceFactory{
    private static ServiceFactory serviceFactory;
    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return serviceFactory == null? serviceFactory = new ServiceFactory() : serviceFactory;
    }
    public enum Servicetypes{
        CUSTOMER,
        ITEM,
        ORDER,
        USER
    }
    public SuperService getService (Servicetypes type){
        switch (type){
            case CUSTOMER:return new CustomerServiceImpl();
            case ITEM:return new ItemServiceImpl();
            case ORDER:return new OrderServiceImpl();
            case USER:return new UserServiceImpl();
            default:return null;
        }
    }
}
