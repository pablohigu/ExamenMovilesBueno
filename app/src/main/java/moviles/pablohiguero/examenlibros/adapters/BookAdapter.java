package moviles.pablohiguero.examenlibros.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import moviles.pablohiguero.examenlibros.R;
import moviles.pablohiguero.examenlibros.model.Book;

// 1. Estructura idéntica a tu ElementAdapter
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookDataHolder> {

    private List<Book> bookList;
    private Context context;

    // 2. Variables para guardar los listeners que nos pasen
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    // 3. Definición de las interfaces
    //    (Pasan el objeto Book ya que no usamos IDs)
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Book book);
    }

    // 4. Constructor idéntico al de tu referencia
    public BookAdapter(List<Book> bookList, Context context, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.bookList = bookList;
        this.context = context;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public BookDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 5. Inflamos tu layout 'item_book.xml'
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookDataHolder holder, int position) {
        // 6. Usamos el patrón bindData, "tal cual"
        holder.bindData(bookList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    // --- VIEWHOLDER (CLASE INTERNA) ---
    // 7. El ViewHolder con el método bindData
    public class BookDataHolder extends RecyclerView.ViewHolder {

        // Vistas de tu item_book.xml
        ImageView ivGenre;
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvState;
        ImageView ivFavorite;
        List<ImageView> stars; // Lista para las estrellas

        public BookDataHolder(@NonNull View itemView) {
            super(itemView);

            // Referenciamos las vistas
            ivGenre = itemView.findViewById(R.id.ivGenre);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvState = itemView.findViewById(R.id.tvState);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);

            // Inicializamos la lista de estrellas
            stars = new ArrayList<>();
            stars.add(itemView.findViewById(R.id.ivStar1));
            stars.add(itemView.findViewById(R.id.ivStar2));
            stars.add(itemView.findViewById(R.id.ivStar3));
            stars.add(itemView.findViewById(R.id.ivStar4));
            stars.add(itemView.findViewById(R.id.ivStar5));
        }

        // 8. MÉTODO BINDDATA "TAL CUAL"
        // Aquí rellenamos la vista y asignamos listeners
        public void bindData(Book book) {

            // --- Rellenar Vistas ---
            tvTitle.setText(book.getNombre());
            tvAuthor.setText(book.getAutor());
            tvState.setText(book.getEstado());
            ivGenre.setImageResource(book.getImagen());

            // --- Lógica Condicional ---

            // Color del estado (usando tus colores de colors.xml)
            tvState.setBackground(getEstadoBackground(book.getEstado(), context));

            // Favorito (Corazón)
            if (book.isFavorito()) {
                ivFavorite.setImageResource(R.drawable.heart);
            } else {
                ivFavorite.setImageResource(R.drawable.heart_empty);
            }

            // Estrellas (Rating)
            for (int i = 0; i < stars.size(); i++) {
                if (i < book.getRating()) {
                    stars.get(i).setImageResource(R.drawable.star);
                } else {
                    stars.get(i).setImageResource(R.drawable.star_empty);
                }
            }

            // --- Asignar Listeners "tal cual" ---
            // Los listeners se asignan al itemView

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemClick(book); // Pasamos el objeto Book
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null) {
                        longClickListener.onItemLongClick(book); // Pasamos el objeto Book
                        return true; // Consumimos el evento
                    }
                    return false;
                }
            });
        }

        // Función de ayuda para poner el color de fondo del estado
        private Drawable getEstadoBackground(String estado, Context context) {
            int drawableRes;
            switch (estado.toLowerCase()) {
                case "leido":
                case "leído":
                    drawableRes = R.color.read;
                    break;
                case "leyendo":
                    drawableRes = R.color.reading;
                    break;
                default: // "Pendiente"
                    drawableRes = R.color.pending;
                    break;
            }
            return ContextCompat.getDrawable(context, drawableRes);
        }
    }
}