# Elevator-System
Symulator obsługi wind, który ma za zadanie przypominać jak najberdziej rzeczywisty problem.

### Założenia:
Czas postoju windy na danym piętrze wynosi N = 3 (wartość ustalona przez użytkownika) iteracji tzn. jest to równe przejazdowi windy przez trzy kolejne piętra bez zatrzymania. Objaśniając, mamy 2 windy i znajdują się one w następującej sytuacji:
- Winda nr 1 -> aktualnie 1 piętro i stoi w miejscu
- Winda nr 2 -> aktualnie 3 piętro i jedzie do góry na 5 piętro
- Dostajemy wezwanie z 6 piętra do góry

Która winda zrealizuje to żądanie? Mogłoby się wydawać, że winda nr 2 ponieważ znajduje się bliżej. Warto mieć jednak na uwadze, iż ma ona jeszcze po drodze postój. W związku z tym mój algorytm wybierze winde nr 1 ponieważ:

- Winda nr 1 -> t = 5 pięć pięter do pokonania bez zatrzymania
- Winda nr 2 -> t = 6 (3 + 3) trzy piętra do pokonania i trzy iteracje postoju

### Algorytm:
Dobór windy uwzględniając powyższe założenia działa w następujący sposób:
1. Sprawdzamy czy istnieje winda bezczynna lub winda która dane żądanie będzie miałą po drodze, tzn. bez zmiany kierunku jazdy.
2. Dopasowujemy windę, która zrealizuje to żądanie najszybciej zgodnie z założeniami.
3. Jeśli taka winda nie jest dostępna to szukamy windy, która najszybciej będzie dostępna.

Warto dodać, iż punkt nr 3 jest realizowany w sytuacji kiedy winda musi zmienić swój kierunek jazdy. W pierwszej chwili może się to wydawać niepotrzebne, jednak ustaliłem taką strategie ponieważ jest to optymalne rozwiązanie w sytuacji gdy jakieś żądanie pojawi się później i będziemy mogli zrealizować je "po drodze". Założenie z N = 3 zostało przyjęte również po to aby zbyt dużo wind nie stało bezczynnych. Logicznie jest to sensowne ponieważ pozwoliłoby to uniknąć nam przepełniania windy przez ludzi.

### Jak uruchomić projekt?

### Walidacja:
