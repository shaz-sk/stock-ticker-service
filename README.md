# Getting Started

### To run locally

- To pull from docker hub and start up, run
  ```shell
  docker run -e APIKEY=C227WD9W3LUVKVV9 \
           -p 8080:8080 shaz900/stock-ticker-service
  ```
- For API calls, in postman.
   ```  
   http://localhost:8080/api/v1/stock/report
   ```
- To build the image locally, then follow the below steps
  ```shell
  git clone https://github.com/shaz-sk/stock-ticker-service.git
  ```
  ```shell 
  cd stock-ticker-service
  ```
  ```shell
  docker build -t stock-ticker-service . && 
  docker run -e APIKEY=C227WD9W3LUVKVV9 \
       -p 8080:8080  \
       -it stock-ticker-service
  ``` 


### What is in the code
- Springboot microservice with delegation and service layer pattern.
- Stock SYMBOL, NDAYS and APIKEY as environment variables.
- Docker image that runs the microservice.
- Docker build and publish script.
- Boilerplate code generation. API endpoints are generated during build using openapi code generator. The pushed code starts from `StockTickerApiDelegateImpl`.
- Actuator for health check endpoints.
- Circuit breaker to handle third party API errors by providing failure protection.
- Exception Handler to standardise the exception handling.
- Integration test using MockMvc and Wiremock. https://github.com/shaz-sk/stock-ticker-service/tree/master/src/test/kotlin/com/organization/stockTicker/integrationTests
- Open API spec is available src/main/resources/openapi.yaml.
- Unit tests are not exhaustive. To show the pattern, I have https://github.com/shaz-sk/stock-ticker-service/blob/master/src/test/kotlin/com/organization/stockTicker/alphavantage/client/AlphaVantageClientImplTest.kt,  https://github.com/shaz-sk/stock-ticker-service/blob/master/src/test/kotlin/com/organization/stockTicker/mapper/StockDetailsMapperTest.kt


### Code details 
- StockTickerApiDelegateImpl --> StockTickerServiceImpl --> AlphaVantageClientImpl --> StockTickerServiceImpl --> StockDetailsMapper --> StockTickerApiDelegateImpl
- StockTickerApiDelegateImpl - receives the API request from Controller and delegates to the service
- StockTickerServiceImpl - handles the business logic for retrieving and processing stock ticker data
- AlphaVantageClientImpl - calls Alpha Vantage API to retrieve stock data
- StockDetailsMapper - maps information received from Alpha Vantage to API response
- ExceptionHandler - handles exception for API calls
