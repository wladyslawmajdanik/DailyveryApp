package pl.dailyveryapp.injection.components;


import dagger.Component;
import pl.dailyveryapp.injection.modules.AppModule;
import pl.dailyveryapp.injection.modules.ContextModule;
import pl.dailyveryapp.injection.modules.NetworkModule;
import pl.dailyveryapp.injection.modules.RealmModule;
import pl.dailyveryapp.injection.scopes.AppScope;
import pl.dailyveryapp.repository.local.LocalRepositoryImpl;
import pl.dailyveryapp.repository.remote.RemoteRepository;


@AppScope
@Component(modules = {AppModule.class, NetworkModule.class, RealmModule.class, ContextModule.class})
public interface AppComponent {

    void inject(LocalRepositoryImpl localRepository);

    RemoteRepository getRemoteRepository();

}
