import com.example.A2_IS.models.Professor;
import com.example.A2_IS.models.Student;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;


public class WebClientApp {
    static WebClient client = WebClient.create("http://localhost:8080");

    public static void main(String[] args) {
        WebClientApp obj = new WebClientApp();
        Class<?> classObj = obj.getClass();

        for (int i = 1; i <= 11; i++) {
            int index = i;
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        Method callReq = classObj.getDeclaredMethod("req" + index, null); // GetDeclaredMethodByName
                        callReq.invoke(obj, null);                                                      //Invokes the method
                    }
                    catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e)
                    {
                        System.out.println(e.getCause());
                    }
                }
            };
            t.start();
        }

//        req1();
//        Thread.sleep(1500);
//        req2();
//        Thread.sleep(1500);
//        req3();
//        Thread.sleep(1500);
//        req4();
//        Thread.sleep(1500);
//        req5();
//        Thread.sleep(1500);
//        req6();
//        Thread.sleep(1500);
//        req7();
//        Thread.sleep(1500);
//        req8();
//        Thread.sleep(1500);
//        req10();
//        Thread.sleep(1500);
    }

    public static void req1() {
        try {
            PrintStream o = new PrintStream("Students.req1.txt");

            client.get()
                    .uri("/a2/student")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .subscribe(student -> {
                        o.println("\nName: " + student.getName() + "\t Date of Birth: " + student.getDob());
                    });
        } catch (Exception e) {
            System.out.println("REQ1 FUNCTION ERROR");
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
            System.out.println("REQ2 FUNCTION ERROR");
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
            System.out.println("REQ3 FUNCTION ERROR");
            e.printStackTrace();
        }
    }

    public static void req4() {
        try {
            PrintStream o = new PrintStream("Students.req4.txt");

            client.get()
                    .uri("/a2/student")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .subscribe(student -> {
                        int completedCouses = student.getCredits() / 6;

                        o.println("\nName of the student: " + student.getName() + " number of completed couses: " + completedCouses);
                    });
        } catch (Exception e) {
            System.out.println("REQ4 FUNCTION ERROR");
            e.printStackTrace();
        }

    }

    public static void req5(){
        try{
            WebClient client = WebClient.create("http://localhost:8080");
            PrintStream o = new PrintStream("Students.req5.txt");
            client.get()
                    .uri("/a2/student/sortedStudentsByCredits")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .subscribe(student -> {
                        if (student.getCredits() > 120 && student.getCredits() < 180)
                            o.println("Student name: " + student.getName() + " -- credits: " + student.getCredits());
                    });

        } catch (Exception e) {
            System.out.println("REQ5 FUNCTION ERROR");
            e.printStackTrace();
        }
    }


    public static void req6() {
        try {
            PrintStream o = new PrintStream("Students.req6.txt");

            var students = client.get()
                    .uri("/a2/student")
                    .retrieve()
                    .bodyToFlux(Student.class);

            reqStandardAndAverageDeviation(students, o);
        } catch (Exception e) {
            System.out.println("REQ6 FUNCTION ERROR");
            e.printStackTrace();
        }
    }

    public static void req7() {
        try {
            PrintStream o = new PrintStream("Students.req7.txt");

            var students = client.get()
                    .uri("/a2/student")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .filter(student -> student.getCredits() >= 180);

            reqStandardAndAverageDeviation(students, o);
        } catch (Exception e) {
            System.out.println("REQ7 FUNCTION ERROR");
        }
    }
    public static void req8(){
        try{
            WebClient client = WebClient.create("http://localhost:8080");
            PrintStream o = new PrintStream("Students.req8.txt");
            client.get()
                    .uri("/a2/student/sortedStudentsByAge")
                    .retrieve()
                    .bodyToFlux(Student.class).take(1)
                    .subscribe(
                            student -> o.println(student.getName() + " ---- " + student.getDob())
                    );
        } catch (Exception e) {
            System.out.println("REQ8 FUNCTION ERROR");
            e.printStackTrace();
        }
    }

    public static void req9(){
        try{
            WebClient client = WebClient.create("http://localhost:8080");
            PrintStream o = new PrintStream("Students.req9.txt");
            client.get()
                    .uri("/a2/student/getAllStudentsWithProfessors")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .collectList()
                    .subscribe(students -> {
                        var sumOfStudentProfessors = students.stream().map(student -> student.getProfessors().size()).mapToInt(value -> value).sum();
                        o.println("Average # of professors per student: " + (sumOfStudentProfessors*1d/students.size()));
                    });

        } catch (Exception e) {
            System.out.println("REQ10 FUNCTION ERROR");
            e.printStackTrace();
        }
    }

    public static void req10(){
        try{
            WebClient client = WebClient.create("http://localhost:8080");
            PrintStream o = new PrintStream("Students.req10.txt");
            client.get()
                    .uri("/a2/professor/getAllProfessorsWithStudents")
                    .retrieve()
                    .bodyToFlux(Professor.class)
                    .sort(Comparator.comparing(professor -> -professor.getStudents().size()))
                    .subscribe(
                            professor -> o.println("Professor "+professor.getName()+"; Number of students: "+professor.getStudents().size())
                    );
        } catch (Exception e) {
            System.out.println("REQ10 FUNCTION ERROR");
            e.printStackTrace();
        }
    }

    public static void req11(){
        try{
            WebClient client = WebClient.create("http://localhost:8080");
            PrintStream o = new PrintStream("Students.req11.txt");
            client.get()
                    .uri("/a2/student/getAllStudentsWithProfessors")
                    .retrieve()
                    .bodyToFlux(Student.class)
                    .subscribe(
                            student -> o.println(student.toString())
                    );
        } catch (Exception e) {
            System.out.println("REQ10 FUNCTION ERROR");
            e.printStackTrace();
        }
    }


    public static void reqStandardAndAverageDeviation(Flux<Student> students, PrintStream o) {
        var studentGrades = students
                .map(Student::getAverage)
                .collectList().block();

        var count = studentGrades.stream().count();
        var avgGrade = studentGrades.stream().reduce(Float::sum).get() / count;
        var standardDeviation = Math.sqrt(
                studentGrades.stream().map(grade -> grade-avgGrade).reduce(Float::sum).get() / count
        );
        var averageDeviation = (1d/count) * studentGrades.stream().map(grade -> Math.abs(grade-avgGrade)).reduce(Float::sum).get();

        o.printf("standardDeviation: %f\n", standardDeviation);
        o.printf("averageDeviation: %f\n", averageDeviation);
    }

}
