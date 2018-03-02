package pl.dailyveryapp.injection.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import pl.dailyveryapp.injection.scopes.AppScope;
import pl.dailyveryapp.repository.local.LocalRepository;
import pl.dailyveryapp.repository.local.LocalRepositoryImpl;
import pl.dailyveryapp.repository.remote.RemoteRepository;
import pl.dailyveryapp.ui.activities.productslist.ProductsListPresenter;
import pl.dailyveryapp.ui.activities.productslist.ProductsListPresenterImpl;
import pl.dailyveryapp.ui.activities.restraurantlist.RestaurantListPresenter;
import pl.dailyveryapp.ui.activities.restraurantlist.RestaurantListPresenterImpl;
import pl.dailyveryapp.utils.Constants;

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application app) {
        mApplication = app;
    }

    @Provides
    @AppScope
    public LocalRepository provideLocalRepository(Realm realm) {
        return new LocalRepositoryImpl(realm);
    }

    @Provides
    SharedPreferences provideSharedPrefs() {
        return mApplication.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    public RestaurantListPresenter provideOrderPresenter(RemoteRepository remoteRepository) {
        return new RestaurantListPresenterImpl(remoteRepository);
    }

    @Provides
    public ProductsListPresenter productsListPresenter(RemoteRepository remoteRepository, LocalRepository localRepository) {
        return new ProductsListPresenterImpl(remoteRepository, localRepository);
    }
}
