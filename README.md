scala-backend-sampling
======================

This is sample rest application using Scala, PlayFramework with Postgres.

- Application is using Slick FRM for connecting to DB.

- in application is also example of usage of Akka Actors for ActionLog

- in application is implemented example of deep validation where all errors, warning and info messages are collected and presented i.e. to user at same time.

- all rest responses are unified and response has same structure every time so it is easier to handle errors, warning and information messages and also it is easier to handle specific data on pages.
Response is structured to have GLOBAL and LOCAL messages. Local messages are messages that are coupled to some field i.e. = "username is too log. Allowed length is 80 chars". Global messages are messages that are reflecting state of whole data on page, i.e. "User will not be active untill is approwed". Local and Global messages are having three levels: ERROR, WARNING and INFORMATION.
example response: http://demo3385913.mockable.io/api/v1/single-create-response

- JSON Web Tokens (JWT) is used for user identification and authentication

- application is divided into modules i.e. user module, organization-structure module etc.



