package Controller;

import javafx.scene.Parent;

public abstract class AbstractController {
    public abstract void setRoot(Parent root);

    // 0508 add
    public void setText(String name, String id, String song) {}

    // add to musicPlayer
    public Parent getRoot() {
        return null;
        //오버라이딩을 위한 선언
    }
}
