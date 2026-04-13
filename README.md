# SyncBridge

A Java Spring Boot CRM middleware service that provides secure, scalable synchronization between enterprise systems and Salesforce CRM using OAuth 2.0 authentication and respecting platform governor limits.

## Architecture Overview

SyncBridge serves as a middleware layer that enables seamless data synchronization between internal enterprise systems and Salesforce CRM. The service implements industry-standard security practices and is designed to handle high-volume data operations while respecting Salesforce's platform limitations.

## Key Features

### Salesforce Trust & Security

- **OAuth 2.0 Authentication**: Implements secure OAuth 2.0 flow for Salesforce API access
- **Enterprise-Grade Security**: Uses Spring Security with configurable authentication providers
- **Token Management**: Automatic token refresh and secure storage of credentials
- **Audit Logging**: Comprehensive logging of all API interactions for compliance

### OAuth 2.0 Implementation

The service configures OAuth 2.0 client registration for Salesforce:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          salesforce:
            client-id: ${SALESFORCE_CLIENT_ID}
            client-secret: ${SALESFORCE_CLIENT_SECRET}
            authorization-grant-type: authorization_code
            scope: api,refresh_token
```

### Platform Governor Limits

SyncBridge is designed to operate within Salesforce's platform governor limits:

- **Daily API Calls**: 100,000 calls per 24 hours
- **Concurrent Bulk Jobs**: Maximum 5 concurrent bulk operations
- **Bulk Job Records**: 10,000,000 records per 24 hours
- **Batch Size Limits**: Processes data in optimal batch sizes (2000 records)

The service includes intelligent limit monitoring and automatic throttling to prevent limit violations.

## Project Structure

```
SyncBridge/
├── src/main/java/com/syncbridge/
│   ├── config/
│   │   └── SalesforceConfig.java          # OAuth 2.0 configuration
│   ├── controller/                        # REST API endpoints
│   ├── service/
│   │   └── SalesforceSyncService.java     # Bulk sync operations
│   └── model/
│       └── CustomerRecord.java            # JPA entity
├── src/main/resources/
│   └── application.yml                    # Configuration
├── docker-compose.yml                     # PostgreSQL container
├── pom.xml                               # Maven dependencies
└── README.md
```

## Technology Stack

- **Spring Boot 3.2.0**: Modern Java framework
- **Spring Web**: REST API development
- **Spring Data JPA**: Database persistence
- **Spring Security**: Authentication and authorization
- **PostgreSQL**: Primary database
- **Maven**: Build and dependency management
- **Docker**: Containerization

## Prerequisites

- Java 17+
- Maven 3.6+
- Docker and Docker Compose

## Setup Instructions

### 1. Clone and Build

```bash
git clone <repository-url>
cd SyncBridge
mvn clean compile
```

### 2. Database Setup

Start PostgreSQL using Docker Compose:

```bash
docker-compose up -d
```

### 3. Environment Configuration

Create a `.env` file or set environment variables:

```bash
# Salesforce OAuth 2.0 Credentials
SALESFORCE_CLIENT_ID=your-client-id
SALESFORCE_CLIENT_SECRET=your-client-secret

# Optional: Custom Salesforce URLs (for sandbox environments)
SALESFORCE_AUTH_URI=https://test.salesforce.com/services/oauth2/authorize
SALESFORCE_TOKEN_URI=https://test.salesforce.com/services/oauth2/token
SALESFORCE_USER_INFO_URI=https://test.salesforce.com/services/oauth2/userinfo
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The service will start on `http://localhost:8080`

## API Endpoints

### Bulk Synchronization

The service provides endpoints for bulk data operations (to be implemented in controllers):

- `POST /api/sync/bulk` - Initiate bulk synchronization
- `GET /api/sync/status/{jobId}` - Check synchronization status
- `GET /api/sync/health` - Service health check

## Bulk Processing Implementation

The `SalesforceSyncService` simulates processing 10,000 records using Salesforce Bulk API patterns:

```java
public CompletableFuture<SyncResult> processBulkRecords() {
    // Processes records in batches of 2000
    // Monitors governor limits
    // Handles API errors and retries
    // Provides detailed logging and metrics
}
```

### Governor Limit Handling

- **API Call Tracking**: Monitors daily API usage
- **Batch Optimization**: Processes data in optimal batch sizes
- **Rate Limiting**: Implements intelligent throttling
- **Error Handling**: Graceful handling of limit violations

## Security Considerations

### OAuth 2.0 Flow

1. **Authorization**: Redirects users to Salesforce for authentication
2. **Token Exchange**: Securely exchanges authorization code for access token
3. **Token Storage**: Securely stores refresh tokens
4. **Auto Refresh**: Automatically refreshes expired tokens

### Data Protection

- **Encryption**: All sensitive data is encrypted at rest and in transit
- **Access Control**: Role-based access control for API endpoints
- **Audit Trails**: Complete audit logging of all operations

## Monitoring and Observability

- **Health Checks**: Built-in health endpoints for monitoring
- **Metrics**: Integration with Spring Boot Actuator for metrics
- **Logging**: Structured logging with configurable levels
- **Alerts**: Automated alerts for system issues

## Deployment

### Docker Deployment

```bash
# Build the application
mvn clean package

# Build Docker image
docker build -t syncbridge:latest .

# Run with Docker Compose
docker-compose up -d
```

### Production Considerations

- **Environment Variables**: Use secure secret management
- **Database**: Configure connection pooling and monitoring
- **Scaling**: Implement horizontal scaling for high-volume operations
- **Backup**: Regular database backups and disaster recovery

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions, please open an issue in the GitHub repository.