package Service;

import Controller.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonService implements ICommonService {
    private final Map<String, String> urlMap = new HashMap<>();
    //키로 버튼 아이디를 대조하, 값으로 fxml파일의 경로를 반환하는 Map
    private Map<String, AbstractController> controllerMap = new HashMap<>();
    //String으로 fxml 이름과 컨트롤러를 저장하는 Map

    public CommonService() {
        //버튼 아이디와 fxml경로를  생성자로 지정
        // HomePage Main Form
        urlMap.put("RootScene", "../FXML/RootScene.fxml");
        urlMap.put("Main", "../FXML/main.fxml");
        urlMap.put("btnhome", "../FXML/main.fxml");
        urlMap.put("btnlogin", "../FXML/login.fxml");
        urlMap.put("btnjoin", "../FXML/join.fxml");
        urlMap.put("btnchart", "../FXML/chart.fxml");
        urlMap.put("btnmagazine", "../FXML/magazine.fxml");
        urlMap.put("btnmv", "../FXML/mv.fxml");

        // login page Form
        urlMap.put("btncancel", "../FXML/main.fxml");
        urlMap.put("LoginSuccess", "../FXML/loginmain.fxml");
        urlMap.put("btnlogout", "../FXML/main.fxml");

        // loginMain page Form
        urlMap.put("loginmain", "../FXML/loginmain.fxml");
        urlMap.put("btnhome1", "../FXML/loginmain.fxml");
        urlMap.put("btnout", "../FXML/main.fxml");
        urlMap.put("btnchart1" ,"../FXML/chart.fxml");
        urlMap.put("btnmagazine1", "../FXML/magazine.fxml");
        urlMap.put("btnmv1", "../FXML/mv.fxml");

        // Help & Version Form
        urlMap.put("Help_Btn", "../FXML/Help.fxml");
        urlMap.put("Version_Btn", "../FXML/Version.fxml");
        urlMap.put("DirectQuestion_Btn", "../FXML/DirectQuestion.fxml");
        urlMap.put("QnA_Btn", "../FXML/QnA.fxml");

        // Player Form
        urlMap.put("Player","../FXML/Player.fxml");
        urlMap.put("PlayInfo","../FXML/PlayInfo.fxml");
        urlMap.put("Settings", "../FXML/Setting.fxml");
        urlMap.put("About", "../FXML/Abouts.fxml");

        // Payment & Coupon Form
        urlMap.put("btnpay", "../FXML/pay.fxml");
        urlMap.put("BtnCoupon", "../FXML/coupon.fxml");
    }

    private Scene getScene(String btnId) {

        String url = urlMap.get(btnId);//버튼 아이디를 찾아서 값으로 경로 받음
        System.out.println(url); //test code 제대로 된 값을 받는지 콘솔창으로 확인
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent root = null;
        try {
            root = loader.load();
            AbstractController controller = loader.getController();
            //추상 클래스 AbstractController에도 컨트롤러의 root를 전달
            //공유하기 위함?
            controller.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fxmlName = url.substring(8);
        //여러 번 실행되면 중복되기 때문에 사용할 맵 크기를 잘라줌
        controllerMap.put(fxmlName, loader.getController());
        //컨트롤러 맵에 fxml이름과 컨트롤러를 저장
//        System.out.println(fxmlName); // test code

        return new Scene(root);
        //새로운 씬을 생성해서 호출한 곳에 반환
    }

    @Override
    public void openWindow(String btnId) {
        //새로운 윈도우를 생성
        Scene scene = getScene(btnId);
        //버튼 아이디를 인자 전달 받아서 Map을 통해 경로를 getScene에 전달
        Stage s = new Stage();
        //새로운 윈도우 창을 띄우기 위한 스테이지 생성

        s.setTitle("MusicWebPlayer");
        s.setScene(scene);
        s.show();
    }

    @Override
    public void changeWindow(ActionEvent event, String btnId) {
        //이벤트 처리를 위한 이벤트와 버튼 아이디 수신
        Scene scene = getScene(btnId);
//        getScene(창 띄우는 메소드)에 버튼아이디 전달
        Parent root = (Parent)event.getSource();
        //전달받은 이벤트의 출처를 전달 getSource
        BorderPane border = (BorderPane)root.getScene().getRoot();
        //root의 씬과 root를 전달받아서 BorderPane에 넣음

        border.setCenter(scene.getRoot());
        //보더팬 가운데 정렬 지정
    }

    // 0508 add to musicPlayer
    @Override
    public void changeWindow(Parent root, String btnId, Pos location) {
        switch (location) {
            case TOP_LEFT:
                ((BorderPane)root).setLeft(getScene(btnId).getRoot());
                //Parent root를 BorderPane으로 캐스팅하고 btnId 씬을 얻어서 왼쪽으로 정렬
                break;
            case CENTER:
                ((BorderPane)root).setCenter(getScene(btnId).getRoot());
                break;
            case BOTTOM_CENTER:
                ((BorderPane)root).setBottom(getScene(btnId).getRoot());
                break;
        }
    }

    @Override
    public void closeWindow(ActionEvent event) {
        //윈도우를 닫는 메소드
        Parent root = (Parent)event.getSource();
        //이벤트의 출처를 받아서
        Stage stage = (Stage) root.getScene().getWindow();
        //스테이지에 그 정보를 받아서 창 닫음
        stage.close();
    }

    // 0511 수정
    @Override
    public AbstractController getController(String fxmlName) {
        if(controllerMap.get(fxmlName) != null)
            return controllerMap.get(fxmlName);
        return makeController(fxmlName.replace(".fxml", ""));
    }

    // 0511 추가
    private AbstractController makeController(String originFxmlName) {
        // makeController 메소드 사용시 찾아낼 url을 잘 확인하고 사용할 것.
        String url = urlMap.get(originFxmlName);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractController controller = loader.getController();
        controller.setRoot(root);
        return controller;
    }
}
