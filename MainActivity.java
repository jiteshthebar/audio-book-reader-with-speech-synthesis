package com.example.p;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private TextView bookText;
    private Button readButton, pauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookText = findViewById(R.id.bookText);
        readButton = findViewById(R.id.readButton);
        pauseButton = findViewById(R.id.pauseButton);

        // Initialize TextToSpeech
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int langResult = tts.setLanguage(Locale.US);
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle unsupported language
                }
            }
        });

        // Start reading
        readButton.setOnClickListener(v -> {
            String text = bookText.getText().toString();
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        });

        // Stop reading
        pauseButton.setOnClickListener(v -> {
            if (tts != null && tts.isSpeaking()) {
                tts.stop();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}


