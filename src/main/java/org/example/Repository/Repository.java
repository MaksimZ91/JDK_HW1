package org.example.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Repository {
    public void write(String data, String name) throws IOException{
        try(FileWriter fr = new FileWriter(name +".txt", true)) {
            fr.write(data);
            fr.append(System.lineSeparator());
            fr.flush();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
    public  String read(String name) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        try(FileReader reader = new FileReader(name +".txt")){
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()){
                sb.append(scan.nextLine()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        return sb.toString();
    }
}


