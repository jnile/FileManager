package manager;

import java.io.File;
import java.util.ArrayList;

import org.json.simple.JSONArray;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class NewLisitng {

    private GridPane infoPanel;
    private Main mainProfile;

    //Constructor
    public NewLisitng(Main inpMainProfile) {
        mainProfile = inpMainProfile;

        infoPanel = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(450);
        infoPanel.getColumnConstraints().addAll(col1,col2);
        infoPanel.setPadding(new Insets(20));
        infoPanel.setVgap(5);

        Label lbl_main_title = new Label("Create New Listing");
        lbl_main_title.setMinWidth(450);
        infoPanel.add(lbl_main_title, 1, 0);


        Label lbl_title = new Label("Title:");
        infoPanel.add(lbl_title, 0, 1);
        TextField tf_title = new TextField();
        infoPanel.add(tf_title, 1, 1);

        Label lbl_date_started = new Label("Date Started:");
        infoPanel.add(lbl_date_started, 0, 3);
        TextField tf_date_started = new TextField();
        infoPanel.add(tf_date_started, 1, 3);

        Label lbl_date_updated = new Label("Date Updated");
        infoPanel.add(lbl_date_updated, 0, 5);
        TextField tf_date_updated = new TextField();
        infoPanel.add(tf_date_updated, 1, 5);

        Label lbl_tags = new Label("Tags");
        infoPanel.add(lbl_tags, 0, 7);
        TextField tf_tags = new TextField();
        infoPanel.add(tf_tags, 1, 7);

        Label lbl_images = new Label("Images:");
        infoPanel.add(lbl_images,0,9);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMaxWidth(430);
        scrollPane.setMaxHeight(265);
        scrollPane.setMinHeight(265);
        infoPanel.add(scrollPane,1,9 );

        HBox hb_images = new HBox();
        hb_images.setMaxWidth(450);
        hb_images.setSpacing(5);
        scrollPane.setContent(hb_images);

        
        //Add Images Button
        Button addImageBtn = new Button("Add +");
        infoPanel.add(addImageBtn, 1, 10);
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
        
        //infoPanel.add(tf_images, 2, 9);

        Label lbl_link = new Label("Link:");
        infoPanel.add(lbl_link,0,11);
        TextField tf_link = new TextField();
        infoPanel.add(tf_link, 1, 11);

        Label lbl_short_desc = new Label("Short Description:");
        infoPanel.add(lbl_short_desc,0,13);
        TextArea ta_short_desc = new TextArea();
        ta_short_desc.setMinHeight(50);
        ta_short_desc.setMaxHeight(50);
        ta_short_desc.setWrapText(true);
        infoPanel.add(ta_short_desc, 1, 13);

        Label lbl_long_desc = new Label("Long Description:");
        infoPanel.add(lbl_long_desc,0,15);
        TextArea ta_long_desc = new TextArea();
        ta_long_desc.setMinHeight(100);
        ta_long_desc.setMaxHeight(100);
        infoPanel.add(ta_long_desc, 1, 15);

        Button btn_save = new Button("Create New Listing");
        infoPanel.add(btn_save, 1,17);

        // btn_save onClick event
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


                DatabaseHandler.createNewListing(tf_title.getText(), tf_date_started.getText(),tf_date_updated.getText(), tagsjsonarr, tf_link.getText(), imgsjsonarr, ta_short_desc.getText(), ta_long_desc.getText(), imgsUrlArr);
            }
        });
    }

    // Getter Function for panel
    public GridPane getPanel() {
        return infoPanel;
    }
}