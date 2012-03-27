package gui

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.Color
import scala.collection.mutable.ListBuffer
import basic.Import
import basic.Annotation
import javax.swing.border.LineBorder
import javax.swing.undo.UndoManager
import javax.swing.JLabel
import javax.swing.JOptionPane
import java.io.File

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
	    JOptionPane.showMessageDialog(null, "Select Items", "Selection", JOptionPane.INFORMATION_MESSAGE)
	    refresh
		setAnnotationPanel("Group", true)
	 	addLabels(false) 
	  }
  })
  
  // menu item list
  view.mntmList.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		JOptionPane.showMessageDialog(null, "Select Items", "Selection", JOptionPane.INFORMATION_MESSAGE)
	    refresh
		setAnnotationPanel("List", true)
		addLabels(false)
	  }
  })
  
  // button group
  view.btnGroup.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    JOptionPane.showMessageDialog(null, "Select Items", "Selection", JOptionPane.INFORMATION_MESSAGE)
	    refresh
		setAnnotationPanel("Group", true)
		addLabels(false)
	  }
  })
  
  // button list
  view.btnList.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    JOptionPane.showMessageDialog(null, "Select Items", "Selection", JOptionPane.INFORMATION_MESSAGE)
	    refresh
		setAnnotationPanel("List", true)
		addLabels(false)
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
  
  // cancel button in relation mode
  view.btnCancel.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    view.mntmSave.setEnabled(false)
	    refresh
	    setAnnotationPanel(null, false)
	  }
  })
  
  // save relation
  view.btnSave.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		  val yn =  JOptionPane.showConfirmDialog(null,"Would you like to save this realation?", "Save", JOptionPane.YES_NO_OPTION)
		  if (yn == JOptionPane.YES_OPTION) {
		  view.mntmSave.setEnabled(false)
		  var list = convertToFilelist(model.relationList.toList)
		  new Annotation(view.lblName.getText, view.name.getText, list)
		  refresh
		  setAnnotationPanel(null, false)
		  }
	  }
  })
  
  // save relation
  view.mntmSave.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	      view.mntmSave.setEnabled(false)
		  val yn =  JOptionPane.showConfirmDialog(null,"Would you like to save this realation?", "Save", JOptionPane.YES_NO_OPTION)
		  if (yn == JOptionPane.YES_OPTION) {
		      view.mntmSave.setEnabled(false)
			  var list = convertToFilelist(model.relationList.toList)
			  new Annotation(view.lblName.getText, view.name.getText, list)
			  refresh
			  setAnnotationPanel(null, false)
		  }		  
	  }
  })
  
  // refreshes the ui
  def refresh {
	  addLabels(true)
	  addOverviewLabels
	  view.panel.updateUI
  }
    
  // adds the from the filesystem created labels to the mid panel
  def addLabels(enabled: Boolean) {
    var list = model.getImageList
    view.mid.removeAll
    for (i <- list) {
      if (!enabled) {
        i.disable()
        addMouseListenerEnabled(i)
      } else {
        if (!i.getText().isEmpty())
        	addMouseListenerBorder(i)
      }
      view.mid.add(i)
    }
    model.imageList = list
  }
  
  // adds the name of the from the filesystem created labels to the overview panel
  def addOverviewLabels {
    var list = model.getOverviewList
    view.overview.removeAll
    for (i <- list) {
    	view.overview.add(new JLabel(i.getText))
    }
    model.overviewList = list
  }
  
  // adds the name of the labels to the new relation list
  def addRelationLabels(str: String) {
    var list = model.getRelationList
    if (str.equals("Group:")) {
      var l = new ListBuffer[JLabel]
      l += new JLabel("")
      for (i <- model.imageList) {
        if (i.isEnabled)
          l += i
      }
      model.relationList = l
      list = model.fill(l.toList, 26)
    }
    view.relation.removeAll
    for (i <- list) {
      view.relation.add(new JLabel(i.getText))
    }
  }
  
  // sets the annotation panel right
  def setAnnotationPanel(s: String, enabled: Boolean) {
	  	if (enabled)
	  		view.annotation.setVisible(true)
	  	else
	  		view.annotation.setVisible(false)
    	view.lblName.setText(s + ":")
    	view.name.setText("New " + s)
    	view.name.requestFocus
    	if (view.annotation.isVisible()) {
    	    view.relation.removeAll
    		view.mntmRefresh.setEnabled(false)
    		view.mntmSave.setEnabled(true)
    		view.btnPlay.setVisible(true)
    		view.btnSave.setVisible(true)
    		view.btnCancel.setVisible(true)
    		view.sp_relation.setVisible(true)
    		model.relationList = new ListBuffer[JLabel]
    	    model.relationList += new JLabel("")
    	} else {
    		view.mntmRefresh.setEnabled(true)
    		view.btnPlay.setVisible(false)
    		view.btnSave.setVisible(false)
    		view.btnCancel.setVisible(false)
    		view.sp_relation.setVisible(false)
    	}
  }
  
  // adds a mouse listener to a jlabel in the relation mode, that turns them en-/disabled
  def addMouseListenerEnabled(label: JLabel) {
    label.addMouseListener( new MouseListener {
		  def mouseClicked(e:MouseEvent) {
		    if (!label.isEnabled()) {
		    	label.setEnabled(true)
		    } else {
		    	label.setEnabled(false)
		    }
		    addRelationLabels(view.lblName.getText())
		    view.panel.updateUI
		  }
		  def mouseExited (e: MouseEvent) {}
		  def mouseEntered (e: MouseEvent) {}
		  def mouseReleased (e: MouseEvent) {}
		  def mousePressed (e: MouseEvent) {}
	    })
  }
  
  // adds a mouse listener to a jlabel in the non relation mode, that marks them with borders [focusable]
  def addMouseListenerBorder(label: JLabel) {
    label.addMouseListener( new MouseListener {
		  def mouseClicked(e:MouseEvent) {
		    if (!label.isFocusable()) {
			  label.setBorder(new LineBorder(Color.BLUE, 1))
			  label.setFocusable(true)
		    } else {
			  label.setBorder(new LineBorder(Color.BLUE, 0))
			  label.setFocusable(false)
		    }
		  }
		  def mouseExited (e: MouseEvent) {}
		  def mouseEntered (e: MouseEvent) {}
		  def mouseReleased (e: MouseEvent) {}
		  def mousePressed (e: MouseEvent) {}
	    })
  }
  
  def convertToFilelist(list: List[JLabel]) = {
    var newList = new ListBuffer[File]
    for (l <- list) {
    	if (!l.getText.isEmpty)
    		newList += new File ("filesystem/" + l.getText.substring(l.getText.length-3, l.getText.length) + "/" + l.getText)
    }
    newList.toList
  }
  
}