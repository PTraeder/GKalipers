package com.plotmarker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.Optional;

/**
 * Created by philip on 22.09.17.
 */
public class MainWindow {



  private JPanel basePanel;
  private JButton buttonOpen;
  private ImagePanel imagePanel;
  private static JFrame mainFrame;

  private int MARKERLEFT = 1;
  private int MARKERRIGHT = 2;
  private int SCALELEFT  = 3;
  private int SCALERIGHT = 4;

  private int CURRENTLYDRAGGING=0;


  public MainWindow() {
    //listen for button clicks
    buttonOpen.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        OpenDialog od = new OpenDialog();
        Optional<File> imageFile =  od.open(mainFrame);
        if(imageFile.isPresent()){
          //load the image
          imagePanel.loadImage(imageFile.get());
          //resize window
          //apparently i am too dumb to get swing/awt to automatically resize correctly:
          Dimension helperDimension = new Dimension((int)imagePanel.getImageDimensions().getWidth(),(int) imagePanel.getImageDimensions().getHeight()+buttonOpen.getHeight());

          mainFrame.setMinimumSize(helperDimension);
          mainFrame.pack();
        }

      }
    });
    //listen for mouse clicks
    imagePanel.addMouseListener(new MouseAdapter() {
      @Override public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

      }
    });
    imagePanel.addMouseMotionListener(new MouseMotionAdapter() {
      @Override public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if(e.getX() >= imagePanel.getMarkerLeft()-10
            && e.getX() <= imagePanel.getMarkerLeft()+10
            && canDrag(MARKERLEFT)){
          imagePanel.setMarkerLeft(e.getX());
          CURRENTLYDRAGGING = MARKERLEFT;
        }

        else if(e.getX() >= imagePanel.getMarkerRight()-10
            && e.getX() <= imagePanel.getMarkerRight()+10
            && canDrag(MARKERRIGHT)){
          imagePanel.setMarkerRight(e.getX());
          CURRENTLYDRAGGING = MARKERRIGHT;
        }

        else if(e.getX() >= imagePanel.getScaleLeft()-10
            && e.getX() <= imagePanel.getScaleLeft()+10
            && e.getY() >= imagePanel.getScaleHeight()-10
            && e.getY() <= imagePanel.getScaleHeight()+10
            && canDrag(SCALELEFT)){
          imagePanel.setScaleLeft(e.getX());
          imagePanel.setScaleHeight(e.getY());
          CURRENTLYDRAGGING = SCALELEFT;
        }

        else if(e.getX() >= imagePanel.getScaleRight()-10
            && e.getX() <= imagePanel.getScaleRight()+10
            && e.getY() >= imagePanel.getScaleHeight()-10
            && e.getY() <= imagePanel.getScaleHeight()+10
            && canDrag(SCALERIGHT)){
          imagePanel.setScaleRight(e.getX());
          imagePanel.setScaleHeight(e.getY());
          CURRENTLYDRAGGING = SCALERIGHT;
        }

        imagePanel.repaint();

      }
    });
    imagePanel.addMouseListener(new MouseAdapter() {
      @Override public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        CURRENTLYDRAGGING = 0;
      }
    });
    imagePanel.addMouseMotionListener(new MouseMotionAdapter() {
    });
  }

  /**
   * Draws everything that is not the background image
   */



  public static void main(String[] args) {
    mainFrame = new JFrame("GKalipers");
    mainFrame.setContentPane(new MainWindow().basePanel);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setMinimumSize(new Dimension(300,200));
    mainFrame.setVisible(true);
    mainFrame.setAlwaysOnTop(true);

  }

  private boolean canDrag(int element){
    return (CURRENTLYDRAGGING == 0 || CURRENTLYDRAGGING == element);
  }

}

