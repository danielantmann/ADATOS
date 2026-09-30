# El patrón de diseño DTO (Data Transfer Object)    

## Qué es un DTO
* Los dtos son objetos que se utilizan para transferir datos entre diferentes capas de una aplicación.  
* Se utilizan para encapsular los datos y evitar la exposición directa de los objetos de dominio.
* Ejemplos y ampliación en [este enlace](https://github.com/joseluisgs/DesarrolloWebEntornosServidor-02-2025-2026/blob/master/springboot/05-Servicios.md#53-patr%C3%B3n-dto)

## Mapeadores *(Mappers)*
* Los *mappers* son clases que se utilizan para mapear los datos entre objetos.

## El patrón Builder
* El patrón Builder es un patrón de diseño creacional que se utiliza para construir objetos complejos paso a paso.  
* Permite separar la construcción de un objeto de su representación.
* En clases con muchos atributos resulta muy útil para evitar constructores con muchos parámetros y mejorar la legibilidad del código.
* Facilita el mantenimiento del código de los mappers