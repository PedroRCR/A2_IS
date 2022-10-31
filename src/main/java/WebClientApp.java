import com.example.A2_IS.models.Student;
import org.springframework.web.reactive.function.client.WebClient;
import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebClientApp {
    public static void main(String[] args) throws InterruptedException, IOException, FileNotFoundException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        PrintStream o = new PrintStream("Students.txt");
        System.setOut(o);

        executorService.submit(WebClientApp::req1);
        executorService.submit(WebClientApp::req2);
        executorService.submit(WebClientApp::req3);
        executorService.submit(WebClientApp::req4);
        executorService.submit(WebClientApp::req6);

        Thread.sleep(1500);
        executorService.shutdown();
    }

    public static void req1() {
        try {
            WebClient client = WebClient.create("http://localhost:8080");
            PrintStream o = new PrintStream("Students.req1.txt");

            var headersSpec = client.get()
                    .uri("/a2/student")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .subscribe(student -> {
                        o.println("\nName: " + student.getName() + "\t Date of Birth: " + student.getDob());
                        o.println(student.getName());
                    });
        } catch (Exception e) {
            System.out.println("REQ6 FUNCTION ERROR");
            e.printStackTrace();
        }
    }

    public static void req2() {
        try {
            WebClient client = WebClient.create("http://localhost:8080");
            PrintStream o = new PrintStream("Students.req2.txt");

            var totalOfStudents = client.get()
                    .uri("/a2/student")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .count()
                    .block();

            o.println("\nTotal of Students: " + totalOfStudents);
        } catch (Exception e) {
            System.out.println("REQ6 FUNCTION ERROR");
            e.printStackTrace();
        }
    }

    public static void req3() {
        try {
            WebClient client = WebClient.create("http://localhost:8080");
            PrintStream o = new PrintStream("Students.req3.txt");

            var totalOfActiveStudents = client.get()
                    .uri("/a2/student")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .map(s -> s.getCredits() < 180)
                    .count()
                    .block();

            o.println("\nTotal number of students that are active: " + totalOfActiveStudents);
        } catch (Exception e) {
            System.out.println("REQ6 FUNCTION ERROR");
            e.printStackTrace();
        }
    }

    public static void req4() {
        try {
            WebClient client = WebClient.create("http://localhost:8080");
            PrintStream o = new PrintStream("Students.req4.txt");

            var completedCourses = client.get()
                    .uri("/a2/student")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .subscribe(student -> {
                        int completedCouses = student.getCredits() / 6;

                        o.println("\nName of the student: " + student.getName() + " number of completed couses: " + completedCouses);
                    });
        } catch (Exception e) {
            System.out.println("REQ6 FUNCTION ERROR");
            e.printStackTrace();
        }
    }

    public static void req6() {
        try {
            WebClient client = WebClient.create("http://localhost:8080");
            PrintStream o = new PrintStream("Students.req6.txt");

            var studentGrades = client.get()
                    .uri("/a2/student")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .map(Student::getAvarage);

            var avgGrade = studentGrades.reduce(Float::sum).block() / studentGrades.count().block();
        } catch (Exception e) {
            System.out.println("REQ6 FUNCTION ERROR");
            e.printStackTrace();
        }
    }
}
