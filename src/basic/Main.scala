package basic

import java.io.File
import javax.swing.JOptionPane
import gui.Control

object Main {
    
  // initialisation
  def main(args: Array[String]) {
	  createFilesystem
//	  new Control
	  // new Annotation
	 new Import
  }
  
  // on startup, checks if a filesystem already exists. if not, it creates one
  def createFilesystem = {
    var jpg = new File("filesystem/jpg")
    var pdf = new File("filesystem/pdf")
    var mp4 = new File("filesystem/mp4")
    var txt = new File("filesystem/relations")
    if (!jpg.exists || !pdf.exists || !mp4.exists || !txt.exists) {
	    if (!jpg.exists)
	    	jpg.mkdirs
	    if (!pdf.exists)
	    	pdf.mkdirs
	    if (!mp4.exists)
	    	mp4.mkdirs
	    if (!txt.exists)
	    	txt.mkdirs
	    JOptionPane.showMessageDialog(null, "The filesystem was successfully created.", "Success", JOptionPane.INFORMATION_MESSAGE)
    }
  }
  
}