# Getting Started

### To run locally

- From /stock-ticker-service run
  ```shell
  docker build -t stock-ticker-service . && 
  docker run -e apikey:C227WD9W3LUVKVV9 \
           -e symbol:MSFT \
           -p 8080:8080  \
           -it stock-ticker-service
  ``` 
- For API calls, in postman.
   ```  
   http://localhost:8080/api/v1/stock/report
   ```
- To have all the classes generated run ```./gradlew bootRun.``` and will need jdk17 

### What is in the code
- Springboot microservice with delegation and service layer pattern.
- Boilerplate code generation using openapi code generator. API endpoints are generated during build. The pushed code starts from `StockTickerApiDelegateImpl`.
- Open API spec is available src/main/resources/openapi.yaml.
- Actuator for health check endpoints.
- Circuit breaker to handle third party API errors.
- Exception Handler to standardise the exception handling.
- Build and publish script.

### Code details 
- StockTickerApiDelegateImpl --> StockTickerServiceImpl --> AlphaVantageClientImpl --> StockTickerServiceImpl --> StockDetailsMapper --> StockTickerApiDelegateImpl
- StockTickerApiDelegateImpl - Delegate class that receives the API request from Controller
- StockTickerServiceImpl - Service class that has the business logic
- AlphaVantageClientImpl - This client is used to make third party API call (Alphavantage)
- StockDetailsMapper - Mapper to map the third party response to our API response
- ExceptionHandler - Exception handler for API
