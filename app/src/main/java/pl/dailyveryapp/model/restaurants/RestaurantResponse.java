package pl.dailyveryapp.model.restaurants;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pl.dailyveryapp.model.Description;


public class RestaurantResponse implements Parcelable {

    public final static Creator<RestaurantResponse> CREATOR = new Creator<RestaurantResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RestaurantResponse createFromParcel(Parcel in) {
            return new RestaurantResponse(in);
        }

        public RestaurantResponse[] newArray(int size) {
            return (new RestaurantResponse[size]);
        }

    };
    @SerializedName("data")
    @Expose
    private RestaurantData data;
    @SerializedName("description")
    @Expose
    private Description description;
    @SerializedName("status")
    @Expose
    private String status;

    protected RestaurantResponse(Parcel in) {
        this.data = ((RestaurantData) in.readValue((RestaurantData.class.getClassLoader())));
        this.description = ((Description) in.readValue((Description.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));

    }

    public RestaurantResponse() {
    }

    public RestaurantData getData() {
        return data;
    }

    public void setData(RestaurantData data) {
        this.data = data;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(data);
        dest.writeValue(description);
        dest.writeValue(status);

    }

    public int describeContents() {
        return 0;
    }

}
