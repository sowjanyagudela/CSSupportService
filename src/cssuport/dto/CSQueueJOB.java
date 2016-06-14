/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssuport.dto;

import cssuport.hibernate.support.dao.HibernateQueueDataDAO;
import cssuport.hibernate.support.dao.HibernateQueueResponseTimesDAO;
import cssupport.daofactory.DAOFactory;
import cssupport.hbm.pojo.QueueData;
import cssupport.hbm.pojo.QueueInput;
import cssupport.hbm.pojo.QueueResponseTimes;

/**
 *
 * @author babji
 */
public class CSQueueJOB {
    public static Byte YETTOSTART = 0;
    public static Byte INPROGRESS = 1;
    public static Byte COMPLETED  = 2;
    public static Byte FAILED = 3;
    public static Byte MAXTRIES = 3;
    
    private QueueInput queueInput;
    private QueueData queueData;
    private QueueResponseTimes queueResponseTimes;
    
    public CSQueueJOB(QueueInput queueInput) throws Exception {
        if(queueInput == null) {
            throw new Exception("Queue Input ID missing....");
        }
        this.queueInput = queueInput;
        Integer queueInputID = queueInput.getId();
        DAOFactory daoFactory = DAOFactory.getInstance();
//        HibernateQueueDataDAO queueDataDAO =  daoFactory.getQueueDataDAO();
//        QueueData queueData = queueDataDAO.findByQueueInputID(queueInputID);
        if(queueData == null) {
            queueData = new QueueData();
            queueData.setQueueInputId(queueInputID);
        }
//        queueData.setSourceRq(null);
//        queueData.setSourceRs(null);
//        queueData.setTargetRq(null);
//        queueData.setTargetRs(null);
        
        setQueueData(queueData);
       
//        HibernateQueueResponseTimesDAO queueResponseTimesDAO = daoFactory.getQueueResponseTimesDAO();
//        QueueResponseTimes queueResponseTimes = queueResponseTimesDAO.findByQueueInputID(queueInputID);
        
        if(queueResponseTimes == null) {
            queueResponseTimes = new QueueResponseTimes();
            queueResponseTimes.setQueueInputId(queueInputID);
        }
        
        setQueueResponseTimes(queueResponseTimes);
    }

    public QueueInput getQueueInput() {
        return queueInput;
    }

    public void setQueueInput(QueueInput queueInput) {
        this.queueInput = queueInput;
    }

    public QueueData getQueueData() {
        return queueData;
    }

    public void setQueueData(QueueData queueData) {
        this.queueData = queueData;
    }

    public QueueResponseTimes getQueueResponseTimes() {
        return queueResponseTimes;
    }

    public void setQueueResponseTimes(QueueResponseTimes queueResponseTimes) {
        this.queueResponseTimes = queueResponseTimes;
    }

    @Override
    public String toString() {
        return "CSQueueJOB{" + "queueInput=" + queueInput + ", queueData=" + queueData + ", queueResponseTimes=" + queueResponseTimes + '}';
    }
    
    
}
