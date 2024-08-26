# Microservices

This repository contains various microservices for a distributed system.

## How to Use Docker-Compose

To deploy the microservices using Docker-compose, follow these steps:

1. Run Maven Lifecycle for clean, install, and tests:

   ```bash
   mvn clean install test
   ```

   This will clean the project, install dependencies, and run the tests.

2. After that, execute the following command:

   ```bash
   docker-compose up --build
   ```

   This command will build and start the containers for the microservices.

## Keycloak Configuration

After successfully running the Keycloak admin interface, follow these additional steps:

1. **Import JSON Configuration:**
   - Access the Keycloak admin console at [http://localhost:8080/auth/admin](http://localhost:8080/auth/admin).
   - Log in with the administrator credentials.
   - Navigate to the "Realm Settings" and choose the realm you want to configure.
   - Click on "Import" and upload the provided JSON configuration file.
   
   - File:  [breakin-keycloak.json](breakin_keycloak.json)

   This JSON file contains the necessary configurations for Keycloak.

## Documentation

Explore detailed documentation on [Breakinpedia](https://helfendeapp.atlassian.net/wiki/spaces/BREAK/overview?homepageId=3637454) for additional information.

Additionally, configure Keycloak by following these steps:

1. [Keycloak Configuration Guide](https://www.keycloak.org/getting-started/getting-started-docker) - Add details on how to configure Keycloak for authentication and authorization.

Feel free to reach out for any further assistance or inquiries.
