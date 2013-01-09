/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.controller;

import java.io.IOException;
import pos.view.IViewListener;

/**
 *
 * @author shaunkutch
 */
public interface IContext {

    public void openStageContent(String fxml, Class controller) throws Exception;
    public void replaceSceneContent(String fxml, Class controller) throws Exception;
}
