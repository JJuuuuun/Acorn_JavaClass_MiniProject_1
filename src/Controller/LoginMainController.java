package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginMainController extends AbstractController implements Initializable {
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

	private String rank;
	public static String id, name, song, abc;
	public int ok=0;
	 
	final static String DRIVER = "org.sqlite.JDBC";
	final static String DB = "jdbc:sqlite:src/resources/login.db";
	Connection conn;
	Parent root;
	ICommonService commonService;
	IMenuBarService menuBarService;
	private boolean isRanked=false;
	//0512 RootScnene Controller
	AbstractController rootController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		commonService = new CommonService();
		menuBarService = new MenuBarService();
		
		/*lblinfo3.textProperty().addListener(l->{
			if(rank != null)
				btnpay.setDisable(true);
		});*/

		btnlogout.setOnAction(e->{
			ErrorMsg("Success", "Logout Success", "Successfully Logout.");
			//0512 add
			commonService.changeWindow(rootController.getRoot(), "Main", Pos.TOP_LEFT);
			Controller mainController = (Controller) commonService.getController("main.fxml");
			mainController.setRootController(rootController);
			commonService.changeWindow(rootController.getRoot(), "RootScene", Pos.CENTER);
		});
		btnout.setOnAction(e->{
			Delete("Delete Membership", "Membership Deleting...", "Really Delete your Membership?");
			if(ok==1) {
				//0512 add
				commonService.changeWindow(rootController.getRoot(), "Main", Pos.TOP_LEFT);
				Controller mainController = (Controller) commonService.getController("main.fxml");
				mainController.setRootController(rootController);
			}
		});
		btnchart1.setOnAction(this::changeWindow1);
		btnmagazine1.setOnAction(this::changeWindow2);
		btnmv1.setOnAction(this::changeWindow3);

		btnhome1.setOnAction(e->{
			commonService.changeWindow(rootController.getRoot(), "RootScene", Pos.CENTER);
		});

		btnpay.setOnAction(e ->{
			Button btn = (Button)e.getSource();
			commonService.openWindow(btn.getId());
			PaymentController pc = (PaymentController)commonService.getController("pay.fxml");
			pc.setId(id);
			pc.setlmcRoot(root);
		});
	}
	public void Delete(String title, String headerStr, String ContentTxt) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerStr);
		alert.setContentText(ContentTxt);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			ErrorMsg("Success", "Delete Success", "Successfully Deleted");
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
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void logoutProc(ActionEvent e) {
		commonService.closeWindow(e);
	}

	private void logoutsuccess()  {
		commonService.openWindow(btnout.getId());
	}
	public void ErrorMsg(String title, String headerStr, String ContentTxt) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerStr);
		alert.setContentText(ContentTxt);
		alert.showAndWait();
	}

	public void changeWindow1(ActionEvent event) {
		commonService.changeWindow(event, btnchart1.getId());
	}

	public void changeWindow2(ActionEvent event) {
		commonService.changeWindow(event, btnmagazine1.getId());
	}

	public void changeWindow3(ActionEvent event) {
	    commonService.changeWindow(event, btnmv1.getId());
	}

	public void HomeProc()  {
		commonService.openWindow(btnhome1.getId());
	}

	@Override
	public void setText(String name, String id, String rank) {
		lblinfo.setText("NAME = "+name);
		lblinfo2.setText("ID = "+id);
		this.id=id;
//		System.out.println(rank);
		if(rank!=null) {
			lblinfo3.setText("Rank = "+rank);
			btnpay.setDisable(true);
		}
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}

	public void eventProc(ActionEvent event) {
		menuBarService.eventProc(event);
		menuBarService.isCurrentUser(root);
	}

	//0512 add to control RootScene
	public void setRootController(AbstractController controller) {
		this.rootController = controller;
	}
}
