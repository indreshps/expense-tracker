# 💰 Expense Tracker V3

A full-stack Expense Tracker application built using Java, Spring Boot, MySQL, HTML, CSS, and JavaScript.

The application allows users to manage their daily expenses through a web interface, with expense data stored in a MySQL database.

## 🌐 Live Demo

Try the deployed app here: [expense-tracker-production-5356.up.railway.app](https://expense-tracker-production-5356.up.railway.app)

Hosted on Railway (Spring Boot app + MySQL database).

## ✨ Features

- ➕ Add new expenses
- 📋 View all expenses
- ✏️ Update existing expenses
- 🔍 Find expenses by ID
- 🗑️ Delete expenses
- 📊 Calculate total expenses
- 📈 View category-wise expense summaries
- ✅ Validate expense data
- ⚠️ Handle errors through a global exception handler
- 🌐 Interact with the backend through REST APIs
- 💾 Store expense records in MySQL
- 🖥️ Use a web-based frontend

## 🛠️ Technologies Used

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- REST APIs
- Maven

### Database

- MySQL

### Frontend

- HTML
- CSS
- JavaScript

### Development Tools

- Visual Studio Code
- Git
- GitHub
- Postman

## 🏗️ Project Structure

```text
expense-tracker-v3/
├── src/
│   ├── main/
│   │   ├── java/com/indresh/expensetracker/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── mapper/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── ExpenseTrackerV3Application.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── style.css
│   │       │   └── script.js
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## ⚙️ Prerequisites

Before running the project, install:

- Java Development Kit (JDK)
- MySQL Server
- Git

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/indreshps/expense-tracker.git
cd expense-tracker
```

### 2. Create the Database

Open MySQL and execute:

```sql
CREATE DATABASE expense_tracker;
```

### 3. Configure Database Credentials

The application uses an environment variable for the MySQL password.

Set `DB_PASSWORD` to your MySQL password before starting the application.

**Windows Command Prompt:**

```cmd
set DB_PASSWORD=your_mysql_password
```

Replace `your_mysql_password` with your actual MySQL password.

**Do not commit your database password to GitHub.**

### 4. Run the Application

From the project root, run:

**Windows:**

```cmd
mvnw.cmd spring-boot:run
```

### 5. Open the Application

Once the application starts, open:

http://localhost:8080/

## ☁️ Deployment

The app is deployed on Railway, with the Spring Boot app and the MySQL database as two services in one project.

The app reads its database settings from environment variables:

| Variable | Purpose |
|---|---|
| DB_URL | JDBC connection URL for the database |
| DB_USERNAME | Database username |
| DB_PASSWORD | Database password |
| PORT | Server port (set automatically by Railway) |

If DB_URL and DB_USERNAME are not set, the app falls back to a local MySQL database at localhost:3306/expense_tracker with user root.

Every push to the main branch triggers an automatic redeploy on Railway.

## 🔌 REST API

The backend exposes REST endpoints for managing expenses.

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/expenses` | Retrieve expenses |
| POST | `/api/expenses` | Add an expense |
| PUT | `/api/expenses/{id}` | Update an expense |
| DELETE | `/api/expenses/{id}` | Delete an expense |

Additional endpoints support expense summaries and searching.

**Note:** Confirm the exact endpoint paths against the current controller implementation.

## 🧪 Testing

Use Postman to test the REST API endpoints.

You can test creating, retrieving, updating, and deleting expenses.

## 🗺️ Project Evolution

This project is being developed incrementally:

- **Version 1:** Console-based Expense Tracker using Java and file handling.
- **Version 2:** Database integration using MySQL and JDBC.
- **Version 3:** REST API backend using Spring Boot, MySQL, and a web frontend.
- **Future Version:** Additional features and improvements.

## 🎯 Learning Objectives

This project demonstrates practical implementation of:

- Object-Oriented Programming in Java
- REST API development
- Spring Boot application architecture
- Database integration using Spring Data JPA
- Data validation and exception handling
- Frontend and backend integration
- Git and GitHub version control

## 👨‍💻 Author

**Indresh Pratap Singh**

GitHub: [@indreshps](https://github.com/indreshps)

⭐ If you find this project useful, consider giving the repository a star!
