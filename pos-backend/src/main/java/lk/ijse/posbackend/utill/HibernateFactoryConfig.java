package lk.ijse.posbackend.utill;


import lk.ijse.posbackend.entity.Customer;
import lk.ijse.posbackend.entity.Item;
import lk.ijse.posbackend.entity.OrderEntity;
import lk.ijse.posbackend.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactoryConfig {
    private SessionFactory sessionFactory;
    public static HibernateFactoryConfig hibernateFactoryConfig;

    private HibernateFactoryConfig(){
        Configuration configuration = new Configuration();
        configuration.configure();

        configuration
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(OrderEntity.class)
                .addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static HibernateFactoryConfig getInstance(){
        return hibernateFactoryConfig == null ? hibernateFactoryConfig = new HibernateFactoryConfig() : hibernateFactoryConfig;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
