package basic

import function.Chris
import java.io.File

class Annotation(s: String, name: String, list: List[File]) {
  
  var function = new Chris 
 
	if (s.equals("List:"))
	  function.list(list, name)
	if (s.equals("Group:"))
	  function.group(list, name)
	  		
}