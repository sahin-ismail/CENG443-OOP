import java.util.Random;

public class Common {
    private static final String title = "Gold Wars";
    private static final int windowWidth = 1650;
    private static final int windowHeight = 1000;

    private static final GoldPrice goldPrice = new GoldPrice(588, 62);

    private static final Random randomGenerator = new Random(1234);

    private static final int upperLineY = 100;
    private final static Country usa = new Country(215,620, "./images/usa.jpg","USA","US");
    private final static Country israel = new Country(490,620, "./images/israel.jpg","Israel","IS");
    private final static Country turkey = new Country(765,620, "./images/turkey.jpg","Turkey","Tr");
    private final static Country russia = new Country(1040,620, "./images/russia.jpg","Russia","RS");
    private final static Country china = new Country(1315,620, "./images/china.jpg","China","CH");

    private final static BasicAgent cia = new BasicAgent(215,400,"./images/cia.png","CIA","USA",10);
    private final static BasicAgent mossad = new BasicAgent(490,400, "./images/mossad.png","Mossad","Israel",20);
    private final static BasicAgent mit = new BasicAgent(765,400, "./images/mit.png","MIT","Turkey",30);
    private final static BasicAgent svr = new BasicAgent(1040,400, "./images/svr.png","SVR","Russia",40);
    private final static BasicAgent mss = new BasicAgent(1315,400, "./images/mss.png","MSS","China",50);
    private static Position[] arr = new Position[5];
    private static int[][] steal = new int[5][10];
    private static int i,j;

    static  {
        // TODO: Here, you can initialize the fields you have declared
        for(i = 0;i<5;i++){
            for(j = 0; j<10 ; j++){
                steal[i][j] = 0;
            }
        }
    }

    // getters
    public static String getTitle() { return title; }
    public static int getWindowWidth() { return windowWidth; }
    public static int getWindowHeight() { return windowHeight; }

    public static GoldPrice getGoldPrice() { return goldPrice; }

    public static Country getUsa() {return usa; }
    public static Country getIsrael() {return israel; }
    public static Country getTurkey() {return turkey; }
    public static Country getRussia() {return russia; }
    public static Country getChina() {return china; }

    public static BasicAgent getCia() {return cia;}
    public static BasicAgent getMossad() {return mossad;}
    public static BasicAgent getMit() {return mit; }
    public static BasicAgent getSvr() {return svr;}
    public static BasicAgent getMss() {return mss; }

    public static Random getRandomGenerator() { return randomGenerator; }
    public static int getUpperLineY() { return upperLineY; }

    //stepping all entities
    public static void stepAllEntities() {

        if (randomGenerator.nextInt(200) == 0) {
            goldPrice.step();
        }

        //step all agents
        cia.step();
        mossad.step();
        mit.step();
        svr.step();
        mss.step();

        arr[0] = cia.getPosition();
        arr[1] = mossad.getPosition();
        arr[2] = mit.getPosition();
        arr[3] = svr.getPosition();
        arr[4] = mss.getPosition();

        //this sends position array of agents to countries
        usa.setC(arr);
        israel.setC(arr);
        turkey.setC(arr);
        russia.setC(arr);
        china.setC(arr);

        //this send current gold prices to countries
        usa.getGold(goldPrice.getCurrentPrice());
        turkey.getGold(goldPrice.getCurrentPrice());
        israel.getGold(goldPrice.getCurrentPrice());
        russia.getGold(goldPrice.getCurrentPrice());
        china.getGold(goldPrice.getCurrentPrice());

        //step all countries
        usa.step();
        israel.step();
        turkey.step();
        russia.step();
        china.step();


        /* The following lines of code were written to set new values for countries and agents after stealings. */

        cia.setGain(cia.getGain() + (int) (usa.getSteal()[0]*goldPrice.getCurrentPrice()));
        cia.setGain(cia.getGain() + usa.getSteal()[1]);

        usa.setGold(usa.getGold() + usa.getSteal()[0]);
        usa.setCash(usa.getCash() + usa.getSteal()[1]);

        cia.setGain(cia.getGain() + (int) (israel.getSteal()[0]*goldPrice.getCurrentPrice()));
        cia.setGain(cia.getGain() + israel.getSteal()[1]);

        usa.setGold(usa.getGold() + israel.getSteal()[0]);
        usa.setCash(usa.getCash() + israel.getSteal()[1]);

        cia.setGain(cia.getGain() + (int) (turkey.getSteal()[0]*goldPrice.getCurrentPrice()));
        cia.setGain(cia.getGain() + turkey.getSteal()[1]);

        usa.setGold(usa.getGold() + turkey.getSteal()[0]);
        usa.setCash(usa.getCash() + turkey.getSteal()[1]);

        cia.setGain(cia.getGain() + (int) (russia.getSteal()[0]*goldPrice.getCurrentPrice()));
        cia.setGain(cia.getGain() + russia.getSteal()[1]);

        usa.setGold(usa.getGold() + russia.getSteal()[0]);
        usa.setCash(usa.getCash() + russia.getSteal()[1]);

        cia.setGain(cia.getGain() + (int) (china.getSteal()[0]*goldPrice.getCurrentPrice()));
        cia.setGain(cia.getGain() + china.getSteal()[1]);

        usa.setGold(usa.getGold() + china.getSteal()[0]);
        usa.setCash(usa.getCash() + china.getSteal()[1]);


        mossad.setGain(mossad.getGain() + (int) (usa.getSteal()[2]*goldPrice.getCurrentPrice()));
        mossad.setGain(mossad.getGain() + usa.getSteal()[3]);

        israel.setGold(israel.getGold() + usa.getSteal()[2]);
        israel.setCash(israel.getCash() + usa.getSteal()[3]);

        mossad.setGain(mossad.getGain() + (int) (israel.getSteal()[2]*goldPrice.getCurrentPrice()));
        mossad.setGain(mossad.getGain() + israel.getSteal()[3]);

        israel.setGold(israel.getGold() + israel.getSteal()[2]);
        israel.setCash(israel.getCash() + israel.getSteal()[3]);

        mossad.setGain(mossad.getGain() + (int) (turkey.getSteal()[2]*goldPrice.getCurrentPrice()));
        mossad.setGain(mossad.getGain() + turkey.getSteal()[3]);

        israel.setGold(israel.getGold() + turkey.getSteal()[2]);
        israel.setCash(israel.getCash() + turkey.getSteal()[3]);

        mossad.setGain(mossad.getGain() + (int) (russia.getSteal()[2]*goldPrice.getCurrentPrice()));
        mossad.setGain(mossad.getGain() + russia.getSteal()[3]);

        israel.setGold(israel.getGold() + russia.getSteal()[2]);
        israel.setCash(israel.getCash() + russia.getSteal()[3]);

        mossad.setGain(mossad.getGain() + (int) (china.getSteal()[2]*goldPrice.getCurrentPrice()));
        mossad.setGain(mossad.getGain() + china.getSteal()[3]);

        israel.setGold(israel.getGold() + china.getSteal()[2]);
        israel.setCash(israel.getCash() + china.getSteal()[3]);


        mit.setGain(mit.getGain() + (int) (usa.getSteal()[4]*goldPrice.getCurrentPrice()));
        mit.setGain(mit.getGain() + usa.getSteal()[5]);

        turkey.setGold(turkey.getGold() + usa.getSteal()[4]);
        turkey.setCash(turkey.getCash() + usa.getSteal()[5]);

        mit.setGain(mit.getGain() + (int) (israel.getSteal()[4]*goldPrice.getCurrentPrice()));
        mit.setGain(mit.getGain() + israel.getSteal()[5]);

        turkey.setGold(turkey.getGold() + israel.getSteal()[4]);
        turkey.setCash(turkey.getCash() + israel.getSteal()[5]);

        mit.setGain(mit.getGain() + (int) (turkey.getSteal()[4]*goldPrice.getCurrentPrice()));
        mit.setGain(mit.getGain() + turkey.getSteal()[5]);

        turkey.setGold(turkey.getGold() + turkey.getSteal()[4]);
        turkey.setCash(turkey.getCash() + turkey.getSteal()[5]);

        mit.setGain(mit.getGain() + (int) (russia.getSteal()[4]*goldPrice.getCurrentPrice()));
        mit.setGain(mit.getGain() + russia.getSteal()[5]);

        turkey.setGold(turkey.getGold() + russia.getSteal()[4]);
        turkey.setCash(turkey.getCash() + russia.getSteal()[5]);

        mit.setGain(mit.getGain() + (int) (china.getSteal()[4]*goldPrice.getCurrentPrice()));
        mit.setGain(mit.getGain() + china.getSteal()[5]);

        turkey.setGold(turkey.getGold() + china.getSteal()[4]);
        turkey.setCash(turkey.getCash() + china.getSteal()[5]);

        svr.setGain(svr.getGain() + (int) (usa.getSteal()[6]*goldPrice.getCurrentPrice()));
        svr.setGain(svr.getGain() + usa.getSteal()[7]);

        russia.setGold(russia.getGold() + usa.getSteal()[6]);
        russia.setCash(russia.getCash() + usa.getSteal()[7]);

        svr.setGain(svr.getGain() + (int) (israel.getSteal()[6]*goldPrice.getCurrentPrice()));
        svr.setGain(svr.getGain() + israel.getSteal()[7]);

        russia.setGold(russia.getGold() + israel.getSteal()[6]);
        russia.setCash(russia.getCash() + israel.getSteal()[7]);

        svr.setGain(svr.getGain() + (int) (turkey.getSteal()[6]*goldPrice.getCurrentPrice()));
        svr.setGain(svr.getGain() + turkey.getSteal()[7]);

        russia.setGold(russia.getGold() + turkey.getSteal()[6]);
        russia.setCash(russia.getCash() + turkey.getSteal()[7]);

        svr.setGain(svr.getGain() + (int) (russia.getSteal()[6]*goldPrice.getCurrentPrice()));
        svr.setGain(svr.getGain() + russia.getSteal()[7]);

        russia.setGold(russia.getGold() + russia.getSteal()[6]);
        russia.setCash(russia.getCash() + russia.getSteal()[7]);

        svr.setGain(svr.getGain() + (int) (china.getSteal()[6]*goldPrice.getCurrentPrice()));
        svr.setGain(svr.getGain() + china.getSteal()[7]);

        russia.setGold(russia.getGold() + china.getSteal()[6]);
        russia.setCash(russia.getCash() + china.getSteal()[7]);

        mss.setGain(mss.getGain() + (int) (usa.getSteal()[8]*goldPrice.getCurrentPrice()));
        mss.setGain(mss.getGain() + usa.getSteal()[9]);

        china.setGold(china.getGold() + usa.getSteal()[8]);
        china.setCash(china.getCash() + usa.getSteal()[9]);

        mss.setGain(mss.getGain() + (int) (israel.getSteal()[8]*goldPrice.getCurrentPrice()));
        mss.setGain(mss.getGain() + israel.getSteal()[9]);

        china.setGold(china.getGold() + israel.getSteal()[8]);
        china.setCash(china.getCash() + israel.getSteal()[9]);

        mss.setGain(mss.getGain() + (int) (turkey.getSteal()[8]*goldPrice.getCurrentPrice()));
        mss.setGain(mss.getGain() + turkey.getSteal()[9]);

        china.setGold(china.getGold() + turkey.getSteal()[8]);
        china.setCash(china.getCash() + turkey.getSteal()[9]);

        mss.setGain(mss.getGain() + (int) (russia.getSteal()[8]*goldPrice.getCurrentPrice()));
        mss.setGain(mss.getGain() + russia.getSteal()[9]);

        china.setGold(china.getGold() + russia.getSteal()[8]);
        china.setCash(china.getCash() + russia.getSteal()[9]);

        mss.setGain(mss.getGain() + (int) (china.getSteal()[8]*goldPrice.getCurrentPrice()));
        mss.setGain(mss.getGain() + china.getSteal()[9]);

        china.setGold(china.getGold() + china.getSteal()[8]);
        china.setCash(china.getCash() + china.getSteal()[9]);


        //these makes stolen money of countries 0 after setting new values
        usa.zeros();
        israel.zeros();
        turkey.zeros();
        russia.zeros();
        china.zeros();
    }
}