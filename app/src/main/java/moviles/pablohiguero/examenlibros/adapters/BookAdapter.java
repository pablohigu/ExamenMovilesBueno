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

import io.realm.RealmResults;
import moviles.pablohiguero.examenlibros.R;
import moviles.pablohiguero.examenlibros.model.Book;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookDataHolder> {
    private RealmResults<Book> bookList;
    private Context context;

    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    public interface OnItemClickListener {
        void onItemClick(int bookId);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int bookId);
    }
    public BookAdapter(RealmResults<Book> bookList, Context context, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.bookList = bookList;
        this.context = context;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public BookDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookDataHolder holder, int position) {
        holder.bindData(bookList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
    public class BookDataHolder extends RecyclerView.ViewHolder {

        ImageView ivGenre, ivFavorite;
        TextView tvTitle, tvAuthor, tvState;
        List<ImageView> stars;

        public BookDataHolder(@NonNull View itemView) {
            super(itemView);
            ivGenre = itemView.findViewById(R.id.ivGenre);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvState = itemView.findViewById(R.id.tvState);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
            stars = new ArrayList<>();
            stars.add(itemView.findViewById(R.id.ivStar1));
            stars.add(itemView.findViewById(R.id.ivStar2));
            stars.add(itemView.findViewById(R.id.ivStar3));
            stars.add(itemView.findViewById(R.id.ivStar4));
            stars.add(itemView.findViewById(R.id.ivStar5));
        }
        public void bindData(Book book) {
            tvTitle.setText(book.getNombre());
            tvAuthor.setText(book.getAutor());
            tvState.setText(book.getEstado());
            ivGenre.setImageResource(book.getImagen());
            tvState.setBackground(getEstadoBackground(book.getEstado(), context));
            if (book.isFavorito()) {
                ivFavorite.setImageResource(R.drawable.heart);
            } else {
                ivFavorite.setImageResource(R.drawable.heart_empty);
            }
            for (int i = 0; i < stars.size(); i++) {
                if (i < book.getRating()) {
                    stars.get(i).setImageResource(R.drawable.star);
                } else {
                    stars.get(i).setImageResource(R.drawable.star_empty);
                }
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        // Pasamos el ID del libro
                        clickListener.onItemClick(book.getId());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null) {
                        longClickListener.onItemLongClick(book.getId());
                        return true;
                    }
                    return false;
                }
            });
        }
        private Drawable getEstadoBackground(String estado, Context context) {
            int drawableRes;
            switch (estado.toLowerCase()) {
                case "leido": case "leído":
                    drawableRes = R.color.read;
                    break;
                case "leyendo":
                    drawableRes = R.color.reading;
                    break;
                default:
                    drawableRes = R.color.pending;
                    break;
            }
            return ContextCompat.getDrawable(context, drawableRes);
        }
    }
}