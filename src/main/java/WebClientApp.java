import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientApp {
    public static void main(String[] args) {
        WebClient client = WebClient.create("http://localhost:8080");
        var headersSpec = client.get()
            .uri("/a2/student");

        var responseText = headersSpec.retrieve().bodyToMono(String.class).block();

        System.out.println(">>>>> Output: "+responseText);

    }
}
