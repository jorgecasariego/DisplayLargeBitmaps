package casariego.jorge.displaylargebitmaps.imagegrid.interfaces;

/**
 * Created by jorgecasariego on 27/9/16.
 */

public interface BaseView<T> {
    // Crea un vinculo view-presenter para relacionar ambas instancias
    void setPresenter(T presenter);
}
