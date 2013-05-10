package com.sleepware.ShooterEngine;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sleepware.ShooterEngine.ShooterEngine;

public class ShooterEngineView extends SurfaceView implements SurfaceHolder.Callback {
	
    /** The Shooter Engine. Runs in own thread */
    private ShooterEngine thread;

    private boolean mUserControlable;
    private int mLevel;
        
    public ShooterEngineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mUserControlable = false;
        
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        createThread();
    }

    public void loadLevel(int level) {
    	mLevel=level;
    	thread.loadLevel(level);
    }
    
    static class myHandler extends Handler {
        @Override
        public void handleMessage(Message m) {
        	int type = m.getData().getInt("type");
        	if(ShooterEngine.MESSAGE_GAMEOVER==type) {
        		//TODO: Game over man! Game over!
        	}
        }
    }    
    
    private void createThread() {
        thread = new ShooterEngine(getHolder(), getContext(), new myHandler() );
        thread.start();
    }
    
    
    public void allowUserControl(boolean focusable) {
        setFocusable(focusable); // make sure we get key events
        mUserControlable=focusable;
    }
    
    
    /**
     * Standard override to get key-press events.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
    	if(!mUserControlable) return false;
        return thread.mShooterEngineContext.mInputController.doKeyDown(keyCode, msg);
    }

    /**
     * Standard override for key-up. We actually care about these, so we can
     * turn off the engine or stop rotating.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent msg) {
    	if(!mUserControlable) return false;
        return thread.mShooterEngineContext.mInputController.doKeyUp(keyCode, msg);
    }

    /**
     * Standard override to get touch events.
     */
    @Override
    public boolean onTouchEvent (MotionEvent event) {
    	if(!mUserControlable) return false;
        return thread.mShooterEngineContext.mInputController.doTouchEvent (event);
    }
    
    /**
     * Standard window-focus override. Notice focus lost so we can pause on
     * focus lost. e.g. user switches to take a call.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) thread.pause();
    }


    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }

    /*
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
    public void surfaceCreated(SurfaceHolder holder) {
    	
    	if (thread.getState() == Thread.State.TERMINATED) {
    		createThread();
        	thread.loadLevel(mLevel);
    	}
        thread.surfaceCreated();
    }

    /*
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
    	shutdown();
    }
    
    
    public void pause() {
        thread.pause();
    }
    
    public void unpause() {
        thread.unpause();
    }
    
    public void shutdown() {
    	if (thread.getState() != Thread.State.TERMINATED) {
    		thread.shutdown();
    	}
    }
    
    
    public Bundle saveState(Bundle map) {
    	return thread.saveState(map);
    }
    
    public void restoreState(Bundle savedState) {
    	thread.restoreState(savedState);
    }
    
    public void setDebugMode() {
    	thread.setDebugMode();
    }

}