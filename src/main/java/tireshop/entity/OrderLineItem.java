package tireshop.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "order_product_relation")
public class OrderLineItem {
    @EmbeddedId
    private OrderLineItemId id;

    @Column(name = "count")
    private Integer count;

    public OrderLineItemId getId() {
        return id;
    }

    public void setId(OrderLineItemId id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}

