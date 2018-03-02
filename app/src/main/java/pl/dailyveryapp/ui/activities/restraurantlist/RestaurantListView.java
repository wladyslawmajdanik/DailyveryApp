package pl.dailyveryapp.ui.activities.restraurantlist;


import java.util.List;

import pl.dailyveryapp.model.restaurants.Restaurant;
import pl.dailyveryapp.ui.View;

/**
 * Created by samsung on 2018-03-02.
 */

public interface RestaurantListView extends View {
    void onSuccessDownloadRestaurantList(List<Restaurant> restaurantList);

    void onErroDownloadRestaurantList();
}
