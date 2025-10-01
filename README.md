# 📖 README: Sistema de Gestión E-commerce: BidCom Store
## Proyecto Final de Programacion II | Informatica Aplicada 2° Año
---
## 1. Introducción al Proyecto

Este proyecto realizado como parte del final de una materia, es un sistema integral de gestión y ventas de un hipotetico **E-Comerce** (BidCom Store) desarrollado sobre java con el framework **Spring Boot**. El objetivo es proporcionar una plataforma centralizada que maneje la autenticación de usuarios, la gestión de inventario (`Producto`), el seguimiento de pedidos (`Pedido`) y una estructura de roles, permisos y autorizaciones.

### Tecnologías Clave

| Categoría | Tecnología | Notas |
| :--- | :--- | :--- |
| **Backend Core** | **Spring Boot 3+** | Base del proyecto, inyección de dependencias y *auto-configuration*. |
| **Persistencia** | **Spring Data JPA / Hibernate** | Manejo de entidades, herencia de clases y repositorios. |
| **Base de Datos** | MySQL | Base de datos, idealmente externa |
| **Seguridad** | **Spring Security** | Autenticación basada en roles (`PasswordEncoder`, `UserDetailsService`). |
| **Frontend** | **Thymeleaf** | Motor de plantillas moderno para la capa de presentación. |
| **Responsive Design** | **Bootstrap** | Marco para crear Webs Responsive. |

---

## 2. Arquitectura y Diseño 

El sistema sigue el patrón **MVC (Modelo-Vista-Controlador)** con una estricta **Arquitectura en Capas** que garantiza la **Separación de Responsabilidades** y las buenas prácticas de Spring.

### 2.1. Ingeniería de Datos y Persistencia

* **Soft Delete (`activo`):** Implementación de **borrado lógico** en entidades clave (`Usuario`, `Producto`, `Pedido`). Esto asegura la **integridad de los datos** y permite la auditoría.
* **Herencia de Entidades:** Uso de herencia de JPA (`Usuario` como clase base para `Cliente`) para modelar correctamente la estructura de usuarios.

### 2.2 Estructura de directorios clave
```text
bidcom-ecommerce/
├── src/
│   ├── main/
│   │   ├── java/com/bidcom/
│   │   │   ├── config/             # Configuración de Spring (Spring Security, MVC, JPA)
│   │   │   ├── controller/         # Lógica de manejo de peticiones (MVC)
│   │   │   │   ├── AdminController.java      # Muestra la lista de Usuarios y Productos para el rol ADMIN.
│   │   │   │   ├── ClienteController.java    # Gestión de pedidos del cliente logueado (Ruta: /cliente/mispedidos).
│   │   │   │   ├── CreateController.java     # Maneja todas las operaciones de CREACIÓN (Producto, Usuario, Pedido).
│   │   │   │   ├── DeleteController.java     # Maneja todas las operaciones de BORRADO LÓGICO (Soft Delete).
│   │   │   │   ├── EditController.java       # Maneja todas las operaciones de EDICIÓN/ACTUALIZACIÓN.
│   │   │   │   ├── HomeController.java       # Punto de entrada y redirecciones iniciales.
│   │   │   │   ├── LoginController.java      # Muestra el formulario de login.
│   │   │   │   ├── RepresentanteController.java # Muestra la lista de Clientes y Pedidos para el rol REPRESENTANTE.
│   │   │   │   └── SetupController.java      # Wizzard de inicialización para crear el primer usuario ADMIN.
│   │   │   ├── model/              # Entidades JPA (Usuario, Producto, Pedido, Cliente)
│   │   │   ├── repositories/       # Repositorios Spring Data JPA
│   │   │   └── service/            # Lógica de Negocio y Transacciones
│   │   └── resources/
│   │       ├── templates/          # Vistas Thymeleaf (HTML)
│   │       ├── static/             # Archivos estáticos (CSS, JS, imágenes)
│   │       └── application.properties # Archivo de configuración principal (DB, puertos, logging)
│   │
│   └── test/
│       └── java/com/bidcom/       # Código para pruebas unitarias e integración
│
├── pom.xml                       # Dependencias y configuración de Maven
├── .gitignore
└── README.md
```

## 3. Gestión de Seguridad y Roles

La seguridad es gestionada por **Spring Security**, controlando el acceso a recursos mediante una estructura de roles bien definida.

### 3.1. Roles Implementados (`rolUsuario`)

| Rol | Uso | Rutas de Acceso |
| :--- | :--- | :--- |
| **ADMINISTRADOR** | Gestión total (CRUD) de inventario y usuarios. | `/admin/**` |
| **REPRESENTANTE** | Gestión de clientes y creación & Soft Delete de pedidos. | `/representante/**` |
| **CLIENTE** | Visualización de pedidos propios. | `/cliente/**` |

### 3.2. Mecanismos de Seguridad Clave

* **Autenticación Inicial:** Implementación de un **Wizzard de Configuración (`SetupController`)** para asegurar la creación del primer usuario (`ADMIN`) de forma segura.
* **Hasheo de Contraseñas:** Uso obligatorio de **`BCryptPasswordEncoder`** en las operaciones de creación y edición.

---

## 4. Puesta en Marcha (Instrucciones)

Sigue estos pasos para correr la aplicación localmente:

### Prerrequisitos

* Java Development Kit (JDK) 17 o superior.
* Maven.

### Ejecución

1.  **Clonar el repositorio:** (Si aplica)
    ```bash
    git clone [URL_DEL_REPOSITORIO]
    cd [nombre-proyecto]
    ```
2.  **Desplegar y conectar a la base de datos**
    Debe desplegarse una base de datos MySQL de manera local (o en un dispositivo de la misma red)
    Y asegurarse de configurar las credenciales para la conexion en `application-properties`

    `spring.datasource.url=jdbc:mysql://<ip>:<puerto>/<db_name>`
    
3.  **Compilar y Ejecutar (Maven):**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

### Acceso Inicial

Una vez que la aplicación inicie en `http://localhost:8080/`:

1.  **Primer Uso (Empty DB):** El sistema redirigirá automáticamente al *Wizzard* de configuración (`/setup`) para crear la cuenta de **Administrador**.
2.  **Acceso a Paneles:**
    * Desde **/login**, una vez iniciada sesion con las credenciales de un usuario existente y activo, se nos redirigira al panel correspondiente segun su rol

---

## 📝 5. Tareas Pendientes

* Terminacion y homogeneización del front-end (aspecto de formularios y wizzard)
