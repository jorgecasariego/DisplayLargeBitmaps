package casariego.jorge.displaylargebitmaps.imagegrid.image;

/**
 * Created by jorgecasariego on 27/9/16.
 */

public class ImageGridPresenter implements ImageGridContract.Presenter, ImageGridInteractor.Callback{

    private final ImageGridContract.View mImageGridView;
    private ImageGridInteractor mImageGridInteractor;

    public ImageGridPresenter(ImageGridContract.View mImageGridView, ImageGridInteractor mImageGridInteractor) {
        this.mImageGridView = mImageGridView;
        mImageGridView.setPresenter(this);
        this.mImageGridInteractor = mImageGridInteractor;
    }

    @Override
    public void start() {

    }
}
