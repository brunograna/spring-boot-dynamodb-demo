<h1 align="center">
    <img alt="Apache Kafka" src="https://github.com/brunograna/spring-boot-dynamodb-demo/blob/master/dynamodb-logo.png" width="300px" />
</h1>

<h3 align="center">
  Project: Spring Boot DynamoDB Demo
</h3>

<p align="center">

  <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-%2304D361">
  <img alt="Language: Java" src="https://img.shields.io/badge/language-java-green">
  <img alt="Version: 1.0" src="https://img.shields.io/badge/version-1.0-yellowgreen">

</p>

## :rocket: Project Features

* CRUD on DynamoDB
* DynamoDB integration tests using [Java TestContainer](https://github.com/testcontainers/testcontainers-java) _(docker needed)_

:mag: Download the project and test by yourself.

## :dart: Development objective

- Java 11 with Spring Boot 2.4.5 and DynamoDB
- CRUD operations on restfull endpoints
- Tests using [TestContainer](https://github.com/testcontainers/testcontainers-java)

## :file_folder: Resources

**Base Url**

*Annotations Route*

```
${HOST_URL}/dynamodb/v1
```

## /foods

**Restfull CRUD Endpoint**

```
${HOST_URL}/dynamodb/v1/foods
```
    
**Json Schema Definition:**
```
type: object
properties:
  id:
    type: string
    description: food id
  name:
    type: string
    description: food name
  weight:
    type: number
    description: food weight
```

---

Developed by Bruno Garcia :wave: [More about me](https://www.linkedin.com/in/dev-brunogarcia/)