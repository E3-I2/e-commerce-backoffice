package e3i2.ecommerce_backoffice.domain.admin.service;

import e3i2.ecommerce_backoffice.domain.admin.dto.GetMyProfileResponse;
import e3i2.ecommerce_backoffice.domain.admin.dto.UpdateMyProfileRequest;
import e3i2.ecommerce_backoffice.domain.admin.dto.UpdateMyProfileResponse;
import e3i2.ecommerce_backoffice.domain.admin.entity.Admin;
import e3i2.ecommerce_backoffice.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public GetMyProfileResponse getMyProfile(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalArgumentException("해당 관리자를 찾을 수 없습니다.")
        );
        if (admin.getDeleted().equals(true)) {
            throw new IllegalStateException("삭제된 관리자입니다.");
        }
        return new GetMyProfileResponse(
                admin.getAdminName(),
                admin.getEmail(),
                admin.getPhone()
        );
    }

    @Transactional
    public UpdateMyProfileResponse updateMyProfile(UpdateMyProfileRequest request, Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalArgumentException("해당 관리자를 찾을 수 없습니다.")
        );
        if (admin.getDeleted().equals(true)) {
            throw new IllegalStateException("삭제된 관리자입니다.");
        }
        if (adminRepository.existsByEmailAndAdminIdNot(request.getEmail(), adminId)) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        admin.update(
                request.getAdminName(),
                request.getEmail(),
                request.getPhone()
        );
        return new UpdateMyProfileResponse(
                admin.getAdminName(),
                admin.getEmail(),
                admin.getPhone()
        );
    }
}
