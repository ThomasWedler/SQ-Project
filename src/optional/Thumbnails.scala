package optional

import java.awt.Image
import java.io.File
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import java.awt.Color
import java.awt.RenderingHints

class Thumbnails {
  // Check if thumbnail exists (file is just the filename and NOT path and filename...)
  def isThumb(file: String): Boolean= {
    var extension = file.substring(file.lastIndexOf(".") + 1)
    var path = "filesystem/thumbnails/"
    var thumbnailFile: File = null
    if (extension == "jpg") {
      path = path + "jpg/"
      var thumbnailFile:  File = new File(path + file)
    } else if (extension == "pdf") {
      path = path + "pdf/"
      var thumbnailFile:  File = new File(path + file)
    } else if (extension == "mp4") {
      path = path + "mp4/"
      var thumbnailFile:  File = new File(path + file)
    }
    return thumbnailFile.exists
  }
  
  // Get path and filename of thumbnail (file is just the filename and NOT path and filename...) (make sure thumbnail exists)
  def getThumb(file: String): String= {
    var extension = file.substring(file.lastIndexOf(".") + 1)
    var path = "filesystem/thumbnails/"
    var thumbnailFile = null
    if (extension == "jpg") {
      var thumbnailFile = path + file
    } else if (extension == "pdf") {
      var thumbnailFile = path + "pdf/" + file.split('.').init :+ "jpg" mkString "."
    } else if (extension == "mp4") {
      var thumbnailFile = path + "mp4/" + file.split('.').init :+ "jpg" mkString "."
    }
    return thumbnailFile
  }
  
  // Generate thumbnail (file is just the filename and NOT path and filename...)
  def mkThumb(file: String) {
    var thumbWidth: Int = 100
    var thumbHeight: Int = 100
    var extension = file.substring(file.lastIndexOf(".") + 1)
    
    if (extension == "jpg") {
      val path = "filesystem/jpg/"
      var filename = path + file
      var thumbnailFile = "filesystem/thumbnails/jpg/" + file
      
      var image: Image = javax.imageio.ImageIO.read(new File(filename))
        
      var thumbRatio: Double = thumbWidth.toDouble/thumbHeight.toDouble
      var imageWidth: Int = image.getWidth(null)
      var imageHeight: Int = image.getHeight(null)
      var imageRatio: Double = imageWidth.toDouble/imageHeight.toDouble
        
      if (thumbRatio < imageRatio){
        thumbHeight = (thumbWidth/imageRatio).toInt      
      } else {
        thumbWidth = (thumbHeight*imageRatio).toInt      
      }
      
      if (imageWidth < thumbWidth && imageHeight < thumbHeight){
        thumbWidth = imageWidth
        thumbHeight = imageHeight;     
      } else if (imageWidth < thumbWidth) {
        thumbWidth = imageWidth
      } else if (imageHeight < thumbHeight) {
        thumbHeight = imageHeight
      }
      
      var thumbImage: BufferedImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB)
      var graphics2D: Graphics2D = thumbImage.createGraphics()
      graphics2D.setBackground(Color.WHITE)
      graphics2D.setPaint(Color.WHITE)
      graphics2D.fillRect(0, 0, thumbWidth, thumbHeight)
      graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
      graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null)
        
      javax.imageio.ImageIO.write(thumbImage, "jpg", new File(thumbnailFile));
    } else if (extension == "pdf") {
      val path = "filesystem/pdf/"
      var filename = path + file
      var thumbnailFile = "filesystem/thumbnails/pdf/" + file.split('.').init :+ "jpg" mkString "."      
    } else if (extension == "mp4") {
      val path = "filesystem/mp4/"
      var filename = path + file
      var thumbnailFile = "filesystem/thumbnails/mp4/" + file.split('.').init :+ "jpg" mkString "."      
    }
  }
}