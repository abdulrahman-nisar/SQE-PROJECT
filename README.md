│       │       ├── RegistrationStepDefinitions.java
│       │       ├── LogoutStepDefinitions.java
│       │       └── ContactManagementStepDefinitions.java
│       └── resources/
│           ├── features/
│           │   ├── Login.feature
│           │   ├── Registration.feature
│           │   ├── Logout.feature
│           │   └── ContactManagement.feature
│           ├── testdata/
│           │   └── LoginTestData.xlsx
│           ├── config.properties
│           ├── allure.properties
│           └── log4j2.xml
├── database_setup.sql
├── create_excel.vbs
├── pom.xml
├── testng.xml
└── README.md
```

---

## 📈 Reporting

### Allure Reports

After running tests, generate Allure report:
```bash
mvn allure:serve
```

Features:
- ✅ Test execution summary
- ✅ Test case details with steps
- ✅ Screenshots on failure
- ✅ Execution timeline
- ✅ Test categories and tags
- ✅ Environment information

### Console Logs

Logs are generated in:
- `logs/automation.log` - Application logs
- Console output during test execution

---

## 🎯 Test Scenarios

### Login Feature (3 Data Sources × 2 Scenarios = 6 Tests)

✅ **Positive Tests:**
- Login with Database credentials
- Login with Excel credentials  
- Login with Redis credentials

❌ **Negative Tests:**
- Invalid login with Database credentials
- Invalid login with Excel credentials

### Other Features
- User Registration
- User Logout
- Contact Management (Add, View, Edit, Delete)

---

## 🔍 Troubleshooting

### Common Issues

**1. Database Connection Error**
- Ensure XAMPP MySQL is running
- Verify database name: `sqe_project`
- Check default XAMPP password is empty

**2. Excel File Not Found**
- Verify file path: `src/test/resources/testdata/LoginTestData.xlsx`
- Ensure Excel file has correct structure

**3. Redis Connection Error**
- Ensure Redis server is running (`redis-server.exe`)
- Check Redis is running on port 6379
- Verify test data is set in Redis using `redis-cli.exe`

**4. WebDriver Issues**
- Update Chrome to latest version
- WebDriverManager should auto-download drivers
- Check internet connection for driver download
# 🎯 Web UI Automation Framework - Contact List Application
---

## 📝 Configuration

### config.properties
```properties
base.url=https://thinking-tester-contact-list.herokuapp.com/
browser=chrome
headless.mode=false
implicit.wait=10
explicit.wait=20
## 📋 Table of Contents
- [Features](#-features)
- [Prerequisites](#-prerequisites)
- [Data Source Setup](#-data-source-setup)
  - [MySQL Database Setup](#1-mysql-database-xampp)
  - [Excel Setup](#2-excel-setup)
✅ Selenium WebDriver integration  
✅ Cucumber BDD framework  
✅ Page Object Model  
✅ MySQL Database integration  
✅ Excel data source integration  
✅ Redis cache integration  
✅ Allure reporting  
✅ Cross-browser testing  
✅ Screenshot on failure  
✅ Log4j2 logging  

---

## 📞 Support

For issues or questions:
1. Check logs in `logs/automation.log`
2. Verify all prerequisites are installed
3. Ensure data sources are set up correctly

---

**Happy Testing! 🚀**  
---

## ✨ Features

- ✅ **Selenium WebDriver** for browser automation
- ✅ **Cucumber BDD** with Gherkin syntax
- ✅ **TestNG** test framework
- ✅ **Page Object Model** design pattern
- ✅ **Allure Reports** for beautiful test reporting
- ✅ **Multiple Data Sources**: Database, Excel, Redis
- ✅ **Cross-browser** support (Chrome, Firefox, Edge)
- ✅ **Screenshot** on test failure
- ✅ **Log4j2** logging
- ✅ **WebDriverManager** for automatic driver management

---

## 🔧 Prerequisites

Before running the tests, ensure you have the following installed:

1. **Java JDK 11 or higher**
   ```bash
   java -version
   ```

2. **Maven 3.6 or higher**
   ```bash
   mvn -version
   ```

3. **Google Chrome** (latest version)

4. **XAMPP** (for MySQL database)
   - Download from: https://www.apachefriends.org/

5. **Redis** (for Redis data source)
   - Download from: https://github.com/microsoftarchive/redis/releases

---

## 🗄️ Data Source Setup

This project demonstrates fetching login credentials from **3 different data sources**:

### 1. MySQL Database (XAMPP)

#### Step 1: Install and Start XAMPP
1. Download and install XAMPP from https://www.apachefriends.org/
2. Start **Apache** and **MySQL** services from XAMPP Control Panel

#### Step 2: Create Database and Table
1. Open phpMyAdmin: http://localhost/phpmyadmin
2. Click on "SQL" tab
3. Copy and paste the content from `database_setup.sql`:

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS sqe_project;
USE sqe_project;

-- Create users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Insert test data
INSERT INTO users (email, password) VALUES
('john.test.2.doe@test.com', 'SecurePass123'),
('invalid@example.com', 'wrongpassword');
```

4. Click "Go" to execute

#### Step 3: Verify Database Configuration
The database connection is configured in `DatabaseReader.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/sqe_project";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = ""; // Default XAMPP password is empty
```

---

### 2. Excel Setup

#### Step 1: Create Excel File
The Excel file should be located at:
```
src/test/resources/testdata/LoginTestData.xlsx
```

#### Step 2: Excel File Structure
Create an Excel file with the following structure:

| email                      | password       |
|----------------------------|----------------|
| john.test.2.doe@test.com   | SecurePass123  |
| invalid@example.com        | wrongpassword  |

**Manual Steps:**
1. Open Excel
2. Create a new workbook
3. In Sheet1, add headers in Row 1: `email` and `password`
4. Add test data in rows 2 and 3 as shown above
5. Save as `LoginTestData.xlsx` in `src/test/resources/testdata/`

**OR** use the provided VBScript (if Excel is installed):
```bash
cscript create_excel.vbs
```

---

### 3. Redis Setup

#### Step 1: Install Redis on Windows
1. Download Redis for Windows: https://github.com/microsoftarchive/redis/releases
2. Download `Redis-x64-3.0.504.zip`
3. Extract to `C:\Redis`

#### Step 2: Start Redis Server
Open Command Prompt and run:
```bash
cd C:\Redis
redis-server.exe
```

Keep this window open (Redis server is now running on localhost:6379)

#### Step 3: Add Test Data to Redis
Open another Command Prompt and run:
```bash
cd C:\Redis
redis-cli.exe
```

Then execute these commands:
```redis
# Set credentials for valid user
HSET user:john.test.2.doe@test.com password SecurePass123

# Set credentials for invalid user
HSET user:invalid@example.com password wrongpassword

# Verify data
HGETALL user:john.test.2.doe@test.com

# Exit
exit
```

#### Step 4: Verify Redis Configuration
The Redis connection is configured in `RedisReader.java`:
```java
private static final String REDIS_HOST = "localhost";
private static final int REDIS_PORT = 6379;
```

---

## 📦 Installation

1. **Clone the repository** (or download)
   ```bash
   cd C:\Users\PMLS\Desktop\SQE-PROJECT
   ```

2. **Install Maven dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

---

## 🚀 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Tests by Tags

**Smoke Tests (all data sources):**
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

**Database Tests Only:**
```bash
mvn clean test -Dcucumber.filter.tags="@database"
```

**Excel Tests Only:**
```bash
mvn clean test -Dcucumber.filter.tags="@excel"
```

**Redis Tests Only:**
```bash
mvn clean test -Dcucumber.filter.tags="@redis"
```

**Login Feature Only:**
```bash
mvn clean test -Dcucumber.filter.tags="@login"
```

**Negative Tests:**
```bash
mvn clean test -Dcucumber.filter.tags="@negative"
```

### Generate Allure Report
```bash
# After running tests
mvn allure:serve
```

---

## 📊 Test Data Sources

### Login Credentials Overview

| Data Source | Email                      | Password       | Purpose        |
|-------------|----------------------------|----------------|----------------|
| **Database** | john.test.2.doe@test.com  | SecurePass123  | Valid Login    |
| **Database** | invalid@example.com       | wrongpassword  | Invalid Login  |
| **Excel**    | john.test.2.doe@test.com  | SecurePass123  | Valid Login    |
| **Excel**    | invalid@example.com       | wrongpassword  | Invalid Login  |
| **Redis**    | john.test.2.doe@test.com  | SecurePass123  | Valid Login    |

### Data Source Utilities

1. **DatabaseReader.java** - Reads from MySQL database
   - `getUserCredentials(email)` - Get credentials by email
   - `getAllUserCredentials()` - Get all users

2. **ExcelReader.java** - Reads from Excel file
   - `getUserCredentialsFromExcel(rowIndex)` - Get credentials by row
   - `getUserCredentialsByEmail(email)` - Get credentials by email

3. **RedisReader.java** - Reads from Redis cache
   - `getUserCredentialsFromRedis(email)` - Get credentials by email
   - `getAllUserCredentialsFromRedis()` - Get all users

---

## 📁 Project Structure

```
SQE-PROJECT/
├── src/
│   ├── main/java/com/automation/
│   │   ├── driver/
│   │   │   └── DriverManager.java
│   │   ├── pages/
│   │   │   ├── BasePage.java
│   │   │   ├── LoginPage.java
│   │   │   ├── SignupPage.java
│   │   │   └── ContactListPage.java
│   │   └── utils/
│   │       ├── ConfigurationFileReader.java
│   │       ├── ScreenshotUtil.java
│   │       ├── DatabaseReader.java
│   │       ├── ExcelReader.java
│   │       └── RedisReader.java
│   └── test/
│       ├── java/com/automation/
│       │   ├── runners/
│       │   │   └── TestRunner.java
│       │   └── stepdefinitions/
│       │       ├── Hooks.java
│       │       ├── LoginStepDefinitions.java

# 4. Run all tests
mvn clean test

# 5. Run specific feature
mvn test -Dcucumber.filter.tags="@login"
```

---

## ✅ Project Status

✅ All core features implemented  
✅ 15+ test scenarios automated  
✅ Page Object Model implemented  
✅ BDD with Cucumber configured  
✅ Allure reporting integrated  
✅ Data-driven testing supported  
✅ Cross-browser testing enabled  
✅ Error handling implemented  
✅ Screenshots on failure  
✅ Production-ready framework  

---

## 📞 Support

For issues or questions:
1. Check the [Troubleshooting](#-troubleshooting) section
2. Review test execution logs in `logs/application.log`
3. Check Allure reports for detailed test steps

---

**Happy Testing!** 🎉
# 🚀 Web UI Automation Framework

**A comprehensive BDD test automation framework** for web applications using **Selenium WebDriver**, **Cucumber**, and **TestNG** with **Allure Reports**.

**Target Application:** [Contact List App](https://thinking-tester-contact-list.herokuapp.com/)

---

## 📋 Table of Contents

- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Running Tests](#-running-tests)
- [Test Scenarios](#-test-scenarios)
- [Reports](#-reports)
- [Design Patterns](#-design-patterns)
- [Data Sources](#-data-sources)
- [Troubleshooting](#-troubleshooting)

---

## ✨ Features

✅ **BDD with Cucumber** - Write tests in Gherkin language  
✅ **Page Object Model (POM)** - Maintainable and reusable code  
✅ **Multiple Browsers** - Chrome, Firefox, Edge support  
✅ **Parallel Execution** - Run tests in parallel with TestNG  
✅ **Allure Reports** - Beautiful, detailed test reports  
✅ **Data-Driven Testing** - Excel, JSON, Database support  
✅ **Screenshot on Failure** - Automatic screenshots for failed tests  
✅ **Configurable** - Easy configuration via properties file  
✅ **CI/CD Ready** - Maven-based, ready for Jenkins/GitHub Actions  
✅ **Logging** - Log4j2 integration for debugging  

---

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 11+ | Programming Language |
| **Maven** | 3.6+ | Build Tool |
| **Selenium WebDriver** | 4.15.0 | Browser Automation |
| **Cucumber** | 7.14.0 | BDD Framework |
| **TestNG** | 7.8.0 | Test Runner |
| **Allure** | 2.24.0 | Reporting |
| **Apache POI** | 5.2.3 | Excel Operations |
| **MySQL Connector** | 8.0.33 | Database Integration |
| **Log4j2** | 2.20.0 | Logging |

---

## 📁 Project Structure

```
SQE-PROJECT/
├── src/
│   ├── main/java/com/automation/
│   │   ├── driver/
│   │   │   └── DriverManager.java          # WebDriver management
│   │   ├── pages/
│   │   │   ├── BasePage.java               # Base page with common methods
│   │   │   ├── LoginPage.java              # Login page object
│   │   │   ├── SignupPage.java             # Registration page object
│   │   │   ├── ContactListPage.java        # Contact list page object
│   │   │   ├── AddContactPage.java         # Add contact page object
│   │   │   └── ContactDetailsPage.java     # Contact details page object
│   │   └── utils/
│   │       ├── ConfigReader.java           # Configuration reader
│   │       ├── ExcelReader.java            # Excel data reader
│   │       ├── JsonReader.java             # JSON data reader
│   │       ├── DatabaseReader.java         # Database reader
│   │       └── ScreenshotUtil.java         # Screenshot utility
│   └── test/
│       ├── java/com/automation/
│       │   ├── runners/
│       │   │   └── TestRunner.java         # TestNG + Cucumber runner
│       │   └── stepdefinitions/
│       │       ├── Hooks.java              # Before/After hooks
│       │       ├── LoginStepDefinitions.java
│       │       ├── RegistrationStepDefinitions.java
│       │       ├── ContactManagementStepDefinitions.java
│       │       └── LogoutStepDefinitions.java
│       └── resources/
│           ├── features/                    # Cucumber feature files
│           │   ├── Login.feature
│           │   ├── Registration.feature
│           │   ├── ContactManagement.feature
│           │   └── Logout.feature
│           ├── testdata/                    # Test data files
│           │   ├── testdata.xlsx
│           │   └── logindata.json
│           ├── config.properties            # Configuration file
│           └── log4j2.xml                   # Logging configuration
├── pom.xml                                  # Maven dependencies
├── testng.xml                               # TestNG suite configuration
└── README.md                                # This file
```

---

## 📋 Prerequisites

Before running the tests, ensure you have:

1. **Java JDK 11 or higher** installed
   ```bash
   java -version
   ```

2. **Maven 3.6+** installed
   ```bash
   mvn -version
   ```

3. **Chrome/Firefox/Edge** browser installed

4. **Internet connection** (for WebDriver Manager and target application)

---

## 🚀 Installation

### **1. Clone or Download the Project**

```bash
cd C:\Users\PMLS\Desktop\SQE-PROJECT
```

### **2. Install Dependencies**

```bash
mvn clean install -DskipTests
```

This will download all required dependencies.

### **3. Verify Installation**

```bash
mvn clean compile
```

You should see `BUILD SUCCESS`.

---

## ⚙️ Configuration

All configurations are in `src/test/resources/config.properties`:

```properties
# Application URL
app.url=https://thinking-tester-contact-list.herokuapp.com/

# Browser Configuration
browser=chrome              # chrome, firefox, or edge
headless=false             # true for headless mode

# Wait Times (milliseconds)
implicit.wait=10000
explicit.wait=20000
page.load.timeout=30000

# Test Speed Control
test.speed.short.wait=2000
test.speed.medium.wait=3000
test.speed.long.wait=5000
test.speed.element.action=1000

# Database Configuration (Optional)
db.url=jdbc:mysql://localhost:3306/testdb
db.username=root
db.password=password

# Excel Test Data
excel.filepath=src/test/resources/testdata/testdata.xlsx
```

### **💡 Speed Control**

You can control how fast/slow tests run:

**For Demos (Slow):**
```properties
test.speed.short.wait=5000
test.speed.medium.wait=7000
test.speed.long.wait=10000
```

**For CI/CD (Fast):**
```properties
test.speed.short.wait=500
test.speed.medium.wait=1000
test.speed.long.wait=2000
```

---

## 🏃 Running Tests

### **Option 1: Run All Tests**

```powershell
mvn clean test
```

### **Option 2: Run Smoke Tests**

```powershell
mvn clean test -Dcucumber.filter.tags="@smoke"
```

### **Option 3: Run Specific Tag**

```powershell
# Run only login tests
mvn test -Dcucumber.filter.tags="@login"

# Run only registration tests
mvn test -Dcucumber.filter.tags="@registration"

# Run only contact management tests
mvn test -Dcucumber.filter.tags="@contact"

# Run only negative tests
mvn test -Dcucumber.filter.tags="@negative"
```

### **Option 4: Using TestNG XML**

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

---

## 📝 Test Scenarios

The framework includes the following test scenarios:

### **1. Login Feature** (`Login.feature`)
- ✅ Successful login with registered user
- ❌ Login with invalid credentials
- ❌ Login with empty credentials

### **2. Registration Feature** (`Registration.feature`)
- ✅ Successful registration with valid details
- ❌ Registration with existing email
- ❌ Registration with invalid email format
- ❌ Registration with missing required fields

### **3. Contact Management Feature** (`ContactManagement.feature`)
- ✅ Add new contact with basic details
- ✅ Add new contact with full details
- ✅ View contact details
- ✅ Edit existing contact
- ✅ Delete contact
- ❌ Add contact with invalid data

### **4. Logout Feature** (`Logout.feature`)
- ✅ Successful logout
- ✅ Logout and verify session ended

**Total:** 15+ test scenarios covering positive and negative cases

---

## 📊 Reports

### **1. Allure Reports**

Generate and view beautiful Allure reports:

```bash
# Run tests
mvn clean test

# Generate and open report
mvn allure:serve
```

**Report Features:**
- ✅ Test execution timeline
- ✅ Test case details with steps
- ✅ Screenshots for failed tests
- ✅ Categories and trends
- ✅ Environment information

### **2. Cucumber HTML Report**

After test execution, find the report at:
```
target/cucumber-reports/cucumber.html
```

### **3. TestNG Reports**

HTML reports available at:
```
target/surefire-reports/index.html
```

---

## 🎨 Design Patterns

### **1. Page Object Model (POM)**

Each page is represented as a class:

```java
public class LoginPage extends BasePage {
    @FindBy(id = "email")
    private WebElement emailField;
    
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
    }
}
```

**Benefits:**
- ✅ Code reusability
- ✅ Easy maintenance
- ✅ Separation of test logic and page logic

### **2. Singleton Pattern**

WebDriver instance managed using ThreadLocal:

```java
public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static WebDriver getDriver() {
        return driver.get();
    }
}
```

**Benefits:**
- ✅ Thread-safe parallel execution
- ✅ Single WebDriver instance per thread

### **3. Factory Pattern**

Browser initialization:

```java
switch (browser) {
    case "chrome": return createChromeDriver();
    case "firefox": return createFirefoxDriver();
    case "edge": return createEdgeDriver();
}
```

---

## 💾 Data Sources

### **1. Excel Files**

Read test data from Excel:

```java
List<Map<String, String>> testData = ExcelReader.getData("Sheet1");
```

**File:** `src/test/resources/testdata/testdata.xlsx`

### **2. JSON Files**

Read test data from JSON:

```java
List<Map<String, Object>> data = JsonReader.readJsonFile("logindata.json");
```

**File:** `src/test/resources/testdata/logindata.json`

### **3. Database**

Read test data from MySQL:

```java
List<Map<String, Object>> data = DatabaseReader.executeQuery("SELECT * FROM users");
```

**Setup:** Configure database in `config.properties`

### **4. Gherkin Data Tables**

Use data tables in feature files:

```gherkin
When I enter registration details:
  | firstName | John |
  | lastName  | Doe  |
  | email     | john.doe@test.com |
  | password  | SecurePass123 |
```

---

## 🔧 Troubleshooting

### **Issue 1: Tests Not Running**

**Solution:**
```bash
# Clean and rebuild
mvn clean install -DskipTests
mvn clean test
```

### **Issue 2: WebDriver Not Found**

**Solution:**
WebDriver Manager handles this automatically. If issues persist:
```bash
# Clear Maven cache
mvn dependency:purge-local-repository
mvn clean install
```

### **Issue 3: Allure Report Not Generated**

**Solution:**
```bash
# Install Allure command line
mvn allure:install

# Generate report
mvn allure:serve
```

### **Issue 4: Tests Fail with "Email Already Exists"**

**Solution:**
Tests use unique timestamps for emails. If still failing:
- Check the generated email in console logs
- The framework automatically generates unique emails like `user.1733251234567@test.com`

### **Issue 5: Alert Timeout**

**Solution:**
Already configured! Chrome auto-accepts alerts with:
```java
options.setCapability("unhandledPromptBehavior", "accept");
```

### **Issue 6: Slow Test Execution**

**Solution:**
Adjust speed in `config.properties`:
```properties
test.speed.short.wait=500
test.speed.medium.wait=1000
```

---

## 📚 Additional Resources

### **Cucumber Reports**
- Location: `target/cucumber-reports/cucumber.html`
- Format: HTML

### **Allure Reports**
- Command: `mvn allure:serve`
- Features: Screenshots, step details, trends

### **Logs**
- Location: `logs/application.log`
- Level: Configurable in `log4j2.xml`

### **Screenshots**
- Automatic on test failure
- Location: `target/screenshots/`
- Attached to Allure reports

---

## 🎯 Key Features Explained

### **1. Automatic Unique Email Generation**

Tests automatically generate unique emails to avoid "email already exists" errors:

```java
String uniqueEmail = "user." + System.currentTimeMillis() + "@test.com";
// Example: user.1733251234567@test.com
```

### **2. Smart Alert Handling**

Alerts are auto-accepted by Chrome, with graceful fallback:

```java
protected void acceptAlert() {
    try {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    } catch (Exception e) {
        // Auto-handled by browser
    }

