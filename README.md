# Proyecto Obra Social - Grupo 6

## Descripción
Este proyecto, por el momento, consiste en una API RESTful desarrollada con Java y Quarkus para la gestión de turnos médicos, visualización de especialistas y  de recetas médicas para la empresa AlMedin.

El grupo se haya conformado por:
* [Nicolas Brites](https://github.com/NicoBrites)
* [Federico Acosta](https://github.com/fede-acos)
* [David Bernhardt](https://github.com/DavidBernhardt)

## Funcionalidades  
* **Autentificación y autorización**: Autoriza al usuario a acceder a las funcionalidades de la API, verificando sus credenciales (usuario y contraseña), y asignando un token de acceso.
- **Gestión de Turnos**: Permite crear, leer, actualizar y eliminar turnos médicos.
- **Cartilla de Especialistas**: Ofrece una vista de los especialistas con sus horarios, ubicación y áreas de especialización.
- **Descarga de recetas**: Los usuarios autorizados pueden acceder a sus recetas medicas.

## Dependencias
Para ejecutar este proyecto, necesitarás las siguientes dependencias:

- REST resources for Hibernate ORM with Panache ([guia](https://quarkus.io/guides/rest-data-panache)): Generate Jakarta REST resources for your Hibernate Panache entities and repositories
- REST ([guia](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- SmallRye OpenAPI ([guia](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- REST Jackson ([guia](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- SmallRye JWT ([guia](https://quarkus.io/guides/security-jwt)): Secure your applications with JSON Web Token
- JDBC Driver - MySQL ([guia](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC
- SmallRye JWT Build ([guia](https://quarkus.io/guides/security-jwt-build)): Create JSON Web Token with SmallRye JWT Build API

## Instalación y Ejecución
1. Instalar ([Docker](https://www.docker.com/get-started/)) y ([Quarkus CLI](https://es.quarkus.io/guides/cli-tooling))
2. Clona el repositorio
3. Iniciar Docker
4. Desde el directorio del repositorio, ejecuta el comando `quarkus dev`
5. Ya podremos acceder a la API, inicializada localmente (por defecto en `localhost:8080`).

## Endpoints (Vista de SwaggerUI): 
![Endpoints y responses](https://i.imgur.com/FbVWM5b.png)
