package pl.dailyveryapp.model.restaurants;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RestaurantData {

    public final static Parcelable.Creator<RestaurantData> CREATOR = new Parcelable.Creator<RestaurantData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RestaurantData createFromParcel(Parcel in) {
            return new RestaurantData(in);
        }

        public RestaurantData[] newArray(int size) {
            return (new RestaurantData[size]);
        }

    };
    @SerializedName("restaurants")
    @Expose
    private List<Restaurant> restaurantList = null;

    protected RestaurantData(Parcel in) {
        in.readList(this.restaurantList, (Restaurant.class.getClassLoader()));

    }

    public RestaurantData() {
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(restaurantList);

    }

    public int describeContents() {
        return 0;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
