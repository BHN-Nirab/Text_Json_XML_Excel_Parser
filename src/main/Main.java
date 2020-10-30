package main;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import parser.Parser;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    private static ArrayList<Parser> parsers = new ArrayList<>();
    private static String JSONFormat;
    private static String XMLFormat;
    private static String CSVFormat;

    private static void takeInputFromTextFileAndParse(File file) {
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                Parser parser = new Parser();
                String[] line;
                String value;

                line = sc.nextLine().split("=");
                value = line[1];
                parser.setName(value);

                line = sc.nextLine().split("=");
                value = line[1];
                parser.setEmail(value);

                line = sc.nextLine().split("=");
                value = line[1];
                parser.setPassword(value);

                line = sc.nextLine().split("=");
                value = line[1];
                parser.setPhoneNumber(value);

                parsers.add(parser);

            }
        } catch (Exception e) {
            System.out.println("Faild to read file! error: " + e.getMessage());
        }
    }

    private static void convert() {
        JSONArray jsonArray = new JSONArray();

        try {

            for (Parser parser : parsers) {

                JSONObject details = new JSONObject();

                details.put("Name", parser.getName());
                details.put("Email", parser.getEmail());
                details.put("Password", parser.getPassword());
                details.put("Phone Number", parser.getPhoneNumber());

                jsonArray.put(details);

            }

            JSONFormat = jsonArray.toString();
            XMLFormat = XML.toString(jsonArray);
            CSVFormat = CDL.toString(jsonArray);

        } catch (Exception e) {
            System.out.println("Faild to Parse Text File! error: " + e.getMessage());
        }

    }

    private static void writeToFile(File file, String type) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);

            if (type.toLowerCase().equals("xml"))
                outputStream.write(XMLFormat.getBytes());
            else if (type.toLowerCase().equals("json"))
                outputStream.write(JSONFormat.getBytes());
            else if (type.toLowerCase().equals("csv"))
                outputStream.write(CSVFormat.getBytes());

            outputStream.close();
        } catch (Exception e) {
            System.out.println("Faild to write file! error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        takeInputFromTextFileAndParse(new File("src/res/input.txt"));
        convert();
        writeToFile(new File("src/res/output.json"), "json");
        writeToFile(new File("src/res/output.xml"), "xml");
        writeToFile(new File("src/res/output.csv"), "csv");

    }

}
