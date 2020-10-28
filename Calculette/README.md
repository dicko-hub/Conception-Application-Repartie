# TP CALCULETTE

## Noms des binômes

- Charles GILLES
- charles.gilles.etu@univ-lille.fr
- Seydou Salia DICKO
- seydousalia.dicko.etu@univ-lille.fr

## Compilation

ce placer dans le dossier **Calculette** puis éxécutez la commande suivante:  
`$mvn package`

## Execution des projets

pour le serveur, éxécutez la commande suivante:  
`$java -jar Server/target/Server.jar`

pour le client :  
`$java -jar Server/target/Client.jar`

## Opérations

les opérations disponibles sont les suivante:
- +
- -
- *
- /

la syntaxe des opérations se fait comme ceci  
`nb1 operateur nb2`  
exemples:  
`1 + 1`  
`-1 * -6`  

Vous pouvez également appliquez une opération au résultat précédent, la syntaxe est la suivante  
`operateur nb`
exemples (le résultat de l'opération précédente est **5**):  
`* 5` => 25  
`/ 2` => 12  
`- -2` => 14  
`1 + 1` => 2
