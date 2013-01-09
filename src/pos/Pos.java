/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pos.controller.MainController;
import pos.controller.PosController;
import pos.view.Mediator;
import pos.view.IViewListener;

/**
 *
 * @author shaunkutch
 */
public class Pos extends Application {
    
    private MainController mainController;
    public Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        mainController = new MainController(this); 
        mainController.start();
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
