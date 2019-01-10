package com.example.canteenchecker.canteenmanager.core;

import com.google.gson.annotations.SerializedName;

public class RatingModel {
    @SerializedName("ratingId")
    private Integer ratingId = null;
    @SerializedName("username")
    private String username = null;
    @SerializedName("remark")
    private String remark = null;
    @SerializedName("ratingPoints")
    private Integer ratingPoints = null;
    @SerializedName("timestamp")
    private Long timestamp = null;

    public Integer getRatingId() {
        return ratingId;
    }
    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRatingPoints() {
        return ratingPoints;
    }
    public void setRatingPoints(Integer ratingPoints) {
        this.ratingPoints = ratingPoints;
    }

    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RatingModel ratingModel = (RatingModel) o;
        return (this.ratingId == null ? ratingModel.ratingId == null : this.ratingId.equals(ratingModel.ratingId)) &&
                (this.username == null ? ratingModel.username == null : this.username.equals(ratingModel.username)) &&
                (this.remark == null ? ratingModel.remark == null : this.remark.equals(ratingModel.remark)) &&
                (this.ratingPoints == null ? ratingModel.ratingPoints == null : this.ratingPoints.equals(ratingModel.ratingPoints)) &&
                (this.timestamp == null ? ratingModel.timestamp == null : this.timestamp.equals(ratingModel.timestamp));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.ratingId == null ? 0: this.ratingId.hashCode());
        result = 31 * result + (this.username == null ? 0: this.username.hashCode());
        result = 31 * result + (this.remark == null ? 0: this.remark.hashCode());
        result = 31 * result + (this.ratingPoints == null ? 0: this.ratingPoints.hashCode());
        result = 31 * result + (this.timestamp == null ? 0: this.timestamp.hashCode());
        return result;
    }

    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class RatingModel {\n");

        sb.append("  ratingId: ").append(ratingId).append("\n");
        sb.append("  username: ").append(username).append("\n");
        sb.append("  remark: ").append(remark).append("\n");
        sb.append("  ratingPoints: ").append(ratingPoints).append("\n");
        sb.append("  timestamp: ").append(timestamp).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
