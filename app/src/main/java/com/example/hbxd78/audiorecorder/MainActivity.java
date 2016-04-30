package com.example.paykey.sound;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Bundle;
import android.os.Environment;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.audiocapture.R;

import java.io.File;
import java.io.IOException;


public class MainActivity extends Activity {
    private String[] mFileList;
    private File mPath = new File(Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/soundDir/");
    private String mChosenFile;
    private static final int DIALOG_LOAD_FILE = 1000;
    Button play,stop,record;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    File mydir;
    private String fileSelected = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play=(Button)findViewById(R.id.button3);
        stop=(Button)findViewById(R.id.button2);
        record=(Button)findViewById(R.id.button);
        int filecount = 0;
        stop.setEnabled(false);
        play.setEnabled(false);
        mydir = new File(Environment.getExternalStorageDirectory() + "/soundDir/");
        if(!mydir.exists()){
            mydir.mkdirs();
        }
        else {
            filecount = mydir.listFiles().length;
        }
        //mydir = null;
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/soundDir/recording" + filecount + ".3gp";

        myAudioRecorder=new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                }

                catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                record.setEnabled(false);
                stop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();

                myAudioRecorder  = null;

                stop.setEnabled(false);
                play.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                showDialog();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    protected Dialog onCreateDialog(int id) {
//        Dialog dialog = null;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        switch(id) {
//            case DIALOG_LOAD_FILE:
//                builder.setTitle("Choose your file");
//                if(mFileList == null) {
////                    Log.e(TAG, "Showing file picker before loading the file list");
//                    dialog = builder.create();
//                    return dialog;
//                }
//                builder.setItems(mFileList, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        mChosenFile = mFileList[which];
//                        //you can do stuff with the file here too
//                    }
//                });
//                break;
//        }
//        dialog = builder.show();
//        return dialog;
//    }
    private void showDialog(){
        FileDialog fileDialog = new FileDialog(this, mPath, ".3gp");
        fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
            public void fileSelected(File file) {
                MediaPlayer m = new MediaPlayer();

                try {
                    Log.d(getClass().getName(), "selected file " + file.toString());
                    m.setDataSource(file.toString());
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

            }
        });
        //fileDialog.addDirectoryListener(new FileDialog.DirectorySelectedListener() {
        //  public void directorySelected(File directory) {
        //      Log.d(getClass().getName(), "selected dir " + directory.toString());
        //  }
        //});
        //fileDialog.setSelectDirectoryOption(false);
        fileDialog.showDialog();
    }
}