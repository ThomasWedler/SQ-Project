package gui
import function.Thomas
import scala.collection.mutable.ListBuffer
import function.Thomas
import javax.swing.JLabel
import function.Thomas
import javax.swing.ImageIcon
import scala.swing.Alignment
import javax.swing.JPanel
import java.io.File

// verwaltet die darzustellenden Daten

class Model {
  
  var thomas = new Thomas
  
  var imageList = getImageList
  var overviewList = getOverviewList
    
  // walks through the filesystem and creates a list containing a label for each file
  def getImageList = {
	  var list = new ListBuffer[JLabel]
	  var filesystem = thomas.walkthrough
	  for (f <- filesystem) {
	    var name = shorten(f.getName(), 18)
	    var image = new ImageIcon("src/resources/Vlc.png")
	    var label = new JLabel(name, image, Alignment.Center.id)
	    label.setVerticalTextPosition(Alignment.Bottom.id)
	    label.setHorizontalTextPosition(Alignment.Center.id)
	    list += label
	  }
	  fill(list.toList, 36)
  }
  
  // shortens a string if it has more than a given number of chars
  def shorten(s: String, lenght: Int) = {
    var result = s
	if (s.length() > lenght)
		result = s.substring(0, lenght-2) + "..."
	result
  }
  
  // if the list is smaller than a given number of items, it fills the empty cells with labels
  def fill(list: List[JLabel], count: Int) = {
    var filledList = list.toBuffer
    var diff = 0
    var length = list.length
    if (length <= count) 
      diff = count - length
    for (i <- 1 to diff)
      filledList += new JLabel("")
    filledList.toList
  }
  
  // fills the overview panel with the right filenames 
  def getOverviewList = {
    var list = new ListBuffer[JLabel]
    list += new JLabel("")
    checkContent("  JPG", "jpg", list)
    checkContent("  PDF", "pdf", list)
    checkContent("  MP4", "mp4", list)
    checkContent("  Relations", "txt", list)
	fill(list.toList, 19)
  }
  
  // checks the filesystem for directory contents and adds them to the overview list
  def checkContent(name: String, s: String, list: ListBuffer[JLabel]) = {
    var counter = 0
	var filesystem = thomas.walkthrough
	list += new JLabel(name)
	for (f <- filesystem) {
	  if (thomas.getType(f).equals(s)) {
		  list += new JLabel("      " + shorten(f.getName, 20))
		  counter += 1
	  }
	}
    if (counter == 0)
      list += new JLabel("      <empty>")
    list += new JLabel("")
    list
  }
  
}