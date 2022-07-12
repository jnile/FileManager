package manager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    private final static int width = 600;
    private final static int height = 800;

    private MainMenuPanel mainMenuPanel;
    private NewLisitng newListingPanel;
    private ModifyManager modifyManagerPanel;
    private Stage stage;


    /**
     * The main entry point for JavaFX programs.
     */
    @Override
    public void start(Stage inpstage)
    {
        DatabaseHandler.setContent();

        stage = inpstage;
        // Instantiate Important Panels
        mainMenuPanel = new MainMenuPanel(this);
        newListingPanel = new NewLisitng(this);
        modifyManagerPanel = new ModifyManager(this);

        Scene scene = new Scene(mainMenuPanel.getPanel(),width,height);

        

        stage.setTitle("ImageViewer");
        stage.setScene(scene);
        stage.show();
    }

    public void changeToNewListingPanel() {
        Scene scene = new Scene(newListingPanel.getPanel(),width,height);
        stage.setScene(scene);
    }

    public void changeToModifyManagerPanel() {
        Scene scene = new Scene(modifyManagerPanel.getPanel(),width,height);
        stage.setScene(scene);
    }

    // Getter Functions

    // Return height constant
    public int getHeight() {
        return height;
    }

    // return width constant
    public int getWidth() {
        return width;
    }
}
