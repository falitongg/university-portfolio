## [HW03](https://cw.fel.cvut.cz/b251/courses/b6b36omo/hw/03)
For clarity: it is my code that is located in the hw folder.
#### This homework assignment covers the following topics:
UML, Facade and Strategy design patterns, and proper application design.

#### Assignment
You have been given an application that follows a multi-layered architecture. It is relatively simple, as shown in the UML diagram below:

<img width="608" height="635" alt="image" src="https://github.com/user-attachments/assets/ba70c278-bfc8-439a-96fb-cd706e3f19a5" />

Package distribution:
 - model - data objects
 - repository - CRUD operations for the model package
 - services - application logic
   -  strategy - strategy utilization
 - facade - facade for the central point of the application
   -  dto - transformed object - for example, for FE
The diagram shows that this is not a clean solution, but the application is ready for migration to a microservice architecture. Therefore, entities do not contain relationships to other entities (with the exception of Address). This is a division into three domains: Book, Author, and Library.

Improve data aggregation. This is a very common problem in projects. You can see that some methods in the project are extensive and confusing. There is a lot of logic in one place. It is difficult to debug. Extensions are very demanding. Such methods need to be decomposed. It is very useful to apply established rules here.

Use the facade design pattern to separate code logic from connecting data into more complex entities.
