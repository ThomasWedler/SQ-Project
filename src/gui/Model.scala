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
        var image = new ImageIcon("src/resources/rel.png")
        if (!thumbnail.isThumb(f.getName) && !thomas.getType(f).equals("mp4"))
          thumbnail.mkThumb(f.getName)
        if (thomas.getType(f).equals("mp4")) {
          image = new ImageIcon("src/resources/Vlc.png")
        }
        if (!thomas.getType(f).equals("txt") && !thomas.getType(f).equals("mp4")) {
          image = new ImageIcon(thumbnail.getThumb(f.getName))
        }
        var label = new JLabel(f.getName, image, Alignment.Center.id)
        var labelLong = new JLabel(f.getName)
        label.setVerticalTextPosition(Alignment.Bottom.id)
        label.setHorizontalTextPosition(Alignment.Center.id)
        label.setFocusable(false)
                label.setMinimumSize(new Dimension(100, 125))
        label.setMaximumSize(new Dimension(100, 125))
                label.setPreferredSize(new Dimension(100, 125))
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
    for (i <- 1 to diff) {
      var label = new JLabel("")
      label.setFocusable(false)
      filledList += label
    }
    filledList.toList
  }

  // 
  def fill(list: ListBuffer[JLabel], count: Int) = {
    var filledList = list
    var diff = 0
    var length = list.length
    if (length <= count)
      diff = count - length
    for (i <- 1 to diff) {
      var label = new JLabel("")
      label.setFocusable(false)
      filledList += label
    }
    filledList
  }

  // fills the overview panel with the right filenames 
  def getOverviewList = {
    var list = new ListBuffer[JLabel]
    list += new JLabel("")
    checkContent("  Relations", "txt", list)
    checkContent("  JPG", "jpg", list)
    checkContent("  MP4", "mp4", list)
    checkContent("  PDF", "pdf", list)
    var newlist = fill(list.toList, 34)
    for (n <- newlist)
      n.setFocusable(false)
    newlist.toList
  }

  // checks the filesystem for directory contents and adds them to the overview list
  def checkContent(name: String, s: String, list: ListBuffer[JLabel]) = {
    var counter = 0
    var filesystem = thomas.walkthrough
    list += new JLabel(name)
    for (f <- filesystem) {
      if (thomas.getType(f).equals(s)) {
        var label = new JLabel("      " + f.getName)
        list += label
        counter += 1
      }
    }
    if (counter == 0)
      list += new JLabel("      <empty>")
    list += new JLabel("")
    list
  }

}