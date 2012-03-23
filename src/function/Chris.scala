package function

import java.io._
import java.io.File
import javax.swing.JOptionPane

class Chris {

	
   def list(array : Array[File], name : String) {
      val writerlist = new PrintWriter(new File(name+".txt" ))	
      writerlist.write("Playlist\r\n")
      for (a <- array)
        writerlist.write(a.getPath + "\r\n")
      writerlist.close
  }
  
    def group(array : Array[File], name : String) {
    	sort(array : Array[File])
    	val writergroup = new PrintWriter(new File(name+".txt" ))
    	writergroup.write("Playlist\r\n")
    	for (a <- array)
    		writergroup.write(a.getPath + "\r\n")
    	writergroup.close
  }

    def sort(array : Array[File]) {
    	scala.util.Sorting.quickSort(array)
  }
    
}