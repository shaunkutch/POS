/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import pos.Pos;
import pos.view.Mediator;
import pos.view.IViewListener;

/**
 *
 * @author shaunkutch
 */
public class MainController implements IContext{
    private Pos mPosApp;
    private HashMap<String, Stage> mViewMap = new HashMap<String, Stage>();
    
    public MainController(Pos app)
    {
        mPosApp = app;
    }
    
    public void start() throws Exception
    {
        replaceSceneContent("view/pos.fxml", PosController.class);
    }
    
    public void replaceSceneContent(String fxml, Class controller) throws Exception {
        IController c = createViewController(controller);
        URL location = mPosApp.getClass().getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Parent root = (Parent)fxmlLoader.load();
        Mediator mediator = (Mediator)fxmlLoader.getController();
        mediator.setViewListener(c.getViewListener());
        
        Scene scene = new Scene(root);
        mPosApp.stage.setScene(scene);
        mPosApp.stage.show();
    }
    
    public void openStageContent(String fxml, Class controller) throws Exception
    {
        if(mViewMap.containsKey(fxml))
        {
            Stage s = mViewMap.get(fxml);
            s.toFront();
            return;
        }
        
        IController c = createViewController(controller);
        
        final Stage stage = new Stage();
        mViewMap.put(fxml, stage);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                Stage s = (Stage)we.getTarget();
                while (mViewMap.values().remove(s));
            }
        });
        
        URL location = mPosApp.getClass().getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Parent root = (Parent)fxmlLoader.load();
        Mediator mediator = (Mediator)fxmlLoader.getController();
        mediator.setViewListener(c.getViewListener());
        
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.show();
    }
    
    public IController createViewController(Class icontroller) throws InstantiationException, IllegalAccessException
    {
        IController c = (IController)icontroller.newInstance();
        c.setContext(this);
        return c;
    }
}
