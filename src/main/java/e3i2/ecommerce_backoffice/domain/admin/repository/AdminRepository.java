package e3i2.ecommerce_backoffice.domain.admin.repository;

import e3i2.ecommerce_backoffice.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmailAndAdminIdNot(String email, Long adminId);
}
