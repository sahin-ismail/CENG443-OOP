import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BasicAgent extends Agent {
    BufferedImage image;
    private String status;
    private int dif;
    private int gain;
    private int maker;
    private int ss;
    private int x;
    private int y;
    private String imageName;
    private String iaName;
    private String country;
    private Font font;
    private File path;
    private GotoXY gotoxy;
    private Shake shake;
    private Random r = new Random(1234);
    private Random a = new Random(1234);
    private Novice novice;
    private Expert expert;
    private Master master;

    public BasicAgent(double x, double y, String imageName, String iaName, String country,int maker) {
        super(x, y);
        this.status = "Rest";
        this.gain = 0;
        this.maker = maker;
        this.imageName = imageName;
        this.iaName = iaName;
        this.country = country;
        this.path = new File((imageName));
        this.gotoxy = new GotoXY(0);
        this.shake = new Shake(0);
        this.ss = 0;
        font = new Font("Verdana", Font.PLAIN, 20);

        this.novice = new Novice(position.getIntX(),position.getIntY()-20);
        this.master = new Master(position.getIntX()+20,position.getIntY()-20);
        this.expert = new Expert(position.getIntX()+40,position.getIntY()-20);


        try {
            image = ImageIO.read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getGain() {
        return gain;
    }

    public void setGain(int gain) {
        this.gain = gain;
    }


    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(String.format(iaName), position.getIntX(), position.getIntY()-5);
        g2d.setColor(Color.BLUE);
        g2d.drawString(String.format(status), position.getIntX(), position.getIntY()+90);
        g2d.setColor(Color.RED);
        g2d.drawString(String.format(""+gain), position.getIntX(), position.getIntY()+110);
        g2d.drawImage(image,position.getIntX(),position.getIntY(),70,70, null);

        if(this.gain>2000){
            novice.draw(g2d);
        }
        if(this.gain>4000){
            master.draw(g2d);
        }
        if(this.gain>6000){
            expert.draw(g2d);
        }
    }

    @Override
    public void step() {
        int s = a.nextInt(250 );

        if(s == (maker + 0) || s == (maker + 1) || s == (maker + 2) || s == (maker+3) ){
            ss = s;
        }
        if(ss==(0+maker)) {
            this.status = "Shake";
            dif = shake.step();
            if (r.nextInt(2) == 0) {
                if (position.getIntX() <= 0 && dif < 0) {
                    position.setX(position.getX() + (dif * (-1)));
                } else {
                    position.setX(position.getX() + dif);
                }
            } else {
                if (position.getIntY() <= 0 && dif < 0) {
                    position.setY(position.getY() + (dif * (-1)));
                } else {
                    position.setY(position.getY() + dif);
                }
            }
        }
        if(ss == (1+maker)){
            this.status = "Rest";

        }
        if(ss == (2+maker) && this.status != "GotoXY"){
            this.status = "GotoXY";
            x = gotoxy.step()[0];
            y = gotoxy.step()[1];
        }
        if(ss == (3+maker)){
            this.status = "ChaseClosest";
            // will done after factory
        }

        if(status == "GotoXY"){
            if(x>position.getIntX()) {
                position.setX(position.getX() + 1);
            }
            else if(x<position.getIntX()){
                position.setX(position.getX() - 1);
            }
            else{

            }
            if(y>position.getIntY()) {
                position.setY(position.getY() + 1);
            }
            else if(x<position.getIntY()){
                position.setY(position.getY() - 1);
            }
            else{

            }
        }
        novice.setC(this.position.getIntX(),this.position.getIntY()-50);
        master.setC(this.position.getIntX()+25,this.position.getIntY()-50);
        expert.setC(this.position.getIntX()+50,this.position.getIntY()-50);
    }
    // TODO
}