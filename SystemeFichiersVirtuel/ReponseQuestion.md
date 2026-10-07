Question 1 : 

C'est important ca comme dit en cours le tableau d'octets ne sait pas ce qu'il contient donc il faut une regle pour chacun pour savoir dans quel ordre lire les octets sinon les valeurs sont différentes dans un sens et dans l'autre. Par exemple 512 deviens 131072 lu à l'envers en binaire. 

Question 2 : 

C'est important parce qu'un byte peut être négatif, je suppose que c'est pareil en C mais en Java si on met pas le & 0xFF un octet comme F0 peut polluer les autres octets quand on les assemble le "&" garde seulement les 8 bits du bas donc ça évite de polluer.

Question 3 : 

C'est simplement car écrire et relire peut marcher même avec un mauvais format, donc s'il y a une erreur d'un côté elle s'annule de l'autre donc il faut garder les octets réels donc pour choisir x prends des cas limites de 0 et -1 avec le poids fort à 1. (il faut vérifier les octets dans le tableau) 

Question 4 : 

Parce qu'un bit par bloc prends moins de place qu'un int par bloc, la taille comme dit dans le TP c'est N/8 octets donc 256 octets pour 2048 blocs, il y a aussi 1 int par bloc qui ferait 4N octets donc 8192. 

Question 5 : 

Les structures du système de fichiers doivent occuper des zones mémoire déterministes car chaque structure est retrouvé à une adresse fixe, si le bitmap bouge sans que le code suive, on écrit ses bits au mauvais endroit et je suppose que ça risque de corrompre ou de devenir illisible. 

Question 6 : 

L'indode c'est la fiche(de taille fixe; adresse = table + n x 128) qui dit où sont les données, donc il est calculable, il peut changer ou rester sans pour autant toucher au contenu, qui lui peut être décalé de plusieurs blocs. En gros les métadonnées changent mais pas les données. 

Question 7 : 

Il se passerait qu'ils se feraient écraser, les blocs de 0 a 128 contiennent la structure du système. Vu que moi ils sont pas marqués dans lel bitmap commencer à 0 écraserai le superbloc, puis le bitmap, puis les inodes.

Question 8 : 

Il est possible que les blocs soient groupés et d'autres éparpillés, on va dire que les blocs L sont libre et les blocs O sont occupés. 

Exemple 1 : LLLLOOOO
Exemple 2 : LOLOLOLO

Dans les 2 ils y a le même nombre de bloc libre et blocs occupés mais placé de manières différentes. 
Dans le 1, les blocs libres sont à côté l'un l'autre, un fichier de 4 blocs rentre en un seul morceau alors que dans le 2 les blocs sont isolés l'un l'autre, le système est plus fragmenté même si le nombre de blocs est le même. 

