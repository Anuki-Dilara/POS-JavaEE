package lk.ijse.posbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.posbackend.bo.BOFactory;
import lk.ijse.posbackend.bo.custom.OrderBO;
import lk.ijse.posbackend.dto.ItemDTO;
import lk.ijse.posbackend.dto.OrderDTO;
import lk.ijse.posbackend.entity.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Order", urlPatterns = "/order", loadOnStartup = 3)
public class OrderController extends HttpServlet {
    private OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBo(BOFactory.BOTypes.ORDER);
    final static Logger logger = LoggerFactory.getLogger(Item.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        OrderDTO orderDTO = mapper.readValue(req.getInputStream(), OrderDTO.class);
        System.out.println(orderDTO);
        System.out.println(orderDTO.getId());

        try {
            if(orderBO.saveOrder(orderDTO)){
                Map<String, Integer> sumOfQtyById = calculateSumOfQtyById(orderDTO.getItems());
                for (Map.Entry<String, Integer> entry : sumOfQtyById.entrySet()) {
//                    System.out.println("Sum of qty for id " + entry.getKey() + ": " + entry.getValue());
                    updateItemQtyByID(entry.getKey(), entry.getValue());
                }
                logger.info("Order Saved");
                resp.getWriter().write("Order Saved");
            }else{
                logger.info("Error while saving Order");
                resp.getWriter().write("Error while saving order");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Error");
            resp.getWriter().write("Error");
        }
    }

    public void updateItemQtyByID(String id , int qty) throws Exception {
        orderBO.updateOrderQtyByOrderID(id, qty);
    }

    private static Map<String, Integer> calculateSumOfQtyById(List<ItemDTO> items) {
        Map<String, Integer> sumOfQtyById = new HashMap<>();

        for (ItemDTO item : items) {
            sumOfQtyById.merge(item.getId(), item.getQty(), Integer::sum);
        }

        return sumOfQtyById;
    }

    @Override
    public void init() throws ServletException {
        logger.info("Init the Order Servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            List<OrderDTO> orders = orderBO.getAllOrders();
            System.out.println(orders);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String jsonItems = objectMapper.writeValueAsString(orders);

            resp.setContentType("application/json");
            logger.info("Fetched");
            resp.getWriter().write(jsonItems);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        OrderDTO order = mapper.readValue(req.getInputStream(), OrderDTO.class);
        System.out.println(order);

        try {
            if (orderBO.updateOrder(order)){
                logger.info("Order Updated");
                resp.getWriter().write("Order Updated");
            }else{
                logger.info("Error while updating Order");
                resp.getWriter().write("Error while updating Order");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Error");
            resp.getWriter().write("Error");
        }
    }
}
