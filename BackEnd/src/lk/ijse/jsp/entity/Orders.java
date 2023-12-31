package lk.ijse.jsp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private String orderID;
    private String date;
    private String cusID;
    private int discount;
    private double total;
}