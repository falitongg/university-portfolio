# Task 7 - Showroom

Your task is to create a simple database (hereinafter referred to as db) of cars. Use a linked list for implementation. The car database interface has a function for creating it from a predefined list (list type), a function for inserting a car into the db, a function for editing the name of an already inserted car, a function for editing the brand of an already inserted car, and a function for changing the status of an already inserted car. For simplicity, it is not possible to change the price of a car. The interface also has a function that can calculate the total value of cars in the showroom. The interface also has a function that returns the header of the linked list, the linked list itself, and a function that clears the database. More information about the functions is provided below.

The following restrictions apply to the database: No IDs appear more than once in the database. Records in the database are always sorted by price from lowest to highest in the order in which they were entered.

The following restrictions apply to the linked list: Each node sees its ancestor and its successor. All data about a specific car is stored in each node. 
