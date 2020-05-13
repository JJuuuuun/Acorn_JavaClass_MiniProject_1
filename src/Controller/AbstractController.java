package Controller;

import javafx.scene.Parent;

public abstract class AbstractController {
    // 0508 add
    public void setText(String name, String id, String song) {
    }

    // add to musicPlayer
    public Parent getRoot() {
        return null;
    }

    public abstract void setRoot(Parent root);
}
