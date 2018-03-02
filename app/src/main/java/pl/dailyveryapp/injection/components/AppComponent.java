package pl.dailyveryapp.injection.components;


import dagger.Component;
import pl.dailyveryapp.injection.modules.AppModule;
import pl.dailyveryapp.injection.modules.ContextModule;
import pl.dailyveryapp.injection.modules.NetworkModule;
import pl.dailyveryapp.injection.modules.RealmModule;
import pl.dailyveryapp.injection.scopes.AppScope;
import pl.dailyveryapp.repository.local.LocalRepositoryImpl;
import pl.dailyveryapp.repository.remote.RemoteRepository;
import pl.dailyveryapp.ui.activities.base.BaseActivity;
import pl.dailyveryapp.ui.activities.productslist.ProductsListActivity;
import pl.dailyveryapp.ui.activities.restraurantlist.RestaurantListActivity;


@AppScope
@Component(modules = {AppModule.class, NetworkModule.class, RealmModule.class, ContextModule.class})
public interface AppComponent {

    RemoteRepository getRemoteRepository();

    void inject(LocalRepositoryImpl localRepository);

    void inject(BaseActivity baseActivity);

    void inject(RestaurantListActivity restaurantListActivity);

    void inject(ProductsListActivity productsListActivity);

}
