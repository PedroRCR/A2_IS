import com.example.A2_IS.models.Student;
import org.springframework.web.reactive.function.client.WebClient;
import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebClientApp {
    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
//            File f = new File("filename.txt");
//        try {
//            if (f.createNewFile()) {
//                System.out.println("File created: " + f.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
        PrintStream o = new PrintStream("Students.txt");
        System.setOut(o);

        Future<?> future1 = executorService.submit(WebClientApp::req1);
        Future<?> future2 = executorService.submit(WebClientApp::req2);
        Future<?> future3 = executorService.submit(WebClientApp::req3);
        Future<?> future4 = executorService.submit(WebClientApp::req4);

        Thread.sleep(1500);
        executorService.shutdown();
    }

    public static void req1() {
        WebClient client = WebClient.create("http://localhost:8080");

        var headersSpec = client.get()
                .uri("/a2/student")
                .retrieve()
                .bodyToFlux(Student.class)
                .subscribe(student -> {
                    System.out.println("\nName: " + student.getName() + "\t Date of Birth: " + student.getDob());
                    System.out.println(student.getName());
                });
    }

    public static void req2() {
        WebClient client = WebClient.create("http://localhost:8080");

        var totalOfStudents = client.get()
                .uri("/a2/student")
                .retrieve()
                .bodyToFlux(Student.class)
                .count()
                .block();

        System.out.println("\nTotal of Students: " + totalOfStudents);
    }

    public static void req3() {
        WebClient client = WebClient.create("http://localhost:8080");

        var totalOfActiveStudents = client.get()
                .uri("/a2/student")
                .retrieve()
                .bodyToFlux(Student.class)
                .map(s-> s.getCredits() < 180)
                .count()
                .block();

        System.out.println("\nTotal number of students that are active: " + totalOfActiveStudents);
    }

    public static void req4() {
        WebClient client = WebClient.create("http://localhost:8080");

        var completedCourses = client.get()
                .uri("/a2/student")
                .retrieve()
                .bodyToFlux(Student.class)
                .subscribe(student -> {
                    int completedCouses = student.getCredits() / 6;

                    System.out.println("\nName of the student: " + student.getName() + " number of completed couses: " + completedCouses);
                });
    }
}
