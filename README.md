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

### What is in the code
- Open API spec. Available src/main/resources/openapi.yaml.
- Actuator for health check endpoints.
- Circuit breaker to handle third party API errors.
- Boiler plate code generation using openapi code generator.
- Service classes were used to do the business logic.
- Exception Handler to standardise the exception handling.
- Build and publish script.
