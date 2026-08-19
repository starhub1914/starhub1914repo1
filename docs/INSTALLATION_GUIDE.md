# INSTALLATION & ADMINISTRATION GUIDE
## Software Development Document Environment (SDM)

### 1. System Requirements
- **Java Runtime:** OpenJDK 21 or higher
- **Build Tool:** Apache Maven 3.9+
- **Python Runtime (For RAG AI):** Python 3.10+ (with Ollama installed)
- **Supported Databases:** H2 (In-memory), PostgreSQL, Oracle, Microsoft SQL Server

---

### 2. Quick Start - Execution Scripts

#### Windows Environment
1. **Build Application:**
   ```cmd
   build.bat
   ```
2. **Start Application:**
   ```cmd
   start.bat
   ```
3. **Run JUnit Tests:**
   ```cmd
   run_tests.bat
   ```
4. **Stop Application:**
   ```cmd
   stop.bat
   ```

#### Linux Environment
1. **Make scripts executable:**
   ```bash
   chmod +x start.sh stop.sh run_tests.sh
   ```
2. **Start Application:**
   ```bash
   ./start.sh
   ```
3. **Run JUnit Tests:**
   ```bash
   ./run_tests.sh
   ```
4. **Stop Application:**
   ```bash
   ./stop.sh
   ```

---

### 3. Database Switching Configuration
Set active profile in `application.yml` or via command line parameter:
- **Oracle:** `--spring.profiles.active=oracle`
- **PostgreSQL:** `--spring.profiles.active=postgres`
- **SQL Server:** `--spring.profiles.active=sqlserver`
- **H2 (Default):** `--spring.profiles.active=h2`

---

### 4. Docker Deployment
Run full stack (Spring Boot + PostgreSQL + Ollama + Flask RAG):
```bash
docker-compose up -d --build
```
