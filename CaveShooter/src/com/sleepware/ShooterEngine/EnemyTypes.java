package com.sleepware.ShooterEngine;

import java.util.ArrayList;

public class EnemyTypes {


	private ArrayList<EntityType> mEnemyTypeList;    
    
    
    EnemyTypes() {
    	
        mEnemyTypeList = new ArrayList<EntityType>();
    	mEnemyTypeList.clear();
    }
    
    public EntityType getEnemyType(int index) {
    	return mEnemyTypeList.get(index);
    }
    
    public EntityType getEnemyType(String name) {
    	
    	for(int i=0; i<mEnemyTypeList.size(); i++) {
    		
    		EntityType e = mEnemyTypeList.get(i);
    		
    		if(e.getName().equals(name)) {
    			return e;
    		}
    		
    	}
    	return null;
    }
    
   public int getEnemyId(String name) {
    	
    	for(int i=0; i<mEnemyTypeList.size(); i++) {
    		    		
    		if(mEnemyTypeList.get(i).getName().equals(name)) {
    			return i;
    		}
    		
    	}
    	return EntityType.BONUSGUN_NONE;
    }
    
    
    public int addEnemyType(EntityType e) {
    	mEnemyTypeList.add(e);
    	return mEnemyTypeList.size()-1;
    }
	    
	
}
