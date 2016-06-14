/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssupport.queue;

import cssuport.dto.CSQueueJOB;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author babji
 */
public class CSSupportWorkerThread implements Runnable {

    private CSQueueJOB newJob;
    private CSQueueJOBHandler handler;

    public CSSupportWorkerThread(CSQueueJOB newJob) {
        this.newJob = newJob;
        handler = new CSQueueJOBHandler();
    }

    @Override
    public void run() {
        Integer noOfTries = 1;
        while (true) {
            try {
                
                System.out.println("Debug @ stmt ::: JOB ID ... "+newJob.getQueueInput().getId());
                handler.processCSQueueJOB(newJob);
                break;
            } catch (Exception ex) {
                if(CSQueueJOB.MAXTRIES <= noOfTries) {
                    // no of retries reached, break the loop
                    System.out.println("Debug @ stmt :: JOB -- "+newJob.getQueueInput().getId()+"   /  noOfTries -- "+noOfTries);
                    break;
                }
            }
            noOfTries++;
        }

    }
}
