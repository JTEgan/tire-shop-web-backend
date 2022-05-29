package tireshop.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLineItemDto {
    Integer countOrdered;

    Integer productId;
    Integer aspectratio;
    String brand;
    Integer diameter;
    String modelNumber;
    Double salePrice;
    String size;
    Integer width;
}
