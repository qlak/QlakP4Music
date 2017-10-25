package com.baza1234.qlakp4music;

/**
 * Track Class to hold Track objects for the aplication.
 */
public class Track {

    private String mNameArtist;
    private String mNameTrack;
    private int mTabResource;
    private int mCoverResource;
    private int mAudioResource;
    private String mYear;
    private String mGenre;
    private String mWho;
    private String mFrom;

    public Track(String nameArtist, String nameTrack, int tabResourceId, int coverResourceId, int audioResourceId, String year, String genre, String who, String from){
        mNameArtist = nameArtist;
        mNameTrack = nameTrack;
        mTabResource = tabResourceId;
        mCoverResource = coverResourceId;
        mAudioResource = audioResourceId;
        mYear = year;
        mGenre = genre;
        mWho = who;
        mFrom = from;
    }

    public String getArtistName() {
        return mNameArtist;
    }

    public String getTrackName() {
        return mNameTrack;
    }

    public int getTabResourceId() {
        return mTabResource;
    }

    public int getCoverResourceID() {
        return mCoverResource;
    }

    public int getAudioResource() {
        return mAudioResource;
    }

    public String getYear() {
        return mYear;
    }

    public String getGenre() {
        return mGenre;
    }

    public String getWho() {
        return mWho;
    }

    public String getFrom() {
        return mFrom;
    }
}