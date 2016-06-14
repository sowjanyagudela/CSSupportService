/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssupport.queue;

import cssuport.dto.CSQueueJOB;
import cssuport.hibernate.support.dao.HibernateQueueDataDAO;
import cssuport.hibernate.support.dao.HibernateQueueInputDAO;
import cssuport.hibernate.support.dao.HibernateQueueResponseTimesDAO;
import cssupport.communicate.CommunicateWithHttpClient;
import cssupport.tools.ToolBox;
import cssupport.daofactory.DAOFactory;
import cssupport.hbm.pojo.Urls;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 *
 * @author babji
 */
public class CSQueueJOBHandler {

    public void processCSQueueJOB(CSQueueJOB job) throws Exception {
        /**
         * 1. Prepare HDI
         */
        try {
            job.getQueueResponseTimes().setInTime(getCurrentTime());
            prepareRequestForCS(job);
            getResponseFromSource(job);
            prepareNotificationRQ(job);
            postToTarget(job);
            job.getQueueInput().setStatus(2);
        } catch (Exception e) {
            System.out.println("Debug @ stmt :: got an exception in Process");
            e.printStackTrace();
            job.getQueueInput().setStatus(3);
            throw e;
        } finally {
            CSSupportTimer.activeThreadCount--;
            saveToDB(job);
            destoy(job);
        }

    }

    public void postToTarget(CSQueueJOB job) throws Exception{
        System.out.println("Debug @ stmt :: Pushing to Target ------------------ Start ::: "+ new Date());
        job.getQueueResponseTimes().setTargetStartTime(getCurrentTime());
        Urls target =  ToolBox.getTarget();
        String response = "";
        try {
            HttpClient client = new HttpClient();
            client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            PostMethod postMethod = new PostMethod(target.getUrl());
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            NameValuePair message = new NameValuePair("otaRQ", job.getQueueData().getTargetRq());
            NameValuePair objektID = new NameValuePair("cltz_id", job.getQueueInput().getObjectId().toString());
            NameValuePair[] data = { message, objektID };
            postMethod.setRequestBody(data);
            int statusCode = client.executeMethod(postMethod);
            if(statusCode == HttpStatus.SC_OK) {
                InputStream is = postMethod.getResponseBodyAsStream();
                if(is != null) {
                    response += parseISToString(is);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        job.getQueueData().setTargetRs(response);
        job.getQueueResponseTimes().setTargetEndTime(getCurrentTime());
        System.out.println("Debug @ stmt :: Pushing to Target ------------------ END "+ new Date());
    }
    
    
      public void postToTarget1(CSQueueJOB job) throws Exception{
        System.out.println("Debug @ stmt :: Pushing to Target ------------------ Start ::: "+ new Date());
        job.getQueueResponseTimes().setTargetStartTime(getCurrentTime());
        Urls target =  ToolBox.getTarget();
        String response = "";
        try {
            HttpClient client = new HttpClient();
            client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            PostMethod postMethod = new PostMethod(target.getUrl());
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            NameValuePair message = new NameValuePair("otaRQ", job.getQueueData().getTargetRq());
            NameValuePair objektID = new NameValuePair("cltz_id", job.getQueueInput().getObjectId().toString());
            NameValuePair[] data = { message, objektID };
            postMethod.setRequestBody(data);
            int statusCode = client.executeMethod(postMethod);
            if(statusCode == HttpStatus.SC_OK) {
                InputStream is = postMethod.getResponseBodyAsStream();
                if(is != null) {
                    response += parseISToString(is);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        job.getQueueData().setTargetRs(response);
        job.getQueueResponseTimes().setTargetEndTime(getCurrentTime());
        System.out.println("Debug @ stmt :: Pushing to Target ------------------ END "+ new Date());
    }
    public String parseISToString(java.io.InputStream is) throws Exception {
        java.io.BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        try {
            String line = null;
            
            while((line = in.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                is.close();
            } catch(Exception ex) {
            }
            
        }// try / catch / finally
        
        return sb.toString();
        
    }
    public void prepareNotificationRQ(CSQueueJOB job) throws Exception {
        StringBuffer sb = new StringBuffer();
        String response = job.getQueueData().getSourceRs();
        Integer ObjectID = job.getQueueInput().getObjectId();
        
        int start = 0;
        start = response.indexOf(" PrimaryLangID=\"") + 16;
        String primaryLang = response.substring(start, start+2);
        
        
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        sb.append("<OTA_HotelDescriptiveContentNotifRQ xmlns=\"http://www.opentravel.org/OTA/2003/05\" TimeStamp=\""+ToolBox.getCurrentTimeStamp()+"\" Target=\"Production\" Version=\"3.00\" PrimaryLangID=\""+primaryLang+"\" >");
        sb.append("<POS>");
        sb.append("\n<Source>\n");
        sb.append("<RequestorID Type=\"10\" ID=\"" + ObjectID + "\"/>");
        sb.append("<BookingChannel Type=\"7\"/>\n</Source>\n");
        sb.append("</POS>\n");
        int startIndex = 0, endIndex = 0;
        startIndex = response.indexOf("<HotelDescriptiveContents");
        endIndex = response.indexOf("</HotelDescriptiveContents>");
        System.out.println("Debug @ stmt ::: indexes --  "+startIndex+" :: "+endIndex);
        if (startIndex > 0 && startIndex < endIndex) {
            sb.append(response.substring(startIndex, endIndex - 1)); // Check the index          
        } else {
            System.out.println("Debug @ stmt ::: ------------------------------------------------------");
            System.out.println("Debug @ stmt ::: Object ID Missing::: "+job.getQueueInput().getObjectId());
            System.out.println("Debug @ stmt ::: ------------------------------------------------------");
            sb.append(" <HotelDescriptiveContents>\n");
            throw new Exception("Missing Object ID "+job.getQueueInput().getObjectId());
        }
        sb.append(" </HotelDescriptiveContents>\n");
        sb.append("</OTA_HotelDescriptiveContentNotifRQ>");
        job.getQueueData().setTargetRq(sb.toString());
    }

    public void getResponseFromSource(CSQueueJOB job) {
        try {
            job.getQueueResponseTimes().setCsstartTime(getCurrentTime());

            System.out.println("Debug @ stmt ---- Trying to get Response --------------");

            Urls source = ToolBox.getSource();

            String CS_URL = source.getServlet();
            String postData = "otaRQ=" + CommunicateWithHttpClient.encodeData(job.getQueueData().getSourceRq());
          
            CommunicateWithHttpClient comm = new CommunicateWithHttpClient(source.getHostName(), source.getPort(), CS_URL, postData);
            comm.doHandShakeWithHTTPClient();
            System.out.println("Debug @ stmt :: HandShake Completed");
            String response = comm.getResponseData();
            int retryCounter = 0;
            if (comm.isConnectionProblems()) {
                response = null;
                while (++retryCounter <= 1) {
                    if (comm.isConnectionProblems()) {
                        response = null;
                        try {
                            Thread.sleep(180000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        comm.setConnectionProblems(false);
                        comm.doHandShakeWithHTTPClient();
                        response = comm.getResponseData();
                    } else {
                        break;
                    }
                }
            }
            System.out.println("Response:::"+response);
            job.getQueueData().setSourceRs(response);
            job.getQueueResponseTimes().setCsendTime(getCurrentTime());
            System.out.println("Debug @ stmt ---- Trying to get Response ENDs --------------");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void prepareRequestForCS(CSQueueJOB job) {
        Integer objectID = job.getQueueInput().getObjectId();
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<OTA_HotelDescriptiveInfoRQ\n"
                + "					xmlns=\"http://www.opentravel.org/OTA/2003/05\"\n"
                + "					TimeStamp=\"" + ToolBox.getCurrentTimeStamp() + "\" Target=\"Production\" Version=\"3.14\"\n"
                + "					PrimaryLangID=\"en\">\n"
                + "	<POS>    \n"
                + "   <Source AgentSine=\"74\" AgentDutyCode=\"18941305ba1e3572\">\n"
                + "      <RequestorID Type=\"10\" ID=\"" + objectID.toString() + "\" ID_Context=\"CLTZ\"/>\n"
                + "     <BookingChannel Type=\"4\"/>\n"
                + "   </Source>\n"
                + "  </POS>\n"
                + "	<HotelDescriptiveInfos>\n"
                + "		<HotelDescriptiveInfo HotelCode=\"" + objectID.toString() + "\">\n"
                + "                     <ContentInfos>"
                + "                         <ContentInfo Name=\"DistributorsConnection\" Code=\"2\" />"
                + "                     </ContentInfos>"
                + "			<HotelInfo SendData=\"1\" />\n"
                + "                     <FacilityInfo SendGuestRooms=\"1\" />"
                + "                     <AreaInfo SendRecreations=\"1\" SendRefPoints=\"1\"/>"
                + "                     <AffiliationInfo SendAwards=\"1\" />"
                + "                     <ContactInfo SendData=\"1\"/>"
                + "                     <Policies SendPolicies=\"1\" />"
                + "		</HotelDescriptiveInfo>\n"
                + "	</HotelDescriptiveInfos>\n"
                + "</OTA_HotelDescriptiveInfoRQ>");
        job.getQueueData().setSourceRq(sb.toString());
    }

    public void saveToDB(CSQueueJOB job) throws Exception {
        try {
            DAOFactory dao = DAOFactory.getInstance();
            HibernateQueueInputDAO queueInputDAO = dao.getQueueInputDAO();
            queueInputDAO.saveOrUpdateQueueInput(job.getQueueInput());

            HibernateQueueDataDAO queueDataDAO = dao.getQueueDataDAO();
            queueDataDAO.saveOrUpdate(job.getQueueData());

            HibernateQueueResponseTimesDAO queueResponseTimesDAO = dao.getQueueResponseTimesDAO();
            queueResponseTimesDAO.saveOrUpdate(job.getQueueResponseTimes());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void destoy(CSQueueJOB job) {
        job = null;
    }

    public Long getCurrentTime() {
        return new Date().getTime();
    }
}
