package Controller;

import java.net.URL;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//			String inputnum = couponnum.getText();
	
			
	}
	
	public void btnTest() {
		String inputnum = couponnum.getText();
		if(inputnum.contentEquals("1")) {
			 Alert alert = new Alert(AlertType.WARNING);
			 alert.setTitle("로그인");
			 alert.setHeaderText("쿠폰등록에 성공하였습니다");
			 alert.setContentText("확인버튼을 눌러주세요");
			 alert.show();
////			 (controller.lblcoupon).setText("2313");
			 
			 
		 } 
		 else {
			 Alert alert = new Alert(AlertType.WARNING);
			 alert.setTitle("로그인");
			 alert.setHeaderText("쿠폰등록에 실패하였습니다");
			 alert.setContentText("다시 입력해주세요");

			 alert.showAndWait(); 
		 }
	}


	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}
}
