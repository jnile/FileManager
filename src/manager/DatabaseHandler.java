package manager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public abstract class DatabaseHandler {
    private static final String LOCATION_ARCHIVE = "D:/GameCreationsHub/Website/kofkarchivegit/jnile.github.io/Archives";
    private static final String LOCATION_DOCUMENTS = LOCATION_ARCHIVE + "/Documents";
    private static final String LOCATION_CONTENT_JSON = LOCATION_ARCHIVE + "/content.json";

    private static JSONObject contentJSON;
    private static long currentMaxDocNo = 0;

    private static JSONObject currentInfoJSON = new JSONObject();
    private static int currentDocNo;


    // Setter Functions
    public static void setContent() {
        
        // Fetch data from content.json
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(LOCATION_CONTENT_JSON));
            contentJSON = (JSONObject) obj;
        } catch(Exception e) {
            System.out.println(e);
        }

        // Get currentMaxDocNo from content.json
        JSONArray arr = (JSONArray) contentJSON.get("storage");
        Iterator iter = arr.iterator();
        while(iter.hasNext()) {
            JSONObject obj = (JSONObject) iter.next();
            int val = Math.toIntExact((long) obj.get("docNo"));
            if(val > currentMaxDocNo) {
                currentMaxDocNo = val;
            }
        }
    }

    public static void setInfoOfDocNo(int inpDocNo) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader((LOCATION_DOCUMENTS + "/" + Integer.toString(inpDocNo) + "/info.json")));
            currentInfoJSON = (JSONObject) obj;
            currentDocNo = inpDocNo;
        } catch(Exception e) {
            System.out.println(e);
        }
    }


    // Getter Functions
    public static JSONObject getInfoOfDocNo(int inpDocNo) {
        if(inpDocNo == currentDocNo) {
            return currentInfoJSON;
        } else {
            setInfoOfDocNo(inpDocNo);
            return currentInfoJSON;
        }
    }

    public static JSONArray getContentJSONStorage() {
        try {
            JSONArray temp = (JSONArray) contentJSON.get("storage");
            return temp;
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }


    // Public Functions
    public static void saveDataCurrentInfoJSONToFile() {
        // Write current Info Json to it's respect info.json file in the documents directory
        try {
            String directory = (LOCATION_DOCUMENTS + "/" + Integer.toString(currentDocNo));
            File toTestDir = new File(directory);
            if(!toTestDir.exists()) {
                toTestDir.mkdirs();
            }

            PrintWriter out = new PrintWriter(new FileWriter(directory + "/info.json"));
            out.write(currentInfoJSON.toString());
            out.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void saveDataContentJSONToFile() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(LOCATION_CONTENT_JSON));
            out.write(contentJSON.toString());
            out.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void saveImgsToDirectory(ArrayList<String> imgsUrl) {
        String dest = LOCATION_DOCUMENTS + "/" + Integer.toString(currentDocNo) + "/";

        for(String url : imgsUrl) {

            
            String[] x = url.split("/");
            try {
                
                File destFile = new File(dest + x[x.length - 1]);
                File sourceFile = new File(url.substring(6));
                sourceFile.createNewFile();
                System.out.println(url.substring(6));
                System.out.println(destFile.toPath());
                System.out.println(sourceFile.toPath());
                Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        System.out.println("Completed");
    }

    public static void createNewListing(String title, String dateStarted, String dateUpdated, JSONArray tags, String link, JSONArray img, String shortDesc, String longDesc, ArrayList<String> imgsUrl) {

        try {
            // Get a new Document No
            int newDocNo = Math.toIntExact(currentMaxDocNo) + 1;
            currentInfoJSON.clear();
            currentDocNo = newDocNo;

            HashMap<String, Object> newInfoHashMap = new HashMap<String, Object>();

            // Create JSONObejct for info.json
            newInfoHashMap.put("docNo", newDocNo);
            newInfoHashMap.put("title", title);
            newInfoHashMap.put("date_started", dateStarted);
            newInfoHashMap.put("date_updated", dateUpdated);
            newInfoHashMap.put("tags", tags);
            newInfoHashMap.put("link", link);
            newInfoHashMap.put("images", img);
            newInfoHashMap.put("short_desc", shortDesc);
            newInfoHashMap.put("long_desc", longDesc);

            currentInfoJSON = new JSONObject(newInfoHashMap);

            HashMap<String,Object> newContentHashMap = new HashMap<String, Object>();

            // Create JSONObject for content.json
            newContentHashMap.put("docNo", newDocNo);
            newContentHashMap.put("title", title);
            newContentHashMap.put("date_started", dateStarted);
            newContentHashMap.put("date_updated", dateUpdated);
            newContentHashMap.put("tags", tags);
            newContentHashMap.put("short_desc", shortDesc);

            // Add new JSONObject to existing content JSONObject
            JSONArray temp = (JSONArray) contentJSON.get("storage");
            temp.add(newContentHashMap);

            System.out.println(contentJSON.toString());
            saveDataContentJSONToFile();
            saveDataCurrentInfoJSONToFile();
            saveImgsToDirectory(imgsUrl);

        } catch(Exception e) {

        }


        System.out.println(currentInfoJSON.toString());
    }

    public static void updateListing(String title, String dateStarted, String dateUpdated, JSONArray tags, String link, JSONArray img, String shortDesc, String longDesc, ArrayList<String> imgsUrl) {
        try {
            // Get a new Document No
            currentInfoJSON.clear();

            HashMap<String, Object> newInfoHashMap = new HashMap<String, Object>();

            // Create JSONObejct for info.json
            newInfoHashMap.put("docNo", currentDocNo);
            newInfoHashMap.put("title", title);
            newInfoHashMap.put("date_started", dateStarted);
            newInfoHashMap.put("date_updated", dateUpdated);
            newInfoHashMap.put("tags", tags);
            newInfoHashMap.put("link", link);
            newInfoHashMap.put("images", img);
            newInfoHashMap.put("short_desc", shortDesc);
            newInfoHashMap.put("long_desc", longDesc);

            currentInfoJSON = new JSONObject(newInfoHashMap);

            // Add new JSONObject to existing content JSONObject
            JSONArray temp = (JSONArray) contentJSON.get("storage");
            JSONObject cont = (JSONObject) temp.get(currentDocNo);

            cont.replace("date_updated", dateUpdated);
            cont.replace("short_desc", shortDesc);
            cont.replace("title", title);
            cont.replace("tags", tags);
            cont.replace("date_started", dateStarted);

            System.out.println(contentJSON.toString());
            System.out.println(currentInfoJSON.toString());
            saveDataContentJSONToFile();
            saveDataCurrentInfoJSONToFile();
            saveImgsToDirectory(imgsUrl);

        } catch(Exception e) {

        }


        System.out.println(currentInfoJSON.toString());
    }

    // Returns the URL of the current images folder for the current docNo
    public static String getImageStorageURL() {
        String temp = "";

        temp += LOCATION_DOCUMENTS + "/" + Integer.toString(currentDocNo) + "/";

        return temp;
    }
}
