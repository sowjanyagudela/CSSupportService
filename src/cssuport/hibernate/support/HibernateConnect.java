/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssuport.hibernate.support;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author babji
 */
public class HibernateConnect {
    private static HibernateConnect me;
    private Configuration config;
    private SessionFactory factory;
    private Session session;
    
    private HibernateConnect() {
        factory = new Configuration().configure().buildSessionFactory();
    }
    public static HibernateConnect getInstance() {
        if(me == null) {
            me = new HibernateConnect();
        }
        return me;
    }
    
    public Session getSession() {
//        if(session == null || !session.isOpen()) {
//            session = factory.openSession();
//        }
        
        session = factory.openSession();
        return session;
    }
}
