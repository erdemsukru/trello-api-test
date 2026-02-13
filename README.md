# Trello API Test Project

This project contains automated API tests for Trello using RestAssured. It was developed as a case study for Testinium.

## 🚀 Technologies Used
- Java 17
- Maven
- Rest-Assured
- JUnit 5
- HTML Reporting

## 📋 Test Scenarios
- Create a Trello board
- Create two cards on the board
- Update a random card
- Delete all cards
- Delete the board

## 📊 Test Report
After running the tests, an HTML report is generated in the `test-output` folder:
```
test-output/TrelloTestReport.html
```

## 🔧 Setup & Run

### Prerequisites
- Java 17 or higher
- Maven
- Trello API Key and Token

### Configuration
1. Get your Trello API Key and Token from: https://trello.com/power-ups/admin
2. Update `src/main/java/com/testinium/config/TrelloConfig.java` with your credentials:
```java
public static final String API_KEY = "your-api-key";
public static final String TOKEN = "your-token";
```

### Run Tests
```bash
mvn clean test
```

## 📁 Project Structure
```
src/main/java/com/testinium/
├── apipages/          # API Page Objects (BoardPage, CardPage)
├── config/            # Configuration files (TrelloConfig)
├── models/            # POJO classes (Board, Card)
└── utils/             # Utility classes (RestUtils, HtmlReportManager)

src/test/java/com/testinium/trello/
└── TrelloTest.java    # Main test class with 6 test scenarios
```

## ✅ Test Results
All tests should pass successfully:

| Test | Description | Status |
|------|-------------|--------|
| Test 1 | Board Creation | ✅ |
| Test 2 | Get Board Lists | ✅ |
| Test 3 | Create Two Cards | ✅ |
| Test 4 | Update Random Card | ✅ |
| Test 5 | Delete Cards | ✅ |
| Test 6 | Delete Board | ✅ |

## 📸 Test Output Example
```
==========================================
TRELLO API TEST STARTED
==========================================

---------- TEST 1: Board Creation ----------
✅ Board created successfully: Board{id='698f6e0bc51d294d0bbad5f0', name='Test Board'}

---------- TEST 2: Get Board Lists ----------
✅ First list ID: 698f6e0bc51d294d0bbad6ba

---------- TEST 3: Create Two Cards ----------
✅ Card 1 created
✅ Card 2 created

---------- TEST 4: Update Random Card ----------
✅ Card updated successfully

---------- TEST 5: Delete Cards ----------
✅ All cards deleted

---------- TEST 6: Delete Board ----------
✅ Board deleted

==========================================
TRELLO API TEST COMPLETED
Report: test-output/TrelloTestReport.html
==========================================
```

## 📊 HTML Report Sample
The HTML report includes:
- Test execution status (PASS/FAIL)
- HTTP status codes (200, 400, 500)
- Request and response details
- Test summary with statistics
- Dark theme for better visualization

## 🔗 API Endpoints Used
- `POST /1/boards/` - Create board
- `GET /1/boards/{id}/lists` - Get board lists
- `POST /1/cards` - Create card
- `PUT /1/cards/{id}` - Update card
- `DELETE /1/cards/{id}` - Delete card
- `DELETE /1/boards/{id}` - Delete board

## 👨‍💻 Author
- GitHub: [@erdemsukru](https://github.com/erdemsukru)

## 📄 License
This project is created for educational purposes as part of Testinium Case Study.
