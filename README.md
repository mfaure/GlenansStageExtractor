# GlenansStageExtractor
Extract stages from Glenans (sailing school) website

## Lancement Méthode 1

* aller dans le dossier <projet> 
* mvn clean build
* aller dans le dossier <projet>/target/
* java -jar stage-extractor-1.0-SNAPSHOT.jar

## Lancement Méthode 2

* aller dans le dossier <projet>
* mvn spring-boot:run

## Utilisation

* Initialisation : Appeler http://localhost:8080/launchExtraction
* Lister tous les stages P : http://localhost:8080/stagesP

http://localhost:8080/stagesP?city=Vannes,Paimpol

## Ressources

* [Tutorial SpringBoot](http://spring.io/guides/gs/rest-service/)

## Notes

* Vérifier si besoin de xhost + pour Firefox (a priori oui)

## Cahier des charges

* Pouvoir faire des recherches sur les stages avec plus de facilités que sur le site Glénans actuel:
** pouvoir cocher des cases parmi : niveau, site, durée en jours, date de début
** conserver la charte graphique actuelle Glénans
** si possible améliorer les erreurs de contraste
* Pouvoir exporter les résultats de recherche sous forme de calendrier .ics (export fichier + URL ics)

## TODO

* implémenter dates sous forme de date (et non string) pour pouvoir faire les requêtes par date de début / fin ou durée
* implémenter durée du stage
* implémenter appli cliente (Angular ? autre utils pour faire des filtres/tris facilement ?)


