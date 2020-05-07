package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
	@FXML private Button Version_Btn;
	@FXML private Button Help_Btn;
	
	@FXML private ComboBox<String> cmbsong;

	ICommonService commonService;
	IMenuBarService menuBarService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 창 띄우기, 변환하기 위한 클래스
		commonService = new CommonService();
		menuBarService = new MenuBarService();

		btnlogin.setOnAction(e->{
			try {
				loginProc();
				loginSuccess(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		btnjoin.setOnAction(e->{
			try {
				joinProc(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		btnchart.setOnAction(e->{
			changeWindow1(e);
		});
		btnmagazine.setOnAction(e->{
			changeWindow2(e);
		});
		btnmv.setOnAction(e->{
			changeWindow3(e);
		});
		btnhome.setOnAction(e->{
			loginSuccess(e);
			try {
				HomeProc(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

	}
	public void loginProc() throws IOException {
		commonService.openWindow(btnlogin.getId());
	}
	public void HomeProc(ActionEvent e) throws IOException {
		commonService.openWindow(btnhome.getId());
	}
	public void joinProc(ActionEvent e) throws IOException {
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

	}

	@FXML
	void eventProc(ActionEvent event) {
		menuBarService.eventProc(event);
	}

}
