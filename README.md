
# Mini Bank Backend

A simple banking backend built using **Spring Boot**, **Spring Security**, and **JWT**, with **PostgreSQL** as the database. The project supports containerized deployment using **Docker** and **Docker Compose**.




## Technologies Used

**Client:** React, Zustand, TailwindCSS, react-router, shadcn/ui, react-hot-toast, lucide-react

**Server:** Java, Spring Boot, PostgreSQL, Docker, Docker Compose


## Requirements

You need to install Docker, Docker Compose and run the Docker engine in order to run the minibank app. https://www.docker.com/
## Running

In order to run the minibank backend app:

Clone the repository:
```bash
  git clone https://github.com/alpturan-dev/minibank-backend.git
```
Change directory
```bash
  cd minibank-backend
  cd minibank
```
Docker compose build
```bash
  docker compose build minibank-app
```
Docker compose up
```bash
  docker compose up
```
Done, now you can navigate to the swagger ui to test the endpoints:

http://localhost:8080/swagger-ui/index.html#/

### Notes: 
- You can reach the api/users/signin and api/users/signup endpoints with a JWT token. In order to access the protected routes, you should click the Authorize button and pass the token in it.


## Writers

- [@alpturandev](https://www.github.com/alpturandev) for design and development.

  

  