package com.sleepware.caveshooter;

import com.sleepware.ShooterEngine.ShooterEngineView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainMenuActivity extends Activity {

	
    private ShooterEngineView mShooterEngineView;
    private View mNewGameButton;
    
    
    private View.OnClickListener sNewGameButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
        	
   		 mShooterEngineView.shutdown();
		 
   		 Intent i = new Intent(getBaseContext(), CaveShooterActivity.class);
   		 startActivity(i);
        }
    };
    
    private View.OnClickListener sDebugButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
        	
   		 mShooterEngineView.shutdown();
		 
   		 Intent i = new Intent(getBaseContext(), CaveShooterActivity.class);
   		 i.setType("text/plain");
   		 i.putExtra(android.content.Intent.EXTRA_TEXT, "debug");
   		 startActivity(i);
        }
    };
    
    private void assignHandlers() {
        //Set listeners for the buttons
        
        mNewGameButton = findViewById(R.id.button1);
        mNewGameButton.setOnClickListener(sNewGameButtonListener);
        
        mNewGameButton = findViewById(R.id.button3);
        mNewGameButton.setOnClickListener(sDebugButtonListener);
    }
    
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // tell system to use the layout defined in our XML file
        setContentView(R.layout.mainmenu_layout);
        
        
        // Get handles to the ShooterEngine from XML, and load the correct level
        
        mShooterEngineView = (ShooterEngineView) findViewById(R.id.mainMenu);
        mShooterEngineView.allowUserControl(false);
        
        if (savedInstanceState == null) {
            // we were just launched: set up a new game
           // mCaveShooterThread.setState(CaveShooterThread.STATE_READY);
        	mShooterEngineView.loadLevel(R.xml.demo);
            //mShooterEngineThread.doStart();
            Log.w(this.getClass().getName(), "SIS is null");
        } else {
            // we are being restored: resume a previous game
        	mShooterEngineView.restoreState(savedInstanceState);
            Log.w(this.getClass().getName(), "SIS is nonnull");
        }
        
        assignHandlers();
	}
	
	
    @Override
    protected void onRestart() {
        super.onRestart();
        
        assignHandlers();  
    }
	
	
	
    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mShooterEngineView.pause(); // pause game when Activity pauses
    }
	
    
}
