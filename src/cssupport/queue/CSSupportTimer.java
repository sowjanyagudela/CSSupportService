/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssupport.queue;

import cssuport.dto.CSQueueJOB;
import cssuport.hibernate.support.dao.HibernateQueueInputDAO;
import cssupport.daofactory.DAOFactory;
import cssupport.hbm.pojo.QueueInput;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author babji
 */
public class CSSupportTimer {

    public static int activeThreadCount = 0;
    public static int maxThreadsCount = 3;
    public static int noOfThreads = 1;
    private ExecutorService executor = null;
    private HibernateQueueInputDAO queueInputDAO;

    public CSSupportTimer() {
        try {
            /**
             * 1. In-progress TO Not Picked 2. Starting Executors Pool 3.
             * Picking Thread by Thread -> assigning JOB
             */
            setQueuesInputToInitialState();

            initializeExecutorsService();
            System.out.println("Debug @ stmt  ---------- Process Starts ----------------");
            processQueue();

            System.out.println("Debug @ stmt  ---------- Process ENDs ----------------");
            stopProcessing();

        } catch (Exception ex) {
            Logger.getLogger(CSSupportTimer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setQueuesInputToInitialState() throws Exception {
        /**
         * 1. In-progress TO Not Picked
         */
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            queueInputDAO = daoFactory.getQueueInputDAO();
            queueInputDAO.setInitialState();
        } catch (Exception e) {
            throw e;
        }

    }

    public void initializeExecutorsService() {
        executor = new ScheduledThreadPoolExecutor(CSSupportTimer.maxThreadsCount);
    }

    public void stopProcessing() {
        executor.shutdown();
    }

    public void processQueue() {


        while (true) {
            System.out.println("Debug @ stmt :: Active Thread Count -- " + CSSupportTimer.activeThreadCount + " / Max Threads allowed " + CSSupportTimer.maxThreadsCount);
            if (CSSupportTimer.activeThreadCount <= CSSupportTimer.maxThreadsCount) {
                try {
                    QueueInput queueInput = queueInputDAO.fetchQueueInputRecord();
                    if (queueInput == null) {
                        break;
                    }
                    CSQueueJOB newJob = new CSQueueJOB(queueInput);
                    CSSupportWorkerThread thread = new CSSupportWorkerThread(newJob);
                    executor.execute(thread);

                } catch (Exception ex) {
                    Logger.getLogger(CSSupportTimer.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
            } else {
//                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(CSSupportTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
