import java.awt.*;

package com.arch.cubo;

public class Text extends GameObject{
  
  String text;
  Color color;
  float size;
  
  public Text(int x1, int y1, String s1, Color c1, float s2){
    super(x1, y1);
    text = s1;
    color = c1;
    size = s2;
  }
  
  public Text(int x1, int y1, String s1, Color c1){
    super(x1, y1);
    text = s1;
    color = c1;
    size = 16;
  }
}
