/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.model;

import com.google.gson.Gson;
import com.shaunkutch.events.EventDispatcher;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pos.Constants;
import pos.events.PosEvent;
import pos.vo.ItemVO;
import pos.vo.TenderVO;
import us.monoid.web.Resty;

/**
 *
 * @author shaunkutch
 */
public class PosModel extends EventDispatcher {

    public static PosModel instnace = null;
    private Gson mGson;

    public PosModel() {
        mGson = new Gson();
    }

    public static PosModel getInstance() {
        if (instnace == null) {
            instnace = new PosModel();
        }
        return instnace;
    }
    
    private ObservableList<TenderVO> mTenders = FXCollections.observableArrayList();
    
    public ObservableList getTenders()
    {
        return mTenders;
    }
    
    public void addTender(TenderVO tender) 
    {
        mTenders.add(tender); 
        update();
    }
    
    public void removeTender(TenderVO tender) 
    {
        mTenders.remove(tender); 
        update();
    }
    
    public double getTenderTotal()
    {
        double total = 0;
        for (TenderVO tender : mTenders) {
            total += tender.amount;
        }
        return total;
    }
    
    private ObservableList<ItemVO> mItems = FXCollections.observableArrayList();

    public ObservableList getItems() {
        return mItems;
    }

    public void setItems(ObservableList<ItemVO> items) {
        mItems = items;
        update();
        this.dispatchEvent(new PosEvent(PosEvent.ITEMS_CHANGE));
    }

    public void addItem(ItemVO item) {
        mItems.add(item);
        update();
        this.dispatchEvent(new PosEvent(PosEvent.ITEMS_CHANGE));
    }
    
    public double getItemsTotal()
    {
        double total = 0;
        for (ItemVO item : mItems) {
            total += item.price;
        }
        return total;
    }

    public void removeItem(ItemVO item) {
        mItems.remove(item);
        update();
        this.dispatchEvent(new PosEvent(PosEvent.ITEMS_CHANGE));
    }

    public ItemVO getItemById(String model) throws IOException {
        String request = Constants.API_URL + model;
        Resty r = new Resty();
        String json = r.text(request).toString();
        ItemVO item = mGson.fromJson(json, ItemVO.class);
        return item;
    }
    
    private double mChange = 0;
    
    public double getChange()
    {
        return mChange;
    }
    
    public void voidTransaction()
    {
        mTenders.clear();
        mItems.clear();
        update();
    }

    private void update() {
     
        double itemTotal = getItemsTotal();        
        double tenderTotal = getTenderTotal();
        
        mChange = tenderTotal - itemTotal;
        
        this.dispatchEvent(new PosEvent(PosEvent.TOTAL_CHANGE));
    }
}
