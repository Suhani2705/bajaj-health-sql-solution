# Bajaj Finserv Health | Qualifier 1 (JAVA)

##  Overview
This Spring Boot application:
1. Registers a webhook with the Bajaj API on startup.
2. Retrieves a webhook URL and JWT access token.
3. Solves **SQL Question 2** (since regNo is even).
4. Submits the final SQL query to the given webhook URL with JWT authorization.

---

## How to Run
```bash
# Clone repo
git clone https://github.com/Suhani2705/bajaj-health-sql-solution.git
cd bajaj-health-sql-solution

# Build JAR
mvn clean package

# Run JAR
java -jar target/bajaj-health-sql-solution-1.0.0.jar
