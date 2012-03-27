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
import java.awt.Color
import java.awt.event.MouseListener
import java.awt.event.MouseEvent
import java.awt.Dimension
import javax.swing.border.LineBorder
import optional.Thumbnails

// verwaltet die darzustellenden Daten

class Model {
  
  var thomas = new Thomas
  var thumbnail = new Thumbnails
  
  var imageList = getImageList
  var overviewList = getOverviewList

  var relationList = new ListBuffer[JLabel]
  
//  def filter(isIn: (Int) => Boolean) {
//    items.foreach(i => if(isIn(i))
//  }
//  
//  
//  def myIsIn(item: Int): Boolean =
//    item < 5
//  
//  var l = 1 :: 2 :: 3 :: 4 :: 5 :: Nil
//  var l2 = List(1,2,3,4,5)
//  
//  var myFunctionVariable: (Int) => Boolean = myIsIn 
//  
//  var l3 = l.filter(_ < 5)
//  
//  var x = 3
//  l3.append(3)
    
  // walks through the filesystem and creates a list containing a label for each file
  def getImageList = {
	  var list = new ListBuffer[JLabel]
	  var filesystem = thomas.walkthrough
	  if (!filesystem.isEmpty) {
		  for (f <- filesystem) {
		    var image = new ImageIcon("src/resources/Vlc.png")
		    if (!thumbnail.isThumb(f.getName))
		    	thumbnail.mkThumb(f.getName)
		    if (!thomas.getType(f).equals("txt")) {
		    	image = new ImageIcon(thumbnail.getThumb(f.getName))
		    } 
		    var label = new JLabel(f.getName, image, Alignment.Center.id)
		    var labelLong = new JLabel(f.getName)
		    label.setVerticalTextPosition(Alignment.Bottom.id)
		    label.setHorizontalTextPosition(Alignment.Center.id)
		    label.setFocusable(false)
		    label.setPreferredSize(new Dimension(100, label.getHeight()))
		    list += label
		  }
	  }
	  fill(list.toList, 36)
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
    checkContent("  Relations", "txt", list)
    checkContent("  JPG", "jpg", list)
    checkContent("  MP4", "mp4", list)
    checkContent("  PDF", "pdf", list)
	fill(list.toList, 34)
  }
  
  // checks the filesystem for directory contents and adds them to the overview list
  def checkContent(name: String, s: String, list: ListBuffer[JLabel]) = {
    var counter = 0
	var filesystem = thomas.walkthrough
	list += new JLabel(name)
	for (f <- filesystem) {
	  if (thomas.getType(f).equals(s)) {
		  list += new JLabel("      " + f.getName)
		  counter += 1
	  }
	}
    if (counter == 0)
      list += new JLabel("      <empty>")
    list += new JLabel("")
    list
  }
  
  // fills the relation list from the image icon content
  def getRelationList = {
	for (i <- imageList) {
	  if (i.isEnabled) {
	    if (!relationList.contains(i))
	      relationList += i
	  } else {
	    if (relationList.contains(i))
	      relationList -= i
	  }
	}
	fill(relationList.toList, 26)
  }
  
}