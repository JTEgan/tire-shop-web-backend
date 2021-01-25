package jonegan.rmmservicesserverapp.repositories;

import jonegan.rmmservicesserverapp.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, String> {
}
