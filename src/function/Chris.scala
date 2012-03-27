package function

import java.io._
import java.io.File
import javax.swing.JOptionPane

class Chris {
	
   def list(list: List[File], name: String) {
      val writer = new PrintWriter(new File("filesystem/relations/" + name + ".txt"))	
      writer.write("List\r\n")
      for (l <- list)
        writer.write(l.getName + "\r\n")
      writer.close
  }
  
    def group(list : List[File], name : String) {
    	val writer = new PrintWriter(new File("filesystem/relations/" + name + ".txt"))
    	writer.write("Group\r\n")
    	for (l <- list)
    		writer.write(l.getName + "\r\n")
    	writer.close
  }
    
}