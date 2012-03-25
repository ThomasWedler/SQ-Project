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
import javax.swing.KeyStroke
import java.awt.event.KeyEvent
import java.awt.event.InputEvent
import java.awt.GridLayout
import javax.swing.JScrollPane
import javax.swing.JScrollBar
import javax.swing.BorderFactory

// stellt die gui

class View extends Frame {
    
  	title = "Awesome GUI";
  
	var panel = new JPanel
	var leftup = new JPanel
	var right = new JPanel
	var mid = new JPanel
	var leftdown = new JPanel
	var annotation = new JPanel
	var overview = new JPanel
	
	var lblOverview = new JLabel("Overview", Alignment.Center.id)
	var lblTools = new JLabel("Relations", Alignment.Center.id)
	var lblName = new JLabel("Name", Alignment.Center.id)
	
	var lblJPG = new JLabel("JPG")
	var lblPDF = new JLabel("PDF")
	var lblMP4 = new JLabel("MP4")
	var lblRelations = new JLabel("Relations")
	
	var contentjpg = new JLabel("    <empty>")
   	var contentpdf = new JLabel("<empty>")
  	var contentmp4 = new JLabel("<empty>")
  	var contentrelations = new JLabel("<empty>")
	
	var btnList = new JButton("List")
	var btnGroup = new JButton("Group")
	var btnRelation = new JButton("Relation")
	
	var name = new JTextField
	
	var springLayout = new SpringLayout
	var sl_leftup = new SpringLayout
	var layoutMid = new GridLayout(0,6)
	var sl_right = new SpringLayout
	var sl_leftdown = new SpringLayout
	var sl_name = new SpringLayout
	var layoutOverview = new GridLayout(0,1)

	panel.setLayout(springLayout);
	leftup.setLayout(sl_leftup);
	right.setLayout(sl_right);
	mid.setLayout(layoutMid);
	leftdown.setLayout(sl_leftdown);
	annotation.setLayout(sl_name)
	overview.setLayout(layoutOverview)
	
	var sp_mid = new JScrollPane(mid)
	var sp_overview = new JScrollPane(overview)
	
    minimumSize = new Dimension(1280, 800)
    centerOnScreen
	resizable = false
	
	var menu = new JMenuBar
	var mnEdit = new JMenu("Edit")
	var mnFile = new JMenu("File")
	var mnHelp = new JMenu("Help")
	var mnOptions = new JMenu("Options")
	var mnNew = new JMenu("New Relation")
	
	var mntmSave = new JMenuItem("Save Relation")
	var mntmOpen = new JMenuItem("Import Files")
	var mntmGroup = new JMenuItem("Group")
	var mntmList = new JMenuItem("List")
	var mntmUndo = new JMenuItem("Undo")
	var mntmRedo = new JMenuItem("Redo")
	var mntmCopy = new JMenuItem("Copy")
	var mntmPaste = new JMenuItem("Paste")
	var mntmQuit = new JMenuItem("Quit")
	var mntmHelp = new JMenuItem("Help Contents")
	var mntmAbout = new JMenuItem("About")
    var mntmRefresh = new JMenuItem("Refresh")
	var mntmDelete = new JMenuItem("Delete Files")
	var mntmAdd = new JMenuItem("Add to Relation")
	var mntmRemove = new JMenuItem("Remove from Relation")
		
	panel.add(menu)
	menu.add(mnFile)
	menu.add(mnEdit)
	menu.add(mnOptions)
	menu.add(mnHelp)
	
	mnFile.add(mnNew)
	mnNew.add(mntmGroup)
	mnNew.add(mntmList)
			
	mnFile.add(new JSeparator)
	mnFile.add(mntmOpen)
	mnFile.add(mntmDelete)
	mnFile.add(mntmRefresh)
	mnFile.add(new JSeparator)
	mnFile.add(mntmQuit)
	
	mnEdit.add(mntmUndo)
	mnEdit.add(mntmRedo)
	mnEdit.add(new JSeparator)
	mnEdit.add(mntmCopy)
	mnEdit.add(mntmPaste)
	mnEdit.add(new JSeparator)
	mnEdit.add(mntmAdd)
	mnEdit.add(mntmRemove)
	mnEdit.add(mntmSave)
	
	mnHelp.add(mntmHelp)
	mnHelp.add(mntmAbout)
	
	mntmAbout.setMnemonic(KeyEvent.VK_A);
	mntmHelp.setMnemonic(KeyEvent.VK_C);
	mntmPaste.setMnemonic(KeyEvent.VK_P);
	mntmCopy.setMnemonic(KeyEvent.VK_C);
	mntmRedo.setMnemonic(KeyEvent.VK_R);
	mntmUndo.setMnemonic(KeyEvent.VK_U);
	mntmQuit.setMnemonic(KeyEvent.VK_Q);
	mntmSave.setMnemonic(KeyEvent.VK_S);
	mntmOpen.setMnemonic(KeyEvent.VK_I);
	mntmList.setMnemonic(KeyEvent.VK_L);
	mntmGroup.setMnemonic(KeyEvent.VK_G);
	mnNew.setMnemonic(KeyEvent.VK_N);
	mnHelp.setMnemonic(KeyEvent.VK_H);
	mnOptions.setMnemonic(KeyEvent.VK_O);
	mnEdit.setMnemonic(KeyEvent.VK_E);
	mnFile.setMnemonic(KeyEvent.VK_F);
	mntmRefresh.setMnemonic(KeyEvent.VK_R)
	mntmDelete.setMnemonic(KeyEvent.VK_D)
	mntmRemove.setMnemonic(KeyEvent.VK_M)
	mntmAdd.setMnemonic(KeyEvent.VK_A)

	mntmAdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
	mntmRemove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
	mntmDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
	mntmRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
	mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
	mntmGroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
	mntmHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
	mntmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
	mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
	mntmRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
	mntmUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
	mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
	mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
	mntmList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
	
	springLayout.putConstraint(SpringLayout.EAST, leftup, 200, SpringLayout.WEST, panel);
	springLayout.putConstraint(SpringLayout.NORTH, leftup, 0, SpringLayout.SOUTH, menu);
	springLayout.putConstraint(SpringLayout.SOUTH, leftup, 375, SpringLayout.NORTH, panel);
	springLayout.putConstraint(SpringLayout.WEST, leftup, 0, SpringLayout.WEST, panel);

	springLayout.putConstraint(SpringLayout.NORTH, leftdown, 0, SpringLayout.SOUTH, leftup);
	springLayout.putConstraint(SpringLayout.WEST, leftdown, 0, SpringLayout.WEST, panel);
	springLayout.putConstraint(SpringLayout.SOUTH, leftdown, 0, SpringLayout.SOUTH, panel);
	springLayout.putConstraint(SpringLayout.EAST, leftdown, 0, SpringLayout.WEST, sp_mid);
	
	springLayout.putConstraint(SpringLayout.NORTH, sp_mid, 0, SpringLayout.NORTH, leftup);
	springLayout.putConstraint(SpringLayout.WEST, sp_mid, 0, SpringLayout.EAST, leftup);
	springLayout.putConstraint(SpringLayout.SOUTH, sp_mid, 0, SpringLayout.SOUTH, panel);
	springLayout.putConstraint(SpringLayout.EAST, sp_mid, 0, SpringLayout.WEST, right);
	
	springLayout.putConstraint(SpringLayout.SOUTH, right, 0, SpringLayout.SOUTH, panel);
	springLayout.putConstraint(SpringLayout.NORTH, right, 0, SpringLayout.NORTH, leftup);
	springLayout.putConstraint(SpringLayout.WEST, right, 1080, SpringLayout.WEST, panel);
	springLayout.putConstraint(SpringLayout.EAST, right, 0, SpringLayout.EAST, panel);

	sl_leftup.putConstraint(SpringLayout.NORTH, lblOverview, 0, SpringLayout.NORTH, leftup);
	sl_leftup.putConstraint(SpringLayout.WEST, lblOverview, 0, SpringLayout.WEST, leftup);
	sl_leftup.putConstraint(SpringLayout.SOUTH, lblOverview, 30, SpringLayout.NORTH, leftup);
	sl_leftup.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblOverview, 0, SpringLayout.HORIZONTAL_CENTER, leftup);
    
	sl_leftup.putConstraint(SpringLayout.NORTH, sp_overview, 0, SpringLayout.SOUTH, lblOverview);
	sl_leftup.putConstraint(SpringLayout.WEST, sp_overview, 0, SpringLayout.WEST, leftup);
	sl_leftup.putConstraint(SpringLayout.SOUTH, sp_overview, 0, SpringLayout.SOUTH, leftup);
	sl_leftup.putConstraint(SpringLayout.EAST, sp_overview, 0, SpringLayout.EAST, leftup);
	
	sl_name.putConstraint(SpringLayout.NORTH, lblName, 5, SpringLayout.NORTH, annotation);
	sl_name.putConstraint(SpringLayout.SOUTH, lblName, 25, SpringLayout.NORTH, annotation);
	sl_name.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, annotation);
	sl_name.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblName, 0, SpringLayout.HORIZONTAL_CENTER, annotation);
	
	sl_name.putConstraint(SpringLayout.NORTH, name, 0, SpringLayout.SOUTH, lblName);
	sl_name.putConstraint(SpringLayout.WEST, name, 10, SpringLayout.WEST, annotation);
	sl_name.putConstraint(SpringLayout.EAST, name, -10, SpringLayout.EAST, annotation);
	
	sl_right.putConstraint(SpringLayout.NORTH, annotation, 0, SpringLayout.NORTH, right);
	sl_right.putConstraint(SpringLayout.WEST, annotation, 0, SpringLayout.WEST, right);
	sl_right.putConstraint(SpringLayout.SOUTH, annotation, 60, SpringLayout.NORTH, right);
	sl_right.putConstraint(SpringLayout.EAST, annotation, 0, SpringLayout.EAST, right);
	
	sl_leftdown.putConstraint(SpringLayout.NORTH, lblTools, 0, SpringLayout.NORTH, leftdown);
	sl_leftdown.putConstraint(SpringLayout.WEST, lblTools, 0, SpringLayout.WEST, leftdown);
	sl_leftdown.putConstraint(SpringLayout.SOUTH, lblTools, 30, SpringLayout.NORTH, leftdown);
	sl_leftdown.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblTools, 0, SpringLayout.HORIZONTAL_CENTER, leftdown);
	
	panel.add(right)
	panel.add(leftup);
	panel.add(leftdown);
	panel.add(sp_mid)
	leftup.add(sp_overview)
	annotation.add(lblName);
	leftup.add(lblOverview);
	annotation.add(name);
	leftdown.add(lblTools);
	right.add(annotation);
	
	overview.add(lblJPG)
	overview.add(contentjpg)
	overview.add(lblPDF)
	overview.add(contentpdf)
	overview.add(lblMP4)
	overview.add(contentmp4)
	overview.add(lblRelations)
	overview.add(contentrelations)

	annotation.setVisible(false)

	menu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
	panel.setBackground(Color.WHITE)
	sp_overview.setBackground(Color.LIGHT_GRAY)
	sp_overview.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
	sp_mid.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.DARK_GRAY, 1), BorderFactory.createLoweredBevelBorder()));
	leftup.setBackground(Color.LIGHT_GRAY);
	overview.setBackground(Color.LIGHT_GRAY);
	leftup.setBorder(new LineBorder(Color.DARK_GRAY, 2));
	right.setBorder(new LineBorder(Color.DARK_GRAY, 2));
	right.setBackground(Color.LIGHT_GRAY);
	annotation.setBackground(SystemColor.scrollbar);
	sp_mid.setBackground(Color.WHITE);
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