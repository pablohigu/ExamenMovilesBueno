package moviles.pablohiguero.examenlibros;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmChangeListener; // Para refrescar la lista
import io.realm.RealmResults;

import moviles.pablohiguero.examenlibros.adapters.BookAdapter;
import moviles.pablohiguero.examenlibros.model.Book;
import moviles.pablohiguero.examenlibros.utils.Utils; // Asegúrate de tener esta clase

public class MainActivity extends AppCompatActivity {
    private RealmResults<Book> bookList;
    // RealmResults se usa para refrescar la lista
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private FloatingActionButton fabAdd;
    private Realm realm;

    private BookAdapter.OnItemClickListener listenerClick;
    private BookAdapter.OnItemLongClickListener listenerLongClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        loadSampleData(); // Carga datos de ejemplo SI LA BD ESTÁ VACÍA
        bookList = realm.where(Book.class).findAll(); // Carga TODOS los libros
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.listenerClick = new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int bookId) {
                // Buscamos el libro en Realm
                Book book = realm.where(Book.class).equalTo("id", bookId).findFirst();
                if (book == null) return;

// ExecuteTransaction:
// 1. Inicia la transacción (beginTransaction).
// 2. Ejecuta el código que le pasamos (el bloque "r -> { ... }").
// 3. Si el código termina sin errores, guarda los cambios (commitTransaction).
// 4. Si el código da un error, deshace todo (cancelTransaction).
//
// ahorro escribir el try-catch y gestionar el commit/cancel a mano.
                realm.executeTransaction(r -> {
                    String currentState = book.getEstado();
                    if (currentState.equalsIgnoreCase("Pendiente")) {
                        book.setEstado("Leyendo");
                    } else if (currentState.equalsIgnoreCase("Leyendo")) {
                        book.setEstado("Leido");
                    } else if (currentState.equalsIgnoreCase("Leido")) {
                        book.setEstado("Pendiente");
                    }
                    else {
                        // esto lo pongo porque había veces que no me reaccionaba cuando esta en Leido
                        book.setEstado("Pendiente");
                    }
                });
                // (notifyDataSetChanged no es necesario, RealmResults lo hace)
                Toast.makeText(MainActivity.this, "Estado: " + book.getEstado(), Toast.LENGTH_SHORT).show();
            }
        };

        this.listenerLongClick = new BookAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int bookId) {
                Book book = realm.where(Book.class).equalTo("id", bookId).findFirst();
                if (book == null) return;
                realm.executeTransaction(r -> {
                    book.setFavorito(!book.isFavorito());
                });
                String favText = book.isFavorito() ? "Añadido a favoritos" : "Quitado de favoritos";
                Toast.makeText(MainActivity.this, favText, Toast.LENGTH_SHORT).show();
            }
        };
        adapter = new BookAdapter(bookList, this, listenerClick, listenerLongClick);
        recyclerView.setAdapter(adapter);

        bookList.addChangeListener(new RealmChangeListener<RealmResults<Book>>() {
            @Override
            public void onChange(RealmResults<Book> books) {
                adapter.notifyDataSetChanged();
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addBook.class);
                startActivity(intent);
            }
        });
    }
    private void loadSampleData() {
        // Comprueba si la base de datos ya tiene datos
        long count = realm.where(Book.class).count();
        if (count == 0) {
            ArrayList<Book> samples = Utils.getSampleBooks();
            realm.beginTransaction();
            realm.copyToRealm(samples);
            realm.commitTransaction();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}