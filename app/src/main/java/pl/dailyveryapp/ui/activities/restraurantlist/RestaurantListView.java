package pl.dailyveryapp.ui.activities.restraurantlist;


import java.util.List;

import pl.dailyveryapp.model.restaurants.Restaurant;
import pl.dailyveryapp.ui.View;


public interface RestaurantListView extends View {
    void onSuccessDownloadRestaurantList(List<Restaurant> restaurantList);

    void onErrorDownloadRestaurantList();
}
