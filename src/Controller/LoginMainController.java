package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginMainController implements Initializable {
	@FXML BorderPane brdPane2;
	@FXML TextField textsearch1;
	@FXML Label lblinfo;
	@FXML Label lblinfo2;
	@FXML Label lblinfo3;
	@FXML Button btnlogout;
	@FXML Button btnout;
	@FXML Button btnpay;
	@FXML Button btnlibrary;
	@FXML Button btnchart1;
	@FXML Button btnmagazine1;
	@FXML Button btnmv1;
	@FXML Button btnhome1;
	@FXML Button btninfo;
	
	public static String id, name, song;
	public int ok=0;
	
	final static String DRIVER = "org.sqlite.JDBC";
	final static String DB = "jdbc:sqlite:C:/Users/user/Desktop/login.db";
	Connection conn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnlogout.setOnAction(e->{
			ErrorMsg("Success", "·Î±×¾Æ¿ô ¼º°ø", "·Î±×¾Æ¿ô µÇ¾ú½À´Ï´Ù.");
			logoutProc(e);
			try {
				logoutsuccess(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		btnout.setOnAction(e->{
			Delete("È¸¿ø Å»Åð", "È¸¿ø Å»Åð ÁøÇàÁß...", "Á¤¸»·Î Å»ÅðÇÏ½Ã°Ú½À´Ï±î?");
			if(ok==1) {
				logoutProc(e);
				try {
					logoutsuccess(e);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnchart1.setOnAction(e->{
			changeWindow1(e);
		});
		btnmagazine1.setOnAction(e->{
			changeWindow2(e);
		});
		btnmv1.setOnAction(e->{
			changeWindow3(e);
		});
		btninfo.setOnAction(e->{
			setLabel(e);
		});
		btnhome1.setOnAction(e->{
			logoutProc(e);
			try {
				HomeProc(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	public void Delete(String title, String headerStr, String ContentTxt) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerStr);
		alert.setContentText(ContentTxt);
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK) {
			ErrorMsg("Success", "È¸¿ø Å»Åð ¼º°ø", "Á¤»óÀûÀ¸·Î Å»ÅðµÇ¾ú½À´Ï´Ù.");
			DeleteProc();
		}

	}
	private void DeleteProc() {
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(DB);
			String sql = "DELETE FROM member "+
							"WHERE ids = "+"'"+id+"'";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			ok=1;
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	private void logoutProc(ActionEvent e) {
		Parent root = (Parent)e.getSource();
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
		
	}
	private void logoutsuccess(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		Stage s = new Stage();
			
		s.setScene(new Scene(root));
		s.show();
		
	}
	public void ErrorMsg(String title, String headerStr, String ContentTxt) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerStr);
		alert.setContentText(ContentTxt);
		alert.showAndWait();
	}
	public void changeWindow1(ActionEvent event) {
	    Parent root = (Parent)event.getSource();
	    Parent rootPane = root.getScene().getRoot();
	    BorderPane brdPane = (BorderPane)rootPane.lookup("#brdPane2");
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("chart.fxml"));
	    Parent form = null;
	    try {
	        form = loader.load();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    brdPane.setCenter(form);
	   	}
	public void changeWindow2(ActionEvent event) {
	    Parent root = (Parent)event.getSource();
	    Parent rootPane = root.getScene().getRoot();
	    BorderPane brdPane = (BorderPane)rootPane.lookup("#brdPane2");
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("magazine.fxml"));
	    Parent form = null;
	    try {
	        form = loader.load();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    brdPane.setCenter(form);
	    }
	public void changeWindow3(ActionEvent event) {
	    Parent root = (Parent)event.getSource();
	    Parent rootPane = root.getScene().getRoot();
	    BorderPane brdPane = (BorderPane)rootPane.lookup("#brdPane2");
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("mv.fxml"));
	    Parent form = null;
	    try {
	        form = loader.load();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    brdPane.setCenter(form);
	    }
	public void HomeProc(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("loginmain.fxml"));
		Stage s = new Stage();
			
		s.setScene(new Scene(root));
		s.show();
		
	}
	public LoginMainController() {
	}
	public LoginMainController(String idid, String namename, String songsong) {
		id = idid;
		name = namename;
		song = songsong;
	}
	public void setLabel(ActionEvent e) {
		lblinfo.setText("ÀÌ¸§ = "+name);
		lblinfo2.setText("ID = "+id);
		lblinfo3.setText("ÃëÇâ = "+song);
	}
	
}
