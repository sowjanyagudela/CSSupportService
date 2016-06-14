/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssuport.hibernate.support.impl;

import cssuport.hibernate.support.HibernateConnect;
import cssuport.hibernate.support.dao.HibernateQueueDataDAO;
import cssupport.hbm.pojo.QueueData;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author babji
 */
public class HibernateQueueDataImpl implements HibernateQueueDataDAO{

    @Override
    public QueueData findByID(Integer id) throws Exception {
        Session session = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            Query query = session.createQuery("FROM QueueData input WHERE input.id=:id")
                    .setInteger("id", id);
            List list =  query.list();
            if(list != null && !list.isEmpty()) {
                for (Iterator<QueueData> IT = list.iterator(); IT.hasNext();) {
                    QueueData queueData = IT.next();
                    if(queueData != null) {
                        return queueData;
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
    public Integer saveOrUpdate(QueueData queueData) throws Exception {
        Session session = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            session.beginTransaction();
            session.saveOrUpdate(queueData);
            session.getTransaction().commit();
            session.flush();
            return queueData.getId();
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
    public QueueData findByQueueInputID(Integer queueInputId) throws Exception {
        Session session = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            Query query = session.createQuery("FROM QueueData input WHERE input.queueInputId=:queueInputId")
                    .setInteger("queueInputId", queueInputId);
            List list =  query.list();
            if(list != null && !list.isEmpty()) {
                for (Iterator<QueueData> IT = list.iterator(); IT.hasNext();) {
                    QueueData queueData = IT.next();
                    if(queueData != null) {
                        return queueData;
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
