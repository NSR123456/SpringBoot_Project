# 🎓 Student Registration System

A modern web application built using **Spring Boot**, **Thymeleaf**, and **Bootstrap 5** for registering students in an academic environment.


<img width="1542" height="880" alt="mmmmmmm" src="https://github.com/user-attachments/assets/54dde18c-1c32-4571-bbd1-4febcc1a1aab" />




---

## 🚀 Features

- 🧑 Student registration with form validation
- 🖼️ Responsive front-end using Bootstrap 5
- 📄 Thymeleaf templating engine
- ✅ Server-side form validation
- 🎨 Beautiful and modern UI
- 💾 PostgreSQL integration (JPA/Hibernate)
- 🔄 Data persistence with `Spring Data JPA`

---

## 🛠️ Technologies Used

| Layer            | Tech Stack                    |
|------------------|-------------------------------|
| Frontend         | Thymeleaf, Bootstrap 5        |
| Backend          | Spring Boot, Spring Web       |
| Database         | PostgreSQL                    |
| ORM              | Spring Data JPA, Hibernate    |
| Validation       | Spring Boot Validation (JSR 380) |
| Build Tool       | Maven                         |

---

## 📸 UI Preview

### 🎨 Registration Page

<img width="1861" height="916" alt="reggggg" src="https://github.com/user-attachments/assets/70488dd9-396d-4977-9461-abd893628c6c" />





---

## 🏗️ Project Structure
├── .gitattributes
├── .gitignore
├── .mvn
    └── wrapper
    │   └── maven-wrapper.properties
├── README.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
        ├── java
        │   └── com
        │   │   └── example
        │   │       └── demo
        │   │           ├── DemoApplication.java
        │   │           ├── HomeController.java
        │   │           ├── User.java
        │   │           ├── UserController.java
        │   │           └── UserRepository.java
        └── resources
        │   ├── application.properties
        │   ├── db
        │       └── migration
        │       │   └── V1__init_schema.sql
        │   └── templates
        │       ├── edit_user.html
        │       ├── register.html
        │       ├── success.html
        │       ├── user_list.html
        │       └── user_view.html
    └── test
        └── java
            └── com
                └── example
                    └── demo
                        └── DemoApplicationTests.java

