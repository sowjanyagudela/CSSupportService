/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssupport;

import cssupport.queue.CSSupportTimer;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author babji
 * 
 * 
 * 
 */
public class CSSupport {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            new CSSupportTimer();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(CSSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
