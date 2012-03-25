package basic

import function.Chris
import java.io.File

class Annotation(s: String, name: String, array: Array[File]) {
  
  var function = new Chris 
 
	if (s.equals("list"))
	  function.list(array, name)
	if (s.equals("group"))
	  function.group(array, name)
	  		
}