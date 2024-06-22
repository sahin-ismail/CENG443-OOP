import java.util.Random;

public class GotoXY extends State {
    private int value;
    Random r = new Random(0);
    Random a = new Random(1234);

    public GotoXY(int value) {
        this.value = value;
    }

    public int[] step(){
        int[] res = new int[2];
        res[0] = r.nextInt(1650);
        res[1] = a.nextInt(620);

        return res;
    }
}