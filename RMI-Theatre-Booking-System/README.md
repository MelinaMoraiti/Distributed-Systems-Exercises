# Distributed Theatre Ticket Reservation System

This project is a Java RMI-based application for reserving theatre tickets. It consists of a server (THServer), a client (THClient), a remote interface (THInterface), its implementation (THImpl), a Reservation and a SeatInfo class.

## Running the Application
To compile the project, execute the following command:
```bash
javac *.java
```
After compilation, start the server by running:
```bash
java THServer
```
Please ensure the server is running before starting the client application to interact with the theater booking system.
### Client Commands
- `java THClient`: Displays instructions on available commands.
- `java THClient list <hostname>`: Lists available seat types, their codes, and prices for a given hostname.
- `java THClient book <hostname> <type> <number> <name>`: Reserves <number> seats of type <type> for user <name>.
- `java THClient guests <hostname>`: Displays a list of customers and their reservations for a given hostname.
- `java THClient cancel <hostname> <type> <number> <name>`: Cancels <number> seats of type <type> reserved by user <name>.

## System Enhancements

The system additionally supports:
- Subscriptions for notifications on failed booking attempts for specific seat types.
- Immediate notifications upon seat cancellation to subscribed clients who have registered for updates regarding the specific seat type.

## Seat Types Available
- Zone A: 100 seats, 45 EUR each
- Zone B: 200 seats, 35 EUR each
- Zone C: 400 seats, 25 EUR each
- Central Balcony: 225 seats, 30 EUR each
- Side Boxes: 75 seats, 20 EUR each

