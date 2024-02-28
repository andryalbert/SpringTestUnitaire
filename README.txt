* Pour le test:
- nécessaire pour assurer une livraison de logiciel fiable pour un client
- assurer le test d'un appel rest-api
- valider le bon fonctionnement de chaque algorithme du logiciel
* il y a trois type de test : test unitaire, test d'integration et test de performance
* on a deux types de libraire pour faire le test: JUnit et Mockito
* régle de nommage de test class: le nom de la classe test doit se terminer par Test
* dependance:
    // pour junit
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
    // pour mockito
        <dependency>
        	<groupId>org.mockito</groupId>
        	<artifactId>mockito-core</artifactId>
        	<scope>test</scope>
        </dependency>
* NB: chaque classe dans le package initiale à son propre classe test avec la même début de nom