package com.abiramee.gradientview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private GradientView mGradientView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //set backgroundImage by url with placeHolder
        mGradientView.setBackgroundImageURL("https://firebasestorage.googleapis.com/v0/b/pooka-166905.appspot.com/o/1.jpg?alt=media&token=e8a8b346-4838-466c-b515-a0cd3f68df8a",
                R.drawable.background);

        //set radius
        //mGradientView.setCornerRadius(50);

        //set orientation
        mGradientView.setOrientation(Orientention.BL_TR);

        //setGradientColors
        //mGradientView.setGradeintColors("#fff", "#fff", "#fff");

        //set background image by drawable
        //mGradientView.setBackgroundImageDrawable();

    }

    private void init() {
        mGradientView = findViewById(R.id.view_gradient);
    }
}
