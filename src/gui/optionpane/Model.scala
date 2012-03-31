package gui.optionpane

class Model {
  
  var config = new basic.Config
  
  var pathReadJPG = config.getReader("jpg")
  var pathReadPDF = config.getReader("pdf")
  var pathReadMP4 = config.getReader("mp4")
  var pathEditJPG = config.getEditor("jpg")
  var pathEditPDF = config.getEditor("pdf")
  var pathEditMP4 = config.getEditor("mp4")
 
}