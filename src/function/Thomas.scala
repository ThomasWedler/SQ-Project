package function

import java.io.{File,FileInputStream,FileOutputStream}
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class Thomas {
  
	def getFiles = {
		var fc = new JFileChooser()
		var filter = new FileNameExtensionFilter("JPG, PDF and MP4", "jpg", "pdf", "mp4")
		fc.setFileFilter(filter)
		fc.setMultiSelectionEnabled(true)
		fc.showOpenDialog(null)
		fc.getSelectedFiles()
   	}
	
	def copy(files: Array[File]) = {
		for (f <- files) {
			var name = f.getName()
			var length = name.length()
			var suffix = name.substring(length-3, length)
			checkCopy(f, suffix)
		}
	}
	
	def checkCopy(f: File, filetype: String) = {
		var destination = new File("filesystem/" + filetype + "/" + f.getName())
	    if (!destination.exists()) {
	    	new FileOutputStream(destination) getChannel() transferFrom(new FileInputStream(f) getChannel, 0, Long.MaxValue)
	    	println(f.getName() + " was successfully imported.")
	  	} else
	    println("Import has failed. " + f.getName() + " already exists.")
	}

}