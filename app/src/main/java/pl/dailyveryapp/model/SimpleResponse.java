package pl.dailyveryapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimpleResponse implements Parcelable {

    public final static Parcelable.Creator<SimpleResponse> CREATOR = new Creator<SimpleResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SimpleResponse createFromParcel(Parcel in) {
            return new SimpleResponse(in);
        }

        public SimpleResponse[] newArray(int size) {
            return (new SimpleResponse[size]);
        }

    };
    @SerializedName("data")
    @Expose
    private JsonObject data;
    @SerializedName("description")
    @Expose
    private Description description;
    @SerializedName("status")
    @Expose
    private Integer status;

    protected SimpleResponse(Parcel in) {
        this.data = ((JsonObject) in.readValue((JsonObject.class.getClassLoader())));
        this.description = ((Description) in.readValue((Description.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public SimpleResponse() {
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
