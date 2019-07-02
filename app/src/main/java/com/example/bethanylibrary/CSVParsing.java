package com.example.bethanylibrary;

import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVParsing extends AppCompatActivity {

    public static ArrayList<String[]> book = new ArrayList<String[]>();
    public static String filename = new String();
    public static int ErrorCheck = 0;


    public void Parser(){

        ErrorCheck = 0;
        book.clear();

        try{
            File file = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = "";

            while((line = br.readLine()) != null){

                String[] token = line.split(",", 5);

                if(token.length != 5) { ErrorCheck = 1; break; }

                if(!token[0].contains("번")) {
                    if (ErrorCheck == 1) break;
                    book.add(token);
                }
            }
            br.close();
        }
        catch(FileNotFoundException e){

        }
        catch(IOException e){

        }
    }

    public ArrayList<String[]> getBook(){
        return book;
    }

//    public String getFilename() {
//        return filename;
//    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
