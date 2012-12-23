package pos.events;

import com.shaunkutch.events.SimpleEvent;

/**
 *
 * @author shaunkutch
 */
public class PosEvent extends SimpleEvent {

    public static final String ITEMS_CHANGE= "itemChange";
    public static final String TOTAL_CHANGE = "totalChange";
    
    public PosEvent(String type) {
        super(type);
    }
}
