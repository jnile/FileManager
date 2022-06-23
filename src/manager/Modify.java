package manager;

import org.json.simple.JSONArray;

import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class Modify {
    private GridPane infoPanel;
    private Main mainProfile;

    //Contructor
    public Modify(Main inpMainProfile) {
        mainProfile = inpMainProfile;

        infoPanel = new GridPane();

        JSONArray options = DatabaseHandler.getContentJSONStorage();
        

        ListView lv_options = new ListView<>();
        infoPanel.add(lv_options, 0, 0);
        for(int x = 0; x < options.size(); x++) {
            lv_options.getItems().add(options.get(x));
        }



    }

    public GridPane getPanel() {
        return infoPanel;
    }

    private void PopulateListView() {

    }
}
