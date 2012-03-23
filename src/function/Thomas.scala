package function

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

import scala.actors.Exit

import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.JFileChooser
import javax.swing.JOptionPane

class Thomas {
  
	// opens a filechooser with a jpg, pdf and mp4 filter and returns the selected files as an array
	def getFiles = {
		var fc = new JFileChooser()
		var filter = new FileNameExtensionFilter("JPG, PDF and MP4", "jpg", "pdf", "mp4")
		fc.setFileFilter(filter)
		fc.setMultiSelectionEnabled(true)
		fc.showOpenDialog(null)
		fc.getSelectedFiles()
	}
	
	// copies the files to the matching folder in the filesystem
	def copy(files: Array[File]) = {
		var abort = false
		for (f <- files) {
			var name = f.getName()
			var length = name.length()
			var suffix = name.substring(length-3, length)
			var destination = new File("filesystem/" + suffix + "/" + f.getName())
			abort = check(f, destination)
			if (abort)
			  new Exit(null, null)
		}
		if (abort)
		  JOptionPane.showMessageDialog(null, "Import canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
		else  
		  JOptionPane.showMessageDialog(null, "Import finished successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// checks, if a file with the name already exists at the specified destination and asks to overwrite it
	def check(f: File, destination: File) : Boolean = {
		var abort = false
		if (destination.exists()) {
			var options: Array[Object] = Array("Cancel", "No", "Yes")
	  		var selected = JOptionPane.showOptionDialog(null, "A file with that name already exists. Do you like to overwrite it?", "Overwrite", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options(1))
	  		if (selected == 0)
	  			abort = true
	  		if (selected == 2) {
	  		  destination.delete()
	  		  check(f, destination)
	  		}
		}
		else
			new FileOutputStream(destination) getChannel() transferFrom(new FileInputStream(f) getChannel, 0, Long.MaxValue)
		return abort
	}

}