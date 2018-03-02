package pl.dailyveryapp.repository.local;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import pl.dailyveryapp.model.products.Products;


public class LocalRepositoryImpl implements LocalRepository {

    private Realm realm;

    public LocalRepositoryImpl(Realm realm) {
        this.realm = realm;
    }


    private void getRealmInstance() {
        if (realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void changeBasket(Products products) {
        getRealmInstance();
        realm.executeTransaction(realm -> realm.insertOrUpdate(products));

    }


    @Override
    public void checkProducts(List<Products> productList) {
        getRealmInstance();
        RealmResults<Products> getProductInBasket = realm.where(Products.class).findAll();
        for (int a = 0; a < productList.size(); a++) {
            for (int b = 0; b < getProductInBasket.size(); b++) {

                if (getProductInBasket.get(b).getId().intValue() == productList.get(a).getId().intValue()) {
                    productList.get(a).setProductsInBasket(getProductInBasket.get(b).getProductsInBasket());
                }
            }
        }


    }
}
