#  Pokemon Card Collection API

A robust RESTful API built with **Kotlin** and **Spring Boot** for managing and retrieving Pokemon Trading Card Game (TCG) data.

This project demonstrates a full-stack backend architecture, complete with a **PostgreSQL** database, **AWS Cloud** hosting, and an automated **CI/CD pipeline** using GitHub Actions.

---

## 🚀 Live Demo
**Base URL:** `http://15.134.213.49:8080`
*(Note: This is hosted on an AWS EC2 instance. If the link is down, the instance may be stopped to save costs.)*

---

## 🛠 Tech Stack & Infrastructure

### Backend
* **Language:** Kotlin
* **Framework:** Spring Boot 3
* **Build Tool:** Gradle
* **Database:** PostgreSQL (AWS RDS)

### DevOps & Cloud
* **Cloud Provider:** AWS (Amazon Web Services)
* **Compute:** EC2 (Linux/Amazon Linux 2023)
* **Database Host:** RDS (Relational Database Service)
* **CI/CD:** GitHub Actions (Automated build & deploy)
* **Security:** AWS Security Groups (Strict Allow-listing)

---

## 📖 API Documentation

### 1. Cards

  ** Retrieves all cards belonging to a specific set ID.

  * **Endpoint:** `GET /db/sets/{setId}/cards`
  * **Example:** `GET /db/sets/base1/cards`
  * **Response Example:**
      ```json
     [
      {
        "id": "base1-1",
        "localId": "1",
        "name": "Alakazam",
        "imageUrl": "[https://assets.tcgdex.net/en/base/base1/1/high.webp](https://assets.tcgdex.net/en/base/base1/1/high.webp)",
        "set": {
          "id": "base1",
          "name": "Base Set",
          "logoUrl": "[https://assets.tcgdex.net/en/base/base1/logo.webp](https://assets.tcgdex.net/en/base/base1/logo.webp)"
        }
      }
    ]
      ```
  ** Retrieves specific card by its ID.
  * **Endpoint:** `GET /card/{id}`
  * **Example:** `GET /card/swsh3-136`

  ** Search Cards
  * **Endpoint:** `GET /cards`
  * **Query Params:**
      * `name` (optional)
      * `page` (default: 1)
  * **Example:** `GET /cards?name=Pikachu&page=1`

### 2. Sets

  Retrieves a list of all Pokemon TCG sets (e.g., "151", "Obsidian Flames").

  * **Endpoint:** `GET /db/sets`
  * **Response Example:**
      ```json
    [
      {
        "id": "base1",
        "name": "Base Set",
        "logoUrl": "[https://assets.tcgdex.net/en/base/base1/logo.webp](https://assets.tcgdex.net/en/base/base1/logo.webp)",
        "symbolUrl": null,
        "cardCountTotal": 102,
        "cardCountOfficial": 102
      },
      {
        "id": "base2",
        "name": "Jungle",
        "logoUrl": "[https://assets.tcgdex.net/en/base/base2/logo.webp](https://assets.tcgdex.net/en/base/base2/logo.webp)",
        "symbolUrl": "[https://assets.tcgdex.net/univ/base/base2/symbol.webp](https://assets.tcgdex.net/univ/base/base2/symbol.webp)",
        "cardCountTotal": 64,
        "cardCountOfficial": 64
      }
    ]
      ```
  ** Get Set Details**
  * **Endpoint:** `GET /set/{id}`
  * **Example:** `GET /set/swsh3`

  ** Search Sets**
  * **Endpoint:** `GET /sets`
  * **Query Params:** `name` (optional)
  * **Example:** `GET /sets?name=Darkness`

  ** Get Card by Set & Local ID**
  * **Endpoint:** `GET /set/{setId}/{localId}`
  * **Example:** `GET /set/swsh3/136`


### 3. Series

  ** Get Series Details**
  * **Endpoint:** `GET /series/{id}`
  * **Example:** `GET /series/swsh`

  ** Search Series**
  * **Endpoint:** `GET /series`
  * **Query Params:** `name` (optional)
  * **Example:** `GET /series?name=Sword`

---

##📚 Data Source & Reference
This project proxies and utilizes data from the TCGDex API.

Official Website: TCGDex.net

API Reference: For detailed information regarding Card IDs, Names, and specific data structures, please refer to the official documentation:

https://tcgdex.dev/reference/card#pok%C3%A9mon-cards

## 💻 Local Development Setup

To run this project locally on your machine:

**Prerequisites:**
* Java JDK 21
* PostgreSQL installed locally (or access to a remote DB)

**1. Clone the repo**

  * git clone [https://github.com/your-username/pokemon-card-server.git](https://github.com/your-username/pokemon-card-server.git)
  * cd pokemon-card-server
       
**2. Configure Database**

  * Update src/main/resources/application.properties with your local database credentials:

  * spring.datasource.url=jdbc:postgresql://localhost:5432/pokemon_db
  * spring.datasource.username=postgres
  * spring.datasource.password=your_password
  
**3. Run the Server**
   
  * ./gradlew bootRun

  * The server will start at http://localhost:8080


