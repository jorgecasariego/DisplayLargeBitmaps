package casariego.jorge.displaylargebitmaps.imagegrid.image;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import casariego.jorge.displaylargebitmaps.imagegrid.Images;
import casariego.jorge.displaylargebitmaps.imagegrid.util.ImageFetcher;
import casariego.jorge.displaylargebitmaps.imagegrid.util.RecyclingImageView;

/**
 * Created by jorgecasariego on 27/9/16.
 */

public class ImageGridAdapter extends BaseAdapter{

    private final Context mContext;
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private int mActionBarHeight = 0;
    private GridView.LayoutParams mImageViewLayoutParams;
    private ImageFetcher mImageFetcher;


    public ImageGridAdapter(Context mContext, ImageFetcher mImageFetcher) {
        super();
        this.mContext = mContext;
        this.mImageFetcher = mImageFetcher;

        mImageViewLayoutParams = new GridView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //Calculamos la altura del ActionBar
        TypedValue tv = new TypedValue();
        if(mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
            mActionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, mContext.getResources().getDisplayMetrics());
        }

    }

    @Override
    public int getCount() {
        // If columns have yet to be determined, return no items
        if(getNumColumns() == 0){
            return 0;
        }

        // Size + number of columns for top empty row
        return Images.imageThumbUrls.length + mNumColumns;

    }

    @Override
    public Object getItem(int position) {
        return position < mNumColumns ? null : Images.imageThumbUrls[position - mNumColumns];
    }

    @Override
    public long getItemId(int position) {
        return position < mNumColumns ? 0 : position - mNumColumns;
    }

    @Override
    public int getViewTypeCount() {
        // Two types of views, the normal ImageView and the top row of empty views
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (position < mNumColumns) ? 1 : 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //BEGIN_INCLUDE(load_gridview_item)
        // First check if this is the top row
        if(position < mNumColumns) {
            if(view == null){
                view = new View(mContext);
            }

            // Set empty view with height of ActionBar
            view.setLayoutParams(new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return view;
        }

        // Now handle the main ImageView thumbnails
        ImageView imageView;
        if(view == null) { // if it's not recycled, instantiate and initialize
            imageView = new RecyclingImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(mImageViewLayoutParams);
        } else {
            imageView = (ImageView) view;
        }

        // Check the height matches our calculated column width
        if(imageView.getLayoutParams().height != mItemHeight){
            imageView.setLayoutParams(mImageViewLayoutParams);
        }

        // Finally load the image asynchronously into the ImageView, this also takes care of
        // setting a placeholder image while the background thread runs
        mImageFetcher.loadImage(Images.imageThumbUrls[position - mNumColumns], imageView);
        return imageView;
        //END_INCLUDE(load_gridview_item)
    }

    /**
     * Sets the item height. Useful for when we know the column width so the height can be set
     * to match.
     *
     * @param height
     */
    public void setmItemHeight(int height){
        if(height == mItemHeight) {
            return;
        }

        mItemHeight = height;
        mImageViewLayoutParams = new GridView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageFetcher.setImageSize(height);
        notifyDataSetChanged();
    }

    public void setmNumColumns(int numColumns) {
        mNumColumns = numColumns;
    }

    public int getNumColumns() {
        return mNumColumns;
    }
}
