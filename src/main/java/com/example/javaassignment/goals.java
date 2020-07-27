package com.example.javaassignment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class goals {
    private String GoalId;
    private String title;
    private String details;
    private Integer eta;
    private Date createDate;
    private Date updateDate;

    public goals(){
        super();
    }
    public goals(String GoalId, String title, String details, Integer eta, Date createDate, Date updateDate) {
        super();
        this.GoalId = GoalId;
        this.title=title;
        this.details = details;
        this.eta = eta;
        this.createDate = createDate;
        this.updateDate=updateDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getGoalId(){
        return GoalId;
    }
    public void setGoalId(String GoalId){
        this.GoalId=GoalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    @Override
    public String toString(){
        return "Goals{" +
                "GoalId=" + GoalId +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", eta='" + eta + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
