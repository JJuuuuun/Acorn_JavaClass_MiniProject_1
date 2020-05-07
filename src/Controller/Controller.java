package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

public class Controller implements Initializable {
	@FXML private BorderPane brdPane;
	@FXML private Button btnhome;
	@FXML private TextField textsearch;
	@FXML private Button btnlogin;
	@FXML private Button btnjoin;
	@FXML private Button btnchart;
	@FXML private Button btnmagazine;
	@FXML private Button btnmv;
	
	@FXML private ComboBox<String> cmbsong;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnlogin.setOnAction(e->{
			try {
				loginProc(e);
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
	public void loginProc(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		Stage s = new Stage();
			
		s.setScene(new Scene(root));
		s.show();
		
	}
	public void HomeProc(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		Stage s = new Stage();
			
		s.setScene(new Scene(root));
		s.show();
		
	}
	public void joinProc(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("join.fxml"));
		Stage s = new Stage();
		
		String [] items= {"¹ß¶óµå", "´í½º°î", "¶ô", "ÈüÇÕ", "Æ®·ÎÆ®"};
		cmbsong = (ComboBox<String>) root.lookup("#cmbsong");
		
		if(cmbsong!=null) {
			for(String item : items)
				cmbsong.getItems().add(item);
		}
		s.setScene(new Scene(root));
		s.show();
	
	}
	private void loginSuccess(ActionEvent e) {
		Parent root = (Parent)e.getSource();
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
		
	}
    public void changeWindow1(ActionEvent event) {
        Parent root = (Parent)event.getSource();
        Parent rootPane = root.getScene().getRoot();
        BorderPane brdPane = (BorderPane)rootPane.lookup("#brdPane");
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
        BorderPane brdPane = (BorderPane)rootPane.lookup("#brdPane");
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
        BorderPane brdPane = (BorderPane)rootPane.lookup("#brdPane");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mv.fxml"));
        Parent form = null;
        try {
            form = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

      brdPane.setCenter(form);
    }
}
