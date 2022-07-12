package manager;

import javafx.scene.layout.GridPane;

public class ModifyManager {
    private Main mainProfile;
    private GridPane modifyPanel;
    private Selection selectionPanel;

    public ModifyManager(Main impMainProfile) {
        mainProfile = impMainProfile;

        modifyPanel = new GridPane();
        
        // Handles selection of the choice of listing to modify
        selectionPanel = new Selection(this);
        modifyPanel.add(selectionPanel.getPanel(), 0, 0);

    }

    public GridPane getPanel() {
        return modifyPanel;
    }
}
