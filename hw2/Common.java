package project.utility;

import java.lang.reflect.Field;
import java.util.Random;

public class Common
{
    public static Random random = new Random() ;

    public static synchronized Object get (Object object , String fieldName )
    {
        // TODO
        // This function retrieves (gets) the private field of an object by using reflection
        // In case the function needs to throw an exception, throw this: SmartFactoryException( "Failed: get!" )

        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new SmartFactoryException("Failed: get!");
        }
    }

    public static synchronized void set ( Object object , String fieldName , Object value )
    {
        // TODO
        // This function modifies (sets) the private field of an object by using reflection
        // In case the function needs to throw an exception, throw this: SmartFactoryException( "Failed: set!" )

        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new SmartFactoryException("Failed: set!");
        }
    }
}