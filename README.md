# 🛳️ NCL Deck Booker - POC

A Full-Stack Proof of Concept (POC) demonstrating a **Real-time Cabin Booking System** designed for high concurrency using the **Actor Model**.

> **Goal:** Simulate a high-demand cruise reservation environment where race conditions are prevented by design.

## 🌟 Key Features
* **Real-time Booking:** Prevents double-booking using Akka Actors' sequential mailbox processing.
* **Smart Booking:** "Quick Book" logic that automatically assigns the best available cabin by category (VIP, Standard, Economy).
* **Session Management:** Simulated User Authentication (Login/Logout) with Pinia.
* **Simulation Control:** Admin controls to Reset the entire deck state instantly.

## 🚀 Tech Stack

### Backend (Scala & Akka)
* **Language:** Scala 2.13
* **Core:** Akka Actors (Typed) for state management.
* **API:** Akka HTTP for REST endpoints.
* **Architecture:** Reactive, Non-blocking, In-memory state.

### Frontend (Vue 3)
* **Framework:** Vue 3 (Composition API).
* **State:** Pinia Store.
* **Build:** Vite.
* **Styling:** Custom CSS (Responsive & Nautical Theme).

## 🛠️ How to Run

### 1. Start Backend (Scala)
```bash
cd backend
sbt run
# Server starts at http://localhost:8081

2. Start Frontend (Vue)

cd frontend
npm install
npm run dev
# Client starts at http://localhost:5173

API Endpoints
GET /api/cabins - Fetch deck status.

POST /api/book - Book a cabin (User ID required).

POST /api/reset - Reset simulation state.