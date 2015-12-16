package com.voiceittech.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.os.Environment;
import android.content.Context;
import java.util.UUID;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import android.os.AsyncTask;
import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VoiceItCordova extends CordovaPlugin {

  private MediaRecorder myRecorder;
  private String outputFile;
  private CountDownTimer countDowntimer;

  @Override
  public boolean execute(String action,final JSONArray args, final CallbackContext callbackContext) throws JSONException {
    Context context = cordova.getActivity().getApplicationContext();
    Integer seconds;
    /*if (args.length() >= 1) {
      seconds = args.getInt(0);
    } else {
      seconds = 5;
    }*/
    seconds = 5;

    if (action.equals("createUser")) {
      VoiceIt myVoiceIt = new VoiceIt(args.getString(0));
      String response = myVoiceIt.createUser(args.getString(1),args.getString(2),args.getString(3),args.getString(4));
      callbackContext.success(response);
      return true;
    }

    if (action.equals("setUser")) {
      VoiceIt myVoiceIt = new VoiceIt(args.getString(0));
      String response = myVoiceIt.setUser(args.getString(1),args.getString(2),args.getString(3),args.getString(4));
      callbackContext.success(response);
      return true;
    }

    if (action.equals("getUser")) {
      VoiceIt myVoiceIt = new VoiceIt(args.getString(0));
      String response = myVoiceIt.getUser(args.getString(1),args.getString(2));
      callbackContext.success(response);
      return true;
    }

    if (action.equals("deleteUser")) {
      VoiceIt myVoiceIt = new VoiceIt(args.getString(0));
      String response = myVoiceIt.deleteUser(args.getString(1),args.getString(2));
      callbackContext.success(response);
      return true;
    }

    if (action.equals("getEnrollments")) {
      VoiceIt myVoiceIt = new VoiceIt(args.getString(0));
      String response = myVoiceIt.getEnrollments(args.getString(1),args.getString(2));
      callbackContext.success(response);
      return true;
    }

    if (action.equals("deleteEnrollment")) {
      VoiceIt myVoiceIt = new VoiceIt(args.getString(0));
      String response = myVoiceIt.deleteEnrollment(args.getString(1),args.getString(2),args.getString(3));
      callbackContext.success(response);
      return true;
    }

    if (action.equals("createEnrollment")) {
      outputFile = context.getFilesDir().getAbsoluteFile() + "/"
      + "voiceitrecording.m4a";
      File f = new File(outputFile);
      f.delete();
      myRecorder = new MediaRecorder();
      myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
      myRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
      myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
      myRecorder.setAudioSamplingRate(44100);
      myRecorder.setAudioChannels(1);
      myRecorder.setAudioEncodingBitRate(16000);
      myRecorder.setOutputFile(outputFile);

      try {
        myRecorder.prepare();
        myRecorder.start();
      } catch (final Exception e) {
        cordova.getThreadPool().execute(new Runnable() {
          public void run() {
            callbackContext.error(e.getMessage());
          }
        });
        return false;
      }

      countDowntimer = new CountDownTimer(seconds * 1000, 1000) {
        public void onTick(long millisUntilFinished) {}
        public void onFinish() {
          try{
          stopRecordEnrollment(callbackContext, args.getString(0), args.getString(1), args.getString(2));
        } catch(Exception ex){
          System.out.println("Exception Error:"+ex.getMessage());
        }
        }
      };
      countDowntimer.start();
      return true;
    }

    if (action.equals("authentication")) {
      outputFile = context.getFilesDir().getAbsoluteFile() + "/"
        + "voiceitrecording.m4a";
      File f = new File(outputFile);
      f.delete();
      myRecorder = new MediaRecorder();
      myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
      myRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
      myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
      myRecorder.setAudioSamplingRate(44100);
      myRecorder.setAudioChannels(1);
      myRecorder.setAudioEncodingBitRate(16000);
      myRecorder.setOutputFile(outputFile);

      try {
        myRecorder.prepare();
        myRecorder.start();
      } catch (final Exception e) {
        cordova.getThreadPool().execute(new Runnable() {
          public void run() {
            callbackContext.error(e.getMessage());
          }
        });
        return false;
      }

      countDowntimer = new CountDownTimer(seconds * 1000, 1000) {
        public void onTick(long millisUntilFinished) {}
        public void onFinish() {
          try{
            stopRecordAuthentication(callbackContext, args.getString(0), args.getString(1), args.getString(2),args.getString(3), args.getString(4), args.getString(5), args.getString(6));
          } catch(Exception ex){
            System.out.println("Exception Error:"+ex.getMessage());
          }
        }
      };
      countDowntimer.start();
      return true;
    }

    if (action.equals("playback")) {
      if(outputFile == null || outputFile.equals("")){
        callbackContext.error("There is nothing to play");
      }
      MediaPlayer mp = new MediaPlayer();
      mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
      try {
        FileInputStream fis = new FileInputStream(new File(outputFile));
        mp.setDataSource(fis.getFD());
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (IllegalStateException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        mp.prepare();
      } catch (IllegalStateException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
          callbackContext.success("Completed Playing Recording");
        }
      });
      mp.start();
      return true;
    }

    return false;
  }

  private void stopRecordEnrollment(final CallbackContext callbackContext,final String developerID, final String email,final String password){
    myRecorder.stop();
    myRecorder.release();
    cordova.getThreadPool().execute(new Runnable() {
      public void run() {
        String response = "";
        try{
        VoiceIt myVoiceIt = new VoiceIt(developerID);
        response = myVoiceIt.createEnrollment(email,password, outputFile);
        } catch(Exception ex){
        System.out.println("Exception Error:"+ex.getMessage());
       }
        callbackContext.success(response);
        }
    });
  }

  private void stopRecordAuthentication(final CallbackContext callbackContext, final String developerID, final String email,final String password,final String accuracy, final String accuracyPasses, final String accuracyPassIncrement, final String confidence){
    myRecorder.stop();
    myRecorder.release();
    cordova.getThreadPool().execute(new Runnable() {
      public void run() {
        String response = "";
        try{
          VoiceIt myVoiceIt = new VoiceIt(developerID);
          response = myVoiceIt.authentication(email,password, outputFile, accuracy, accuracyPasses, accuracyPassIncrement, confidence);
        } catch(Exception ex){
          System.out.println("Exception Error:"+ex.getMessage());
        }
        callbackContext.success(response);
        }
    });
  }

}
