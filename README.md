# Hotel

## Authors

- [Krzysztof Pala](https://github.com/pallovsky)
- [Tomasz Wojakowski](https://github.com/Wojaqqq)
- [Bartosz Nieroda](https://github.com/qymaensheel)


## Content
This is a template application of a decision based game 'Hotel'. It allows to create games for chosen participants and manage them. The participants may take part in turn-based game, where the result is determined by their decisions. Currently only visual logic is implemented, as business logic of this game is planned to be added in the future.

Initial set of users is defined in file 'data.sql'. It consist of:
* admin user with password admin
* user1, user2, user3, user4 users wuth password: password1, password2 etc.

## How to run
### Frontend

Project is currently run on angular development server. It requires:
* node v14.15.5, npm v8.19.3 (can be installed simultaneously with [node version manager](https://github.com/nvm-sh/nvm))
* [ng](https://www.npmjs.com/package/@angular/cli) (angular client) v13.3.2

1. Enter frontend directory:

`cd frontend/hotel`

2. Install dependencies:

`npm install`

3. Run development server.

`ng serve`

4. Frontend app is available on `localhost:4200`.

### Backend
Project is run with maven spring-boot plugin. It requires"
* JDK 17
* mvn

1. Enter backend directory.

`cd backend`

2. Execute spring-boot run command.

`mvn spring-boot:run`

3. Application is available at `localhost:8080`. CORS request from port 4200 are currently allowed.
