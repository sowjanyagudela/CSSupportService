package cssupport.hbm.pojo;
// Generated 8 Jul, 2014 4:49:08 PM by Hibernate Tools 3.2.1.GA



/**
 * QueueData generated by hbm2java
 */
public class QueueData  implements java.io.Serializable {


     private Integer id;
     private Integer queueInputId;
     private String sourceRq;
     private String sourceRs;
     private String targetRq;
     private String targetRs;

    public QueueData() {
    }

    public QueueData(Integer queueInputId, String sourceRq, String sourceRs, String targetRq, String targetRs) {
       this.queueInputId = queueInputId;
       this.sourceRq = sourceRq;
       this.sourceRs = sourceRs;
       this.targetRq = targetRq;
       this.targetRs = targetRs;
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
    public String getSourceRq() {
        return this.sourceRq;
    }
    
    public void setSourceRq(String sourceRq) {
        this.sourceRq = sourceRq;
    }
    public String getSourceRs() {
        return this.sourceRs;
    }
    
    public void setSourceRs(String sourceRs) {
        this.sourceRs = sourceRs;
    }
    public String getTargetRq() {
        return this.targetRq;
    }
    
    public void setTargetRq(String targetRq) {
        this.targetRq = targetRq;
    }
    public String getTargetRs() {
        return this.targetRs;
    }
    
    public void setTargetRs(String targetRs) {
        this.targetRs = targetRs;
    }

    @Override
    public String toString() {
        return "QueueData{" + "id=" + id + ", queueInputId=" + queueInputId + ", sourceRq=" + sourceRq + ", sourceRs=" + sourceRs + ", targetRq=" + targetRq + ", targetRs=" + targetRs + '}';
    }




}


