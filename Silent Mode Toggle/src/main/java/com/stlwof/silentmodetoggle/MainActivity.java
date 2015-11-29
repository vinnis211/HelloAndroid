package com.stlwof.silentmodetoggle;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.stlwof.silentmodetoggle.util.RingerHelper;

public class MainActivity extends Activity {

    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to Android’s AudioManager so we can use
        // it to toggle our ringer.
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Find the view with the ID "content" in our layout file.
        FrameLayout contentView =
                (FrameLayout) findViewById(R.id.content);

        // Create a click listener for the contentView that will toggle
        // the phone’s ringer state, and then update the UI to reflect
        // the new state.
        contentView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Toggle the ringer mode. If it’s currently normal,
                // make it silent. If it’s currently silent,
                // do the opposite.
                RingerHelper.performToggle(audioManager);
                // Update the UI to reflect the new state
                updateUi();
            }
        });

        Log.d("SilentModeApp", "This is a test");

    }


    /**
     * Updates the UI image to show an image representing silent or
     * normal, as appropriate
     */
    private void updateUi() {
        // Find the view named phone_icon in our layout. We know it’s
        // an ImageView in the layout, so downcast it to an ImageView.
        ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
        // Set phoneImage to the ID of image that represents ringer on
        // or off. These are found in res/drawable-xxhdpi
        int phoneImage = RingerHelper.isPhoneSilent(audioManager)
                ? R.drawable.ringer_off
                : R.drawable.ringer_on;
        // Set the imageView to the image in phoneImage
        imageView.setImageResource(phoneImage);
    }
    /**
     * Every time the activity is resumed, make sure to update the
     * buttons to reflect the current state of the system (since the
     * user may have changed the phone’s silent state while we were in
     * the background).
     *
     * Visit http://d.android.com/reference/android/app/Activity.html
     * for more information about the Android Activity lifecycle.
     */

    @Override
    protected void onResume() {
        super.onResume();
        // Update our UI in case anything has changed.
        updateUi();
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
}
