package gui
import function.Thomas
import scala.collection.mutable.ListBuffer
import function.Thomas
import javax.swing.JLabel
import function.Thomas
import javax.swing.ImageIcon
import scala.swing.Alignment

// verwaltet die darzustellenden Daten

class Model {
  
  var Thomas = new Thomas
  
  var imageList = getImageList
    
  // walks through the filesystem and creates a list containing a label for each file
  def getImageList = {
	  var list = new ListBuffer[JLabel]
	  var filesystem = Thomas.walkthrough
	  for (f <- filesystem) {
	    var name = f.getName()
	    if (name.length() > 18) {
	      name = name.substring(0, 16) + "..."
	    }
	    var image = new ImageIcon("src/resources/Vlc.png")
	    var label = new JLabel(name, image, Alignment.Center.id)
	    label.setVerticalTextPosition(Alignment.Bottom.id)
	    label.setHorizontalTextPosition(Alignment.Center.id)
	    list += label
	  }
	  list.toList
  }
  
}