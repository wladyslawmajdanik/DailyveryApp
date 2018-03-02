package pl.dailyveryapp.ui.activities.productslist;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pl.dailyveryapp.model.products.Products;
import pl.dailyveryapp.model.products.ProductsResponse;
import pl.dailyveryapp.repository.local.LocalRepository;
import pl.dailyveryapp.repository.remote.RemoteRepository;
import pl.dailyveryapp.ui.Presenter;
import timber.log.Timber;

public class ProductsListPresenterImpl implements ProductsListPresenter, Presenter {


    RemoteRepository remoteRepository;
    LocalRepository localRepository;
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

    private Observable<ProductsResponse> getProductsObservable(int restaurantId) {
        return remoteRepository.getProducts(restaurantId)
                .subscribeOn(Schedulers.io());
    }

    private void onGetProductsSuccess(ProductsResponse productsResponse) {
        view.onSuccessDownloadProductList(productsResponse.getData().getProductsList());
    }

    private void onGetProductsError(Throwable throwable) {
        view.onErroDownloadProductList();
        Timber.e(throwable);

    }


}
