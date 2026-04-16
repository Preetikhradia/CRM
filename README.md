# 🚀 CRM Backend (Spring Boot)

A robust **Customer Relationship Management (CRM) Backend System** built using **Java Spring Boot**, designed to manage users, customers, leads, tasks, and sales with secure JWT-based authentication and role-based access control.

---

## 🛠️ Tech Stack

- Java  
- Spring Boot  
- Spring Security  
- JWT Authentication  
- MySQL  
- Maven  

---

## 🔐 Security Features

- JWT-based authentication system  
- Role-based authorization (Admin / Sales)  
- Password encryption  
- Secure API access using Spring Security  
- Token stored on frontend (localStorage)  

---

## 📦 Functional Modules

---

### 👤 User Management & Authentication

#### API Endpoints

- `POST /api/register` – Register new user (Admin / Sales)  
- `POST /api/login` – Login and receive JWT token  
- `GET /api/users/me` – Get logged-in user profile  

#### User Data

- Full Name  
- Email  
- Password (encrypted)  
- Role (Admin / Sales)  
- CreatedAt  

---

### 🧑‍💼 Customer Management

#### API Endpoints

- `GET /api/customers`  
- `GET /api/customers/{id}`  
- `POST /api/customers`  
- `PUT /api/customers/{id}`  
- `DELETE /api/customers/{id}`  

#### Customer Data

- Name  
- Email  
- Phone  
- Company  
- Address  
- Assigned Sales Representative  
- Notes  

---

### 📈 Lead Management

#### API Endpoints

- `GET /api/leads`  
- `POST /api/leads`  
- `PUT /api/leads/{id}` – Update lead info/status  
- `DELETE /api/leads/{id}`  

#### Lead Data

- Name  
- Contact Info  
- Source (Referral, Ads, Web)  
- Status (New, Contacted, Converted, Lost)  
- Assigned Sales Representative  

---

### 📋 Task Management

#### API Endpoints

- `GET /api/tasks`  
- `POST /api/tasks`  
- `PUT /api/tasks/{id}`  
- `DELETE /api/tasks/{id}`  

#### Task Data

- Title  
- Description  
- Due Date  
- Priority  
- Assigned To (User ID)  
- Status (Open, In Progress, Completed)  

---

### 💰 Sales Management

#### API Endpoints

- `GET /api/sales`  
- `POST /api/sales`  
- `GET /api/sales/{id}`  
- `PUT /api/sales/{id}`  

#### Sales Data

- Customer ID  
- Amount  
- Status (Proposal, Negotiation, Closed-Won, Closed-Lost)  
- Date  
- Assigned Sales Representative  

---

## 🗄️ Database Schema (MySQL)

### Tables

- users  
- customers  
- leads  
- tasks  
- sales  

### Relationships

- A **User (Sales Rep)** can be assigned to:
  - Customers  
  - Leads  
  - Tasks  
  - Sales  

- Each **Sale** is linked to a **Customer**  

---

## ▶️ How to Run

```bash
git clone https://github.com/Preetikhradia/CRM.git
cd crm-backend
