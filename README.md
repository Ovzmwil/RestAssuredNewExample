# RestAssuredNewExample

API test automation project using **Java**, **RestAssured**, and **TestNG**, with **Maven** as the build tool.

## 📌 Technologies Used

- Java 1.8
- Maven
- RestAssured
- TestNG
- ExtentReports (for test reports)

## 📂 Project Structure

```
RestAssuredNewExample/
├── pom.xml
├── src/
│   ├── main/
|       └── java/
|           └── me.lsantana.RestAssuredNewExample
|               └── utils/
│   ├── test/
│       ├── java/
│           └── me.lsantana.RestAssuredNewExample
|               └── test/
|       └── resources/
|           └── config.properties
└── README.md
```


## ⚙️ How to Run the Tests

1. **Clone the repository:**

```bash
git clone https://github.com/Ovzmwil/RestAssuredNewExample.git
cd RestAssuredNewExample
```

2. **Run tests using Maven:**

```bash
mvn clean test
```

3. **View the test report:**

After execution, a file named `ExampleClassTestReport.html` will be generated in the project path `target/report`, containing detailed test results.

**Note:** Make sure you have Java JDK 8+ and Maven installed.



