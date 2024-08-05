package wanted.recruitment.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.recruitment.domain.location.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
}