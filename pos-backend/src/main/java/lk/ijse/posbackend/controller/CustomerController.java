package lk.ijse.posbackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.posbackend.bo.BOFactory;
import lk.ijse.posbackend.bo.custom.CustomerBO;
import lk.ijse.posbackend.bo.custom.impl.CustomerBoImpl;
import lk.ijse.posbackend.dto.CustomerDTO;
import lk.ijse.posbackend.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "Customer", urlPatterns = "/customer", loadOnStartup = 4)
public class CustomerController extends HttpServlet {
    Connection connection;
    final static Logger logger = LoggerFactory.getLogger(Customer.class);

    private CustomerBO customerBO = (CustomerBoImpl) BOFactory.getInstance().getBo(BOFactory.BOTypes.CUSTOMER);

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Init The Customer Servlet");

        try {
            InitialContext ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/pos");
            this.connection = pool.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Customer DoGet");
        try{
            List<CustomerDTO> customers = customerBO.getAllCustomers();
            System.out.println(customers);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonCustomers = objectMapper.writeValueAsString(customers);

            resp.setContentType("application/json");
            resp.getWriter().write(jsonCustomers);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        CustomerDTO customer = mapper.readValue(req.getInputStream(), CustomerDTO.class);
        System.out.println(customer);

        try {
            if (customerBO.saveCustomer(customer)){
                logger.info("Customer Saved");
                resp.getWriter().write("Customer Saved");
            }else{
                logger.info("Error while saving Customer");
                resp.getWriter().write("Error while saving Customer");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Error");
            resp.getWriter().write("Error");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        CustomerDTO customer = mapper.readValue(req.getInputStream(), CustomerDTO.class);
        System.out.println(customer);

        try {
            if (customerBO.updateCustomer(customer)){
                logger.info("Customer Updated");
                resp.getWriter().write("Customer Updated");
            }else{
                logger.info("Error while updating Customer");
                resp.getWriter().write("Error while updating Customer");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Error");
            resp.getWriter().write("Error");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(req.getReader());

            String id = jsonNode.get("id").asText();

            if (customerBO.deleteCustomer(id)) {
                logger.info("Customer Deleted");
                resp.getWriter().write("Customer Deleted");
            } else {
                logger.info("Error while deleting Customer");
                resp.getWriter().write("Error while deleting Customer");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Error");
            resp.getWriter().write("Error");
        }
    }


}
