package moviles.pablohiguero.examenlibros;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.slider.Slider;
import io.realm.Realm;
import moviles.pablohiguero.examenlibros.app.MyApplication;
import moviles.pablohiguero.examenlibros.model.Book;
// ----------------------------


public class addBook extends AppCompatActivity {

    private Realm realm;
    private EditText etTitle, etAuthor;
    private Spinner spStatus;
    private Slider sliderRating;
    private MaterialSwitch switchFavorite;
    private MaterialButtonToggleGroup groupCovers;
    private Button btnSave, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        realm = Realm.getDefaultInstance();
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        spStatus = findViewById(R.id.spStatus);
        sliderRating = findViewById(R.id.sliderRating);
        switchFavorite = findViewById(R.id.switchFavorite);
        groupCovers = findViewById(R.id.groupCovers);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Dar un valor por defecto al grupo de botones
        groupCovers.check(R.id.btnCoverFantasy);
    }
    private void saveBook() {
        // Recoger datos
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "El título y el autor son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        // Lógica para el Spinner
        String state = spStatus.getSelectedItem().toString();

        // Lógica para el Slider
        int rating = (int) sliderRating.getValue();

        // Lógica para el MaterialSwitch
        boolean isFavorite = switchFavorite.isChecked();

        // Lógica para el MaterialButtonToggleGroup
        int checkedButtonId = groupCovers.getCheckedButtonId();
        int genreImage = getGenreImage(checkedButtonId); // Helper para la imagen

        // He probado esto, para comprobar el porque me dio fallo en el ejercicio de clase
        // y al hacerlo de esta manera, con el createObject y luego asignando las propiedades, me va Perfectamente
        realm.executeTransaction(r -> {
            // Crear el objeto con el id Generado y realm.CreateObject
            Book book = realm.createObject(Book.class, MyApplication.getNextBookId());
            // Seteo el resto de propiedades
            book.setNombre(title);
            book.setAutor(author);
            book.setEstado(state);
            book.setRating(rating);
            book.setFavorito(isFavorite);
            book.setImagen(genreImage);
        });
        finish(); // cierra el activity
    }

    // Helper para la imagen
    // Recibe el ID del botón pulsado
    private int getGenreImage(int buttonId) {
        // Uso if/else if porque los IDs de R no son "constantes" para un switch
        if (buttonId == R.id.btnCoverFantasy) {
            return R.drawable.fantasy;
        } else if (buttonId == R.id.btnCoverScifi) {
            return R.drawable.scifi;
        } else if (buttonId == R.id.btnCoverYA) {
            return R.drawable.ya;
        } else if (buttonId == R.id.btnCoverHistory) {
            return R.drawable.history;
        } else if (buttonId == R.id.btnCoverTerror) {
            return R.drawable.terror;
        } else if (buttonId == R.id.btnCoverMistery) {
            return R.drawable.mistery;
        } else {
            return R.drawable.fantasy; // Por defecto
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