[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/gqsmd06D)
# 📋 Tarea Semanal: Proyecto E2E - Entrega 2

## Descripción 💡

En esta entrega, vamos a llevar la implementación del primer laboratorio a un nivel más eficiente, aplicando los conceptos que hemos aprendido durante la semana: **DTOs** y **Manejo de Excepciones**.

En esta fase del proyecto, se te proporciona una solución inicial al laboratorio anterior. Tu tarea consiste en mejorar esta solución implementando DTOs y manejando excepciones adecuadamente. Además, debes asegurarte de que los códigos de estado HTTP de error y éxito se manejen correctamente. A continuación, se detallan los puntos que debes tener en cuenta en tu implementación:

## Manejo de Excepciones

Debes manejar las distintas situaciones problemáticas que puedan surgir durante el procesamiento de las solicitudes. Los endpoints serán probados bajo ciertas condiciones de fallo, por lo que no será suficiente hardcodear los `HttpStatus`.

### Estados de Error a Considerar

- **404:** Para cualquier recurso no encontrado en cualquier tipo de solicitud.
- **409:** Cuando la solicitud entra en conflicto con el estado actual del servidor. Por ejemplo, intentar crear un recurso con un atributo único que ya pertenece a otro registro en la base de datos.
- **400** y **405:** Serán manejados automáticamente por Spring Boot.

### Estados de Éxito a Considerar

- **200:** Para toda solicitud procesada y devuelta con éxito, que no requiera información adicional más allá de la solicitada.
- **201:** Para toda solicitud que cree recursos. Además de la información solicitada, debe devolver el URI al cual se debe hacer un GET para acceder al nuevo recurso. Por ejemplo, si se crea un recurso "carro" con el ID 1, se debe devolver un código 201 junto con el URI `carro/1`, asumiendo que el endpoint GET está definido como `carro/{id}`. Este uri se devuelve en los headers, ver [este link](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Location) para más información.
- **204:** Para los endpoints de tipo `DELETE`.

---
## DTOs

Dentro de la mayoría de las entidades, encontrarás los DTOs que debes utilizar. A continuación, se describe su uso:

- `DriverDto`:
  - Tipo de `response body` para los endpoints `GET /driver/{id}` y `PATCH /driver/{id}/car`
  - Tipo de `request body` para el endpoint `PUT /driver/{id}`


- `NewDriverRequestDto`:
  - Tipo de `request body` para el endpoint `POST /driver`


- `PassengerResponseDto`:
  - Tipo de `response body` para el endpoint `GET /passenger/{id}`


- `PassengerRequestDto`:
  - Tipo de `request body` para el endpoint `POST /passenger`


- `PassengerLocationResponseDto`:
  - Tipo de `request body` para el endpoint `PATCH /passenger/{id}/places`
  - Tipo de los elementos de la lista retornada en el endpoint `GET /passenger/{id}/places`


- `NewReviewDto`:
  - Tipo de `request body` para el endpoint `POST /review`


- `ReviewsByUser` (Proyección):
  - Tipo de elementos de la página retornada por el endpoint `GET /passenger/{driverId}`


- `RideRequestDto`:
  - Tipo de `request body` para el endpoint `POST /ride`


- `RidesByUserDto`:
  - Tipo de elementos de la página retornada por el endpoint `GET /ride/{userId}`


Los DTOs `CoordinateDto` y `VehicleDto` son utilizados dentro de algunos de los DTOs mencionados previamente.

### Validaciones

| DTO                                         | Constraint                                                                                                                                                                                                                                                         |
|---------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| DriverDto                                   | - `category`: no nulo.<br>- `trips` y `avgRating`: no nulos y mayores o iguales a 0.<br>- `firstName` y `lastName`: no nulos y longitud entre 2 y 50 caracteres.                                                                                                   |
| NewDriverRequestDto                         | - Todos los atributos: no nulos.<br>- `firstName` y `lastName`: no nulos y longitud entre 2 y 50 caracteres.<br>- `email`: debe ser un email válido.<br>- `phoneNummber`: longitud entre 9 y 15 caracteres.<br>- `password`: longitud entre 6 y 50 caracteres.<br> |
| CoordinateDto                               | - `latitude` y `longitude`: no nulos y entre -90 y 90 para `latitude` y -180 y 180 para `longitude`.                                                                                                                                                               |
| PassengerResponseDto<br>PassengerRequestDto | - `firstName` y `lastName`: no nulos y longitud entre 2 y 50 caracteres.<br>- `phoneNummber`: longitud entre 9 y 15 caracteres.<br>- `avgRating`: no nulo y entre 0.0 y 5.0.<br>- En `PassengerRequestDto`, `email`: debe ser un email válido.                     |
| PassengerLocationResponseDto                | - `description`: no nulo y longitud entre 2 y 255 caracteres.                                                                                                                                                                                                      |
| NewReviewDto                                | - Todos los atributos: no nulos.<br>- `rating`: entre 0 y 5.                                                                                                                                                                                                       |
| RideRequestDto                              | - Todos los atributos: no nulos.<br>- `destinationName`: no nulo y longitud entre 2 y 255 caracteres.<br>- `price`: no nulo y mayor a 0.<br>                                                                                                                       |
| RidesByUserDto                              | - Todos los atributos: no nulos.<br>- `destinationName`: no nulo y longitud entre 2 y 255 caracteres.<br>- `price`: no nulo y mayor a 0.                                                                                                                           |
| VehicleDto                                  | - `brand`, `model`, `licensePlate` y `color`: no nulos y longitud entre 2 y 50 caracteres.<br>- `year`: no nulo y entre 1900 y el año actual.<br>                                                                                                                  |


---

## Consideraciones Importantes

Los únicos cambios que deberán hacer en los endpoints respecto al laboratorio anterior son:
- Añadir el endpoint `POST /passenger`, para crear un pasajero usando el DTO `PassengerRequestDto`.


- Añadir el endpoint `GET /review/{driverId}`, para obtener todas las `review` donde el atributo `target` haga referencia al `driverId`. Este endpoint debe devolver un objeto `Page` de `ReviewsByUSer`.


- El endpoint `PATCH /passenger/{id}` ahora es `PATCH /passenger/{id}/places` para una mejor visibilidad.