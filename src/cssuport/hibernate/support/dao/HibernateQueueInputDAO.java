/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssuport.hibernate.support.dao;

import cssupport.hbm.pojo.QueueInput;
import java.util.List;

/**
 *
 * @author babji
 */
public interface HibernateQueueInputDAO {
    public void setInitialState() throws Exception;
    public QueueInput findByID(Integer id)  throws Exception;
    public List findAllByStatus(Byte status) throws Exception;
    public Integer saveOrUpdateQueueInput(QueueInput queueInput) throws Exception;
    public QueueInput fetchQueueInputRecord() throws Exception;
}
