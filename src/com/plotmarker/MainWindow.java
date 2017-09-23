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





  public MainWindow() {
    //listen for button clicks
    buttonOpen.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        OpenDialog od = new OpenDialog();
        Optional<File> imageFile =  od.open();
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
            && e.getX() <= imagePanel.getMarkerLeft()+10){
          imagePanel.setMarkerLeft(e.getX());
        }

        else if(e.getX() >= imagePanel.getMarkerRight()-10
            && e.getX() <= imagePanel.getMarkerRight()+10){
          imagePanel.setMarkerRight(e.getX());
        }

        else if(e.getX() >= imagePanel.getScaleLeft()-10
            && e.getX() <= imagePanel.getScaleLeft()+10
            && e.getY() >= imagePanel.getScaleHeight()-10
            && e.getY() <= imagePanel.getScaleHeight()+10){
          imagePanel.setScaleLeft(e.getX());
          imagePanel.setScaleHeight(e.getY());
        }

        else if(e.getX() >= imagePanel.getScaleRight()-10
            && e.getX() <= imagePanel.getScaleRight()+10
            && e.getY() >= imagePanel.getScaleHeight()-10
            && e.getY() <= imagePanel.getScaleHeight()+10){
          imagePanel.setScaleRight(e.getX());
          imagePanel.setScaleHeight(e.getY());
        }

        imagePanel.repaint();

      }
    });
  }

  /**
   * Draws everything that is not the background image
   */



  public static void main(String[] args) {
    mainFrame = new JFrame("PlotMarker");
    mainFrame.setContentPane(new MainWindow().basePanel);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setMinimumSize(new Dimension(300,200));
    mainFrame.setVisible(true);
    mainFrame.setAlwaysOnTop(true);

  }
}
