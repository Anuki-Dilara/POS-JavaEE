package lk.ijse.posbackend.bo.custom;

import lk.ijse.posbackend.bo.SuperBO;
import lk.ijse.posbackend.dto.UserDTO;

import java.util.List;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDTO dto) throws Exception;
    List<UserDTO> getAuth() throws Exception;
}
