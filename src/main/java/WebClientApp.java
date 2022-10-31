import com.example.A2_IS.models.Student;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class WebClientApp {
    public static void main(String[] args) throws InterruptedException, IOException {
        WebClient client = WebClient.create("http://localhost:8080");

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

        BufferedWriter writer = new BufferedWriter(new FileWriter("Students.txt",true));

        var headersSpec = client.get()
                .uri("/a2/student")
                .retrieve()
                .bodyToFlux(Student.class)
                .subscribe(student -> {
                    try {
                        writer.append("\nName: " + student.getName() + "\t Date of Birth: " + student.getDob());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(student.getName());
                });

        Thread.sleep(1500);


        var totalOfStudents = client.get()
                .uri("/a2/student")
                .retrieve()
                .bodyToFlux(Student.class)
                .count()
                .block();

        writer.write("\nTotal of Students: " + totalOfStudents);

        Thread.sleep(1500);

        var totalOfActiveStudents = client.get()
                .uri("/a2/student")
                .retrieve()
                .bodyToFlux(Student.class)
                .map(s-> s.getCredits() < 180)
                .count()
                .block();

        writer.write("\nTotal number of students that are active: " + totalOfActiveStudents);

        Thread.sleep(1500);

        var completedCourses = client.get()
                .uri("/a2/student")
                .retrieve()
                .bodyToFlux(Student.class)
                .subscribe(student -> {
                    int completedCouses = student.getCredits() / 6;

                    try {
                        writer.write("\nName of the student: " + student.getName() + " number of completed couses: " + completedCouses);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        Thread.sleep(1500);

        writer.close();

    }
}
