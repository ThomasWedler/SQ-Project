package gui

import java.awt.event.ActionListener
import scala.swing.event.ActionEvent
import basic.Import
import function.Thomas
import scala.collection.mutable.ListBuffer
import javax.swing.ImageIcon
import javax.swing.JLabel
import scala.swing.Alignment
import javax.swing.JButton
import java.awt.GridLayout
import javax.swing.JPanel
import java.awt.Dimension

// Verwaltung der darzustellenden Daten

class Control {
  
  var Thomas = new Thomas

  val view = new View
  val model = new Model
  
  refresh
  
  def refresh() {
    var list = getImageList
    for (i <- list) {
      view.mid.add(i)
    }
    
    view.panel.updateUI()
  }
  
  def getImageList() = {
	  var list = new ListBuffer[JLabel]
	  var filesystem = Thomas.walkthrough
	  for (f <- filesystem) {
	    var image = new ImageIcon("src/resources/Vlc.png")
	    var label = new JLabel(f.getName(), image, Alignment.Center.id)
	    label.setVerticalTextPosition(Alignment.Bottom.id)
	    label.setHorizontalTextPosition(Alignment.Center.id)
	    list += label
	  }
	  list.toList
  }
  
}