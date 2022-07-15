package manager;

import java.lang.module.ModuleFinder;

import javafx.scene.layout.GridPane;

public class ModifyManager {
    private Main mainProfile;
    private GridPane modifyManager;
    private Selection selectionPanel;
    private Modify modifyPanel;

    public ModifyManager(Main impMainProfile) {
        mainProfile = impMainProfile;

        modifyManager = new GridPane();
        
        // Handles selection of the choice of listing to modify
        selectionPanel = new Selection(this);
        modifyManager.add(selectionPanel.getPanel(), 0, 0);
    }

    public GridPane getPanel() {
        return modifyManager;
    }

    public void setToModifyPanel(int docNo) {
        modifyManager.getChildren().clear();
        modifyPanel = new Modify(this, docNo);
        modifyManager.add(modifyPanel.getPanel(), 0, 0);
        mainProfile.setStageToDefaultSize();
    }
}
