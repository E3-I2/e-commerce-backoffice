package e3i2.ecommerce_backoffice.domain.admin.controller;

import e3i2.ecommerce_backoffice.domain.admin.dto.GetMyProfileResponse;
import e3i2.ecommerce_backoffice.domain.admin.dto.UpdateMyProfileRequest;
import e3i2.ecommerce_backoffice.domain.admin.dto.UpdateMyProfileResponse;
import e3i2.ecommerce_backoffice.domain.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/me")
    public ResponseEntity<GetMyProfileResponse> getMyProfile(HttpSession session) {
        Long adminId = (Long) session.getAttribute("adminId");
        if (adminId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getMyProfile(adminId));
    }

    @PutMapping("/me")
    public ResponseEntity<UpdateMyProfileResponse> updateMyProfile(@Valid @RequestBody UpdateMyProfileRequest request, HttpSession session) {
        Long adminId = (Long) session.getAttribute("adminId");
        if (adminId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateMyProfile(request, adminId));
    }
}
