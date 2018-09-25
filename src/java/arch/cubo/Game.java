import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.*;
import java.io.File;
import javax.imageio.ImageIO;



public class Game extends JFrame {
  
 
  static final long serialVersionUID = 100;
  
  boolean isRunning = true;
  boolean isLose = false;
  boolean isCount = false;
  boolean isBlue = false;
  int fps = 30;
  int wWidth = 480;
  int wHeight = 360;
  int count = 0;
 
  BufferedImage backBuffer;
  Insets insets;
  InputHandler input;
 
  Player p;
  ArrayList<GameObject> wall = new ArrayList<GameObject>();
  ArrayList<GameObject> lava = new ArrayList<GameObject>();
  ArrayList<GameObject> orange = new ArrayList<GameObject>();
  ArrayList<GameObject> blue = new ArrayList<GameObject>();
  ArrayList<GameObject> goo = new ArrayList<GameObject>();
  ArrayList<GameObject> breakWall = new ArrayList<GameObject>();
  ArrayList<Spike> spikeUp = new ArrayList<Spike>();
  ArrayList<Spike> spikeDown = new ArrayList<Spike>();
  ArrayList<Spike> spikeRight = new ArrayList<Spike>();
  ArrayList<Spike> spikeLeft = new ArrayList<Spike>();
  ArrayList<Text> text = new ArrayList<Text>();
  GameObject goal;
 
  public static void main(String[]args)throws Exception{
    Game game = new Game();
    game.initialize();
    game.menu();
    game.isCount = true;
    do{game.scene1();}while(game.isLose);
    do{game.scene2();}while(game.isLose);
    do{game.scene3();}while(game.isLose);
    do{game.scene4();}while(game.isLose);
    do{game.scene5();}while(game.isLose);
    do{game.scene6();}while(game.isLose);
    do{game.scene7();}while(game.isLose);
    //do{game.scene8();}while(game.isLose);
    game.isCount = false;
    do{game.sceneEnd();}while(game.isLose);
    System.exit(0);
  }
 
  void initialize() throws Exception{
    setTitle("Cubo's Adventure");
    setSize(wWidth, wHeight);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    setIconImage(ImageIO.read(new File("assets/icon.png")));
    insets = getInsets();
    setSize(insets.left + wWidth + insets.right, insets.top + wHeight + insets.bottom);
    backBuffer = new BufferedImage(wWidth, wHeight, BufferedImage.TYPE_INT_RGB);
    input = new InputHandler(this);
    p = new Player();
    goal = new GameObject(0, wHeight+1, 30, 30);
  }
 
  public void run(Color back) throws Exception{
    while(isRunning){
      long time = System.currentTimeMillis();
      update();
      draw(back);
      time = (1000 / fps) - (System.currentTimeMillis() - time);
      if (time > 0){try{Thread.sleep(time);}catch(Exception e){}}
    }
    isRunning = true;
  }
 
  void update()throws Exception{
    if(input.isKeyDown(KeyEvent.VK_RIGHT)||input.isKeyDown(KeyEvent.VK_D)){p.right();}
    if(input.isKeyDown(KeyEvent.VK_LEFT)||input.isKeyDown(KeyEvent.VK_A)){p.left();}
    boolean[] b = p.touchArray(breakWall);
    for (int i = 0; i < breakWall.size(); i++){if (breakWall.get(i).t>0&&(!b[i]||breakWall.get(i).t<90)){
      breakWall.get(i).t--;if(breakWall.get(i).t==0){breakWall.remove(i);}}}
    ArrayList<GameObject> allWalls = new ArrayList<GameObject>();
    for (int i = 0; i < wall.size(); i++){allWalls.add(wall.get(i));}
    for (int i = 0; i < breakWall.size(); i++){allWalls.add(breakWall.get(i));}
    p.phis(allWalls, goo, input);
    isRunning = !p.touchSingle(goal)&&!p.touch(orange)&&!p.touch(blue);
    if (p.touch(blue)){isBlue = true;}
    if (p.touch(lava))lose();
    for (int i = 0; i < spikeUp.size(); i++){if (p.touchSingle(spikeUp.get(i))){lose();break;}}
    for (int i = 0; i < spikeDown.size(); i++){if (p.touchSingle(spikeDown.get(i))){lose();break;}}
    for (int i = 0; i < spikeRight.size(); i++){if (p.touchSingle(spikeRight.get(i))){lose();break;}}
    for (int i = 0; i < spikeLeft.size(); i++){if (p.touchSingle(spikeLeft.get(i))){lose();break;}}
  }
 
  void lose() throws Exception{
    isLose = true;
    count++;
    isRunning = false;
  }
 
  public void menu()throws Exception{
    isLose = false;
    p.set(wWidth, wHeight);
    wall.add(new GameObject(0, 0, 40, wHeight));
    wall.add(new GameObject(wWidth-40, 0, 40, wHeight));
    wall.add(new GameObject(0, wHeight-40, wWidth, 40));
    wall.add(new GameObject(0, 0, wWidth, 40));
    goal.set(wWidth, wHeight);
    /* //experimental button code
    JButton start = new JButton("Start Game");
    JButton credits = new JButton("Credits");
    */
    text.add(new Text(98, 85, "Cubo's Adventure", Color.RED, 36));
    /* //more experimental button code
    add(start);
    start.setBounds(300, 220, 40, 15);
    start.setVisible(true);
    add(credits);
    credits.setBounds(300, 250, 40, 15);
    credits.setVisible(true);
    
    System.out.println(start.getX()+" "+start.getY()+" "+start.getWidth()+" "+start.getHeight());
    System.out.println(credits.getX()+" "+credits.getY()+" "+credits.getWidth()+" "+credits.getHeight());
    */                   
    while(!input.isKeyDown(KeyEvent.VK_ENTER)){draw(Color.BLUE);}
    clear();
  }
 
  void scene1()throws Exception{
    isLose = false;
    p.set(61, 302);
    wall.add(new GameObject(0, 0, 25, wHeight));
    wall.add(new GameObject(wWidth-25, 0, 25, wHeight));
    wall.add(new GameObject(0, 0, wWidth, 25));
    wall.add(new GameObject(0, wHeight-25, wWidth, 25));
    wall.add(new GameObject(190, 130, 90, 205));
    text.add(new Text(295, 190, "Touch The Goal To Win", new Color(194, 194, 255), 14));
    goal.set(405, 295);
    run(Color.WHITE);
    clear();
  }
 
  void scene2()throws Exception{
    isLose = false;
    p.set(61, 302);
    wall.add(new GameObject(0, 0, 25, wHeight));
    wall.add(new GameObject(wWidth-25, 0, 25, wHeight));
    wall.add(new GameObject(0, 0, wWidth, 25));
    wall.add(new GameObject(0, wHeight-25, 175, 25));
    wall.add(new GameObject(275, wHeight-25, 205, 25));
    wall.add(new GameObject(125, 140, 45, wHeight-25-140));
    wall.add(new GameObject(280, 190, 65, wHeight-25-190));
    lava.add(new GameObject(176, wHeight-15, 98, 15));
    text.add(new Text(100, 90, "Don't Touch The Lava; It Will Kill You", new Color(194, 194, 255)));
    goal.set(405, 295);
    run(Color.WHITE);
    clear();
  }
 
  void scene3()throws Exception{
    isLose = false;
    p.set(61, 302);
    wall.add(new GameObject(0, 0, 25, wHeight));
    wall.add(new GameObject(wWidth-25, 0, 25, wHeight));
    wall.add(new GameObject(0, 0, wWidth, 25));
    wall.add(new GameObject(0, wHeight-25, 130, 25));
    wall.add(new GameObject(185, wHeight-25, wWidth-25-185, 25));
    wall.add(new GameObject(95, 230, 30, wHeight-25-230));
    wall.add(new GameObject(190, 205, 35, wHeight-25-205));
    wall.add(new GameObject(290, 180, 35, wHeight-25-180));
    lava.add(new GameObject(131, wHeight-16, 53, 15));
    spikeUp.add(new Spike(226, 310, 63, wHeight-26-310, 4));
    text.add(new Text(150, 150, "Spikes Will Also Kill You", new Color(194, 194, 255)));
    goal.set(405, 295);
    run(Color.WHITE);
    clear();
  }
 
  void scene4()throws Exception{
    isLose = false;
    p.set(61, 302);
    wall.add(new GameObject(0, 0, 25, wHeight));
    wall.add(new GameObject(wWidth-25, 0, 25, wHeight));
    wall.add(new GameObject(0, 0, wWidth, 25));
    wall.add(new GameObject(0, wHeight-25, 175, 25));
    wall.add(new GameObject(235, wHeight-25, wWidth-25-235, 25));
    wall.add(new GameObject(115, 215, 50, wHeight-25-215));
    wall.add(new GameObject(245, 235, wWidth-25-245, 25));
    wall.add(new GameObject(245, 260, 30, 30));
    lava.add(new GameObject(176, wHeight-15, 58, 15));
    spikeRight.add(new Spike(166, 230, 12, wHeight-25-230, 11));
    text.add(new Text(145, 105, "Timing Is Everything; This Is Possible", new Color(194, 194, 255)));
    goal.set(405, 295);
    run(Color.WHITE);
    clear();
  }
 
  void scene5()throws Exception{
    isLose = false;
    p.set(61, 302);
    wall.add(new GameObject(0, 0, 25, wHeight));
    wall.add(new GameObject(wWidth-25, 0, 25, wHeight));
    wall.add(new GameObject(0, 0, wWidth, 25));
    wall.add(new GameObject(0, wHeight-25, wWidth, 25));
    wall.add(new GameObject(25, 160, 140, 25));
    wall.add(new GameObject(290, 65, 35, wHeight-25-65));
    wall.add(new GameObject(155, 240, 135, 30));
    wall.add(new GameObject(230, 65, 60, 25));
    spikeRight.add(new Spike(326, 75, 10, wHeight-25-75, 36));
    goal.set(405, 295);
    run(Color.WHITE);
    clear();
  }
 
  void scene6()throws Exception{
    isLose=false;
    scene6_1(41, 302);
  }
 
  void scene6_1(int x, int y)throws Exception{
    isRunning = true;
    isBlue = false;
    p.set(x, y);
    wall.add(new GameObject(0, 0, 25, wHeight));
    wall.add(new GameObject(0, 0, wWidth, 25));
    wall.add(new GameObject(0, wHeight-25, 105, 25));
    wall.add(new GameObject(80, 135, 25, 200));
    wall.add(new GameObject(394, 235, wWidth-25-394, 8));
    wall.add(new GameObject(wWidth-25, 235, 25, wHeight-235));
    wall.add(new GameObject(225, 310, 115, 10));
    lava.add(new GameObject(106, wHeight-15, wWidth-25-107, 15));
    spikeDown.add(new Spike(394, 244, wWidth-25-395, 21, 12));
    orange.add(new GameObject(wWidth-10, 26, 10, 208));
    text.add(new Text(120, 90, "Orange Walls Will Take You To", new Color(215, 215, 255)));
    text.add(new Text(120, 110, "The Next Part Of The Level", new Color(215, 215, 255)));
    run(Color.WHITE);
    y = p.y;
    clear();
    if(!isLose)scene6_2(y);
  }
 
  void scene6_2(int y)throws Exception{
    isRunning = true;
    p.set(11, y);
    wall.add(new GameObject(0, 0, wWidth, 25));
    wall.add(new GameObject(0, 235, 25, wWidth-25-235));
    wall.add(new GameObject(0, wHeight-25, 360, 25));
    wall.add(new GameObject(wWidth-30, wHeight-25, 30, 25));
    wall.add(new GameObject(wWidth-25, 0, 25, wHeight));
    wall.add(new GameObject(150, 65, 35, wHeight-25-65));
    wall.add(new GameObject(275, 25, 40, 275));
    lava.add(new GameObject(361, wHeight-15, 88, 15));
    spikeRight.add(new Spike(186, 85, 30, wHeight-26-85, 46));
    spikeLeft.add(new Spike(110, 230, 39, wHeight-26-230, 12));
    blue.add(new GameObject(0, 26, 10, 208));
    goal.set(405, 295);
    run(Color.WHITE);
    y = p.y;
    clear();
    if(isBlue)scene6_1(wWidth-41, y);
  }
 
  void scene7()throws Exception{
    isLose = false;
    p.set(61, 302);
    wall.add(new GameObject(0, 0, 25, wHeight));
    wall.add(new GameObject(wWidth-25, 0, 25, wHeight));
    wall.add(new GameObject(0, 0, wWidth, 25));
    wall.add(new GameObject(0, wHeight-25, 170, 25));
    wall.add(new GameObject(235, wHeight-25, 55, 25));
    wall.add(new GameObject(420, wHeight-25, wWidth-25-420, 25));
    wall.add(new GameObject(120, 220, 40, wHeight-25-220));
    wall.add(new GameObject(245, 135, 40, wHeight-25-135));
    wall.add(new GameObject(245, 0, 40, 85));
    lava.add(new GameObject(171, wHeight-15, 63, 15));
    lava.add(new GameObject(291, wHeight-15, 128, 15));
    spikeRight.add(new Spike(286, 145, 20, wHeight-26-145, 17));
    goo.add(new GameObject(155, 220, 10, wHeight-26-220));
    goo.add(new GameObject(240, 135, 10, wHeight-26-135));
    goo.add(new GameObject(240, 26, 10, 59));
    text.add(new Text(40, 140, "You Can't Wall Jump On Goo", new Color(215, 215, 255), 14));
    text.add(new Text(40, 160, "Covered Walls Because They", new Color(215, 215, 255), 14));
    text.add(new Text(40, 180, "Are Too Slippery", new Color(215, 215, 255), 14));
    goal.set(405, 295);
    run(Color.WHITE);
    clear();
  }
 
  void scene8()throws Exception{
    isLose = false;
    scene8_1(41, 302);
  }
 
  void scene8_1(int x, int y)throws Exception{
    isRunning = true;
    isBlue = false;
    p.set(x, y);
    wall.add(new GameObject(0, 0, 25, wHeight));
    wall.add(new GameObject(0, 0, wWidth, 25));
    wall.add(new GameObject(0, wHeight-25, 105, 25));
    wall.add(new GameObject(80, 80, 25, wHeight-80-25));
    wall.add(new GameObject(wWidth-25, 115, 25, wHeight-115));
    wall.add(new GameObject(wWidth-80, 115, 80, 10));
    wall.add(new GameObject(wWidth-25, 26, 25, 14));
    lava.add(new GameObject(106, wHeight-15, wWidth-106-26, 15));
    orange.add(new GameObject(wWidth-10, 41, 15, 73));
    goo.add(new GameObject(106, 80, 9, wHeight-80-16));
    goo.add(new GameObject(wWidth-35, 126, 9, wHeight-126-16));
    breakWall.add(new GameObject(180, 200, 75, 25));
    text.add(new Text(180, 60, "Keys Will Open Paths", new Color(215, 215, 255)));
    text.add(new Text(180, 100, "Grey Blocks Will Break", new Color(215, 215, 255)));
    text.add(new Text(180, 120, "When You Touch Them", new Color(215, 215, 255)));
    run(Color.WHITE);
    y = p.y;
    clear();
  }
 
  void sceneEnd()throws Exception{
    isLose = false;
    p.set(61, 302);
    wall.add(new GameObject(0, 0, 25, wHeight));
    wall.add(new GameObject(wWidth-25, 0, 25, wHeight));
    wall.add(new GameObject(0, 0, wWidth, 25));
    wall.add(new GameObject(0, wHeight-25, wWidth, 25));
    text.add(new Text(210, 80, "Credits:", Color.BLACK));
    text.add(new Text(55, 100, "Josh Cheatum: Author Of The Origional Game Demo On Scratch", Color.BLACK, 13));
    text.add(new Text(55, 120, "TerranceN: Author Of The Initial Game Engine (Our Starting Point)", Color.BLACK, 13));
    text.add(new Text(55, 140, "Josh Cheatum: Head Advisor And Text Font Implementation", Color.BLACK, 13));
    text.add(new Text(55, 160, "Arvid Gustafson: Java Software Development And Implementation", Color.BLACK, 13));
    run(new Color(255, 128, 128));
    clear();
  }
 
  void clear(){
    p.set(0, wHeight+1);
    for (int i = wall.size()-1; i >= 0; i--)
      wall.remove(i);
    for (int i = lava.size()-1; i >= 0; i--)
      lava.remove(i);
    for (int i = spikeUp.size()-1; i >= 0; i--)
      spikeUp.remove(i);
    for (int i = spikeDown.size()-1; i >= 0; i--)
      spikeDown.remove(i);
    for (int i = spikeRight.size()-1; i >= 0; i--)
      spikeRight.remove(i);
    for (int i = spikeLeft.size()-1; i >= 0; i--)
      spikeLeft.remove(i);
    for (int i = orange.size()-1; i >= 0; i--)
      orange.remove(i);
    for (int i = blue.size()-1; i >= 0; i--)
      blue.remove(i);
    for (int i = goo.size()-1; i >= 0; i--)
      goo.remove(i);
    for (int i = breakWall.size()-1; i >= 0; i++)
      breakWall.remove(i);
    for (int i = text.size()-1; i >= 0; i--)
      text.remove(i);
      goal.set(0, wHeight+1);
  }
 
  void draw(Color back) throws Exception{
    Graphics g = getGraphics();
    Graphics bbg = backBuffer.getGraphics();
    bbg.setColor(back);
    bbg.fillRect(0, 0, wWidth, wHeight);
    bbg.setColor(Color.BLACK);
    for (int i = 0; i < wall.size(); i++){
      bbg.fillRect(wall.get(i).x, wall.get(i).y, wall.get(i).l+1, wall.get(i).h+1);
    }
    bbg.setColor(Color.RED);
    for (int i = 0; i < lava.size(); i++){
      bbg.fillRect(lava.get(i).x, lava.get(i).y, lava.get(i).l+1, lava.get(i).h+1);
    }
    for (int i = 0; i < spikeUp.size(); i++){
      for (int j = 0; j < spikeUp.get(i).amount; j++){
        for (float k = 0; k <= spikeUp.get(i).l/spikeUp.get(i).amount/2; k++){
          for (int l = 0; l <= (int)(spikeUp.get(i).h*k/spikeUp.get(i).l*spikeUp.get(i).amount*2+.5); l++){
            bbg.fillRect(spikeUp.get(i).x+j*spikeUp.get(i).l/spikeUp.get(i).amount+(int)(k+.5),
                      spikeUp.get(i).y+spikeUp.get(i).h-l, 2, 1);
            bbg.fillRect(spikeUp.get(i).x+(j+1)*spikeUp.get(i).l/spikeUp.get(i).amount-(int)(k+.5)-1,
                      spikeUp.get(i).y+spikeUp.get(i).h-l, 2, 1);
   }}}}
    for (int i = 0; i < spikeDown.size(); i++){
      for (int j = 0; j < spikeDown.get(i).amount; j++){
        for (float k = 0; k <= spikeDown.get(i).l/spikeDown.get(i).amount/2; k++){
          for (int l = 0; l <= (int)(spikeDown.get(i).h*k/spikeDown.get(i).l*spikeDown.get(i).amount*2+.5); l++){
            bbg.fillRect(spikeDown.get(i).x+j*spikeDown.get(i).l/spikeDown.get(i).amount+(int)(k+.5),
                      spikeDown.get(i).y+l, 2, 1);
            bbg.fillRect(spikeDown.get(i).x+(j+1)*spikeDown.get(i).l/spikeDown.get(i).amount-(int)(k+.5)-1,
                      spikeDown.get(i).y+l, 2, 1);
   }}}}
    for (int i = 0; i < spikeRight.size(); i++){
      for (int j = 0; j < spikeRight.get(i).amount; j++){
        for (float k = 0; k <= spikeRight.get(i).h/spikeRight.get(i).amount/2; k++){
          for (int l = 0; l <= (int)(spikeRight.get(i).l*k/spikeRight.get(i).h*spikeRight.get(i).amount*2+.5); l++){
            bbg.fillRect(spikeRight.get(i).x+l,
                      spikeRight.get(i).y+j*spikeRight.get(i).h/spikeRight.get(i).amount+(int)(k+.5)-1, 1, 2);
            bbg.fillRect(spikeRight.get(i).x+l,
                      spikeRight.get(i).y+(j+1)*spikeRight.get(i).h/spikeRight.get(i).amount-(int)(k+.5)-2, 1, 2);
   }}}}
    for (int i = 0; i < spikeLeft.size(); i++){
      for (int j = 0; j < spikeLeft.get(i).amount; j++){
        for (float k = 0; k <= spikeLeft.get(i).h/spikeLeft.get(i).amount/2; k++){
          for (int l = 0; l <= (int)(spikeLeft.get(i).l*k/spikeLeft.get(i).h*spikeLeft.get(i).amount*2+.5); l++){
            bbg.fillRect(spikeLeft.get(i).x+spikeLeft.get(i).l-l,
                      spikeLeft.get(i).y+j*spikeLeft.get(i).h/spikeLeft.get(i).amount+(int)(k+.5)-1, 1, 2);
            bbg.fillRect(spikeLeft.get(i).x+spikeLeft.get(i).l-l,
                      spikeLeft.get(i).y+(j+1)*spikeLeft.get(i).h/spikeLeft.get(i).amount-(int)(k+.5)-2, 1, 2);
   }}}}
    for (int i = 0; i < text.size(); i++){
      Font currentFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/FORCED SQUARE.ttf"));
      Font newFont = currentFont.deriveFont(currentFont.getSize() * text.get(i).size);
      bbg.setFont(newFont);
      bbg.setColor(text.get(i).color);
      bbg.drawString(text.get(i).text, text.get(i).x, text.get(i).y);
    }
    bbg.setColor(Color.ORANGE);
    for (int i = 0; i < orange.size(); i++){
      bbg.fillRect(orange.get(i).x, orange.get(i).y, orange.get(i).l+1, orange.get(i).h+1);
    }
    bbg.setColor(new Color(215, 215, 255));
    for (int i = 0; i < blue.size(); i++){
      bbg.fillRect(blue.get(i).x, blue.get(i).y, blue.get(i).l+1, blue.get(i).h+1);
    }
    bbg.setColor(new Color(128, 255, 128));
    for (int i = 0; i < goo.size(); i++){
      bbg.fillRect(goo.get(i).x, goo.get(i).y, goo.get(i).l+1, goo.get(i).h+1);
    }
    bbg.setColor(new Color(128, 128, 128));
    for (int i = 0; i < breakWall.size(); i++){
      bbg.fillRect(breakWall.get(i).x, breakWall.get(i).y, breakWall.get(i).l+1, breakWall.get(i).h+1);
    }
    if(isCount){
      bbg.setColor(new Color(200, 200, 200));
      bbg.fillRect(50, 40, 100, 20);
      bbg.setColor(Color.BLACK);
      Font currentFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/FORCED SQUARE.ttf"));
      Font newFont = currentFont.deriveFont(currentFont.getSize() * 18f);
      bbg.setFont(newFont);
      bbg.drawString("Deaths: ", 60, 55);
      bbg.setColor(new Color(0, 128, 0));
      bbg.fillRect(130, 45, 17, 12);
      bbg.setColor(Color.WHITE);
      bbg.drawString(""+count, 132, 55);
    }
    bbg.setColor(new Color(255, 255, 0));
    bbg.fillRect(goal.x, goal.y, goal.l+1, goal.h+1);
    bbg.setColor(p.c);
    bbg.fillRect(p.x, p.y, p.l+1, p.l+1);
    bbg.setColor(Color.BLACK);
    bbg.fillRect(p.x+p.eye, p.y+7, 3, 7);
    bbg.fillRect(p.x+p.eye+6, p.y+7, 3, 7);
  /*if(false){
      bbg.setColor(new Color(255, 128, 128));
      bbg.fillRect(60, 40, wWidth-120, wHeight-80);
      Font currentFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/FORCED SQUARE.ttf"));
      Font newFont = currentFont.deriveFont(currentFont.getSize() * 60f);
      bbg.setFont(newFont);
      bbg.setColor(Color.RED);
      bbg.drawString("You Died", 122, 180);
    }*/
    g.drawImage(backBuffer, insets.left, insets.top, this);
  }
}
