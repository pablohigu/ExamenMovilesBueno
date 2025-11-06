package moviles.pablohiguero.examenlibros.utils;

import java.util.ArrayList;
import java.util.Collections;
import moviles.pablohiguero.examenlibros.R;
import moviles.pablohiguero.examenlibros.model.Book;

public class Utils {

    public static ArrayList<Book> getSampleBooks() {
        ArrayList<Book> list = new ArrayList<>();
        list.add(new Book("El nombre del viento", "Patrick Rothfuss", "Leido", 5, R.drawable.fantasy, true));
        list.add(new Book("La comunidad del anillo", "J.R.R. Tolkien", "Leyendo", 4, R.drawable.fantasy, false));
        list.add(new Book("Juego de tronos", "George R.R. Martin", "Pendiente", 0, R.drawable.fantasy, false));
        list.add(new Book("Dune", "Frank Herbert", "Leido", 5, R.drawable.scifi, true));
        list.add(new Book("Neuromante", "William Gibson", "Leyendo", 4, R.drawable.scifi, false));
        list.add(new Book("Fundación", "Isaac Asimov", "Pendiente", 0, R.drawable.scifi, false));
        list.add(new Book("Los juegos del hambre", "Suzanne Collins", "Leido", 5, R.drawable.ya, true));
        list.add(new Book("Bajo la misma estrella", "John Green", "Leyendo", 4, R.drawable.ya, true));
        list.add(new Book("Divergente", "Veronica Roth", "Pendiente", 0, R.drawable.ya, false));
        list.add(new Book("Los pilares de la Tierra", "Ken Follett", "Leido", 5, R.drawable.history, true));
        list.add(new Book("La catedral del mar", "Ildefonso Falcones", "Leyendo", 4, R.drawable.history, false));
        list.add(new Book("El médico", "Noah Gordon", "Pendiente", 0, R.drawable.history, false));
        list.add(new Book("It", "Stephen King", "Leido", 5, R.drawable.terror, true));
        list.add(new Book("Drácula", "Bram Stoker", "Leyendo", 4, R.drawable.terror, false));
        list.add(new Book("El resplandor", "Stephen King", "Pendiente", 0, R.drawable.terror, false));
        list.add(new Book("Asesinato en el Orient Express", "Agatha Christie", "Leido", 5, R.drawable.mistery, true));
        list.add(new Book("El código Da Vinci", "Dan Brown", "Leyendo", 4, R.drawable.mistery, true));
        list.add(new Book("La chica del tren", "Paula Hawkins", "Pendiente", 0, R.drawable.mistery, false));
        // He cambiado los nulls a ceros
        Collections.shuffle(list);
        return list;
    }
}