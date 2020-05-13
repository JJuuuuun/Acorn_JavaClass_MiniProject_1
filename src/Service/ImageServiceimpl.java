package Service;

import javafx.scene.control.ButtonBase;

public class ImageServiceimpl implements ImageService {
    public ButtonBase btnImage(ButtonBase btn, String Path, int width, int height) {
        btn.setStyle("-fx-background-image: url('" + Path + "');" +
                "-fx-background-size: " + width + "px " + height + "px");
        return btn;
    }
}
