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
- [ ] MySQL implementation
- [ ] Authentication
- [ ] JWT
- [ ] Dockerfile