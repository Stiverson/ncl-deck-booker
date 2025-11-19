# 🛳️ NCL Deck Booker - POC

Proof of Concept (POC) developed to demonstrate a **Real-time Cabin Booking System** using the **Actor Model** for high concurrency and a reactive Frontend.

## 🚀 Tech Stack

### Backend (The Core)
* **Language:** Scala 2.13
* **Framework:** Akka HTTP & Akka Actors (Typed)
* **Architecture:** Actor Model (Stateful in-memory management for race-condition free bookings)

### Frontend (The Client)
* **Framework:** Vue 3 (Composition API)
* **State Management:** Pinia
* **Build Tool:** Vite
* **HTTP Client:** Axios
* **Styling:** CSS 3 (Custom Responsive Grid)

## 🧠 Why this Architecture?

Traditional MVC architectures often struggle with race conditions in high-demand scenarios (like thousands of users trying to book the same cabin simultaneously).

By using **Akka Actors**, requests are processed sequentially in a mailbox, ensuring data consistency without complex database locks. This POC demonstrates:
1.  **State Isolation:** Each actor manages its own state.
2.  **Non-blocking I/O:** Leveraging Akka HTTP for high throughput.
3.  **Reactive UI:** Vue 3 updates instantly based on backend responses.

## 🛠️ How to Run

### 1. Start Backend (Scala)
```bash
cd backend
sbt run
# Server will start at http://localhost:8081

2. Start Frontend (Vue)

cd frontend
npm install
npm run dev
# Client will start at http://localhost:5173

🧪 API Endpoints
GET /api/cabins - List all cabin statuses.

POST /api/book - Attempt to book a cabin.