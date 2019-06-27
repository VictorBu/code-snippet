package com.karonda.sequentialuuid.utils;

// https://github.com/hibernate/hibernate-orm/blob/master/hibernate-core/src/main/java/org/hibernate/id/UUIDHexGenerator.java

public class SequentialUuidHexGenerator extends AbstractUUIDGenerator{

    private static final String sep = "-";

    public static String generate() {
//        return format( getIP() ) + sep
//                + format( getJVM() ) + sep
//                + format( getHiTime() ) + sep
//                + format( getLoTime() ) + sep
//                + format( getCount() );

        return
                format( getJVM() ) + sep
                + format( getHiTime() ) + sep
                + format( getLoTime() ) + sep
                + format( getIP() ) + sep
                + format( getCount() );
    }

    protected static String format(int intValue) {
        String formatted = Integer.toHexString( intValue );
        StringBuilder buf = new StringBuilder( "00000000" );
        buf.replace( 8 - formatted.length(), 8, formatted );
        return buf.toString();
    }

    protected  static String format(short shortValue) {
        String formatted = Integer.toHexString( shortValue );
        StringBuilder buf = new StringBuilder( "0000" );
        buf.replace( 4 - formatted.length(), 4, formatted );
        return buf.toString();
    }
}
