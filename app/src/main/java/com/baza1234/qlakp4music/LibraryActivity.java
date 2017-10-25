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
import java.util.ArrayList;
import java.util.Random;

public class LibraryActivity extends AppCompatActivity {

    boolean first_button = true;
    boolean loop_current_track = false;
    boolean negative_number_active = false;
    boolean randomize_track = false;

    int current_track = 0;

    ArrayList<Track> tracks = new ArrayList<Track>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        logoAnimateStart();

        // Create an ArrayList of Track objects.
        tracks.add(new Track("Artist Name", "Track Name", R.drawable.tabtemplate, R.drawable.testcover, R.raw.letitbesolo, "year", "genre", "who", "from")); // Default Template Track Index 0.
        tracks.add(new Track("Pink Floyd", "Comfortably Numb - Solo 1", R.drawable.tabcomfortablynumb1, R.drawable.pink_floyd_the_wall, R.raw.comfnumbsolo, "23 June 1980", "Progressive Rock, Art Rock", "Roger Waters, David Gilmour, Nick Mason, Richard Wright", "London, England"));
        tracks.add(new Track("Pink Floyd", "Wish You Were Here", R.drawable.tabwishyouwerehere, R.drawable.pink_floyd_wish, R.raw.wishsolo, "12 September 1975", "Progressive Rock, Art Rock", "Roger Waters, David Gilmour, Nick Mason, Richard Wright", "London, England"));
        tracks.add(new Track("The Beatles", "Let It Be", R.drawable.tabletitbe, R.drawable.beatles_let_it_be, R.raw.letitbesolo, "8 May 1970", "Rock, Rhytm and Blues", "John Lennon, Paul McCartney, George Harrison, Ringo Starr", "Liverpool, England"));
        tracks.add(new Track("Guns'n'Roses", "Knockin On Heavens Door - Solo 1", R.drawable.tabknockinheaven1, R.drawable.gnr_use_your_illusion, R.raw.knockindoorssolo1, "1992", "Hard Rock, Blues Rock", "William Rose Jr., Saul Hudson, Jeffrey Isbell, Michael McKagan, Matt Sorum, Dizzy Reed", "Los Angeles, California"));
        tracks.add(new Track("Guns'n'Roses", "Knockin On Heavens Door - Solo 2", R.drawable.tabknockinheaven2, R.drawable.gnr_use_your_illusion, R.raw.knockindoorssolo2, "1992", "Hard Rock, Blues Rock", "William Rose Jr., Saul Hudson, Jeffrey Isbell, Michael McKagan, Matt Sorum, Dizzy Reed", "Los Angeles, California"));

        // HOME BUTTON
        ImageView home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent libraryIntent = new Intent(LibraryActivity.this, MainActivity.class);
                startActivity(libraryIntent);
            }
        });
        // PLAY BUTTON
        ImageView play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent playIntent = new Intent(LibraryActivity.this, PlayActivity.class);
                startActivity(playIntent);
            }
        });
        // MARKET BUTTON
        ImageView market = (ImageView) findViewById(R.id.market);
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent marketIntent = new Intent(LibraryActivity.this, MarketActivity.class);
                startActivity(marketIntent);
            }
        });

        // LEFT BAR MAIN BUTTON
        ImageView leftbar = (ImageView) findViewById(R.id.leftbar);
        leftbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftBarClick();
                previousSong();
            }
        });
        // RIGHT BAR MAIN BUTTON
        ImageView rightbar = (ImageView) findViewById(R.id.rightbar);
        rightbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightBarClick();
                nextSong();
            }
        });

        // PLAY BUTTON
        ImageView playbutton = (ImageView) findViewById(R.id.playtrack);
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playSong(current_track);

            }
        });

        randomizeTrack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
    /**
     *  Takes the random fact based on the current track.
     *  3 facts per track in the strings.xml
     */
    public void newFact(){

        TextView fact = (TextView) findViewById(R.id.fact);
        int nextfact = getResources().getIdentifier("fact" + current_track + "_" + randomizeNumbers(1, 3, 0), "string", this.getPackageName());
        fact.setText(nextfact);
    }

    /**
     *  Sets the random track when entering library for the first time.
     */
    public void randomizeTrack(){
        current_track = randomizeNumbers(1, tracks.size()-1, 0);

        int coverId = 0;

        String trackname = "";
        String artist = "";
        String trackinfo = "";

        // Changes the Artist.
        TextView titletext = (TextView) findViewById(R.id.titletext);
        artist = getTrack(current_track).getArtistName();
        titletext.setText(artist);

        // Changes the info about the solo - Year, Genre, From, Artist.
        TextView songinfo = (TextView) findViewById(R.id.songinfo);
        trackinfo = getTrack(current_track).getYear() + " \n" + getTrack(current_track).getGenre() + " \n" + getTrack(current_track).getFrom() + " \n" + getTrack(current_track).getWho() ;
        songinfo.setText(trackinfo);

        // Changes the name of the song.
        TextView songtitle = (TextView) findViewById(R.id.songtitle);
        trackname = getTrack(current_track).getTrackName();
        songtitle.setText(trackname);

        // Changes the album cover.
        ImageView albumcover = (ImageView) findViewById(R.id.albumcover);
        coverId = getTrack(current_track).getCoverResourceID();
        albumcover.setImageResource(coverId);

        newFact();
    }

    /**
     *   Gets the Track index.
     */
    public Track getTrack(int index){
        return tracks.get(index);
    }

    /**
     *  Sets the next song on the screen.
     *  Does the animation.
     */
    public void nextSong(){

        current_track = current_track + 1;

        if(current_track == tracks.size()) {
            loop_current_track = true;
        }

        if(randomize_track == true){
            current_track = randomizeNumbers(1, tracks.size()-1, 0);
            randomize_track = false;
        }

        RelativeLayout downmenu = (RelativeLayout) findViewById(R.id.downmenu);

        TranslateAnimation animation = new TranslateAnimation(0, Math.round(pxFromDp(this, 1400)), 0, 0);    //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(400);  // animation duration
        downmenu.startAnimation(animation);  // start animation

        animation.setFillAfter(true);

        if(first_button == false){
            first_button = true;
        } else {
            first_button = false;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                if(loop_current_track == true){
                    current_track = 1;
                    loop_current_track = false;
                }

                // Moving Layout.
                RelativeLayout downmenu = (RelativeLayout) findViewById(R.id.downmenu);

                int coverId = 0;

                String trackname = "";
                String artist = "";
                String trackinfo = "";

                // Changes the Artist.
                TextView titletext = (TextView) findViewById(R.id.titletext);
                artist = getTrack(current_track).getArtistName();
                titletext.setText(artist);

                // Changes the info about the solo - Year, Genre, From, Artist.
                TextView songinfo = (TextView) findViewById(R.id.songinfo);
                trackinfo = getTrack(current_track).getYear() + " \n" + getTrack(current_track).getGenre() + " \n" + getTrack(current_track).getFrom() + " \n" + getTrack(current_track).getWho() ;
                songinfo.setText(trackinfo);

                // Changes the name of the song.
                TextView songtitle = (TextView) findViewById(R.id.songtitle);
                trackname = getTrack(current_track).getTrackName();
                songtitle.setText(trackname);

                // Changes the album cover.
                ImageView albumcover = (ImageView) findViewById(R.id.albumcover);
                coverId = getTrack(current_track).getCoverResourceID();
                albumcover.setImageResource(coverId);

                newFact();

                // Changes Image of the button.
                ImageView titlebutton = (ImageView) findViewById(R.id.titlebutton);
                if(first_button == false){
                    titlebutton.setImageResource(R.drawable.songinfobutton2);
                }
                if(first_button == true){
                    titlebutton.setImageResource(R.drawable.songinfobutton1);
                }
                // New View comes from the left side of the screen.
                TranslateAnimation animation = new TranslateAnimation(Math.round(pxFromDp(getApplicationContext(), -500)), 0, 0, 0);    //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation.setDuration(400);
                downmenu.startAnimation(animation);
            }
        }, 200);
    }

    /**
     *  Sets the previous song on the screen.
     *  Does the animation.
     */
    public void previousSong(){

        current_track = current_track - 1;

        if(current_track == 0) {
            loop_current_track = true;
        }

        if(randomize_track == true){
            current_track = randomizeNumbers(1, tracks.size()-1, 0);
            randomize_track = false;
        }

        RelativeLayout downmenu = (RelativeLayout) findViewById(R.id.downmenu);

        TranslateAnimation animation = new TranslateAnimation(0, Math.round(pxFromDp(this, -1400)), 0, 0);    //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(400);  // animation duration
        downmenu.startAnimation(animation);  // start animation

        animation.setFillAfter(true);

        if(first_button == false){
            first_button = true;
        } else {
            first_button = false;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                if(loop_current_track == true){
                    current_track = tracks.size() - 1;
                    loop_current_track = false;
                }

                // Moving Layout.
                RelativeLayout downmenu = (RelativeLayout) findViewById(R.id.downmenu);

                int coverId = 0;

                String trackname = "";
                String artist = "";
                String trackinfo = "";

                // Changes the Artist.
                TextView titletext = (TextView) findViewById(R.id.titletext);
                artist = getTrack(current_track).getArtistName();
                titletext.setText(artist);

                // Changes the info about the solo - Year, Genre, From, Artist.
                TextView songinfo = (TextView) findViewById(R.id.songinfo);
                trackinfo = getTrack(current_track).getYear() + " \n" + getTrack(current_track).getGenre() + " \n" + getTrack(current_track).getFrom() + " \n" + getTrack(current_track).getWho() ;
                songinfo.setText(trackinfo);

                // Changes the name of the song.
                TextView songtitle = (TextView) findViewById(R.id.songtitle);
                trackname = getTrack(current_track).getTrackName();
                songtitle.setText(trackname);

                // Changes the album cover.
                ImageView albumcover = (ImageView) findViewById(R.id.albumcover);
                coverId = getTrack(current_track).getCoverResourceID();
                albumcover.setImageResource(coverId);

                newFact();

                // Changes Image of the button.
                ImageView titlebutton = (ImageView) findViewById(R.id.titlebutton);
                if(first_button == false){
                    titlebutton.setImageResource(R.drawable.songinfobutton2);
                }
                if(first_button == true){
                    titlebutton.setImageResource(R.drawable.songinfobutton1);
                }
                // New View comes from the left side of the screen.
                TranslateAnimation animation = new TranslateAnimation(Math.round(pxFromDp(getApplicationContext(), 500)), 0, 0, 0);    //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation.setDuration(400);
                downmenu.startAnimation(animation);
            }
        }, 200);
    }

    /**
     *  Opens selected track in the Player.
     */
    public void playSong(int trackId){

        Globals songfromlibrary = Globals.getInstance();
        songfromlibrary.setData(trackId);

        Intent playIntent = new Intent(LibraryActivity.this, PlayActivity.class);
        startActivity(playIntent);
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

    // Changes DP to PX for different devices.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}