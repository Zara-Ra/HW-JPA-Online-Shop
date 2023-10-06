# HW-JPA-Online-Shop
## Online Shop Console Application
## Overview
The Online Shop Console Application is a versatile shopping platform featuring three distinct categories of items: Electronics, Shoes, and Readables. Users can create an account, sign in, and perform various shopping-related actions, making it a comprehensive solution for online shoppers. Here's a brief overview of the application's key features:

## Features
### User Account Management
- Sign Up: Users can create an account by providing essential information.

- Sign In: Registered users can sign in to access their accounts and start shopping.

### Shopping Cart Management
- Add Items: Users can add items to their shopping cart, specifying the quantity they desire.

- Delete Items: Items can be removed from the shopping cart if no longer needed.

- View Cart: Users can print the contents of their shopping cart, along with the quantity of each item.

- Total Cost: The application calculates and displays the total cost of all items in the shopping cart.

### Finalizing the Shopping Experience
- Checkout: Users can finalize their shopping experience, updating the stock quantity of remaining items in the shop accordingly.
### Data Persistence with JPA 
- The application leverages JPA (Java Persistence API) to connect to the underlying DBMS (PostgreSQL), ensuring data consistency and reliability.
### Data Management with Hashmap
- Shopping cart data is managed using a Hashmap, facilitating efficient item addition and removal operations.
### Data Validation and Exception Handling
- User-entered data is thoroughly validated to ensure accuracy and data integrity.

- The application includes robust exception handling to gracefully manage errors and unexpected situations.
