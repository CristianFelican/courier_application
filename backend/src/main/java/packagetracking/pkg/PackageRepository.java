package packagetracking.pkg;

import org.springframework.data.jpa.repository.JpaRepository;
import packagetracking.Courier.Courier;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Integer> {

    List<Package> findByStatus(Status status);  // Căutare pachete după status
    List<Package> findByCourierId(int courierId);

}
