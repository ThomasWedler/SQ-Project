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
import java.io.BufferedReader
import java.io.FileReader
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.SwingConstants

// Verwaltung der darzustellenden Daten

class Control {
  
  val view = new View
  val model = new Model
  
  var chris = new function.Chris
  var thomas = new function.Thomas
  
  var undomanager = new UndoManager
  undomanager.setLimit(1000)
    
  refresh
  
  view.name.getDocument.addUndoableEditListener(undomanager)
  
  // menu item group
  view.mntmGroup.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {	    
	    JOptionPane.showMessageDialog(null, "Please select the items you would like to add to your new group.", "Group", JOptionPane.INFORMATION_MESSAGE)
	    refresh
		setAnnotationPanel("Group", true)
	 	addLabels(false) 
	  }
  })
  
  // menu item list
  view.mntmList.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		JOptionPane.showMessageDialog(null, "Please select the items you would like to add to your new list.", "List", JOptionPane.INFORMATION_MESSAGE)
	    refresh
		setAnnotationPanel("List", true)
		addLabels(false)
	  }
  })
  
  // button group
  view.btnGroup.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    JOptionPane.showMessageDialog(null, "Please select the items you would like to add to your new group.", "Group", JOptionPane.INFORMATION_MESSAGE)
	    refresh
		setAnnotationPanel("Group", true)
		addLabels(false)
	  }
  })
  
  // button list
  view.btnList.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    JOptionPane.showMessageDialog(null, "Please select the items you would like to add to your new list.", "List", JOptionPane.INFORMATION_MESSAGE)
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
	    val devImage: ImageIcon = new ImageIcon(getClass().getResource("/resources/devTeam.png"), "");
	    val devLabel: JLabel = new JLabel("<html><br />Version 1.0.0<br /><br />The Team (FLTR): David Cyborra, Thomas Wedler, Kristof Korwisi, Chris Zimmerer</html>", devImage, javax.swing.SwingConstants.CENTER)
	  	devLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
	    devLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER)
	    JOptionPane.showMessageDialog(null, devLabel, "About", JOptionPane.PLAIN_MESSAGE);
	  }
  })
  
  // cancel button in relation mode
  view.btnCancel.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    refresh
	    setAnnotationPanel(null, false)
	  }
  })
  
  // save relation
  view.btnSave.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		  var answer = saveQuestion
		  if (answer.equals("Yes")) {
		    var result = thomas.overwrite(new File("filesystem/relations/" + view.name.getText + ".txt"))
		    if (result.equals("Yes") || result.equals("none")) {
			  var list = convertToFilelist(model.relationList.toList)
			  new Annotation(view.lblName.getText, view.name.getText, list)
			  refresh
			  setAnnotationPanel(null, false)
		    }
		  } else if (answer.equals("No")) {
			  	refresh
				setAnnotationPanel(null, false)
		  }
	  }
  })
  
  // save relation
  view.mntmSave.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
		  var answer = saveQuestion
		  if (answer.equals("Yes")) {
		    var result = thomas.overwrite(new File("filesystem/relations/" + view.name.getText + ".txt"))
		    if (result.equals("Yes") || result.equals("none")) {
			  var list = convertToFilelist(model.relationList.toList)
			  new Annotation(view.lblName.getText, view.name.getText, list)
			  refresh
			  setAnnotationPanel(null, false)
		    }
		  } else if (answer.equals("No")) {
			  	refresh
				setAnnotationPanel(null, false)
		  }
	  }
  })
  
  // delete file
  view.mntmDelete.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    var result = deleteQuestion
	    if (result.equals("Yes")) {
		    var file = new File("")
		    var bool = false
			  for (i <- model.imageList) {
				  if (i.isFocusable()) {
				    file = new File("filesystem/" + i.getText.substring(i.getText.length-3, i.getText.length) + "/" + i.getText)
				    model.imageList.remove(i => bool: Boolean)
				  }
			  }
		    for (f <- thomas.walkthrough()) {
		      if (thomas.checkName(f, file))
		        f.delete()
		    }
		    refresh
	    }
	  }
  })
  
  // load relation
  view.mntmLoad.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    var fc = new JFileChooser
		var filter = new FileNameExtensionFilter("TXT", "txt")
		fc.setFileFilter(filter)
		fc.setMultiSelectionEnabled(false)
		fc.showOpenDialog(null)
		loadRelation(fc.getSelectedFile)
	  }
  })
  
  // configuration
  view.mntmConfig.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    new gui.optionpane.Control
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
        if (!i.getText.isEmpty)
        	addMouseListenerBorder(i)
        else
        	addMouseListenerDeselect(i)
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
      var label = new JLabel(i.getText)
      if (!i.isFocusable) {
    	  if (!i.getText.isEmpty && i.getText.substring(i.getText.length-3, i.getText.length).equals("txt"))
    		  addMouseListenerLoad(label)
      }
      view.overview.add(label)
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
      
            // mouse listener fŸr clickable und play file
      
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
    	if (view.annotation.isVisible) {
    	    view.relation.removeAll
    		view.mntmRefresh.setEnabled(false)
    		view.mntmSave.setEnabled(true)
    		view.btnPlay.setVisible(true)
    		view.btnSave.setVisible(true)
    		view.btnCancel.setVisible(true)
    		view.sp_relation.setVisible(true)
    		model.relationList = new ListBuffer[JLabel]
    	    model.relationList += new JLabel("")
    	    view.mntmSave.setEnabled(true)
    	} else {
    		view.mntmRefresh.setEnabled(true)
    		view.btnPlay.setVisible(false)
    		view.btnSave.setVisible(false)
    		view.btnCancel.setVisible(false)
    		view.sp_relation.setVisible(false)
    		view.mntmSave.setEnabled(false)
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
  // and loads the file on double click
  def addMouseListenerBorder(label: JLabel) {
    label.addMouseListener( new MouseListener {
		  def mouseClicked(e:MouseEvent) {
		    if (e.getClickCount == 2) {
		      if (label.getText.substring(label.getText.length-3, label.getText.length).equals("txt")) {
		    	  var file = new File("")
		    	  var name = label.getText
		    	  for (f <- thomas.walkthrough) {
		    		  if (f.getName.equals(name))
		    			  file = f
		    	  }
		    	  loadRelation(file)
			  }
			  var list = model.getOverviewList
			  for (l <- list) {
			    l.setFocusable(false)
			    if (!l.getText.equals("") && l.getText.substring(5, l.getText.length).equals(" " + label.getText)) {
			    	l.setText("<html><body>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"background-color: #87CEFA\";><b>" + l.getText + "</b></span></body></html>")
			    	l.setFocusable(true)
			    }
			  }
		      view.overview.removeAll
			  for (i <- list) {
			    var l = new JLabel(i.getText)
			    if (!i.isFocusable) {
				    if (!i.getText.isEmpty && i.getText.substring(i.getText.length-3, i.getText.length).equals("txt"))
				    	addMouseListenerLoad(l)
			    }
			    view.overview.add(l)
			  }
			  model.overviewList = list
		    }
		    for (i <- model.imageList) {
		      if (i.isFocusable()) {
		        view.mntmPlay.setEnabled(false)
		        view.mntmDelete.setEnabled(false)
		        i.setBorder(new LineBorder(Color.BLUE, 0))
		        i.setFocusable(false)
		      }
		    }
		    if (!label.isFocusable()) {
		      view.mntmPlay.setEnabled(true)
		      view.mntmDelete.setEnabled(true)
			  label.setBorder(new LineBorder(Color.DARK_GRAY, 1))
			  label.setFocusable(true)
		    }
		  }
		  def mouseExited (e: MouseEvent) {}
		  def mouseEntered (e: MouseEvent) {}
		  def mouseReleased (e: MouseEvent) {}
		  def mousePressed (e: MouseEvent) {}
	    })
  }
  
  //  a mouse listener which sets empty borders to focused items in the image list 
   def addMouseListenerDeselect(label: JLabel) {
    label.addMouseListener( new MouseListener {
		  def mouseClicked(e:MouseEvent) {
		    for (i <- model.imageList) {
		      if (i.isFocusable()) {
		        view.mntmPlay.setEnabled(false)
		        view.mntmDelete.setEnabled(false)
		        i.setBorder(new LineBorder(Color.BLUE, 0))
		        i.setFocusable(false)
		      }
		    }
		  }
		  def mouseExited (e: MouseEvent) {}
		  def mouseEntered (e: MouseEvent) {}
		  def mouseReleased (e: MouseEvent) {}
		  def mousePressed (e: MouseEvent) {}
	    })
  }
  
   // converts a list of jlabels to a list of files
  def convertToFilelist(list: List[JLabel]) = {
    var newList = new ListBuffer[File]
    for (l <- list) {
    	if (!l.getText.isEmpty)
    		newList += new File ("filesystem/" + l.getText.substring(l.getText.length-3, l.getText.length) + "/" + l.getText)
    }
    newList.toList
  }
  
  // save question with the answer as return
  def saveQuestion = {
	  var result = "none"
	    var options: Array[Object] = Array("Cancel", "No", "Yes")
		var selected = JOptionPane.showOptionDialog(null, "Do you like to save your current relation?", "Save", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options(1))
		if (selected == 0)
			result = "Cancel"
		if (selected == 1)
			result = "No"
		if (selected == 2)
			result = "Yes"
	  result
	}
  
    // delete question with the answer as return
  def deleteQuestion = {
    var result = "none"
	    var options: Array[Object] = Array("No", "Yes")
		var selected = JOptionPane.showOptionDialog(null, "Do you really want to delete the selected file from the filesystem?", "Delete", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options(0))
		if (selected == 0)
			result = "No"
		if (selected == 1)
			result = "Yes"
	  result
  }
  
  // reads a txt file and adds for each line a label to the relation list
  def loadRelation(f: File) = {
    var br = new BufferedReader(new FileReader(f))
    var list = new ListBuffer[String]
    var zeile = br.readLine()
	while (zeile != null) {
		list += zeile
		zeile = br.readLine()
	}
	setAnnotationPanel(list.first, true)
	view.name.setText(f.getName.substring(0, f.getName.length-4))
	addLabels(false)
	for (i <- model.imageList) {
	  if (list.contains(i.getText))
	    i.setEnabled(true)
	}
	addRelationLabels(list.first)
  }
  
  // on click find the txt file in the filesystem and load it
   def addMouseListenerLoad(label: JLabel) {
    label.addMouseListener( new MouseListener {
		  def mouseClicked(e:MouseEvent) {
			  var file = new File("")
			  var name = label.getText.substring(6, label.getText.length)
			  for (f <- thomas.walkthrough) {
			    if (f.getName.equals(name))
			      file = f
			  }
			  var focused = false
			  for (l <- model.overviewList) {
			    if (l.isFocusable)
			      focused = true
			  }
			  if (!focused) {
				  loadRelation(file)
				  refreshOverview(label)
			  } else {
			    var answer = saveQuestion
			    if (answer.equals("Yes")) {
			      var result = thomas.overwrite(new File("filesystem/relations/" + view.name.getText + ".txt"))
					    if (result.equals("Yes") || result.equals("none")) {
						  var l = convertToFilelist(model.relationList.toList)
						  new Annotation(view.lblName.getText, view.name.getText, l)
						  loadRelation(file)
						  refreshOverview(label)
					    }
			      		if (result.equals("No")) {
			      			loadRelation(file)
			      			refreshOverview(label)
			      		}
			    }
			    if (answer.equals("No")) {
			    	loadRelation(file)
			    	refreshOverview(label)
			    }
			  }
		  }
		  def mouseExited (e: MouseEvent) {}
		  def mouseEntered (e: MouseEvent) {}
		  def mouseReleased (e: MouseEvent) {}
		  def mousePressed (e: MouseEvent) {}
	    })
  }
   
   // refreshs the overview for load relation purposes
   def refreshOverview(label: JLabel) {
      var list = model.getOverviewList
	  for (l <- list) {
	    if (l.getText.equals(label.getText)) {
		  l.setText("<html><body>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"background-color: #87CEFA\";><b>" + label.getText + "</b></span></body></html>")
		  l.setFocusable(true)
	    }
	  }
	  view.overview.removeAll
	  for (i <- list) {
	    var l = new JLabel(i.getText)
	    if (!i.isFocusable) {
		    if (!i.getText.isEmpty && i.getText.substring(i.getText.length-3, i.getText.length).equals("txt"))
		    	addMouseListenerLoad(l)
	    }
	    view.overview.add(l)
	  }
	  model.overviewList = list
   }
  
}