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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class MarketActivity extends AppCompatActivity {

    int current_track = 0;

    ArrayList<Track> tracks = new ArrayList<Track>();

    boolean negative_number_active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        // Create an ArrayList of Track objects.
        tracks.add(new Track("Artist Name", "Track Name", R.drawable.tabtemplate, R.drawable.testcover, R.raw.letitbesolo, "year", "genre", "who", "from")); // Default Template Track Index 0.
        tracks.add(new Track("Pink Floyd", "Comfortably Numb - Solo 1", R.drawable.tabcomfortablynumb1, R.drawable.pink_floyd_the_wall, R.raw.comfnumbsolo, "23 June 1980", "Progressive Rock, Art Rock, Psychedelic Rock", "Roger Waters, David Gilmour, Nick Mason, Richard Wright", "London, England"));
        tracks.add(new Track("Pink Floyd", "Wish You Were Here", R.drawable.tabwishyouwerehere, R.drawable.pink_floyd_wish, R.raw.wishsolo, "12 September 1975", "Progressive Rock, Art Rock, Psychedelic Rock", "Roger Waters, David Gilmour, Nick Mason, Richard Wright", "London, England"));
        tracks.add(new Track("The Beatles", "Let It Be", R.drawable.tabletitbe, R.drawable.beatles_let_it_be, R.raw.letitbesolo, "8 May 1970", "Rock, Rhytm and Blues", "John Lennon, Paul McCartney, George Harrison, Ringo Starr", "Liverpool, England"));
        tracks.add(new Track("Guns'n'Roses", "Knockin On Heavens Door - Solo 1", R.drawable.tabknockinheaven1, R.drawable.gnr_use_your_illusion, R.raw.knockindoorssolo1, "1992", "Hard Rock, Blues Rock", "William Bruce Rose Jr., Saul Hudson, Jeffrey Isbell, Michael McKagan, Matt Sorum, Dizzy Reed", "Los Angeles, California"));
        tracks.add(new Track("Guns'n'Roses", "Knockin On Heavens Door - Solo 2", R.drawable.tabknockinheaven2, R.drawable.gnr_use_your_illusion, R.raw.knockindoorssolo2, "1992", "Hard Rock, Blues Rock", "William Bruce Rose Jr., Saul Hudson, Jeffrey Isbell, Michael McKagan, Matt Sorum, Dizzy Reed", "Los Angeles, California"));

        // HOME BUTTON
        ImageView home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent libraryIntent = new Intent(MarketActivity.this, MainActivity.class);
                startActivity(libraryIntent);
            }
        });
        // PLAY BUTTON
        ImageView play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent playIntent = new Intent(MarketActivity.this, PlayActivity.class);
                startActivity(playIntent);
            }
        });
        // LIBRARY BUTTON
        ImageView library = (ImageView) findViewById(R.id.library);
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent libraryIntent = new Intent(MarketActivity.this, LibraryActivity.class);
                startActivity(libraryIntent);
            }
        });


        // LEFT BAR MAIN BUTTON
        ImageView leftbar = (ImageView) findViewById(R.id.leftbar);
        leftbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftBarClick();
                noMoreTracksAtMoment();
            }
        });
        // RIGHT BAR MAIN BUTTON
        ImageView rightbar = (ImageView) findViewById(R.id.rightbar);
        rightbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightBarClick();
                noMoreTracksAtMoment();
            }
        });

        // POSITIONS:
        RelativeLayout position1 = (RelativeLayout) findViewById(R.id.position1);
        position1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alreadyOwned();
            }
        });
        RelativeLayout position2 = (RelativeLayout) findViewById(R.id.position2);
        position2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alreadyOwned();
            }
        });
        RelativeLayout position3 = (RelativeLayout) findViewById(R.id.position3);
        position3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alreadyOwned();
            }
        });
        RelativeLayout position4 = (RelativeLayout) findViewById(R.id.position4);
        position4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alreadyOwned();
            }
        });
        RelativeLayout position5 = (RelativeLayout) findViewById(R.id.position5);
        position5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alreadyOwned();
            }
        });
        RelativeLayout position6 = (RelativeLayout) findViewById(R.id.position6);
        position6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToPayment();

                // Start delayed to highlight the button.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent paymentIntent = new Intent(MarketActivity.this, PaymentActivity.class);
                        startActivity(paymentIntent);
                    }
                }, 250);
            }
        });
        RelativeLayout position7 = (RelativeLayout) findViewById(R.id.position7);
        position7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sorryUnavailable();
            }
        });
        RelativeLayout position8 = (RelativeLayout) findViewById(R.id.position8);
        position8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sorryUnavailable();
            }
        });

        logoAnimateStart();

        setOwnedTracks();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    /**
     *   Moves both columns sideways in order to go to PaymentActivity.
     */
    public void goToPayment(){

        // Handles the Left wing.
        LinearLayout leftcolumn = (LinearLayout) findViewById(R.id.leftcolumn);
        TranslateAnimation leftanimation = new TranslateAnimation(0, -1400, 0, 0);    //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        leftanimation.setDuration(400);  // animation duration
        leftcolumn.startAnimation(leftanimation);  // start animation

        // Handles the Right wing.
        LinearLayout rightcolumn = (LinearLayout) findViewById(R.id.rightcolumn);
        TranslateAnimation rightanimation = new TranslateAnimation(0, 1400, 0, 0);    //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        rightanimation.setDuration(400);  // animation duration
        rightcolumn.startAnimation(rightanimation);  // start animation
    }

    /**
     *   Shows toast if the track is currently unavailable to buy, but in the shop.
     */
    public void sorryUnavailable(){

        Context context = getApplicationContext();
        CharSequence text = "This solo is currently unavailable. Sorry!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     *   Shows toast if user tries to buy the same track again.
     */
    public void alreadyOwned(){

        Context context = getApplicationContext();
        CharSequence text = "You already own that one!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    /**
     *   Shows toast if there are no more tracks in the market and can't switch pages.
     */
    public void noMoreTracksAtMoment(){

        Context context = getApplicationContext();
        CharSequence text = "No more tracks at the moment!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     *   Sets the tracks that you own on the screen.
     */
    public void setOwnedTracks(){

        for(int i=1; i<tracks.size(); i++){

            current_track = i;

            TextView marketpositionartist = (TextView) findViewById(getResources().getIdentifier("marketposition" + i + "artist", "id", this.getPackageName()));
            marketpositionartist.setText(getTrack(current_track).getArtistName());

            TextView marketpositionname = (TextView) findViewById(getResources().getIdentifier("marketposition" + i + "name", "id", this.getPackageName()));
            marketpositionname.setText(getTrack(current_track).getTrackName());

            ImageView marketpositioncover = (ImageView) findViewById(getResources().getIdentifier("marketposition" + i + "cover", "id", this.getPackageName()));
            marketpositioncover.setImageResource(getTrack(current_track).getCoverResourceID());
        }
    }

    /**
     *   Gets the Track index.
     */
    public Track getTrack(int index){
        return tracks.get(index);
    }

    /**
     *   Moves left bar a little when clicked on.
     */
    public void leftBarClick() {
        ImageView leftbar = (ImageView) findViewById(R.id.leftbar);
        negative_number_active = true;
        TranslateAnimation animation = new TranslateAnimation(0, randomizeNumbers(Math.round(pxFromDp(this, 30)), Math.round(pxFromDp(this, 35)), 0), 0, 0);    //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        negative_number_active = false;
        animation.setDuration(250);  // animation duration
        animation.setRepeatCount(1);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        leftbar.startAnimation(animation);  // start animation
    }
    /**
     *   Moves right bar a little when clicked on.
     */
    public void rightBarClick() {
        ImageView rightbar = (ImageView) findViewById(R.id.rightbar);
        TranslateAnimation animation = new TranslateAnimation(0, randomizeNumbers(Math.round(pxFromDp(this, 30)), Math.round(pxFromDp(this, 35)), 0), 0, 0);     //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(250);  // animation duration
        animation.setRepeatCount(1);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        rightbar.startAnimation(animation);  // start animation
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

    // Changes DP to PX for different devices.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}