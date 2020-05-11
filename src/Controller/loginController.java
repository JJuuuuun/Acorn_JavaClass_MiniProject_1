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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class loginController extends AbstractController implements Initializable{
	@FXML private Label lbllogin;
	@FXML private TextField textid;
	@FXML private TextField textpw;
	@FXML private Button btnlogin;
	@FXML private Button btncancel;
	
	public String idid, namename, songsong;
	public int check=6554, success=0;
	
	final static String DRIVER = "org.sqlite.JDBC";
	final static String DB = "jdbc:sqlite:src/resources/login.db";
	Connection conn;
	Parent root;
	ICommonService commonService;

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
		btncancel.setOnAction(e->{
//			MainView();
			CancelProc(e);
		});
		
	}
	public void LoginProc(ActionEvent e) throws IOException {
		String id = textid.getText();
		String pw = textpw.getText();
		
		if (id.equals("")) {
			ErrorMsg("Error", "로그인에 실패했습니다", "ID를 입력하지 않았습니다.");
			textid.requestFocus();
		}
		else if (pw.equals("")) {
			ErrorMsg("Error", "로그인에 실패했습니다", "비밀번호를 입력하지 않았습니다.");
			textpw.requestFocus();
		}
		else {
			check(id, pw);
			if(success==1) {
				LoginMain();
				commonService.closeWindow(e);
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
				ErrorMsg("Error", "로그인 실패", "존재하지 않는 ID입니다.");
				textid.clear();
				textid.requestFocus();
			}
			else {
				ResultSet rs = stmt.executeQuery(sql2);
				if(!rs.getString(1).equals(pw)) {
					ErrorMsg("Error", "로그인 실패", "비밀번호가 다릅니다.");
					textpw.clear();
					textpw.requestFocus();
				}
				else {
					ErrorMsg("Success", "로그인 성공", "로그인에 성공했습니다.");
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
		commonService.closeWindow(e);
	}
	public void LoginMain() {
		commonService.openWindow("LoginSuccess");
	}
	private void SendData() {
		AbstractController controller = commonService.getController("loginmain.fxml");
		controller.setText(idid, namename, songsong);

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

			String sql2 = "SELECT song FROM member "+	
						"WHERE ids = "+"'"+id+"'";
			ResultSet rs2 = stmt2.executeQuery(sql2);
			songsong = rs2.getString(1);
			
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
}
