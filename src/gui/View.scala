package gui

import swing._
import scala.swing.Button
import scala.swing.FlowPanel
import scala.swing.Frame
import scala.swing.Label
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.JTextField
import javax.swing.SpringLayout
import javax.swing.border.LineBorder
import java.awt.SystemColor
import java.awt.Color
import javax.swing.JComponent
import scala.swing.event.ButtonClicked
import basic.Import

// stellt die gui

class View extends Frame {
  
  	title = "Awesome GUI";
  
	var panel = new JPanel()
	var leftup = new JPanel()
	var right = new JPanel()
	var mid = new JPanel()
	var leftdown = new JPanel()
	var namebackground = new JPanel()
	
	var lblOverview = new JLabel("Overview", Alignment.Center.id)
	var lblTools = new JLabel("Relations", Alignment.Center.id)
	var lblName = new JLabel("Name", Alignment.Center.id)
	
	var btnList = new JButton("List")
	var btnGroup = new JButton("Group")
	var btnRelation = new JButton("Relation")
	
	var name = new JTextField()
	
	var springLayout = new SpringLayout()
	var sl_leftup = new SpringLayout()
	var sl_mid = new SpringLayout()
	var sl_right = new SpringLayout()
	var sl_leftdown = new SpringLayout()
	
    minimumSize = new Dimension(1280, 800)
    centerOnScreen
	resizable = false
	
	var mnEdit = new Menu("Edit")
	var mnFile = new Menu("File")
	var mnHelp = new Menu("Help")
	var mnOptions = new Menu("Options")
	var mnNew = new Menu("New")
	
	var mntmOpen = new MenuItem("Open")
	var mntmGroup = new MenuItem("Group")
	var mntmList = new MenuItem("List")
	var mntmUndo = new MenuItem("Undo")
	var mntmRedo = new MenuItem("Redo")
	var mntmCopy = new MenuItem("Copy")
	var mntmPaste = new MenuItem("Paste")
	var mntmQuit = new MenuItem("Quit")
	var mntmHelp = new MenuItem("Help Contents")
	var mntmAbout = new MenuItem("About")
	
	menuBar = new MenuBar {
	  contents += new Menu("File") {
	    contents += new Menu("New") {
	      contents += new MenuItem("Group")
	      contents += new MenuItem("List")
	    }
	    contents += new Separator()
	    contents += new MenuItem("Import Files")
	    contents += new Separator()
	    contents += new MenuItem("Quit")
	  }
	  contents += new Menu("Edit") {
	    contents += new MenuItem("Undo")
	    contents += new MenuItem("Redo")
	    contents += new Separator()
	    contents += new MenuItem("Copy")
	    contents += new MenuItem("Paste")
	  }
	  contents += new Menu("Options") {
	  }
	  contents += new Menu("Help") {
	    contents += new MenuItem("Help")
	    contents += new MenuItem("About")
	  }
	}
	
	springLayout.putConstraint(SpringLayout.EAST, leftup, 200, SpringLayout.WEST, panel);
	springLayout.putConstraint(SpringLayout.NORTH, leftup, 0, SpringLayout.NORTH, panel);
	springLayout.putConstraint(SpringLayout.SOUTH, leftup, 375, SpringLayout.NORTH, panel);
	springLayout.putConstraint(SpringLayout.WEST, leftup, 0, SpringLayout.WEST, panel);

	springLayout.putConstraint(SpringLayout.NORTH, leftdown, 0, SpringLayout.SOUTH, leftup);
	springLayout.putConstraint(SpringLayout.WEST, leftdown, 0, SpringLayout.WEST, panel);
	springLayout.putConstraint(SpringLayout.SOUTH, leftdown, 0, SpringLayout.SOUTH, panel);
	springLayout.putConstraint(SpringLayout.EAST, leftdown, 0, SpringLayout.WEST, mid);
	
	springLayout.putConstraint(SpringLayout.NORTH, mid, 0, SpringLayout.NORTH, panel);
	springLayout.putConstraint(SpringLayout.WEST, mid, 0, SpringLayout.EAST, leftup);
	springLayout.putConstraint(SpringLayout.SOUTH, mid, 0, SpringLayout.SOUTH, panel);
	springLayout.putConstraint(SpringLayout.EAST, mid, 0, SpringLayout.WEST, right);
	
	springLayout.putConstraint(SpringLayout.SOUTH, right, 0, SpringLayout.SOUTH, panel);
	springLayout.putConstraint(SpringLayout.NORTH, right, 0, SpringLayout.NORTH, leftup);
	springLayout.putConstraint(SpringLayout.WEST, right, 1080, SpringLayout.WEST, panel);
	springLayout.putConstraint(SpringLayout.EAST, right, 0, SpringLayout.EAST, panel);

	sl_leftup.putConstraint(SpringLayout.NORTH, lblOverview, 0, SpringLayout.NORTH, leftup);
	sl_leftup.putConstraint(SpringLayout.WEST, lblOverview, 0, SpringLayout.WEST, leftup);
	sl_leftup.putConstraint(SpringLayout.SOUTH, lblOverview, 30, SpringLayout.NORTH, leftup);
	sl_leftup.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblOverview, 0, SpringLayout.HORIZONTAL_CENTER, leftup);
    
	sl_right.putConstraint(SpringLayout.NORTH, lblName, 5, SpringLayout.NORTH, right);
	sl_right.putConstraint(SpringLayout.SOUTH, lblName, 25, SpringLayout.NORTH, right);
	sl_right.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, right);
	sl_right.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblName, 0, SpringLayout.HORIZONTAL_CENTER, right);
	
	sl_right.putConstraint(SpringLayout.NORTH, name, 0, SpringLayout.SOUTH, lblName);
	sl_right.putConstraint(SpringLayout.WEST, name, 10, SpringLayout.WEST, right);
	sl_right.putConstraint(SpringLayout.EAST, name, -10, SpringLayout.EAST, right);
	
	sl_right.putConstraint(SpringLayout.NORTH, namebackground, 0, SpringLayout.NORTH, right);
	sl_right.putConstraint(SpringLayout.WEST, namebackground, 0, SpringLayout.WEST, right);
	sl_right.putConstraint(SpringLayout.SOUTH, namebackground, 60, SpringLayout.NORTH, right);
	sl_right.putConstraint(SpringLayout.EAST, namebackground, 0, SpringLayout.EAST, right);
	
	sl_leftdown.putConstraint(SpringLayout.NORTH, lblTools, 0, SpringLayout.NORTH, leftdown);
	sl_leftdown.putConstraint(SpringLayout.WEST, lblTools, 0, SpringLayout.WEST, leftdown);
	sl_leftdown.putConstraint(SpringLayout.SOUTH, lblTools, 30, SpringLayout.NORTH, leftdown);
	sl_leftdown.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblTools, 0, SpringLayout.HORIZONTAL_CENTER, leftdown);

	panel.setLayout(springLayout);
	leftup.setLayout(sl_leftup);
	right.setLayout(sl_right);
	mid.setLayout(sl_mid);
	leftdown.setLayout(sl_leftdown);
	
	panel.add(right)
	panel.add(leftup);
	panel.add(mid);
	panel.add(leftdown);
	right.add(lblName);
	leftup.add(lblOverview);
	right.add(name);
	name.setColumns(10);
	leftdown.add(lblTools);
	right.add(namebackground);

	leftup.setBackground(Color.LIGHT_GRAY);
	leftup.setBorder(new LineBorder(Color.DARK_GRAY, 2));
	right.setBorder(new LineBorder(Color.DARK_GRAY, 2));
	right.setBackground(Color.LIGHT_GRAY);
	namebackground.setBackground(SystemColor.scrollbar);
	mid.setBackground(Color.WHITE);
	leftdown.setBackground(Color.LIGHT_GRAY);
	leftdown.setBorder(new LineBorder(Color.DARK_GRAY, 2));
	
	lblName.setFont(new Font("Dialog Bold", 1, 13));
		
	lblOverview.setFont(new Font("Dialog Bold", 1, 13));
    lblOverview.setBackground(SystemColor.scrollbar);
    lblOverview.setOpaque(true);
	
    lblTools.setBackground(SystemColor.scrollbar);
    lblTools.setOpaque(true);
	lblTools.setFont(new Font("Dialog Bold", 1, 13));
	
	sl_leftdown.putConstraint(SpringLayout.NORTH, btnGroup, 50, SpringLayout.NORTH, leftdown);
	sl_leftdown.putConstraint(SpringLayout.WEST, btnGroup, 0, SpringLayout.WEST, leftdown);
	sl_leftdown.putConstraint(SpringLayout.EAST, btnGroup, 0, SpringLayout.EAST, leftdown);
	leftdown.add(btnGroup);
	
	sl_leftdown.putConstraint(SpringLayout.NORTH, btnList, 0, SpringLayout.SOUTH, btnGroup);
	sl_leftdown.putConstraint(SpringLayout.WEST, btnList, 0, SpringLayout.WEST, leftdown);
	sl_leftdown.putConstraint(SpringLayout.EAST, btnList, 0, SpringLayout.EAST, leftdown);
	leftdown.add(btnList);
	  
	contents = Component.wrap(panel)
	
	visible = true

  
}