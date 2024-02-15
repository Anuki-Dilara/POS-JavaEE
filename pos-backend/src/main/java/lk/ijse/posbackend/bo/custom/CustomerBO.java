package lk.ijse.posbackend.bo.custom;

import lk.ijse.posbackend.bo.SuperBO;
import lk.ijse.posbackend.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO extends SuperBO {

    List<CustomerDTO> getAllCustomers() throws  Exception;

    boolean saveCustomer(CustomerDTO dto) throws Exception;

    boolean updateCustomer(CustomerDTO dto) throws Exception;

    boolean deleteCustomer(String id) throws Exception;
}
