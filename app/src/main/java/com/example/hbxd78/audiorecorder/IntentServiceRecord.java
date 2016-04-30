package com.example.hbxd78.audiorecorder;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;
import java.io.IOException;

/**
 * Created by HBXD78 on 12/3/2015.
 */

public class IntentServiceRecord extends Service {
    private MediaRecorder player;
    private static int id;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()  {
        id = 0;
        Toast.makeText(getApplicationContext(), "Preparing to record.", Toast.LENGTH_SHORT).show();
        player = new MediaRecorder();
        player.setAudioSource(MediaRecorder.AudioSource.MIC);
        player.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        player.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        player.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp");
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Stopped recording.", Toast.LENGTH_SHORT).show();
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        Toast.makeText(this, "Record Started.", Toast.LENGTH_SHORT).show();
        try {
            player .prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Failure Occurred.
        }
        return 0; // Success
    }
}