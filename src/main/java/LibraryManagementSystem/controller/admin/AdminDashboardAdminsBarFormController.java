package LibraryManagementSystem.controller.admin;

import LibraryManagementSystem.controller.admin.AdminSignInFormController;
import LibraryManagementSystem.controller.admin.ViewAdminPopUpFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import LibraryManagementSystem.projection.AdminProjection;
import LibraryManagementSystem.util.Navigation;

import java.io.IOException;

public class AdminDashboardAdminsBarFormController {

    @FXML
    private Label lblAdminId;

    @FXML
    private Label lblAdminName;

    @FXML
    private Label lblStatus;

    @FXML
    void btnViewAdminOnAction(ActionEvent event) throws IOException {
        ViewAdminPopUpFormController.adminId = Integer.parseInt(lblAdminId.getText());
        Navigation.imgPopUpBackground("viewAdminPopUpForm.fxml");
    }

    public void setData(AdminProjection projection) {
        lblAdminId.setText(String.valueOf(projection.getId()));
        lblAdminName.setText(projection.getName().getFirstName() +" "+
                projection.getName().getLastName());

        if (projection.getId() == 1 || projection.getId() == AdminSignInFormController.admin.getId()
        ) lblStatus.setText("Active");
        else lblStatus.setText("Offline");
    }

}
