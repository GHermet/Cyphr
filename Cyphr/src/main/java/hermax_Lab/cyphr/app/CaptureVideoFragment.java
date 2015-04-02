package hermax_Lab.cyphr.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Date;

public class CaptureVideoFragment extends Fragment implements SurfaceHolder.Callback
{
    private boolean recording=false;
    private ImageView btnStartRec;
    private String filename;
    private String path;
    private Chronometer duration;
    private ImageView camera_icon;
    private ImageButton goback;
    private ImageView replay;
    private boolean Music;
    PrintWriter pw;
    private ProgressBar micDecibel;


    @Override
    public void onDestroy()
    {
        if(recording){
            try {
                stopRecording();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        releaseMediaRecorder();
        super.onDestroy();
    }

    private SurfaceHolder surfaceHolder;
    private VideoView VideoView;
    public MediaRecorder mrec = new MediaRecorder();
    private Camera mCamera;
    private MediaPlayer music;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.camera, container,
                false);


        VideoView = (VideoView) view.findViewById(R.id.surfaceCamera);
        btnStartRec = (ImageView) view.findViewById(R.id.btnCaptureVideo);
        duration = (Chronometer) view.findViewById(R.id.video_duration);
        goback = (ImageButton) view.findViewById(R.id.camera_return_button);
        micDecibel = (ProgressBar) view.findViewById(R.id.progressBar);
        micDecibel.setVisibility(view.INVISIBLE);
        replay = (ImageView) view.findViewById(R.id.replayview);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRecording();
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SongFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null).setCustomAnimations(R.anim.slide_in_left, R.anim.do_nothing).replace(R.id.content_frame, fragment).commit();
            }
        });

        duration.setVisibility(view.INVISIBLE);
        camera_icon = (ImageView) view.findViewById(R.id.camera_icon);
        camera_icon.setVisibility(view.INVISIBLE);
        btnStartRec.setEnabled(false);
        btnStartRec.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               try{
                                                   if (recording) {
                                                       btnStartRec.setImageResource(R.drawable.ic_mic);
                                                       mrec.stop();
                                                       //stopMusic();
                                                       duration.setBase(SystemClock.elapsedRealtime());
                                                       duration.stop();
                                                       mCamera.lock();
                                                       mrec.release();

                                                       mrec = null;
                                                       duration.setVisibility(v.INVISIBLE);
                                                       camera_icon.setVisibility(v.INVISIBLE);
                                                     //  micDecibel.setVisibility(v.INVISIBLE);
                                                       

                                                       recording = false;
                                                       // Let's initRecorder so we can record again
                                                       //initRecorder();
                                                       //prepareRecorder();

                                                   } else {
                                                       recording = true;
                                                       btnStartRec.setImageResource(R.drawable.ic_pause_black);
                                                       duration.setVisibility(v.VISIBLE);
                                                       camera_icon.setVisibility(v.VISIBLE);
                                                       //micDecibel.setVisibility(v.VISIBLE);
                                                       mCamera.unlock();
                                                       startRecording();


                                                     //playMusic();
                                                       int stoppedMilliseconds = 0;

                                                       String chronoText = duration.getText().toString();
                                                       String array[] = chronoText.split(":");
                                                       // sauvegarde en temps réel de la valeur du chrono pour reprendre le chrono la ou il s'est arreté
                                                       if (array.length == 2) {
                                                           stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                                                                   + Integer.parseInt(array[1]) * 1000;
                                                       } else if (array.length == 3) {
                                                           stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                                                                   + Integer.parseInt(array[1]) * 60 * 1000
                                                                   + Integer.parseInt(array[2]) * 1000;
                                                       }

                                                       duration.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
                                                       duration.start();
                                                   }

                                               } catch(RuntimeException e){
                                                   String message = e.getMessage();
                                                   Log.i(null, "Problem " + message);
                                                   mrec.release();

                                               } catch(Exception e){
                                                   String message = e.getMessage();
                                                   Log.i(null, "Problem " + message);
                                                   mrec.release();

                                               }
                                           }
                                       });
       

        surfaceHolder = VideoView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        while(recording){
            mrec.getMaxAmplitude();
        }

return view;

    }


    protected void startRecording() throws IOException
    {

        mCamera.stopPreview();

        path= Environment.getExternalStorageDirectory().getPath();
        File Directory = new File(path+"/CyphrMedia");
       if(!Directory.exists()){
           Directory.mkdirs();
       }

        Date date=new Date();

        filename="/CyphrMedia/CyphrRec_"+date.toString().replace(" ", "_")+".mp4";

        File file =new File(path,filename);

        mrec = new MediaRecorder();

        mCamera.lock();
        mCamera.unlock();

        // Please maintain sequence of following code.

        // If you change sequence it will not work.
        mrec.setCamera(mCamera);
        mrec.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mrec.setAudioSource(MediaRecorder.AudioSource.MIC);
        mrec.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mrec.setOrientationHint(270);
        mrec.setVideoSize(1080,920);
        mrec.setVideoFrameRate(24); //might be auto-determined due to lighting
        mrec.setVideoEncodingBitRate(3000000);
        mrec.setAudioEncodingBitRate(12200);
        mrec.setAudioSamplingRate(44100);
        mrec.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mrec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        mrec.setPreviewDisplay(surfaceHolder.getSurface());
        mrec.setOutputFile(path+filename);
        mrec.prepare();
        mrec.start();


       // micDecibel.setProgress( mrec.getMaxAmplitude());


        recording=true;




    }

    protected void stopRecording() throws FileNotFoundException {

        if(mrec!=null&& Music)
        {   stopMusic();
            writeToFile(filename,"Videopath.txt");
            mrec.stop();
            mrec.release();
            mCamera.release();
            mCamera.lock();
            recording=false;
            mCamera.startPreview();

           // playRecording();

        }
    }
    private void playRecording(){
        try {

            // Start the MediaController
            MediaController mediacontroller = new MediaController(getActivity());
            mediacontroller.setAnchorView(VideoView);
            // Get the URL from String VideoURL
            Uri mVideo = Uri.parse("/storage/emulated/0/"+readFile("Videopath.txt"));
            VideoView.setMediaController(mediacontroller);
            VideoView.setVideoURI(mVideo);



        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();

        }

        VideoView.requestFocus();
        VideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                VideoView.start();

            }
        });

        VideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                mCamera.startPreview();

            }
        });

        btnStartRec.setEnabled(false);
    }

    private void releaseMediaRecorder() {

        if (mrec != null) {
            mrec.reset(); // clear recorder configuration
            mrec.release(); // release the recorder object
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release(); // release the camera for other applications
            mCamera = null;
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        int i;
        for (i=0; i< Camera.getNumberOfCameras(); i++) {
            Camera.CameraInfo newInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, newInfo);
            if (newInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

                mCamera = Camera.open(i);
                break;
            }
        }




        try {

                setDisplayOrientation(mCamera,90);
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }

           btnStartRec.setEnabled(true);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
if(Music){
    stopMusic();
}

        releaseCamera();


        recording=false;

    }

    public void playMusic(){
        music= MediaPlayer.create(getActivity(),R.raw.skinny);
        music.setLooping(true);
        music.start();
        Music = true;
    }
    public void stopMusic(){
        music.pause();
        music.reset();
        Music = false;
    }
    protected void setDisplayOrientation(Camera camera, int angle){
        Method downPolymorphic;
        try
        {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
            if (downPolymorphic != null)
                downPolymorphic.invoke(camera, new Object[] { angle });
        }
        catch (Exception e1)
        {
        }
    }
    private void writeToFile(String data, String F) throws FileNotFoundException {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(F), "utf-8"));
            writer.write(data);
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {}
        }


    }
    public String readFile(String filename)
    {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }



    }








