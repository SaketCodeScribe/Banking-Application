# Notes

1. Spring Security:
   In postman we can't use httpsession to store user authentication.
     Postman, as a tool for API testing and development, does not maintain an HTTP session in the same way that a web browser does.
     In a web browser, when you interact with a web application, the browser maintains an HTTP session by sending a session cookie with each request.
     This session cookie allows the server to identify the user and maintain their state across multiple requests.
     However, in Postman, each request is independent and stateless by default. Postman does not automatically handle cookies or maintain session state between requests.
