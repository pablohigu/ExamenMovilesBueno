package moviles.pablohiguero.examenlibros.model;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import moviles.pablohiguero.examenlibros.app.MyApplication;
public class Book extends RealmObject {
    @PrimaryKey
    private int id;
    private String nombre;
    private String autor;
    private String estado;
    private int rating;
    private int imagen;
    private boolean favorito;
    public Book() {
    }
    public Book(String nombre, String autor, String estado, int rating, int imagen, boolean favorito) {
        this.id = MyApplication.getNextBookId();
        this.nombre = nombre;
        this.autor = autor;
        this.estado = estado;
        this.rating = rating;
        this.imagen = imagen;
        this.favorito = favorito;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}