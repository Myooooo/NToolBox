package com.moyang.ntoolbox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemNote extends BasicNote {

    private String location;
    private int quantity;
    private String quantifier;
    private int[] validPeriod = new int[4];  // 年 月 日 小时
    private Date dueDate;
    private boolean dueFlag;
    //private List<Uri> imageUri = new ArrayList<>();
    private int imageNum;

    public ItemNote(String title, String description, List<String> tags, boolean isAlert, Date alertDate, boolean isStarred,
                    String location, int quantity, String quantifier, Date dueDate, boolean dueFlag){

        super(title, description, tags, isAlert, alertDate, isStarred);

        this.location = location;
        this.quantity = quantity;
        this.quantifier = quantifier;
        this.dueDate = dueDate;
        this.dueFlag = dueFlag;
        validPeriod = getValidPeriodLeft();
        imageNum = 0;

        //setImageUri(imageUri);

    }

    public void setLocation(String input){
        location = input;
    }

    public void setQuantity(int n){
        quantity = n;
    }

    public void setQuantifier(String input){
        quantifier = input;
    }

    public void setDueFlag(boolean input){
        dueFlag = input;
    }

    /*public void setImageUri(List<Uri> list){
        for(int i = 0; i < list.size(); i++){
            if(imageNum < MAX_IMAGE_NUM){
                imageUri.add(list.get(i));
                imageNum++;
            }
        }
    }

    public boolean addImage(Uri uri){
        if(imageNum < MAX_IMAGE_NUM){
            imageUri.add(uri);
            return true;
        }else{
            return false;
        }
    }*/

    public String getLocation(){
        return location;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getQuantifier(){
        return quantifier;
    }

    public int[] getValidPeriod(){
        return validPeriod;
    }

    public int[] getValidPeriodLeft(){
        return TimeUtility.daysToYears(TimeUtility.getTimeDiff(new Date(System.currentTimeMillis()), dueDate));
    }

    public Date getDueDate(){
        return dueDate;
    }

    public boolean getDueFlag(){
        return dueFlag;
    }

    /*public List<Uri> getImageUri(){
        return imageUri;
    }*/

    public int getImageNum(){
        return imageNum;
    }

}
