# 🧠 Microservices System Design for Deepfake Detection

<p align="center">
  <img src="https://img.shields.io/badge/17-green?style=flat&logo=java&label=Java" />
  <img src="https://img.shields.io/badge/3.9.9-green?style=flat&logo=apachemaven&logoColor=green&label=Maven" />
  <img src="https://img.shields.io/badge/3.5.3-green?style=flat&logo=springboot&label=SpringBoot" />
  <img src="https://img.shields.io/badge/2025.0.0-green?style=flat&logo=spring&label=Spring%20Cloud" />
  <img src="https://img.shields.io/badge/3.12-green?style=flat&logo=python&logoColor=%233776AB&label=Python" />
  <img src="https://img.shields.io/badge/3.1.1-green?style=flat&logo=flask&logoColor=%233BABC3&label=Flask" />
  <img src="https://img.shields.io/badge/3.1.0-green?style=flat&logo=openapiinitiative&logoColor=%236BA539&label=OpenAPI&color=%236BA539" />
  <img src="https://img.shields.io/badge/v1.1-green?style=flat&logo=cloudinary&logoColor=%233448C5&label=Cloudinary" />
  <img src="https://img.shields.io/badge/v1.1-green?style=flat&logo=apachekafka&logoColor=%23231F20&label=Kafka" />
  <img src="https://img.shields.io/badge/8-green?style=flat&logo=mysql&logoColor=%234479A1&label=MySQL" />
  <img src="https://img.shields.io/badge/27.5.1-green?style=flat&logo=docker&logoColor=%232496ED&label=Docker" />
</p>


## 📌 Project Description

This project showcases a cloud-native, microservices-based architecture for detecting **deepfakes** in uploaded media using a scalable and modular approach. The system is built with modern technologies such as **Spring Boot**, **React**, **Kafka**, **Python (Flask)**, **Cloudinary**, **MySQL**, and **Docker**.


## 📸 System Architecture
  <img src="./diagrams/system-design-capstone.gif" alt="System Design Diagram" />


## 🧱 Microservice Modules Breakdown

### 🖥️ Frontend

* Built using **React.js**
* Connects with backend via API Gateway



### 🧭 API Gateway

Handles all client requests and routes them to the appropriate service.


* ` /api/v1/auth`
* ` /api/v1/classify`
* ` /api/v1/history`



### 🔐 Authentication Service

* Manages user login/signup
* Uses **MySQL** for credential storage



### ☁️ Upload Service

* Handles image/video uploads to **Cloudinary**



### 🧪 Classify Handler Service

* Coordinates:

  * Media upload
  * Deepfake analysis
  * Notification sending
  * History logging



### 🧠 Classify Service

* Implements AI model in **Python + Flask**
* REST-based deepfake detection
* Publishes results via **Kafka**



### 🔔 Notification Service

* Sends alerts (email, SMS, push)
* Kafka consumer



### 🕒 History Service

* Saves user action and classification logs in **MySQL**
* Supports history retrieval via API



### 🧭 Service Discovery (Eureka)

* Registers all services dynamically for load balancing and discovery



### 🧾 Config Server

* Centralized configuration system using **Spring Cloud Config**
* Syncs with a **Git-based** configuration repo



### 📊 Observability Stack

* **Zipkin** for distributed tracing
* **ELK Stack** (Elasticsearch + Logstash + Kibana) for logging and analysis



## ⚙️ Tech Stack Overview

| Category          | Technology                |
| ----------------- | ------------------------- |
| Frontend          | React.js                  |
| Microservices     | Spring Boot (Java)        |
| AI Service        | Python + Flask            |
| Storage           | Cloudinary                |
| Messaging Queue   | Apache Kafka              |
| Database          | MySQL                     |
| Configuration     | Spring Cloud Config + Git |
| Service Discovery | Eureka                    |
| Tracing           | Zipkin                    |
| Logging           | ELK Stack                 |
| Containerization  | Docker                    |
