/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssuport.hibernate.support.impl;

import cssuport.hibernate.support.HibernateConnect;
import cssuport.hibernate.support.dao.HibernateUrlsDAO;
import cssupport.daofactory.DAOFactory;
import cssupport.hbm.pojo.Urls;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author babji
 */
public class HibernateUrlsImpl implements HibernateUrlsDAO{

    @Override
    public List findAll() throws Exception{
        Session session = null;
        try {
            HibernateConnect connect = HibernateConnect.getInstance();
            session = connect.getSession();
            Query query = session.createQuery("FROM Urls url WHERE url.status = 1");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw  e;
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
    public Urls findByType(String type) throws Exception {
        Urls urls = null;
        try {
            List list = null;
            DAOFactory daoFactory = DAOFactory.getInstance();
            HibernateUrlsDAO hibernateUrlsDAO = daoFactory.getUrlsDAO();
            list = hibernateUrlsDAO.findAll();
            
            for (Iterator<Urls> IT = list.iterator(); IT.hasNext();) {
                Urls tmpUrls = IT.next();
                if(tmpUrls != null && tmpUrls.getType().equalsIgnoreCase(type)) {
                    urls = tmpUrls;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return urls;
    }

    @Override
    public Urls findSourceURL() throws Exception {
        try {
            return findByType("SOURCE");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Urls findTargetURL() throws Exception {
        try {
            return findByType("TARGET");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
}
