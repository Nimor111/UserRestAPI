# UserRestAPI
Provides crud operations on a user model.

## Routes
* GET all users from the database.
```
/api/users/get
```
* GET a user by `email`
```
/api/users/{email}
```
* POST create a new user (accepts JSON and XML)
```
/api/users/create
```
* PUT update a user (finds it by email)
```
/api/users/update
```
* DELETE remove a user by email from the database
```
/api/users/delete/{email}
```
## Notes
Passwords are initially null. 
A user's role can be `User` or `Administrator`. An administrator account cannot be updated ( some other type of authentication is applied maybe? ). 
User passwords are set by an administrator.

## Front-end
The front-end for this application is written with OpenUI5.
[Link](https://github.com/Nimor111/User-frontend)
