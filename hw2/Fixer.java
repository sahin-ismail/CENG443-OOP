package project.parts.logics;

import project.SimulationRunner;
import project.components.Factory;
import project.components.Robot;
import project.parts.payloads.*;
import project.utility.Common;

import java.util.List;

public class Fixer extends Logic
{
    @Override public void run ( Robot robot )
    {
        // TODO
        // Following messages are appropriate for this class
        // System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", ...);
        // System.out.printf("Robot %02d : Nothing to fix, waiting!%n", ...);
        // System.out.printf("Robot %02d : Fixer woke up, going back to work.%n", ...);

        Factory factory = SimulationRunner.factory;

        synchronized (factory.brokenRobots) {
            while((boolean)Common.get(robot, "isWaiting") == true){
                System.out.printf("Robot %02d : Nothing to fix, waiting!%n" , Common.get(robot, "serialNo"));
                try {
                    factory.brokenRobots.wait();
                    if(factory.stopProduction == true) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (System.out) {
                System.out.printf("Robot %02d : Fixer woke up, going back to work.%n" , Common.get(robot, "serialNo")) ;
            }

            if(factory.brokenRobots.size() > 0) {
                Robot broken = factory.brokenRobots.get(0);
                synchronized (broken) {
                    if (Common.get(broken, "arm") == null) {
                        Common.set(broken, "arm", factory.createPart("Arm"));
                    } else if (Common.get(broken, "payload") == null) {
                        Logic logic = (Logic) Common.get(broken, "logic");
                        if(logic instanceof Supplier){
                            Common.set(broken, "payload", factory.createPart("Gripper"));
                        } else if(logic instanceof Builder) {
                            Common.set(broken, "payload", factory.createPart("Welder"));
                        } else if(logic instanceof Inspector) {
                            Common.set(broken, "payload", factory.createPart("Camera"));
                        } else if(logic instanceof Fixer){
                            Common.set(broken, "payload", factory.createPart("MaintenanceKit"));
                        }
                    } else if (Common.get(broken, "logic") == null) {
                        Payload playload = (Payload) Common.get(broken, "payload");
                        if(playload instanceof Gripper) {
                            Common.set(broken, "logic", factory.createPart("Supplier"));
                        } else if (playload instanceof Welder) {
                            Common.set(broken, "logic", factory.createPart("Builder"));
                        } else if (playload instanceof Camera) {
                            Common.set(broken, "logic", factory.createPart("Inspector"));
                        } else if (playload instanceof MaintenanceKit){
                            Common.set(broken, "logic", factory.createPart("Fixer"));
                        }
                    }
                    factory.brokenRobots.remove(broken);
                    broken.notify();
                    synchronized (System.out) {
                        System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", Common.get(robot, "serialNo"), Common.get(broken, "serialNo"));
                    }
                }
            }
            if(factory.brokenRobots.size() == 0) {
                System.out.printf("Robot %02d : Nothing to fix, waiting!%n", Common.get(robot, "serialNo")) ;
                Common.set(robot, "isWaiting", true);
            }
        }
    }
}