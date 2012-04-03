package gui

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.Color
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

import scala.collection.mutable.ListBuffer

import basic.Annotation
import basic.Import
import javax.swing.border.LineBorder
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.undo.UndoManager
import javax.swing.ImageIcon
import javax.swing.JFileChooser
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.SwingConstants

// Verwaltung der darzustellenden Daten

class Control {

  val view = new View
  val model = new Model

  var chris = new function.Chris
  var thomas = new function.Thomas
  var david = new function.David

  var undomanager = new UndoManager
  undomanager.setLimit(1000)
  view.name.getDocument.addUndoableEditListener(undomanager)

  refresh

  // ---------------------- Action Listener -------------------------------

  // menu item group
  view.mntmGroup.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      newRelation("Group")
    }
  })

  // menu item list
  view.mntmList.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      newRelation("List")
    }
  })

  // button group
  view.btnGroup.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      newRelation("Group")
    }
  })

  // button list
  view.btnList.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      newRelation("List")
    }
  })

  // undo
  view.mntmUndo.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      if (undomanager.canUndo)
        undomanager.undo
      else
        JOptionPane.showMessageDialog(null, "Cannot undo the last action!", "Cannot Undo", JOptionPane.ERROR_MESSAGE)
    }
  })

  // redo
  view.mntmRedo.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      if (undomanager.canRedo)
        undomanager.redo
      else
        JOptionPane.showMessageDialog(null, "Cannot redo the last action!", "Cannot Redo", JOptionPane.ERROR_MESSAGE);
    }
  })

  // adds the quit function
  view.mntmQuit.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      System.exit(0)
    }
  })

  // adds the import function
  view.mntmOpen.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      new Import
      refresh
    }
  })

  // add the refresh function
  view.mntmRefresh.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      refresh
    }
  })

  // help contents
  view.mntmHelp.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      JOptionPane.showMessageDialog(null, "Help Contents are still under development.", "Help Contents", JOptionPane.INFORMATION_MESSAGE);
    }
  })

  // about
  view.mntmAbout.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      val devImage: ImageIcon = new ImageIcon(getClass().getResource("/resources/devTeam.png"), "");
      val devLabel: JLabel = new JLabel("<html><br />Version 1.0.0<br /><br />The Team (FLTR): David Cyborra, Thomas Wedler, Kristof Korwisi, Chris Zimmerer</html>", devImage, javax.swing.SwingConstants.CENTER)
      devLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
      devLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER)
      JOptionPane.showMessageDialog(null, devLabel, "About", JOptionPane.PLAIN_MESSAGE);
    }
  })

  // cancel button in relation mode
  view.btnCancel.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      refresh
      setAnnotationPanel(null, false)
    }
  })

  // save relation
  view.btnSave.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      var answer = saveQuestion
      if (answer.equals("Yes")) {
        var result = thomas.overwrite(new File("filesystem/relations/" + view.name.getText + ".txt"))
        if (result.equals("Yes") || result.equals("none")) {
          cleanRelations
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
  view.mntmSave.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      var answer = saveQuestion
      if (answer.equals("Yes")) {
        var result = thomas.overwrite(new File("filesystem/relations/" + view.name.getText + ".txt"))
        if (result.equals("Yes") || result.equals("none")) {
          cleanRelations
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

  //
  def cleanRelations {
    var list = model.relationList
    for (l <- list)
      if (l.getText.startsWith("<html>")) {
        var name = l.getText.replace("<html><body><span style=\"background-color: #87CEFA\";><b>", "")
        name = name.replace("</b></span></body></html>", "")
        l.setText(name)
      }
    model.relationList = list
  }

  // delete file
  view.mntmDelete.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      var result = deleteQuestion
      if (result.equals("Yes")) {
        var file = new File("")
        var bool = false
        for (i <- model.imageList) {
          if (i.isFocusable()) {
            file = new File("filesystem/" + i.getText.substring(i.getText.length - 3, i.getText.length) + "/" + i.getText)
            model.imageList.remove(i => bool: Boolean)
          }
        }
        for (f <- thomas.walkthrough) {
          if (f.getName.equals(file.getName))
            f.delete()
        }
        refresh
      }
    }
  })

  // load relation
  view.mntmLoad.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      if (!view.annotation.isVisible) {
        var file = openFCTXT
        if (file != null) {
          loadRelation(file)
          refreshOverview(new JLabel("      " + file.getName))
        }
      } else {
        var answer = saveQuestion
        if (answer.equals("Yes")) {
          var result = thomas.overwrite(new File("filesystem/relations/" + view.name.getText + ".txt"))
          if (result.equals("Yes") || result.equals("none")) {
            var l = convertToFilelist(model.relationList.toList)
            new Annotation(view.lblName.getText, view.name.getText, l)
            var file = openFCTXT
            if (file != null) {
              loadRelation(file)
              refreshOverview(new JLabel("      " + file.getName))
            }
          }
          if (result.equals("No")) {
            var file = openFCTXT
            if (file != null) {
              loadRelation(file)
              refreshOverview(new JLabel("      " + file.getName))
            }
          }
        }
        if (answer.equals("No")) {
          var file = openFCTXT
          if (file != null) {
            loadRelation(file)
            refreshOverview(new JLabel("      " + file.getName))
          }
        }
      }
    }
  })

  def openFCTXT = {
    var fc = new JFileChooser
    var filter = new FileNameExtensionFilter("TXT", "txt")
    fc.setFileFilter(filter)
    fc.setMultiSelectionEnabled(false)
    fc.showOpenDialog(null)
    fc.getSelectedFile
  }

  // configuration
  view.mntmConfig.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      new gui.optionpane.Control
    }
  })

  // menu item play
  view.mntmPlay.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      var il = model.imageList
      var rl = model.relationList
      if (!view.annotation.isVisible) {
        for (n <- il) {
          if (n.isFocusable) {
            var filename = n.getText
            var ending = filename.substring(n.getText.length - 3, n.getText.length)
            var file = new File("filesystem/" + ending + "/" + filename)
            david.openReader(file)
          }
        }
      } else {
        var counter = -1
        for (n <- rl) {
          counter += 1
          if (n.getText.startsWith("<html>")) {
            var name = n.getText.replace("<html><body><span style=\"background-color: #87CEFA\";><b>", "")
            name = name.replace("</b></span></body></html>", "")
            n.setText(name)
            if (rl(counter + 1).getText.isEmpty) {
              view.btnPlay.setEnabled(false)
              view.mntmPlay.setEnabled(false)
            }
            var newname = rl(counter + 1).getText
            rl(counter + 1).setText("<html><body><span style=\"background-color: #87CEFA\";><b>" + newname + "</b></span></body></html>")
            println(n.getText)
            println(rl(counter + 1).getText)
            view.relation.removeAll
            for (i <- rl) {
              var label = new JLabel(i.getText)
              if (!i.getText.isEmpty)
                addMouseListenerRelation(label)
              view.relation.add(label)
            }
            view.panel.updateUI
            var filename = n.getText
            var ending = filename.substring(n.getText.length - 3, n.getText.length)
            var file = new File("filesystem/" + ending + "/" + filename)
            david.openReader(file)
            return
          }
        }
      }
    }
  })

  // button play
  view.btnPlay.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      var rl = model.relationList
      var counter = -1
      for (n <- rl) {
        counter += 1
        if (n.getText.startsWith("<html>")) {
          var name = n.getText.replace("<html><body><span style=\"background-color: #87CEFA\";><b>", "")
          name = name.replace("</b></span></body></html>", "")
          n.setText(name)
          if (rl(counter + 1).getText.isEmpty) {
            view.btnPlay.setEnabled(false)
            view.mntmPlay.setEnabled(false)
          }
          var newname = rl(counter + 1).getText
          rl(counter + 1).setText("<html><body><span style=\"background-color: #87CEFA\";><b>" + newname + "</b></span></body></html>")
          view.relation.removeAll
          for (i <- rl) {
            var label = new JLabel(i.getText)
            if (!i.getText.isEmpty)
              addMouseListenerRelation(label)
            view.relation.add(label)
          }
          view.panel.updateUI
          var filename = n.getText
          var ending = filename.substring(n.getText.length - 3, n.getText.length)
          var file = new File("filesystem/" + ending + "/" + filename)
          david.openReader(file)
          return
        }
      }
    }
  })

  // button up
  view.btnUp.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      view.btnDown.setEnabled(true)
      moveUp
    }
  })

  // button down
  view.btnDown.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      view.btnUp.setEnabled(true)
      moveDown
    }
  })

  // menu item edit
  view.mntmEdit.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      var il = model.imageList
      for (n <- il) {
        if (n.isFocusable) {
          var filename = n.getText
          var ending = filename.substring(n.getText.length - 3, n.getText.length)
          var file = new File("filesystem/" + ending + "/" + filename)
          david.openEditor(file)
        }
      }
    }
  })

  // ---------------------- Mouse Listener -------------------------------

  // adds a mouse listener to a jlabel in the relation mode, that turns them en-/disabled
  def addMouseListenerEnabled(label: JLabel) {
    label.addMouseListener(new MouseListener {
      def mouseClicked(e: MouseEvent) {
        if (!label.isEnabled()) {
          label.setEnabled(true)
        } else {
          label.setEnabled(false)
        }
        view.btnUp.setEnabled(false)
        view.btnDown.setEnabled(false)
        addRelationLabels(view.lblName.getText)
        view.panel.updateUI
      }
      def mouseExited(e: MouseEvent) {}
      def mouseEntered(e: MouseEvent) {}
      def mouseReleased(e: MouseEvent) {}
      def mousePressed(e: MouseEvent) {}
    })
  }

  // adds a mouse listener to a jlabel in the non relation mode, that marks them with borders [focusable]
  // and loads the file on double click
  def addMouseListenerBorder(label: JLabel) {
    label.addMouseListener(new MouseListener {
      def mouseClicked(e: MouseEvent) {
        if (e.getClickCount == 2) {
          if (label.getText.substring(label.getText.length - 3, label.getText.length).equals("txt")) {
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
              if (!i.getText.isEmpty && i.getText.substring(i.getText.length - 3, i.getText.length).equals("txt"))
                addMouseListenerLoad(l)
            }
            view.overview.add(l)
          }
          model.overviewList = list
        }
        for (i <- model.imageList) {
          if (i.isFocusable()) {
            view.mntmPlay.setEnabled(false)
            view.mntmEdit.setEnabled(false)
            view.mntmDelete.setEnabled(false)
            i.setBorder(new LineBorder(Color.BLUE, 0))
            i.setFocusable(false)
          }
        }
        if (!label.isFocusable()) {
          view.mntmPlay.setEnabled(true)
          view.mntmDelete.setEnabled(true)
          view.mntmEdit.setEnabled(true)
          label.setBorder(new LineBorder(Color.DARK_GRAY, 1))
          label.setFocusable(true)
        }
      }
      def mouseExited(e: MouseEvent) {}
      def mouseEntered(e: MouseEvent) {}
      def mouseReleased(e: MouseEvent) {}
      def mousePressed(e: MouseEvent) {}
    })
  }

  //  a mouse listener which sets empty borders to focused items in the image list 
  def addMouseListenerDeselect(label: JLabel) {
    label.addMouseListener(new MouseListener {
      def mouseClicked(e: MouseEvent) {
        for (i <- model.imageList) {
          if (i.isFocusable()) {
            view.mntmPlay.setEnabled(false)
            view.mntmEdit.setEnabled(false)
            view.mntmDelete.setEnabled(false)
            i.setBorder(new LineBorder(Color.BLUE, 0))
            i.setFocusable(false)
          }
        }
      }
      def mouseExited(e: MouseEvent) {}
      def mouseEntered(e: MouseEvent) {}
      def mouseReleased(e: MouseEvent) {}
      def mousePressed(e: MouseEvent) {}
    })
  }

  // on click find the txt file in the filesystem and load it
  def addMouseListenerLoad(label: JLabel) {
    label.addMouseListener(new MouseListener {
      def mouseClicked(e: MouseEvent) {
        var file = new File("")
        var name = label.getText.substring(6, label.getText.length)
        for (f <- thomas.walkthrough) {
          if (f.getName.equals(name))
            file = f
        }
        if (!view.annotation.isVisible) {
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
      def mouseExited(e: MouseEvent) {}
      def mouseEntered(e: MouseEvent) {}
      def mouseReleased(e: MouseEvent) {}
      def mousePressed(e: MouseEvent) {}
    })
  }

  // mouse listener for clickable relation files
  def addMouseListenerRelation(label: JLabel) {
    label.addMouseListener(new MouseListener {
      def mouseClicked(e: MouseEvent) {
        addLabels(false)
        view.mntmPlay.setEnabled(true)
        view.btnPlay.setEnabled(true)
        var list = model.relationList
        var buffer = new ListBuffer[JLabel]
        for (l <- list) {
          if (!l.getText.isEmpty)
            buffer += l
        }
        if (view.lblName.getText.equals("List:")) {
          view.btnUp.setEnabled(true)
          view.btnDown.setEnabled(true)
        }
        if (label.getText.equals(buffer.first.getText))
          view.btnUp.setEnabled(false)
        if (label.getText.equals(buffer.last.getText))
          view.btnDown.setEnabled(false)
        for (l <- list) {
          if (l.isFocusable) {
            var name = l.getText.replace("<html><body><span style=\"background-color: #87CEFA\";><b>", "")
            name = name.replace("</b></span></body></html>", "")
            l.setText(name)
          }
        }
        for (l <- list) {
          for (m <- model.imageList) {
            if (m.getText.equals(l.getText))
              m.setEnabled(true)
          }
        }
        for (l <- list) {
          l.setFocusable(false)
          if (l.getText.equals(label.getText)) {
            l.setText("<html><body><span style=\"background-color: #87CEFA\";><b>" + label.getText + "</b></span></body></html>")
            l.setFocusable(true)
          }
        }
        list = model.fill(list, 26)
        view.relation.removeAll
        for (i <- list) {
          var l = new JLabel(i.getText)
          if (!i.isFocusable && !i.getText.isEmpty) {
            addMouseListenerRelation(l)
          }
          view.relation.add(l)
        }
        model.relationList = list
        view.panel.updateUI
      }
      def mouseExited(e: MouseEvent) {}
      def mouseEntered(e: MouseEvent) {}
      def mouseReleased(e: MouseEvent) {}
      def mousePressed(e: MouseEvent) {}
    })
  }

  // ---------------------- Methoden -------------------------------

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
      if (!enabled && !i.getText.isEmpty) {
        i.disable
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
        if (!i.getText.isEmpty && i.getText.substring(i.getText.length - 3, i.getText.length).equals("txt"))
          addMouseListenerLoad(label)
      }
      view.overview.add(label)
    }
    model.overviewList = list
  }

  // adds the name of the labels to the new relation list
  def addRelationLabels(str: String) {
    var list = model.relationList.toList
    view.mntmPlay.setEnabled(false)
    if (str.equals("Group:")) {
      var l = new ListBuffer[JLabel]
      for (i <- model.imageList) {
        if (i.isEnabled && !i.getText.isEmpty)
          l += i
      }
      model.relationList = l
      list = model.fill(l.toList, 26)
    } else {
      var newlist = new ListBuffer[JLabel]
      for (l <- list) {
        if (l.isFocusable) {
          var name = l.getText.replace("<html><body><span style=\"background-color: #87CEFA\";><b>", "")
          name = name.replace("</b></span></body></html>", "")
          l.setText(name)
          l.setFocusable(false)
        }
        if (!l.getText.equals(""))
          newlist += l
      }
      var copy = new ListBuffer[String]
      for (n <- newlist)
        copy += n.getText
      for (i <- model.imageList) {
        if (!i.getText.isEmpty) {
          if (i.isEnabled) {
            if (!copy.contains(i.getText)) {
              newlist += i
            }
          } else {
            if (copy.contains(i.getText)) {
              var counter = 0
              for (n <- newlist)
                if (n.getText.equals(i.getText))
                  newlist.remove(counter)
                else
                  counter += 1
            }
          }
        }
      }
      model.relationList = newlist
      list = model.fill(newlist.toList, 26)
    }
    view.relation.removeAll
    for (i <- list) {
      var label = new JLabel(i.getText)
      if (!i.getText.isEmpty)
        addMouseListenerRelation(label)
      view.relation.add(label)
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
      view.mntmDelete.setEnabled(false)
      view.mntmOpen.setEnabled(false)
      view.mntmSave.setEnabled(true)
      view.btnPlay.setVisible(true)
      view.btnSave.setVisible(true)
      view.btnCancel.setVisible(true)
      view.sp_relation.setVisible(true)
      model.relationList = new ListBuffer[JLabel]
      view.mntmSave.setEnabled(true)
      view.btnUp.setVisible(true)
      view.btnDown.setVisible(true)
      view.mntmEdit.setEnabled(false)
      view.mntmPlay.setEnabled(false)
    } else {
      view.mntmRefresh.setEnabled(true)
      view.mntmDelete.setEnabled(false)
      view.mntmOpen.setEnabled(true)
      view.btnPlay.setVisible(false)
      view.btnSave.setVisible(false)
      view.btnCancel.setVisible(false)
      view.sp_relation.setVisible(false)
      view.mntmSave.setEnabled(false)
      view.btnUp.setVisible(false)
      view.btnDown.setVisible(false)
      view.mntmEdit.setEnabled(false)
      view.mntmPlay.setEnabled(false)
    }
  }

  // converts a list of jlabels to a list of files
  def convertToFilelist(list: List[JLabel]) = {
    var newList = new ListBuffer[File]
    for (l <- list) {
      if (!l.getText.isEmpty)
        newList += new File("filesystem/" + l.getText.substring(l.getText.length - 3, l.getText.length) + "/" + l.getText)
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
    refresh
    var br = new BufferedReader(new FileReader(f))
    var list = new ListBuffer[JLabel]
    var zeile = br.readLine
    while (zeile != null) {
      list += new JLabel(zeile)
      zeile = br.readLine
    }
    setAnnotationPanel(list.first.getText, true)
    view.name.setText(f.getName.substring(0, f.getName.length - 4))
    addLabels(false)
    for (i <- model.imageList) {
      for (j <- list)
        if (i.getText.equals(j.getText))
          i.setEnabled(true)
    }
    if (list.first.getText.equals("Group")) {
      var l = new ListBuffer[JLabel]
      for (i <- model.imageList) {
        if (i.isEnabled && !i.getText.isEmpty)
          l += i
      }
      model.relationList = model.fill(l, 26)
      list = model.fill(l, 26)
    } else {
      var l = new ListBuffer[JLabel]
      for (item <- list)
        if (!item.getText.equals("List"))
          l += item
      model.relationList = model.fill(l, 26)
      list = model.fill(l, 26)
    }
    view.relation.removeAll
    for (i <- list) {
      var label = new JLabel(i.getText)
      if (!i.getText.isEmpty)
        addMouseListenerRelation(label)
      view.relation.add(label)
    }
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
        if (!i.getText.isEmpty && i.getText.substring(i.getText.length - 3, i.getText.length).equals("txt"))
          addMouseListenerLoad(l)
      }
      view.overview.add(l)
    }
    model.overviewList = list
  }

  def newRelation(s: String) {
    if (view.annotation.isVisible) {
      var answer = saveQuestion
      if (answer.equals("Yes")) {
        var result = thomas.overwrite(new File("filesystem/relations/" + view.name.getText + ".txt"))
        if (result.equals("Yes") || result.equals("none")) {
          var list = convertToFilelist(model.relationList.toList)
          new Annotation(view.lblName.getText, view.name.getText, list)
          JOptionPane.showMessageDialog(null, "Please select the items you would like to add to your New " + s + ".", "New " + s, JOptionPane.INFORMATION_MESSAGE)
          refresh
          setAnnotationPanel(s, true)
          addLabels(false)
        }
      }
      if (answer.equals("No")) {
        JOptionPane.showMessageDialog(null, "Please select the items you would like to add to your New " + s + ".", "New " + s, JOptionPane.INFORMATION_MESSAGE)
        refresh
        setAnnotationPanel(s, true)
        addLabels(false)
      }
    } else {
      JOptionPane.showMessageDialog(null, "Please select the items you would like to add to your New " + s + ".", "New " + s, JOptionPane.INFORMATION_MESSAGE)
      refresh
      setAnnotationPanel(s, true)
      addLabels(false)
    }
  }

  def moveUp {
    var list = model.relationList
    var copy = list
    var cache = new JLabel("")
    var counter = -1
    for (l <- copy) {
      counter += 1
      if (l.getText.startsWith("<html>")) {
        if (counter == 1)
          view.btnUp.setEnabled(false)
        cache = list(counter - 1)
        list(counter - 1) = l
        list(counter) = cache
      }
    }
    list = model.fill(list, 26)
    view.relation.removeAll
    for (i <- list) {
      var l = new JLabel(i.getText)
      if (!i.isFocusable && !i.getText.isEmpty) {
        addMouseListenerRelation(l)
      }
      view.relation.add(l)
    }
    model.relationList = list
    view.panel.updateUI
  }

  def moveDown {
    var list = model.relationList
    var copy = new ListBuffer[JLabel]
    for (l <- list)
      if (!l.getText.isEmpty)
        copy += l
    var cache = new JLabel("")
    var counter = -1
    for (l <- copy) {
      counter += 1
      if (l.getText.startsWith("<html>")) {
        if (list(counter + 2).getText.isEmpty)
          view.btnDown.setEnabled(false)
        cache = list(counter + 1)
        list(counter + 1) = l
        list(counter) = cache
      }
    }
    list = model.fill(list, 26)
    view.relation.removeAll
    for (i <- list) {
      var l = new JLabel(i.getText)
      if (!i.isFocusable && !i.getText.isEmpty) {
        addMouseListenerRelation(l)
      }
      view.relation.add(l)
    }
    model.relationList = list
    view.panel.updateUI
  }

}