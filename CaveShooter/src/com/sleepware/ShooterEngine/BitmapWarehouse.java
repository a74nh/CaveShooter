package com.sleepware.ShooterEngine;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapWarehouse {
	
	
    private ArrayList<Bitmap> mBitmapList;
    private ArrayList<Integer> mImageIdList;


    public BitmapWarehouse() {

    	mBitmapList = new ArrayList<Bitmap>();
    	mBitmapList.clear();
    	mImageIdList = new ArrayList<Integer>();
    	mImageIdList.clear();
    }
    
    
	public void add (ShooterEngineContext shooterEngineContext, int imageid) {

		for(int i=0; i<mImageIdList.size(); i++) {
			if(mImageIdList.get(i)== imageid) return;
		}
		
		Bitmap b = BitmapFactory.decodeResource(shooterEngineContext.mContext.getResources(), imageid);

		mImageIdList.add(imageid);
		mBitmapList.add(b);
	}

	
	public Bitmap getImage(int imageid) {
		
		for(int i=0; i<mImageIdList.size(); i++) {
			if(mImageIdList.get(i)== imageid) return mBitmapList.get(i);
		}
		
		return null;
	}

}
