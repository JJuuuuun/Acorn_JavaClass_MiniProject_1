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
					textarea.setText("1. 珥앹튃\r\n" +
							"�젣1議� (紐⑹쟻)\r\n" +
							"�씠 �빟愿��� 二쇱떇�쉶�궗 移댁뭅�삤(�씠�븯 �섑쉶�궗�숇씪 �빀�땲�떎)媛� �젣怨듯븯�뒗 �쑀臾댁꽑 �씤�꽣�꽬 �쓬�븙�꽌鍮꾩뒪(�쎒, 紐⑤컮�씪 �쎒���빋 �꽌鍮꾩뒪瑜� �룷�븿�빀�땲�떎.)�씤 Melon(硫쒕줎) 諛� 硫쒕줎 愿��젴 �젣諛� �꽌鍮꾩뒪�쓽 �쑀猷뚯꽌鍮꾩뒪瑜� �씠�슜�븿�뿉 �엳�뼱 �쉶�궗�� �쉶�썝媛� �젣諛� 踰뺣쪧愿�怨� 諛� 湲고� 愿��젴 �궗�빆�쓣 洹쒖젙�븿�쓣 紐⑹쟻�쑝濡� �빀�땲�떎.\r\n" +
							"�젣2議� (�슜�뼱�쓽 �젙�쓽)\r\n" +
							"�몺 �씠 �빟愿��뿉�꽌 �궗�슜�릺�뒗 �슜�뼱�쓽 �젙�쓽�뒗 �떎�쓬怨� 媛숈뒿�땲�떎.\r\n" +
							"1.�섑쉶�썝媛��엯�숈씠�씪 �븿�� 硫쒕줎 �꽌鍮꾩뒪 �삉�뒗 �쑀猷뚯꽌鍮꾩뒪瑜� �씠�슜�븯怨좎옄 �븯�뒗 �옄(�섍퀬媛앪�숈씠�씪怨좊룄 �빀�땲�떎)媛� 硫쒕줎 �씠�슜�빟愿��뿉 �룞�쓽�븯怨� �쉶�궗�� 洹� �씠�슜怨꾩빟�쓣 泥닿껐�븯�뒗 寃껋쓣 �쓽誘명빀�땲�떎. �쉶�썝媛��엯�쓣 �븯�뒗 寃쎌슦 怨좉컼�� 硫쒕줎�꽌鍮꾩뒪 �쉶�썝�씠 �맗�땲�떎.\r\n" +
							"2.�섑쉶�썝�깉�눜�숇씪 �븿�� �쉶�썝�씠 硫쒕줎 �씠�슜�빟愿� �삉�뒗 硫쒕줎�쑀猷뚯꽌鍮꾩뒪�빟愿��쓽 泥닿껐濡� 諛쒖깮�븳 �젣諛� 沅뚮━�� �쓽臾� �벑�쓽 踰뺣쪧愿�怨꾨�� �옄�쓽濡� �쁺援ы엳 醫낅즺�븯嫄곕굹, �룷湲고븯�뒗 寃껋쓣 �쓽誘명빀�땲�떎.\r\n" +
							"3.�섏쑀猷뚯꽌鍮꾩뒪�숇씪 �븿�� �쉶�썝�씠 �쉶�궗�뿉 �씪�젙 湲덉븸�쓣 吏�遺덊빐�빞留� �씠�슜�븷 �닔 �엳�뒗 �쉶�궗�쓽 �꽌鍮꾩뒪 �삉�뒗 �씠�슜沅�(�긽�뭹)�쓣 �쓽誘명븯嫄곕굹, �쉶�궗 �삉�뒗 �젣3�옄���쓽 嫄곕옒 �궡吏� �빟�젙 議곌굔�쓣 �닔�씫�븯�뒗 ��媛�濡� �씠�슜�븯寃� �릺�뒗 �쉶�궗�쓽 �꽌鍮꾩뒪 �삉�뒗 �씠�슜沅�(�긽�뭹)�쓣 �쓽誘명빀�땲�떎. �쑀猷뚯꽌鍮꾩뒪�쓽 �꽭遺��궡�슜�� 媛곴컖�쓽 �꽌鍮꾩뒪 �삉�뒗 �씠�슜沅�(�긽�뭹) 援щℓ �럹�씠吏� 諛� �빐�떦 �꽌鍮꾩뒪 �삉�뒗 �씠�슜沅�(�긽�뭹)�쓽 寃곗젣 �럹�씠吏��뿉 �긽�꽭�엳 �꽕紐낅릺�뼱 �엳�뒿�땲�떎. �떒�닚�엳 硫쒕줎罹먯떆瑜� 異⑹쟾�븯嫄곕굹 �쉶�궗 �삉�뒗 �젣3�옄濡쒕��꽣 �쟻由� 諛쏅뒗 �뻾�쐞, �궗�씠�듃�뿉 �뿰寃�(留곹겕)�맂 �쑀猷� 肄섑뀗痢� �젣怨듭뾽泥� 諛� �쉶�궗�� 怨꾩빟�쓣 �넻�븯�뿬 �엯�젏�븳 �젣�쑕�꽌鍮꾩뒪�뒗 �쉶�궗�쓽 �쑀猷뚯꽌鍮꾩뒪 �씠�슜�뻾�쐞濡� 蹂댁� �븡�쑝硫�, 蹂� �빟愿��쓣 �쟻�슜�븯吏� �븘�땲�빀�땲�떎.\r\n" +
							"4.�섏쑀猷뚰쉶�썝�숈씠�씪 �븿�� 蹂꾨룄�쓽 湲덉븸�쓣 吏�遺덊븯�뿬 �쑀猷뚯꽌鍮꾩뒪瑜� 援щℓ�븳 �쉶�썝 諛� �쉶�궗 �삉�뒗 �젣3�옄媛� �젙�븯�뒗 �씠踰ㅽ듃, 留덉��똿�뿉 李몄뿬�븯�뒗 �벑�쓽 諛⑸쾿�쑝濡� �쉶�궗�쓽 �쑀猷뚯꽌鍮꾩뒪瑜� �씠�슜�븯�뒗 �쉶�썝�쓣 留먰빀�땲�떎.\r\n" +
							"5.�섎Т猷뚰쉶�썝�숈씠�씪 �븿�� �쑀猷뚰쉶�썝�씠 �븘�땶 �쉶�썝�쑝濡� �쉶�썝媛��엯 �씠�썑�뿉 湲곕낯�쟻�쑝濡� 紐⑤뱺 �쉶�썝�뿉寃� 遺��뿬�릺�뒗 �옄寃⑹쓣 媛�吏� �쉶�썝�쓣 �쓽誘명빀�땲�떎.\r\n" +
							"6.�섎찞濡좎틦�떆�숇씪 �븿�� �쉶�썝�씠 吏곸젒 �씠 �빟愿� �젣5議�(寃곗젣�닔�떒 �벑)�쓽 媛곸쥌 �닔�떒�뿉 �쓽�븳 寃곗젣瑜� �넻�븯�뿬 異⑹쟾, �깮�꽦�븯嫄곕굹, �쉶�궗 �삉�뒗 �젣3�옄媛� �떆�뻾�븯�뒗 �씠踰ㅽ듃, �봽濡쒕え�뀡, 湲고� 嫄곕옒�뿉 �룞�쓽, �빟�젙, 議곌굔 �씠�뻾 �벑�쓽 ��媛�, �솚遺덉닔�떒�쑝濡� 吏�湲� 諛쏅뒗 �궗�씠踰꾨㉧�땲濡쒖꽌, 硫쒕줎�쓽 �쑀猷뚯꽌鍮꾩뒪瑜� �씠�슜�븯湲� �쐞�빐 �쁽湲덉쿂�읆 吏�遺덊븷 �닔 �엳�뒗 �쑀臾댁꽑�씤�꽣�꽬�긽�쓽 吏�湲� �닔�떒�쓣 �쓽誘명빀�땲�떎.\r\n" +
							"7.�섏땐�쟾�숈씠�씪 �븿�� �쉶�썝�씠 �꽌鍮꾩뒪�쓽 援щℓ �삉�뒗 �씠�슜�쓣 �쐞�븯�뿬 硫쒕줎罹먯떆瑜� �쉶�궗媛� �젣怨듯븯�뒗 媛곸쥌 吏�遺덉닔�떒�쓣 �넻�븯�뿬 �쁽湲덉쑝濡� 吏�湲� �썑 硫쒕줎罹먯떆瑜� �쉷�뱷�븯�뒗 �뻾�쐞瑜� �쓽誘명븯硫�, �쁽湲� 1�썝�떦 硫쒕줎罹먯떆 1�썝�쓽 鍮꾩쑉濡� 異⑹쟾�맗�땲�떎.\r\n" +
							"8.�섍껐�젣�숇씪 �븿�� �쉶�썝�씠 �듅�젙 �쑀猷뚯꽌鍮꾩뒪瑜� �씠�슜�븯湲� �쐞�븯�뿬 媛곸쥌 吏�遺덉닔�떒�쓣 �넻�븯�뿬 �쉶�궗媛� �젙�븳 �씪�젙 湲덉븸�쓣 �쉶�궗�뿉 吏�遺덊븯�뒗 寃껋쓣 �쓽誘명빀�땲�떎.\r\n" +
							"9.�섍뎄留ㅲ�숇씪 �븿�� �쉶�궗媛� �쑀猷뚯꽌鍮꾩뒪�뿉 ���븳 �씠�슜 �듅�굺 諛� �쑀猷뚯꽌鍮꾩뒪 �젣怨듭씠 媛��뒫�븷 �닔 �엳�룄濡� �쉶�썝�씠 �씠�슜�븯怨좎옄 �븯�뒗 �쑀猷뚯꽌鍮꾩뒪瑜� �꽑�깮�븯�뿬 �빐�떦 �쑀猷뚯꽌鍮꾩뒪�쓽 媛��븸怨� �룞�씪�븳 硫쒕줎罹먯떆�쓽 李④컧�쓣 泥�援ы븯嫄곕굹 吏�遺덉닔�떒�쓣 �넻�븳 寃곗젣濡쒖뜥 洹� ��媛�瑜� 吏�湲됲븯�뒗 �뻾�쐞瑜� �쓽誘명빀�땲�떎.\r\n" +
							"�몼 �씠 �빟愿��뿉�꽌 �궗�슜�븯�뒗 �슜�뼱�쓽 �젙�쓽�뒗 �젣1�빆�뿉�꽌 �젙�븯�뒗 寃껋쓣 �젣�쇅�븯怨좊뒗 愿�怨� 踰뺣졊 諛� �꽌鍮꾩뒪蹂� �븞�궡�뿉�꽌 �젙�븯�뒗 諛붿뿉 �쓽�빀�땲�떎.\r\n" +
							"�젣3議� (�빟愿��쓽 �슚�젰/蹂�寃� �벑)\r\n" +
							"�몺 �씠 �빟愿��� �쑀猷뚯꽌鍮꾩뒪 �삉�뒗 硫쒕줎罹먯떆瑜� �씠�슜�븯湲곕�� �씗留앺븯�뒗 �쉶�썝�씠 �룞�쓽�븿�쑝濡쒖뜥 �슚�젰�씠 諛쒖깮�븯硫�, �쉶�썝�씠 �씠 �빟愿��뿉 ���븯�뿬 �쒕룞�쓽�븯湲겸�� 踰꾪듉�쓣 �겢由��븳 寃쎌슦, �씠 �빟愿��쓽 �궡�슜�쓣 異⑸텇�엳 �씠�빐�븯怨� 洹� �쟻�슜�뿉 �룞�쓽�븳 寃껋쑝濡� 遊낅땲�떎.\r\n" +
							"�몼 �쉶�궗�뒗 愿��젴 踰뺣졊�쓣 �쐞諛고븯吏� �븡�뒗 踰붿쐞�뿉�꽌 �씠 �빟愿��쓣 媛쒖젙�븷 �닔 �엳�뒿�땲�떎. �쉶�궗媛� �빟愿��쓣 媛쒖젙�븯�뒗 寃쎌슦�뿉�뒗 �쟻�슜�씪�옄 諛� 蹂�寃쎌궗�쑀瑜� 紐낆떆�븯�뿬 洹� �쟻�슜�씪�옄 15�씪 �쟾遺��꽣 �솃�럹�씠吏��뿉 怨듭��빀�땲�떎. �떎留�, �쉶�썝�뿉寃� 遺덈━�븳 �빟愿��쓽 蹂�寃쎌쓽 寃쎌슦�뿉�뒗 洹� 媛쒖젙 �궡�슜�쓣 怨좉컼�씠 �븣湲� �돺寃� �몴�떆�븯�뿬 洹� �쟻�슜�씪�옄 30�씪 �쟾遺��꽣 怨듭��븯硫�, �씠硫붿씪 二쇱냼, 臾몄옄硫붿떆吏� �벑�쑝濡� �쉶�썝�뿉寃� 媛쒕퀎 �넻吏��빀�땲�떎. �쉶�썝�쓽 �뿰�씫泥� 誘멸린�옱, 蹂�寃� �벑�쑝濡� �씤�븯�뿬 媛쒕퀎 �넻吏�媛� �뼱�젮�슫 寃쎌슦, �쉶�썝�씠 �벑濡앺븳 �뿰�씫泥섎줈 怨듭�瑜� �븯���쓬�뿉�룄 2�쉶 �씠�긽 諛섏넚�맂 寃쎌슦 �씠 �빟愿��뿉 �쓽�븳 怨듭�瑜� �븿�쑝濡쒖뜥 媛쒕퀎 �넻吏��븳 寃껋쑝濡� 媛꾩＜�빀�땲�떎.\r\n" +
							"�몾 �쉶�궗媛� �젣3�빆�쓽 怨듭� �삉�뒗 �넻蹂대�� �븯硫댁꽌 媛쒖젙 �빟愿��쓽 �쟻�슜/�떆�뻾�씪源뚯� �쉶�썝�씠 嫄곕� �쓽�궗瑜� �몴�떆�븯吏� �븘�땲�븷 寃쎌슦 �빟愿��쓽 媛쒖젙�뿉 �룞�쓽�븳 寃껋쑝濡� 媛꾩＜�븳�떎�뒗 �궡�슜�쓣 怨좎��븯���쑝�굹, �쉶�썝�씠 紐낆떆�쟻�쑝濡� �빟愿� 蹂�寃쎌뿉 ���븳 嫄곕��쓽�궗瑜� �몴�떆�븯吏� �븘�땲�븯硫�, �쉶�궗�뒗 �쉶�썝�씠 �쟻�슜/�떆�뻾�씪�옄 遺�濡� 蹂�寃� �빟愿��뿉 �룞�쓽�븳 寃껋쑝濡� 媛꾩＜�빀�땲�떎. 媛쒖젙/蹂�寃� �빟愿��뿉 ���븯�뿬 嫄곕��쓽�궗瑜� �몴�떆�븳 �쉶�썝�� 怨꾩빟�쓽 �빐吏� �삉�뒗 �쉶�썝 �깉�눜瑜� �꽑�깮�븷 �닔 �엳�뒿�땲�떎.\r\n" +
							"�몿 �쉶�궗�뒗 �씠 �빟愿��쓣 �쉶�썝�씠 洹� �쟾遺�瑜� �씤�뇙�븷 �닔 �엳怨� �솗�씤�븷 �닔 �엳�룄濡� �븘�슂�븳 湲곗닠�쟻 議곗튂瑜� 痍⑦빀�땲�떎.\r\n" +
							"�뫀 �씠 �빟愿��� �쉶�썝�씠 �씠 �빟愿��뿉 �룞�쓽�븳 �궇濡쒕��꽣 �쉶�썝 �깉�눜 �떆源뚯� �쟻�슜�븯�뒗 寃껋쓣 �썝移숈쑝濡� �빀�땲�떎. �떒, �씠 �빟愿��쓽 �씪遺� 議고빆�� �쉶�썝�씠 �깉�눜 �썑�뿉�룄 �쑀�슚�븯寃� �쟻�슜�맆 �닔 �엳�뒿�땲�떎.\r\n" +
							"�뫁 �씠 �쑀猷뚯꽌鍮꾩뒪�빟愿��쓽 �젣諛� 洹쒖젙��, 硫쒕줎 �씠�슜�빟愿� �삉�뒗 移댁뭅�삤 �넻�빀�꽌鍮꾩뒪�빟愿�(怨꾩젙�넻�빀�쓣 吏꾪뻾�븳 寃쎌슦)�쓽 愿��젴 洹쒖젙�뿉 �슦�꽑 �쟻�슜�릺硫�, �씠�슜�빟愿�怨� �씠 �빟愿��쓽 �젙�쓽, �궡�슜 �벑�씠 �꽌濡� �긽異⑸릺�뒗 寃쎌슦 �씠 �빟愿��쓽 愿��젴 洹쒖젙�쓣 �쟻�슜�빀�땲�떎. �씠 �빟愿��뿉 紐낆떆�릺吏� �븘�땲�븳 �궗�빆�뿉 ���븯�뿬�뒗 肄섑뀗痢좎궛�뾽吏꾪씎踰�, �쟾�옄�긽嫄곕옒 �벑�뿉�꽌�쓽 �냼鍮꾩옄 蹂댄샇�뿉 愿��븳 踰뺣쪧, �빟愿��쓽 洹쒖젣�뿉 愿��븳 踰뺣쪧 �벑 愿��젴 踰뺣졊�뿉 �뵲由낅땲�떎.");

					textarea.setWrapText(true);
					textarea.setMinSize(500, 500);

		 VBox vbox = new VBox(textarea);
					Stage stage = new Stage();
			        Scene scene = new Scene(vbox, 500,500);
					stage.setTitle("�빟愿�");
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

	
	


