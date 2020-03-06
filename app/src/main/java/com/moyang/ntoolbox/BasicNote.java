package com.moyang.ntoolbox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BasicNote {

    private String title;
    private String description;
    private List<String> tags;
    private Date addDate;
    private Date lastEditDate;
    private boolean isAlert;
    private Date alertDate;
    private boolean isStarred;

    public BasicNote(){
        this("无标题");
    }

    public BasicNote(String input_title){
        this(input_title,"无描述", new ArrayList<String>(), false, new Date(System.currentTimeMillis()),false);
    }

    public BasicNote(String title, String description, List<String> tags, boolean isAlert, Date alertDate, boolean isStarred){
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.isAlert = isAlert;
        this.alertDate = alertDate;
        this.isStarred = isStarred;
        addDate = new Date(System.currentTimeMillis());
        lastEditDate = addDate;
    }

    public void setTitle(String input){
        title = input;
    }

    public void setDescription(String input){
        description = input;
    }

    public void setTags(ArrayList<String> input){
        tags = input;
    }

    public void addTag(String input){
        tags.add(input);
    }

    public void setIsAlert(boolean input){
        isAlert = input;
    }

    public void setAlertDate(Date input){
        alertDate = input;
    }

    public void setIsStarred(boolean input){
        isStarred = input;
    }

    public void setLastEditDate(){
        lastEditDate = new Date(System.currentTimeMillis());
    }

    // Get Functions
    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public List<String> getTags(){
        return tags;
    }

    public boolean getIsAlert(){
        return isAlert;
    }

    public Date getAlertDate(){
        return alertDate;
    }

    public boolean getIsStarred(){
        return isStarred;
    }

    public Date getAddDate(){
        return addDate;
    }

    public Date getLastEditDate(){
        return lastEditDate;
    }

}
