# Credit Bureau Service API

A RESTful Spring Boot WebFlux API designed for **demonstration purposes with Agentic AI as a tool**. This service simulates a credit bureau system that provides credit checks, credit scores, credit history, and loan application management capabilities.

## 🎯 Purpose

This API serves as a demonstration project showcasing how **Agentic AI tools** can interact with and utilize modern RESTful services. It provides a realistic credit bureau simulation that AI agents can use to:

- Perform comprehensive credit assessments
- Retrieve credit scores and detailed credit history
- Access loan history for credit evaluation purposes
- Demonstrate real-world API integration patterns

## 🚀 Features

- **Reactive Programming**: Built with Spring WebFlux for non-blocking, asynchronous operations
- **Comprehensive API Documentation**: Interactive Swagger UI with detailed endpoint documentation
- **Credit Management**: Full credit scoring and history tracking
- **Loan History Access**: Read-only access to loan history for credit assessment purposes
- **In-Memory Database**: H2 database with pre-loaded sample data for immediate testing
- **Production-Ready**: Includes health checks, metrics, and proper error handling

## 📋 API Endpoints

### Credit Operations
- `POST /api/v1/credit/check` - Perform comprehensive credit check
- `GET /api/v1/credit/score/{ssn}` - Get credit score by SSN
- `GET /api/v1/credit/history/{ssn}` - Get credit history by SSN

### Loan History (Read-Only for Credit Assessment)
- `GET /api/v1/credit/loan-history/{ssn}` - Get loan history for credit evaluation purposes

### System Operations
- `GET /actuator/health` - Health check endpoint
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Application metrics

## 🛠️ Technology Stack

- **Java 17+** - Programming language
- **Spring Boot 3.5.6** - Application framework
- **Spring WebFlux** - Reactive web framework
- **Spring Data R2DBC** - Reactive database access
- **H2 Database** - In-memory database for demo
- **SpringDoc OpenAPI** - API documentation
- **Maven** - Dependency management
- **Lombok** - Code generation

## 🏃‍♂️ Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+ (or use included Maven wrapper)

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd credit-bureau-service
   ```

2. **Run with Maven**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Access the API**
   - **Swagger UI**: http://localhost:8080/swagger-ui.html
   - **API Docs**: http://localhost:8080/api-docs
   - **Health Check**: http://localhost:8080/actuator/health

## 📊 Sample Data

The application comes pre-loaded with sample data for immediate testing:

### Sample SSNs for Testing
- `123-45-6789` - John Doe (Score: 750, Risk: LOW)
- `987-65-4321` - Jane Smith (Score: 620, Risk: MEDIUM)
- `555-12-3456` - Bob Johnson (Score: 580, Risk: MEDIUM)
- `111-22-3333` - Alice Brown (Score: 720, Risk: LOW)
- `444-55-6666` - Charlie Wilson (Score: 450, Risk: HIGH)

### Sample Loan Applications
- `app-001` - Approved personal loan
- `app-002` - Under manual review
- `app-003` - Rejected application

## 🤖 Agentic AI Integration

This API is specifically designed to work seamlessly with AI agents and tools:

### For AI Tool Development
- **Clear endpoint documentation** with examples for easy AI consumption
- **Consistent response formats** for predictable parsing
- **Comprehensive error handling** with meaningful HTTP status codes
- **Sample data** for immediate testing and demonstration

### Common AI Use Cases
1. **Credit Assessment**: AI agents can check creditworthiness before loan recommendations
2. **Risk Analysis**: Evaluate credit history patterns for decision making
3. **Application Processing**: Automate loan application workflows
4. **Data Enrichment**: Gather comprehensive financial profiles

### Example AI Integration
```bash
# Credit check example
curl -X POST "http://localhost:8080/api/v1/credit/check" \
  -H "Content-Type: application/json" \
  -d '{
    "ssn": "123-45-6789",
    "firstName": "John",
    "lastName": "Doe"
  }'

# Get credit score
curl "http://localhost:8080/api/v1/credit/score/123-45-6789"
```

## 📖 API Documentation

Interactive API documentation is available through Swagger UI:
- **URL**: http://localhost:8080/swagger-ui.html
- **Features**: 
  - Try out endpoints directly
  - View request/response schemas
  - See example values
  - Download OpenAPI specification

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controller    │    │    Service      │    │   Repository    │
│   (Web Layer)   │───▶│  (Business)     │───▶│  (Data Access)  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                                               ┌─────────────────┐
                                               │   H2 Database   │
                                               │  (In-Memory)    │
                                               └─────────────────┘
```

## 🔧 Configuration

The application uses YAML configuration with environment-specific profiles:

### Key Configuration Files
- `application.yml` - Main application configuration
- `OpenApiConfig.java` - API documentation configuration
- `schema.sql` - Database schema
- `data.sql` - Sample data

### Environment Variables
- `SERVER_PORT` - Server port (default: 8080)
- `LOGGING_LEVEL_ROOT` - Root logging level
- `SPRING_PROFILES_ACTIVE` - Active Spring profile

## 🧪 Testing

### Manual Testing with Swagger UI
1. Navigate to http://localhost:8080/swagger-ui.html
2. Expand any endpoint
3. Click "Try it out"
4. Enter parameters and execute

### Sample API Calls
```bash
# Health check
curl http://localhost:8080/actuator/health

# Get credit score
curl http://localhost:8080/api/v1/credit/score/123-45-6789

# Get credit history
curl http://localhost:8080/api/v1/credit/history/123-45-6789

# Get loan history for credit assessment
curl http://localhost:8080/api/v1/credit/loan-history/123-45-6789

# Perform comprehensive credit check
curl -X POST http://localhost:8080/api/v1/credit/check \
  -H "Content-Type: application/json" \
  -d '{
    "ssn": "123-45-6789",
    "firstName": "John",
    "lastName": "Doe",
    "requestedAmount": 25000,
    "loanType": "AUTO",
    "termMonths": 60,
    "annualIncome": 75000,
    "employmentStatus": "EMPLOYED"
  }'
```

## ⚠️ Important Notes

- **Demo Purpose Only**: This API is for demonstration and should not be used in production environments
- **Sample Data**: All data is fictional and for testing purposes only
- **Security**: No authentication/authorization implemented - suitable for demo environments only
- **Data Persistence**: Uses in-memory H2 database - data resets on application restart

## 🤝 Contributing

This project is designed for educational and demonstration purposes. Contributions that enhance its utility as an AI integration example are welcome.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For questions about AI integration or API usage:
- **Email**: support@creditbureau.com
- **Documentation**: http://localhost:8080/swagger-ui.html
- **Health Status**: http://localhost:8080/actuator/health

---

**Built for AI • Powered by Spring Boot • Ready for Integration**
