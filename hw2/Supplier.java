package project.parts.logics;

import project.SimulationRunner;
import project.components.Factory;
import project.components.ProductionLine;
import project.components.Robot;
import project.parts.Part;
import project.utility.Common;

public class Supplier extends Logic
{
    @Override public void run ( Robot robot )
    {
        // TODO
        // Following messages are appropriate for this class
        // System.out.printf( "Robot %02d : Supplying a random part on production line.%n", ...);
        // System.out.printf( "Robot %02d : Production line is full, removing a random part from production line.%n", ...);
        // System.out.printf( "Robot %02d : Waking up waiting builders.%n", ...);

        Factory factory = SimulationRunner.factory;

        synchronized (factory.productionLine) {

            ProductionLine productionline = factory.productionLine;

            if (productionline.parts.size() < productionline.maxCapacity) {

                synchronized (System.out) {
                    System.out.printf("Robot %02d : Supplying a random part on production line.%n", Common.get(robot, "serialNo"));
                }

                synchronized (factory.productionLine.parts){
                    productionline.parts.add(randomPart());
                }
            } else if (productionline.parts.size() == productionline.maxCapacity) {

                synchronized (System.out) {
                    System.out.printf("Robot %02d : Production line is full, removing a random part from production line.%n", Common.get(robot, "serialNo"));
                }

                synchronized (factory.productionLine.parts){
                    int random_index = Common.random.nextInt(productionline.maxCapacity);
                    productionline.parts.remove(random_index);
                }
            }
            System.out.printf( "Robot %02d : Waking up waiting builders.%n", Common.get(robot,"serialNo")) ;
            synchronized (factory.robots) {

                for (Robot r : factory.robots) {

                    synchronized (r) {

                        if (Common.get(r, "logic") instanceof Builder) {
                            if ((boolean) Common.get(r, "isWaiting") == true) {
                                Common.set(r, "isWaiting", false);
                            }
                        }
                    }
                }
            }
            productionline.notifyAll();
        }
        synchronized (factory.productionLine.parts) {
            SimulationRunner.productionLineDisplay.repaint();
        }
    }



    public Part randomPart() {
        int random = Common.random.nextInt(16);
        if(random == 0 || random == 1 || random == 2 || random == 3){
            return Factory.createBase();
        }else if (random == 4 || random == 5 || random == 6 || random == 7){
            return Factory.createPart("Arm");
        }else if(random == 8){
            return Factory.createPart("Gripper");
        }else if(random == 9) {
            return Factory.createPart("Welder");
        }else if(random == 10) {
            return Factory.createPart("Camera");
        }else if(random == 11){
            return Factory.createPart("MaintenanceKit");
        }else if(random == 12){
            return Factory.createPart("Supplier");
        }else if( random == 13) {
            return Factory.createPart("Builder");
        }else if(random == 14) {
            return Factory.createPart("Inspector");
        }else if(random == 15) {
            return Factory.createPart("Fixer");
        }else{
            return Factory.createBase();
        }
    }
}