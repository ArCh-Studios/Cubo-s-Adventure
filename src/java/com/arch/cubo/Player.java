import java.awt.*;
import java.awt.event.*;
import java.util.*;

package com.arch.cubo

public class Player{
  
  int speed = 1;
  int jump = 15;
  int l = 30;
  int x = 0;
  int y = 0;
  int eye = 15;
  Color c = new Color(0, 0, 255);
  float xForce = 0f;
  int yForce = 0;
  int gravity = 1;
  
  public void set(int x1, int y1){
    x = x1;
    y = y1;
  }
  
  public void right(){
    eye = 15;
    xForce+=speed;
  }
  
  public void left(){
    eye = 6;
    xForce-=speed;
  }
  
  public boolean touchSingle(GameObject obj){
    boolean b = false;
    for(int j = 0; j <= l; j++){
      if (x+j <= obj.x+obj.l && x+j >= obj.x){
        b = true;
      }
    }
    if (b){
      b = false;
      for(int j = 0; j <= l; j++){
        if (y+j >= obj.y && y+j <= obj.y+obj.h){
          b = true;
        }
      }
    }
    return b;
  }
  
  public boolean[] touchArray(ArrayList<GameObject> obj){
    boolean[] b = new boolean[obj.size()];
    for(int i = 0; i < obj.size(); i++){
      b[i] = touchSingle(obj.get(i));
    }
    return b;
  }
  
  public boolean touch(ArrayList<GameObject> obj){
    boolean[] b = touchArray(obj);
    boolean b1 = false;
    for (int i=0; i<b.length; i++){
      if (b[i])
        b1 = true;
    }
    return b1;
  }
  
  public void phis(ArrayList<GameObject> wall, ArrayList<GameObject> goo, InputHandler input){
    xForce*=.9f;//This simulates drag by decreasing Cubo's horizontal force
    x+=(int)(xForce+.5f);//This is where the x position of the Player is modified by the horizontal force
    boolean isGoo = false;//This comes in later
    if (touch(goo)){//This tests if the player is touching a verticle side of goo 
      y-=1;//Notice that this is in the same script as all of the other physic things are
      if (touch(goo)){
        y-=1;//Also notice that the y value of the Player temporarily changes
        if (touch(goo)){
          y-=1;
          if (touch(goo)){
            y-=1;
            if (touch(goo)){
              y-=1;
              if (touch(goo)){
                xForce*=-2;//This makes the Player go the opposite direction twice as fast as he hit the goo
                y+=5;//This resets the y value
                isGoo = true;//This lets the code know that cubo is touching goo.
              }//isGoo prevents him from wall jumping or stop moving because of wall colisions this frame
            }
          }
        }
      }
    }
    if(!isGoo){//If the player is not touching goo
      if (touch(wall)){//If the player is touching the side of a wall
        y-=1;
        if (touch(wall)){
          y-=1;
          if (touch(wall)){
            y-=1;
            if (touch(wall)){
              y-=1;
              if (touch(wall)){
                y-=1;
                if (touch(wall)){
                  x+=(int)(xForce+.5f)*-1;//Stops the player with newton's third law
                  y+=5;
                  if(input.isKeyDown(KeyEvent.VK_UP)||input.isKeyDown(KeyEvent.VK_W)){//If the up key is also pressed
                    if (xForce > 0f)//If the player is approaching this wall going right
                      xForce=jump/3f*-1f;//The player gose to the left
                    else//If the player is approaching this wall going left
                      xForce=jump/3f;//The player goas right
                    yForce=jump*2/3;//In both cases, the player also goes up
                  }
                  else
                    xForce=0f;//If the up key is not being pressed, the player is stopped
                }
              }
            }
          }
        }
      }
    }
    yForce-=gravity;//Gravity acts on the vertical force of the player
    y-=yForce;//The verticle force of the player effects its y position
    if (!isGoo){//If the player is not touching goo
      if(touch(wall)||touch(goo)){//If the player is in floor or ceiling (because vetrical walls were already handled)
        y+=yForce;//Undo the effects of the verticle force on the y position of the player
        yForce=0;//sets the verticle force of the player to 0
      }
      y+=1;//temporary change in y axis in order to test if the player is on the floor
      if(touch(wall)||touch(goo)){//If it is touching the floor
        if (input.isKeyDown(KeyEvent.VK_UP)||input.isKeyDown(KeyEvent.VK_W)){//If the up key is also pressed
          yForce = jump;//Then the verticle force is equal to Cubo's jumping power
        }
      }
      y-=1;//reverts the temporary change in the y axis
    }
  }
}

