import java.util.Random;

public class Rest extends State {
    private int value;
    Random r = new Random(value);

    public Rest(int value) {
        this.value = value;
    }

    public int step(){
        return 0;
    }
}