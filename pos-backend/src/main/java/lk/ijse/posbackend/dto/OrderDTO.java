package lk.ijse.posbackend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private CustomerDTO customer;
    private List<ItemDTO> items;
    private double discount;
    private double total;
}
