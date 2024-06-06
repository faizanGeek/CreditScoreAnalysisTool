package com.ms.credit.client;

// Import necessary classes from the Spring Framework and Reactor libraries.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

// Declare the service layer component for managing user-related operations.
@Service
public class UserManagementClient {

    // Automatically inject an instance of WebClient to handle HTTP requests.
    @Autowired
    private WebClient webClient;

    // URL endpoint for the user management service.
    private final String USER_SERVICE_URL = "http://user-management-service/users";

    /**
     * Retrieves user details from the user management service using a reactive WebClient.
     * @param userId The ID of the user whose details are to be fetched.
     * @return a Mono that emits the user details as a string or an error signal if an error occurs.
     */
    public Mono<String> getUserDetails(int userId) {
        return webClient.get()  // Create an HTTP GET request.
                        .uri(USER_SERVICE_URL + "/{userId}", userId)  // Append the user ID to the URL and set the variable in the path.
                        .retrieve()  // Extract the response body automatically.
                        .bodyToMono(String.class);  // Convert the response body to a Mono that emits strings.
    }
}
