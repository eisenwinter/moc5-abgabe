package com.example.canteenchecker.canteenmanager.core;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

public class CanteenModel {
    @SerializedName("canteenId")
    private Integer canteenId = null;
    @SerializedName("name")
    private String name = null;
    @SerializedName("meal")
    private String meal = null;
    @SerializedName("mealPrice")
    private Double mealPrice = null;
    @SerializedName("address")
    private String address = null;
    @SerializedName("website")
    private String website = null;
    @SerializedName("phone")
    private String phone = null;
    @SerializedName("averageRating")
    private Double averageRating = null;
    @SerializedName("averageWaitingTime")
    private Integer averageWaitingTime = null;
    @SerializedName("ratings")
    private Collection<RatingModel> ratings = null;

    public CanteenModel(
            Integer canteenId,
            String name,
            String meal,
            Double mealPrice,
            String address,
            String website,
            String phone,
            Double averageRating,
            Integer averageWaitingTime,
            Collection<RatingModel> ratings
    ){
        this.canteenId = canteenId;
        this.name = name;
        this.meal = meal;
        this.mealPrice = mealPrice;
        this.address = address;
        this.website = website;
        this.phone = phone;
        this.averageRating = averageRating;
        this.averageWaitingTime = averageWaitingTime;
        this.ratings = ratings;
    }

    public CanteenModel(){

    }

    public Integer getCanteenId() {
        return canteenId;
    }
    public void setCanteenId(Integer canteenId) {
        this.canteenId = canteenId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getMeal() {
        return meal;
    }
    public void setMeal(String meal) {
        this.meal = meal;
    }


    public Double getMealPrice() {
        return mealPrice;
    }
    public void setMealPrice(Double mealPrice) {
        this.mealPrice = mealPrice;
    }


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getAverageRating() {
        return averageRating;
    }
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getAverageWaitingTime() {
        return averageWaitingTime;
    }
    public void setAverageWaitingTime(Integer averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }


    public Collection<RatingModel> getRatings() {
        return ratings;
    }
    public void setRatings(List<RatingModel> ratings) {
        this.ratings = ratings;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CanteenModel canteenModel = (CanteenModel) o;
        return (this.canteenId == null ? canteenModel.canteenId == null : this.canteenId.equals(canteenModel.canteenId)) &&
                (this.name == null ? canteenModel.name == null : this.name.equals(canteenModel.name)) &&
                (this.meal == null ? canteenModel.meal == null : this.meal.equals(canteenModel.meal)) &&
                (this.mealPrice == null ? canteenModel.mealPrice == null : this.mealPrice.equals(canteenModel.mealPrice)) &&
                (this.address == null ? canteenModel.address == null : this.address.equals(canteenModel.address)) &&
                (this.website == null ? canteenModel.website == null : this.website.equals(canteenModel.website)) &&
                (this.phone == null ? canteenModel.phone == null : this.phone.equals(canteenModel.phone)) &&
                (this.averageRating == null ? canteenModel.averageRating == null : this.averageRating.equals(canteenModel.averageRating)) &&
                (this.averageWaitingTime == null ? canteenModel.averageWaitingTime == null : this.averageWaitingTime.equals(canteenModel.averageWaitingTime)) &&
                (this.ratings == null ? canteenModel.ratings == null : this.ratings.equals(canteenModel.ratings));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.canteenId == null ? 0: this.canteenId.hashCode());
        result = 31 * result + (this.name == null ? 0: this.name.hashCode());
        result = 31 * result + (this.meal == null ? 0: this.meal.hashCode());
        result = 31 * result + (this.mealPrice == null ? 0: this.mealPrice.hashCode());
        result = 31 * result + (this.address == null ? 0: this.address.hashCode());
        result = 31 * result + (this.website == null ? 0: this.website.hashCode());
        result = 31 * result + (this.phone == null ? 0: this.phone.hashCode());
        result = 31 * result + (this.averageRating == null ? 0: this.averageRating.hashCode());
        result = 31 * result + (this.averageWaitingTime == null ? 0: this.averageWaitingTime.hashCode());
        result = 31 * result + (this.ratings == null ? 0: this.ratings.hashCode());
        return result;
    }

    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class CanteenModel {\n");

        sb.append("  canteenId: ").append(canteenId).append("\n");
        sb.append("  name: ").append(name).append("\n");
        sb.append("  meal: ").append(meal).append("\n");
        sb.append("  mealPrice: ").append(mealPrice).append("\n");
        sb.append("  address: ").append(address).append("\n");
        sb.append("  website: ").append(website).append("\n");
        sb.append("  phone: ").append(phone).append("\n");
        sb.append("  averageRating: ").append(averageRating).append("\n");
        sb.append("  averageWaitingTime: ").append(averageWaitingTime).append("\n");
        sb.append("  ratings: ").append(ratings).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
