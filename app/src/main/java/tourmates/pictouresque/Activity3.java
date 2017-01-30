package tourmates.pictouresque;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import android.widget.Toast;
import android.os.Build;
import android.util.Log;

public class Activity3 extends Activity {
    TextToSpeech t1;
    TextView descriptionText;
    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        descriptionText = (TextView)findViewById(R.id.descriptionText);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }

                if (status == TextToSpeech.SUCCESS) {

                    int result = t1.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        playButton.setEnabled(true);
                        speakOut();
                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });

        //PLAY
        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });

        //STOP
        Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.stop();
                t1.shutdown();
            }
        });

        //GO BACK
        Button gobackButton = (Button) findViewById(R.id.gobackButton);
        gobackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to MainActivity
                goToMainActivity();
            }
        });

        //SAVE
        Button saveButton = (Button) findViewById(R.id.saveButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //supposed to save this location to your favorites
            }
        });
    }

    @Override
    protected void onPause() {
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void speakOut() {
        String toSpeak = descriptionText.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();

        if (Build.VERSION.RELEASE.startsWith("5")) {
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
