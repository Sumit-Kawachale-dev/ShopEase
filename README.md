# 🛒 ShopEase - E-commerce Backend API

A scalable and production-ready backend system for an e-commerce platform built using Spring Boot.  
It provides robust APIs for managing products, categories, search, pagination, and image uploads.

---

## 🚀 Key Features

- 📦 Product & Category Management (CRUD)
- 🔍 Keyword-based Product Search
- 📄 Pagination & Sorting for optimized performance
- 🧾 DTO-based API architecture
- ⚠️ Global Exception Handling
- ✅ Input Validation (Jakarta Validation)
- 🖼️ Image Upload Support for Products
- 🔄 ModelMapper for entity-DTO conversion

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL / MySQL
- REST APIs
- Lombok
- ModelMapper

---

## 📁 Architecture


- Clean layered architecture
- Separation of concerns
- Production-grade design

---

## 🔗 API Highlights

### 📌 Categories
- GET `/api/public/categories` (Pagination + Sorting)
- POST `/api/public/categories`
- PUT `/api/public/categories/{id}`
- DELETE `/api/admin/categories/{id}`

### 📌 Products
- GET `/api/public/products`
- GET `/api/public/products/search?keyword=...`
- GET `/api/public/categories/{id}/products`
- POST `/api/admin/categories/{id}/product`
- PUT `/api/admin/product/{id}`
- DELETE `/api/admin/products/{id}`

---

## ⚙️ Key Implementations

- **Pagination & Sorting** for scalable APIs :contentReference[oaicite:0]{index=0}  
- **DTO + Response Structure** for clean API design :contentReference[oaicite:1]{index=1}  
- **Global Exception Handling** for consistent error responses :contentReference[oaicite:2]{index=2}  
- **Validation Layer** for input constraints :contentReference[oaicite:3]{index=3}  
- **ModelMapper Integration** for entity mapping :contentReference[oaicite:4]{index=4}  
- **Image Upload Service** using file storage system :contentReference[oaicite:5]{index=5}  

---

## ▶️ How to Run

1. Clone the repository  
2. Configure database in `application.properties`  
3. Set image upload path (`project.image`)  
4. Run `ShopEaseApplication` :contentReference[oaicite:6]{index=6}  
5. Test APIs via Postman  

---

## 📌 Future Enhancements

- JWT Authentication & Role-based Access  
- Swagger API Documentation  
- Payment Integration  
- Caching (Redis)

---

## 👨‍💻 Author

Sumit Kawachale
