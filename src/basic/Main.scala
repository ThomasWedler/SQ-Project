package basic

import java.io.File
import javax.swing.JOptionPane
import gui.Control

object Main {
  
  // initialisation
  def main(args: Array[String]) {
	  createFilesystem
	  // comment out for vlc-debugging -> in most cases problems are related to 32/64bit issues 
	  //System.load("C:\\Program Files (x86)\\VideoLAN\\VLC\\libvlc.dll")
	  new Control
  }
  
  // on startup, checks if a filesystem already exists. if not, it creates one
  def createFilesystem = {
    var jpg = new File("filesystem/jpg")
    var pdf = new File("filesystem/pdf")
    var mp4 = new File("filesystem/mp4")
    var txt = new File("filesystem/relations")
    var tjpg = new File("filesystem/thumbnails/jpg")
    var tpdf = new File("filesystem/thumbnails/pdf")
    var tmp4 = new File("filesystem/thumbnails/mp4")
    if (!jpg.exists || !pdf.exists || !mp4.exists || !txt.exists || !tjpg.exists || !tpdf.exists || !tmp4.exists) {
	    if (!jpg.exists)
	    	jpg.mkdirs
	    if (!pdf.exists)
	    	pdf.mkdirs
	    if (!mp4.exists)
	    	mp4.mkdirs
	    if (!txt.exists)
	    	txt.mkdirs
	    if (!tjpg.exists)
	    	tjpg.mkdirs
    	if (!tpdf.exists)
    		tpdf.mkdirs
    	if (!tmp4.exists)
    		tmp4.mkdirs
	    JOptionPane.showMessageDialog(null, "The filesystem was successfully created.", "Success", JOptionPane.INFORMATION_MESSAGE)
    }
  }
  
}