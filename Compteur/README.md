# TP COMPTEUR

## Noms des binômes

|  **Nom**  |  **Prénom**  |    
|-----------|--------------|
| GILLES    | Charles      |
| DICKO     | Seydou Salia |

## Compilation

ce placer dans le dossier **Compteur** puis éxécutez la commande suivante:  
`$mvn package`  
Les tests seront éxécutés directement, le fichier Compteur-1-SNAPSHOT.jar sera généré, il est égal au fichier Compteur.jar fourni

## Execution des projets

Compteur Séquentiel:  
`$java -cp Compteur.jar car.Compteur nomDuFichier`  
  
Compteur MultiThread:  
`$java -cp Compteur.jar car.CompteurMultiThread nombreDeThreads nomDuFichier`  
  
Serveur:  
`$java -cp Compteur.jar car.Server nombreDeClients nombreDeThreadsParClient`  
  
Client :  
`$java -cp Compteur.jar car.Client [nomDuFichier]`  
Le nom du fichier est requis si vous souhaitez analyser un fichier.  
Sinon vous devrez écrire les lignes une par une. Dans ce cas, pour mettre fin au flux, il faut écrire **:::END:::**  
Le serveur traitera ensuite les lignes envoyées


