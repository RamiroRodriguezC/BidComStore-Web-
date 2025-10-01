📖 README: Sistema de Gestión E-commerce (BidCom)
🌟 1. Introducción al Proyecto
Este proyecto es un sistema integral de gestión y ventas (E-commerce B2B/B2C) desarrollado con el framework Spring Boot. El objetivo es proporcionar una plataforma centralizada que maneje la autenticación de usuarios, la gestión de inventario (Producto), el seguimiento de pedidos (Pedido) y una estructura de permisos multinivel (Usuario / Cliente / Representante / Admin).

⚙️ Tecnologías Clave
Categoría	Tecnología	Notas
Backend Core	Spring Boot 3+	Base del proyecto, inyección de dependencias y auto-configuration.
Persistencia	Spring Data JPA / Hibernate	Manejo de entidades, herencia de clases y repositorios.
Base de Datos	[H2, PostgreSQL o MySQL]	[Base de datos en memoria para desarrollo o externa para producción].
Seguridad	Spring Security	Autenticación basada en roles (PasswordEncoder, UserDetailsService).
Frontend	Thymeleaf	Motor de plantillas moderno para la capa de presentación.

Exportar a Hojas de cálculo
🏗️ 2. Arquitectura y Diseño Profesional
El sistema sigue el patrón MVC (Modelo-Vista-Controlador) con una estricta Arquitectura en Capas que garantiza la Separación de Responsabilidades y las buenas prácticas de Spring.

2.1. Ingeniería de Datos y Persistencia
Soft Delete (activo): Implementación de borrado lógico en entidades clave (Usuario, Producto, Pedido) utilizando la bandera activo (boolean activo = true por defecto). Esto asegura la integridad de los datos y permite la auditoría completa del historial.

Herencia de Entidades: Uso de herencia de JPA (Usuario como clase base para Cliente) para modelar correctamente la estructura de usuarios del sistema.

2.2. Capa de Servicios y Lógica de Negocio
Toda la Lógica de Negocio reside en la capa de Service.

Los servicios aplican el filtro de soft delete: los métodos buscarTodos() solo retornan registros activo=true.

Se manejan transacciones complejas (ej. creación de Pedido con sus Items) para garantizar la coherencia de los datos.

2.3. Capa de Controladores (Controller Layer)
Inyección por Constructor: Todos los controladores utilizan la inyección por constructor para dependencias, garantizando la inmutabilidad y la facilidad de testing.

Rutas de Seguridad: Los controllers utilizan rutas segmentadas (/admin/**, /representante/**) para el control de acceso fino de Spring Security.

Patrón de Actualización Segura: En las operaciones de Edit, se utiliza el patrón de actualización parcial (@ModelAttribute) para buscar el objeto original en DB y solo actualizar los campos permitidos (ej. la lógica condicional en actualizarUsuario para el campo rol).

🔒 3. Gestión de Seguridad y Roles
La seguridad es gestionada por Spring Security, controlando el acceso a recursos mediante una estructura de roles bien definida.

3.1. Roles Implementados (rolUsuario)
Rol	Uso	Rutas de Acceso
ADMINISTRADOR	Gestión total (CRUD) de inventario y usuarios.	/admin/**
REPRESENTANTE	Gestión de clientes y creación de pedidos.	/representante/**
CLIENTE	Visualización de pedidos propios y navegación.	/cliente/**

Exportar a Hojas de cálculo
3.2. Mecanismos de Seguridad Clave
Autenticación Inicial: Implementación de un Wizzard de Configuración (SetupController) para asegurar la creación del primer usuario (ADMIN) en un entorno limpio.

Hasheo de Contraseñas: Uso obligatorio de BCryptPasswordEncoder en las operaciones de creación y edición de usuarios (aplicado en CreateController y EditController).

🚀 4. Puesta en Marcha (Instrucciones)
Sigue estos pasos para correr la aplicación localmente:

Prerrequisitos
Java Development Kit (JDK) 17 o superior.

Maven.

Ejecución
Clonar el repositorio: (Si aplica)

Bash

git clone [URL_DEL_REPOSITORIO]
cd [nombre-proyecto]
Compilar y Ejecutar (Maven):

Bash

mvn clean install
mvn spring-boot:run
Acceso Inicial
Una vez que la aplicación inicie en http://localhost:8080/:

Primer Uso: El sistema redirigirá automáticamente al Wizzard de configuración (/setup) para crear la cuenta de Administrador.

Acceso a Paneles:

Admin: http://localhost:8080/admin/usuarios

Representante: http://localhost:8080/representante/clientes

📝 5. Tareas Pendientes
[Detalle una tarea pendiente]

[Detalle otra tarea pendiente]
