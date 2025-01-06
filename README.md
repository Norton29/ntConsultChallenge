# Ntconsult Challenge

## Description
NtConsult Challenge is an API designed to create a space for agenda voting. The application facilitates the creation, voting, and result management for different agendas.

## Technologies Used
- **Java 21**
- **Spring Boot 3.4**
- **Relational Database**: MySQL
- **Messaging Service**: RabbitMQ

## How to Install and Run
The API is provisioned in the cloud and ready for use. Its documentation is available through Swagger at the following link:

[Swagger Documentation](https://nt-consult-desafio-8143d06b9be2.herokuapp.com/swagger-ui.html)

## Example of Use
1. **Create Agendas**: Users can create agendas they wish to put up for voting.
2. **Set Voting Period**: Define how long an agenda will be open for voting.
3. **Vote**: Registered associates can cast a single vote ("yes" or "no") per agenda.
4. **Messaging**: At the end of the voting period, the results are sent to a messaging queue (RabbitMQ) for further processing by other parts of the application.

---
