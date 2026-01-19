# 고객 및 상품 관리 시스템 프로젝트
- 팀명 : E3-I2
- 팀장 : 김영재
- 팀원 : 김대훈, 나은총, 이준연, 이한비
- 개발 기간 : 2026년 1월 13일 ~ 2026년 1월 20일

## 프로젝트 요구사항
- 고객, 상품, 주문 정보를 **체계적으로 관리**할 수 있는 기능
- 상품 리뷰를 **조회하고 관리**할 수 있으며, **상품별 평점과 통계**를 제공하는 기능
- 관리자가 백오피스에 **회원가입을 요청**하고, 슈퍼 관리자가 **승인/거부**할 수 있는 기능
- 데이터 증가에 따른 **검색, 정렬, 필터, 페이징** 기능
- 커머스 서비스 현황을 한눈에 파악할 수 있는 **대시보드** 기능

## 패키지 구조

<details>
<summary> ecommerce_backoffice 패키지 </summary>
<div markdown="1">

```bash
ecommerce_backoffice
└─ ecommerce_backoffice
   ├─ common
   │  ├─ annotation        # 공통 커스텀 어노테이션
   │  ├─ config            # 전역 설정 (Encoder, Web, JPA)
   │  ├─ dto
   │  │  ├─ response       # 공통 응답 DTO
   │  │  └─ session        # 세션 관련 DTO
   │  ├─ entity            # 공통 엔티티(Base Entity)
   │  ├─ exception
   │  │  └─ dto            # 에러 응답 DTO
   │  ├─ interceptor       # 인터셉터
   │  └─ util
   │     ├─ initializer    # 데이터 시딩(초기화)
   │     └─ pagination     # 페이징 유틸
   │
   └─ domain
      ├─ admin             # 관리자 도메인
      │  ├─ controller
      │  ├─ dto
      │  ├─ entity
      │  ├─ repository
      │  └─ service
      │
      ├─ account           # 고객 도메인
      │  ├─ controller
      │  ├─ dto
      │  ├─ entity
      │  ├─ repository
      │  └─ service
      │
      ├─ product           # 상품 도메인
      │  ├─ controller
      │  ├─ dto
      │  ├─ entity
      │  ├─ repository
      │  │  └─ projection  # 대시보드 조회 전용 Projection
      │  └─ service
      │
      ├─ ordering          # 주문 도메인
      │  ├─ controller
      │  ├─ dto
      │  ├─ entity
      │  ├─ repository
      │  └─ service
      │
      └─ review            # 리뷰 도메인
         ├─ controller
         ├─ dto
         ├─ entity
         ├─ repository
         └─ service
```

</div>
</details>

## 삭제 전략
### Soft Delete 정책
- [x] 실제 삭제 대신 deleted_at 업데이트
- [x] 기본 조회 시 삭제 데이터 제외
- [x] 각 Entity 내부 delete(), restore() 메서드로 제어

## Branch 전략


<details>
<summary>Dev, Stage, Prod </summary>
<div markdown="1">

- **main (Prod)**
    - **운영 단계 브랜치**
    - **stage에서만 merge**
    - 3명 이상의 approve 필요
    - PR Conversation 전부 Resolve 필요
    - ***PR만 허용, merge 불가능함***
- **stage**
    - **main 에 배포 전 검증 브랜치**
    - dev 에서 merge
    - 1명 이상의 approve 필요
    - PR Conversation 전부 Resolve 필요
    - ***PR만 허용, merge 불가능함***
- **dev**
    - **feature 가 통합되어 모이는 단계**
    - 1명 이상의 approve 필요
    - ***PR만 허용, merge 불가능함***
- **feature**
    - **각자 기능 개발하는 개인 브랜치**
    - **기능 완성 후엔 삭제**
    - ***feature/업무 ID 형식으로 네이밍***
</div>
</details>

## API 명세서
- 공통 사항
  - API 헤더는 공통적으로 Content-Type: application/json를 사용
  - 인증이 필요한 API는 로그인 필요(세션)
    - 로그인 하지않고 해당 API 접속 시 401 UnAuthorized
<details>
<summary> 관리자 </summary>
<div markdown="1">

### 1. 관리자 회원가입
- **URL**: `/api/admins/signup`
- **Method**: `POST`
- **Request Body**:
```json
{
  "adminName": "은총",
  "email": "email@email.com",
  "password": "12345678",
  "phone": "010-1111-1111",
  "role": "CS_ADMIN",
  "requestMessage": "지원사유"
}
```
- **Response**:
```json
{
  "code": "CREATED",
  "data": {
    "adminId": 1,
    "adminName": "은총",
    "email": "email@email.com",
    "phone": "010-1111-1111",
    "role": "CS_ADMIN",
    "createdAt": "2026-01-15",
    "status": "WAIT",
    "requestMessage": "지원사유"
  },
  "message": "회원가입 신청이 완료되었습니다. 관리자 승인을 기다려주세요.",
  "success": true
}
```

### 2. 관리자 로그인
- **URL**: `/api/admins/login`
- **Method**: `POST`
- **Request Body**:
```json
{
  "email": "super@super.com",
  "password": "12345678"
}
```
- **Response**:
```json
{
  "code": "OK",
  "data": {
    "acceptedAt": null,
    "adminId": 1,
    "adminName": "은총",
    "createdAt": "2026-01-15",
    "email": "super@super.com",
    "phone": "010-1111-1111",
    "role": "SUPER_ADMIN",
    "status": "ACT"
  },
  "message": "로그인 성공",
  "success": true
}
```

### 3. 관리자 로그아웃
- **URL**: `/api/admins/logout`
- **Method**: `POST`
- **Response**:

### 4. 관리자 승인
- **URL**: `/api/admins/{adminId}/accept`
- **Method**: `PUT`
- **Response**:
```json
{
  "code": "OK",
  "data": {
    "acceptedAt": "2026-01-15",
    "adminId": 2,
    "adminName": "대훈",
    "createdAt": "2026-01-15",
    "deniedAt": null,
    "email": "op@op.com",
    "phone": "010-1111-1111",
    "requestMessage": "지원사유",
    "role": "OP_ADMIN",
    "status": "ACT"
  },
  "message": "관리자가 승인되었습니다.",
  "success": true
}
```

### 5. 관리자 거부
- **URL**: `/api/admins/{adminId}/deny`
- **Method**: `PUT`
- **Request Body**:
```json
{
    "deniedReason": "거부 사유"
}
```
- **Response**:
```json
{
  "code": "OK",
  "data": {
    "acceptedAt": null,
    "adminId": 2,
    "adminName": "대훈",
    "createdAt": "2026-01-15",
    "deniedAt": "2026-01-15T11:41:32.0677364",
    "email": "op@op.com",
    "phone": "010-1111-1111",
    "requestMessage": "지원사유",
    "role": "OP_ADMIN",
    "status": "DENY"
  },
  "message": "관리자 신청이 거부되었습니다.",
  "success": true
}
```

### 6. 관리자 리스트 조회
- **URL**: `/api/admins`
- **Method**: `GET`
- **Query Parameters**:
    - `page`: 페이지 번호 (default: 1)
    - `limit`: 페이지당 항목 수 (default: 10)
    - `sortBy` : 정렬 기준 (adminName, email, createdAt 등...) 
    - `sortOrder` : 정렬 순서 (asc, desc)
    - `role`: 역할 필터 (SUPER_ADMIN, CS_ADMIN, OP_ADMIN)
    - `status`: 상태 필터 (ACT, IN_ACT, SUSOEND)
     
- **Response**:
```json
{
  "success": true,
  "code": "OK",
  "data": {
    "items": [
      {
        "id": 2,
        "name": "홍길동",
        "email": "hong@test.com",
        "phone": "010-1234-5678",
        "role": "CS_ADMIN",
        "status": "WAIT",
        "createdAt": "2026-01-16",
        "acceptedAt": "",
        "deniedAt": "",
        "requestMessage": "CS관리자에 지원합니다."
      },
      {
        "id": 1,
        "name": "TEST_SUPER_ADMIN",
        "email": "admin@test.com",
        "phone": "010-0000-0000",
        "role": "SUPER_ADMIN",
        "status": "ACT",
        "createdAt": "2026-01-16",
        "acceptedAt": "",
        "deniedAt": ""
      }
    ],
    "pagination": {
      "limit": 10,
      "page": 1,
      "total": 2,
      "totalPages": 1
    }
  }
}
```

### 7. 관리자 상세 조회
- **URL**: `/api/admins/{adminId}`
- **Method**: `GET`
- **Response**:
```json
{
  "success": true,
  "code": "OK",
  "data": {
    "id": 2,
    "name": "홍길동",
    "email": "hong@test.com",
    "phone": "010-1234-5678",
    "role": "CS_ADMIN",
    "status": "WAIT",
    "createdAt": "2026-01-16",
    "acceptedAt": "",
    "deniedAt": "",
    "requestMessage": "CS관리자에 지원합니다."
  }
}
```

### 8. 관리자 수정
- **URL**: `/api/admins/{adminId}`
- **Method**: `PUT`
- **Request Body**:
```json
{
  "adminName": "홍길동",
  "email": "updated@sparta.com",
  "phone": "010-1111-2222"
}
```
- **Response**:
```json
{
  "success": true,
  "code": "200 OK",
  "data": {
    "id": 1,
    "name": "홍길동",
    "email": "updated@sparta.com",
    "phone": "010-1111-2222",
    "role": "OP_ADMIN",
    "status": "ACT",
    "createdAt": "2025-04-26",
    "updatedAt": "2026-01-14",
    "aceeptAt": "2025-04-27",
    "deniedAt": ""

  }
}
```

### 9. 관리자 삭제
- **URL**: `/api/admins/{adminId}`
- **Method**: `DELETE`
- **Response**:
```json
{
  "success": true,
  "code": "OK",
  "message": "사용자가 삭제되었습니다"
}
```

### 10. 관리자 역할 변경
- **URL**: `/api/admins/{adminId}/role`
- **Method**: `PUT`
- **Description**: SUPER_ADMIN 만 역할 변경이 가능
- **Request Body**:
```json
{
  "role": "CS_ADMIN"
}
```
- **Response**:
```json
{
  "code": "OK",
  "data": {
    "acceptedAt": "2026-01-15",
    "adminId": 2,
    "adminName": "대훈",
    "createdAt": "2026-01-15",
    "deniedAt": null,
    "email": "op@op.com",
    "phone": "010-1111-1111",
    "role": "CS_ADMIN",
    "status": "ACT"
  },
  "success": true
}
```

### 11. 관리자 상태 변경
- **URL**: `/api/admins/{adminId}/status`
- **Method**: `PUT`
- **Description**: SUPER_ADMIN 만 상태 변경 가능
- **Request Body**:
```json
{
  "status": "SUSPEND"
}
```
- **Response**:
```json
{
  "code": "OK",
  "data": {
    "acceptedAt": "2026-01-15",
    "adminId": 2,
    "adminName": "대훈",
    "createdAt": "2026-01-15",
    "deniedAt": null,
    "email": "op@op.com",
    "phone": "010-1111-1111",
    "role": "OP_ADMIN",
    "status": "ACT"
  },
  "success": true
}
```

### 12. 내 프로필 조회
- **URL**: `/api/admins/me`
- **Method**: `GET`
- **Response**:
```json
{
  "code": "OK",
  "data": {
    "acceptedAt": null,
    "adminId": 1,
    "adminName": "은총",
    "createdAt": "2026-01-15",
    "deniedAt": null,
    "email": "super@super.com",
    "phone": "010-1111-1111",
    "role": "SUPER_ADMIN",
    "status": "ACT"
  },
  "success": true
}
```

### 13. 내 프로필 수정
- **URL**: `/api/admins/me/profile`
- **Method**: `PUT`
- **Request Body**:
```json
{
  "adminName": "총은",
  "email": "repus@repus.com",
  "phone": "010-9999-9999"
}
```
- **Response**:
```json
{
  "code": "OK",
  "data": {
    "acceptedAt": null,
    "adminId": 1,
    "adminName": "총은",
    "createdAt": "2026-01-15",
    "deniedAt": null,
    "email": "repus@repus.com",
    "phone": "010-9999-9999",
    "role": "SUPER_ADMIN",
    "status": "ACT"
  },
  "message": "프로필이 성공적으로 업데이트되었습니다.",
  "success": true
}
```

### 14. 내 비밀번호 변경
- **URL**: `/api/admins/me/password`
- **Method**: `PUT`
- **Request Body**:
```json
{
  "currentPassword": "12345678",
  "newPassword": "87654321"
}
```
- **Response**:
```json
{
  "code": "OK",
  "message": "비밀번호가 성공적으로 변경되었습니다.",
  "success": true
}
```

</div>
</details>

<details>
<summary> 상품 </summary>
<div markdown="1">



</div>
</details>

<details>
<summary> 고객 </summary>
<div markdown="1">



</div>
</details>

<details>
<summary> 주문 </summary>
<div markdown="1">



</div>
</details>

<details>
<summary> 리뷰 </summary>
<div markdown="1">



</div>
</details>

<details>
<summary> 대시보드 </summary>
<div markdown="1">



</div>
</details>

## ERD


