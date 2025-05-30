# Product Watcher API

## 📚 API Documentation

### Swagger UI
Access the interactive API documentation:  
`http://localhost:8080/swagger-ui.html`

**Note:** The application must be running to access these endpoints.

---

## 🚀 Infrastructure Setup

### Start Services
```bash
bash run.sh
```

**What this does:**
- Starts PostgreSQL database
- Initializes Flyway migrations
- Sets up any other required services

**Requirements:**
- Docker and Docker Compose installed
- Ports 8080 (app) and 5432 (DB) available
