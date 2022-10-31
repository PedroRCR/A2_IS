import com.example.A2_IS.models.Student;
import org.springframework.web.reactive.function.client.WebClient;
import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebClientApp {
    static WebClient client = WebClient.create("http://localhost:8080");

    public static void main(String[] args) throws InterruptedException, IOException, FileNotFoundException {
        req1();
        req2();
        req3();
        req4();
        req6();
    }

    public static void req1() {
        try {
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
            PrintStream o = new PrintStream("Students.req6.txt");

            var studentGrades = client.get()
                    .uri("/a2/student")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .map(Student::getAvarage)
                    .collectList().block();


            var count = studentGrades.stream().count();
            var avgGrade = studentGrades.stream().reduce(Float::sum).get() / count;
            var standardDeviation = Math.sqrt(
              studentGrades.stream().map(grade -> grade-avgGrade).reduce(Float::sum).get() / count
            );
            System.out.println("standardDeviation: "+standardDeviation);
        } catch (Exception e) {
            System.out.println("REQ6 FUNCTION ERROR");
            e.printStackTrace();
        }
    }
}
