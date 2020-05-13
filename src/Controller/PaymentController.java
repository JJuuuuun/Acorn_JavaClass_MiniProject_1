package Controller;

import java.io.IOException;
import Service.CommonService;
import Service.ICommonService;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class PaymentController extends AbstractController implements Initializable {

	@FXML Button BtnExit;
	@FXML Button Btnpromise;
	@FXML Button BtnCoupon;
	@FXML Label OGmoney;
	@FXML Label lblcoupon;
	@FXML Label Resultmoney;
	@FXML ToggleButton Btn6m;
	@FXML ToggleGroup month;
	@FXML ToggleButton Btn3m;
	@FXML ToggleButton Btn12m;
	@FXML ToggleButton Btn1m;
	@FXML TextField couponnum;

	ICommonService commonService;
	Parent root, lmc;
	public String rank;
	public static String id;
	public int abc, def;
	final static String DRIVER = "org.sqlite.JDBC";
	final static String DB = "jdbc:sqlite:src/resources/login.db";
	Connection conn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		commonService = new CommonService();

		int a = 8900;
		int b = 10900;
		int c = 15900;
		int d = 21900;
		Btn1m.setOnAction(event -> {OGmoney.setText(a+"");	rank = "Bronze";});
		Btn3m.setOnAction(event -> {OGmoney.setText(b+"");	rank = "Silver";});
		Btn6m.setOnAction(event -> {OGmoney.setText(c +"");	rank = "Gold";});
		Btn12m.setOnAction(event -> {OGmoney.setText(d +""); rank = "Platinum";});
		BtnCoupon.setOnAction(e->{
			commonService.openWindow("BtnCoupon");
			CouponController cc = (CouponController)commonService.getController("coupon.fxml");
			cc.setId(id);
			cc.setPCroot(root);
		});


		Btnpromise.setOnAction(action -> {
			TextArea textarea = new TextArea();
			System.out.println(textarea.getText());
			textarea.setText("1. 총칙\r\n" +
					"제1조 (목적)\r\n" +
					"이 약관은 주식회사 카카오(이하 ‘회사’라 합니다)가 제공하는 유무선 인터넷 음악서비스(웹, 모바일 웹•앱 서비스를 포함합니다.)인 Melon(멜론) 및 멜론 관련 제반 서비스의 유료서비스를 이용함에 있어 회사와 회원간 제반 법률관계 및 기타 관련 사항을 규정함을 목적으로 합니다.\r\n" +
					"제2조 (용어의 정의)\r\n" +
					"① 이 약관에서 사용되는 용어의 정의는 다음과 같습니다.\r\n" +
					"1.‘회원가입’이라 함은 멜론 서비스 또는 유료서비스를 이용하고자 하는 자(‘고객’이라고도 합니다)가 멜론 이용약관에 동의하고 회사와 그 이용계약을 체결하는 것을 의미합니다. 회원가입을 하는 경우 고객은 멜론서비스 회원이 됩니다.\r\n" +
					"2.‘회원탈퇴’라 함은 회원이 멜론 이용약관 또는 멜론유료서비스약관의 체결로 발생한 제반 권리와 의무 등의 법률관계를 자의로 영구히 종료하거나, 포기하는 것을 의미합니다.\r\n" +
					"3.‘유료서비스’라 함은 회원이 회사에 일정 금액을 지불해야만 이용할 수 있는 회사의 서비스 또는 이용권(상품)을 의미하거나, 회사 또는 제3자와의 거래 내지 약정 조건을 수락하는 대가로 이용하게 되는 회사의 서비스 또는 이용권(상품)을 의미합니다. 유료서비스의 세부내용은 각각의 서비스 또는 이용권(상품) 구매 페이지 및 해당 서비스 또는 이용권(상품)의 결제 페이지에 상세히 설명되어 있습니다. 단순히 멜론캐시를 충전하거나 회사 또는 제3자로부터 적립 받는 행위, 사이트에 연결(링크)된 유료 콘텐츠 제공업체 및 회사와 계약을 통하여 입점한 제휴서비스는 회사의 유료서비스 이용행위로 보지 않으며, 본 약관을 적용하지 아니합니다.\r\n" +
					"4.‘유료회원’이라 함은 별도의 금액을 지불하여 유료서비스를 구매한 회원 및 회사 또는 제3자가 정하는 이벤트, 마케팅에 참여하는 등의 방법으로 회사의 유료서비스를 이용하는 회원을 말합니다.\r\n" +
					"5.‘무료회원’이라 함은 유료회원이 아닌 회원으로 회원가입 이후에 기본적으로 모든 회원에게 부여되는 자격을 가진 회원을 의미합니다.\r\n" +
					"6.‘멜론캐시’라 함은 회원이 직접 이 약관 제5조(결제수단 등)의 각종 수단에 의한 결제를 통하여 충전, 생성하거나, 회사 또는 제3자가 시행하는 이벤트, 프로모션, 기타 거래에 동의, 약정, 조건 이행 등의 대가, 환불수단으로 지급 받는 사이버머니로서, 멜론의 유료서비스를 이용하기 위해 현금처럼 지불할 수 있는 유무선인터넷상의 지급 수단을 의미합니다.\r\n" +
					"7.‘충전’이라 함은 회원이 서비스의 구매 또는 이용을 위하여 멜론캐시를 회사가 제공하는 각종 지불수단을 통하여 현금으로 지급 후 멜론캐시를 획득하는 행위를 의미하며, 현금 1원당 멜론캐시 1원의 비율로 충전됩니다.\r\n" +
					"8.‘결제’라 함은 회원이 특정 유료서비스를 이용하기 위하여 각종 지불수단을 통하여 회사가 정한 일정 금액을 회사에 지불하는 것을 의미합니다.\r\n" +
					"9.‘구매’라 함은 회사가 유료서비스에 대한 이용 승낙 및 유료서비스 제공이 가능할 수 있도록 회원이 이용하고자 하는 유료서비스를 선택하여 해당 유료서비스의 가액과 동일한 멜론캐시의 차감을 청구하거나 지불수단을 통한 결제로써 그 대가를 지급하는 행위를 의미합니다.\r\n" +
					"② 이 약관에서 사용하는 용어의 정의는 제1항에서 정하는 것을 제외하고는 관계 법령 및 서비스별 안내에서 정하는 바에 의합니다.\r\n" +
					"제3조 (약관의 효력/변경 등)\r\n" +
					"① 이 약관은 유료서비스 또는 멜론캐시를 이용하기를 희망하는 회원이 동의함으로써 효력이 발생하며, 회원이 이 약관에 대하여 “동의하기” 버튼을 클릭한 경우, 이 약관의 내용을 충분히 이해하고 그 적용에 동의한 것으로 봅니다.\r\n" +
					"② 회사는 관련 법령을 위배하지 않는 범위에서 이 약관을 개정할 수 있습니다. 회사가 약관을 개정하는 경우에는 적용일자 및 변경사유를 명시하여 그 적용일자 15일 전부터 홈페이지에 공지합니다. 다만, 회원에게 불리한 약관의 변경의 경우에는 그 개정 내용을 고객이 알기 쉽게 표시하여 그 적용일자 30일 전부터 공지하며, 이메일 주소, 문자메시지 등으로 회원에게 개별 통지합니다. 회원의 연락처 미기재, 변경 등으로 인하여 개별 통지가 어려운 경우, 회원이 등록한 연락처로 공지를 하였음에도 2회 이상 반송된 경우 이 약관에 의한 공지를 함으로써 개별 통지한 것으로 간주합니다.\r\n" +
					"③ 회사가 제3항의 공지 또는 통보를 하면서 개정 약관의 적용/시행일까지 회원이 거부 의사를 표시하지 아니할 경우 약관의 개정에 동의한 것으로 간주한다는 내용을 고지하였으나, 회원이 명시적으로 약관 변경에 대한 거부의사를 표시하지 아니하면, 회사는 회원이 적용/시행일자 부로 변경 약관에 동의한 것으로 간주합니다. 개정/변경 약관에 대하여 거부의사를 표시한 회원은 계약의 해지 또는 회원 탈퇴를 선택할 수 있습니다.\r\n" +
					"④ 회사는 이 약관을 회원이 그 전부를 인쇄할 수 있고 확인할 수 있도록 필요한 기술적 조치를 취합니다.\r\n" +
					"⑤ 이 약관은 회원이 이 약관에 동의한 날로부터 회원 탈퇴 시까지 적용하는 것을 원칙으로 합니다. 단, 이 약관의 일부 조항은 회원이 탈퇴 후에도 유효하게 적용될 수 있습니다.\r\n" +
					"⑥ 이 유료서비스약관의 제반 규정은, 멜론 이용약관 또는 카카오 통합서비스약관(계정통합을 진행한 경우)의 관련 규정에 우선 적용되며, 이용약관과 이 약관의 정의, 내용 등이 서로 상충되는 경우 이 약관의 관련 규정을 적용합니다. 이 약관에 명시되지 아니한 사항에 대하여는 콘텐츠산업진흥법, 전자상거래 등에서의 소비자 보호에 관한 법률, 약관의 규제에 관한 법률 등 관련 법령에 따릅니다.");

			textarea.setWrapText(true);
			textarea.setMinSize(500, 500);

			VBox vbox = new VBox(textarea);
			Stage stage = new Stage();
			Scene scene = new Scene(vbox, 500,500);
			stage.setTitle("약관");
			stage.setScene(scene);
			stage.show();
			// TODO Auto-generated catch block
		});
		BtnExit.setOnAction(e->{
			try {
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(DB);
				String sql = "UPDATE member "+
						"SET rank = "+"'"+rank+"'"+
						"WHERE ids = "+"'"+id+"';";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();

				pstmt.close();
				conn.close();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			Label lbl = (Label) lmc.lookup("#lblinfo3");
			lbl.setText("Rank = "+rank);
			Button btn = (Button) lmc.lookup("#btnpay");
			btn.setDisable(true);


			Parent root = (Parent)e.getSource();
			Stage stage = (Stage) root.getScene().getWindow();
			stage.close();
		});



		OGmoney.textProperty().addListener(l->{
			abc = Integer.valueOf(OGmoney.getText());
			Resultmoney.setText(abc-def+"");
		});
		lblcoupon.textProperty().addListener(l->{
			def = Integer.valueOf(lblcoupon.getText());
			if(abc-def>=0)
				Resultmoney.setText(abc-def+"");
		});

	}

	public void setlbl(String str) {
		lblcoupon.setText(str);
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}
	public void setId(String id) {
		this.id=id;
	}
	public void setlmcRoot(Parent root) {
		this.lmc = root;
	}
}

	
	


