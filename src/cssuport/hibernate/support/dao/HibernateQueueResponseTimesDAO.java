/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssuport.hibernate.support.dao;

import cssupport.hbm.pojo.QueueResponseTimes;

/**
 *
 * @author babji
 */
public interface HibernateQueueResponseTimesDAO {
    public QueueResponseTimes findByID(Integer id) throws Exception;
    public QueueResponseTimes findByQueueInputID(Integer queueInputID) throws Exception;
    public Integer saveOrUpdate(QueueResponseTimes queueResponseTimes) throws Exception;
}
