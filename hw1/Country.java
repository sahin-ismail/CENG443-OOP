
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Country extends Entity {
    BufferedImage image;
    private Random r = new Random(0);
    private String imageName;
    private Font font;
    private String countryName;
    private File path;
    private SellOrderFactory sf ;
    private BuyOrderFactory bf;
    private Order tmp = null;
    private Order tmp1 = null;
    private Order tmp2 = null;
    private Order tmp3 = null;
    private Position[] arr;
    private int[] steal;
    private int gold;
    private int cash;
    private int worth;
    private double goldPrice;


    public Country(double x, double y,String imageName, String countryName, String c) {
        super(x, y);
        font = new Font("Verdana", Font.PLAIN, 20);
        this.imageName = imageName;
        this.countryName = countryName;
        this.gold = 50;
        this.cash = 10000;
        this.worth = 12500;
        this.goldPrice = 0;
        this.steal = new int[10];
        this.steal[0] = 0;
        this.steal[1] = 0;
        this.steal[2] = 0;
        this.steal[3] = 0;
        this.steal[4] = 0;
        this.steal[5] = 0;
        this.steal[6] = 0;
        this.steal[7] = 0;
        this.steal[8] = 0;
        this.steal[9] = 0;

        arr = null;
        this.path = new File((imageName));
        this.sf = new SellOrderFactory(position.getIntX(),position.getIntY(),c);
        this.bf = new BuyOrderFactory(position.getIntX(),position.getIntY(),c);
        try {
            image = ImageIO.read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getGold() {
        return gold;
    }

    public int getCash() {
        return cash;
    }


    public void setGold(int gold) {
        this.gold = gold;
        this.worth = (int) (this.gold*goldPrice + cash);
    }

    public void setCash(int cash) {

        this.cash = cash;
        this.worth = (int) (this.gold*goldPrice + cash);
    }

    @Override
    public void draw(Graphics2D g2d) {


        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(String.format(countryName), position.getIntX()+30, position.getIntY()+130);
        g2d.setColor(Color.YELLOW);
        g2d.drawString(String.format(gold+" gold"), position.getIntX()+15, position.getIntY()+155);
        g2d.setColor(Color.GREEN);
        g2d.drawString(String.format(cash+" cash"), position.getIntX()+15, position.getIntY()+180);
        g2d.setColor(Color.BLUE);
        g2d.drawString(String.format("Worth: " + worth), position.getIntX()+15, position.getIntY()+205);
        g2d.setColor(Color.BLACK);
        g2d.drawImage(image,position.getIntX(),position.getIntY(),120,100, null);


        if(r.nextInt(300) == 0) {
            if (this.tmp == null) {
                this.tmp = sf.getOrder();
            }
        }
        if(r.nextInt(250) == 5) {
            if (this.tmp1 == null) {
                this.tmp1 = bf.getOrder();
            }
        }
        if(r.nextInt(600) == 17) {
            if (this.tmp2 == null) {
                this.tmp2 = sf.getOrder();
            }
        }
        if(r.nextInt(450) == 6) {
            if (this.tmp3 == null) {
                this.tmp3 = bf.getOrder();
            }
        }
        if (this.tmp != null) {
            tmp.draw(g2d);
        }
        if (this.tmp1 != null) {
            tmp1.draw(g2d);
        }
        if (this.tmp2 != null) {
            tmp2.draw(g2d);
        }
        if (this.tmp3 != null) {
            tmp3.draw(g2d);
        }


    }

    @Override
    public void step() {

        if (this.tmp != null) {
            tmp.step();
            if(this.countryName != "USA" && tmp.position.getIntX()>=arr[0].getIntX() && tmp.position.getIntX()<= arr[0].getIntX()+70 && tmp.position.getIntY()>=arr[0].getIntY() && tmp.position.getIntY()<= arr[0].getIntY()+110){
                steal[0] = steal[0] + tmp.amount;
                this.gold = this.gold - tmp.amount;
                tmp = null;
            }
            else if(this.countryName != "Israel" && tmp.position.getIntX()>=arr[1].getIntX() && tmp.position.getIntX()<= arr[1].getIntX()+70 && tmp.position.getIntY()>=arr[1].getIntY() && tmp.position.getIntY()<= arr[1].getIntY()+110){
                steal[2] = steal[2] + tmp.amount;
                this.gold = this.gold - tmp.amount;
                tmp = null;
            }
            else if(this.countryName != "Turkey" && tmp.position.getIntX()>=arr[2].getIntX() && tmp.position.getIntX()<= arr[2].getIntX()+70 && tmp.position.getIntY()>=arr[2].getIntY() && tmp.position.getIntY()<= arr[2].getIntY()+110){
                steal[4] = steal[4] + tmp.amount;
                this.gold = this.gold -  tmp.amount;
                tmp = null;
            }
            else if(this.countryName != "Russia" && tmp.position.getIntX()>=arr[3].getIntX() && tmp.position.getIntX()<= arr[3].getIntX()+70 && tmp.position.getIntY()>=arr[3].getIntY() && tmp.position.getIntY()<= arr[3].getIntY()+110){
                steal[6] = steal[6] + tmp.amount;
                this.gold = this.gold - tmp.amount;
                tmp = null;
            }
            else if(this.countryName != "China" && tmp.position.getIntX()>=arr[4].getIntX() && tmp.position.getIntX()<= arr[4].getIntX()+70 && tmp.position.getIntY()>=arr[4].getIntY() && tmp.position.getIntY()<= arr[4].getIntY()+110){
                steal[8] = steal[8] + tmp.amount;
                this.gold = this.gold + tmp.amount;
                tmp = null;
            }
            else if(tmp.position.getIntY() <= 100){
                this.cash = (int) (this.cash + goldPrice* tmp.amount);
                this.gold = this.gold -  tmp.amount;
                this.worth = (int) (goldPrice*gold)+cash;
                tmp = null;
            }
            else {

            }
        }
        if (this.tmp1 != null) {
            tmp1.step();
            if(this.countryName != "USA" && tmp1.position.getIntX()>=arr[0].getIntX() && tmp1.position.getIntX()<= arr[0].getIntX()+70 && tmp1.position.getIntY()>=arr[0].getIntY() && tmp1.position.getIntY()<= arr[0].getIntY()+110){
                steal[1] = (int) (steal[1] + tmp1.amount*goldPrice);
                this.cash = (int) (this.cash - tmp1.amount*goldPrice);
                tmp1 = null;
            }
            else if(this.countryName != "Israel" && tmp1.position.getIntX()>=arr[1].getIntX() && tmp1.position.getIntX()<= arr[1].getIntX()+70 && tmp1.position.getIntY()>=arr[1].getIntY() && tmp1.position.getIntY()<= arr[1].getIntY()+110){
                steal[3] = (int) (steal[3] + tmp1.amount*goldPrice);
                this.cash = (int) (this.cash - tmp1.amount*goldPrice);
                tmp1 = null;
            }
            else if(this.countryName != "Turkey" && tmp1.position.getIntX()>=arr[2].getIntX() && tmp1.position.getIntX()<= arr[2].getIntX()+70 && tmp1.position.getIntY()>=arr[2].getIntY() && tmp1.position.getIntY()<= arr[2].getIntY()+110){
                steal[5] =(int) (steal[5] + tmp1.amount*goldPrice);
                this.cash =(int) (this.cash - tmp1.amount*goldPrice);
                tmp1 = null;
            }
            else if(this.countryName != "Russia" && tmp1.position.getIntX()>=arr[3].getIntX() && tmp1.position.getIntX()<= arr[3].getIntX()+70 && tmp1.position.getIntY()>=arr[3].getIntY() && tmp1.position.getIntY()<= arr[3].getIntY()+110){
                steal[7] = (int) (steal[7] + tmp1.amount*goldPrice);
                this.cash = (int) (this.cash - tmp1.amount*goldPrice);
                tmp1 = null;
            }
            else if(this.countryName != "China" && tmp1.position.getIntX()>=arr[4].getIntX() && tmp1.position.getIntX()<= arr[4].getIntX()+70 && tmp1.position.getIntY()>=arr[4].getIntY() && tmp1.position.getIntY()<= arr[4].getIntY()+110){
                steal[9] =(int) (steal[9] + tmp1.amount*goldPrice);
                this.cash = (int) (this.cash - tmp1.amount*goldPrice);
                tmp1 = null;
            }
            else if(tmp1.position.getIntY() <= 100){
                this.cash = (int) (this.cash - goldPrice*tmp1.amount);
                this.gold = this.gold + tmp1.amount;
                this.worth = (int) (goldPrice*gold)+cash;
                tmp1 = null;
            }
            else {

            }
        }
        if (this.tmp2 != null) {
            tmp2.step();
            if(this.countryName != "USA" && tmp2.position.getIntX()>=arr[0].getIntX() && tmp2.position.getIntX()<= arr[0].getIntX()+70 && tmp2.position.getIntY()>=arr[0].getIntY() && tmp2.position.getIntY()<= arr[0].getIntY()+110){
                steal[0] += tmp2.amount;
                this.gold -= tmp2.amount;
                tmp2 = null;
            }
            else if(this.countryName != "Israel" && tmp2.position.getIntX()>=arr[1].getIntX() && tmp2.position.getIntX()<= arr[1].getIntX()+70 && tmp2.position.getIntY()>=arr[1].getIntY() && tmp2.position.getIntY()<= arr[1].getIntY()+110){
                steal[2] += tmp2.amount;
                this.gold -= tmp2.amount;
                tmp2 = null;
            }
            else if(this.countryName != "Turkey" && tmp2.position.getIntX()>=arr[2].getIntX() && tmp2.position.getIntX()<= arr[2].getIntX()+70 && tmp2.position.getIntY()>=arr[2].getIntY() && tmp2.position.getIntY()<= arr[2].getIntY()+110){
                steal[4] += tmp2.amount;
                this.gold -= tmp2.amount;
                tmp2 = null;
            }
            else if(this.countryName != "Russia" && tmp2.position.getIntX()>=arr[3].getIntX() && tmp2.position.getIntX()<= arr[3].getIntX()+70 && tmp2.position.getIntY()>=arr[3].getIntY() && tmp2.position.getIntY()<= arr[3].getIntY()+110){
                steal[6] += tmp2.amount;
                this.gold -= tmp2.amount;
                tmp2 = null;
            }
            else if(this.countryName != "China" && tmp2.position.getIntX()>=arr[4].getIntX() && tmp2.position.getIntX()<= arr[4].getIntX()+70 && tmp2.position.getIntY()>=arr[4].getIntY() && tmp2.position.getIntY()<= arr[4].getIntY()+110){
                steal[8] += tmp2.amount;
                this.gold -= tmp2.amount;
                tmp2 = null;
            }
            else if(tmp2.position.getIntY() <= 100){
                this.cash =(int) (this.cash + goldPrice*gold);
                this.gold = this.gold - tmp2.amount;
                this.worth = (int) (goldPrice*gold)+cash;
                tmp2 = null;
            }
            else{

            }
        }
        if (this.tmp3 != null) {
            tmp3.step();
            if(this.countryName != "USA" && tmp3.position.getIntX()>=arr[0].getIntX() && tmp3.position.getIntX()<= arr[0].getIntX()+70 && tmp3.position.getIntY()>=arr[0].getIntY() && tmp3.position.getIntY()<= arr[0].getIntY()+110){
                steal[1] =(int) (steal[1]+tmp3.amount*goldPrice);
                this.cash =(int) (this.cash-tmp3.amount*goldPrice);
                tmp3 = null;
            }
            else if(this.countryName != "Israel" && tmp3.position.getIntX()>=arr[1].getIntX() && tmp3.position.getIntX()<= arr[1].getIntX()+70 && tmp3.position.getIntY()>=arr[1].getIntY() && tmp3.position.getIntY()<= arr[1].getIntY()+110){
                steal[3] =(int) (steal[3] + tmp3.amount*goldPrice);
                this.cash =(int) (this.cash - tmp3.amount*goldPrice);
                tmp3 = null;
            }
            else if(this.countryName != "Turkey" && tmp3.position.getIntX()>=arr[2].getIntX() && tmp3.position.getIntX()<= arr[2].getIntX()+70 && tmp3.position.getIntY()>=arr[2].getIntY() && tmp3.position.getIntY()<= arr[2].getIntY()+110){
                steal[5] =(int) (steal[5] + tmp3.amount*goldPrice);
                this.cash =(int) (this.cash - tmp3.amount*goldPrice);
                tmp3 = null;
            }
            else if(this.countryName != "Russia" && tmp3.position.getIntX()>=arr[3].getIntX() && tmp3.position.getIntX()<= arr[3].getIntX()+70 && tmp3.position.getIntY()>=arr[3].getIntY() && tmp3.position.getIntY()<= arr[3].getIntY()+110){
                steal[7] =(int) (steal[7] + tmp3.amount*goldPrice);
                this.cash =(int) (this.cash -  tmp3.amount*goldPrice);
                tmp3 = null;
            }
            else if(this.countryName != "China" && tmp3.position.getIntX()>=arr[4].getIntX() && tmp3.position.getIntX()<= arr[4].getIntX()+70 && tmp3.position.getIntY()>=arr[4].getIntY() && tmp3.position.getIntY()<= arr[4].getIntY()+110){
                steal[9] = (int) (steal[9] + tmp3.amount*goldPrice);
                this.cash =(int) (this.cash - tmp3.amount*goldPrice);
                tmp3 = null;
            }
            else if(tmp3.position.getIntY() <= 100){
                this.cash = (int) (this.cash - goldPrice*gold);
                this.gold = this.gold + tmp3.amount;
                this.worth = (int) (goldPrice*gold)+cash;
                tmp3 = null;
            }
            else{

            }
        }

    }

    //get current gold price
    public void getGold(double gp){
        this.goldPrice = gp;
    }


    public int[] getSteal(){
        return this.steal;
    }

    //makes zero all steal array
    public void zeros(){
        this.steal[0] = 0;
        this.steal[1] = 0;
        this.steal[2] = 0;
        this.steal[3] = 0;
        this.steal[4] = 0;
        this.steal[5] = 0;
        this.steal[6] = 0;
        this.steal[7] = 0;
        this.steal[8] = 0;
        this.steal[9] = 0;


    };

    //get positions of agents
    public void setC(Position[] arr){
        this.arr = arr;
    }


}