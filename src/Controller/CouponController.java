package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import Service.ICommonService;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CouponController extends AbstractController implements Initializable {
	Parent root;
	@FXML TextField couponnum;
	@FXML Button Btncouponfin;
	
	public String code;
	public static String idid;
	public static int number;
	final static String DRIVER = "org.sqlite.JDBC";
	final static String DB = "jdbc:sqlite:src/resources/login.db";
	Connection conn;
	ICommonService commonService;
	Parent pc;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {			
	}
	
	public void btnTest(ActionEvent e) {
		String inputnum = couponnum.getText();
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(DB);
			
			Statement stmt = conn.createStatement();
			String sql = "SELECT code FROM member "+	
						"WHERE ids = "+"'"+idid+"'";
			ResultSet rs = stmt.executeQuery(sql);
			code = rs.getString(1);
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if(inputnum.equals(code)==true) {
			 Alert alert = new Alert(AlertType.WARNING);
			 alert.setTitle("Success");
			 alert.setHeaderText("Correct Code");
			 alert.setContentText("Discount Code Inputed");
			 alert.show();
			 
			 Label lbl = (Label) pc.lookup("#lblcoupon");
			 lbl.setText("3000");
			 
			 Parent root = (Parent)e.getSource();
				Stage stage = (Stage) root.getScene().getWindow();
				stage.close();
 
		 } 
		 else {
			 Alert alert = new Alert(AlertType.WARNING);
			 alert.setTitle("Failed");
			 alert.setHeaderText("Code Input Failed");
			 alert.setContentText("You Inputed worng Code");

			 alert.showAndWait(); 
		 }
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}

	public void setId(String id) {
		this.idid = id;

	}
	public void setPCroot(Parent root) {
		this.pc = root;
	}
}
