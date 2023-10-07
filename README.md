# Java Blockchain

![Java](https://img.shields.io/badge/Java-17-orange.svg)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

A simple Java blockchain implementation using Gson and Bouncy Castle.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
- [Dependencies](#dependencies)
- [Contributing](#contributing)

## Introduction

This project is a basic blockchain implementation in Java. It utilizes the Gson library for JSON serialization/deserialization and the Bouncy Castle library for cryptographic operations. With this blockchain, you can create and manage a decentralized ledger of transactions.

## Features

- Blockchain data structure
- Block mining with Proof of Work (PoW)
- Transaction creation and validation
- Cryptographic hashing and digital signatures
- JSON serialization/deserialization of blockchain and transactions
- Simple command-line interface (CLI) for interacting with the blockchain

## Getting Started

1. **Clone the repository:**

   ```bash
   git clone https://github.com/carlosportella16/my-chain.git
   cd my-chain
Build the project:

Ensure you have Java 11 or higher installed.

bash
Copy code
javac -d out src/main/java/*.java
Run the blockchain:

bash
Copy code
java -cp out Main
This will start the blockchain node.

## Dependencies
Gson: A Java library for JSON serialization/deserialization.
Bouncy Castle: A Java library for cryptographic operations.
These dependencies are managed using Maven, and you can find the specific versions in the pom.xml file.

## Contributing
Contributions are welcome! If you'd like to contribute to this project, please follow these guidelines:

Fork the repository.
Create a new branch for your feature or bug fix.
Make your changes and test them thoroughly.
Submit a pull request with a clear description of your changes.
