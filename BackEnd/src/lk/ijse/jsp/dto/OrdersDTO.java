package lk.ijse.jsp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    private String orderID;
    private String date;
    private String cusID;
    private int discount;
    private double total;
}