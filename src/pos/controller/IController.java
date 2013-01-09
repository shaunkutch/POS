/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.controller;

import pos.view.IViewListener;

/**
 *
 * @author shaunkutch
 */
public interface IController {
    public void setContext(IContext context);
    public IViewListener getViewListener();
}
