package pl.dailyveryapp.ui.activities.productslist;


import org.json.JSONArray;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pl.dailyveryapp.model.SimpleResponse;
import pl.dailyveryapp.model.products.Products;
import pl.dailyveryapp.model.products.ProductsResponse;
import pl.dailyveryapp.repository.local.LocalRepository;
import pl.dailyveryapp.repository.remote.RemoteRepository;
import pl.dailyveryapp.ui.Presenter;
import timber.log.Timber;

public class ProductsListPresenterImpl implements ProductsListPresenter, Presenter {


    private RemoteRepository remoteRepository;
    private LocalRepository localRepository;
    private ProductsListView view;
    private CompositeDisposable disposable;

    public ProductsListPresenterImpl(RemoteRepository remoteRepository, LocalRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
        disposable = new CompositeDisposable();
    }

    @Override
    public void clearRx() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void setView(ProductsListView view) {
        this.view = view;
    }

    @Override
    public void changeBasket(Products products) {
        localRepository.changeBasket(products);
    }

    @Override
    public void checkProducts(List<Products> productList) {
        localRepository.checkProducts(productList);
    }

    @Override
    public void getProducts(int restaurantId) {
        disposable.add(getProductsObservable(restaurantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetProductsSuccess, this::onGetProductsError));
    }


    @Override
    public void orderProducts(int restaurantId, JSONArray productsToOrder) {
        disposable.add(orderProductsObservable(restaurantId, productsToOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMakeOrderSuccess, this::onMakeOrderError));
    }

    @Override
    public void removeBasket() {
        localRepository.removeBasket();
    }

    private Observable<ProductsResponse> getProductsObservable(int restaurantId) {
        return remoteRepository.getProducts(restaurantId)
                .subscribeOn(Schedulers.io());
    }

    private Observable<SimpleResponse> orderProductsObservable(int restaurantId, JSONArray productsToOrder) {
        return remoteRepository.orderProducts(restaurantId, productsToOrder)
                .subscribeOn(Schedulers.io());

    }

    private void onGetProductsSuccess(ProductsResponse productsResponse) {
        view.onSuccessDownloadProductList(productsResponse.getData().getProductsList());
    }

    private void onGetProductsError(Throwable throwable) {
        view.onErrorDownloadProductList();
        Timber.e(throwable);

    }

    private void onMakeOrderSuccess(SimpleResponse simpleResponse) {
        view.onMakeOrderSuccess();
    }

    private void onMakeOrderError(Throwable throwable) {
        view.onMakeOrderError();
        Timber.e(throwable);

    }


}
