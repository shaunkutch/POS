/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pos.Pos;
import pos.model.PosModel;
import pos.view.ItemInputMediator;
import pos.view.Mediator;
import pos.view.ViewListener;
import pos.vo.ItemVO;
import pos.vo.TenderVO;

/**
 *
 * @author shaunkutch
 */
public class PosController {

    private PosModel posModel;
    Boolean mStartNumber = true;
    private final Pos posApp;

    public PosController(Application app) {
        posModel = PosModel.getInstance();
        posApp = (Pos)app;
    }
    
    public void start() throws Exception
    {
        replaceSceneContent("view/pos.fxml", posViewListener);
        showStage();
    }
    
    public void replaceSceneContent(String fxml, ViewListener listener) throws Exception {
        URL location = posApp.getClass().getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Parent root = (Parent)fxmlLoader.load();
        Mediator mediator = (Mediator)fxmlLoader.getController();
        mediator.setViewListener(listener);
        
        Scene scene = new Scene(root); 
        posApp.stage.setScene(scene);
    }
    
    public void openStageContent(String fxml, ViewListener listener) throws IOException
    {
        Stage stage = new Stage();
        
        URL location = posApp.getClass().getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Parent root = (Parent)fxmlLoader.load();
        Mediator mediator = (Mediator)fxmlLoader.getController();
        mediator.setViewListener(listener);
        
        Scene scene = new Scene(root); 
        stage.setScene(scene);
    }
    
    public void showStage()
    {
        posApp.stage.show();
    }
    
    public static interface PosViewListener extends ViewListener {

        public void removeItem(ItemVO item);

        public void addItem(String model);

        public void addTender(String amount, String tenderType);

        public void removeTender(TenderVO tender);

        public void voidTransaction();

        public void done();

        public void showItemInput() throws Exception;
    }
    
    public PosViewListener posViewListener = new PosController.PosViewListener() {
        @Override
        public void removeItem(ItemVO item) {
            posModel.removeItem(item);
        }

        @Override
        public void addItem(String model) {
            try {
                ItemVO item = posModel.getItemById(model);
                posModel.addItem(item);
            } catch (IOException ex) {
                Logger.getLogger(PosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void done() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void addTender(String amount, String tenderType) {
            TenderVO tender = new TenderVO();
            tender.amount = Double.valueOf(amount);
            tender.tenderType = tenderType;
            posModel.addTender(tender);
        }

        @Override
        public void removeTender(TenderVO tender) {
            posModel.removeTender(tender);
        }

        @Override
        public void voidTransaction() {
            posModel.voidTransaction();
        }

        @Override
        public void showItemInput() throws Exception {
           replaceSceneContent("view/itemInput.fxml", itemInputViewListener);
        }
    };
    
    public static interface ItemInputViewListener extends ViewListener  {
        
    }
    
    public ItemInputViewListener itemInputViewListener = new PosController.ItemInputViewListener() {
        
    };
}
