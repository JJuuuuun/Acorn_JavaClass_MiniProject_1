package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Service.CommonService;
import Service.ICommonService;
import Service.IMenuBarService;
import Service.MenuBarService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controller extends AbstractController implements Initializable {
	@FXML private BorderPane brdPane;
	@FXML private Button btnhome;
	@FXML private TextField textsearch;
	@FXML private Button btnlogin;
	@FXML private Button btnjoin;
	@FXML private Button btnchart;
	@FXML private Button btnmagazine;
	@FXML private Button btnmv;
	
	@FXML private ComboBox<String> cmbsong;

	Parent root;
	ICommonService commonService;
	IMenuBarService menuBarService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		commonService = new CommonService();
		menuBarService = new MenuBarService();

		btnlogin.setOnAction(e->{
			loginProc();
		});

		btnjoin.setOnAction(this::joinProc);

		btnchart.setOnAction(this::changeWindow1);

		btnmagazine.setOnAction(this::changeWindow2);

		btnmv.setOnAction(this::changeWindow3);

		btnhome.setOnAction(e->{
			commonService.changeWindow(root.getScene().getRoot(), "RootScene", Pos.CENTER);
		});

	}
	public void loginProc() {
		commonService.changeWindow(root.getScene().getRoot(), "btnlogin", Pos.CENTER);
	}

	public void HomeProc() {
		commonService.openWindow(btnhome.getId());
	}

	public void joinProc(ActionEvent e) {
		commonService.openWindow(btnjoin.getId());
	}

	private void loginSuccess(ActionEvent e) {
		commonService.closeWindow(e);
	}

    public void changeWindow1(ActionEvent event) {
      commonService.changeWindow(event, btnchart.getId());
    }

    public void changeWindow2(ActionEvent event) {
		commonService.changeWindow(event, btnmagazine.getId());
    }

    public void changeWindow3(ActionEvent event) {
		commonService.changeWindow(event, btnmv.getId());
	}


	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}

	public void eventProc(ActionEvent event) {
		menuBarService.eventProc(event);
	}

	//0508 add to musicPlayer
	@Override
	public Parent getRoot() {
		return root;
	}
}
