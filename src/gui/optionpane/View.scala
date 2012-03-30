package gui.optionpane
import scala.swing.Frame
import javax.swing.JPanel
import javax.swing.SpringLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JTextField
import java.awt.Dimension
import scala.swing.Component
import javax.swing.border.LineBorder
import java.awt.Color
import scala.swing.Alignment
import java.awt.Font

class View extends Frame {
  
  title = "Configuration"
    
    var panel = new JPanel
    var layout = new SpringLayout
    panel.setLayout(layout)
    
    var btnSave = new JButton("Save")
  	var btnCancel = new JButton("Cancel")
  	
  	var btnFC1 = new JButton("...")
   	var btnFC2 = new JButton("...")
  	var btnFC3 = new JButton("...")
  	var btnFC4 = new JButton("...")
  	var btnFC5 = new JButton("...")
  	var btnFC6 = new JButton("...")
  
  	var lblPathEditJPG = new JLabel("edit JPG with:")
    var lblPathEditPDF = new JLabel("edit PDF with:")
  	var lblPathEditMP4 = new JLabel("edit MP4 with:")
  	var lblPathReadJPG = new JLabel("view JPG with:")
  	var lblPathReadPDF = new JLabel("view PDF with:")
  	var lblPathReadMP4 = new JLabel("view MP4 with:")
  	var lblEdit = new JLabel("Programs to edit files")
  	var lblView = new JLabel("Programs to view files")

  	lblEdit.setFont(new Font("Dialog Bold", 1, 15));
	lblView.setFont(new Font("Dialog Bold", 1, 15));
  
	lblEdit.setHorizontalAlignment(Alignment.Center.id)
	lblView.setHorizontalAlignment(Alignment.Center.id)
  	lblPathEditJPG.setHorizontalAlignment(Alignment.Right.id)
  	lblPathEditPDF.setHorizontalAlignment(Alignment.Right.id)
  	lblPathEditMP4.setHorizontalAlignment(Alignment.Right.id)
  	lblPathReadJPG.setHorizontalAlignment(Alignment.Right.id)
  	lblPathReadPDF.setHorizontalAlignment(Alignment.Right.id)
  	lblPathReadMP4.setHorizontalAlignment(Alignment.Right.id)
  
  	var tfPathEditJPG = new JTextField
  	var tfPathEditPDF = new JTextField
  	var tfPathEditMP4 = new JTextField
  	var tfPathReadJPG = new JTextField
  	var tfPathReadPDF = new JTextField
  	var tfPathReadMP4 = new JTextField
  	
  	tfPathEditJPG.setEditable(false)
  	tfPathEditPDF.setEditable(false)
  	tfPathEditMP4.setEditable(false)
  	tfPathReadJPG.setEditable(false)
	tfPathReadPDF.setEditable(false)
	tfPathReadMP4.setEditable(false)

  	minimumSize = new Dimension(800, 600)
    centerOnScreen
	resizable = false

	layout.putConstraint(SpringLayout.EAST, btnCancel, -20, SpringLayout.EAST, panel);
	layout.putConstraint(SpringLayout.SOUTH, btnCancel, -20, SpringLayout.SOUTH, panel);
	layout.putConstraint(SpringLayout.NORTH, btnCancel, 530, SpringLayout.NORTH, panel);
	layout.putConstraint(SpringLayout.WEST, btnCancel, 680, SpringLayout.WEST, panel);
	panel.add(btnCancel)
	
	layout.putConstraint(SpringLayout.EAST, btnSave, -10, SpringLayout.WEST, btnCancel);
	layout.putConstraint(SpringLayout.NORTH, btnSave, 0, SpringLayout.NORTH, btnCancel);
	layout.putConstraint(SpringLayout.SOUTH, btnSave, 0, SpringLayout.SOUTH, btnCancel);
	layout.putConstraint(SpringLayout.WEST, btnSave, 570, SpringLayout.WEST, panel);
	panel.add(btnSave)
	
	layout.putConstraint(SpringLayout.EAST, lblPathEditJPG, -627, SpringLayout.EAST, panel);
	layout.putConstraint(SpringLayout.SOUTH, lblPathEditJPG, -370, SpringLayout.SOUTH, panel);
	layout.putConstraint(SpringLayout.NORTH, lblPathEditJPG, 180, SpringLayout.NORTH, panel);
	layout.putConstraint(SpringLayout.WEST, lblPathEditJPG, 70, SpringLayout.WEST, panel);
	panel.add(lblPathEditJPG)
	
	layout.putConstraint(SpringLayout.EAST, tfPathEditJPG, -481, SpringLayout.EAST, panel);
	layout.putConstraint(SpringLayout.SOUTH, tfPathEditJPG, 0, SpringLayout.SOUTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.NORTH, tfPathEditJPG, 0, SpringLayout.NORTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.WEST, tfPathEditJPG, 6, SpringLayout.EAST, lblPathEditJPG);
	panel.add(tfPathEditJPG)
	
	layout.putConstraint(SpringLayout.EAST, btnFC1, -425, SpringLayout.EAST, panel);
	layout.putConstraint(SpringLayout.SOUTH, btnFC1, 0, SpringLayout.SOUTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.NORTH, btnFC1, 0, SpringLayout.NORTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.WEST, btnFC1, 6, SpringLayout.EAST, tfPathEditJPG);
	panel.add(btnFC1)
	
	layout.putConstraint(SpringLayout.EAST, lblPathReadJPG, -272, SpringLayout.EAST, panel);
	layout.putConstraint(SpringLayout.SOUTH, lblPathReadJPG, 0, SpringLayout.SOUTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.NORTH, lblPathReadJPG, 0, SpringLayout.NORTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.WEST, lblPathReadJPG, 50, SpringLayout.EAST, btnFC1);
	panel.add(lblPathReadJPG)
	
	layout.putConstraint(SpringLayout.EAST, tfPathReadJPG, -126, SpringLayout.EAST, panel);
	layout.putConstraint(SpringLayout.SOUTH, tfPathReadJPG, 0, SpringLayout.SOUTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.NORTH, tfPathReadJPG, 0, SpringLayout.NORTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.WEST, tfPathReadJPG, 6, SpringLayout.EAST, lblPathReadJPG);
	panel.add(tfPathReadJPG)
	
	layout.putConstraint(SpringLayout.EAST, btnFC2, -70, SpringLayout.EAST, panel);
	layout.putConstraint(SpringLayout.SOUTH, btnFC2, 0, SpringLayout.SOUTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.NORTH, btnFC2, 0, SpringLayout.NORTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.WEST, btnFC2, 6, SpringLayout.EAST, tfPathReadJPG);
	panel.add(btnFC2)
	
	layout.putConstraint(SpringLayout.EAST, lblPathEditPDF, 0, SpringLayout.EAST, lblPathEditJPG);
	layout.putConstraint(SpringLayout.SOUTH, lblPathEditPDF, 100, SpringLayout.SOUTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.NORTH, lblPathEditPDF, 100, SpringLayout.NORTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.WEST, lblPathEditPDF, 0, SpringLayout.WEST, lblPathEditJPG);
	panel.add(lblPathEditPDF)
	
	layout.putConstraint(SpringLayout.EAST, tfPathEditPDF, 0, SpringLayout.EAST, tfPathEditJPG);
	layout.putConstraint(SpringLayout.SOUTH, tfPathEditPDF, 0, SpringLayout.SOUTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.NORTH, tfPathEditPDF, 0, SpringLayout.NORTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.WEST, tfPathEditPDF, 0, SpringLayout.WEST, tfPathEditJPG);
	panel.add(tfPathEditPDF)
	
	layout.putConstraint(SpringLayout.EAST, btnFC3, 0, SpringLayout.EAST, btnFC1);
	layout.putConstraint(SpringLayout.SOUTH, btnFC3, 0, SpringLayout.SOUTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.NORTH, btnFC3, 0, SpringLayout.NORTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.WEST, btnFC3, 0, SpringLayout.WEST, btnFC1);
	panel.add(btnFC3)
	
	layout.putConstraint(SpringLayout.EAST, lblPathReadPDF, 0, SpringLayout.EAST, lblPathReadJPG);
	layout.putConstraint(SpringLayout.SOUTH, lblPathReadPDF, 0, SpringLayout.SOUTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.NORTH, lblPathReadPDF, 0, SpringLayout.NORTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.WEST, lblPathReadPDF, 0, SpringLayout.WEST, lblPathReadJPG);
	panel.add(lblPathReadPDF)
	
	layout.putConstraint(SpringLayout.EAST, tfPathReadPDF, 0, SpringLayout.EAST, tfPathReadJPG);
	layout.putConstraint(SpringLayout.SOUTH, tfPathReadPDF, 0, SpringLayout.SOUTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.NORTH, tfPathReadPDF, 0, SpringLayout.NORTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.WEST, tfPathReadPDF, 0, SpringLayout.WEST, tfPathReadJPG);
	panel.add(tfPathReadPDF)
	
	layout.putConstraint(SpringLayout.EAST, btnFC4, 0, SpringLayout.EAST, btnFC2);
	layout.putConstraint(SpringLayout.SOUTH, btnFC4, 0, SpringLayout.SOUTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.NORTH, btnFC4, 0, SpringLayout.NORTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.WEST, btnFC4, 0, SpringLayout.WEST, btnFC2);
	panel.add(btnFC4)
	
	layout.putConstraint(SpringLayout.EAST, lblPathEditMP4, 0, SpringLayout.EAST, lblPathEditPDF);
	layout.putConstraint(SpringLayout.SOUTH, lblPathEditMP4, 100, SpringLayout.SOUTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.NORTH, lblPathEditMP4, 100, SpringLayout.NORTH, lblPathEditPDF);
	layout.putConstraint(SpringLayout.WEST, lblPathEditMP4, 0, SpringLayout.WEST, lblPathEditPDF);
	panel.add(lblPathEditMP4)
	
	layout.putConstraint(SpringLayout.EAST, tfPathEditMP4, 0, SpringLayout.EAST, tfPathEditPDF);
	layout.putConstraint(SpringLayout.SOUTH, tfPathEditMP4, 0, SpringLayout.SOUTH, lblPathEditMP4);
	layout.putConstraint(SpringLayout.NORTH, tfPathEditMP4, 0, SpringLayout.NORTH, lblPathEditMP4);
	layout.putConstraint(SpringLayout.WEST, tfPathEditMP4, 0, SpringLayout.WEST, tfPathEditPDF);
	panel.add(tfPathEditMP4)
	
	layout.putConstraint(SpringLayout.EAST, btnFC5, 0, SpringLayout.EAST, btnFC3);
	layout.putConstraint(SpringLayout.SOUTH, btnFC5, 0, SpringLayout.SOUTH, lblPathEditMP4);
	layout.putConstraint(SpringLayout.NORTH, btnFC5, 0, SpringLayout.NORTH, lblPathEditMP4);
	layout.putConstraint(SpringLayout.WEST, btnFC5, 0, SpringLayout.WEST, btnFC3);
	panel.add(btnFC5)
	
	layout.putConstraint(SpringLayout.EAST, lblPathReadMP4, 0, SpringLayout.EAST, lblPathReadPDF);
	layout.putConstraint(SpringLayout.SOUTH, lblPathReadMP4, 0, SpringLayout.SOUTH, lblPathEditMP4);
	layout.putConstraint(SpringLayout.NORTH, lblPathReadMP4, 0, SpringLayout.NORTH, lblPathEditMP4);
	layout.putConstraint(SpringLayout.WEST, lblPathReadMP4, 0, SpringLayout.WEST, lblPathReadPDF);
	panel.add(lblPathReadMP4)
	
	layout.putConstraint(SpringLayout.EAST, tfPathReadMP4, 0, SpringLayout.EAST, tfPathReadPDF);
	layout.putConstraint(SpringLayout.SOUTH, tfPathReadMP4, 0, SpringLayout.SOUTH, lblPathEditMP4);
	layout.putConstraint(SpringLayout.NORTH, tfPathReadMP4, 0, SpringLayout.NORTH, lblPathEditMP4);
	layout.putConstraint(SpringLayout.WEST, tfPathReadMP4, 0, SpringLayout.WEST, tfPathReadPDF);
	panel.add(tfPathReadMP4)
	
	layout.putConstraint(SpringLayout.EAST, btnFC6, 0, SpringLayout.EAST, btnFC4);
	layout.putConstraint(SpringLayout.SOUTH, btnFC6, 0, SpringLayout.SOUTH, lblPathEditMP4);
	layout.putConstraint(SpringLayout.NORTH, btnFC6, 0, SpringLayout.NORTH, lblPathEditMP4);
	layout.putConstraint(SpringLayout.WEST, btnFC6, 0, SpringLayout.WEST, btnFC4);
	panel.add(btnFC6) 
	
	layout.putConstraint(SpringLayout.EAST, lblEdit, -400, SpringLayout.EAST, panel);
	layout.putConstraint(SpringLayout.SOUTH, lblEdit, -65, SpringLayout.NORTH, lblPathEditJPG);
	layout.putConstraint(SpringLayout.NORTH, lblEdit, 65, SpringLayout.NORTH, panel);
	layout.putConstraint(SpringLayout.WEST, lblEdit, 0, SpringLayout.WEST, panel);
	panel.add(lblEdit) 
	
	layout.putConstraint(SpringLayout.EAST, lblView, 0, SpringLayout.EAST, panel);
	layout.putConstraint(SpringLayout.SOUTH, lblView, 0, SpringLayout.SOUTH, lblEdit);
	layout.putConstraint(SpringLayout.NORTH, lblView, 0, SpringLayout.NORTH, lblEdit);
	layout.putConstraint(SpringLayout.WEST, lblView, 400, SpringLayout.WEST, panel);
	panel.add(lblView) 
	
	contents = Component.wrap(panel)
	
	visible = true

}