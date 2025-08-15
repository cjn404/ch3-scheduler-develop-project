# Ch3 일정 관리 앱 Develop 프로젝트

## 목표
JPA를 활용한 연관관계 CRUD & 인증/인가

<br>

## 개발 대상
Lv1부터 Lv4까지 단계별 일정 관리 API 구현

<br>

## 개발 조건
- 3 Layer Architecture에 따라 각 Layer의 목적에 맞게 개발
- CRUD 필수 기능은 DB(MySQL) 연결 및 JPA를 사용해서 개발
- 인증/인가 절차는 Cookie/Session을 활용하여 개발
- 모든 테이블은 고유 식별자(ID) 가짐
- JPA 연관관계는 단방향

<br>

## 개발 프로세스
- **Lv0** : API 명세 및 ERD 작성 
- **Lv1** : 일정 CRUD 구현
- **Lv2** : 유저 CRUD 및 ManyToOne 연관 관계 구현
- **Lv3** : 유저 회원가입 및 로그인·로그아웃 구현
- **Lv4** : 리팩토링

<br>

## 디렉토리 구조
```
src
├── main
│   ├── java
│   │   └── org
│   │       └── example
│   │           └── ch3schedulerdevelopproject
│   │               ├── Ch3SchedulerDevelopProjectApplication.java      → 프로젝트의 시작점 (main 메서드)
│   │               ├── common
│   │               │   └── filter
│   │               │       ├── FilterConfig.java                       → 필터 등록 설정 클래스
│   │               │       └── LoginFilter.java                        → 로그인 여부 체크 필터
│   │               ├── config
│   │               │   └── PasswordConfig.java                         → 비밀번호 인코더 전역 설정
│   │               ├── controller
│   │               │   ├── AuthController.java                         → 회원가입, 로그인, 로그아웃 API
│   │               │   ├── ScheduleController.java                     → 일정 관련 API
│   │               │   └── UserController.java                         → 유저 관련 API
│   │               ├── dto
│   │               │   ├── AuthRequest.java                            → 회원가입·로그인 요청 DTO
│   │               │   ├── AuthResponse.java                           → 회원가입·로그인 응답 DTO
│   │               │   ├── ScheduleDeleteRequest.java                  → 일정 삭제 요청 DTO
│   │               │   ├── ScheduleRequest.java                        → 일정 생성·조회·수정 요청 DTO
│   │               │   ├── ScheduleResponse.java                       → 일정 응답 DTO
│   │               │   ├── UserDeleteRequest.java                      → 유저 삭제 요청 DTO
│   │               │   ├── UserRequest.java                            → 유저 생성·조회·수정 요청 DTO
│   │               │   └── UserResponse.java                           → 유저 응답 DTO
│   │               ├── entity
│   │               │   ├── BaseEntity.java                             → 공통 엔티티 (생성·수정 시간 필드)
│   │               │   ├── Schedule.java                               → 일정 테이블 매핑 엔티티
│   │               │   └── User.java                                   → 유저 테이블 매핑 엔티티
│   │               ├── repository
│   │               │   ├── ScheduleRepository.java                     → 일정 DB CRUD 인터페이스
│   │               │   └── UserRepository.java                         → 유저 DB CRUD 인터페이스
│   │               └── service
│   │                   ├── AuthService.java                            → 회원가입·로그인 비즈니스 로직
│   │                   ├── ScheduleService.java                        → 일정 비즈니스 로직
│   │                   └── UserService.java                            → 유저 비즈니스 로직
│   └── resources
│       ├── application.yml                                             → 환경 설정 파일
│       ├── static                                                      → 정적 리소스(css/js/img)
│       └── templates                                                   → HTML 템플릿 파일
└── test
    └── java
        └── org
            └── example
                └── ch3schedulerdevelopproject
                    └── Ch3SchedulerDevelopProjectApplicationTests.java → 테스트 클래스

```
<br>

## 주요 기능
- **Lv1 일정 CRUD**
  - ID, 비밀번호, 작성자명, 일정 제목, 일정 내용, 작성일, 수정일 저장
    - 각 일정의 고유 식별자(ID) 자동 생성 및 관리
    - 최소 생성 시, 수정일과 작성일 동일
  - API 응답 시 비밀번호 제외
  - 수정·삭제 요청 시 비밀번호 필요
  - 비밀번호 해시값 변환
  - 실패 시 적합한 HTTP 코드 설정
 
- **Lv2 유저 CRUD 및 ManyToOne 연관 관계**  
  - ID, 비밀번호, 유저명, 이메일, 작성일, 수정일 저장
    - 각 유저의 고유 식별자(ID) 자동 생성 및 관리
    - 최소 생성 시, 수정일과 작성일 동일
  - 일정과 유저를 ManyToOne으로 매칭하여, 하나의 유저가 여러 일정을 가질 수 있도록 구성
  - API 응답 시 비밀번호 제외
  - 수정·삭제 요청 시 비밀번호 필요
  - 비밀번호 해시값 변환
  - 실패 시 적합한 HTTP 코드 설정
 
- **Lv3 유저 회원가입 및 로그인·로그아웃**  
  - Cookie/Session을 활용해 로그인 기능 구현
    - 로그인 세션 만료 시간 설정
    - 로그아웃 시 세션 무효화
  - Filter를 활용해 인증 처리
    - 회원가입, 로그인 요청은 인증 처리에서 제외 
  - 회원가입 시 이메일 중복 여부 확인
  - 비밀번호 해시값 변환
  - 실패 시 적합한 HTTP 코드 설정
    
- **Lv4 리팩토링**
  - 일정 생성·조회·수정 시 작성자명은 유저명과 자동 동기화
  - 헬퍼 메서드 추가 및 중복 코드 통합 등 
 
<br> 

## 개발 환경
- java gradle corretto 17
- Spring Boot v3.5.4
- MySQL

<br>

## API 명세서

### 회원가입 및 로그인·로그아웃
| Method | Endpoint      | Description | Parameters | Request Body                                                            | Response                                                        | Status Code    | Error Codes                                             |
| ------ | ------------- | ----------- | ---------- | ----------------------------------------------------------------------- | --------------------------------------------------------------- | -------------- | ------------------------------------------------------- |
| POST   | /users/signup | 회원 가입       | 없음         | { "password": string } <br> { "name": string } <br> { "email": string } | { "id": long } <br> { "name": string } <br> { "email": string } | 200 OK         |  400 Bad Request  <br> 409 CONFLICT            |
| POST   | /users/login  | 로그인         | 없음         | { "password": string } <br> { "name": string } <br> { "email": string } | { "id": long } <br> { "name": string } <br> { "email": string } | 200 OK         |  400 Bad Request <br> 401 UNAUTHORIZED <br> 404 Not Found |
| POST   | /users/logout | 로그아웃        | 없음         | 없음                                                                      | 없음                                                              | 200 OK  | 없음                                                      |


<br>

### 유저 CRUD
| Method | Endpoint        | Description                     | Parameters    | Request Body                                                            | Response                                                                                                                                | Status Code    | Error Codes                         |
| ------ | --------------- | ------------------------------- | ------------- | ----------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------- | -------------- | ----------------------------------- |
| GET    | /users          | 전체 사용자 조회                       | 없음            | 없음                                                                      | { "userId": long } <br> { "userName": string } <br> { "email": string } <br> { "createdAt": string } <br> { "modifiedAt": string }   | 200 OK         | 없음                                  |
| GET    | /users/{userId} | 단건 사용자 조회                       | userId (path) | 없음                                                                      | { "userId": long } <br> { "userName": string } <br> { "email": string } <br> { "createdAt": string } <br> { "modifiedAt": string }      | 200 OK         | 404 Not Found                       |
| PATCH  | /users/{userId} | 단건 사용자 수정 (비밀번호 검증 후 이름/이메일 수정) | userId (path) | { "password": string } <br> { "name": string } <br> { "email": string } | { "userId": long } <br> { "userName": string } <br> { "email": string } <br> { "createdAt": string } <br> { "modifiedAt": string }      | 200 OK         | 400 Bad Request<br> 401 UNAUTHORIZED <br> 404 Not Found  |
| DELETE | /users/{userId} | 단건 사용자 삭제                       | userId (path) | { "password": string }                                                  | 없음                                                                                                                                      | 200 OK |  400 Bad Request<br> 401 UNAUTHORIZED <br> 404 Not Found |


<br>

### 일정 CRUD

| Method | Endpoint                               | Description                   | Parameters                | Request Body                                                               | Response                                                                                                                                                                         | Status Code    | Error Codes                         |
| ------ | -------------------------------------- | ----------------------------- | ------------------------- | -------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------- | ----------------------------------- |
| POST   | /users/{userId}/schedules              | 일정 생성                         | userId (path)             | { "password": string } <br> { "title": string } <br> { "content": string } | { "user": { "userId": long } <br> "userName": string <br> "email": string <br> "createdAt": string <br> "modifiedAt": string} <br> { "schedule": { "scheduleId": long } <br> "scheduleAuthorName": string <br> "title": string <br> "content": string <br> "createdAt": string <br> "modifiedAt": string }| 200 OK         | 400 Bad Request <br> 404 Not Found  |
| GET    | /users/{userId}/schedules              | 전체 일정 조회                      | userId (path)             | 없음                                                                        |  { "user": { "userId": long } <br> "userName": string <br> "email": string <br> "createdAt": string <br> "modifiedAt": string } <br> { "schedule": { "scheduleId": long } <br> "scheduleAuthorName": string <br> "title": string <br> "content": string <br> "createdAt": string <br> "modifiedAt": string }| 200 OK         | 404 Not Found                       |
| GET    | /users/{userId}/schedules/{scheduleId} | 선택 일정 조회                      | userId, scheduleId (path) | 없음                                                                         |  { "user": { "userId": long } <br> "userName": string <br> "email": string <br> "createdAt": string <br> "modifiedAt": string} <br> { "schedule": { "scheduleId": long } <br> "scheduleAuthorName": string <br> "title": string <br> "content": string <br> "createdAt": string <br> "modifiedAt": string }| 200 OK         | 404 Not Found                       |
| PATCH  | /users/{userId}/schedules/{scheduleId} | 선택 일정 수정 (작성자명 동기화, 제목/내용 수정) | userId, scheduleId (path) | { "password": string } <br> { "title": string } <br> { "content": string } |  { "user": { "userId": long } <br> "userName": string <br> "email": string <br> "createdAt": string <br> "modifiedAt": string} <br> { "schedule": { "scheduleId": long } <br> "scheduleAuthorName": string <br> "title": string <br> "content": string <br> "createdAt": string <br> "modifiedAt": string }| 200 OK         |  400 Bad Request <br>401 UNAUTHORIZED <br> 404 Not Found |
| DELETE | /users/{userId}/schedules/{scheduleId} | 선택 일정 삭제                      | userId, scheduleId (path) | { "password": string }                                                     | 없음                                                                                                                                                                               | 200 OK | 400 Bad Request <br>401 UNAUTHORIZED <br> 404 Not Found |

<br>


## ERD

<img width="369" height="1009" alt="Image" src="https://github.com/user-attachments/assets/2aa53871-9e29-4b6d-9120-0148330cf6b4" />

