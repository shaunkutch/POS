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
public class ItemInputController implements IController{
    private IContext context;

    @Override
    public void setContext(IContext context) {
        this.context = context;
    }

    @Override
    public IViewListener getViewListener() {
        return itemViewListener;
    }
    
    public static interface ItemViewListener extends IViewListener
    {
        
    }
    
    private ItemViewListener itemViewListener = new ItemViewListener()
    {
        
    };
}
