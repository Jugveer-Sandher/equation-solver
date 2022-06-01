import java.io.FileNotFoundException;

public class test {

    public static void main(String arg[]){
        Calculator cal = new Calculator();
        try {
            cal.run(arg[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}