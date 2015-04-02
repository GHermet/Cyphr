package hermax_Lab.cyphr.app;

import android.provider.MediaStore;

/**
 * Created by macbook on 5/12/14.
 */
public class Song {


    private String title;
    private String artist;
    private String duration;
    private MediaStore.Audio.Media audio;


    public Song(String songTitle, String songArtist, String songDuration) {

        title=songTitle;
        artist=songArtist;
        duration=songDuration;

    }
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getDuration(){return duration;}
}
