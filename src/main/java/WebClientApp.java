import com.example.A2_IS.models.Student;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientApp {
    public static void main(String[] args) throws InterruptedException {
        WebClient client = WebClient.create("http://localhost:8080");

        System.out.println(">>>>> Student List <<<<<");
        var headersSpec = client.get()
            .uri("/a2/student")
            .retrieve()
            .bodyToFlux(Student.class)
            .subscribe(student -> {
                System.out.println(student.getName());
            });

        Thread.sleep(1500);
    }
}
