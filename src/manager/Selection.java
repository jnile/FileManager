package manager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class Selection {
    private GridPane selectionPanel;
    private ModifyManager modifyManager;
    private int selectedDocNo;

    //Contructor
    public Selection(ModifyManager inpmodifyManager) {
        modifyManager = inpmodifyManager;

        selectionPanel = new GridPane();

        JSONArray options = DatabaseHandler.getContentJSONStorage();
        

        ListView<String> lv_options = new ListView<>();
        selectionPanel.add(lv_options, 0, 0);

        populateListView(options, lv_options);


        // Button to confirm selection of listing
        Button nextBtn = new Button("Modify Selected Listing");
        selectionPanel.add(nextBtn, 0, 1);

        nextBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                ObservableList<String> selectedListing = lv_options.getSelectionModel().getSelectedItems();
                String docNo_str = selectedListing.get(0).toString().split("|")[0];
                selectedDocNo = Integer.parseInt(docNo_str);
                modifyManager.setToModifyPanel(selectedDocNo);
            }
        });

    }

    public GridPane getPanel() {
        return selectionPanel;
    }

    private void populateListView(JSONArray options, ListView<String> lv_options) {
        for(int x = 0; x < options.size(); x++) {
            JSONObject currentItem = (JSONObject) options.get(x);
            String toDisplay = (currentItem.get("docNo") + " | " + currentItem.get("title"));

            lv_options.getItems().add(toDisplay);
        }
    }


}
