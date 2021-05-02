Bare metal Java

Java, a tak naprawdę JVM, cieszy się zasłużoną sławą platformy nieprzyjaznej dla sprzętu, a co za tym idzie, nikt poważny (oprócz Apache Kafka, Elastic, Cassandra i Neo4j) nie będzie
budował baz danych, kolejek i innych rozwiązań gdzie "mechanical sympathy" jest kluczowe.
Garbage collector, spekulujący JIT, brak kontroli nad "object layout", fatalne wsparcie (głównie przez brak abstrakcji) dla funkcji nowoczesnych procesorów i systemów operacyjnych.
I do tego jeszcze JNI (kto tam był to wiem o czym mówię). (O implementacji IO nawet przez szacunek do samych siebie, nie wspominam)

Celem tej prezentacji jest odczarowanie tych mitów i pokazanie jak wiele wysiłku ostatnio idzie ostatnio w uczynienie JVM bardziej przyjaznego dla sprzętu.
Będzie o API których są jeszcze w fazie inkubacji (choć niektóre jeszcze nie dojrzały do tego, a niektóre są już stabilne). API których, być może nigdy w życiu nie zobaczycie, a które 
będą w przyszłości stanowić fundament baz danych, czy kolejek. 

Zaczniemy od Unsafe, by nadać rys historyczny. By potem pokazać, jak kolejne API zastępują kolejne metody w Unsafe:

* foreign memory access, czyli jak bezpiecznie pracować z pamięcią poza stertą,
* var handles, czyli jak okiełznać memory consitency i cache coherence,
* vector api, by wycisnąć siódme poty z instrukcji SIMD,
* projekt Panama i nowe foreign functions interface (czyli foreign linker), czyli jak wywołać C z Java ( i w drugą stronę )
* frozen arrays, i całą seria optymalizacji, która za tym idzie
* primitive objects (i projekt Valhalla)

Wszystko polane sosem niedziałających przykładów i przekłamanych micro benchmarków i OpenJDK kompilowanego na pięć minut przed prezentacją.

