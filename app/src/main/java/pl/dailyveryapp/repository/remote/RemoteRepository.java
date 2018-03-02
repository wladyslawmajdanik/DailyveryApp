package pl.dailyveryapp.repository.remote;


import org.json.JSONArray;

import io.reactivex.Observable;
import pl.dailyveryapp.model.SimpleResponse;
import pl.dailyveryapp.model.products.ProductsResponse;
import pl.dailyveryapp.model.restaurants.RestaurantResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RemoteRepository {

    @GET("restaurants")
    Observable<RestaurantResponse> getRestaurants();

    @FormUrlEncoded
    @POST("products")
    Observable<ProductsResponse> getProducts(@Field("restaurant_id") int restaurantId);

    @FormUrlEncoded
    @POST("order")
    Observable<SimpleResponse> orderProducts(@Field("restaurant_id") int restaurantId, @Field("products") JSONArray productsArray);
}