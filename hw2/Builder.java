package project.parts.logics;

import project.SimulationRunner;
import project.components.Factory;
import project.components.ProductionLine;
import project.components.Robot;
import project.parts.Arm;
import project.parts.Base;
import project.parts.Part;
import project.parts.payloads.*;
import project.utility.Common;

public class Builder extends Logic
{
    @Override public void run ( Robot robot )
    {
        // TODO
        // Following messages are appropriate for this class
        // System.out.printf("Robot %02d : Builder cannot build anything, waiting!%n", ...);
        // System.out.printf("Robot %02d : Builder woke up, going back to work.%n", ...);
        // System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", ...);

        Factory factory = SimulationRunner.factory;
        ProductionLine productionline = factory.productionLine;
        boolean carried = false;

        synchronized (productionline) {
            while((boolean) Common.get(robot, "isWaiting")) {
                try {
                    productionline.wait();
                    if(factory.stopProduction) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (System.out) {
                System.out.printf( "Robot %02d : Builder woke up, going back to work.%n", Common.get(robot, "serialNo"));
            }
            if (carried == false) {
                synchronized (factory.productionLine.parts) {
                    int not_arm;
                    not_arm = -1;
                    for(int i=0; i<productionline.parts.size(); i++) {
                        Part p = productionline.parts.get(i);
                        if(p instanceof Base){
                            Base b = (Base)p;
                            if(Common.get(b, "arm") == null) {
                                not_arm =  i;
                            }
                        }
                    }
                    if (not_arm != -1) {
                        int first_arm = -1;
                        for(int i=0; i<productionline.parts.size(); i++) {
                            if(productionline.parts.get(i) instanceof Arm) {
                                first_arm = i;
                            }
                        }
                        if (first_arm != -1) {
                            Base b = (Base) productionline.parts.get(not_arm);
                            Arm a = (Arm) productionline.parts.get(first_arm);
                            Common.set(b, "arm", a);
                            productionline.parts.remove(a);
                            carried = true;
                        }
                    }
                }
            }
            if (carried == false) {
                synchronized (factory.productionLine.parts){
                    int not_paylaod = -1;
                    for(int i=0; i<productionline.parts.size(); i++) {
                        Part p = productionline.parts.get(i);
                        if(p instanceof Base) {
                            Base b = (Base) p;
                            if(Common.get(b, "arm") != null && Common.get(b, "payload") == null) {
                                not_paylaod = i;
                            }
                        }
                    }
                    if(not_paylaod != -1) {
                        int first_payload = -1;
                        for(int i=0; i<productionline.parts.size(); i++){
                            if(productionline.parts.get(i) instanceof Payload) {
                                first_payload = i;
                            }
                        }
                        if(first_payload != -1) {
                            Base b = (Base) productionline.parts.get(not_paylaod);
                            Payload p = (Payload) productionline.parts.get(first_payload);
                            Common.set(b, "payload", p);
                            productionline.parts.remove(p);
                            carried = true;
                        }
                    }
                }
            }
            if (carried == false) {
                synchronized (SimulationRunner.factory.productionLine.parts) {
                    int not_logic = -1;
                    int start = 0;
                    for(int i=start; i<productionline.parts.size(); i++) {
                        Part p = productionline.parts.get(i);
                        if(p instanceof Base) {
                            Base b = (Base) p;
                            if(Common.get(b, "arm") != null && Common.get(b, "payload")!= null && Common.get(b, "logic") == null) {
                                not_logic = i;
                            }
                        }
                    }
                    if(not_logic != -1) {
                        while(not_logic != -1) {
                            Base b = (Base) productionline.parts.get(not_logic);
                            Payload p = (Payload) Common.get(b, "payload");
                            if(p instanceof Gripper) {
                                int firstSupplier = -1;
                                for(int i=0; i<productionline.parts.size(); i++) {
                                    if(productionline.parts.get(i) instanceof Supplier) {
                                        firstSupplier = i;
                                    }
                                }
                                if(firstSupplier != -1) {
                                    Logic l = (Logic) productionline.parts.get(firstSupplier);
                                    Common.set(b, "logic", l);
                                    productionline.parts.remove(l);
                                    carried = true;
                                }
                            } else if (p instanceof Welder) {
                                int firstBuilder = -1;
                                for(int i=0; i<productionline.parts.size(); i++) {
                                    if(productionline.parts.get(i) instanceof Builder) {
                                        firstBuilder = i;
                                    }
                                }
                                if(firstBuilder  != -1) {
                                    Logic l = (Logic) productionline.parts.get(firstBuilder);
                                    Common.set(b, "logic", l);
                                    productionline.parts.remove(l);
                                    carried = true;
                                }
                            } else if (p instanceof Camera) {
                                int firstInspector = -1;
                                for(int i=0; i<productionline.parts.size(); i++) {
                                    if(productionline.parts.get(i) instanceof Inspector) {
                                        firstInspector = i;
                                    }
                                }
                                if(firstInspector != -1) {
                                    Logic l = (Logic) productionline.parts.get(firstInspector);
                                    Common.set(b, "logic", l);
                                    productionline.parts.remove(l);
                                    carried = true;
                                }
                            } else if (p instanceof MaintenanceKit) {
                                int firstFixer = -1;
                                for(int i=0; i<productionline.parts.size(); i++) {
                                    if(productionline.parts.get(i) instanceof Fixer) {
                                        firstFixer = i;
                                    }
                                }
                                if(firstFixer != -1) {
                                    Logic l = (Logic) productionline.parts.get(firstFixer);
                                    Common.set(b, "logic", l);
                                    productionline.parts.remove(l);
                                    carried = true;
                                }
                            }
                            start = not_logic + 1;
                            not_logic = -1;
                            for(int i=start; i<productionline.parts.size(); i++) {
                                Part p2 = productionline.parts.get(i);
                                if(p2 instanceof Base) {
                                    Base b2 = (Base) p2;
                                    if(Common.get(b2, "arm") != null && Common.get(b2, "payload")!= null && Common.get(b2, "logic") == null) {
                                        not_logic = i;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (carried == false) {
                if(carryRobot(productionline,factory) == 0){
                    carried = false;
                }else if(carryRobot(productionline,factory) == 1){
                    carried = true;
                }
            }
            if (carried == false) {
                synchronized (System.out) {
                    System.out.printf("Robot %02d : Builder cannot build anything, waiting!%n", Common.get(robot, "serialNo"));
                }
                synchronized (this) {
                    Common.set(robot, "isWaiting", true);
                }
            }
        }
        if(carried == true) {
            synchronized (System.out) {
                System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", Common.get(robot, "serialNo"));
            }
            synchronized (factory.productionLine.parts){
                SimulationRunner.productionLineDisplay.repaint();
            }
            synchronized (factory.robots){
                SimulationRunner.robotsDisplay.repaint();
            }
            synchronized (factory.storage.robots){
                SimulationRunner.storageDisplay.repaint();
                if(factory.storage.robots.size() == factory.storage.maxCapacity) {
                    factory.initiateStop();
                }
            }
        }
    }

    public int carryRobot(ProductionLine productionline, Factory factory) {
        synchronized (factory.productionLine.parts) {
            int first_completed = -1;
            int i = 0;

            while( i<productionline.parts.size()) {
                Part p =productionline.parts.get(i);
                if(p instanceof Base) {
                    Base b = (Base)p;

                    if(Common.get(b, "arm") != null && Common.get(b, "payload")!= null && Common.get(b, "logic") != null) {
                        first_completed = i;
                    }
                }
                i++;
            }
            if (first_completed != -1) {
                synchronized (factory.robots) {
                    if (factory.robots.size() < factory.maxRobots) {
                        Robot r = (Robot) productionline.parts.get(first_completed);
                        factory.robots.add(r);
                        productionline.parts.remove(r);

                        new Thread(r).start();

                        return 1;
                    }
                }
                synchronized (factory.storage.robots) {
                    if (factory.storage.robots.size() < factory.storage.maxCapacity) {
                        Robot r = (Robot) productionline.parts.get(first_completed);
                        factory.storage.robots.add(r);
                        productionline.parts.remove(r);

                        return 1;
                    }
                }
            }
            return 0;
        }
    }

}