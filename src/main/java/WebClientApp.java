import com.example.A2_IS.models.Student;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WebClientApp {
    public static void main(String[] args) throws InterruptedException, IOException {
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

        req1();
        req2();
        req3();
        req4();
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

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
        }
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

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
        }
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

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
        }
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

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
        }
    }
}
