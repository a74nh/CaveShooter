

package com.sleepware.caveshooter;


import com.sleepware.ShooterEngine.ShooterEngineView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


public class CaveShooterActivity extends Activity {


    private static final int MENU_RESUME = 5;

    private static final int MENU_START = 6;


    /** A handle to the View in which the game is running. */
    private ShooterEngineView mShooterEngineView;
 

    /**
     * Invoked during init to give the Activity a chance to set up its Menu.
     * 
     * @param menu the Menu to which entries may be added
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, MENU_START, 0, R.string.menu_start);
        menu.add(0, MENU_RESUME, 0, R.string.menu_resume);
        return true;
    }

    /**
     * Invoked when the user selects an item from the Menu.
     * 
     * @param item the Menu entry which was selected
     * @return true if the Menu item was legit (and we consumed it), false
     *         otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_START:
              //  mShooterEngineThread.doStart();
                return true;

            case MENU_RESUME:
            	mShooterEngineView.unpause();
                return true;
           
        }

        return false;
    }

    /**
     * Invoked when the Activity is created.
     * 
     * @param savedInstanceState a Bundle containing state saved from a previous
     *        execution, or null if this is a new execution
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // tell system to use the layout defined in our XML file
        setContentView(R.layout.caveshooter_layout);

        // get handles to the ShooterEngineView from XML
        mShooterEngineView = (ShooterEngineView) findViewById(R.id.caveShooter);

        mShooterEngineView.allowUserControl(true);

        if (savedInstanceState == null) {
            // we were just launched: set up a new game
           // mCaveShooterThread.setState(CaveShooterThread.STATE_READY);
        	mShooterEngineView.loadLevel(R.xml.level);
            //mShooterEngineThread.doStart();
            Log.w(this.getClass().getName(), "SIS is null");
        } else {
            // we are being restored: resume a previous game
        	mShooterEngineView.restoreState(savedInstanceState);
            Log.w(this.getClass().getName(), "SIS is nonnull");
        }
        
    }

    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mShooterEngineView.pause(); // pause game when Activity pauses
    }

    
    /**
     * Notification that something is about to happen, to give the Activity a
     * chance to save state.
     * 
     * @param outState a Bundle into which this Activity should save its state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        mShooterEngineView.saveState(outState);
        Log.w(this.getClass().getName(), "SIS called");
    }
    
    /*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean result = true;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		} else {
			result = super.onKeyDown(keyCode, event);
		}
		return result;
	}*/
}
