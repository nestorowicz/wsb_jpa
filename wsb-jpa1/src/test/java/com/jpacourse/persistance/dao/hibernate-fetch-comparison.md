# Porównanie strategii ładowania danych w Hibernate

## Scenariusz 1 - FetchMode.SELECT
W pierwszym scenariuszu używamy `@Fetch(FetchMode.SELECT)` dla kolekcji visits, co skutkuje:
1. Wykonaniem trzech oddzielnych zapytań SQL:
    * Pierwsze zapytanie pobiera dane pacjenta
    * Drugie zapytanie pobiera wszystkie wizyty pacjenta
    * Trzecie zapytanie pobiera adres pacjenta
2. Strategia ta powoduje problem N+1 zapytań - dla każdego pacjenta będą wykonywane oddzielne zapytania dla jego wizyt.

```
Hibernate: select pe1_0.id,pe1_0.address_id,pe1_0.date_of_birth,pe1_0.date_of_passing,pe1_0.email,pe1_0.first_name,pe1_0.last_name,pe1_0.patient_number,pe1_0.telephone_number from patient pe1_0 where pe1_0.id=?
Hibernate: select v1_0.patient_id,v1_0.id,v1_0.description,v1_0.doctor_id,v1_0.time from visit v1_0 where v1_0.patient_id=?
Hibernate: select ae1_0.id,ae1_0.address_line1,ae1_0.address_line2,ae1_0.city,ae1_0.postal_code from address ae1_0 where ae1_0.id=?
```

## Scenariusz 2 - FetchMode.JOIN
W drugim scenariuszu używamy `@Fetch(FetchMode.JOIN)` dla kolekcji visits, co skutkuje:
1. Wykonaniem tylko dwóch zapytań SQL:
    * Pierwsze zapytanie pobiera dane pacjenta razem z wizytami za pomocą LEFT JOIN
    * Drugie zapytanie pobiera adres pacjenta
2. Strategia ta eliminuje problem N+1 zapytań dla wizyt, łącząc dane pacjenta i wizyty w jedno zapytanie.

```
Hibernate: select pe1_0.id,pe1_0.address_id,pe1_0.date_of_birth,pe1_0.date_of_passing,pe1_0.email,pe1_0.first_name,pe1_0.last_name,pe1_0.patient_number,pe1_0.telephone_number,v1_0.patient_id,v1_0.id,v1_0.description,v1_0.doctor_id,v1_0.time from patient pe1_0 left join visit v1_0 on pe1_0.id=v1_0.patient_id where pe1_0.id=?
Hibernate: select ae1_0.id,ae1_0.address_line1,ae1_0.address_line2,ae1_0.city,ae1_0.postal_code from address ae1_0 where ae1_0.id=?
```

## Wnioski

1. **Wydajność**:
    * Scenariusz 2 jest bardziej wydajny przy pobieraniu pojedynczego pacjenta, ponieważ redukuje liczbę zapytań do bazy danych.
    * Przy ładowaniu większej liczby pacjentów, JOIN może jednak zwracać zduplikowane dane pacjenta dla każdej wizyty, co zwiększa ilość przesyłanych danych.

2. **Pamięć**:
    * FetchMode.JOIN może prowadzić do większego zużycia pamięci przy dużej liczbie wizyt, ponieważ dane pacjenta są powtarzane dla każdego wiersza wyniku.

3. **Lazy loading adresu**:
    * W obu scenariuszach adres jest ładowany osobnym zapytaniem (fetch = FetchType.LAZY), co jest prawidłowe jeśli nie zawsze potrzebujemy danych adresowych.

4. **Optymalizacja**:
    * Dla relacji one-to-many z dużą liczbą elementów, warto rozważyć strategię FetchMode.SUBSELECT zamiast JOIN lub SELECT, co pozwoliłoby pobrać wszystkie wizyty dla wielu pacjentów w jednym zapytaniu.

5. **Zastosowanie**:
    * FetchMode.JOIN (scenariusz 2) jest lepszy dla przypadków, gdy prawie zawsze potrzebujemy zarówno pacjenta jak i jego wizyt.
    * FetchMode.SELECT (scenariusz 1) może być lepszy, gdy czasami potrzebujemy tylko danych pacjenta bez wizyt.
