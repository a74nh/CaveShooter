package com.sleepware.ShooterEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;


public class ShooterEngine extends Thread {
	

	private boolean mStateLevelLoaded;
	private boolean mStateSurfaceCreated;
	private boolean mStatePaused;
	//private boolean mStateRunning; Now in Context

	private boolean mExitRequested;
	
	
	final public static int MESSAGE_GAMEOVER =1;
	
	
	public ShooterEngineContext mShooterEngineContext;

	
	 /** Message handler used by thread to interact with TextView */
    private Handler mHandler;

    /** Handle to the surface manager object we interact with */
    private SurfaceHolder mSurfaceHolder;
    
    /** Used to figure out elapsed time between frames */
    private long mLastTime;
    
	private int mCanvasHeight;
    private int mCanvasWidth;
	
    
    public ShooterEngine(SurfaceHolder surfaceHolder, Context context, Handler handler) {
    	
    	// get handles to some important objects
        mSurfaceHolder = surfaceHolder;
        mHandler = handler;
       
        mShooterEngineContext = new ShooterEngineContext(context);
            	
    	mStateLevelLoaded =false;
    	mStateSurfaceCreated =false;
    	mStatePaused =false;
    	mExitRequested=false;
	}
	
    
    public void loadLevel(int level) {
    	
    	LevelLoader.loadLevelTree(level, mShooterEngineContext);
    	
    	synchronized (this) {
    		mStateLevelLoaded=true;
    	}
    }
    

    
    @Override
    //This gets called by someone else calling start() on us
    public void run() {
    	
    	synchronized (this) {
	    	//These should never return back to false after being set true....
	    	while (!mStateLevelLoaded || !mStateSurfaceCreated) {
	            try {
	            	
	                wait();
	            } catch (InterruptedException e) {
	                
	            }
	    	}
    	}
            
        launch();
        mShooterEngineContext.mStateRunning =true;
    	
    	while(!mExitRequested) {

	    	//pause and reflect for a while...
	    	while (mStatePaused) {
	        
	    		synchronized (this) {	
		    		try {
		                wait();
		            } catch (InterruptedException e) {
		                
		            }
	    		}
	        }
	    	
            Canvas c = null;
            try {
                c = mSurfaceHolder.lockCanvas(null);
                synchronized (mSurfaceHolder) {
                    
                	long now = System.currentTimeMillis();
                    updatePhysics(now);
                    doDraw(c);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    mSurfaceHolder.unlockCanvasAndPost(c);
                }
            }
    	}
    	
    	
    	//Clear everything and return
    	
    	//synchronized (mSurfaceHolder) {
    	//	mStateRunning=false;
    	//}
    	
    	mShooterEngineContext.clear();
    	
    	mStateLevelLoaded =false;
    }
    
    
    public void surfaceCreated() {
    	
    	synchronized (this) {
    		mStateSurfaceCreated=true;
    		notify();
    	}
    }

    
    
    /**
     * Pauses the physics update & animation.
     */
    public void pause() {
        synchronized (this) {
        	mStatePaused=true;
        }
    }
    
    /**
     * Resumes from a pause.
     */
    public void unpause() {
    	if(!mStatePaused) return;
    	
        // Move the real time clock up to now
        synchronized (this) {
            mLastTime = System.currentTimeMillis() + 100;
            mStatePaused=false;
            notify();
        }
    }
    
    public void shutdown() {
    	boolean retry = true;
    	mExitRequested=true;
    	unpause();
        while (retry) {
            try {
                join();
                retry = false;
            } catch (InterruptedException e) {
            	e.printStackTrace();
            }
        }
    }
    
    
    
    private void launch() {
    	
    	Canvas c = null;
        try {
            c = mSurfaceHolder.lockCanvas(null);
            synchronized (mSurfaceHolder) {
            	
            	long now = System.currentTimeMillis();
            	
        		mCanvasWidth = c.getWidth();
        		mCanvasHeight = c.getHeight();
        		        	    
        		mShooterEngineContext.mLevelSections.doStart(now, mCanvasWidth, mCanvasHeight);
        	    
        	    // pick a convenient initial location
        	    if(mShooterEngineContext.mPlayerType!=null) {
        	    	mShooterEngineContext.setPlayer(mShooterEngineContext.mPlayerType.spawn(mShooterEngineContext, now, 50, mCanvasHeight / 2));
        	    }
        	    
        	    mShooterEngineContext.mBullets.doStart();
        	    mShooterEngineContext.mEnemies.doStart();
        	    
                mLastTime = now + 100;
                
            }
        } finally {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
            if (c != null) {
                mSurfaceHolder.unlockCanvasAndPost(c);
            }
        }
    }
    
	
	public void doDraw(Canvas canvas) {
	 
		//canvas.save();

		mShooterEngineContext.mLevelSections.doDraw(canvas);
    		
		mShooterEngineContext.mHud.doDraw(canvas);
	
		mShooterEngineContext.mEnemies.doDraw(canvas);
	        
		mShooterEngineContext.mBullets.doDraw(canvas);
	    
		Player player = mShooterEngineContext.getPlayer();
	    if(player!=null) player.doDraw(canvas);
	        
	    //canvas.restore();
    }
	
	
	
    private void updatePhysics(long now) {

        // Do nothing if mLastTime is in the future.
        // This allows the game-start to delay the start of the physics
        // by 100ms or whatever.
        if (mLastTime > now) return;
        
        long elapsed = now - mLastTime;

        mShooterEngineContext.mFPS = (1000.0 / (double)elapsed);
        
    	            /*
            //String output = myFormatter.format((1000.0 / (double)elapsed));  //fps
            //String output = myFormatter.format((mBackground.getXwallPos()));
            //String output = myFormatter.format((mY));
            //String output = myFormatter.format((mBackground.mWallImage.getPixel((int)(mX+mBackground.getXwallPos()), (int)mY)));
            Message msg = mHandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("type", M_FPS);
            b.putString("text", output);
            msg.setData(b);
            mHandler.sendMessage(msg);
            mlastTimeFPSUpdated=now;*/

            		
		//Move everything
		
        mShooterEngineContext.mLevelSections.updatePhysics(now, elapsed, mCanvasWidth, mCanvasHeight);
		
        Player player = mShooterEngineContext.getPlayer();
		if(player!=null) player.updatePhysics(now, elapsed);
        
		mShooterEngineContext.mEnemies.updatePhysics(now, elapsed, mCanvasWidth, mCanvasHeight);
                    
		mShooterEngineContext.mBullets.updatePhysics(now, elapsed, mCanvasWidth, mCanvasHeight);

        
        //Check Collisions
		player = mShooterEngineContext.getPlayer();
    	if(player!=null && player.doBackgroundCollisions(now)) {
    		playerDied(now);
        }
    	
    	mShooterEngineContext.mBullets.doCollisions(mShooterEngineContext, now);

    	player = mShooterEngineContext.getPlayer();
    	if(player!=null && player.doCollisions(now)) {
    		playerDied(now);
        }
        
        //The list may be full of blanks by now
    	
    	mShooterEngineContext.mEnemies.cleanup();
        
    	
    	//Last because could house all sorts of state
    	mShooterEngineContext.mHud.updatePhysics(now, elapsed, mCanvasWidth, mCanvasHeight);

        mLastTime = now;
    }
    
    
    private void playerDied(long now) {
    	mShooterEngineContext.mLives--;
    	
    	if(mShooterEngineContext.mLives>0) {
    		mShooterEngineContext.setPlayer(mShooterEngineContext.mPlayerType.spawn(mShooterEngineContext, now, 50, mCanvasHeight / 2));
            return;
    	}
    	
    	mShooterEngineContext.setPlayer(null);
    	
        Message msg = mHandler.obtainMessage();
        Bundle b = new Bundle();
        b.putInt("type", MESSAGE_GAMEOVER);
        //b.putString("text", "");
        msg.setData(b);
        mHandler.sendMessage(msg);
    }
    
    

    
    /**
     * Invoked when the surface dimensions change.
     */
    public void setSurfaceSize(int width, int height) {
        // Synchronised to make sure these all change atomically
        synchronized (mSurfaceHolder) {
            mCanvasWidth = width;
            mCanvasHeight = height;

        }
    }
    
 
	
    
    
    /**
     * Dump game state to the provided Bundle. Typically called when the
     * Activity is being suspended.
     * 
     * @return Bundle with this view's state
     */
    public Bundle saveState(Bundle map) {
        synchronized (mSurfaceHolder) {
            if (map != null) {
                //map.putDouble(KEY_X, Double.valueOf(mX));
               // map.putDouble(KEY_Y, Double.valueOf(mY));
               // map.putInt(KEY_LANDER_WIDTH, Integer.valueOf(mPlayerWidth));
               // map.putInt(KEY_LANDER_HEIGHT, Integer .valueOf(mPlayerHeight));
               // Global.mBullets.saveState(map);
            }
        }
        return map;
    }
    
    /**
     * Restores game state from the indicated Bundle. Typically called when
     * the Activity is being restored after having been previously
     * destroyed.
     * 
     * @param savedState Bundle containing the game state
     */
    public synchronized void restoreState(Bundle savedState) {
        synchronized (mSurfaceHolder) {
        	//mState = SHOOTER_STATE_PAUSED;

            //mX = savedState.getDouble(KEY_X);
            //mY = savedState.getDouble(KEY_Y);

           // mPlayerWidth = savedState.getInt(KEY_LANDER_WIDTH);
           // mPlayerHeight = savedState.getInt(KEY_LANDER_HEIGHT);
           // Global.mBullets.restoreState(savedState);
        }
    }
    
}
