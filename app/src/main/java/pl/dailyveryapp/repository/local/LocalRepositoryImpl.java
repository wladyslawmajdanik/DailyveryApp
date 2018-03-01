package pl.dailyveryapp.repository.local;

import io.realm.Realm;


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
}
