package manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class Modify {
    private GridPane modifyPanel;
    private ModifyManager modifyManager;
    private int currentDocNo;
    private JSONObject infoDoc;
    private JSONArray contentJson;

    //Contructor
    public Modify(ModifyManager inpmodifyManager, int docNo) {
        modifyManager = inpmodifyManager;

        // Fetch data of respective document
        setToDocNoData(docNo);

        modifyPanel = new GridPane();
        

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(450);
        modifyPanel.getColumnConstraints().addAll(col1,col2);
        modifyPanel.setPadding(new Insets(20));
        modifyPanel.setVgap(5);

        Label lbl_main_title = new Label("Modify Listing");
        lbl_main_title.setMinWidth(450);
        modifyPanel.add(lbl_main_title, 1, 0);


        Label lbl_title = new Label("Title:");
        modifyPanel.add(lbl_title, 0, 1);
        TextField tf_title = new TextField();
        tf_title.setText(infoDoc.get("title").toString());
        modifyPanel.add(tf_title, 1, 1);

        Label lbl_date_started = new Label("Date Started:");
        modifyPanel.add(lbl_date_started, 0, 3);
        TextField tf_date_started = new TextField();
        tf_date_started.setText(infoDoc.get("date_started").toString());
        modifyPanel.add(tf_date_started, 1, 3);

        Label lbl_date_updated = new Label("Date Updated");
        modifyPanel.add(lbl_date_updated, 0, 5);
        TextField tf_date_updated = new TextField();
        tf_date_updated.setText(infoDoc.get("date_updated").toString());
        modifyPanel.add(tf_date_updated, 1, 5);

        Label lbl_tags = new Label("Tags");
        modifyPanel.add(lbl_tags, 0, 7);
        TextField tf_tags = new TextField();
        tf_tags.setText(jsonArrayToString((JSONArray) infoDoc.get("tags")));
        modifyPanel.add(tf_tags, 1, 7);

        Label lbl_images = new Label("Images:");
        modifyPanel.add(lbl_images,0,9);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMaxWidth(430);
        scrollPane.setMaxHeight(265);
        scrollPane.setMinHeight(265);
        modifyPanel.add(scrollPane,1,9 );

        HBox hb_images = new HBox();
        hb_images.setMaxWidth(450);
        hb_images.setSpacing(5);
        scrollPane.setContent(hb_images);
        loadImages(hb_images);
        
        //Add Images Button
        Button addImageBtn = new Button("Add +");
        modifyPanel.add(addImageBtn, 1, 10);
        addImageBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();

                FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

                File file = fileChooser.showOpenDialog(null);

                if(file != null) {
                    Image image = new Image(file.toURI().toString());
                    ImageView iview = new ImageView(image);
                    iview.setFitHeight(250);
                    iview.setFitWidth(250);
                    hb_images.getChildren().add(0, iview);
                }
            }
        });
        
        //modifyPanel.add(tf_images, 2, 9);

        Label lbl_link = new Label("Link:");
        modifyPanel.add(lbl_link,0,11);
        TextField tf_link = new TextField();
        tf_link.setText((String) infoDoc.get("link"));
        modifyPanel.add(tf_link, 1, 11);

        Label lbl_short_desc = new Label("Short Description:");
        modifyPanel.add(lbl_short_desc,0,13);
        TextArea ta_short_desc = new TextArea();
        ta_short_desc.setMinHeight(50);
        ta_short_desc.setMaxHeight(50);
        ta_short_desc.setWrapText(true);
        ta_short_desc.setText((String) infoDoc.get("short_desc"));
        modifyPanel.add(ta_short_desc, 1, 13);

        Label lbl_long_desc = new Label("Long Description:");
        modifyPanel.add(lbl_long_desc,0,15);
        TextArea ta_long_desc = new TextArea();
        ta_long_desc.setMinHeight(100);
        ta_long_desc.setMaxHeight(100);
        ta_long_desc.setText((String) infoDoc.get("long_desc"));
        modifyPanel.add(ta_long_desc, 1, 15);

        Button btn_save = new Button("Update Listing");
        modifyPanel.add(btn_save, 1,17);

        btn_save.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                // Call DatabaseHandler createNewListing Function
                JSONArray tagsjsonarr = new JSONArray();
                String[] temp = tf_tags.getText().split(",");
                for(int x  = 0; x < temp.length; x++) {
                    tagsjsonarr.add(temp[x]);
                }

                JSONArray imgsjsonarr = new JSONArray();
                ArrayList<String> imgsUrlArr = new ArrayList<String>();
                for(Node child: hb_images.getChildren()) {
                    //Get image view
                    ImageView imgView = (ImageView) child;
                    // Split the url split into its different components
                    String url = imgView.getImage().getUrl();
                    String[] urlSplit = url.split("/");
                    String title = urlSplit[urlSplit.length - 1];
                    imgsjsonarr.add(title);
                    imgsUrlArr.add(url);
                }

                DatabaseHandler.updateListing(tf_title.getText(), tf_date_started.getText(),tf_date_updated.getText(), tagsjsonarr, tf_link.getText(), imgsjsonarr, ta_short_desc.getText(), ta_long_desc.getText(), imgsUrlArr);
            }   
        });

    }

    public GridPane getPanel() {
        return modifyPanel;
    }

    // Set docNo to the entered docNo
    public void setToDocNoData(int docNo) {
        currentDocNo = docNo;
        contentJson = DatabaseHandler.getContentJSONStorage();
        
        infoDoc = DatabaseHandler.getInfoOfDocNo(currentDocNo);
    }

    // Get String of array
    public String jsonArrayToString(JSONArray jsonArr) {
        String result = "";
        JSONArray tagsArr = (JSONArray) infoDoc.get("tags");
        for(Iterator i = tagsArr.iterator(); i.hasNext();) {
            String tag = i.toString();
            result += i.next();

            if(i.hasNext()) {
                result += ",";
            }
        }
        return result;
    }

    public void loadImages(HBox hb_images) {
        String location = DatabaseHandler.getImageStorageURL();
        JSONArray imgArr = (JSONArray) infoDoc.get("images");

        for(Iterator imgName = imgArr.iterator(); imgName.hasNext();) {
            String imgStr = location + ((String) imgName.next());

            try {
                File file = new File(imgStr);

                if(file != null) {
                    Image img = new Image(file.toURI().toString());
                    ImageView iview = new ImageView(img);
                    iview.setFitHeight(250);
                    iview.setFitWidth(250);
                    hb_images.getChildren().add(0, iview);
                }
            } catch(Exception e) {
                System.out.println(e);
            }
            
        }

    }
}
