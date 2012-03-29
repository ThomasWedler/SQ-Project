package basic

import scala.xml._
import org.apache.commons.io.FileUtils
import java.io.File

/** Provides methods for reading and editing the configuration file */
class Config {
  /** Returns reader for given filetype.
    *
    * @param filetype (String).
    * @return reader for given filetype (String).
    */
  def getReader(filetype: String): String = {
    val config = scala.xml.XML.loadFile("config.xml")
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
    val config = scala.xml.XML.loadFile("config.xml")
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
    var config = scala.xml.XML.loadFile("config.xml")
    val jpgread = (config \\ "config" \\ "jpg" \ "read").text
    val jpgedit = (config \\ "config" \\ "jpg" \ "edit").text
    val pdfread = (config \\ "config" \\ "pdf" \ "read").text
    val pdfedit = (config \\ "config" \\ "pdf" \ "edit").text
    val mp4read = (config \\ "config" \\ "mp4" \ "read").text
    val mp4edit = (config \\ "config" \\ "mp4" \ "edit").text
    val newjpgread, newjpgedit, newpdfread, newpdfedit, newmp4read, newmp4edit = null
    if (filetype == "jpg") {
      val newjpgread = Elem(null, "read", Null, TopScope, Text(reader))
      val newjpgedit = Elem(null, "edit", Null, TopScope, Text(jpgedit))
      val newpdfread = Elem(null, "read", Null, TopScope, Text(pdfread))
      val newpdfedit = Elem(null, "edit", Null, TopScope, Text(pdfedit))
      val newmp4read = Elem(null, "read", Null, TopScope, Text(mp4read))
      val newmp4edit = Elem(null, "edit", Null, TopScope, Text(mp4edit))
    } else if (filetype == "pdf") {
      val newjpgread = Elem(null, "read", Null, TopScope, Text(jpgread))
      val newjpgedit = Elem(null, "edit", Null, TopScope, Text(jpgedit))
      val newpdfread = Elem(null, "read", Null, TopScope, Text(reader))
      val newpdfedit = Elem(null, "edit", Null, TopScope, Text(pdfedit))
      val newmp4read = Elem(null, "read", Null, TopScope, Text(mp4read))
      val newmp4edit = Elem(null, "edit", Null, TopScope, Text(mp4edit))
    } else if (filetype == "mp4") {
      val newjpgread = Elem(null, "read", Null, TopScope, Text(jpgread))
      val newjpgedit = Elem(null, "edit", Null, TopScope, Text(jpgedit))
      val newpdfread = Elem(null, "read", Null, TopScope, Text(pdfread))
      val newpdfedit = Elem(null, "edit", Null, TopScope, Text(pdfedit))
      val newmp4read = Elem(null, "read", Null, TopScope, Text(reader))
      val newmp4edit = Elem(null, "edit", Null, TopScope, Text(mp4edit))
    }
    val newjpg = Elem(null, "jpg", Null, TopScope, newjpgread, newjpgedit)
    val newpdf = Elem(null, "jpg", Null, TopScope, newpdfread, newpdfedit)
    val newmp4 = Elem(null, "jpg", Null, TopScope, newmp4read, newmp4edit)

    val newconfig = Elem(null, "config", Null, TopScope, newjpg, newpdf, newmp4)

    scala.xml.XML.save("config.xml", newconfig)
  }

  /** Sets given editor for given filetype.
    *
    * @param filetype (String).
    * @param editor (String).
    */
  def setEditor(filetype: String, editor: String) {
    var config = scala.xml.XML.loadFile("config.xml")
    val jpgread = (config \\ "config" \\ "jpg" \ "read").text
    val jpgedit = (config \\ "config" \\ "jpg" \ "edit").text
    val pdfread = (config \\ "config" \\ "pdf" \ "read").text
    val pdfedit = (config \\ "config" \\ "pdf" \ "edit").text
    val mp4read = (config \\ "config" \\ "mp4" \ "read").text
    val mp4edit = (config \\ "config" \\ "mp4" \ "edit").text
    val newjpgread, newjpgedit, newpdfread, newpdfedit, newmp4read, newmp4edit = null
    if (filetype == "jpg") {
      val newjpgread = Elem(null, "read", Null, TopScope, Text(jpgread))
      val newjpgedit = Elem(null, "edit", Null, TopScope, Text(editor))
      val newpdfread = Elem(null, "read", Null, TopScope, Text(pdfread))
      val newpdfedit = Elem(null, "edit", Null, TopScope, Text(pdfedit))
      val newmp4read = Elem(null, "read", Null, TopScope, Text(mp4read))
      val newmp4edit = Elem(null, "edit", Null, TopScope, Text(mp4edit))
    } else if (filetype == "pdf") {
      val newjpgread = Elem(null, "read", Null, TopScope, Text(jpgread))
      val newjpgedit = Elem(null, "edit", Null, TopScope, Text(jpgedit))
      val newpdfread = Elem(null, "read", Null, TopScope, Text(pdfread))
      val newpdfedit = Elem(null, "edit", Null, TopScope, Text(editor))
      val newmp4read = Elem(null, "read", Null, TopScope, Text(mp4read))
      val newmp4edit = Elem(null, "edit", Null, TopScope, Text(mp4edit))
    } else if (filetype == "mp4") {
      val newjpgread = Elem(null, "read", Null, TopScope, Text(jpgread))
      val newjpgedit = Elem(null, "edit", Null, TopScope, Text(jpgedit))
      val newpdfread = Elem(null, "read", Null, TopScope, Text(pdfread))
      val newpdfedit = Elem(null, "edit", Null, TopScope, Text(pdfedit))
      val newmp4read = Elem(null, "read", Null, TopScope, Text(mp4read))
      val newmp4edit = Elem(null, "edit", Null, TopScope, Text(editor))
    }
    val newjpg = Elem(null, "jpg", Null, TopScope, newjpgread, newjpgedit)
    val newpdf = Elem(null, "jpg", Null, TopScope, newpdfread, newpdfedit)
    val newmp4 = Elem(null, "jpg", Null, TopScope, newmp4read, newmp4edit)

    val newconfig = Elem(null, "config", Null, TopScope, newjpg, newpdf, newmp4)

    var prettyfier = new scala.xml.PrettyPrinter(80, 2)
    val header = """<?xml version="1.0" encoding="UTF-8" ?>""" + "\n"
    val prettyConfig = header + prettyfier.format(config)
    val xmlFile: File = new File("config.xml")
    FileUtils.write(xmlFile, prettyConfig, "UTF-8")    
  }

}