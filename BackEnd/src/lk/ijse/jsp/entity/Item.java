package lk.ijse.jsp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    String code;
    String itemName;
    int qty;
    double unitPrice;
}
