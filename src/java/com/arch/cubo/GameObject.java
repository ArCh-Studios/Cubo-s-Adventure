package com.arch.cubo;

public class GameObject{
  
  int x;
  int y;
  int l;
  int h;
  int t = 90;
  
  public GameObject(int x1, int y1, int l1, int h1){
    x = x1;
    y = y1;
    l = l1;
    h = h1;
  }
  
  public GameObject(int x1, int y1){
    x = x1;
    y = y1;
    l = 0;
    h = 0;
  }
  
  public void set(int x1, int y1){
    x = x1;
    y = y1;
  }
}


