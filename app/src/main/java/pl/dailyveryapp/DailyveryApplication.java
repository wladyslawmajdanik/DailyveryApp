package pl.dailyveryapp;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import pl.dailyveryapp.injection.components.AppComponent;
import pl.dailyveryapp.injection.components.DaggerAppComponent;
import pl.dailyveryapp.injection.modules.AppModule;
import pl.dailyveryapp.injection.modules.ContextModule;
import pl.dailyveryapp.injection.modules.NetworkModule;
import pl.dailyveryapp.injection.modules.RealmModule;
import pl.dailyveryapp.utils.Constants;


public class DailyveryApplication extends MultiDexApplication {

    private static DailyveryApplication instance;
    private AppComponent component;

    public DailyveryApplication() {
        instance = this;
    }

    public static DailyveryApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Realm.getInstance(config);

        Stetho.initializeWithDefaults(this);

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .contextModule(new ContextModule(this))
                .realmModule(new RealmModule())
                .networkModule(new NetworkModule(Constants.getBaseUrl()))
                .build();

    }


    public AppComponent getComponent() {
        return component;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}