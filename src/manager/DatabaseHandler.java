package manager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
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

    public static JSONObject getInfoOfDocNo(int inpDocNo) {
        if(inpDocNo == currentDocNo) {
            return currentInfoJSON;
        } else {
            setInfoOfDocNo(inpDocNo);
            return currentInfoJSON;
        }
    }

    public static void saveDataCurrentInfoJSONToFile() {
        // Write current Info Json to it's respect info.json file in the documents directory
        try {
            String directory = (LOCATION_DOCUMENTS + "/" + Integer.toString(currentDocNo));
            File toTestDir = new File(directory);
            if(!toTestDir.exists()) {
                toTestDir.mkdirs();
            }

            PrintWriter out = new PrintWriter(new FileWriter(directory + "/info,json"));
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

    public static void createNewListing(String title, String dateStarted, String dateUpdated, JSONArray tags, String link, JSONArray img, String shortDesc, String longDesc) {
        JSONObject newListingToAddToContent = new JSONObject();

        try {
            // Get a new Document No
            int newDocNo = Math.toIntExact(currentMaxDocNo) + 1;
            currentInfoJSON.clear();
            currentDocNo = newDocNo;

            // Create JSONObejct for info.json
            currentInfoJSON.put("docNo", newDocNo);
            currentInfoJSON.put("title", title);
            currentInfoJSON.put("date_started", dateStarted);
            currentInfoJSON.put("date_updated", dateUpdated);
            currentInfoJSON.put("tags", tags);
            currentInfoJSON.put("link", link);
            currentInfoJSON.put("images", img);
            currentInfoJSON.put("short_desc", shortDesc);
            currentInfoJSON.put("long_desc", longDesc);

            // Create JSONObject for content.json
            newListingToAddToContent.put("docNo", newDocNo);
            newListingToAddToContent.put("title", title);
            newListingToAddToContent.put("date_started", dateStarted);
            newListingToAddToContent.put("date_updated", dateUpdated);
            newListingToAddToContent.put("tags", tags);
            newListingToAddToContent.put("short_desc", shortDesc);

            // Add new JSONObject to existing content JSONObject
            JSONArray temp = (JSONArray) contentJSON.get("storage");
            temp.add(newListingToAddToContent);

            System.out.println(contentJSON.toString());
            saveDataContentJSONToFile();
            saveDataCurrentInfoJSONToFile();


        } catch(Exception e) {

        }


        
        System.out.println(currentInfoJSON.toString());
    }
}
