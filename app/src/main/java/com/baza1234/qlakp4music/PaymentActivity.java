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
import android.widget.Toast;
import java.util.Random;

public class PaymentActivity extends AppCompatActivity {

    boolean negative_number_active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

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

        // HOME BUTTON
        ImageView home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent libraryIntent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(libraryIntent);
            }
        });
        // PLAY BUTTON
        ImageView play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent playIntent = new Intent(PaymentActivity.this, PlayActivity.class);
                startActivity(playIntent);
            }
        });
        // LIBRARY BUTTON
        ImageView library = (ImageView) findViewById(R.id.library);
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent libraryIntent = new Intent(PaymentActivity.this, LibraryActivity.class);
                startActivity(libraryIntent);
            }
        });

        ImageView backbutton = (ImageView) findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent marketIntent = new Intent(PaymentActivity.this, MarketActivity.class);
                startActivity(marketIntent);
            }
        });

        ImageView buybutton = (ImageView) findViewById(R.id.buybutton);
        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mentionAPI();
            }
        });

        logoAnimateStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    /**
     *   Shows toast to inform about the payment methods and possibilities, as in project specifications:
     *   App must contain a Payment Activity. Student should find an external library or API that can be used in this situation.
     *   In the TextView of that activity, describe the library or API and how it can be used.
     */
    public void mentionAPI(){

        Context context = getApplicationContext();
        CharSequence text = "Now, the APP should use either PayU REST API or MPL Library from PayPal.";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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

    // Changes DP to PX for different devices.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}