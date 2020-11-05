# Action monitor

This microservice allow users to join a room and communicate between themselves. 
All the messages are stored in a database.

This application was generated using JHipster 6.10.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.10.1](https://www.jhipster.tech/documentation-archive/v6.10.1).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.

## Readme links

- [Technical task instructions](README-TASK.md)
- [How to run the app](RUN.md)

## Architecture

![architecture](/src/main/resources/static/img/architecture.png)

## Flow chart

![flowchart](/src/main/resources/static/img/flowchart.png)

## Database model

![db model](/src/main/resources/static/img/database-model.png)

## Use application

- Chat UI : ``localhost:8081``  
- Reach application : ``localhost:8081/action-monitor.html``  

This page displays database insertions :  
```
Action monitoring logs
Web socket connection established !
timestamp=2020-11-05T23:34:18.263570Z :: MessageEntity{id=21, content='message1', timestamp=2020-11-05T23:34:18.233240Z, roomId='lobby1', user=3} was inserted
timestamp=2020-11-05T23:34:49.292008Z :: UserEntity{id=12, name='david'} was inserted
timestamp=2020-11-05T23:34:51.495356Z :: MessageEntity{id=22, content='message2', timestamp=2020-11-05T23:34:51.476589Z, roomId='lobby1', user=12} was inserted
timestamp=2020-11-05T23:35:36.033295Z :: MessageEntity{id=23, content='message3', timestamp=2020-11-05T23:35:36.030936Z, roomId='lobby1', user=12} was inserted
STOMP error. Try to reload the page. Error = Whoops! Lost connection to http://localhost:8081/ws
```

- App status : ``localhost:8081/management/health``  
  
Output example :  
```json
{
"status": "UP"
}
```

- App version : ``localhost:8081/management/info``  
Warning, */info* works only on **prod** profile (use docker).  

Output example :
```json
{
	"display-ribbon-on-profiles": "dev",
	"git": {
		"commit": {
			"id": {
				"describe": "9ecba76-dirty",
				"abbrev": "9ecba76"
			}
		},
		"branch": "master"
	},
	"build": {
		"artifact": "chat",
		"name": "Chat",
		"time": "2020-11-05T22:13:14.588Z",
		"version": "1.0.0",
		"group": "fr.dla.chat"
	},
	"activeProfiles": [
		"prod",
		"swagger"
	]
}
```
  
- **MySQL database** is used when using docker-compose.  
- **H2 database** when running locally.  

More details to run/administrate the app here :
- [How to run the app](RUN.md)

## Testing strategy

**Unitary** tests and **integration** tests are used.  
Mocks are used with unitary tests.  
H2 database is used for integration tests.  

To check the test coverage, you can use Jacoco with this command line :  
```
./mvnw -Pprod clean verify
```

And check the result here : ``/target/jacoco/test/index.html``


[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v6.10.1/microservices-architecture/
