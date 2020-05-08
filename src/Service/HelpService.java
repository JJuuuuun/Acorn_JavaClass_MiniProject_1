package Service;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class HelpService implements IHelpService {
    ICommonService comn;

    public HelpService() {
        comn = new CommonService();
    }


    @Override
    public void eventProc(ActionEvent event) {
        String btnId = eventCheck(event);
        comn.changeWindow(event, btnId);
    }

    private String eventCheck(ActionEvent event) {
        Button btn = (Button)event.getSource();
        return btn.getId();
    }
}
