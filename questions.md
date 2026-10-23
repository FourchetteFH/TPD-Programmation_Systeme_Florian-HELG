Après implémentation, répondez à ces questions :

Performance : Quelle version est la plus rapide ? Pourquoi ?
- Attendu : Version parallèle plus rapide grâce à l’utilisation de plusieurs cœurs CPU
- Oui mais : Actuelement les calcules prenent de base peu de temps, et la gestion des threads rajoute du temps
- Au final la nouvelle version peu prendre un peu plus de temps que prévue, mais dans une situation ou les calcules prennent beaucoup de temps -> c’est largement mieux.
- Dans ce cas d’utilisation les ExecutorService et Callable sont à préviligier.
Résultats : Les résultats sont-ils identiques entre les versions ? Pourquoi n’y a-t-il pas de race condition ?
- Point clé : Chaque thread travaille sur des données différentes et l’agrégation se fait de manière séquentielle


Le plus rapide est la version séquentielle dans le cas actuel car les calculs prennent peu de temps. Les threads de calculParalleles se marchent sur les pieds au moment de la création et démarrage ce qui fait que le calcul séquentiel est plus rapide. 

Les 2 versions donnent le même résultat. IL n'y a pas de race condition car chaque thread travaille sur sa donnée et écrit dans sa donnée "resultat", l'addition dans sommeTotal se fait avec le thread principal. Donc au final la donnée n'est pas modifiée en meme temps.







// Partie Sémaphore : 
Ordre des acquire() : Que se passe-t-il si on inverse placesLibres.acquire() et mutexBuffer.acquire() ?


Si on inverse mutexBuffer.acquire() puis placesLibres.acuire() on risque un deadlock, si le buffer est plein un producteur prend le mutex et attend une place libre en le gardant mais pour libérer une place un consommateur a aussi besoin du mutex donc si la place est réservée et qu'on prend le mutex après ça évite ce problème là.


Cohérence : Les compteurs de production/consommation s’équilibrent-ils ?


Dans le scénario équilibré les compteurs s'équilibrent vu que 2 prod * 15 = 30 et 3 * 10 = 30. 
Dans le scénario non équilibré 5 * 10 = 50 prod mais 2 * 10 = 20 conso possibles donc c'est pas équilibré. 

D'après le test que j'ai fais : le buffer ne dépasse jamais 2 éléments et les consommateurs suivent sans soucis pour la partie équilibrée. Mais le buffer monte à 5 et reste bloqué entre 4 et 5 pendant l'exécution. L'écart est de 19 - 14 = 5 soit la taille du buffer.



Blocages : Dans quelles conditions le système peut-il se bloquer ?


Dans le cas de la question 1, si les les consommateurs attendent plus que ce qui est produit et dans le cas où il y a plus de productions que ce que peuvent consommer les consommateurs, d'où le .exit(0) dans le test. 