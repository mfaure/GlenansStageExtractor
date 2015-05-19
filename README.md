# GlenansStageExtractor
Extract stages from Glenans (sailing school) website

## Prerequisites

* Linux host (tested on Ubuntu 14.04)
* Maven
* Firefox 
* XVFB

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
* Lister tous les stages P à Vannes : http://localhost:8080/stagesP?city=Vannes (respecter la casse)
* Lister tous les stages P à Vannes ou Paimpol: http://localhost:8080/stagesP?city=Vannes,Paimpol (respecter la casse)
* Lister tous les stages P à Vannes ou Paimpol de 5 jours : http://localhost:8080/stagesP?city=Vannes,Paimpol&duration=5 (respecter la casse)

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

* DONE récupérer aussi l'URL du stage
* DONE implémenter dates sous forme de date (et non string) pour pouvoir faire les requêtes par date de début / fin ou durée
* ajouter dans les résultats la date d'extraction et le nombre de résultats (ainsi les résultats de requêtes ne seront jamais vide)
* implémenter durée du stage
* implémenter appli cliente (Angular ? autre utils pour faire des filtres/tris facilement ?)


