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
//  var annotation = new c
  
  var undomanager = new UndoManager
  undomanager.setLimit(1000)
  
  addLabels
  refresh
  
  view.name.getDocument().addUndoableEditListener(undomanager)
  
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
	    addLabels
	    refresh
	  }
  })
  
  // add the refresh function
  view.mntmRefresh.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    refresh
	  }
  })
  
  // refreshes the ui
  def refresh {
	  view.panel.updateUI
  }
    
  // adds the from the filesystem created labels to the mid panel
  def addLabels {
    var list = model.getImageList
    view.mid.removeAll
    for (i <- list) {
      view.mid.add(i)
    }
  }
  
  

}