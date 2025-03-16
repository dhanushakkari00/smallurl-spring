# Small URL - URL Shortener with Zookeeper, Redis & MongoDB

A **high-performance, distributed URL shortener** built using:
- **Spring Boot** for backend services
- **MongoDB** as a persistent database
- **Redis** for caching short URLs
- **Zookeeper** to ensure unique shortcodes in a distributed setup
- **Base62 encoding** for efficient short URLs

---

##  Features
1. **Shorten any URL** into a tiny, shareable link  
2. **Custom expiration** (set expiry for links)  
3. **High-performance** caching with **Redis**  
4. **Ensures unique codes** using **Zookeeper**  
5. **Automatic redirection** when visiting the short URL  
6. **Distributed System Ready** – Can be scaled horizontally  

---

##  Project Structure
```
smallurl/
│── src/main/java/com/example/smallurl/
│   ├── controller/        # API controllers (UrlController)
│   ├── service/           # Business logic (UrlService)
│   ├── repository/        # MongoDB repository (UrlRepository)
│   ├── config/            # Configuration for MongoDB, Redis, and Zookeeper
│   ├── model/             # URL entity (Url.java)
│   ├── SmallurlApplication.java  # Main entry point
│── src/main/resources/
│   ├── application.properties  # App configuration
│── pom.xml                # Maven dependencies
│── README.md              # You are here! 📌
```

---

## ⚙️ Setup & Installation

### 1️.Prerequisites
Before running, ensure you have:
- **Java 17+** installed
- **MongoDB** running on port `27017`
- **Redis** running on port `6379`
- **Zookeeper** running on port `2181`
- **Maven** installed

---

### 2️.Clone the Repository
```sh
git clone https://github.com/yourusername/smallurl.git
cd smallurl
```

---

### 3️.Configure the App
Update the `application.properties` file inside `src/main/resources/`:

```properties
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/smallurl_db

# Redis Configuration
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379

# Zookeeper Configuration
zookeeper.connectString=localhost:2181
zookeeper.sessionTimeout=3000
zookeeper.connectionTimeout=3000
```

---

### 4️.Run the Services
**Start MongoDB:**
```sh
mongod --dbpath "C:\data\db"
```

**Start Redis (Memurai for Windows users):**
```sh
memurai-server
```

**Start Zookeeper:**
```sh
zkServer.cmd
```

---

### 5️.Build & Run
```sh
mvn clean install
mvn spring-boot:run
```
or
```sh
./mvnw spring-boot:run  # (For Linux/macOS)
```

---

## 🛠️ API Endpoints

### 1️.Shorten a URL
```http
POST /api/shorten
```
**Request Params:**
| Parameter     | Type   | Required | Description |
|--------------|--------|----------|-------------|
| `originalUrl` | String |  Yes | The long URL to shorten |
| `expireInSeconds` | Long |  No | Expiry time in seconds |

**Example:**
```sh
curl -X POST "http://localhost:8080/api/shorten?originalUrl=https://example.com&expireInSeconds=3600"
```
**Response:**
```json
{
    "shortUrl": "http://localhost:8080/api/redirect/abc123"
}
```

---

### 2️.Redirect to Original URL
```http
GET /api/redirect/{shortCode}
```
**Example:**  
```sh
http://localhost:8080/api/redirect/abc123
```
 **Redirects** to the original URL!

If expired or invalid:
```json
{
    "error": "URL not found or expired."
}
```

---

##  Tech Stack
- **Spring Boot** – Backend Framework
- **MongoDB** – Persistent Storage
- **Redis** – Caching Layer
- **Zookeeper** – Unique ID Coordination
- **Base62 Encoding** – URL Shortening
- **Maven** – Dependency Management

---

##  Future Enhancements
1. **Custom shortcodes** (user-defined)  
2. **Click tracking** (view analytics)  
3. **QR Code generator** for short URLs  
4. **User authentication** for private links  

---

##  License
This project is **open-source** and free to use.

---

##  Contact
If you have any questions or need help, feel free to reach out!  

 **Dhanush Akkari**  
 www.linkedin.com/in/dhanushakkari

---



