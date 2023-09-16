package lk.ijse.jsp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String code;
    private String itemName;
    private int qty;
    private double unitPrice;
}
