package pl.dailyveryapp.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProductsData {

    public final static Parcelable.Creator<ProductsData> CREATOR = new Parcelable.Creator<ProductsData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductsData createFromParcel(Parcel in) {
            return new ProductsData(in);
        }

        public ProductsData[] newArray(int size) {
            return (new ProductsData[size]);
        }

    };
    @SerializedName("products")
    @Expose
    private List<Products> productsList = null;

    protected ProductsData(Parcel in) {
        in.readList(this.productsList, (Products.class.getClassLoader()));

    }

    public ProductsData() {
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(productsList);

    }

    public int describeContents() {
        return 0;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setRestaurantList(List<Products> restaurantList) {
        this.productsList = productsList;
    }
}
