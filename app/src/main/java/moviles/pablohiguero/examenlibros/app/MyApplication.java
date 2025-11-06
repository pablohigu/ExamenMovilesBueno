// Ruta: app/src/main/java/moviles/pablohiguero/examenlibros/app/MyApplication.java
package moviles.pablohiguero.examenlibros.app;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import moviles.pablohiguero.examenlibros.model.Book;

public class MyApplication extends Application {

    public static AtomicInteger BookID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("books.realm")
                .schemaVersion(1)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();
        BookID = getIdByTable(realm, Book.class);
        realm.close();
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        if (results.size() > 0) {
            Number maxId = realm.where(anyClass).max("id");
            return new AtomicInteger(maxId.intValue());
        } else {
            return new AtomicInteger(0);
        }
    }
    public static int getNextBookId() {
        return BookID.incrementAndGet();
    }
}