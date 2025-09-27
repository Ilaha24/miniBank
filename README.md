# Getting Started


# Bank Account Management System

## Layihə haqqında
Bu layihə sadə bank sistemini simulyasiya edir. İstifadəçilər (User), onların hesabları (Account) və bütün maliyyə əməliyyatlarının tarixçəsi (TransactionLog) idarə olunur.
Müəllimin tələbinə uyğun olaraq **entity-lər arasında relation saxlanılmır**, yalnız ID-lər istifadə olunur.

## Texnologiyalar
- Java 17
- Spring Boot (Web, Data JPA)
- Lombok
- H2 Database (və ya istənilən RDBMS)

## Entity-lər
- **UserEntity** – istifadəçi məlumatlarını saxlayır (ad, yaş, email, status, createdAt, updatedAt).
- **AccountEntity** – balans və sahibini saxlayır. Hesab həmişə balans=0 ilə açılır. Balans yalnız əməliyyatlarla dəyişir.
- **TransactionLogEntity** – bütün deposit/withdraw/transfer əməliyyatlarının tarixçəsini saxlayır.

## DTO-lar
- **UserCreateRequest / UserResponse / UpdateUserRequest**
- **AccountCreateRequest / AccountResponse**
- **DepositWithdrawRequest** – müəyyən hesaba pul yatırmaq və ya çıxarmaq üçün.
- **TransferRequest** – iki hesab arasında pul köçürmək üçün.
- **TransactionLogResponse** – əməliyyatların tarixçəsini göstərmək üçün.

## Əməliyyat Məntiqi
- **User**: CRUD əməliyyatları.
- **Account**: Create (yalnız userId ilə, balans=0). Sonrakı əməliyyatlar:
    - Deposit
    - Withdraw
    - Transfer
- **TransactionLog**: bütün əməliyyatlar avtomatik loglanır. Request yoxdur, yalnız cavab DTO-su var.

## Qərarların əsaslandırılması
- **Relation yoxdur** – müəllimin tapşırığına uyğun olaraq yalnız ID saxlanılır.
- **Validation DTO-larda saxlanılır** – Entity yalnız saxlama üçün istifadə olunur.
- **Balans 0-dan başlayır** – İlk və sonrakı bütün pul yatırımları eyni qaydada edilir (konsistentlik üçün).
