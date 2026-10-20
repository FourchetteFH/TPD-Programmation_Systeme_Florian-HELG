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