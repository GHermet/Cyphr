package hermax_Lab.cyphr.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SongFragment extends Fragment {

    private ArrayList<Song> songList;
    private ListView songView;
    private Fragment fragment;
    private ImageView listImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.songlist, container,
                false);

        songView = (ListView) view.findViewById(R.id.song_list);
        songList = new ArrayList<Song>();

        SongAdapter songAdt = new SongAdapter(this.getActivity(), songList);
        songView.setAdapter(songAdt);







        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));
        songList.add(new Song("Lies","Skinny Berlin","4:03"));



    songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         fragment =  new CaptureVideoFragment();FragmentTransaction transaction = getFragmentManager().beginTransaction();
         transaction.addToBackStack(null).setCustomAnimations(R.anim.slide_in_right, R.anim.do_nothing).replace(R.id.content_frame, fragment).commit();

        }
    });


        Collections.sort(songList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });



        return view;
    }



    public void getSongList() {
        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int durationColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisDuration = musicCursor.getString(durationColumn);
                songList.add(new Song(thisTitle,thisArtist,thisDuration));
            }
            while (musicCursor.moveToNext());
        }
    }

}