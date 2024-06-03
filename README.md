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

### Algorytm doboru windy:
Dobór windy uwzględniając powyższe założenia działa w następujący sposób:
1. Sprawdzamy czy istnieje winda bezczynna lub winda która dane żądanie będzie miałą po drodze, tzn. bez zmiany kierunku jazdy.
2. Dopasowujemy windę, która zrealizuje to żądanie najszybciej zgodnie z założeniami (droga docelowa i postoje).
3. Jeśli taka winda nie jest dostępna to szukamy windy, która najszybciej będzie dostępna (droga docelowa i postoje w 2 strony).

W najgorszym przypadku (punkt nr 3) winda musi zmienić kierunek, czyli zrealizować wszystkie żądania w aktualnym kierunku jazdy i dopiero wtedy zawrócić.

### Co w sytuacji kiedy windą jedzie więcej osób i mają różne piętra docelowe?
Pasażer w każdej chwili może nacisnąć przycisk w windzie. W sytuacji kiedy piętra te są różne, algorytm najpierw zrealizuje te które są najbliżej zgodnie z aktualnym kierunkiem jazdy. Przykładowo winda znajduje się na piętrze 8 i jedzie w dół. Osoba nr 1 naciśnie piętro 3, a osoba nr 2 naciśnie piętro 10. W takiej sytuacji winda zrealizuje najpierw wszystkie żądania, które są po drodze jadąc na dół i dopiero wtedy kiedy się zakończą, winda zacznie jechać do góry.

### Jak uruchomić projekt?
Sklonuj zawartość repozytorium:

1. Z IDE - uruchom plik main (możliwa symulacja według własnych potrzeb)
2. Za pomocą pliku jar - przejdź do katalogu ./out/artifacts/Elevator_System_jar i uruchom komende:
```bash
java -jar Elevator-System.jar
```

### Walidacja:
- przyciski ze strzałką w górę i w dół na każdym piętrze
- przyciski z piętrami dla każdej z wind
- ilość pięter i wind

Program sprawdza czy każdy z przycisków jest już aktualnie wciśnięty. Kiedy winda dojedzie do danego piętra automatycznie aktualizuje jego stan.
