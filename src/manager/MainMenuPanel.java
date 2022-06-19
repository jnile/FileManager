package manager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainMenuPanel {
    
    private BorderPane mainMenuPanel;
    private Main mainProfile;

    public MainMenuPanel(Main inpMainProfile) {
        mainProfile = inpMainProfile;

        mainMenuPanel = new BorderPane();
        mainMenuPanel.maxHeight(mainProfile.getHeight());
        mainMenuPanel.maxWidth(mainProfile.getWidth());

        Label title = new Label("KOFK Archive Manager");
        title.setMaxWidth(mainProfile.getWidth());
        title.setAlignment(Pos.CENTER);

        VBox buttonsVBox = new VBox();
        buttonsVBox.setAlignment(Pos.CENTER);

        Button createBtn = new Button("Create New");

        // createBtn Button onClick Action
        createBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                createBtnOnClick();
            }
        });

        Button modifyBtn = new Button("Modify");

        // modifyBtn onClick Action
        modifyBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                modifyBtnOnClick();
            }
        });

        buttonsVBox.getChildren().addAll(createBtn, modifyBtn);
        
        
        mainMenuPanel.setTop(title);
        mainMenuPanel.setCenter(buttonsVBox);
    }

    // Private Functions

    // OnClick function for createBtn
    private void createBtnOnClick() {
        mainProfile.changeToInfoPanel();
    }

    // OnClick function for modifyBtn
    private void modifyBtnOnClick() {
        
    }

    //Public Functions

    // Getter Functions
    public BorderPane getPanel(){
        return mainMenuPanel;
    }
}
