# Spring-Security-Project
Dokumentáció

Használati utmutató:
1. Szükséges kellékek: Java, JDK, Maven, MySql adatbázis.
2. A projekt forrás fájlai a MASTER ágra lettek felrakva.

Specifikációk
1. Felhasználó regisztrálása
2. Felhasználó verifikálása tokenen keresztül, amit akár újra is lehet küldeni.
3. Jelszó titkosítás az adatbázisban, Spring Security kivitelezés.
4. Felhasználói jogok kezelése, elérhető jogkörök: GUEST, USER, ADMIN.
5. GUEST: Olyan aki még nem verifikált, USER: Olyan aki már verifikált, ADMIN: Olyan aki tudja kezelni a bejegyzéseket
6. USER Felhasználói jogkörtől már blog posztolás, ADMIN jogkörtől pedig azok törlése, GUEST Felhasználó kizárólag csak posztok megtekintésére képes.
7. A projektről működés közbeni videó is készült: https://youtu.be/ZADg1LQjFow

Használt technológiák:
1. Spring Security, és túlzottan is sok függőség.
2. Java nyelv
3. HTML, Thymleaf mint frontend
4. MySql mint adatbázis
