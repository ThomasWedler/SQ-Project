package basic

import function.Chris
import java.io.File

class Annotation {
  
  var function = new Chris 
	var name = "hans"
  	var file1 = new File("filesystem/pdf/erhebung.pdf")
  	var file2 = new File("filesystem/pdf/aufgaben.pdf")
    var file3 = new File("filesystem/pdf/bewertungsbogen.pdf")
	var array: Array[File] = Array(file1, file2, file3)
  function.group(array, name)
}