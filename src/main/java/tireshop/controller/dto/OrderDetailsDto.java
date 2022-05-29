package tireshop.controller.dto;

import lombok.Builder;
import lombok.Data;
import tireshop.entity.Customer;
import tireshop.entity.WorkOrder;

import java.util.List;

@Data
@Builder
public class OrderDetailsDto {
    WorkOrder workOrder;
    Customer customer;
    List<OrderLineItemDto> lineItems;
}
