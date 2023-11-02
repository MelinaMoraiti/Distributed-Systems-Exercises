# Concurrent RPC Server with Middleware in C

## Description

This project is a Concurrent Server implemented in C that incorporates a Middleware component to manage user interactions and facilitate communication between clients and the server. The server offers multiple computational tasks using Remote Procedure Calls (RPC) and serves multiple clients. The architecture includes the following key components:

- **Server:** The server hosts the procedures implemented for various calculations.
- **Middleware:** The middleware handles user input, processes requests, and manages the flow of data between clients and the server.
- **TCP Client:** The client program provides a user-friendly menu for clients to select a calculation, input data, and display results.

## Features

- Concurrent Server: The server can handle multiple client connections simultaneously.
- Middleware Interface: The middleware component provides an interface for clients to select and send their computation requests.
- Remote Procedure Calls (RPC): RPC is used to perform the calculations, with the server acting as an RPC client.
- User Interaction: Clients interact with the middleware to select a calculation choice and input data.
- Modular Design: The project is organized into server, middleware, and client components, making it extensible and maintainable.

## Usage

### Prerequisites

- C Compiler (e.g., GCC)
- ONC RPC Library

### Building and Running

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/YourUsername/Concurrent-RPC-Server-with-Middleware.git
   cd vector_calculator
   ```
   
2. Compile the server, middleware, and client programs. Ensure you install the necessary dependencies if required.
  ```bash
    make all
  ```
3. Start the server:

  ```bash
   ./vector_calc_server
  ```
4. Start the middleware component, specifying the server's hostname and port number:

  ```bash
    ./vector_calc_client <server_hostname> <server_port>
  ```
Replace server_hostname and server_port with the actual hostname and port number of the server.

5. Start one or more clients in separate terminal windows, specifying the server's hostname and port number:

  bash
  ```bash
    ./tcp_client <server_hostname> <server_port>
  ```
Again, replace server_hostname and server_port with the actual hostname and port number of the server.
