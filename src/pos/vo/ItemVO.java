/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.vo;

/**
 *
 * @author shaunkutch
 */
public class ItemVO{

    public int product_id;
    public String model;
    public String sku;
    public String name;
    public double price;
    public int quantity;

    public int getProduct_id() {
        return product_id;
    }
    
    public void getProduct_id(int id)
    {
        this.product_id = id;
    }
    
    public String getModel() {
        return model;
    }
    
    public void getModel(String model)
    {
        this.model = model;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public double getPrice()
    {
        return price;
    }
    
    public void setPrice(double price)
    {
        this.price = price;
    }
}
