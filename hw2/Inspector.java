package project.parts.logics;

import project.SimulationRunner;
import project.components.Factory;
import project.components.Robot;
import project.parts.Arm;
import project.parts.payloads.Payload;
import project.utility.Common;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Inspector extends Logic
{
    @Override public void run ( Robot robot )
    {
        // TODO
        // Following messages are appropriate for this class
        // System.out.printf( "Robot %02d : Detected a broken robot (%02d), adding it to broken robots list.%n", ...);
        // System.out.printf( "Robot %02d : Notifying waiting fixers.%n", ...)

        Factory factory = SimulationRunner.factory;

        Arm arm;
        Payload payload;
        Logic logic;

        synchronized (factory.robots) {
            for(Robot r : factory.robots) {

                arm = (Arm) Common.get(r,"arm");
                payload = (Payload) Common.get(r,"payload");
                logic = (Logic) Common.get(r,"logic");

                if(arm == null || payload == null || logic == null) {

                    synchronized (factory.brokenRobots){

                        if(factory.brokenRobots.contains(r) == false) {

                            synchronized (System.out) {
                                System.out.printf( "Robot %02d : Detected a broken robot (%02d), adding it to broken robots list.%n", Common.get(robot, "serialNo"), Common.get(r, "serialNo")) ;
                            }

                            factory.brokenRobots.add(r);

                            synchronized (System.out) {
                                System.out.printf( "Robot %02d : Notifying waiting fixers.%n", Common.get(robot, "serialNo")) ;
                            }

                            for(Robot rr: factory.robots) {

                                if(Common.get(rr, "logic") instanceof Fixer) {
                                    if((boolean)Common.get(rr, "isWaiting") == true) {
                                        Common.set(rr, "isWaiting", false);
                                    }
                                }
                            }

                            factory.brokenRobots.notifyAll();

                        }
                    }
                }
            }
        }
    }
}