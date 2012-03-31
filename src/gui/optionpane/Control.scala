package gui.optionpane
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.JOptionPane
import scala.swing.FileChooser
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class Control {

  val view = new View
  val model = new Model
  
  view.tfPathEditJPG.setText(model.pathEditJPG)
  view.tfPathEditPDF.setText(model.pathEditPDF)
  view.tfPathEditMP4.setText(model.pathEditMP4)
  view.tfPathReadJPG.setText(model.pathReadJPG)
  view.tfPathReadPDF.setText(model.pathReadPDF)
  view.tfPathReadMP4.setText(model.pathReadMP4)
  
  JOptionPane.showMessageDialog(null, "Please set the programs you like to use for editing and viewing files.", "Configuration", JOptionPane.INFORMATION_MESSAGE);

  view.btnSave.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {	    
	    var answer = saveQuestion
	    if (answer.equals("Yes")) {
	      // throws exceptions in config.setReader
	    	model.config.setReader("jpg", view.tfPathReadJPG.getText)
	    	model.config.setReader("pdf", view.tfPathReadPDF.getText)
	    	model.config.setReader("mp4", view.tfPathReadMP4.getText)
	    	model.config.setEditor("jpg", view.tfPathEditJPG.getText)
	    	model.config.setEditor("pdf", view.tfPathEditPDF.getText)
	    	model.config.setEditor("mp4", view.tfPathEditMP4.getText)
	    	view.dispose
	    }
	    if (answer.equals("No"))
	    	view.dispose
	  }
  })
  
  view.btnCancel.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {	    
	    view.dispose
	  }
  })
  
  view.btnFC1.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {	    
		var fc = new JFileChooser
		var filter = new FileNameExtensionFilter("EXE", "exe")
		fc.setFileFilter(filter)
		fc.setMultiSelectionEnabled(false)
		fc.showOpenDialog(null)
		var file = fc.getSelectedFile
		if (file != null)
			view.tfPathEditJPG.setText(file.getName)
	  }
  })
  
  view.btnFC2.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    var fc = new JFileChooser
		var filter = new FileNameExtensionFilter("EXE", "exe")
		fc.setFileFilter(filter)
		fc.setMultiSelectionEnabled(false)
		fc.showOpenDialog(null)
		var file = fc.getSelectedFile
		if (file != null)
			view.tfPathReadJPG.setText(file.getName)
	  }
  })
  
  view.btnFC3.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    var fc = new JFileChooser
		var filter = new FileNameExtensionFilter("EXE", "exe")
		fc.setFileFilter(filter)
		fc.setMultiSelectionEnabled(false)
		fc.showOpenDialog(null)
		var file = fc.getSelectedFile
		if (file != null)
			view.tfPathEditPDF.setText(file.getName)
	  }
  })
  
  view.btnFC4.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    var fc = new JFileChooser
		var filter = new FileNameExtensionFilter("EXE", "exe")
		fc.setFileFilter(filter)
		fc.setMultiSelectionEnabled(false)
		fc.showOpenDialog(null)
		var file = fc.getSelectedFile
		if (file != null)
			view.tfPathReadPDF.setText(file.getName)
	  }
  })
  
  view.btnFC5.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    var fc = new JFileChooser
		var filter = new FileNameExtensionFilter("EXE", "exe")
		fc.setFileFilter(filter)
		fc.setMultiSelectionEnabled(false)
		fc.showOpenDialog(null)
		var file = fc.getSelectedFile
		if (file != null)
			view.tfPathEditMP4.setText(file.getName)
	  }
  })
  
  view.btnFC6.addActionListener( new ActionListener {
	  def actionPerformed(e:ActionEvent) {
	    var fc = new JFileChooser
		var filter = new FileNameExtensionFilter("EXE", "exe")
		fc.setFileFilter(filter)
		fc.setMultiSelectionEnabled(false)
		fc.showOpenDialog(null)
		var file = fc.getSelectedFile
		if (file != null)
			view.tfPathReadMP4.setText(file.getName)
	  }
  })
  
  def saveQuestion = {
	  var result = "none"
	    var options: Array[Object] = Array("Cancel", "No", "Yes")
		var selected = JOptionPane.showOptionDialog(null, "Do you like to save your current configuration?", "Save", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options(1))
		if (selected == 0)
			result = "Cancel"
		if (selected == 1)
			result = "No"
		if (selected == 2)
			result = "Yes"
	  result
	}
  
}