package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Service.CommonService;
import Service.ICommonService;
import Service.IMenuBarService;
import Service.MenuBarService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class loginController extends AbstractController implements Initializable{
	@FXML private Label lbllogin;
	@FXML private TextField textid;
	@FXML private TextField textpw;
	@FXML private Button btnlogin;
	@FXML private Button btncancel;
	
	public String idid, namename, rank;
	public int check=6554, success=0;
	
	final static String DRIVER = "org.sqlite.JDBC";
	final static String DB = "jdbc:sqlite:src/resources/login.db";
	Connection conn;
	Parent root;
	ICommonService commonService;

	//0512 RootScnene Controller
	AbstractController rootController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		commonService = new CommonService();

		btnlogin.setOnAction(e->{
			try {
				idid = textid.getText();
				LoginProc(e);
				if(success==1) {
					setData();
					SendData();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		btncancel.setOnAction(this::CancelProc);
		
	}
	public void LoginProc(ActionEvent e) throws IOException {
		String id = textid.getText();
		String pw = textpw.getText();
		
		if (id.equals("")) {
			ErrorMsg("Error", "Login Fail", "You do not input ID");
			textid.requestFocus();
		}
		else if (pw.equals("")) {
			ErrorMsg("Error", "Login Fail", "You do not input Password");
			textpw.requestFocus();
		}
		else {
			check(id, pw);
			if(success==1) {
				LoginMain();
			}
		}
	}
	public void check(String id, String pw) {
		try {
			Class.forName(DRIVER);
	        conn = DriverManager.getConnection(DB);

	        Statement stmt = conn.createStatement();
			String sql = "SELECT COUNT(*) "+
						"FROM member ";

			String sql2 = "SELECT pw FROM member "+	
					"WHERE ids = "+"'"+id+"'";
			ResultSet rsmax = stmt.executeQuery(sql);
			int max = rsmax.getInt(1);
			
			for(int i=1;i<=max;i++) {
				String sql3 = "SELECT ids FROM member "+	
							"WHERE ROWID = "+i;
				ResultSet rs3 = stmt.executeQuery(sql3);
				if(rs3.getString(1).equals(id)) {
					check=1;
					break;
				}
				else check=0;
			}
			if(check==0) {
				ErrorMsg("Error", "Login Fail", "Cannot finded ID");
				textid.clear();
				textid.requestFocus();
			}
			else {
				ResultSet rs = stmt.executeQuery(sql2);
				if(!rs.getString(1).equals(pw)) {
					ErrorMsg("Error", "Login Fail", "Wrong Password");
					textpw.clear();
					textpw.requestFocus();
				}
				else {
					ErrorMsg("Success", "Login Success", "Successfully Login");
					success=1;
				}
			}
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}
	public void ErrorMsg(String title, String headerStr, String ContentTxt) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerStr);
		alert.setContentText(ContentTxt);
		alert.showAndWait();
	}
	public void MainView()  {
		commonService.openWindow(btncancel.getId());
	}
	public void CancelProc(ActionEvent e) {
//		commonService.closeWindow(e);
		commonService.changeWindow(rootController.getRoot(), "RootScene", Pos.CENTER);
	}

	public void LoginMain() {
		commonService.changeWindow(rootController.getRoot(), "LoginSuccess", Pos.TOP_LEFT);
		commonService.changeWindow(rootController.getRoot(), "RootScene", Pos.CENTER);	// 0512 鍮덊솕硫� 異쒕젰�쐞�븳 �엫�떆 肄붾뱶
//		System.out.println(); // test code
	}

	private void SendData() {
		LoginMainController loginMainController = (LoginMainController)commonService.getController("loginmain.fxml");
		loginMainController.setText(namename, idid, rank);
		loginMainController.setRootController(rootController);
//		System.out.println("Data transmission success"); // test code
	}
	private void setData() {
		String id = textid.getText();

		try {

			Class.forName(DRIVER);
	        conn = DriverManager.getConnection(DB);
	        Statement stmt2 = conn.createStatement();
			String sql = "SELECT name FROM member "+
						"WHERE ids = "+"'"+id+"'";
			ResultSet rs = stmt2.executeQuery(sql);
			namename = rs.getString(1);

			String sql2 = "SELECT rank FROM member "+	
						"WHERE ids = "+"'"+id+"'";
			ResultSet rs2 = stmt2.executeQuery(sql2);
			rank = rs2.getString(1);
			
			stmt2.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}

	//0512 add to control RootScene
	public void setRootController(AbstractController controller) {
		this.rootController = controller;
	}
}
