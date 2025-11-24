🏏 CREX – Cricket Buzz Backend Application (Spring Boot + MySQL + JWT Security)
- CREX is a cricket information backend system built using Spring Boot, MySQL, and JWT Authentication, designed to manage tournaments, series, matches, teams, and players — similar to platforms like Cricbuzz / ESPNcricinfo.
- This application supports role-based access, email notifications, and real-time cricket match management.

Key Features :
| Module                  | Capabilities                                                                   |
| ----------------------- | ------------------------------------------------------------------------------ |
| **Authentication**      | User Registration, Login, Logout (JWT Based), Role-Based Access (USER / ADMIN) |
| **Teams**               | Add, Update, Delete, View Teams (Authenticated users)                          |
| **Players**             | Add, Update, Delete, View Players (Team-based mapping + Email notifications)   |
| **Match System**        | Store match info, live score, toss, result, schedule & status                  |
| **Tournament**          | Admin can manage tournaments and view standings                                |
| **Series**              | Admin can manage international/domestic cricket series                         |
| **Email Notifications** | Emails sent on user registration, login, logout, and player creation           |
