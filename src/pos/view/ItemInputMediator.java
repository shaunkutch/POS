/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import pos.controller.PosController;
import pos.controller.PosController.ItemInputViewListener;

/**
 *
 * @author shaunkutch
 */
public class ItemInputMediator implements Mediator, Initializable {
    
    private ItemInputViewListener viewListener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @Override
    public void setViewListener(ViewListener viewListener) {
        this.viewListener = (ItemInputViewListener)viewListener;
    }
    
}
