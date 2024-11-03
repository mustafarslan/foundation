# Brokage Firm Challenge

## Prerequisites

- Ensure you have Docker and Docker Compose installed on your system.
- You will need Postman (or a similar API client) to test the API endpoints

## Installation

- Clone the repository and navigate to the project’s root directory (foundation).
- Build and run the application using Docker Compose:

    ``
    docker-compose up --build
    ``

- To stop the application, run the following command:

    ``
    docker-compose down
    ``

## Test

- Use the provided Postman collection (located in the postman directory) to send API requests.
- Before making any API requests, you must first authenticate by sending a "login" request. This will return a JWT token.
- Use this JWT token as a Bearer token in the Authorization header for all subsequent API requests.

## Example

- Send a POST request to /login with valid credentials.
- Copy the JWT token from the response.
- For all other requests, include this token in the Authorization header:

    ``
    Authorization: Bearer <your-jwt-token>
    ``