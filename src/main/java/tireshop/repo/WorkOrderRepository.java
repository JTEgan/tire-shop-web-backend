package tireshop.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tireshop.entity.WorkOrder;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface WorkOrderRepository extends PagingAndSortingRepository<WorkOrder, Integer> {
}
