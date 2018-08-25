package com.samayu.sca.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception{
        String filename = "/home/samayuadmin/Downloads/in.csv";

        List<String> lines = new ArrayList<>();

        BufferedReader buf = new BufferedReader( new FileReader( filename ));

        while( true ){
            String line = buf.readLine();
            if( line == null ) break;
            lines.add( convertToSQL( line ));
        }

        formSQL(lines);
    }

    public static void formSQL(List<String> entries ){

        StringBuilder builder = new StringBuilder("insert into sca_cities ( country_code , latitude , longitude , name ) values ");

        for(String entry:entries ){
            builder.append(" ");
            builder.append( entry );
            builder.append(",\n");
        }

        builder.deleteCharAt( builder.length() -1 );
        builder.deleteCharAt( builder.length() -1 );

        System.out.println( builder.toString() );

    }

    public static String convertToSQL( String line){

        String[] cols = line.split("," , -1 );

        String sql = "('"+cols[4]+"','"+cols[1]+"','"+cols[2]+"','"+cols[0]+"')";
        return sql;


    }
}
