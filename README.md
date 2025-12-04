# 🎯 Contact List - Web Automation Framework

> **Advanced Test Automation Framework** with BDD, Multiple Data Sources, and Comprehensive Reporting

A production-ready Selenium WebDriver automation framework built with **Cucumber BDD**, **TestNG**, and **Allure Reporting**. Demonstrates enterprise-level test automation patterns including **Page Object Model**, **Multi-Source Data-Driven Testing** (Database, Excel, Redis), and automated screenshot capture on failures.

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.15.0-green.svg)](https://www.selenium.dev/)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.14.0-brightgreen.svg)](https://cucumber.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.8.0-red.svg)](https://testng.org/)
[![Allure](https://img.shields.io/badge/Allure-2.24.0-yellow.svg)](https://docs.qameta.io/allure/)

---

## 📋 Table of Contents
- [✨ Key Features](#-key-features)
- [🏗️ Framework Architecture](#️-framework-architecture)
- [🔧 Prerequisites](#-prerequisites)
- [🗄️ Data Source Setup](#️-data-source-setup)
- [📦 Installation](#-installation)
- [🚀 Running Tests](#-running-tests)
- [📊 Test Scenarios](#-test-scenarios)
- [📁 Project Structure](#-project-structure)
- [📈 Reporting](#-reporting)
- [⚙️ Configuration](#️-configuration)
- [🔍 Troubleshooting](#-troubleshooting)

---

## ✨ Key Features

### 🎭 **BDD with Cucumber**
- Gherkin syntax for readable test scenarios
- Business-friendly feature files
- Reusable step definitions

### 🎨 **Page Object Model (POM)**
- Clean separation of page logic and test logic
- Maintainable and scalable code structure
- Reusable page components

### 📊 **Multiple Data Sources**
- **MySQL Database** - Production-like data storage
- **Excel Files** - Easy data management for testers
- **Redis Cache** - Fast in-memory data access

### 📸 **Automated Screenshots**
- Captures screenshots on test failures
- Attached to Allure reports automatically
- Configurable via properties file

### 📈 **Advanced Reporting**
- Allure Framework integration
- Beautiful HTML reports with charts
- Detailed step-by-step execution logs
- Screenshot attachments for failures

### 🔧 **Configuration-Driven**
- Centralized config.properties
- Easy environment switching
- No hardcoded values

### 🌐 **Cross-Browser Support**
- Chrome (default)
- Firefox
- Edge
- Headless mode support

---

## 🏗️ Framework Architecture

```
├── Page Object Model (POM)
│   ├── BasePage (Common functionality)
│   ├── LoginPage
│   ├── SignupPage
│   ├── ContactListPage
│   ├── AddContactPage
│   └── ContactDetailsPage
│
├── Test Layer (BDD)
│   ├── Feature Files (Gherkin)
│   ├── Step Definitions
│   ├── Hooks (Setup/Teardown)
│   └── Test Runner
│
├── Utilities
│   ├── DriverManager (WebDriver lifecycle)
│   ├── ConfigurationFileReader
│   ├── DatabaseReader (MySQL)
│   ├── ExcelReader (Apache POI)
│   ├── RedisReader (Jedis)
│   └── ScreenshotUtil (Allure integration)
│
└── Reporting
    ├── Allure Reports
    ├── Cucumber HTML Reports
    └── Log4j2 Logs
```

**Design Patterns Used:**
- ✅ Page Object Model
- ✅ Singleton (DriverManager)
- ✅ Factory (Data Reader utilities)
- ✅ Data-Driven Testing

---

## 🔧 Prerequisites

Before running the tests, ensure you have:

| Requirement | Version | Download Link |
|------------|---------|---------------|
| **Java JDK** | 11 or higher | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) |
| **Maven** | 3.6+ | [Apache Maven](https://maven.apache.org/download.cgi) |
| **Google Chrome** | Latest | [Chrome](https://www.google.com/chrome/) |
| **XAMPP** | Latest | [XAMPP](https://www.apachefriends.org/) |
| **Redis** | 3.0+ | [Redis for Windows](https://github.com/microsoftarchive/redis/releases) |
| **Git** (optional) | Latest | [Git](https://git-scm.com/downloads) |

**Verify Installation:**
```bash
java -version      # Should show Java 11+
mvn -version       # Should show Maven 3.6+
```

---

## 🗄️ Data Source Setup

This framework demonstrates fetching login credentials from **3 different data sources**. Follow these steps to set them up:

### 1️⃣ MySQL Database (XAMPP)

#### **Install XAMPP**
1. Download from: https://www.apachefriends.org/
2. Install with default settings
3. Open **XAMPP Control Panel**
4. Click **Start** for **Apache** and **MySQL**

#### **Create Database**
1. Open browser: http://localhost/phpmyadmin
2. Click **SQL** tab
3. Copy and run the script from `database_setup.sql`:

```sql
CREATE DATABASE IF NOT EXISTS sqe_project;
USE sqe_project;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

INSERT INTO users (email, password) VALUES
('john.test.2.doe@test.com', 'SecurePass123'),
('invalid@example.com', 'wrongpassword');
```

4. Click **Go** to execute

#### **Verify Database**
- Database `sqe_project` exists
- Table `users` has 2 rows of test data

---

### 2️⃣ Excel File Setup

#### **Create Excel File**
1. Open **Microsoft Excel**
2. Create a new workbook
3. Add data as follows:

| email | password |
|-------|----------|
| john.test.2.doe@test.com | SecurePass123 |
| invalid@example.com | wrongpassword |

4. **Save As:**
   - File name: `LoginTestData.xlsx`
   - Location: `src/test/resources/testdata/`
   - Type: Excel Workbook (*.xlsx)

**OR use the VBScript:**
```bash
cscript create_excel.vbs
```

---

### 3️⃣ Redis Setup

#### **Install Redis**
1. Download: https://github.com/microsoftarchive/redis/releases
2. Get `Redis-x64-3.0.504.zip`
3. Extract to `C:\Redis`

#### **Start Redis Server**
Open Command Prompt:
```bash
cd C:\Redis
redis-server.exe
```
Keep this window **open** (Redis is now running on port 6379)

#### **Add Test Data**
Open **another** Command Prompt:
```bash
cd C:\Redis
redis-cli.exe
```

Execute these commands:
```redis
HSET user:john.test.2.doe@test.com password SecurePass123
HSET user:invalid@example.com password wrongpassword
HGETALL user:john.test.2.doe@test.com
exit
```

**OR use the batch script:**
```bash
setup_redis.bat
```

---

## 📦 Installation

### **Clone/Download Project**
```bash
cd C:\Users\PMLS\Desktop\SQE-PROJECT
```

### **Install Maven Dependencies**
```bash
mvn clean install -DskipTests
```

This will download:
- Selenium WebDriver 4.15.0
- Cucumber 7.14.0
- TestNG 7.8.0
- Allure 2.24.0
- Apache POI (Excel)
- MySQL Connector
- Jedis (Redis)
- Log4j2
- And all other dependencies

---

## 🚀 Running Tests

### **Run All Tests**
```bash
mvn clean test
```

### **Run Tests by Tag**

**Smoke Tests:**
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

**Login Tests (All Data Sources):**
```bash
mvn clean test -Dcucumber.filter.tags="@login"
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

**Registration Tests:**
```bash
mvn clean test -Dcucumber.filter.tags="@registration"
```

**Negative Tests:**
```bash
mvn clean test -Dcucumber.filter.tags="@negative"
```

**Contact Management Tests:**
```bash
mvn clean test -Dcucumber.filter.tags="@contact"
```

### **Generate Allure Report**
```bash
mvn allure:serve
```
This will automatically open the report in your browser! 🎉

---

## 📊 Test Scenarios

### **Login Feature** (5 Scenarios)

| # | Scenario | Data Source | Type | Tags |
|---|----------|-------------|------|------|
| 1 | Successful login | Database | ✅ Positive | @smoke @login @database |
| 2 | Successful login | Excel | ✅ Positive | @smoke @login @excel |
| 3 | Successful login | Redis | ✅ Positive | @smoke @login @redis |
| 4 | Invalid login | Database | ❌ Negative | @negative @login @database |
| 5 | Invalid login | Excel | ❌ Negative | @negative @login @excel |

### **Registration Feature** (3 Scenarios)

| # | Scenario | Type | Tags |
|---|----------|------|------|
| 1 | Successful registration | ✅ Positive | @smoke @registration |
| 2 | Registration with existing email | ❌ Negative | @smoke @negative @registration |
| 3 | Registration with invalid email format | ❌ Negative | @smoke @negative @registration |

### **Logout Feature** (2 Scenarios)

| # | Scenario | Type | Tags |
|---|----------|------|------|
| 1 | Successful logout | ✅ Positive | @smoke @logout |
| 2 | Logout and verify session cleared | ✅ Positive | @smoke @logout |

### **Contact Management Feature** (4+ Scenarios)

| # | Scenario | Type | Tags |
|---|----------|------|------|
| 1 | Add new contact | ✅ Positive | @smoke @contact |
| 2 | View contact details | ✅ Positive | @smoke @contact |
| 3 | Edit contact information | ✅ Positive | @smoke @contact |
| 4 | Delete contact | ✅ Positive | @smoke @contact |

**Total: 14+ Automated Test Scenarios**

---

## 📁 Project Structure

```
SQE-PROJECT/
│
├── src/
│   ├── main/java/com/automation/
│   │   ├── driver/
│   │   │   └── DriverManager.java              # WebDriver lifecycle management
│   │   ├── pages/                              # Page Object Model
│   │   │   ├── BasePage.java                   # Common page functionality
│   │   │   ├── LoginPage.java
│   │   │   ├── SignupPage.java
│   │   │   ├── ContactListPage.java
│   │   │   ├── AddContactPage.java
│   │   │   └── ContactDetailsPage.java
│   │   └── utils/                              # Utility classes
│   │       ├── ConfigurationFileReader.java    # Config management
│   │       ├── ScreenshotUtil.java             # Screenshot capture
│   │       ├── DatabaseReader.java             # MySQL integration
│   │       ├── ExcelReader.java                # Excel file reader
│   │       └── RedisReader.java                # Redis cache reader
│   │
│   └── test/
│       ├── java/com/automation/
│       │   ├── runners/
│       │   │   └── TestRunner.java             # Cucumber TestNG runner
│       │   └── stepdefinitions/                # BDD Step definitions
│       │       ├── Hooks.java                  # Before/After hooks
│       │       ├── LoginStepDefinitions.java
│       │       ├── RegistrationStepDefinitions.java
│       │       ├── LogoutStepDefinitions.java
│       │       └── ContactManagementStepDefinitions.java
│       └── resources/
│           ├── features/                       # Cucumber feature files
│           │   ├── Login.feature
│           │   ├── Registration.feature
│           │   ├── Logout.feature
│           │   └── ContactManagement.feature
│           ├── testdata/
│           │   └── LoginTestData.xlsx         # Test data Excel file
│           ├── config.properties               # Framework configuration
│           ├── allure.properties               # Allure configuration
│           └── log4j2.xml                      # Logging configuration
│
├── target/                                     # Build output
│   ├── allure-results/                         # Allure test results
│   ├── cucumber-reports/                       # Cucumber HTML reports
│   └── surefire-reports/                       # TestNG XML reports
│
├── logs/
│   └── automation.log                          # Execution logs
│
├── screenshots/                                # Failure screenshots
│
├── database_setup.sql                          # Database setup script
├── create_excel.vbs                            # Excel creation script
├── setup_redis.bat                             # Redis setup script
├── pom.xml                                     # Maven dependencies
├── testng.xml                                  # TestNG suite configuration
└── README.md                                   # This file
```

---

## 📈 Reporting

### **Allure Reports** (Recommended) 🏆

**Generate and view report:**
```bash
mvn allure:serve
```

**Features:**
- ✅ Beautiful HTML dashboard
- ✅ Test execution timeline
- ✅ Pass/Fail/Skip statistics with charts
- ✅ Step-by-step test execution details
- ✅ Screenshots attached for failures
- ✅ Test categorization by features and tags
- ✅ Environment information
- ✅ Historical trends (if enabled)

**Sample Report Sections:**
- **Overview** - Summary with graphs
- **Suites** - Test suites and scenarios
- **Graphs** - Visual representation
- **Timeline** - Execution timeline
- **Behaviors** - BDD features
- **Packages** - Java packages

### **Cucumber HTML Reports**

Located at: `target/cucumber-reports/cucumber.html`

Open in browser after test execution.

### **Console Logs**

**Real-time logs during execution:**
```
[INFO] com.automation.driver.DriverManager - Initializing chrome driver
[INFO] com.automation.pages.BasePage - Navigated to URL: https://...
[INFO] com.automation.utils.DatabaseReader - Retrieved credentials for email: john.test.2.doe@test.com
```

**Log file:** `logs/automation.log`

---

## ⚙️ Configuration

### **config.properties**

Located at: `src/test/resources/config.properties`

```properties
# Browser Configuration
browser=chrome                    # chrome, firefox, edge
headless=false                    # true for headless mode
implicit.wait=10                  # Implicit wait in seconds
explicit.wait=20                  # Explicit wait in seconds
page.load.timeout=30              # Page load timeout

# Application URL
app.url=https://thinking-tester-contact-list.herokuapp.com/

# Screenshot Configuration
screenshot.on.failure=true        # Capture screenshot on failure

# Database Configuration
db.url=jdbc:mysql://localhost:3306/sqe_project
db.username=root
db.password=                      # Empty for XAMPP default

# Redis Configuration
redis.host=localhost
redis.port=6379
redis.timeout=2000                # Connection timeout in ms

# Test Data
excel.path=src/test/resources/testdata/LoginTestData.xlsx

# Test Execution Speed
test.speed.short.wait=2000        # Short wait in ms
test.speed.medium.wait=3000       # Medium wait in ms
test.speed.element.action=1000    # Element action delay
```

### **testng.xml**

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Automation Test Suite">
    <test name="Cucumber Tests">
        <classes>
            <class name="com.automation.runners.TestRunner"/>
        </classes>
    </test>
</suite>
```

### **Changing Configuration**

**Run with different browser:**
```properties
browser=firefox    # Change in config.properties
```

**Run in headless mode:**
```properties
headless=true      # Change in config.properties
```

**Change database:**
```properties
db.url=jdbc:mysql://prod-server:3306/prod_database
db.username=prod_user
db.password=prod_password
```

---

## 🔍 Troubleshooting

### **Common Issues and Solutions**

#### **1. Database Connection Error**
```
Error: Unable to connect to database
```
**Solution:**
- Ensure XAMPP MySQL is running (green in Control Panel)
- Verify database name is `sqe_project`
- Check username is `root` and password is empty
- Run `database_setup.sql` script

#### **2. Excel File Not Found**
```
Error: FileNotFoundException: LoginTestData.xlsx
```
**Solution:**
- Verify file exists at: `src/test/resources/testdata/LoginTestData.xlsx`
- Check file has exactly 3 rows (1 header + 2 data)
- Ensure file is saved as `.xlsx` format
- Use `create_excel.vbs` to auto-create

#### **3. Redis Connection Refused**
```
Error: Connection refused to localhost:6379
```
**Solution:**
- Start Redis server: `cd C:\Redis && redis-server.exe`
- Keep the Redis server window open
- Verify Redis is running: `redis-cli.exe ping` (should return PONG)
- Check port 6379 is not blocked by firewall

#### **4. WebDriver/ChromeDriver Issues**
```
Error: SessionNotCreatedException: This version of ChromeDriver only supports Chrome version X
```
**Solution:**
- Update Chrome browser to latest version
- WebDriverManager auto-downloads compatible driver
- Check internet connection for driver download
- Clear Maven cache if needed

#### **5. Tests Fail - "Element not clickable"**
**Solution:**
- Increase wait times in `config.properties`
- Check if application is slow to load
- Disable headless mode to see what's happening
- Check screenshots in Allure report

#### **6. Maven Build Fails**
```
Error: Failed to execute goal
```
**Solution:**
```bash
mvn clean install -DskipTests    # Skip tests during install
mvn dependency:purge-local-repository    # Clear dependencies
```

#### **7. Allure Report Not Generating**
```
Error: Allure results directory not found
```
**Solution:**
- Run tests first: `mvn clean test`
- Then generate report: `mvn allure:serve`
- Check `target/allure-results` directory exists

---

## 🎓 Learning Outcomes

This framework demonstrates:

✅ **BDD with Cucumber** - Business-readable test scenarios  
✅ **Page Object Model** - Maintainable page structure  
✅ **Data-Driven Testing** - Multiple data source integration  
✅ **Configuration Management** - Externalized configuration  
✅ **Reporting** - Professional test reports with Allure  
✅ **Logging** - Comprehensive execution logs  
✅ **Design Patterns** - Singleton, Factory, POM  
✅ **Database Integration** - MySQL with JDBC  
✅ **File Operations** - Excel reading with Apache POI  
✅ **Cache Integration** - Redis with Jedis  
✅ **Screenshot Capture** - Automated failure debugging  
✅ **CI/CD Ready** - Maven-based, command-line executable  

---

## 📞 Support

For issues or questions:
1. Check **Troubleshooting** section above
2. Review logs in `logs/automation.log`
3. Check screenshots in `screenshots/` folder
4. Verify Allure report for detailed error info

---

## 📄 License

This project is for **educational and demonstration purposes**.

---

## 🙏 Acknowledgments

- **Application Under Test:** [Contact List App](https://thinking-tester-contact-list.herokuapp.com/)
- **Selenium Community**
- **Cucumber Team**
- **Allure Framework Team**

---

**Built with ❤️ for Test Automation Excellence**

**Last Updated:** December 4, 2025
