package pl.dailyveryapp.ui.activities.restraurantlist;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pl.dailyveryapp.model.restaurants.RestaurantResponse;
import pl.dailyveryapp.repository.remote.RemoteRepository;
import pl.dailyveryapp.ui.Presenter;
import timber.log.Timber;

public class RestaurantListPresenterImpl implements RestaurantListPresenter, Presenter {


    RemoteRepository remoteRepository;
    private RestaurantListView view;
    private CompositeDisposable disposable;

    public RestaurantListPresenterImpl(RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
        disposable = new CompositeDisposable();
    }

    @Override
    public void clearRx() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void setView(RestaurantListView view) {
        this.view = view;
    }

    @Override
    public void getRestaurants() {
        disposable.add(getRestaurantObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetRestaurantSuccess, this::onGetRestaurantError));
    }

    private Observable<RestaurantResponse> getRestaurantObservable() {
        return remoteRepository.getRestaurants()
                .subscribeOn(Schedulers.io());
    }

    private void onGetRestaurantSuccess(RestaurantResponse restaurantResponse) {
        view.onSuccessDownloadRestaurantList(restaurantResponse.getData().getRestaurantList());
    }

    private void onGetRestaurantError(Throwable throwable) {
        view.onErroDownloadRestaurantList();
        Timber.e(throwable);
    }


}
