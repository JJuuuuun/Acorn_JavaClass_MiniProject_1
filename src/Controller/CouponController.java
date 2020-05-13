package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class CouponController extends AbstractController implements Initializable {
	Parent root;
	@FXML TextField couponnum;
	@FXML Button Btncouponfin;
	
	public String code;
	public static String idid;
	final static String DRIVER = "org.sqlite.JDBC";
	final static String DB = "jdbc:sqlite:src/resources/login.db";
	Connection conn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//			String inputnum = couponnum.getText();		
	}
	
	public void btnTest() {
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(inputnum.equals(code)==true) {
			 Alert alert = new Alert(AlertType.WARNING);
			 alert.setTitle("Success");
			 alert.setHeaderText("Correct Code");
			 alert.setContentText("Discount Code Inputed");
			 alert.show();
		}
////			 (controller.lblcoupon).setText("2313");
 
		 else {
			 Alert alert = new Alert(AlertType.WARNING);
			 alert.setTitle("濡쒓렇�씤");
			 alert.setHeaderText("荑좏룿�벑濡앹뿉 �떎�뙣�븯���뒿�땲�떎");
			 alert.setContentText("�떎�떆 �엯�젰�빐二쇱꽭�슂");

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
}
