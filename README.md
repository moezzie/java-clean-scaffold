# Java Clean Scaffold

This project is a starting point for any kind of RESTful API.
It's based on (my interpretation of) the principles of Robert C. Martins Clean Architecture, including borrowing a couple of concepts from DDD.

The main though behind this kind of architecture is to separate the domain and application logic from the infrastructure and presentation layer (API).
We want to structure our application in a way that components like the database or web framework are interchangeable.
It should work just as well with a MySQL or Postgres database as with files stored on disk. And the changes required to switch between these data stores 
should be minimal.
Switching from for example MySQL to Postgres or files stored on disk should be a trivial task.

The domain and application layer which holds all the business logic should be able to work all on its own with no outside dependencies, except for the standard library.

This project is meant to be a starting point for future projects in order to quickly get up and running.

### Getting started
The quickest way to start the application is using docker-compose.  
This starts the application including a Postgres database.

#### Configuration
The application uses a .env file to pass configurations to the docker containers and the application inside.  
If you would like configure anything, create a .env file from the env-sample file.
```shell
# Create an .env-file
cp env-sample .env
```

Change any values you like in the .env-file.


#### Start application in production mode (uses default config values)
```shell
docker-compose up
```





### Structure
```
Root
|--domain
|  |--entity
|  |--valueobject
|  |--exception
|
|--application
|  |--usecase
|
|--infrastructure
|  |--dto
|  |--repository
|
|--api
   |--configuration
   |--controller
```

## Roadmap
- [x] Clean architecture
- [x] RESTful API
- [x] Connect to Postgres
- [x] Authentication
- [x] JWT
- [x] Dockerfile