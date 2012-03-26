package function

  import java.io.File
  import scala.swing.FileChooser

  abstract class David {

    def getType(f: File): String = {
      //get the filepath and split it at "."
      val resArray: Array[String] = f.getAbsolutePath().split(".")
      //return last entry
      return resArray(resArray.length - 1)
    }

    def openProg(file: File, readOrEdit: String): Integer = {
      //invoke method getType to get the file ending as a string
      val line: String = "";
      val commandline: String = "";
      val fileType = getType(file)
      //check if the file shall be read or edited
// -------------------------------read------------------------------------------ //
      if (readOrEdit.equals("read")) {
        //check for filetype and..
        if (fileType.equals("pdf")) {
          //..open adobe reader
          Runtime.getRuntime().exec("start acrord32.exe " + file.getAbsolutePath())          
        } else if (fileType.equals("mp4")) {
          //..open windows media player
          Runtime.getRuntime().exec("start wmplayer " + file.getAbsolutePath())
        } else if (fileType.equals("jpg") || fileType.equals("jpeg")) {
          //..open windows picture stuff
          Runtime.getRuntime().exec("rundll32.exe %windir%\\system32\\shimgvw.dll,ImageView_Fullscreen " + file.getAbsolutePath())
        } else return 0
// ----------------------------------edit--------------------------------------- //
      } else if (readOrEdit.equals("edit")) {
        if (fileType.equals("pdf")) {
          //..open adobe acrobat
          Runtime.getRuntime().exec("start acrord32.exe " + file.getAbsolutePath())
        } else if (fileType.equals("mp4")) {
          //..open windows movie maker
          Runtime.getRuntime().exec("start moviemaker " + file.getAbsolutePath())
        } else if (fileType.equals("jpg") || fileType.equals("jpeg")) {
          //..open paint
          Runtime.getRuntime().exec("start mspaint " + file.getAbsolutePath())
        } else return 0
      } else return 0
      return 1
    }

    def main(args: Array[String]) {
      val fc = new FileChooser()
      println(getType(fc.selectedFile))
    }
  }