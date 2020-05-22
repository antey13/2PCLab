package company.repository.fly;

import company.model.fly.FlyBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlyBookingRepository extends JpaRepository<FlyBooking, Long> {
}
