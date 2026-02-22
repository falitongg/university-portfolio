## [HW01](https://cw.fel.cvut.cz/b251/courses/b6b36omo/hw/01)
Write a class Homework1 that has the following (non-private) methods:
```java
boolean f() — always returns true
static boolean g() — always returns false
int h() — always returns the number of times method h has been called on this instance (including the current call, i.e. the first call to h on a given instance returns 1)
int i() — always returns the number of times method i has been called on any instance of class Homework1 (including the current call)
```

## [HW02](https://cw.fel.cvut.cz/b251/courses/b6b36omo/hw/02)
#### This homework assignment covers the following topics:
UML, Refactoring, the Adapter design pattern, Template Method, and a brief review of Git and working with null values.

#### Assignment
Your company is implementing a system that allows payments to be made using a payment gateway. For a long time, the system worked with the old interface provided by the payment gateway. The payment gateway provider has released a new version that your system should start supporting. At the same time, a relatively large part of your solution depends on the original version.

Your colleague has been tasked with extending the existing system to support the new payment gateway. Your colleague has not taken the OMO course, so you can see the result in the Merge Request they have prepared for you to review:

https://gitlab.fel.cvut.cz/B241_B6B36OMO/hw02_internal/-/merge_requests/1

Download the code directly from the feature/modern-payment-system branch.

## [HW03](https://cw.fel.cvut.cz/b251/courses/b6b36omo/hw/03)
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
## [HW04](https://cw.fel.cvut.cz/b251/courses/b6b36omo/hw/04)

#### This homework assignment will cover the following topics:
functional programming, lambda functions, map reduce, higher-order functions, and promises in Java.

#### Introduction
In this assignment, we will focus on the Czech presidential election. Specifically, we will be interested in its results and the interesting statistics that can be derived from them. Your task will be to take the open data from the Czech Statistical Office and extract interesting statistics from it.
#### Assignment
Download the template for Homework 4. Here you will find four data sets (Round 1 2018, Round 2 2018, Round 1 2023, and Round 2 2023) of Czech presidential election results. You will also find the completed data set loading, parsing, and mapping to the prepared object model and class templates for calculating statistics. Your task is to complete all methods that contain only the TODO Homework comment instead of the actual implementation.

Do not modify any classes other than those containing the TODO Homework comment.
The purpose of all methods should be fairly clear from their names; however, you can find additional information about each of them and how exactly they should work in the interface of the given classes.

To help you continuously check the correctness of your solution, you will find several unit tests here. These are based on the second round of the 2023 elections. You can also use the Main class, where you will find several statistics reports. You can then compare them with the raw data or just enjoy your statistics.

To make it a little more challenging and to really test your knowledge of functional elements in Java, the use of loops and conditional blocks is prohibited throughout the entire task! It is desirable to replace their use with StreamAPI, lambda functions, method references, Optional, and higher-order functions. Furthermore, a maximum of 5 auxiliary variables can be declared in the entire code. Auxiliary private methods are not prohibited.

