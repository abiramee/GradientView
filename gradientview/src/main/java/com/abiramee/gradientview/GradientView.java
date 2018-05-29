package com.abiramee.gradientview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.makeramen.roundedimageview.RoundedImageView;

public class GradientView extends RelativeLayout {
    private Context mContext;
    private AttributeSet mAttributeSet;
    private int mStyleAttr;
    private View mView;

    private RoundedImageView mImageViewBackground;
    private View mViewGradeint;

    private float mRadius;
    private int mPlaceHolder = 0;
    private String mOrientation;
    private String mStartColor;
    private String mCenterColor;
    private String mEndPoint;
    private String mLastImageURL;
    private Drawable mDrawableBackgroundImage;

    private GradientDrawable mGradientDrawable;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public GradientView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public GradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mAttributeSet = attrs;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public GradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mAttributeSet = attrs;
        this.mStyleAttr = defStyleAttr;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        this.mView = this;
        inflate(mContext, R.layout.layout_gradient_view, this);

        TypedArray arr = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.GradientView,
                mStyleAttr, 0);

        getDataFromUIComponent(arr);

        mImageViewBackground = findViewById(R.id.imageView_background);
        mViewGradeint = findViewById(R.id.view_gradient);

        setUpGradientDrawable();
        setUpRadius();

        mViewGradeint.setBackground(mGradientDrawable);
        setUpBackgroundImage();

        arr.recycle();
    }

    private void getDataFromUIComponent(TypedArray array) {
        mRadius = array.getDimension(R.styleable.GradientView_corner_radius, 0);
        mOrientation = array.getString(R.styleable.GradientView_orientation);
        mStartColor = array.getString(R.styleable.GradientView_start_color);
        mCenterColor = array.getString(R.styleable.GradientView_center_color);
        mEndPoint = array.getString(R.styleable.GradientView_end_color);
        mDrawableBackgroundImage = array.getDrawable(R.styleable.GradientView_background_image);
    }

    private void setUpGradientDrawable() {
        int[] colorNames;

        if (mCenterColor != null) {
            colorNames = new int[]{Color.parseColor(mStartColor), Color.parseColor(mCenterColor),
                    Color.parseColor(mEndPoint)};
        } else {
            colorNames = new int[]{Color.parseColor(mStartColor), Color.parseColor(mEndPoint)};
        }

        switch (mOrientation) {
            case "0":
                mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colorNames);
                break;
            case "1":
                mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TR_BL, colorNames);
                break;
            case "2":
                mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colorNames);
                break;
            case "3":
                mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BR_TL, colorNames);
                break;
            case "4":
                mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colorNames);
                break;
            case "5":
                mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, colorNames);
                break;
            case "6":
                mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colorNames);
                break;
            case "7":
                mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colorNames);
                break;
            default:
                mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colorNames);
                break;
        }
    }

    private void setUpRadius() {
        if (mRadius != 0) {
            mGradientDrawable.setCornerRadii(new float[]{mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius});
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setUpBackgroundImage() {
        mImageViewBackground.setImageDrawable(mDrawableBackgroundImage);
        mImageViewBackground.setCornerRadius(mRadius);
        mImageViewBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageViewBackground.setAlpha(0.5f);

        if (mLastImageURL != null) {
            if (mPlaceHolder != 0) {
                setBackgroundImageURL(mLastImageURL, mPlaceHolder);
            } else {
                setBackgroundImageURL(mLastImageURL);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setBackgroundImageDrawable(Drawable drawable) {
        mDrawableBackgroundImage = drawable;
        setUpBackgroundImage();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setCornerRadius(float radius) {
        mRadius = radius;
        setUpRadius();
        mViewGradeint.setBackground(mGradientDrawable);
        setUpBackgroundImage();
    }

    public void setGradeintColors(String startColor, String centerColor, String endColor) {
        mStartColor = startColor;
        mCenterColor = centerColor;
        mEndPoint = endColor;
        setUpGradientDrawable();
        setUpRadius();
        mImageViewBackground.setBackground(mGradientDrawable);
    }

    public void setGradeintColors(String startColor, String endColor) {
        mStartColor = startColor;
        mEndPoint = endColor;
        setUpGradientDrawable();
        setUpRadius();
        mImageViewBackground.setBackground(mGradientDrawable);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setOrientation(String orientation) {
        this.mOrientation = orientation;
        setUpGradientDrawable();
        setUpRadius();
        mViewGradeint.setBackground(mGradientDrawable);
    }

    public void setBackgroundImageURL(String imageURL, int placeHolder) {
        mLastImageURL = imageURL;
        mPlaceHolder = placeHolder;
        Glide.with(this)
                .load(imageURL)
                .apply(new RequestOptions()
                        .placeholder(placeHolder)
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontTransform())
                .into(mImageViewBackground);
        mImageViewBackground.setAlpha(0.5f);
        mImageViewBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void setBackgroundImageURL(String imageURL) {
        mLastImageURL = imageURL;
        mPlaceHolder = 0;
        Glide.with(this)
                .load(imageURL)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .dontTransform())
                .into(mImageViewBackground);
        mImageViewBackground.setAlpha(0.5f);
        mImageViewBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}
