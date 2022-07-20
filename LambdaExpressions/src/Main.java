import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main
{
    private static String staffFile = "LambdaExpressions/data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) throws ParseException {
        ArrayList<Employee> staff = loadStaffFromFile();
        String dateOld = "01.01.2017";
        String dateNew = "31.12.2017";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date1 = format.parse(dateOld);
        Date date2 = format.parse(dateNew);

        System.out.println("");
        System.out.println("----------------------------------");
        System.out.println("Все кто начал работать в 2017 году");
        System.out.println("----------------------------------");
        staff.stream().filter((e) -> e.getWorkStart().after(date1) && e.getWorkStart().before(date2)).forEach(System.out::println);
        System.out.println("----------------------------------");
        System.out.println("");
        System.out.println("----------------------------------");
        System.out.println("У кого самая большая ЗП из тех кто начал работуть в 2017 году");
        System.out.println("----------------------------------");
        staff.stream().filter((e) -> e.getWorkStart().after(date1) && e.getWorkStart().before(date2)).max(Comparator.comparing(Employee::getSalary)).ifPresent(System.out::println);
        System.out.println("----------------------------------");

        // Способ №1
        //staff.sort(new SortByMoney().thenComparing(new SortByName()));

        // Способ №2
        //staff.sort(Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName));
    }
    private static ArrayList<Employee> loadStaffFromFile()
    {
        ArrayList<Employee> staff = new ArrayList<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines)
            {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                    fragments[0],
                    Integer.parseInt(fragments[1]),
                    (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}