import java.util.Random;

public class Shake extends State {
    private int value;
    Random r = new Random(value);

    public Shake(int value) {
        this.value = value;
    }

    public int step(){
        int res,a;
        res = r.nextInt(2);
        a = r.nextInt(2);
        if(a == 0){
            res = (-1)*res;
        }
        return res;
    }
}