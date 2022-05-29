package tireshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tireshop.controller.dto.OrderDetailsDto;
import tireshop.controller.dto.OrderLineItemDto;
import tireshop.entity.WorkOrder;
import tireshop.repo.CustomerRepository;
import tireshop.repo.OrderLineItemDao;
import tireshop.repo.WorkOrderRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class OrderDetailsController {
    private final WorkOrderRepository workOrderRepository;
    private final CustomerRepository customerRepository;
    private final OrderLineItemDao orderLineItemDao;


    public OrderDetailsController(WorkOrderRepository workOrderRepository, CustomerRepository customerRepository, OrderLineItemDao orderLineItemDao) {
        this.workOrderRepository = workOrderRepository;
        this.customerRepository = customerRepository;
        this.orderLineItemDao = orderLineItemDao;
    }

    @GetMapping("/order-details/{orderId}")
    public ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable Integer orderId) {
        Optional<WorkOrder> order = workOrderRepository.findById(orderId);
        if (order.isPresent()){
            final WorkOrder workOrder = order.get();
            OrderDetailsDto detailsDto = OrderDetailsDto.builder()
                    .workOrder(workOrder)
                    .customer(customerRepository.findById(workOrder.getCustomerId()).orElse(null))
                    .lineItems(getLineItems(orderId))
                    .build();
            return new ResponseEntity<>(detailsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private List<OrderLineItemDto> getLineItems(Integer orderId) {
        return orderLineItemDao.getOrderLineItems(orderId);
    }


}
