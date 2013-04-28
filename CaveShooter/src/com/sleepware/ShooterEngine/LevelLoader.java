package com.sleepware.ShooterEngine;

import org.xmlpull.v1.XmlPullParser;

import com.badlogic.gdx.math.Vector2;

import android.content.res.XmlResourceParser;

public class LevelLoader {

	
	public LevelLoader () {

	}
	
	
	private static PlayerType parsePlayer(XmlResourceParser parser, ShooterEngineContext shooterEngineContext) {

		String name = null;
		int image = 0;
		int invincibleImage =0;
		int acceleration =0;
		int speed =0;
		int delay =0;
		int hitpoints =EntityType.INVINCIBLE;
		
		for(int i=0; i < parser.getAttributeCount(); i++) { 
		
            if (parser.getAttributeName(i).equals("name")) {
            	name = parser.getAttributeValue(i);
            	
            } else if (parser.getAttributeName(i).equals("image")) {
            	int val = parser.getAttributeResourceValue(i, -1);
            	if(val!=-1) image = val;
            	
            } else if (parser.getAttributeName(i).equals("invincibleImage")) {
            	int val = parser.getAttributeResourceValue(i, -1);
            	if(val!=-1) invincibleImage = val;
            	
            } else if (parser.getAttributeName(i).equals("acceleration")) {
            	acceleration = parser.getAttributeIntValue(i, -1);
            	
            } else if (parser.getAttributeName(i).equals("speed")) {
            	speed = parser.getAttributeIntValue(i, -1);
            	
            } else if (parser.getAttributeName(i).equals("delay")) {
            	delay = parser.getAttributeIntValue(i, -1);
            	
            } else if (parser.getAttributeName(i).equals("hitpoints")) {
            	hitpoints = parser.getAttributeIntValue(i, -1);
            }
		}
	
	   shooterEngineContext.mPlayerType =new PlayerType(name, shooterEngineContext.mContext.getResources().getDrawable(image),
			                                            shooterEngineContext.mContext.getResources().getDrawable(invincibleImage),
			                                            acceleration, speed, delay, hitpoints);
       return shooterEngineContext.mPlayerType;
	}
	
    
	private static EntityType parseEnemyType(XmlResourceParser parser, ShooterEngineContext shooterEngineContext, int type) {

		String name = null;
		int image = 0;
		int acceleration =0;
		int speed =0;
		int delay =0;
		int hitpoints =EntityType.INVINCIBLE;
		int lifetime =EntityType.FOREVER;
		int bonusScore =0;
		int bonusGunId =EntityType.BONUSGUN_NONE;
		boolean boss = false;
		
		for(int i=0; i < parser.getAttributeCount(); i++) { 
		
            if (parser.getAttributeName(i).equals("name")) {
            	name = parser.getAttributeValue(i);
            	
            } else if (parser.getAttributeName(i).equals("image")) {
            	int val = parser.getAttributeResourceValue(i, -1);
            	if(val!=-1) image = val;
            
            } else if (parser.getAttributeName(i).equals("acceleration")) {
            	acceleration = parser.getAttributeIntValue(i, -1);
            	
            } else if (parser.getAttributeName(i).equals("speed")) {
            	speed = parser.getAttributeIntValue(i, -1);
            	
            } else if (parser.getAttributeName(i).equals("delay")) {
            	delay = parser.getAttributeIntValue(i, -1);
            	
            } else if (parser.getAttributeName(i).equals("hitpoints")) {
            	hitpoints = parser.getAttributeIntValue(i, -1);
            	
            } else if (parser.getAttributeName(i).equals("lifetime")) {
            	lifetime = parser.getAttributeIntValue(i, -1);
            	
            } else if (parser.getAttributeName(i).equals("bonusScore")) {
            	bonusScore = parser.getAttributeIntValue(i, -1);
            	
            } else if (parser.getAttributeName(i).equals("bonusGun")) {
            	
            	String gunname = parser.getAttributeValue(i);

            	bonusGunId=shooterEngineContext.mEnemyTypes.getEnemyId(gunname);
            	
            } else if (parser.getAttributeName(i).equals("boss")) {
	        	
	        	if (parser.getAttributeValue(i).equals("yes")) {
	        		boss = true;
				}
	        }
		}
		
       EntityType enemyType =new EntityType(name, type, shooterEngineContext.mContext.getResources().getDrawable(image),
    		                                acceleration, speed, delay, hitpoints, lifetime, bonusScore, bonusGunId, boss);
       
       shooterEngineContext.mEnemyTypes.addEnemyType(enemyType);
       return enemyType;
	}
	
	
	private static MovementType parseMovementType(XmlResourceParser parser, ShooterEngineContext shooterEngineContext) {
		
		int movement = MovementType.MOVEMENT_STRAIGHT;
		int direction =0;
		int gravity =0;
		int gravitydirection =0;
		String spline = null;
		int turnspeed =0;
		
		for(int i=0; i < parser.getAttributeCount(); i++) { 
			
	        if (parser.getAttributeName(i).equals("movement")) {
	        	
	        	if (parser.getAttributeValue(i).equals("straight")) {
	        		movement = MovementType.MOVEMENT_STRAIGHT;
	        		
				} else if (parser.getAttributeValue(i).equals("homing")) {
	        		movement = MovementType.MOVEMENT_HOMING;
	        		
				} else if (parser.getAttributeValue(i).equals("homingmissile")) {
	        		movement = MovementType.MOVEMENT_HOMING_MISSILE;
	        		
				} else if (parser.getAttributeValue(i).equals("spline")) {
	        		movement = MovementType.MOVEMENT_SPLINE;
	        		
				}
	        	
	        } else if (parser.getAttributeName(i).equals("direction")) {
	        	direction = parser.getAttributeIntValue(i, -1);
	        
	        } else if (parser.getAttributeName(i).equals("gravity")) {
	        	gravity = parser.getAttributeIntValue(i, -1);
	        
	        } else if (parser.getAttributeName(i).equals("gravitydirection")) {
	        	gravitydirection = parser.getAttributeIntValue(i, -1);
	        
	        } else if (parser.getAttributeName(i).equals("spline")) {
	        	spline = parser.getAttributeValue(i);
        		movement = MovementType.MOVEMENT_SPLINE;
        		
	        } else if (parser.getAttributeName(i).equals("turnspeed")) {
	        	turnspeed = parser.getAttributeIntValue(i, -1);
	        }
		}
		
		MovementType movementType =null;

		switch(movement) {
		
		case MovementType.MOVEMENT_STRAIGHT:
			movementType = new MovementTypeStraight(direction, gravity, gravitydirection);
			break;

		case MovementType.MOVEMENT_HOMING:
			movementType = new MovementTypeHoming();
			break;
			
		case MovementType.MOVEMENT_HOMING_MISSILE:
			movementType = new MovementTypeHomingMissile(direction, turnspeed);
			break;

		case MovementType.MOVEMENT_SPLINE:
			movementType = new MovementTypeSpline(spline);
			break;
		}

		return movementType;
	}
	
	
	private static EnemyGunType parseGun(XmlResourceParser parser, ShooterEngineContext shooterEngineContext) {
		
		String type = null;
		int rate =0;
		int level =0;
		
		for(int i=0; i < parser.getAttributeCount(); i++) { 
			
	        if (parser.getAttributeName(i).equals("type")) {
	        	type = parser.getAttributeValue(i);
	
	        } else if (parser.getAttributeName(i).equals("rate")) {
	        	rate = parser.getAttributeIntValue(i, -1);
	        
	        } else if (parser.getAttributeName(i).equals("level")) {
	        	level = parser.getAttributeIntValue(i, -1);
	      
	        }
		}
		
		//Create movement type
		MovementType movementType = parseMovementType(parser, shooterEngineContext);

		//Find enemy type using name...
		EntityType gun = shooterEngineContext.mEnemyTypes.getEnemyType(type);
				

		if(gun!=null) {
			return new EnemyGunType(gun, rate, level, movementType);
		}
		return null;
	}
	
	private static EnemyParasiteType parseParasite(XmlResourceParser parser, ShooterEngineContext shooterEngineContext) {
		
		String type = null;
		int offsetx =0;
		int offsety =0;
		int parentDeathAttack =0;
		
		for(int i=0; i < parser.getAttributeCount(); i++) { 
			
	        if (parser.getAttributeName(i).equals("type")) {
	        	type = parser.getAttributeValue(i);
	
	        } else if (parser.getAttributeName(i).equals("offsetx")) {
	        	offsetx = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("offsety")) {
	        	offsety = parser.getAttributeIntValue(i, -1);
	        
	        } else if (parser.getAttributeName(i).equals("parentDeathAttack")) {
	        	parentDeathAttack = parser.getAttributeIntValue(i, -1);

	        }
		}
		
		//Find type using name...
		
		EntityType grub = shooterEngineContext.mEnemyTypes.getEnemyType(type);
		
		if(grub!=null) {
			return new EnemyParasiteType(shooterEngineContext, grub, offsetx, offsety, parentDeathAttack);
		}
		return null;
	}
	
	
	private static LevelSections parseLevel(XmlResourceParser parser, ShooterEngineContext shooterEngineContext) {
		
		shooterEngineContext.mLevelSections = new LevelSections(shooterEngineContext);
        return shooterEngineContext.mLevelSections;
	}
	
	private static LevelMap parseLevelMap(XmlResourceParser parser, ShooterEngineContext shooterEngineContext) {
		
		String data = null;
		int width = 0;
		int height = 0;
		int tiles = 0;
		int tilewidth =0;
		int tileheight =0;
		int startxright =0;
		int startybottom =0;
		
		for(int i=0; i < parser.getAttributeCount(); i++) { 
			
	        if (parser.getAttributeName(i).equals("width")) {
	        	width = parser.getAttributeIntValue(i, -1);
	
	        } else if (parser.getAttributeName(i).equals("height")) {
	        	height = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("tiles")) {
				int val = parser.getAttributeResourceValue(i, -1);
				if(val!=-1) tiles = val;
	        	
	        } else if (parser.getAttributeName(i).equals("tilewidth")) {
	        	tilewidth = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("tileheight")) {
	        	tileheight = parser.getAttributeIntValue(i, -1);
	        
	        } else if (parser.getAttributeName(i).startsWith("data")) {
	        	if(data==null)  data = new String(parser.getAttributeValue(i));
	        	else { 
	        		//data = data.concat(",");
	        		data = data.concat(parser.getAttributeValue(i));
	        	}
	        	
	        } else if (parser.getAttributeName(i).equals("startxright")) {
	        	startxright = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("startybottom")) {
	        	startybottom = parser.getAttributeIntValue(i, -1);
	        	
	        }
            
		}
		
		shooterEngineContext.mBitmapWarehouse.add(shooterEngineContext, tiles);
		
        return shooterEngineContext.mLevelSections.setLevelMap(shooterEngineContext, data, width, height, tiles,
        		                                               tilewidth, tileheight, startxright, startybottom);
	}
	
	private static LevelSection parseLevelSection(XmlResourceParser parser, ShooterEngineContext shooterEngineContext) {
		
		int type = LevelSection.SECTION_STILL;
		int length = 0;
		int time =0;
		int numbosses=0;
		
		for(int i=0; i < parser.getAttributeCount(); i++) { 
			
	        if (parser.getAttributeName(i).equals("type")) {
	        	
	        	if (parser.getAttributeValue(i).equals("still")) {
	        		type = LevelSection.SECTION_STILL;
	        		
				} else if (parser.getAttributeValue(i).equals("timedstill")) {
	        		type = LevelSection.SECTION_TIMED_STILL;
	        		
				} else if (parser.getAttributeValue(i).equals("scrollx")) {
	        		type = LevelSection.SECTION_SCROLL_X;
	        		
				} else if (parser.getAttributeValue(i).equals("scrollxup")) {
	        		type = LevelSection.SECTION_SCROLL_X_UP;
	        		
				} else if (parser.getAttributeValue(i).equals("scrollxdown")) {
	        		type = LevelSection.SECTION_SCROLL_X_DOWN;	
				
				} else if (parser.getAttributeValue(i).equals("bossstill")) {
	        		type = LevelSection.SECTION_BOSS_STILL;	
				}
	        	
	        } else if (parser.getAttributeName(i).equals("length") ) {
	        	length = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("time") || parser.getAttributeName(i).equals("speed")) {
	        	time = parser.getAttributeIntValue(i, -1);
	        	
	        }  else if (parser.getAttributeName(i).equals("numbosses")) {
	        	numbosses = parser.getAttributeIntValue(i, -1);
	        	
	        } 
		}
		
        return shooterEngineContext.mLevelSections.addLevelSectionToEnd(shooterEngineContext, type, length, time, numbosses);
	}
	
	
	private static LevelLayer parseLevelLayer(XmlResourceParser parser, ShooterEngineContext shooterEngineContext) {
		
		final int LayerTypeImage = 0;
		final int LayerTypeShapeMap = 1;
		
		int type = LayerTypeImage;
		double relativespeed = 1;
		int start = 0;
		int end =0;
		int image = 0;
		String topx= null;
		String bottomx= null;
		String topy= null;
		String bottomy= null;
		
		for(int i=0; i < parser.getAttributeCount(); i++) { 
			
	        if (parser.getAttributeName(i).equals("type")) {
	        	
	        	if (parser.getAttributeValue(i).equals("image")) {
	        		type = LayerTypeImage;
	        		
				} else if (parser.getAttributeValue(i).equals("shapemap")) {
	        		type = LayerTypeShapeMap;
	        		
				}
	        	
	        } else if (parser.getAttributeName(i).equals("relativespeed") ) {
	        	relativespeed = parser.getAttributeFloatValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("start")) {
	        	
	        	if (parser.getAttributeValue(i).equals("left")) {
	        		start = LevelLayer.SECTION_START_LEFT;
	        	}
	        	else {
	        		start = parser.getAttributeIntValue(i, -1);
	        	}
	       
	        } else if (parser.getAttributeName(i).equals("end")) {
	        	
	        	if (parser.getAttributeValue(i).equals("forever")) {
	        		end = LevelLayer.SECTION_FOREVER;
	        	}
	        	else {
	        		end = parser.getAttributeIntValue(i, -1);
	        	}
	        	
	        } else if (parser.getAttributeName(i).equals("image")) {
				int val = parser.getAttributeResourceValue(i, -1);
				if(val!=-1) image = val;
				
	        } else if (parser.getAttributeName(i).equals("topx")) {
	        	topx = parser.getAttributeValue(i);
	        	
	        } else if (parser.getAttributeName(i).equals("bottomx")) {
	        	bottomx = parser.getAttributeValue(i);
	        	
	        } else if (parser.getAttributeName(i).equals("topy")) {
	        	topy = parser.getAttributeValue(i);
	        	
	        } else if (parser.getAttributeName(i).equals("bottomy")) {
	        	bottomy = parser.getAttributeValue(i);			
	        }
	        	
		}
		
		shooterEngineContext.mBitmapWarehouse.add(shooterEngineContext, image);
				
		switch (type) {
		
		case LayerTypeImage:
			
			return new LevelLayerImage(shooterEngineContext, relativespeed, start, end, image);
		 
		case LayerTypeShapeMap:
			
			return new LevelLayerShapeMap(shooterEngineContext, relativespeed, start, end, image,
                    					  topx, bottomx, topy, bottomy);
		}
		
		return null;
	}
	
	
	private static EnemyResource parseEnemyInstance(XmlResourceParser parser, ShooterEngineContext shooterEngineContext) {
		
		String type = null;
		int begin = -1;
		int x = -1;
		int y = -1;
		int repeat = 0;
		int repeatdelay =0;
		
		for(int i=0; i < parser.getAttributeCount(); i++) { 
			
			if (parser.getAttributeName(i).equals("type")) {
				type = parser.getAttributeValue(i);
	
	        } else if (parser.getAttributeName(i).equals("begin")) {
	        	begin = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("x")) {
	        	x = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("y")) {
	        	y = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("repeat")) {
	        	repeat = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("repeatdelay")) {
	        	repeatdelay = parser.getAttributeIntValue(i, -1);
	        	
	        }
		}
		
		//Create movement type
	    MovementType movementType = parseMovementType(parser, shooterEngineContext);

		//Find enemy type using name...
		EntityType enemy = shooterEngineContext.mEnemyTypes.getEnemyType(type);
		
		if(enemy!=null) {
			return new EnemyResource(enemy, begin, x, y, movementType, repeat, repeatdelay);
		}
		return null;
	}
	
	
	private static void parseHudEntry(XmlResourceParser parser, ShooterEngineContext shooterEngineContext) {
		
		int type = HudEntryValue.HUD_ENTRY_SCORE;
		int x = -1;
		int y = -1;
		int speed = 0;
		String font = null;
		int size =0;
		int color =0;
		
		for(int i=0; i < parser.getAttributeCount(); i++) { 
			
			if (parser.getAttributeName(i).equals("type")) {
	        	
	        	if (parser.getAttributeValue(i).equals("score")) {
	        		type = HudEntryValue.HUD_ENTRY_SCORE;
	        		
				} else if (parser.getAttributeValue(i).equals("levelposition")) {
	        		type = HudEntryValue.HUD_ENTRY_LEVEL_POS;
	        		
				} else if (parser.getAttributeValue(i).equals("levelsection")) {
	        		type = HudEntryValue.HUD_ENTRY_LEVEL_SECTION;
	        			
				} else if (parser.getAttributeValue(i).equals("fps")) {
	        		type = HudEntryValue.HUD_ENTRY_FPS;
	        	
				} else if (parser.getAttributeValue(i).equals("lives")) {
	        		type = HudEntryValue.HUD_ENTRY_LIVES;
	        		
				} else if (parser.getAttributeValue(i).equals("debug")) {
	        		type = HudEntryValue.HUD_ENTRY_DEBUG;
	        		
				} 
	        	
	        } else if (parser.getAttributeName(i).equals("x")) {
	        	x = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("y")) {
	        	y = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("speed")) {
	        	speed = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("font")) {
	        	font = parser.getAttributeValue(i);
	        	
	        } else if (parser.getAttributeName(i).equals("size")) {
	        	size = parser.getAttributeIntValue(i, -1);
	        	
	        } else if (parser.getAttributeName(i).equals("color")) {
	        	color = parser.getAttributeIntValue(i, -1);
	        	
	        }
		}
		
		HudEntryValue hudEntryValue = new HudEntryValue(shooterEngineContext, new Vector2(x, y), type, speed, font, size, color);
		
		shooterEngineContext.mHud.addHudEntry(hudEntryValue);		
	}
	
	
	public static void loadLevelTree(int resource, ShooterEngineContext shooterEngineContext) {
       
		/*if (levels.size() > 0 && mLoadedResource == resource) {
        	// already loaded
        	return;
        }*/
        
    	XmlResourceParser parser = shooterEngineContext.mContext.getResources().getXml(resource);
            	
        try { 
            int eventType = parser.getEventType();
            
            EntityType entityType = null;
            LevelSections levelSections = null;
            LevelSection levelSection = null;
            LevelMap levelMap = null;
            LevelLayer levelLayer =null;
            
            while (eventType != XmlPullParser.END_DOCUMENT) { 
                if(eventType == XmlPullParser.START_TAG) { 

                	if (parser.getName().equals("enemy")) {
                		entityType=parseEnemyType(parser, shooterEngineContext, EntityType.TYPE_ENEMY);
                	
                	} else if (parser.getName().equals("bullet")) {
                		entityType=parseEnemyType(parser, shooterEngineContext, EntityType.TYPE_BULLET);
                		
                	} else if (parser.getName().equals("explosion")) {
                		entityType=parseEnemyType(parser, shooterEngineContext, EntityType.TYPE_EXPLOSION);
                		
                	} else if (parser.getName().equals("powerup")) {
                    	entityType=parseEnemyType(parser, shooterEngineContext, EntityType.TYPE_POWERUP);
                    		
                	} else if (parser.getName().equals("playerbullet")) {
                		entityType=parseEnemyType(parser, shooterEngineContext, EntityType.TYPE_PLAYER_BULLET);
                		
                	} else if (parser.getName().equals("player")) {
                		entityType=parsePlayer(parser, shooterEngineContext);
                		
                	} else if (parser.getName().equals("gun") && entityType != null) { 
	                	EnemyGunType g = parseGun(parser, shooterEngineContext);
	            		if(g!=null) entityType.addGunType(g);
	            		
	                } else if (parser.getName().equals("explosiongun") && entityType != null) { 
	                	EnemyGunType g = parseGun(parser, shooterEngineContext);
	            		if(g!=null) entityType.addExplosionGunType(g);
	            		
	                } else if (parser.getName().equals("parasite") && entityType != null) { 
	                	EnemyParasiteType c = parseParasite(parser, shooterEngineContext);
	            		if(c!=null) entityType.addParasiteType(c);
	            			
	                } else if (parser.getName().equals("level")) {
                		levelSections =parseLevel(parser, shooterEngineContext);
                		
	                } else if (parser.getName().equals("levelmap") && levelSections != null) {
                		levelMap =parseLevelMap(parser, shooterEngineContext);
                		
                	} else if (parser.getName().equals("levelsection") && levelSections != null) {
	                	levelSection=parseLevelSection(parser, shooterEngineContext);
	                	
                	} else if (parser.getName().equals("levellayer") && levelSections != null) {
	                	levelLayer=parseLevelLayer(parser, shooterEngineContext);
	                	levelSections.addLevelLayer(levelLayer);
	                	
                	} else if (parser.getName().equals("enemyInstance") && levelSection != null) { 
	                	EnemyResource e = parseEnemyInstance(parser, shooterEngineContext);
	            		if(e!=null) levelSection.addEnemyResourcetoEnd(e);
	                
                	} else if (parser.getName().equals("hudentry")) {
                		parseHudEntry(parser, shooterEngineContext);
            		
                	}
	                
            	}
                eventType = parser.next(); 
            } 
        } catch(Exception e) { 
            //    DebugLog.e("LevelTree", e.getStackTrace().toString()); 
        } finally { 
            parser.close(); 
        } 
    }
}
