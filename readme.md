# Chessora

World-class chess game built with modern web technologies. Chessora offers a seamless and interactive experience for chess enthusiasts of all levels. Whether you're a beginner looking to learn the basics or an advanced player seeking challenging opponents, Chessora has something for everyone.

## Features we offer :

- **Multiplayer**: Play against friends or other players from around the world.
- **Tournaments**: Participate in various tournaments to test your skills.
- **Room Support**: Create or join rooms to play with specific players.
- **Chat**: Communicate with other players during gameplay.

## Technologies Used:

- **Frontend**: Svelte Kit, Tailwind CSS
- **Backend**: Java, Spring Boot, Spring Security, Java Mail Service
- **Database**: PostgreSQL
- **Developer Tools**: Lombok, JUnit, Mockito
- **WebSocket**: For real-time communication between players.
- **Authentication**: JWT (JSON Web Tokens) for secure user authentication and session management.
- **Deployment**: Docker

## Getting Started

To get started with Chessora, follow these steps:

1.**Clone the repository**: Use the following command to clone the Chessora repository to your local machine:

```bash
   git clone https://github.com/princepal-dev/chessora.git
```

2. **Navigate to the project directory**: Change into the cloned repository's directory:

```bash
  cd chessora
```

3. **Install dependencies**: Install the required dependencies for both frontend and backend:

```bash
  # For frontend
  cd frontend
  npm install

  # For backend
  cd ../backend
  ./mvnw install
```

4. **Configure environment variables**: Set up the necessary environment variables for the backend, such as database connection details and JWT secret keys.

5. **Run the application**: Start the frontend and backend servers:

```bash
  # Start backend server
  cd backend
  ./mvnw spring-boot:run

  # Start frontend server
  cd ../frontend
  npm run dev
```

### Contributing
We welcome contributions from the community! If you'd like to contribute to Chessora, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them with clear messages.
4. Push your changes to your forked repository.
5. Open a pull request to the main repository.
6. Wait for your pull request to be reviewed and merged.

Created by love by **Prince Pal** for chess, Chessora is a collaborative effort to bring the joy of chess to players worldwide. Join us in making Chessora even better!