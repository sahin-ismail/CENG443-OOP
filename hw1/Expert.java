import java.awt.*;

public class Expert extends AgentDecorator {
    public Expert(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(position.getIntX(),position.getIntY(),20,20);
        g2d.setColor(Color.BLACK);
    }

    void setC(int x, int y){
        this.position.setX(x);
        this.position.setY(y);
    }

    @Override
    public void step() {

    }
    // TODO
}