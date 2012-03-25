package gui

import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import basic.Import
import function.Chris
import javax.swing.undo.UndoManager
import javax.swing._
import javax.swing.JLabel
import javax.swing.JPanel


// Verwaltung der darzustellenden Daten

class Control {
  
  val view = new View
  val model = new Model
  
  var chris = new function.Chris
  
  var undomanager = new UndoManager
  undomanager.setLimit(1000)
  
  refresh
  
  view.name.getDocument().addUndoableEditListener(undomanager)
  
  // menu item group
  view.mntmGroup.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		setAnnotationPanel("Group")
	  }
  })
  
  // menu item list
  view.mntmList.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		 setAnnotationPanel("Playlist")
	  }
  })
  
  // button group
  view.btnGroup.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		setAnnotationPanel("Group")
	  }
  })
  
  // button list
  view.btnList.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		 setAnnotationPanel("Playlist")
	  }
  })
  
  // undo
  view.mntmUndo.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		if (undomanager.canUndo)
			undomanager.undo
		else
			JOptionPane.showMessageDialog(null, "Cannot undo the last action!", "Cannot Undo", JOptionPane.ERROR_MESSAGE)
	  }
  })
  
  // redo
  view.mntmRedo.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		if (undomanager.canRedo)
			undomanager.redo
		else
			JOptionPane.showMessageDialog(null, "Cannot redo the last action!", "Cannot Redo", JOptionPane.ERROR_MESSAGE);	  
	  }
  })
  
  // adds the quit function
  view.mntmQuit.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		  System.exit(0)
	  }
  })
  
  // adds the import function
  view.mntmOpen.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    new Import
	    refresh
	  }
  })
  
  // add the refresh function
  view.mntmRefresh.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    refresh
	  }
  })
  
  // help contents
  view.mntmHelp.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	  	JOptionPane.showMessageDialog(null, "Help Contents are still under development.", "Help Contents", JOptionPane.INFORMATION_MESSAGE);
	  }
  })
  
  // about
  view.mntmAbout.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	  	JOptionPane.showMessageDialog(null, "Version 1.0.0\n\nVery special thanks to:\n    David Cyborra\n    Kristof Korwisi\n    Thomas Wedler\n    Chris Zimmerer", "About", JOptionPane.INFORMATION_MESSAGE);
	  }
  })
  
  // refreshes the ui
  def refresh {
	  addLabels
	  addOverviewLabels
	  view.panel.updateUI
  }
    
  // adds the from the filesystem created labels to the mid panel
  def addLabels {
    var list = model.getImageList
    view.mid.removeAll
    for (i <- list)
      view.mid.add(i)
    model.imageList = list
  }
  
  // adds the name of the from the filesystem created labels to the overview panel
  def addOverviewLabels {
    var list = model.getOverviewList
    view.overview.removeAll()
    for (i <- list) {
    	view.overview.add(new JLabel(i.getText()))
    }
    model.overviewList = list
  }
  
  // sets the annotation panel right
  def setAnnotationPanel(s: String) {
    	view.annotation.setVisible(true)
    	view.lblName.setText(s + ":")
    	view.name.setText("New " + s)
    	view.name.requestFocus
  }
  
}