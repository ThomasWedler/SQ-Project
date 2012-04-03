package function

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import scala.actors.Exit
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import scala.collection.mutable.ListBuffer
import javax.swing.ImageIcon

class Thomas {
  
	// opens a filechooser with a jpg, pdf and mp4 filter and returns the selected files as an array
	def getFiles = {
		var fc = new JFileChooser
		var filter = new FileNameExtensionFilter("JPG, PDF and MP4", "jpg", "pdf", "mp4")
		fc.setFileFilter(filter)
		fc.setMultiSelectionEnabled(true)
		fc.showOpenDialog(null)
		fc.getSelectedFiles
	}
	
	// copies the files to the matching folder in the filesystem
	def copy(files: Array[File]) = {
	  var abort = false
		for (f <- files) {
			var suffix = getType(f)
			var destination = new File("filesystem/" + suffix + "/" + f.getName.replace(" ", "_"))
			var result = overwrite(f)
			
			if (result.equals("Cancel")) {
				abort = true
				new Exit(null, null)
			}
			if (result.equals("Yes")) {
				destination.delete
			  	new FileOutputStream(destination) getChannel() transferFrom(new FileInputStream(f) getChannel, 0, Long.MaxValue)
			}
			if (result.equals("none")) {
			  	new FileOutputStream(destination) getChannel() transferFrom(new FileInputStream(f) getChannel, 0, Long.MaxValue)
			}
	
		}
	  if (abort)
	  	JOptionPane.showMessageDialog(null, "Import canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
	  else
		JOptionPane.showMessageDialog(null, "Import finished successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// returns a string with the type (jpg, pdf, mp4) of the given file
	def getType(f: File) = {
	  var name = f.getName
	  var length = name.length
	  var suffix = name.substring(length-3, length)
	  suffix
	}
	
	// checks if the type of the given file is a valid type
	def checkType(f: File) = {
	  var check = false
	  if (getType(f).equals("jpg") || getType(f).equals("pdf") || getType(f).equals("mp4") || getType(f).equals("txt"))
	    check = true
	  check
	}
	
	// walks through the filesystem and returns a list containing all files (no directories) 
	def walkthrough = {
	  var list = new ListBuffer[File]
	  	for (f <- new File("filesystem/").listFiles) {
	  	  if (f.listFiles != null && !f.getName.equals("thumbnails")) {
	  	    for (file <- f.listFiles) {
		      if (checkType(file))
		        list += file
		    }
	  	  }
		}
		list.toList
	}
	
	// returns the answer of the overwrite-question
	def overwrite(f: File) = {
	  var result = "none"
	  for (file <- walkthrough) {
		if (f.getName.equals(file.getName))
			result = overwriteQuestion(f)
		}
	  result
	}
	
	// asks, if the file should be overwritten
	def overwriteQuestion(f: File) = {
	  var result = "none"
	    var options: Array[Object] = Array("Cancel", "No", "Yes")
		var selected = JOptionPane.showOptionDialog(null, "A file with the name '" + f.getName() + "' already exists. Do you like to overwrite it?", "Overwrite", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options(1))
		if (selected == 0)
			result = "Cancel"
		if (selected == 1)
			result = "No"
		if (selected == 2)
			result = "Yes"
	  result
	}
	
}