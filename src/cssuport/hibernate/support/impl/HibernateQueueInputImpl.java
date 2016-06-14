/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssuport.hibernate.support.impl;

import cssuport.dto.CSQueueJOB;
import cssuport.hibernate.support.HibernateConnect;
import cssuport.hibernate.support.dao.HibernateQueueInputDAO;
import cssupport.hbm.pojo.QueueInput;
import cssupport.queue.CSSupportTimer;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author babji
 */
public class HibernateQueueInputImpl implements HibernateQueueInputDAO {

    @Override
    public void setInitialState() throws Exception {
        try {
            List queueInputList = findAllByStatus(new Byte("1"));
            if (queueInputList != null && !queueInputList.isEmpty()) {
                for (Iterator<QueueInput> IT = queueInputList.iterator(); IT.hasNext();) {
                    QueueInput queueInput = IT.next();
                    if (queueInput != null) {
                        queueInput.setStatus(new Integer("0"));
                        Integer updatedQueueInputID = saveOrUpdateQueueInput(queueInput);

                        System.out.println("Debug @ stmt :: setInitialState -- updatedQueueInputID :: " + updatedQueueInputID);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
            } catch (Exception e) {
            }
        }
    }

    @Override
    public QueueInput findByID(Integer id) throws Exception {
        Session session = null;
        QueueInput input = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            Query query = session.createQuery("FROM QueueInput input WHERE input.id=:id")
                    .setInteger("id", id);
            List list = query.list();
            if (list != null && !list.isEmpty()) {
                for (Iterator<QueueInput> IT = list.iterator(); IT.hasNext();) {
                    QueueInput tmpInput = IT.next();
                    if (tmpInput != null) {
                        input = tmpInput;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
            }
        }
        return input;
    }

    @Override
    public List findAllByStatus(Byte status) throws Exception {
        Session session = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            Query query = session.createQuery("FROM QueueInput input WHERE input.status=:status")
                    .setByte("status", status);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public Integer saveOrUpdateQueueInput(QueueInput queueInput) throws Exception {
        Session session = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            session.beginTransaction();
            session.saveOrUpdate(queueInput);
            session.getTransaction().commit();
            session.flush();
            return queueInput.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized QueueInput fetchQueueInputRecord() throws Exception {
        Session session = null;
        QueueInput input = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            Query query = session.createQuery("FROM QueueInput input WHERE input.status=0 ORDER BY input.id");
            query.setMaxResults(1);

            List list = query.list();
            if (list != null && !list.isEmpty()) {
                input = (QueueInput) list.get(0);
                CSSupportTimer.activeThreadCount++;
                input.setStatus(1);
                this.saveOrUpdateQueueInput(input);
            }



        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
            }
        }
        return input;
    }
}
