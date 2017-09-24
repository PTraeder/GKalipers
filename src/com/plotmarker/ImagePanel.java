package com.plotmarker;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel{
  private BufferedImage image;

  public int getMarkerLeft() {
    return markerLeft;
  }

  public void setMarkerLeft(int markerLeft) {
    this.markerLeft = markerLeft;
    markerMiddle = Math.abs(markerLeft+markerRight)/2;
  }

  public int getMarkerRight() {
    return markerRight;
  }

  public void setMarkerRight(int markerRight) {
    this.markerRight = markerRight;
    markerMiddle = Math.abs(markerLeft+markerRight)/2;
  }

  public void setScaleLeft(int scaleLeft) {
    this.scaleLeft = scaleLeft;
  }

  public void setScaleHeight(int scaleHeight) {
    this.scaleHeight = scaleHeight;
  }

  public void setScaleRight(int scaleRight) {
    this.scaleRight = scaleRight;
  }

  public int getScaleLeft() {
    return scaleLeft;
  }

  public int getScaleRight() {
    return scaleRight;
  }

  public int getScaleHeight() {
    return scaleHeight;
  }

  private int MARKERRADIUS =  10;
  private int BIGSTROKE =  2;
  private int SMALLSTROKE =  1;
  private  Color MARKERCOLOR = new Color(0,0,255);

  private int scaleLeft = 20;
  private int scaleRight = 80;
  private int scaleHeight = 20;

  private int markerLeft = 100,markerRight =200,markerMiddle = (markerLeft+markerRight)/2;

  public JLabel scaleLabel= new JLabel("1000 ms"),caliperLabel = new JLabel(getCaliperLabelText()) ;

  public ImagePanel() {
    add(scaleLabel);
    add(caliperLabel);
    //register listeners
  }

  private String getCaliperLabelText(){
    return String.format("%.0f",1000*(Math.abs(markerRight-markerLeft)/ (float)Math.abs(scaleRight-scaleLeft)))+" ms";
  }

  public void loadImage(File imageSource){
    try {
      image = ImageIO.read(imageSource);
    } catch (IOException ex) {
      // handle exception...
    }
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(image, 0, 0, this);
    drawInterface(g);
  }

  public Dimension getImageDimensions(){
    return new Dimension(image.getWidth(),image.getHeight());
  }

  private void drawInterface(Graphics g){
    //draw markers:
    //set color
    g.setColor(MARKERCOLOR);
    ((Graphics2D)g).setStroke(new BasicStroke(BIGSTROKE));
    //draw Calipers
    drawMarker(markerLeft,g);
    drawMarker(markerRight,g);
    //Draw line between them at 20% height from bottom of panel
    int lineHeight = (int) (getHeight()*0.8);
    g.drawLine(markerLeft,lineHeight,markerRight,lineHeight);
    //draw lighter vertical line in the middle of the two markers
    ((Graphics2D)g).setStroke(new BasicStroke(SMALLSTROKE));
    g.drawLine(markerMiddle,0,markerMiddle,getHeight());
    //draw scale
    ((Graphics2D)g).setStroke(new BasicStroke(BIGSTROKE));
    g.drawLine(scaleLeft, scaleHeight, scaleRight, scaleHeight);
    g.drawLine(scaleLeft, scaleHeight -5, scaleLeft, scaleHeight +5);
    g.drawLine(scaleRight, scaleHeight -5, scaleRight, scaleHeight +5);
    //update text for scale
    scaleLabel.setLocation(scaleLeft,scaleHeight);
    //update caliper text
    caliperLabel.setText(getCaliperLabelText());
    caliperLabel.setLocation(markerLeft,lineHeight);

  }

  private void drawMarker(int marker,Graphics g){
    //draw vertical line spanning all of g
    g.drawLine(marker,0,marker,getHeight());
  }

}