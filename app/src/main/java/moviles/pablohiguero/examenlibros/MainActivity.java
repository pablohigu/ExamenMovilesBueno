package moviles.pablohiguero.examenlibros;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast; // Importamos Toast

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

// 1. Importamos el Adapter y el Modelo
import moviles.pablohiguero.examenlibros.adapters.BookAdapter;
import moviles.pablohiguero.examenlibros.model.Book;

// 2. La "cabecera" de la clase está limpia, NO 'implements'
public class MainActivity extends AppCompatActivity {

    private List<Book> bookList;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private FloatingActionButton fabAdd;

    // 3. DEFINIMOS LOS LISTENERS COMO VARIABLES (CAMPOS)
    private BookAdapter.OnItemClickListener listenerClick;
    private BookAdapter.OnItemLongClickListener listenerLongClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- Referencias (como ya tenías) ---
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
        bookList = new ArrayList<>();
        loadSampleData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 4. INICIALIZAMOS LAS VARIABLES (aquí va la lógica)
        this.listenerClick = new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                // Lógica del click (cambiar estado)
                String currentState = book.getEstado();
                if (currentState.equalsIgnoreCase("Pendiente")) {
                    book.setEstado("Leyendo");
                } else if (currentState.equalsIgnoreCase("Leyendo")) {
                    book.setEstado("Leido");
                } else if (currentState.equalsIgnoreCase("Leido")) {
                    book.setEstado("Pendiente");
                }

                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Estado: " + book.getEstado(), Toast.LENGTH_SHORT).show();
            }
        };

        this.listenerLongClick = new BookAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Book book) {
                // Lógica del click largo (favorito)
                book.setFavorito(!book.isFavorito());

                adapter.notifyDataSetChanged();
                String favText = book.isFavorito() ? "Añadido a favoritos" : "Quitado de favoritos";
                Toast.makeText(MainActivity.this, favText, Toast.LENGTH_SHORT).show();
            }
        };


        // 5. CREAMOS EL ADAPTER (la llamada queda limpia)
        adapter = new BookAdapter(bookList, this, listenerClick, listenerLongClick);
        recyclerView.setAdapter(adapter);

        // --- Evento del FAB (como ya tenías) ---
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addBook.class);
                startActivity(intent);
            }
        });
    }

    // --- loadSampleData (como ya tenías) ---
    private void loadSampleData() {
        bookList.add(new Book("El Nombre del Viento", "Patrick Rothfuss", "Leido", 5, R.drawable.fantasy, true));
        bookList.add(new Book("Dune", "Frank Herbert", "Pendiente", 4, R.drawable.scifi, false));
        bookList.add(new Book("1984", "George Orwell", "Leyendo", 5, R.drawable.history, true));
        bookList.add(new Book("El misterio del solitario", "Jostein Gaarder", "Leido", 3, R.drawable.mistery, false));
        bookList.add(new Book("It", "Stephen King", "Pendiente", 4, R.drawable.terror, false));
    }

    // 6. FÍJATE: No hay métodos onImteClick aquí abajo
}