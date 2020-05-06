package Player;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class btnImg {
	public ImageView setimg(String Path) {
		Image img = new Image(getClass().getResourceAsStream(Path));
		//이미지 주소 받아서 처리 
		
		return new ImageView(img);//이미지 뷰 리턴
	}
	public ImageView Playchange(Button Play) {
		
		
		return null;
	}

}
