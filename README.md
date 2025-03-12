## Coffee Shop Management System

### Overview

The Coffee Shop Management System is a backend application designed to streamline and automate the operations of coffee shops. Built with Java Spring Boot, this system provides robust APIs for managing inventory, orders, users and roles while ensuring high performance and security. The system supports features like dynamic filtering, powerful search capabilities and comprehensive reporting, making it ideal for coffee shop owners and staff to manage daily operations efficiently.

The system is designed to meet the challenges of managing inventory, tracking orders and ensuring secure access through permission-based access control (PBAC). It integrates seamlessly with MySQL for reliable data storage and Redis for caching, ensuring optimal performance and scalability.

### Features

#### User Authentication and Authorization

- Secure login and permission-based access using Spring Security and JWT.
- Fine-grained permission control to restrict unauthorized access.

#### Inventory Management

- Manage products, categories, ingredients and suppliers.
- Support for associating images with products and categories.
- Dynamic stock management through stock batches, tracking initial and remaining quantities, as well as monitoring batch usage across orders, to ensure efficient inventory control and minimize wastage.

#### Order Management

- Create, update and track customer orders and supply orders.
- Generate detailed sales and inventory reports for data-driven decision-making.

#### Advanced Filtering and Searching

- Dynamic filtering options for products, orders and categories based on multiple parameters (e.g., price, date and status).
- Full-text search support for rapid data retrieval.

#### Reporting and Analytics

- **Sales performance:** View revenue trends and order statistics.
- **Inventory:** Track stock levels and identify low-stock items.
- **Supply orders:** Monitor incoming inventory and supplier performance.

#### RESTful APIs

- Efficiently designed APIs with pagination support to handle large datasets.
- Easy integration with third-party systems.

#### High Performance and Scalability

- Redis caching to improve response times for frequently accessed data.
- MySQL ensures reliable and scalable data storage.

#### Security

- Protect sensitive user information with password encryption.
- Comprehensive logging and monitoring for auditing user actions.
### ERD
![image](https://github.com/user-attachments/assets/fe9a95c6-4603-424e-bf18-96be92f721cb)
### Tech Stack

#### Backend:

- **Java Spring Boot:** Core backend framework.
- **Spring Security:** Authentication and authorization.
- **MySQL:** Relational database for data persistence.
- **Redis:** In-memory data store for caching.
- **Maven:** Dependency management and build automation.

### Installation and Setup

#### Prerequisites

- **Java 17+**
- **MySQL**

#### Backend Setup

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd coffee-shop-management
   ```

2. Configure the database:

    - Create a MySQL database named `coffee_shop`.
    - Update `application-example.yml`, `application-common-example.yml`, `application-dev-example.yml`, `application-pro-example.yml` (remove 'example' from filename) in `src/main/resources` with your environment variables.

3. Build and run the backend:

   ```bash
   ./mvnw spring-boot:run
   ```

### License

[MIT](https://choosealicense.com/licenses/mit/)

### Acknowledgments

- Spring Boot Documentation
- Keycloak Community
- Redis and MySQL Documentation

### Contact

For questions or support, please contact:

- **Author:** Quy Truong
- **Email:** truongvanquy2k3@gmail.com
- **GitHub:** https://github.com/quytrg
