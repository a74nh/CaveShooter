package com.sleepware.ShooterEngine;

import java.util.ArrayList;
import android.graphics.Canvas;


public class Hud {

    private ArrayList<HudEntry> mHudEntryList;

	
    
	public Hud() {
		
		mHudEntryList = new ArrayList<HudEntry>();
		mHudEntryList.clear();
	}
	
	
	public void doDraw(Canvas canvas) {
		
		for(int i=0; i<mHudEntryList.size(); i++) {
			mHudEntryList.get(i).doDraw(canvas);
		}
	}
	
	
    public void updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
    	
		for(int i=0; i<mHudEntryList.size(); i++) {
			mHudEntryList.get(i).updatePhysics(now, elapsed, canvasWidth, canvasHeight);
		}
    }
    
    
    public void addHudEntry(HudEntry entry) {
    	mHudEntryList.add(entry);
    }
}
