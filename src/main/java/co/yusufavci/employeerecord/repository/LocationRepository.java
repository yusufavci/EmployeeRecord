package co.yusufavci.employeerecord.repository;

import co.yusufavci.employeerecord.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yusuf on 1.08.2021.
 */

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
}
