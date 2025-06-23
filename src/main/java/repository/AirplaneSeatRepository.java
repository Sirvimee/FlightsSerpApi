package repository;

import model.AirplaneSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneSeatRepository extends JpaRepository<AirplaneSeat, Long> {
}
