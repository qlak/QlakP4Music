package com.baza1234.qlakp4music;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    int current_track = 0;

    private static final String LOG = "Log Help";

    ArrayList<Track> tracks = new ArrayList<Track>();

    boolean negative_number_active = false;

    private MediaPlayer mp;
    private SeekBar songprogressbar;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        songprogressbar = (SeekBar) findViewById(R.id.songprogressbar);
        songprogressbar.setOnSeekBarChangeListener(this);

        logoAnimateStart();

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
                if (mp.isPlaying()) {
                    try{
                        mp.reset();
                    }catch (NullPointerException e){
                        Log.e(LOG, "Music Player is empty.");
                    }
                }
                Intent libraryIntent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(libraryIntent);
            }
        });
        // LIBRARY BUTTON
        ImageView library = (ImageView) findViewById(R.id.library);
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    try{
                        mp.reset();
                    }catch (NullPointerException e){
                        Log.e(LOG, "Music Player is empty.");
                    }
                }
                Intent libraryIntent = new Intent(PlayActivity.this, LibraryActivity.class);
                startActivity(libraryIntent);
            }
        });
        // MARKET BUTTON
        ImageView market = (ImageView) findViewById(R.id.market);
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    try{
                        mp.reset();
                    }catch (NullPointerException e){
                        Log.e(LOG, "Music Player is empty.");
                    }
                }
                Intent marketIntent = new Intent(PlayActivity.this, MarketActivity.class);
                startActivity(marketIntent);
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

        // PLAY BUTTON
        ImageView playbutton = (ImageView) findViewById(R.id.playbutton);
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playCurrentSolo();
            }
        });

        // PAUSE BUTTON
        ImageView pausebutton = (ImageView) findViewById(R.id.pausebutton);
        pausebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pauseCurrentSolo();
            }
        });

        // Checks if the user selected particular song from library.
        Globals songfromlibrary = Globals.getInstance();
        int checklibrary = songfromlibrary.getData();
        if (checklibrary != 0) {
            current_track = checklibrary;
            setSong(current_track);
            createSong();
        } else {
            setRandomSong();
            createSong();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
    @Override
    public void onStop() {
        super.onStop();
        try{
            mp.reset();
        }catch (NullPointerException e){
            Log.e(LOG, "Music Player is empty.");
        }
    }

    /**
     * Prepares song ID in MediaPlayer.
     */
    public void createSong() {
        int audioTrackId = getTrack(current_track).getAudioResource();
        mp = MediaPlayer.create(PlayActivity.this, audioTrackId);
    }

    /**
     * Stops solo after pressing PAUSE button.
     */
    public void pauseCurrentSolo() {
        if (mp.isPlaying()) {
            mp.pause();
        } else if (!mp.isPlaying()) {
            mp.start();
            updateProgressBar();
        }
    }

    /**
     * Plays solo after pressing PLAY button.
     */
    public void playCurrentSolo() {
        if (mp.isPlaying()) {
            try{
                mp.reset();
            }catch (NullPointerException e){
                Log.e(LOG, "Music Player is empty.");
            }
            createSong();
            mp.start();
            updateProgressBar();
        }else{
            createSong();
            mp.start();
            updateProgressBar();
        }
    }

    /**
     * Update timer on the SeekBar.
     */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread.
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (mp.isPlaying()) {
                long totalDuration = mp.getDuration();
                long currentDuration = mp.getCurrentPosition();

                // Updating progress bar
                int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
                //Log.d("Progress", ""+progress);
                songprogressbar.setProgress(progress);

                // Running this thread after 100 milliseconds
                mHandler.postDelayed(this, 100);
            }
        }
    };

    /**
     * Function to get Progress percentage.
     *
     * @param currentDuration shows the current time of the track.
     * @param totalDuration   shows the total time of the track.
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Override the onProgressChanged.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
    }

    /**
     * When user starts moving the progress handler.
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress hanlder.
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mp.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    /**
     * Function to change progress to timer
     * returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    /**
     * Gets the Track index.
     */
    public Track getTrack(int index) {
        return tracks.get(index);
    }

    /**
     * Sets random Song and Tab.
     */
    public void setRandomSong() {

        int randomTrackNumber = 0;

        int coverId = 0;
        int tabId = 0;
        String name = "";

        TextView artist = (TextView) findViewById(R.id.name);
        ImageView cover = (ImageView) findViewById(R.id.albumcover);
        ImageView tab = (ImageView) findViewById(R.id.tab);

        randomTrackNumber = randomizeNumbers(1, tracks.size() - 1, 0);
        current_track = randomTrackNumber;

        name = getTrack(randomTrackNumber).getArtistName() + "-" + getTrack(randomTrackNumber).getTrackName();
        artist.setText(name);

        coverId = getTrack(randomTrackNumber).getCoverResourceID();
        cover.setImageResource(coverId);

        tabId = getTrack(randomTrackNumber).getTabResourceId();
        tab.setImageResource(tabId);

        createSong();
    }

    /**
     * Sets particular Song and Tab from the library.
     */
    public void setSong(int tracknumber) {
        int coverId = 0;
        int tabId = 0;
        String name = "";

        TextView artist = (TextView) findViewById(R.id.name);
        ImageView cover = (ImageView) findViewById(R.id.albumcover);
        ImageView tab = (ImageView) findViewById(R.id.tab);

        name = getTrack(tracknumber).getArtistName() + "-" + getTrack(tracknumber).getTrackName();
        artist.setText(name);

        coverId = getTrack(tracknumber).getCoverResourceID();
        cover.setImageResource(coverId);

        tabId = getTrack(tracknumber).getTabResourceId();
        tab.setImageResource(tabId);
    }

    /**
     * Sets next track.
     */
    public void nextSong() {
        int coverId = 0;
        int tabId = 0;
        String name = "";

        TextView artist = (TextView) findViewById(R.id.name);
        ImageView cover = (ImageView) findViewById(R.id.albumcover);
        ImageView tab = (ImageView) findViewById(R.id.tab);

        if (current_track == tracks.size() - 1) {
            current_track = 1;

            name = getTrack(current_track).getArtistName() + "-" + getTrack(current_track).getTrackName();
            artist.setText(name);

            coverId = getTrack(current_track).getCoverResourceID();
            cover.setImageResource(coverId);

            tabId = getTrack(current_track).getTabResourceId();
            tab.setImageResource(tabId);

            if (mp.isPlaying()) {
                try{
                    mp.reset();
                }catch (NullPointerException e){
                    Log.e(LOG, "Music Player is empty.");
                }
            }

            createSong();

        } else {
            current_track = current_track + 1;
        }
        name = getTrack(current_track).getArtistName() + "-" + getTrack(current_track).getTrackName();
        artist.setText(name);

        coverId = getTrack(current_track).getCoverResourceID();
        cover.setImageResource(coverId);

        tabId = getTrack(current_track).getTabResourceId();
        tab.setImageResource(tabId);

        if (mp.isPlaying()) {
            try{
                mp.reset();
            }catch (NullPointerException e){
                Log.e(LOG, "Music Player is empty.");
            }
        }

        createSong();
    }

    /**
     * Sets previous track.
     */
    public void previousSong() {
        int coverId = 0;
        int tabId = 0;
        String name = "";

        TextView artist = (TextView) findViewById(R.id.name);
        ImageView cover = (ImageView) findViewById(R.id.albumcover);
        ImageView tab = (ImageView) findViewById(R.id.tab);

        if (current_track == 1) {
            current_track = tracks.size() - 1;

            name = getTrack(current_track).getArtistName() + "-" + getTrack(current_track).getTrackName();
            artist.setText(name);

            coverId = getTrack(current_track).getCoverResourceID();
            cover.setImageResource(coverId);

            tabId = getTrack(current_track).getTabResourceId();
            tab.setImageResource(tabId);

            if (mp.isPlaying()) {
                try{
                    mp.reset();
                }catch (NullPointerException e){
                    Log.e(LOG, "Music Player is empty.");
                }
            }

            createSong();

        } else {
            current_track = current_track - 1;
        }
        name = getTrack(current_track).getArtistName() + "-" + getTrack(current_track).getTrackName();
        artist.setText(name);

        coverId = getTrack(current_track).getCoverResourceID();
        cover.setImageResource(coverId);

        tabId = getTrack(current_track).getTabResourceId();
        tab.setImageResource(tabId);

        if (mp.isPlaying()) {
            try{
                mp.reset();
            }catch (NullPointerException e){
                Log.e(LOG, "Music Player is empty.");
            }
        }

        createSong();
    }

    /**
     * Moves left bar a little when clicked on.
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

        previousSong();
    }

    /**
     * Moves right bar a little when clicked on.
     */
    public void rightBarClick() {
        ImageView rightbar = (ImageView) findViewById(R.id.rightbar);
        TranslateAnimation animation = new TranslateAnimation(0, randomizeNumbers(Math.round(pxFromDp(this, 30)), Math.round(pxFromDp(this, 35)), 0), 0, 0);     //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(250);  // animation duration
        animation.setRepeatCount(1);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        rightbar.startAnimation(animation);  // start animation

        nextSong();
    }

    // Changes DP to PX for different devices.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    /**
     * Random number generator for several methods, for example to get random Hint.
     *
     * @param min     sets minimal random value to choose from.
     * @param max     sets max random value to choose from.
     * @param without that number can not be chosen - program will reroll to get different random number.
     */
    public int randomizeNumbers(int min, int max, int without) {
        Random randomNumber = new Random();
        int randomNum = randomNumber.nextInt((max - min) + 1) + min;
        // Rerolls the dice if that number was marked as not wanted.
        if (randomNum == without) {
            while (randomNum == without) {
                randomNum = randomNumber.nextInt((max - min) + 1) + min;
            }
        }
        // Changes the output to be negative if needed.
        if (negative_number_active == true) {
            randomNum = (randomNum * -1);
        }
        return randomNum;
    }

    /**
     * Starts the pulsing animation of the logo.
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
}