package tireshop.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tireshop.entity.Inventory;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Integer> {
}
//public interface InventoryRepository extends CrudRepository<Inventory, Integer> {
//}
