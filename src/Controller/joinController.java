package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Service.CommonService;
import Service.ICommonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class joinController extends AbstractController implements Initializable {
	@FXML private Label lbljoin;
	@FXML private TextField textname;
	@FXML private TextField textid;
	@FXML private TextField textpw;
	@FXML private TextField textpwok;
	@FXML private ComboBox<String> cmbsong;
	final String [] items= {"Trot", "Ballade", "Rock", "Hip-Hop", "Trot"};
	@FXML private Button btncheck;
	@FXML private Button btnjoin;
	@FXML private Button btncancel;
	public int check=0;
	final static String DRIVER = "org.sqlite.JDBC";
	final static String DB = "jdbc:sqlite:src/resources/login.db";
	Connection conn;
	Parent root;
	ICommonService commonService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		commonService = new CommonService();

		if(cmbsong!=null) {
			for(String item : items)
				cmbsong.getItems().add(item);
		}

		textid.setOnKeyPressed(e->{
			btncheck.setDisable(false);
			check=0;
		});

		btncheck.setOnAction(e->{
			String id = textid.getText();
			idcheck(e, id);
		});

		btnjoin.setOnAction(this::joinProc);
		btncancel.setOnAction(this::CancelProc);
		
	}
	public void joinProc(ActionEvent e) {
		String name = textname.getText();
		String id = textid.getText();
		String pw = textpw.getText();
		String pwok = textpwok.getText();
		String song = cmbsong.getValue();
		if (name.equals("")) {
			ErrorMsg("Error", "Register Failed", "You do not input Name");
			textname.requestFocus();
		}
		else if (id.equals("")) {
			ErrorMsg("Error", "Register Failed", "You do not input ID");
			textid.requestFocus();
		}
		else if (check==0) {
			ErrorMsg("Error", "Register Failed", "You do not checked ID");
			btncheck.requestFocus();
		}
		else if (pw.equals("")) {
			ErrorMsg("Error", "Register Failed", "You do not input Password");
			textpw.requestFocus();
		}
		else if (pwok.equals("")) {
			ErrorMsg("Error", "Register Failed", "You do not input Password(Re)");
			textpwok.requestFocus();
		}
		else if (song == null) {
			ErrorMsg("Error", "Register Failed", "You do not input favorite Song");
			cmbsong.requestFocus();
		}
		else if (!pwok.equals(pw)) {
			ErrorMsg("Error", "Register Failed", "You inputed worng Password");
			textpw.clear();
			textpwok.clear();
			textpw.requestFocus();
		}
		else {
			String num = String.valueOf(Math.random()*10000).substring(0, 3);
			InputProc(name, id, pw, song, num);
			ErrorMsg("Success", "Register succeed.","Discount Code : "+num);

			commonService.closeWindow(e);
		}
		
		
	}
	public void ErrorMsg(String title, String headerStr, String ContentTxt) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerStr);
		alert.setContentText(ContentTxt);
		alert.showAndWait();
	}
	public void InputProc(String name, String id, String pw, String song, String num) {
		String sql = "INSERT INTO member "+
			 	 "(name, ids, pw, song, code) "+
			 	 "VALUES (?,?,?,?,?); ";
		
		try {
			Class.forName(DRIVER);
	        conn = DriverManager.getConnection(DB);
	        
	        PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			pstmt.setString(4, song);
			pstmt.setString(5, num);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void idcheck(ActionEvent e, String id) {
		if(id.equals("")) {
			ErrorMsg("Error", "ID check Failed", "You do not input ID");
			textid.requestFocus();
			return;
		}
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(DB);
			Statement stmt = conn.createStatement();
			String sql2 = "SELECT COUNT(*) "+
						"FROM member ";
			ResultSet rsmax = stmt.executeQuery(sql2);
			int max = rsmax.getInt(1);
			
			for(int i=1;i<=max;i++) {
				String sql = "SELECT ids FROM member "+	
							"WHERE ROWID ="+i;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.getString(1).equals(id)) {
					check=0;
					break;
				}
				else check=1;
			}
			if(check==0) {
				ErrorMsg("Error", "ID check Failed", "ID Overlaped");
				textid.clear();
				textid.requestFocus();
			}
			else{
				ErrorMsg("Success", "ID check succeed", "You can use this ID");
				btncheck.setDisable(true);
				textpw.requestFocus();
			}
			conn.close();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}
	public void CancelProc(ActionEvent e) {
		Parent root = (Parent)e.getSource();
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}
}
