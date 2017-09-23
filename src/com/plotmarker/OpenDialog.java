package com.plotmarker;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Optional;

import javax.swing.JFileChooser;

public class OpenDialog {

  public Optional<File> open() {
    final JFileChooser chooser = new JFileChooser("Choose File");
    chooser.setDialogType(JFileChooser.OPEN_DIALOG);
    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    final File file = new File(".");

    chooser.setCurrentDirectory(file);

    chooser.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)
            || e.getPropertyName().equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
          final File f = (File) e.getNewValue();
        }
      }
    });

    chooser.setVisible(true);
    final int result = chooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      File inputFolderFile = chooser.getSelectedFile();
      String inputFolderStr = inputFolderFile.getPath();
      System.out.println("Path:" + inputFolderStr);
      return Optional.of(new File(inputFolderStr));
    }
    chooser.setVisible(false);
    return Optional.empty();
  }
} 