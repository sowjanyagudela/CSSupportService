/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssupport.tools;

import cssuport.hibernate.support.dao.HibernateUrlsDAO;
import cssupport.daofactory.DAOFactory;
import cssupport.hbm.pojo.Urls;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author babji
 */
public class ToolBox {

    private static Urls source;
    private static Urls targert;

    public static String getCurrentTimeStamp() {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat1.format(date) + "T" + dateFormat2.format(date);
    }

    public static Urls getSource() {
        if (source == null) {
            try {
                DAOFactory factory = DAOFactory.getInstance();
                HibernateUrlsDAO urlsDAO = factory.getUrlsDAO();
                source = urlsDAO.findSourceURL();

            } catch (Exception ex) {
                Logger.getLogger(ToolBox.class.getName()).log(Level.SEVERE, "In GetSource", ex);
            }
        }
        return source;
    }

    public static Urls getTarget() {
        if (targert == null) {
            try {
                DAOFactory factory = DAOFactory.getInstance();
                HibernateUrlsDAO urlsDAO = factory.getUrlsDAO();
                targert = urlsDAO.findTargetURL();

            } catch (Exception ex) {
                Logger.getLogger(ToolBox.class.getName()).log(Level.SEVERE, "In GetTarget", ex);
            }
        }
        return targert;
    }
}
