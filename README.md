
# Mini Bank Backend

A simple banking backend built using **Spring Boot**, **Spring Security**, and **JWT**, with **PostgreSQL** as the database. The project supports containerized deployment using **Docker** and **Docker Compose**.




## Kullanılan Teknolojiler

**Client:** React, Zustand, TailwindCSS, react-router, shadcn/ui, react-hot-toast, lucide-react

**Server:** Java, Spring Boot, PostgreSQL, Docker, Docker Compose


## Needs

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
```
Docker compose build
```bash
  docker compose build minibank-app
```
Docker compose up
```bash
  docker compose up
```
Done, now you can navigate to the swagger ui:

http://localhost:8080/swagger-ui/index.html#/

  