package basic

import java.io.File
import function.Thomas
import java.awt.event.ActionListener

// Thomas

class Import {
  
  var function = new Thomas  
  var files = function.getFiles
  
  // calls the copy function
  function.copy(files)
  
}