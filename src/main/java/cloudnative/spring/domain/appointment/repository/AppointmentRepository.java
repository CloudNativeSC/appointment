package cloudnative.spring.domain.appointment.repository;

import cloudnative.spring.domain.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AppointmentRepository
 *
 * <p>약속(Apptointment) 엔티티의 기본 CRUD를 제공하는 JPA Repository.</p>
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
