# PayLinkSim

A RazorPay-integrated payment gateway simulation that securely generates payment links, tracks transactions, and manages statuses with JWT-authenticated APIs.

**Core Functionality:** Creates dummy orders with custom order amounts and interacts with the RazorPay API to generate payment links.  
**Transaction Handling:** Consumes RazorPay responses (success/failure), updates payment statuses, and records the details in the database.  
**API Documentation & Testing:** SpringDoc OpenAPI is integrated for seamless API testing and documentation.  
**Security:** JWT token authentication secures all endpoints, with token validation logic ensuring the authenticity and validity of tokens.  
*Previously deployed on AWS Elastic Beanstalk with a PostgreSQL database hosted on AWS RDS.*  

Swagger url  
http://localhost:8082/swagger-ui/index.html#/

Reference and docs  
https://github.com/razorpay/razorpay-java

Test key and card details  
https://dashboard.razorpay.com/app/website-app-settings/api-keys
https://razorpay.com/docs/payments/payments/test-card-details/
