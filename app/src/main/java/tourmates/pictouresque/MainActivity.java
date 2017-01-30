package tourmates.pictouresque;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.util.Size;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.moodstocks.android.Scanner;
import com.moodstocks.android.MoodstocksError;

public class MainActivity extends Activity {
    // Moodstocks API key/secret pair
    private static final String API_KEY    = "cxnc9lywiefp20noia2b";
    private static final String API_SECRET = "WGKExPIWdBaRwzZB";

    private boolean compatible = false;
    private Scanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compatible = Scanner.isCompatible();
        if (compatible) {
            try {
                scanner = Scanner.get();
                String path = Scanner.pathFromFilesDir(this, "scanner.db");
                scanner.open(path, API_KEY, API_SECRET);
                scanner.sync();
            } catch (MoodstocksError e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compatible) {
            try {
                scanner.close();
                scanner.destroy();
                scanner = null;
            } catch (MoodstocksError e) {
                e.printStackTrace();
            }
        }

    }

    public void onScanButtonClicked(View view) {
        if (compatible) {
            startActivity(new Intent(this, ScanActivity.class));
        }
    }

    private void goToActivity3() {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

}
