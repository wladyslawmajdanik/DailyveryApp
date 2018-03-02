package pl.dailyveryapp.model.products;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Products extends RealmObject implements Parcelable {

    public final static Creator<Products> CREATOR = new Creator<Products>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        public Products[] newArray(int size) {
            return (new Products[size]);
        }

    };
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    private int productsInBasket;

    protected Products(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productsInBasket = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Products() {
    }

    public Products(String price, int id, String name, int productsInBasket) {
        this.price = price;
        this.id = id;
        this.name = name;
        this.productsInBasket = productsInBasket;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductsInBasket() {
        return productsInBasket;
    }

    public void setProductsInBasket(int productsInBasket) {
        this.productsInBasket = productsInBasket;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(productsInBasket);
        dest.writeValue(name);
        dest.writeValue(price);

    }

    public int describeContents() {
        return 0;
    }

}
