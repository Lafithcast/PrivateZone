# Private Zone Wellness

Aplicación web de una cafetería y un salón de belleza, hecha con Spring Boot y Thymeleaf (proyecto SC-403).

## Requisitos

- Java 17
- Maven instalado en el sistema (`mvn`); el proyecto no incluye Maven wrapper
- MySQL 8
- MySQL Workbench

## Cómo ejecutar

1. Abre `database/private_zone.sql` en MySQL Workbench y ejecútalo completo. El script recrea la base de datos `private_zone`, el usuario y las tablas, y carga los datos de ejemplo; si lo vuelves a ejecutar, también reinicia los datos.
2. Inicia la aplicación:

```
mvn spring-boot:run
```

La aplicación queda disponible en http://localhost:8086.

## Configuración

La aplicación se conecta por defecto a MySQL en `localhost:3306`, base `private_zone`, con el usuario que crea el script (ver `application.properties`). Ya no se usa H2, y Hibernate no crea ni modifica tablas: el esquema vive en `database/private_zone.sql`.

Si usas otras credenciales u otro puerto, el archivo `src/main/resources/application-local.properties` es opcional:

1. Copia `src/main/resources/application.properties.example` como `src/main/resources/application-local.properties`.
2. Descomenta las líneas del datasource y reemplaza `tu_usuario` y `tu_password` por tus credenciales.

`application-local.properties` está en `.gitignore`: no se sube al repositorio.

## Flujo de trabajo del equipo

- Cada tarea se desarrolla en una rama `feature/<area>-<tema>` creada desde `main`.
- Los cambios se integran a `main` mediante pull requests.
