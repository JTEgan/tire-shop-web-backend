package tireshop.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "lineItemProjection", types = OrderLineItem.class)
public interface LineItemProjection {

    @Value("#{target.getId()?.getOrderId()}")
    Integer getOrderId();
    @Value("#{target.getId()?.getProductId()}")
    Integer getProductId();

    Integer getCount();
}