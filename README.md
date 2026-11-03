# Private Zone Wellness

Aplicación web de una cafetería y un salón de belleza, hecha con Spring Boot y Thymeleaf (proyecto SC-403).

## Requisitos

- Java 17
- Maven instalado en el sistema (`mvn`); el proyecto no incluye Maven wrapper
- MySQL (opcional; ver Configuración)

## Cómo ejecutar

```
mvn spring-boot:run
```

La aplicación queda disponible en http://localhost:8086.

## Configuración

Por defecto, sin ninguna configuración adicional, la aplicación usa una base de datos H2 en memoria: los datos se pierden al detenerla.

Para usar MySQL:

1. Copia `src/main/resources/application.properties.example` como `src/main/resources/application-local.properties`.
2. Descomenta las líneas del datasource y reemplaza `tu_usuario` y `tu_password` por tus credenciales.

`application-local.properties` está en `.gitignore`: no se sube al repositorio.

## Flujo de trabajo del equipo

- Cada tarea se desarrolla en una rama `feature/<area>-<tema>` creada desde `main`.
- Los cambios se integran a `main` mediante pull requests.
