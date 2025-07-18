
## Project Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   └── pl/
│   │       └── rengreen/
│   │           └── taskmanager/
│   │               ├── configuration/
│   │               ├── controller/
│   │               ├── dataloader/
│   │               ├── model/
│   │               ├── repository/
│   │               └── service/
│   └── resources/
│       ├── static/
│       │   ├── css/
│       │   │   └── flatly/
│       │   ├── js/
│       │   └── images/
│       └── templates/
│           ├── forms/
│           ├── fragments/
│           └── views/
├── test/
│   └── java/
│       └── pl/
│           └── rengreen/
│               └── taskmanager/
│                   ├── controller/
│                   └── service/
```

---

## Architectural Principles

- Project follows **Spring MVC** pattern.
- Code is modularized into **Controller → Service → Repository** layers.
- Models in `model/` represent JPA entities and DTOs.
- Static web content lives under `resources/static` (CSS/JS/images).
- Server-rendered views are Thymeleaf templates in `resources/templates`.
- JUnit test classes follow the same structure under `src/test/java`.

---

## Key Directories Explained

| Layer         | Path                                              | Responsibility                                        |
| ------------- | ------------------------------------------------- | ----------------------------------------------------- |
| Configuration | `configuration/`                                  | Spring configuration (e.g. WebConfig, SecurityConfig) |
| Controller    | `controller/`                                     | Handles web requests (REST + Thymeleaf)               |
| Service       | `service/`                                        | Business logic                                        |
| Repository    | `repository/`                                     | Spring Data JPA Repos                                 |
| Model         | `model/`                                          | Entities + DTOs                                       |
| DataLoader    | `dataloader/`                                     | DB seeders or init data classes                       |
| Templates     | `resources/templates/forms`, `views`, `fragments` | Thymeleaf views                                       |
| Static files  | `resources/static/css`, `js`, `images`            | Front-end assets                                      |
| Unit Tests    | `src/test/java/pl/rengreen/taskmanager/...`       | Controller and service test cases                     |

---