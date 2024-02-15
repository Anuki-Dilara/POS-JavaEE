package lk.ijse.posbackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.posbackend.bo.BOFactory;
import lk.ijse.posbackend.bo.custom.UserBO;
import lk.ijse.posbackend.dto.ItemDTO;
import lk.ijse.posbackend.dto.UserDTO;
import lk.ijse.posbackend.entity.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "User", urlPatterns = "/user", loadOnStartup = 3)
public class UserController extends HttpServlet {
    private UserBO userBO = (UserBO) BOFactory.getInstance().getBo(BOFactory.BOTypes.USER);
    final static Logger logger = LoggerFactory.getLogger(Item.class);


    @Override
    public void init() throws ServletException {
        logger.info("Init the User Servlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userDTO = mapper.readValue(req.getInputStream(), UserDTO.class);
        System.out.println(userDTO);

        try {
            if(userBO.saveUser(userDTO)){
                logger.info("User Saved");
                resp.getWriter().write("User Saved");
            }else{
                logger.info("Error while saving User");
                resp.getWriter().write("Error while saving User");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Error");
            resp.getWriter().write("Error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            List<UserDTO> users = userBO.getAuth();
            System.out.println(users);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonUsers = objectMapper.writeValueAsString(users);

            resp.setContentType("application/json");
            logger.info("Fetched");
            resp.getWriter().write(jsonUsers);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
