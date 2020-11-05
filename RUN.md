# Action monitor - how to run the app

[Back to main Readme.md](README.md)

## Starting the application with Docker

Just execute `start.sh` at the root of the project.
It will execute the `docker-compose.yml` file at the same place than the script.
 
4 images will be built or pulled and started :
- 2 instances of **action-monitor** app
- an **ActiveMq** message broker
- a **MySQL** database

### Manage containers services

1. **action-monitor** apps :
    - ``localhost:8081 & 8082`` : redirect on index.html, small UI to join a room and chat
    - ``localhost:808x/action-monitor.html`` : monitor database insertions
    
2. **ActiveMq** message broker : 
    - ``localhost:8161`` : admin UI
    
3. **MySQL** database : 
    - use a chrome light Mysql client app ([chrome-mysql-admin](https://chrome.google.com/webstore/detail/chrome-mysql-admin/ndgnpnpakfcdjmpgmcaknimfgcldechn) for example) or 
    - use command line :
```
docker exec -t -i <mysql_container_name> /bin/bash
mysql -u root -p
-> no password
```

Useful commands :
```
mysql> show databases;
mysql> use chat; -> select 'chat' database
mysql> show tables;
mysql> SELECT * FROM user;
mysql> SELECT * FROM message;
```
    
## Local - Development

To start your application in the dev profile, run:

```
./mvnw
```

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

You can reach **action-monitor app** on : ``localhost:8081``  
You can reach **H2 database** admin UI (login : 'chat', no password) : ``localhost:8081/h2-console``  
To change **message broker** url, go to **application-dev.yml** file and change this property : ``spring.activemq.broker-url``

## Building for production

### Packaging as jar

To build the final jar and optimize the chat application for production, run:

```
./mvnw -Pprod clean verify
```

To ensure everything worked, run:

```
java -jar target/*.jar
```

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mysql database in a docker container, run:

```
docker-compose -f src/main/docker/mysql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/mysql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod verify jib:dockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 6.10.1 archive]: https://www.jhipster.tech/documentation-archive/v6.10.1
[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v6.10.1/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v6.10.1/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v6.10.1/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v6.10.1/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v6.10.1/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v6.10.1/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v6.10.1/setting-up-ci/
