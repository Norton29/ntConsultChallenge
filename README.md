# NtConsult Challenge

![Status](https://img.shields.io/badge/Status-Up-brightgreen)

## Description
NtConsult Challenge is an API designed to create a space for agenda voting. The application facilitates the creation, voting, and result management for different agendas.

## Technologies Used
- **Java 21**
- **Spring Boot 3.4**
- **Relational Database**: MySQL
- **Messaging Service**: RabbitMQ

## How to Install and Run

### Cloud Deployment
The API is provisioned in the cloud and ready for use at the followinf link: 
[NtConsult Challenge API](https://nt-consult-desafio-8143d06b9be2.herokuapp.com)

Its documentation is available through Swagger at the following link:
[Swagger Documentation](https://nt-consult-desafio-8143d06b9be2.herokuapp.com/swagger-ui.html)

### Local Setup
If you wish to run the application locally, follow the steps below:

#### Prerequisites
1. **Java Development Kit (JDK)**: Version 21 or higher.
2. **Maven**: Ensure Maven is installed and configured in your system.
3. **Docker**: Ensure Docker is installed and running on your system.

#### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/Norton29/ntConsultChallenge.git
   ```

2. Start MySQL and RabbitMQ using Docker:
   - Run the provided docker-compose.yml file:
   ```bash
   docker-compose up -d
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```


5. Access the application:
   - Open your browser and navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to view the API documentation.

---

## Example of Use
1. **Create Agendas**: Users can create agendas they wish to put up for voting.
2. **Set Voting Period**: Define how long an agenda will be open for voting.
3. **Vote**: Registered associates can cast a single vote ("yes" or "no") per agenda.
4. **Messaging**: At the end of the voting period, the results are sent to a messaging queue (RabbitMQ) for further processing by other parts of the application.

---

## Notes
- The implementation for accessing the external API proposed in the challenge has been included in the code. However, the external API is currently non-functional, and the related code section has been commented out.

---
