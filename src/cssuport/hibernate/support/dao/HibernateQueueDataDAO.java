/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssuport.hibernate.support.dao;

import cssupport.hbm.pojo.QueueData;

/**
 *
 * @author babji
 */
public interface HibernateQueueDataDAO {
    public QueueData findByID(Integer id) throws Exception;
    public QueueData findByQueueInputID(Integer queueInputID) throws Exception;
    public Integer saveOrUpdate(QueueData queueData) throws Exception;
}
