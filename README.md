# Concurrent RPC Server with Middleware in C

## Description

This project is a Concurrent **RPC Server** implemented in C, a **Middleware component (RPC Client)** to manage user interactions and facilitate communication between clients and the server and a **TCP Client** implemented with C Sockets. The server offers 3 computational tasks with vectors using Remote Procedure Calls (RPC) and serves multiple clients. The architecture includes the following key components:

- **Server:** The server hosts the procedures implemented for various calculations.
- **Middleware:** The middleware handles user input, processes requests, and manages the flow of data between clients and the server. **It is the RPC client program that acts as a middleware.**
- **TCP Client:** The client program provides a menu for clients to select a calculation, input data, and display results.

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
   
2. Compile the server, middleware, and client programs. 
  ```bash
    make 
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

  ```bash
    ./tcp_client <server_hostname> <server_port>
  ```
Again, replace server_hostname and server_port with the actual hostname and port number of the server.
## RPC Implementation
- The RPC interface definitions are provided in the .x files.
- Use rpcgen to generate the server and client stubs.
- Implement the RPC server functions in the vector_calc_server.c file.
- Incorporate the RPC client functions into the vector_calc_client.c file.
