package Player.Service;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public interface ImageService {
    Button btnImage(Button btn, String Path, int width, int height);

    ToggleButton btnImage(ToggleButton btn, String Path, int width, int height);
}
