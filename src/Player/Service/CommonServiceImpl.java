package Player.Service;

import java.io.IOException;


import Player.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//창 열고 닫기

public class CommonServiceImpl implements ICommonService{

	@Override
	public void showWindow(Stage s, String formPath) {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(formPath));
		Parent root = null;
		try {
			root = loader.load();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Controller ctrler = loader.getController();
		ctrler.setRoot(root);
		s.setTitle("Test");
		s.setScene(new Scene(root));
		s.show();
		
		
	}
	

}
