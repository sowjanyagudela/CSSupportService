/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssuport.hibernate.support.impl;

import cssuport.hibernate.support.HibernateConnect;
import cssuport.hibernate.support.dao.HibernateQueueResponseTimesDAO;
import cssupport.hbm.pojo.QueueResponseTimes;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author babji
 */
public class HibernateQueueResponseTimesImpl implements HibernateQueueResponseTimesDAO{

    @Override
    public QueueResponseTimes findByID(Integer id) throws Exception {
        Session session = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            Query query = session.createQuery("FROM QueueResponseTimes input WHERE input.id=:id")
                    .setInteger("id", id);
            List list =  query.list();
            if(list != null && !list.isEmpty()) {
                for (Iterator<QueueResponseTimes> IT = list.iterator(); IT.hasNext();) {
                    QueueResponseTimes tmpQueueResponseTimes = IT.next();
                    if(tmpQueueResponseTimes != null) {
                        return tmpQueueResponseTimes;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if(session != null) {
                    session.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public Integer saveOrUpdate(QueueResponseTimes queueResponseTimes) throws Exception {
        Session session = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            session.beginTransaction();
            session.saveOrUpdate(queueResponseTimes);
            session.getTransaction().commit();
            session.flush();
            return queueResponseTimes.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if(session != null) {
                    session.close();
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public QueueResponseTimes findByQueueInputID(Integer queueInputId) throws Exception {
        Session session = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            Query query = session.createQuery("FROM QueueResponseTimes input WHERE input.id=:queueInputId")
                    .setInteger("queueInputId", queueInputId);
            List list =  query.list();
            if(list != null && !list.isEmpty()) {
                for (Iterator<QueueResponseTimes> IT = list.iterator(); IT.hasNext();) {
                    QueueResponseTimes tmpQueueResponseTimes = IT.next();
                    if(tmpQueueResponseTimes != null) {
                        return tmpQueueResponseTimes;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if(session != null) {
                    session.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
    
}
