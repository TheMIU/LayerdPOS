package lk.ijse.jsp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderDTO {
    private String orderID;
    private String date;
    private ArrayList<CartDTO> cartArray;
    private double total;
    private int discount;
    private String cusID;
}
