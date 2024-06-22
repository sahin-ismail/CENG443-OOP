import java.awt.*;

public abstract class Order extends Entity {
    public int amount;
    public String country;
    public Font font;
    public Order(double x, double y, int amount, String country) {
        super(x, y);
        this.country = country;
        this.amount = amount;
        font = new Font("Verdana",Font.PLAIN, 20);
    }


    public void draw(Graphics2D g2d) {

    }


}