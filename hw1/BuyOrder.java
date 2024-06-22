import java.awt.*;
import java.util.Random;

public class BuyOrder extends Order {
    Random r ;
    public BuyOrder(double x, double y, int amount, String country)
    {
        super(x, y, amount, country);
        r = new Random(position.getIntX());
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.setFont(font);
        g2d.drawString(country, position.getIntX(), position.getIntY());
        g2d.fillOval(position.getIntX(),position.getIntY(),30,30);
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(amount), position.getIntX()+7, position.getIntY()+22);
    }

    @Override
    public void step() {
        int a,b;
        b = 1;
        a = amount - r.nextInt(5);

        if(a<0){
            b = (-1)*a;
        }
        if(a>0){
            b = a;
        }
        if(a == 0){
            a = 1;
            b = 1;
        }

        if(position.getIntX()<=0 || position.getIntY()>=1650){
            a = (-1)*a;
        }
        position.setY(position.getIntY()-b);
        position.setX(position.getIntX()+a);
    }
    // TODO
}