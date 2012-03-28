/** This package provides optional features to SQ-Project */
package optional

import java.awt.Image
import java.io.File
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import java.awt.Color
import java.awt.RenderingHints

/*
 * "PDFRenderer only deals with up to version 1.4 of the PDF spec.
 * The current version is 1.6, and there have been quite a few additions
 * and changes between 1.4 and 1.6 which seem to break PDFRenderer."
 */
import com.sun.pdfview.PDFFile
import com.sun.pdfview.PDFPage

import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.ByteBuffer
import java.awt.Rectangle

// See http://www.capricasoftware.co.uk/vlcj/index.php for requirements and dependencies
import java.util.concurrent.CountDownLatch
import uk.co.caprica.vlcj.player.MediaPlayer
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.MediaPlayerFactory

/** Provides methods for creating and managing thumbnails */
class Thumbnails {
  /** Checks if thumbnail of given file exists.
    *
    * @param file filename (don't include path) (String).
    * @return if thumbnail for given file already exists (Boolean).
    */
  def isThumb(file: String): Boolean = {
    var extension = file.substring(file.lastIndexOf(".") + 1)
    var path = "filesystem/thumbnails/"
    var thumbnailFile: File = new File("")
    if (extension == "jpg") {
      path = path + "jpg/"
      thumbnailFile = new File(path + file)
    } else if (extension == "pdf") {
      path = path + "pdf/"
      thumbnailFile = new File(path + file)
    } else if (extension == "mp4") {
      path = path + "mp4/"
      thumbnailFile = new File(path + file)
    } else if (extension == "txt") {
      /* 
       * txt-files are for relations only and therefor will not have a thumbnail.
       * To avoid thumbnail-generation true is returned even it's not :-)
       */
      return true
    }
    return thumbnailFile.exists
  }

  /** Gets path and filename of thumbnail.
    * Make sure thumbnail exists.
    *
    * @param file filename (don't include path) (String).
    * @return path and filename of thumbnail (String).
    */
  def getThumb(file: String): String = {
    var extension = file.substring(file.lastIndexOf(".") + 1)
    var path = "filesystem/thumbnails/"
    var thumbnailFile = ""
    if (extension == "jpg") {
      path = path + "jpg/"
      thumbnailFile = path + file
    } else if (extension == "pdf") {
      thumbnailFile = path + "pdf/" + file.split('.').init :+ "jpg" mkString "."
    } else if (extension == "mp4") {
      thumbnailFile = path + "mp4/" + file.split('.').init :+ "jpg" mkString "."
    }
    return thumbnailFile
  }

  /** Generates thumbnail and writes it to disc.
    *
    * @param file filename (don't include path) (String).
    */
  def mkThumb(file: String) {
    // Width and height of thumbnail
    var thumbWidth: Int = 100
    var thumbHeight: Int = 100

    var extension = file.substring(file.lastIndexOf(".") + 1)
    var image: Image = null
    var thumbnailFile: String = ""

    // Generate thumbnail of a JPEG-file
    if (extension == "jpg") {
      val path = "filesystem/jpg/"
      var filename = path + file
      thumbnailFile = "filesystem/thumbnails/jpg/" + file

      image = javax.imageio.ImageIO.read(new File(filename))

      // Generate thumbnail of a PDF-file
    } else if (extension == "pdf") {
      val path = "filesystem/pdf/"
      var filename = path + file
      thumbnailFile = "filesystem/thumbnails/pdf/" + file.split('.').init :+ "jpg" mkString "."

      var pdffilename: File = new File(filename)
      var raf: RandomAccessFile = new RandomAccessFile(pdffilename, "r")
      var channel: FileChannel = raf.getChannel()
      var buf: ByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size())
      var pdffile: PDFFile = new PDFFile(buf)

      // Draw first page to an image
      var page: PDFPage = pdffile.getPage(0)

      // Get width and height for the doc at the default zoom
      var rect: Rectangle = new Rectangle(0, 0, page.getBBox().getWidth().toInt, page.getBBox().getHeight().toInt)

      // Generate image
      image = page.getImage(rect.width, rect.height, rect, null, true, true)

      // Generate thumbnail of an MPEG4-file
    } else if (extension == "mp4") {
      val path = "filesystem/mp4/"
      var filename = path + file
      var thumbnailFile = "filesystem/thumbnails/mp4/" + file.split('.').init :+ "jpg" mkString "."

      /*
       * The following is an adaption of
       * http://code.google.com/p/vlcj/source/browse/trunk/vlcj/src/test/java/uk/co/caprica/vlcj/test/thumbs/ThumbsTest.java
       */
      val VLC_THUMBNAIL_POSITION: Float = 30.0f / 100.0f

      // Set media resource locator
      val mrl: String = "file://" + filename

      var factory: MediaPlayerFactory = new MediaPlayerFactory(
        "--intf", "dummy", /* no interface */
        "--vout", "dummy", /* we don't want video (output) */
        "--no-audio", /* we don't want audio (decoding) */
        "--no-video-title-show", /* nor the filename displayed */
        "--no-stats", /* no stats */
        "--no-sub-autodetect-file", /* we don't want subtitles */
        "--no-inhibit", /* we don't want interfaces */
        "--no-disable-screensaver", /* we don't want interfaces */
        "--no-snapshot-preview" /* no blending in dummy vout */ )

      var mediaPlayer: MediaPlayer = factory.newHeadlessMediaPlayer();

      val inPositionLatch: CountDownLatch = new CountDownLatch(1)
      val snapshotTakenLatch: CountDownLatch = new CountDownLatch(1)

      mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
        override def positionChanged(mediaPlayer: MediaPlayer, newPosition: Float) {
          if (newPosition >= VLC_THUMBNAIL_POSITION * 0.9f) { /* 90% margin */
            inPositionLatch.countDown()
          }
        }

        override def snapshotTaken(mediaPlayer: MediaPlayer, filename: String) {
          // Debugging
          System.out.println("snapshotTaken(filename=" + filename + ")")
          snapshotTakenLatch.countDown()
        }
      })

      if (mediaPlayer.startMedia(mrl)) {
        mediaPlayer.setPosition(VLC_THUMBNAIL_POSITION)
        inPositionLatch.await() // Might wait forever if error
        image = mediaPlayer.getSnapshot()
        snapshotTakenLatch.await() // Might wait forever if error
        mediaPlayer.stop()
      }

      mediaPlayer.release()
      factory.release()
    }

    var thumbRatio: Double = thumbWidth.toDouble / thumbHeight.toDouble
    var imageWidth: Int = image.getWidth(null)
    var imageHeight: Int = image.getHeight(null)
    var imageRatio: Double = imageWidth.toDouble / imageHeight.toDouble

    if (thumbRatio < imageRatio) {
      thumbHeight = (thumbWidth / imageRatio).toInt
    } else {
      thumbWidth = (thumbHeight * imageRatio).toInt
    }

    if (imageWidth < thumbWidth && imageHeight < thumbHeight) {
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

  }
}