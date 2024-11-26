# Getting Started

### To run locally

- To pull from docker hub and start up, run
```shell
  docker run -e APIKEY:C227WD9W3LUVKVV9 \
           -p 8080:8080 shaz900/stock-ticker-service
  ```
- For API calls, in postman.
   ```  
   http://localhost:8080/api/v1/stock/report
   ```
- If cloning the repo from github and then running it, go to /stock-ticker-service run
  ```shell
  docker build -t stock-ticker-service . && 
  docker run -e APIKEY:C227WD9W3LUVKVV9 \
         -p 8080:8080  \
         -it stock-ticker-service
  ``` 
- To have all the classes generated run ```./gradlew bootRun.``` and will need jdk17 

### What is in the code
- Springboot microservice with delegation and service layer pattern.
- Boilerplate code generation. API endpoints are generated during build using openapi code generator. The pushed code starts from `StockTickerApiDelegateImpl`.
- Actuator for health check endpoints.
- Circuit breaker to handle third party API errors by providing failure protection.
- Exception Handler to standardise the exception handling.
- Build and publish script.
- Integration test using MockMvc and Wiremock
- Open API spec is available src/main/resources/openapi.yaml.

### Code details 
- StockTickerApiDelegateImpl --> StockTickerServiceImpl --> AlphaVantageClientImpl --> StockTickerServiceImpl --> StockDetailsMapper --> StockTickerApiDelegateImpl
- StockTickerApiDelegateImpl - Delegate class that receives the API request from Controller
- StockTickerServiceImpl - Service class that has the business logic
- AlphaVantageClientImpl - This client is used to make third party API call (Alphavantage)
- StockDetailsMapper - Mapper to map the third party response to our API response
- ExceptionHandler - Exception handler for API
