/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssupport.daofactory;

import cssuport.hibernate.support.dao.HibernateQueueDataDAO;
import cssuport.hibernate.support.dao.HibernateQueueInputDAO;
import cssuport.hibernate.support.dao.HibernateQueueResponseTimesDAO;
import cssuport.hibernate.support.dao.HibernateUrlsDAO;
import cssuport.hibernate.support.impl.HibernateQueueDataImpl;
import cssuport.hibernate.support.impl.HibernateQueueInputImpl;
import cssuport.hibernate.support.impl.HibernateQueueResponseTimesImpl;
import cssuport.hibernate.support.impl.HibernateUrlsImpl;

/**
 *
 * @author babji
 */
public class DAOFactory extends Object{
    /**
     *  Initialization of DAO's
     */
    
    private HibernateQueueDataDAO queueDataDAO;
    private HibernateQueueInputDAO queueInputDAO;
    private HibernateQueueResponseTimesDAO queueResponseTimesDAO;
    private HibernateUrlsDAO urlsDAO;
    
    private DAOFactory() {
    }
    
    @Override
    public Object clone() {
        return DAOFactoryHolder.INSTANCE;
    }
    
    public static DAOFactory getInstance() {
        return DAOFactoryHolder.INSTANCE;
    }
    
    private static class DAOFactoryHolder {
        private static final DAOFactory INSTANCE = new DAOFactory();
    }

    public HibernateQueueDataDAO getQueueDataDAO() {
        if(queueDataDAO == null) {
            queueDataDAO = new HibernateQueueDataImpl();
        }
        return queueDataDAO;
    }

    public HibernateQueueInputDAO getQueueInputDAO() {
        if(queueInputDAO == null) {
            queueInputDAO = new HibernateQueueInputImpl();
        }
        return queueInputDAO;
    }

    public HibernateQueueResponseTimesDAO getQueueResponseTimesDAO() {
        if(queueResponseTimesDAO == null) {
            queueResponseTimesDAO = new HibernateQueueResponseTimesImpl();
        }
        return queueResponseTimesDAO;
    }

    public HibernateUrlsDAO getUrlsDAO() {
        if(urlsDAO == null) {
            urlsDAO = new HibernateUrlsImpl();
        }
        return urlsDAO;
    }
    
    
}
