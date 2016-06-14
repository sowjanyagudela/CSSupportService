/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssuport.hibernate.support.dao;

import cssupport.hbm.pojo.Urls;
import java.util.List;

/**
 *
 * @author babji
 */
public interface HibernateUrlsDAO {
    public List findAll() throws Exception;
    public Urls findByType(String type)  throws Exception;
    public Urls findSourceURL() throws Exception;
    public Urls findTargetURL() throws Exception;
}
