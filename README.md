# Simple Credit Card Fraud Detection

This is a simple credit card fraud detection service that determines whether 
certain transactions from a card number is fraudulent or not. This service
provides simple REST API to determine whether a transaction for an specific 
card number is fraudulent. In addition, it includes a Dockerfile for containerization.

## Prerequisites

* Have JDK 19 or later installed.
* Have Gradle 8.0 or later installed.
* Have Docker installed

## Running the Application

To build the application, use the following command

`gradle build`

The build artifacts will be stored in the build/libs/ directory.

## Running the Docker Container

To build the Docker image, use the following command:

`docker build -t mctakehome.jar`

To run the Docker container, use the following command:

`docker run -p 8080:8080 mctakehome.jar`

This will start the container <u>port 8080</u>, and the REST API can be accesed at
<u>http://localhost</u>

## How to access the API

The API has a single endpoint, `/analyzeTransaction` Once the project is built and running, 
use the following command to use this endpoint:

`curl -X POST http://localhost:8080/analyzeTransaction -H "Content-Type: application/json -d [body]}`

where body is of the form `{ 'cardNum': xxxxxxxxxxxxxxxx, 'amount' : 0.0 }`

<b>cardNum must have 16 digits and amount must be greater than 0</b>

Example curl command:

`curl -X POST http://localhost:8080/analyzeTransaction -H "Content-Type: application/json -d '{"cardNum": 1234888833339876,"amount":12.0 }'`