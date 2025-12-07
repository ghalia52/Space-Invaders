package patterns.state;

import javax.swing.JPanel;
import utils.Logger;

public interface State {
    
    
    JPanel getMainPanel();
    
 
    default void onEnter() {
        Logger.state(this.getClass().getSimpleName() + " entered");
    }
    
    
    default void onExit() {
        Logger.state(this.getClass().getSimpleName() + " exited");
    }
    

    default void update() {
        
    }
    
    default String getStateName() {
        return this.getClass().getSimpleName();
    }
}