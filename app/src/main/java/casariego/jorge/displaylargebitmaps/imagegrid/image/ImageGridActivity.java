package casariego.jorge.displaylargebitmaps.imagegrid.image;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import casariego.jorge.displaylargebitmaps.R;

public class ImageGridActivity extends AppCompatActivity implements ImageGridFragment.Callback{

    public static final String TAG = "ImageGridActivity";
    private ImageGridPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);
        //setupTranslucentStatusBar();

        ImageGridFragment imageGridFragment = (ImageGridFragment) getSupportFragmentManager()
                .findFragmentById(R.id.grid_container);

        if(imageGridFragment == null){
            imageGridFragment = ImageGridFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.grid_container, imageGridFragment)
                    .commit();
        }

        ImageGridInteractor imageGridInteractor = new ImageGridInteractor(getApplicationContext());
        mPresenter = new ImageGridPresenter(imageGridFragment, imageGridInteractor);

    }

    private void setupTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
