package pl.dailyveryapp.ui.activities.restraurantlist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.dailyveryapp.DailyveryApplication;
import pl.dailyveryapp.R;
import pl.dailyveryapp.model.restaurants.Restaurant;
import pl.dailyveryapp.ui.activities.base.BaseActivity;
import pl.dailyveryapp.ui.activities.restraurantlist.adapter.RestaurantListAdapter;

public class RestaurantListActivity extends BaseActivity implements RestaurantListView {
    @Inject
    public RestaurantListPresenter restaurantListPresenter;
    @BindView(R.id.restaurant_list_cointainer)
    RelativeLayout restaurantListCointainer;

    @BindView(R.id.restaurant_list_recycler)
    RecyclerView restaurantsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restraurant_list);
        ButterKnife.bind(this);
        ((DailyveryApplication) getApplication()).getComponent().inject(this);
        restaurantListPresenter.setView(this);
        getRestaurants();

    }

    @Override
    public void onSuccessDownloadRestaurantList(List<Restaurant> restaurantList) {
        setRestaurantListAdapter(restaurantList);
        hideWaitDialog();
    }

    @Override
    public void onErrorDownloadRestaurantList() {
        showError(restaurantListCointainer);
        hideWaitDialog();
    }


    private void getRestaurants() {
        if (isNetworkAvailable()) {
            waitDialog.show();
            restaurantListPresenter.getRestaurants();
        } else {
            handleNoInternetState(restaurantListCointainer);
        }
    }

    private void setRestaurantListAdapter(List<Restaurant> restaurantList) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        restaurantsRecycler.setLayoutManager(layoutManager);
        RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(this, restaurantList);
        restaurantsRecycler.setAdapter(restaurantListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideWaitDialog();
    }
}
