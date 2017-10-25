package com.baza1234.qlakp4music;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // PLAY=1 ; LIBRARY=2 ; MARKET=3 ; ABOUT=4
    int which_button = 0;

    boolean negative_number_active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoAnimateStart();
        newHint();

        // PLAY MAIN BUTTON
        ImageView play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                which_button = 1;
                buttonPressed(which_button);

                // Start delayed to highlight the button.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent playIntent = new Intent(MainActivity.this, PlayActivity.class);
                        startActivity(playIntent);
                    }
                }, 300);
            }
        });

        // LIBRARY MAIN BUTTON
        ImageView library = (ImageView) findViewById(R.id.library);
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                which_button = 2;
                buttonPressed(which_button);

                // Start delayed to highlight the button.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent libraryIntent = new Intent(MainActivity.this, LibraryActivity.class);
                        startActivity(libraryIntent);
                    }
                }, 300);
            }
        });

        // MARKET MAIN BUTTON
        ImageView market = (ImageView) findViewById(R.id.market);
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                which_button = 3;
                buttonPressed(which_button);

                // Start delayed to highlight the button.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent marketIntent = new Intent(MainActivity.this, MarketActivity.class);
                        startActivity(marketIntent);
                    }
                }, 300);
            }
        });

        // ABOUT MAIN BUTTON
        ImageView about = (ImageView) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                which_button = 4;
                buttonPressed(which_button);

                // Start delayed to highlight the button.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(aboutIntent);
                    }
                }, 300);
            }
        });

        // LEFT BAR MAIN BUTTON
        ImageView leftbar = (ImageView) findViewById(R.id.leftbar);
        leftbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftBarClick();
            }
        });
        // RIGHT BAR MAIN BUTTON
        ImageView rightbar = (ImageView) findViewById(R.id.rightbar);
        rightbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightBarClick();
            }
        });

        // HINT VIEW MAIN BUTTON (on lower bar)
        LinearLayout hintview = (LinearLayout) findViewById(R.id.hintview);
        hintview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintViewClick();
                lowBarClick();
                // Delay in order to wait for the hint to disappear from the screen first.
                // 4500ms in total, the hint text changes in 3250 when off screen.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        newHint();
                    }
                }, 3250);
            }
        });
        // LOWER BAR MAIN BUTTON (with hint view)
        ImageView lowbar = (ImageView) findViewById(R.id.lowbar);
        lowbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowBarClick();
                hintViewClick();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        newHint();
                    }
                }, 3250);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    /**
     *   Random number generator for several methods, for example to get random Hint.
     *   @param min sets minimal random value to choose from.
     *   @param max sets max random value to choose from.
     *   @param without that number can not be chosen - program will reroll to get different random number.
     */
    public int randomizeNumbers(int min, int max, int without){
        Random randomNumber = new Random();
        int randomNum = randomNumber.nextInt((max - min) + 1) + min;
        // Rerolls the dice if that number was marked as not wanted.
        if(randomNum == without){
            while(randomNum == without) {
                randomNum = randomNumber.nextInt((max - min) + 1) + min;
            }
        }
        // Changes the output to be negative if needed.
        if(negative_number_active == true){
            randomNum = (randomNum * -1);
        }
        return randomNum;
    }

    /**
     *   Moves left bar a little when clicked on.
     */
    public void leftBarClick() {
        ImageView leftbar = (ImageView) findViewById(R.id.leftbar);
        negative_number_active = true;
        TranslateAnimation animation = new TranslateAnimation(0, randomizeNumbers(Math.round(pxFromDp(this, 5)), Math.round(pxFromDp(this, 32)), 0), 0, 0);     //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        negative_number_active = false;
        animation.setDuration(randomizeNumbers(200, 2200, 0));  // animation duration
        animation.setRepeatCount(randomizeNumbers(1, 3, 2));  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        leftbar.startAnimation(animation);  // start animation
    }
    /**
     *   Moves right bar a little when clicked on.
     */
    public void rightBarClick() {
        ImageView rightbar = (ImageView) findViewById(R.id.rightbar);
        TranslateAnimation animation = new TranslateAnimation(0, randomizeNumbers(Math.round(pxFromDp(this, 5)), Math.round(pxFromDp(this, 32)), 0), 0, 0);     //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(randomizeNumbers(200, 2200, 0));  // animation duration
        animation.setRepeatCount(randomizeNumbers(1, 3, 2));  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        rightbar.startAnimation(animation);  // start animation
    }

    /**
     *   Moves hint off the screen alongside with lowbar in order to change the hint for the new one.
     */
    public void hintViewClick() {
        LinearLayout hintview = (LinearLayout) findViewById(R.id.hintview);
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, Math.round(pxFromDp(this, 60)));     //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(4500);  // animation duration
        animation.setRepeatCount(1);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        hintview.startAnimation(animation);  // start animation
    }
    /**
     *   Chooses new Hint to display.
     */
    public void newHint(){
        TextView hint = (TextView) findViewById(R.id.hinttext);
        int nexthint = getResources().getIdentifier("hint"+randomizeNumbers(1, 8, 0), "string", this.getPackageName());
        hint.setText(nexthint);
    }
    /**
     *   Moves low bar off the screen slowly to reload new hint text in the hint view.
     */
    public void lowBarClick() {
        ImageView lowbar = (ImageView) findViewById(R.id.lowbar);
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, Math.round(pxFromDp(this, 60)));     //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(4500);  // animation duration
        animation.setRepeatCount(1);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        lowbar.startAnimation(animation);  // start animation
    }

    // Changes DP to PX for different devices.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    /**
     *   Starts the pulsing animation of the logo.
     */
    public void logoAnimateStart() {
        final AnimationDrawable logoAnimation = new AnimationDrawable();
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo01), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo02), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo03), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo04), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo05), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo06), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo07), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo08), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo09), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo10), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo11), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo12), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo13), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo14), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo15), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo16), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo17), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo18), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo19), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo20), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo21), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo22), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo23), 100);
        logoAnimation.addFrame(getResources().getDrawable(R.drawable.logo24), 800);

        ImageView logo = (ImageView) findViewById(R.id.logoanimation);
        logo.setBackgroundDrawable(logoAnimation);
        // Start delayed to overwrite onCreate ImageView
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                logoAnimation.start();
            }
        }, 200);
    }

    /**
     *   Highlights the pressed button.
     *   @param which_button checks which button was used: PLAY=1 ; LIBRARY=2 ; MARKET=3 ; ABOUT=4
     */
    public void buttonPressed(int which_button) {
        final AnimationDrawable buttonPress = new AnimationDrawable();
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton01), 5);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton02), 5);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton03), 5);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton04), 5);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton05), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton06), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton07), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton08), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton09), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton10), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton11), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton12), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton13), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton14), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton15), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton16), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton17), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton18), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton19), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton20), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton21), 10);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton22), 15);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton23), 25);
        buttonPress.addFrame(getResources().getDrawable(R.drawable.abutton00), 10);
        buttonPress.setOneShot(true);

        if(which_button == 1){
            ImageView playpress = (ImageView) findViewById(R.id.playpress);
            playpress.setBackgroundDrawable(buttonPress);
            buttonPress.start();
        }
        if(which_button == 2){
            ImageView librarypress = (ImageView) findViewById(R.id.librarypress);
            librarypress.setBackgroundDrawable(buttonPress);
            buttonPress.start();
        }
        if(which_button == 3){
            ImageView marketpress = (ImageView) findViewById(R.id.marketpress);
            marketpress.setBackgroundDrawable(buttonPress);
            buttonPress.start();
        }
        if(which_button == 4){
            ImageView aboutpress = (ImageView) findViewById(R.id.aboutpress);
            aboutpress.setBackgroundDrawable(buttonPress);
            buttonPress.start();
        }
    }
}