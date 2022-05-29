package tireshop.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tireshop.entity.OrderLineItem;
import tireshop.entity.OrderLineItemId;

@RepositoryRestResource(collectionResourceRel = "lineitems", path = "lineitems")
public interface OrderLineItemsRepository extends CrudRepository<OrderLineItem, OrderLineItemId> {
}
