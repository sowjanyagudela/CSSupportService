package cssupport.hbm.pojo;
// Generated 8 Jul, 2014 4:49:08 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * QueueResponseTimes generated by hbm2java
 */
public class QueueResponseTimes  implements java.io.Serializable {


     private Integer id;
     private Integer queueInputId;
     private Long inTime;
     private Long csstartTime;
     private Long csendTime;
     private Long targetStartTime;
     private Long targetEndTime;

    public QueueResponseTimes() {
    }

    public QueueResponseTimes(Integer id, Integer queueInputId, Long inTime, Long csstartTime, Long csendTime, Long targetStartTime, Long targetEndTime) {
        this.id = id;
        this.queueInputId = queueInputId;
        this.inTime = inTime;
        this.csstartTime = csstartTime;
        this.csendTime = csendTime;
        this.targetStartTime = targetStartTime;
        this.targetEndTime = targetEndTime;
    }

    
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getQueueInputId() {
        return this.queueInputId;
    }
    
    public void setQueueInputId(Integer queueInputId) {
        this.queueInputId = queueInputId;
    }

    public Long getInTime() {
        return inTime;
    }

    public void setInTime(Long inTime) {
        this.inTime = inTime;
    }

    public Long getCsstartTime() {
        return csstartTime;
    }

    public void setCsstartTime(Long csstartTime) {
        this.csstartTime = csstartTime;
    }

    public Long getCsendTime() {
        return csendTime;
    }

    public void setCsendTime(Long csendTime) {
        this.csendTime = csendTime;
    }

    public Long getTargetStartTime() {
        return targetStartTime;
    }

    public void setTargetStartTime(Long targetStartTime) {
        this.targetStartTime = targetStartTime;
    }

    public Long getTargetEndTime() {
        return targetEndTime;
    }

    public void setTargetEndTime(Long targetEndTime) {
        this.targetEndTime = targetEndTime;
    }
    
    @Override
    public String toString() {
        return "QueueResponseTimes{" + "id=" + id + ", queueInputId=" + queueInputId + ", inTime=" + inTime + ", csstartTime=" + csstartTime + ", csendTime=" + csendTime + ", targetStartTime=" + targetStartTime + ", targetEndTime=" + targetEndTime + '}';
    }




}

