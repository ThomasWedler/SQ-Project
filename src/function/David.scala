package function

import java.io.File
import java.lang.Object._

class David {

  var config = new basic.Config

  def getType(f: File): String = {
    //get the filepath and split it at "."
    val resArray: Array[String] = f.getAbsolutePath().split('.')
    //return last entry
    return resArray(resArray.length - 1)
  }

  //function for opening a file (read only)
  def openReader(file: File) {
    //get the absolute path to the particular editor 
    val reader = config.getReader(getType(file))
    var array = reader.split('/')
    var name = array(array.length - 1)
    name = name.substring(0, name.length - 4)
    var command = "open -a " + reader + "/Contents/MacOS/" + name + " " + file.getAbsolutePath
    Runtime.getRuntime.exec(command)
  }

  //function for opening a file (edit mode)
  def openEditor(file: File) {
    //get the absolute path to the particular editor 
    val editor = config.getEditor(getType(file))
    //start it for the given file
    var array = editor.split('/')
    var name = array(array.length - 1)
    name = name.substring(0, name.length - 4)
    var command = "open -a " + editor + "/Contents/MacOS/" + name + " " + file.getAbsolutePath
    Runtime.getRuntime.exec(command)
  }

}