import java.util.Random;

public class BuyOrderFactory extends OrderFactory {
    Random r;
    private int x;
    private int y;
    private String country;

    public BuyOrderFactory(int x, int y, String country) {
        this.x = x;
        this.y = y;
        this.country = country;
        r = new Random(x);
    }

    @Override
    Order getOrder() {
        int a;
        a = r.nextInt(5);
        return new BuyOrder(x,y,a+1,country);
    }
}