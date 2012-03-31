package basic

import scala.xml._
import org.apache.commons.io.FileUtils
import java.io.{ File, IOException }

/** Provides methods for reading and editing the configuration file */
class Config {
  // Set path and filename of configuration file
  val configFile = "config.xml"

  /** Checks if configuration file exits and contains valid values.
    * In case file doesn't exists it is created and filled with standard values.
    */
  def tryConfig() {
    if (!(new File(configFile).exists())) {
      val file: File = new File(configFile)
      try {
        new File(configFile).createNewFile()
      } catch {
        case io: IOException => println("No configuration file found. Failed to create a new one.")
      }
      var config = scala.xml.XML.loadFile(configFile)
      
      // Create XML-Elements with empty values.
      val newJpgRead = Elem(null, "read", Null, TopScope, Text(""))
      val newJpgEdit = Elem(null, "edit", Null, TopScope, Text(""))
      val newPdfRead = Elem(null, "read", Null, TopScope, Text(""))
      val newPdfEdit = Elem(null, "edit", Null, TopScope, Text(""))
      val newMp4Read = Elem(null, "read", Null, TopScope, Text(""))
      val newMp4Edit = Elem(null, "edit", Null, TopScope, Text(""))
      val newJpg = Elem(null, "jpg", Null, TopScope, newJpgRead, newJpgEdit)
      val newPdf = Elem(null, "jpg", Null, TopScope, newPdfRead, newPdfEdit)
      val newMp4 = Elem(null, "jpg", Null, TopScope, newMp4Read, newMp4Edit)
      val newConfig = Elem(null, "config", Null, TopScope, newJpg, newPdf, newMp4)
      
      var prettyfier = new scala.xml.PrettyPrinter(80, 2)
      val header = """<?xml version="1.0" encoding="UTF-8" ?>""" + "\n"
      val prettyConfig = header + prettyfier.format(config)
      val xmlFile: File = new File(configFile)
      try {
        FileUtils.write(xmlFile, prettyConfig, "UTF-8")
      } catch {
        case io: IOException => println("No configuration file found. Created a new one but failed to fill it.")
      }
    } else {
      try {
        var config = scala.xml.XML.loadFile(configFile)
        val jpgRead = (config \\ "config" \\ "jpg" \ "read").text
        val jpgEdit = (config \\ "config" \\ "jpg" \ "edit").text
        val pdfRead = (config \\ "config" \\ "pdf" \ "read").text
        val pdfEdit = (config \\ "config" \\ "pdf" \ "edit").text
        val mp4Read = (config \\ "config" \\ "mp4" \ "read").text
        val mp4Edit = (config \\ "config" \\ "mp4" \ "edit").text
      } catch {
        case wrongXML: Exception => println("Configuration file is broken. Remove it and retry again.")
      }
    }
  }

  /** Returns reader for given filetype.
    *
    * @param filetype (String).
    * @return reader for given filetype (String).
    */
  def getReader(filetype: String): String = {
    tryConfig()
    val config = scala.xml.XML.loadFile(configFile)
    if (filetype == "jpg") {
      return (config \\ "config" \\ "jpg" \ "read").text
    } else if (filetype == "pdf") {
      return (config \\ "config" \\ "pdf" \ "read").text
    } else if (filetype == "mp4") {
      return (config \\ "config" \\ "mp4" \ "read").text
    } else {
      return "Unknown filetype."
    }
  }

  /** Returns editor for given filetype.
    *
    * @param filetype (String).
    * @return editor for given filetype (String).
    */
  def getEditor(filetype: String): String = {
    tryConfig()
    val config = scala.xml.XML.loadFile(configFile)
    if (filetype == "jpg") {
      return (config \\ "config" \\ "jpg" \ "edit").text
    } else if (filetype == "pdf") {
      return (config \\ "config" \\ "pdf" \ "edit").text
    } else if (filetype == "mp4") {
      return (config \\ "config" \\ "mp4" \ "edit").text
    } else {
      return "Unknown filetype."
    }
  }

  /** Sets given reader for given filetype.
    *
    * @param filetype (String).
    * @param reader (String).
    */
  def setReader(filetype: String, reader: String) {
    tryConfig()
    var config = scala.xml.XML.loadFile(configFile)

    // Read in specific values out of the configuration file.
    val jpgRead = (config \\ "config" \\ "jpg" \ "read").text
    val jpgEdit = (config \\ "config" \\ "jpg" \ "edit").text
    val pdfRead = (config \\ "config" \\ "pdf" \ "read").text
    val pdfEdit = (config \\ "config" \\ "pdf" \ "edit").text
    val mp4Read = (config \\ "config" \\ "mp4" \ "read").text
    val mp4Edit = (config \\ "config" \\ "mp4" \ "edit").text

    // Create new XML-elements by filling them with read values.
    var newJpgRead = Elem(null, "read", Null, TopScope, Text(jpgRead))
    val newJpgEdit = Elem(null, "edit", Null, TopScope, Text(jpgEdit))
    var newPdfRead = Elem(null, "read", Null, TopScope, Text(pdfRead))
    val newPdfEdit = Elem(null, "edit", Null, TopScope, Text(pdfEdit))
    var newMp4Read = Elem(null, "read", Null, TopScope, Text(mp4Read))
    val newMp4Edit = Elem(null, "edit", Null, TopScope, Text(mp4Edit))

    // Change values according to the given filetype.
    if (filetype == "jpg") {
      newJpgRead = Elem(null, "read", Null, TopScope, Text(reader))
    } else if (filetype == "pdf") {
      newPdfRead = Elem(null, "read", Null, TopScope, Text(reader))
    } else if (filetype == "mp4") {
      newMp4Read = Elem(null, "read", Null, TopScope, Text(reader))
    }
    val newJpg = Elem(null, "jpg", Null, TopScope, newJpgRead, newJpgEdit)
    val newPdf = Elem(null, "jpg", Null, TopScope, newPdfRead, newPdfEdit)
    val newMp4 = Elem(null, "jpg", Null, TopScope, newMp4Read, newMp4Edit)

    val newConfig = Elem(null, "config", Null, TopScope, newJpg, newPdf, newMp4)

    var prettyfier = new scala.xml.PrettyPrinter(80, 2)
    val header = """<?xml version="1.0" encoding="UTF-8" ?>""" + "\n"
    val prettyConfig = header + prettyfier.format(newConfig)
    val xmlFile: File = new File(configFile)
    FileUtils.write(xmlFile, prettyConfig, "UTF-8")
  }

  /** Sets given editor for given filetype.
    *
    * @param filetype (String).
    * @param editor (String).
    */
  def setEditor(filetype: String, editor: String) {
    tryConfig()
    var config = scala.xml.XML.loadFile(configFile)

    // Read in specific values out of the configuration file.
    val jpgRead = (config \\ "config" \\ "jpg" \ "read").text
    val jpgEdit = (config \\ "config" \\ "jpg" \ "edit").text
    val pdfRead = (config \\ "config" \\ "pdf" \ "read").text
    val pdfEdit = (config \\ "config" \\ "pdf" \ "edit").text
    val mp4Read = (config \\ "config" \\ "mp4" \ "read").text
    val mp4Edit = (config \\ "config" \\ "mp4" \ "edit").text

    // Create new XML-elements by filling them with read values.
    val newJpgRead = Elem(null, "read", Null, TopScope, Text(jpgRead))
    var newJpgEdit = Elem(null, "edit", Null, TopScope, Text(jpgEdit))
    val newPdfRead = Elem(null, "read", Null, TopScope, Text(pdfRead))
    var newPdfEdit = Elem(null, "edit", Null, TopScope, Text(pdfEdit))
    val newMp4Read = Elem(null, "read", Null, TopScope, Text(mp4Read))
    var newMp4Edit = Elem(null, "edit", Null, TopScope, Text(mp4Edit))

    // Change values according to the given filetype.
    if (filetype == "jpg") {
      val newJpgEdit = Elem(null, "edit", Null, TopScope, Text(editor))
    } else if (filetype == "pdf") {
      val newPdfEdit = Elem(null, "edit", Null, TopScope, Text(editor))
    } else if (filetype == "mp4") {
      val newMp4Edit = Elem(null, "edit", Null, TopScope, Text(editor))
    }
    val newJpg = Elem(null, "jpg", Null, TopScope, newJpgRead, newJpgEdit)
    val newPdf = Elem(null, "jpg", Null, TopScope, newPdfRead, newPdfEdit)
    val newMp4 = Elem(null, "jpg", Null, TopScope, newMp4Read, newMp4Edit)

    val newConfig = Elem(null, "config", Null, TopScope, newJpg, newPdf, newMp4)

    var prettyfier = new scala.xml.PrettyPrinter(80, 2)
    val header = """<?xml version="1.0" encoding="UTF-8" ?>""" + "\n"
    val prettyConfig = header + prettyfier.format(newConfig)
    val xmlFile: File = new File(configFile)
    FileUtils.write(xmlFile, prettyConfig, "UTF-8")
  }
}