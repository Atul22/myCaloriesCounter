package model;

import java.io.Serializable;

/**
 * Created by Atul Kumar on 11-12-2015.
 */
public class Food implements Serializable {
    private static final long serialVersionUID = 10L;

    private int foodId;
    private String foodName;
    private int foodCal;
    private String recordDate;


    public int getFoodCal() {
        return foodCal;
    }

    public void setFoodCal(int foodCal) {
        this.foodCal = foodCal;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

}
