# Action monitor - coding task

[Back to main Readme.md](README.md)

Write a program “action-monitor” satisfying these requirements:

1. The program must be written in Java using the following libraries/frameworks/containers if needed:
- Maven 3
- Tomcat
- Spring (boot, core, mvc, integration)
- SL4j + LogBack (for logging purposes)
- A lightweight database
- WebSockets
- ActiveMQ
- EasyMock, Mockito or other

2. The program should allow two user to communicate between themselves pushing the messages to the destination user. All the messages
should be stored in a database.

## Stretch goals
3. The program should expose 2 rest endpoints returning:
- status of the application
- version of the application

4. Please submit your source code along with:
A README document detailing how to build and deploy the code. The readme file should also contain a brief note about your unit testing
strategy: classical, mockist or both?

A 1 page document with a small diagram explaining the architecture you followed
action-monitor proof of concept
- ReadMe.txt
- ImplementationArchitecture.pdf
- action-monitor (containing maven structure + pom file)

5. Please provide us your code using a Git online service.
The following points will also affect your score:
- We need to be able to easily build and run the program.
- Make it easier for us to exercise the system (interacting with the database you choose inserting/ updating rows).
- We will be looking at the code and tests and assessing style, implementation and test coverage.
