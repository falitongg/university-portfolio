# Homework – Coworking Center Reservation System

A coworking center operates several branches and offers various types of spaces:
 - Workdesks (desk)

 - Meeting rooms

 - Training rooms

Each customer has an account in the system through which they create reservations. The system must track:

 - Customers

 - Their user accounts

 - Coworking branches

 - Individual spaces

 - Reservations

 - Payments for reservations

Each space:

 - Belongs to exactly one branch

 - Has a capacity (number of persons)

 - Has a space type (e.g. desk / meeting room / training room)

Reservations:

 - Are always created by one customer

 - Are always for one specific space

 - Have a start time and end time

 - Have a status (e.g. created, confirmed, cancelled, completed)

The system must allow:

 - One reservation to have multiple payments (e.g. deposit + remainder)

 - Customer Contact Details

The coworking center must have for each customer:

 - At least one email address

 - At least one phone number

If a customer changes their email or phone number, the system must retain a history of changes, including:

 - The date range during which each contact was valid (valid from / valid to)

 - Price List and Pricing

The price of a reservation is not fixed per space, but is determined by:

 - Space type

 - Branch

 - Time-based tariff (e.g. weekday, weekend, evening tariff)

 - The system must therefore maintain a price list that can be updated over time.

Additional Constraints:

 - One customer can have multiple active and historical reservations

 - Two valid reservations for the same space must not overlap in time

 - Assignment

Design a class diagram that includes:

 - The main entities of the system

 - Their attributes

 - Relationships between classes

 - Multiplicities

 - A model for the history of contact details
