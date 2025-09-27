# Account Task — Business Logic Explanation

Bu layihə sadə bir bank sisteminin nümunəsidir. Məntiq olaraq üç əsas domen üzərində qurulub:

- **User** — istifadəçilərin saxlanması və idarə olunması (CRUD əməliyyatları).
- **Account** — hər istifadəçiyə aid balans hesabları. Burada `deposit`, `withdraw`, `transfer` əməliyyatları aparılır.
- **TransactionLog** — bütün maliyyə əməliyyatlarının tarixçəsi. Hansı hesabdan, hansı hesaba, hansı məbləğdə əməliyyat aparılıb və əməliyyatdan sonra balanslar necə dəyişib — hamısı burada saxlanılır.

## Əsas Məntiq

- **User**
  - Sadə CRUD əməliyyatları.
  - **Soft delete** tətbiq olunur: silinən istifadəçilərin statusu `DELETED` olur, amma DB-dən tam silinmir.

- **Account**
  - Hesab yaradıldıqda balans **0** olur və status `ACTIVE` təyin edilir.
  - **Deposit** → hesabın balansı artır, eyni anda TransactionLog-a qeyd düşür.
  - **Withdraw** → balans kifayət qədərdirsə, məbləğ çıxılır və log yazılır.
  - **Transfer** → bir hesabdan digərinə məbləğ köçürülür, hər iki balans yenilənir və əməliyyat loglanır.
  - **UpdateOwner** → hesab başqa user-ə verilə bilər.
  - **Delete** → soft delete (status=DELETED).

- **TransactionLog**
  - Əl ilə yazılmır, yalnız Account əməliyyatları zamanı avtomatik yaranır.
  - Əməliyyatdan sonra həm “from”, həm də “to” balansları qeyd olunur.
  - Bu sayədə hər bir əməliyyatın izini sonradan görmək mümkündür.

## Niyə belə dizayn?
- **Relations istifadə olunmur**: müəllimin tələbi ilə `@ManyToOne` və s. əvəzinə sadə `Long userId`, `Long fromAccountId` saxlanılır.
- **Soft delete**: həm User, həm də Account tam silinmir, status dəyişir. Beləliklə tarixçə itmədən qalır.
- **TransactionLog**: bütün maliyyə əməliyyatları mütləq loglanır → audit və izləmə təmin olunur.
- **Validation**: sadə format yoxlamaları (məs.: email, yaş, məbləğ > 0) DTO səviyyəsində, biznes qaydaları isə service səviyyəsindədir (məs.: kifayət qədər balans yoxlanışı).

---

## Qısa Nəticə
Layihənin məqsədi sadə bank əməliyyatlarını (deposit, withdraw, transfer) icra etmək və bütün addımları tarixçə ilə izləməkdir.  
Ən vacib məqamlar:
- Soft delete ilə məlumat itməsinin qarşısını almaq,
- TransactionLog ilə şəffaf audit təmin etmək,
- Relations olmadan sadə ID-lərlə işləmək.
