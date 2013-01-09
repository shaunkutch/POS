package pos.view;

/**
 * Sample Skeleton for "pos.fxml" Controller Class You can copy and paste this
 * code into your favorite IDE
 *
 */
import com.shaunkutch.events.Event;
import com.shaunkutch.events.EventListener;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import pos.controller.PosController.PosViewListener;
import pos.events.PosEvent;
import pos.model.PosModel;
import pos.vo.ItemVO;
import pos.vo.TenderVO;

public class PosMediator implements Mediator, Initializable {

    @FXML private Button zeroBtn;
    @FXML private Button oneBtn;
    @FXML private Button twoBtn;
    @FXML private Button threeBtn;
    @FXML private Button fourBtn;
    @FXML private Button fiveBtn;
    @FXML private Button sixBtn;
    @FXML private Button sevenBtn;
    @FXML private Button eightBtn;
    @FXML private Button nineBtn;
    @FXML private Button backBtn;
    @FXML private Button decimalBtn;
    
    @FXML private Button cashBtn;
    @FXML private Button creditBtn;
    @FXML private Button otherBtn;
    @FXML private TextField tenderInput;
    @FXML private ListView tenders;
    @FXML private Button doneBtn;
    @FXML private Button voidBtn;
    @FXML private Button findBtn;
    @FXML private Button addBtn;
    @FXML private Button deleteBtn;
    @FXML private Label itemsTotal;
    @FXML private Label tender;
    @FXML private Label change;
    @FXML private TextField itemInput;
    @FXML private ListView itemList;
    
    @FXML private Button settingsBtn;

    private PosModel posModel;
    
    /**
     * The listener reference for sending events
     */
    private PosViewListener viewListener;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        posModel = PosModel.getInstance();
        itemList.setCellFactory(new Callback<ListView<ItemVO>, ListCell<ItemVO>>() {
            @Override public ListCell<ItemVO> call(ListView<ItemVO> p) {
                return new ListCell<ItemVO>() {
                    
                    @Override 
                    protected void updateItem(ItemVO item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item != null) {
                            String itemName = item.name;
                            if(itemName.length() > 40)
                            {
                              itemName = item.name.substring(0, 40) + "...";  
                            }
                            
                            this.setText("["+item.model+"] "+"["+itemName+ "] @" + item.price);
                        }
                   }
              };
          }
       });
       
        tenders.setCellFactory(new Callback<ListView<TenderVO>, ListCell<TenderVO>>() {
            @Override public ListCell<TenderVO> call(ListView<TenderVO> p) {
                return new ListCell<TenderVO>() {
                    
                    @Override 
                    protected void updateItem(TenderVO tender, boolean empty) {
                        super.updateItem(tender, empty);
                        
                        if (tender != null) {                        
                            this.setText("["+tender.tenderType+"] @" + tender.amount);
                        }
                   }
              };
          }
       });

        posModel.addListener(PosEvent.ITEMS_CHANGE, itemChangeListener);
        posModel.addListener(PosEvent.TOTAL_CHANGE, totalChangeListener);
        
        oneBtn.getProperties().put("inputValue", "1");
        oneBtn.setOnAction(numberInputHandler);
        
        twoBtn.getProperties().put("inputValue", "2");
        twoBtn.setOnAction(numberInputHandler);
        
        threeBtn.getProperties().put("inputValue", "3");
        threeBtn.setOnAction(numberInputHandler);
        
        fourBtn.getProperties().put("inputValue", "4");
        fourBtn.setOnAction(numberInputHandler);
        
        fiveBtn.getProperties().put("inputValue", "5");
        fiveBtn.setOnAction(numberInputHandler);
        
        sixBtn.getProperties().put("inputValue", "6");
        sixBtn.setOnAction(numberInputHandler);
        
        sevenBtn.getProperties().put("inputValue", "7");
        sevenBtn.setOnAction(numberInputHandler);
        
        eightBtn.getProperties().put("inputValue", "8");
        eightBtn.setOnAction(numberInputHandler);
        
        nineBtn.getProperties().put("inputValue", "9");
        nineBtn.setOnAction(numberInputHandler);
        
        zeroBtn.getProperties().put("inputValue", "0");
        zeroBtn.setOnAction(numberInputHandler);
        
        decimalBtn.getProperties().put("inputValue", ".");
        decimalBtn.setOnAction(numberInputHandler);
        
        backBtn.setOnAction(backBtnHandler);
        cashBtn.setOnAction(tenderHandler);
        cashBtn.getProperties().put("tenderType", TenderVO.CASH);
        creditBtn.setOnAction(tenderHandler);
        creditBtn.getProperties().put("tenderType", TenderVO.CREDIT);
        otherBtn.setOnAction(tenderHandler);
        otherBtn.getProperties().put("tenderType", TenderVO.OTHER);

        this.voidBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewListener.voidTransaction();
            }
        });
        
        this.doneBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewListener.done();
            }
        });
        
        this.addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                 try {
                    viewListener.showItemInput();
                } catch (Exception ex) {
                    Logger.getLogger(PosMediator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        this.findBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String model = itemInput.getText();
                if (model != null) {
                    viewListener.addItem(model);
                }
            }
        });

        this.deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ItemVO item = (ItemVO)itemList.getSelectionModel().getSelectedItem();
                if(item != null)
                {
                    viewListener.removeItem(item);
                }
                
                TenderVO tender = (TenderVO)tenders.getSelectionModel().getSelectedItem();
                if(tender != null)
                {
                     viewListener.removeTender(tender);
                }
            }
        });
        
        this.settingsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               
            }
        });
    }

    @Override
    public void setViewListener(IViewListener viewListener) {
        this.viewListener = (PosViewListener)viewListener;
    }
    
    private EventListener itemChangeListener = new EventListener() {
        @Override
        public void onEvent(Event event) {
            itemList.setItems(posModel.getItems());
        }
    };
    private EventListener totalChangeListener = new EventListener() {
        @Override
        public void onEvent(Event event) {
            tenders.setItems(posModel.getTenders());
            change.setText(String.valueOf(posModel.getChange()));
            itemsTotal.setText(String.valueOf(posModel.getItemsTotal()));
        }
    };
    
    private EventHandler backBtnHandler = new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event) {
            String currentTender = tenderInput.getText();
            String s = currentTender.substring(0, currentTender.length() - 1);
            tenderInput.setText(s);
        }
    };
    
    private EventHandler numberInputHandler = new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event) {
            Button target = (Button)event.getSource();
            String tender = (String)target.getProperties().get("inputValue");
            String currentTender = tenderInput.getText();
            tenderInput.setText(currentTender+tender);
        }
    };
    
    private EventHandler tenderHandler = new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event) {
            Button target = (Button)event.getSource();
            String tenderType = (String)target.getProperties().get("tenderType");
            String amount = tenderInput.getText();
            
            viewListener.addTender(amount, tenderType);
            tenderInput.clear();
        }
    };
}
