package project.components;

import project.parts.Arm;
import project.parts.Base;
import project.parts.Part;
import project.parts.logics.Builder;
import project.parts.logics.Fixer;
import project.parts.logics.Inspector;
import project.parts.logics.Supplier;
import project.parts.payloads.Camera;
import project.parts.payloads.Gripper;
import project.parts.payloads.MaintenanceKit;
import project.parts.payloads.Welder;
import project.utility.Common;
import project.utility.SmartFactoryException;

import java.util.ArrayList;
import java.util.List;

public class Factory {
    private static int nextSerialNo = 1;

    public static Base createBase() {
        // TODO
        // This function returns a base by applying factory and abstract factory

        Base base;
        try{
            base = (Base)Class.forName("project.parts.Base").getConstructor(Integer.TYPE).newInstance(nextSerialNo);

        }catch (Exception e){
            throw new SmartFactoryException("Failed: createBase!");
        }
        nextSerialNo++;
        return base;
    }

    public static Part createPart(String name) {
        // TODO
        // This function returns a robot part by applying factory and abstract factory patterns
        // In case the function needs to throw an exception, throw this: SmartFactoryException( "Failed: createPart!" )

        try {
            if (name == "Arm") {
                return (Part)Class.forName("project.parts."+name).getConstructor().newInstance();
            } else if (name == "Gripper"){
                return (Part)Class.forName("project.parts.payloads."+name).getConstructor().newInstance();
            } else if (name == "Supplier"){
                return (Part)Class.forName("project.parts.logics."+name).getConstructor().newInstance();
            }else if(name =="Welder"){
                return (Part)Class.forName("project.parts.payloads."+name).getConstructor().newInstance();
            } else if(name == "Camera") {
                return (Part)Class.forName("project.parts.payloads."+name).getConstructor().newInstance();
            } else if (name == "Builder") {
                return (Part)Class.forName("project.parts.logics."+name).getConstructor().newInstance();
            } else if(name == "MaintenanceKit") {
                return (Part)Class.forName("project.parts.payloads."+name).getConstructor().newInstance();
            } else if(name == "Fixer") {
                return (Part)Class.forName("project.parts.logics."+name).getConstructor().newInstance();
            } else if (name == "Inspector"){
                return (Part)Class.forName("project.parts.logics."+name).getConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new SmartFactoryException("Failed: createPart!");
        }

        return null;

    }

    public  int            maxRobots      ;
    public List<Robot>     robots         ;
    public ProductionLine  productionLine ;
    public  Storage        storage        ;
    public  List<Robot>    brokenRobots   ;
    public  boolean        stopProduction ;

    public Factory ( int maxRobots , int maxProductionLineCapacity , int maxStorageCapacity )
    {

        this.maxRobots      = maxRobots                                       ;
        this.robots         = new ArrayList<>()                               ;
        this.productionLine = new ProductionLine( maxProductionLineCapacity ) ;
        this.storage        = new Storage( maxStorageCapacity        )        ;
        this.brokenRobots   = new ArrayList<>()                               ;
        this.stopProduction = false                                           ;
        Base robot ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "Gripper"        ) ) ;
        Common.set( robot , "logic"   , createPart( "Supplier"       ) ) ;
        robots.add(robot ) ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "Welder"         ) ) ;
        Common.set( robot , "logic"   , createPart( "Builder"        ) ) ;
        robots.add(robot ) ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "Camera"         ) ) ;
        Common.set( robot , "logic"   , createPart( "Inspector"      ) ) ;
        robots.add(robot ) ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "Camera"         ) ) ;
        Common.set( robot , "logic"   , createPart( "Inspector"      ) ) ;
        robots.add(robot ) ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "MaintenanceKit" ) ) ;
        Common.set( robot , "logic"   , createPart( "Fixer"          ) ) ;
        robots.add(robot ) ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "MaintenanceKit" ) ) ;
        Common.set( robot , "logic"   , createPart( "Fixer"          ) ) ;
        robots.add(robot ) ;
    }

    public void start ()
    {
        for ( Robot r : robots )  { new Thread( r ).start() ; }
    }

    public void initiateStop ()
    {
        stopProduction = true ;

        synchronized ( robots )
        {
            for ( Robot r : robots )  { synchronized ( r )  { r.notifyAll() ; } }
        }

        synchronized ( productionLine )  { productionLine.notifyAll() ; }
        synchronized ( brokenRobots   )  { brokenRobots  .notifyAll() ; }
    }
}