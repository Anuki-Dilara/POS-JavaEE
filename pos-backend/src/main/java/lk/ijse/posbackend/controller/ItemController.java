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
import lk.ijse.posbackend.bo.custom.ItemBO;
import lk.ijse.posbackend.bo.custom.impl.ItemBoImpl;
import lk.ijse.posbackend.dto.CustomerDTO;
import lk.ijse.posbackend.dto.ItemDTO;
import lk.ijse.posbackend.entity.Customer;
import lk.ijse.posbackend.entity.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Item", urlPatterns = "/item", loadOnStartup = 3)
public class ItemController extends HttpServlet {
    private ItemBO itemBO = (ItemBoImpl) BOFactory.getInstance().getBo(BOFactory.BOTypes.ITEM);
    final static Logger logger = LoggerFactory.getLogger(Item.class);
    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Init The Item Servlet");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ItemDTO item = mapper.readValue(req.getInputStream(), ItemDTO.class);
        System.out.println(item);

        try {
            if (itemBO.updateItem(item)){
                logger.info("Item Updated");
                resp.getWriter().write("Item Updated");
            }else{
                logger.info("Error while updating Item");
                resp.getWriter().write("Error while updating Item");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Error");
            resp.getWriter().write("Error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ItemDTO item = mapper.readValue(req.getInputStream(), ItemDTO.class);
        System.out.println(item);

        try {
            if (itemBO.saveItem(item)){
                logger.info("Item Saved");
                resp.getWriter().write("Item Saved");
            }else{
                logger.info("Error while saving Item");
                resp.getWriter().write("Error while saving Item");
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

            if (itemBO.deleteItem(id)) {
                logger.info("Item Deleted");
                resp.getWriter().write("Item Deleted");
            } else {
                logger.info("Error while deleting Item");
                resp.getWriter().write("Error while deleting Item");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Error");
            resp.getWriter().write("Error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Item DoGet");
        try{
            List<ItemDTO> items = itemBO.getAllItems();
            System.out.println(items);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonItems = objectMapper.writeValueAsString(items);

            resp.setContentType("application/json");
            logger.info("Successful");
            resp.getWriter().write(jsonItems);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
