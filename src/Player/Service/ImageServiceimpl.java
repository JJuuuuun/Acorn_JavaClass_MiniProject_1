package Player.Service;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageServiceimpl implements ImageService {
    public Button btnImage(Button btn, String Path, int width, int height) {
        Image img = new Image(getClass().getResourceAsStream(Path), width, height, false, false);
        btn.setGraphic(new ImageView(img));
        return btn;
    }

    @Override
    public ToggleButton btnImage(ToggleButton btn, String Path, int width, int height) {
        Image img = new Image(getClass().getResourceAsStream(Path), width, height, false, false);
        btn.setGraphic(new ImageView(img));
        return btn;
    }

}
