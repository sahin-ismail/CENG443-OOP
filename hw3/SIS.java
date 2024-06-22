import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.SyncFailedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SIS {
    private static String fileSep = File.separator;
    private static String lineSep = System.lineSeparator();
    private static String space   = " ";

    private List<Student> studentList = new ArrayList<>();

    public SIS(){ processOptics(); }

    private void processOptics(){
        // TODO

        //get files from input folder
        try (Stream<Path> paths = Files.walk(Paths.get("./input"))) {
            paths.filter(Files::isRegularFile).collect(Collectors.toList()).forEach(path->{
                //get one file
                try (Stream<String> stream = Files.lines(Paths.get(""+path))) {
                    String[] ss = stream.toArray(String[]::new);
                    String[] first = ss[0].split(" ");
                    String[] second = ss[1].split(" ");
                    String[] third = ss[2].split(" ");
                    String[] fourth = ss[3].split(" ");

                    int id = Integer.parseInt(first[first.length-1]);
                    String surname = first[first.length-2];
                    String[] names = Arrays.copyOfRange(first, 0, first.length-2);
                    Student s;
                    Course c;
                    List<Student> result = studentList.stream()
                            .filter(item -> item.getStudentID()==(id))
                            .collect(Collectors.toList());

                    //if 0, do not control and initialize
                    if(result.size()==0) {

                        //construct new student
                        s = new Student(names,surname,id);
                        int course_code = Integer.parseInt(second[1]);
                        int year = Integer.parseInt(second[0]); //.substring(0, 4)
                        String exam_type = third[0];
                        int credit = Integer.parseInt(second[2]);
                        int grade = 0;
                        String answer = fourth[0];
                        long a = answer.chars().filter(ch -> ch == 'T').count();
                        grade = (int) (100*(a/(float)answer.length()));

                        //fill course list
                        s.getTakenCourses().add(new Course(course_code,year,exam_type,credit,grade));

                        //add it student list
                        studentList.add(s);
                    }else {
                        int course_code = Integer.parseInt(second[1]);
                        int year = Integer.parseInt(second[0]); //.substring(0, 4)
                        String exam_type = third[0];
                        int credit = Integer.parseInt(second[2]);
                        int grade = 0;
                        String answer = fourth[0];
                        long a = answer.chars().filter(ch -> ch == 'T').count();
                        grade = (int) (100*(a/(float)answer.length()));

                        //fill result
                        result.get(0).getTakenCourses().add(new Course(course_code,year,exam_type,credit,grade));
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public double getGrade(int studentID, int courseCode, int year){
        // TODO
        //if there is problem return -1
        double[] result = {-1};

        //exams of related course
        List<Course> s = studentList.stream().filter(x -> x.getStudentID() == studentID).collect(Collectors.toList()).get(0).getTakenCourses()
                .stream().filter(l -> l.getCourseCode() == courseCode && l.getYear() == year).collect(Collectors.toList());
        if(s.size()!=0){
            result[0] = 0;
        }

        //add them result with calculations
        s.stream().forEach(e->{
            if(e.getExamType().equals("Midterm1") || e.getExamType().equals("Midterm2")){

                result[0] = result[0]+ e.getGrade()/4;
            }else {
                result[0] = result[0]+ e.getGrade()/2;
            }
        });
        return result[0];
    }

    public void updateExam(int studentID, int courseCode, String examType, double newGrade){
        // TODO
        // get courses of students
        List<Course> courses = studentList.stream().filter(x -> x.getStudentID() == studentID).collect(Collectors.toList()).get(0).getTakenCourses();
        //get related courses
        courses = courses.stream().filter(x->x.getCourseCode()==courseCode).collect(Collectors.toList());
        //find and update exam
        courses.stream().filter(f-> f.getExamType().equals(examType)).sorted(Comparator.comparing(Course::getYear).reversed()).collect(Collectors.toList()).get(0).setGrade(newGrade);

    }

    public void createTranscript(int studentID){
        // TODO
        // get courses
        List<Course> courses = studentList.stream().filter(x -> x.getStudentID() == studentID).collect(Collectors.toList()).get(0).getTakenCourses();
        List<Course> sorted = courses.stream().sorted(Comparator.comparing(Course::getYear)).collect(Collectors.toList());

        //get all years of courses
        List<Integer> years = sorted.stream().map(Course::getYear).collect(Collectors.toList());

        //list of getted courses for cgpa
        List<String> get = new ArrayList<>();


        years = years.stream().distinct().collect(Collectors.toList());
        years.stream().forEach(e-> {

            //prints courses with year order
            System.out.println(e);
            List<Course> courses1 = courses.stream().filter(x->x.getYear()==e).sorted(Comparator.comparing(Course::getCourseCode)).collect(Collectors.toList());
            List<Integer> courseCode = courses1.stream().map(Course::getCourseCode).collect(Collectors.toList());
            courseCode = courseCode.stream().distinct().collect(Collectors.toList());
            courseCode.stream().forEach(s->{
                String not = "";
                double gra = getGrade(studentID,s,e);
                if(gra>=89.5){
                    not = "AA";
                }else if(gra>=84.5){
                    not = "BA";
                }else if(gra>=79.5){
                    not = "BB";
                }else if(gra>=74.5){
                    not = "CB";
                }else if(gra>=69.5){
                    not = "CC";
                }else if(gra>=64.5){
                    not = "DC";
                }else if(gra>=59.5){
                    not = "DD";
                }else if(gra>=49.5){
                    not = "FD";
                }else{
                    not = "FF";
                }
                System.out.println(s+" "+not);

                get.removeIf(k -> k.substring(0, 7).equals(s+""));
                get.add(s + " " + not);
            });


        });
        double[] cmp = {0};
        double[] total = {0};

        // this is for cgpa
        get.stream().forEach(x->{
            int course_code = Integer.parseInt(x.substring(0, 7));
            String note = x.substring(8, 10);
            List<Course> weightfor = studentList.stream().filter(p -> p.getStudentID() == studentID).collect(Collectors.toList()).get(0).getTakenCourses();
            int weight = weightfor.stream().filter(os->os.getCourseCode()==course_code).collect(Collectors.toList()).get(0).getCredit();
            if(note.equals("AA")){
                total[0] = total[0]+4*weight;
            }else if(note.equals("BA")){
                total[0] = total[0]+3.5*weight;
            }else if(note.equals("BB")){
                total[0] = total[0]+3*weight;
            }else if(note.equals("CB")){
                total[0] = total[0]+2.5*weight;
            }else if(note.equals("CC")){
                total[0] = total[0]+2*weight;
            }else if(note.equals("DC")){
                total[0] = total[0]+1.5*weight;
            }else if(note.equals("DD")){
                total[0] = total[0]+1*weight;
            }else if(note.equals("FD")){
                total[0] = total[0]+0.5*weight;
            }else{
                total[0] = total[0];
            }
            cmp[0]=cmp[0]+weight;
        });
        cmp[0] = total[0]/cmp[0];

        System.out.printf(Locale.US, "CGPA: %.2f%n",cmp[0]);


    }

    public void findCourse(int courseCode){
        // TODO

        //get all courses
        List<Course> res = new ArrayList<>();
        studentList.forEach(x->{
            x.getTakenCourses().forEach(y->{
                res.add(y);
            });
        });

        //some filters and sorts
        List<Course> res1 = res.stream().filter(x->x.getCourseCode() == courseCode).collect(Collectors.toList());
        List<Course> res1sorted = res1.stream().sorted(Comparator.comparing(Course::getYear)).collect(Collectors.toList());
        List<Integer> years = res1sorted.stream().map(Course::getYear).collect(Collectors.toList());

        //find distinct years of course
        years = years.stream().distinct().collect(Collectors.toList());

        //prints them for all years
        years.forEach(x->{
            int a = res1.stream().filter(y->y.getYear()==x).collect(Collectors.toList()).size();
            System.out.println(x+" "+a/3);
        });

    }

    public void createHistogram(int courseCode, int year){
        // TODO
        int[] s0010 = {0};
        int[] s1020 = {0};
        int[] s2030 = {0};
        int[] s3040 = {0};
        int[] s4050 = {0};
        int[] s5060 = {0};
        int[] s6070 = {0};
        int[] s7080 = {0};
        int[] s8090 = {0};
        int[] s9000 = {0};

        //get all related counts here
        studentList.stream().forEach(x->{
            if(getGrade(x.getStudentID(),courseCode,year)==-1){

            }else if(getGrade(x.getStudentID(),courseCode,year)<10){
                s0010[0]++;
            }else if(getGrade(x.getStudentID(),courseCode,year)<20){
                s1020[0]++;
            }else if(getGrade(x.getStudentID(),courseCode,year)<30){
                s2030[0]++;
            }else if(getGrade(x.getStudentID(),courseCode,year)<40){
                s3040[0]++;
            }else if(getGrade(x.getStudentID(),courseCode,year)<50){
                s4050[0]++;
            }else if(getGrade(x.getStudentID(),courseCode,year)<60){
                s5060[0]++;
            }else if(getGrade(x.getStudentID(),courseCode,year)<70){
                s6070[0]++;
            }else if(getGrade(x.getStudentID(),courseCode,year)<80){
                s7080[0]++;
            }else if(getGrade(x.getStudentID(),courseCode,year)<90){
                s8090[0]++;
            }else if(getGrade(x.getStudentID(),courseCode,year)<100){
                s9000[0]++;
            }else{

            }
        });

        //prints histogram with counts
        System.out.println("0-10 "+s0010[0]);
        System.out.println("10-20 "+s1020[0]);
        System.out.println("20-30 "+s2030[0]);
        System.out.println("30-40 "+s3040[0]);
        System.out.println("40-50 "+s4050[0]);
        System.out.println("50-60 "+s5060[0]);
        System.out.println("60-70 "+s6070[0]);
        System.out.println("70-80 "+s7080[0]);
        System.out.println("80-90 "+s8090[0]);
        System.out.println("90-100 "+s9000[0]);
    }
}