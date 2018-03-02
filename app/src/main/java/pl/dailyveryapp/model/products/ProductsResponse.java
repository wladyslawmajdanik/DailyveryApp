package pl.dailyveryapp.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pl.dailyveryapp.model.Description;


public class ProductsResponse implements Parcelable {

    public final static Creator<ProductsResponse> CREATOR = new Creator<ProductsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductsResponse createFromParcel(Parcel in) {
            return new ProductsResponse(in);
        }

        public ProductsResponse[] newArray(int size) {
            return (new ProductsResponse[size]);
        }

    };
    @SerializedName("data")
    @Expose
    private ProductsData data;
    @SerializedName("description")
    @Expose
    private Description description;
    @SerializedName("status")
    @Expose
    private String status;

    protected ProductsResponse(Parcel in) {
        this.data = ((ProductsData) in.readValue((ProductsData.class.getClassLoader())));
        this.description = ((Description) in.readValue((Description.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));

    }

    public ProductsResponse() {
    }

    public ProductsData getData() {
        return data;
    }

    public void setData(ProductsData data) {
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
