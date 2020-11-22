# TP MiniChat

## Noms des binômes

- Charles GILLES
- Seydou Salia DICKO

## Compilation

ce placer dans le dossier **MiniChat/Server** puis éxécutez la commande suivante:  
`$mvn package`

ce placer dans le dossier **MiniChat/Client** puis éxécutez la commande suivante:  
`$mvn package`

## Problemes racontrer

-Impossible d'envoyer une instance du client à travers le reseau : probleme de serialisation de l'object.

## Execution des projets

pour le serveur, éxécutez la commande suivante:  
`$java -cp target/Server-1.0-SNAPSHOT.jar server.Server`

pour le client :  
`java -cp target/Client-1.0-SNAPSHOT.jar client.Client`

## Actions

-Identifiants valides login ::: password

	seydou ::: dicko
	charles ::: gilles

Les actions à accomplir sont:

-Entrer un pseudo qui vas etre affichee pour les autres utilisateurs 

-Entrer son login  

-Entrer son password

Apres connection vous pourrez :
 
-Envoyer un message a tout le monde en saisissant juste le message

-Envoyer à une personne en ecrivant d'abord le nom du destinataire separer du message par ` : `
