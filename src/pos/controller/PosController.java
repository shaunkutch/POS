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
import pos.view.IViewListener;
import pos.vo.ItemVO;
import pos.vo.TenderVO;

/**
 *
 * @author shaunkutch
 */
public class PosController implements IController
{
    private IContext mContext;
    private PosModel posModel;
    Boolean mStartNumber = true;

    public PosController() 
    {
        posModel = PosModel.getInstance();
    }

    @Override
    public IViewListener getViewListener() {
        return posViewListener;
    }

    @Override
    public void setContext(IContext context) {
       mContext = context;
    }
    
    public static interface PosViewListener extends IViewListener {

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
           mContext.openStageContent("view/itemInput.fxml", ItemInputController.class);
        }
    };
}
