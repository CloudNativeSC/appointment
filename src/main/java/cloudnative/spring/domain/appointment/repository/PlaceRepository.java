package cloudnative.spring.domain.appointment.repository;

import cloudnative.spring.domain.appointment.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PlaceRepository
 *
 * <p>장소(Place) 엔티티의 기본 CRUD를 제공하는 JPA Repository.</p>
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
