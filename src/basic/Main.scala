package basic

import java.io.File

object Main {

  def main(args: Array[String]) {
	  createFilesystem;
	  new Import();
  }
  
  def createFilesystem = {
    var jpg = new File("filesystem/jpg")
    var pdf = new File("filesystem/pdf")
    var mp4 = new File("filesystem/mp4")
    if (!jpg.exists()) {
    	jpg.mkdirs()
      	println("Directory filesystem/jpg successfully created.")
    } else
    	println("Directory filesystem/jpg already exists.")
    if (!pdf.exists()) {
    	pdf.mkdirs()
    	println("Directory filesystem/pdf successfully created.")
    } else
    	println("Directory filesystem/pdf already exists.")
    if (!mp4.exists()) {
    	mp4.mkdirs()
    	println("Directory filesystem/mp4 successfully created.")
    } else
    	println("Directory filesystem/mp4 already exists.")
  }
  
}