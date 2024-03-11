## Introduction

This is a simple project that hava 2 screens, login and home screen. There are 2 type of user, first Admin who can see list of users, and the other one is normal user who can see album list on their home.


## Features

Login :
The login feature has two field (username & password) that must be filled by user. The application will find the user from local database (Room)/ If user's found, it will redirect to home.

Signup :
It used to register new user into local database (Room). It need 4 attributes to filled by user (username, password, email, role)

Album List :
The feature is only visible for normal user. It used paging 3 that will auto refresh while user reach the last item of the lists.

User List :
The feature is only visible for admin user. Admin could update or even delete the user that shown on the list. It required password when admin want to delete user.


## Architecture

There are two modules beside the main app that should maintained (data & domain). 

Data:
This module will handle everything about data such as model, database, dao, repository, constant value, etc. This is the lowest layer that will imported to another module.

Domain:
This modul will handle business usecase and utils that will used to build our feature. Usecase will handle logic to get data from data layer and pass it to presentation layer which is 'app' in this case.

App:
App is the main module that will handle the UI presentation. It will get the data by calling the usecase in domain layer.
