package lk.ijse.posbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    private String id;
    private LocalDate date;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Item> items;
    private double discount;
    private double total;
}
