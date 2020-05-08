package Service;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class ImageServiceimpl implements ImageService {
    public Button btnImage(Button btn, String Path, int width, int height) {
        btn.setStyle("-fx-background-image: url('" + Path + "');" +
                "-fx-background-size: " + width + "px " + height + "px");
        return btn;
    }

    @Override
    public ToggleButton btnImage(ToggleButton btn, String Path, int width, int height) {
        btn.setStyle("-fx-background-image: url('" + Path + "');" +
                "-fx-background-size: " + width + "px " + height + "px");
        return btn;
    }

}
