package LibraryManagementSystem.controller.admin;

import LibraryManagementSystem.controller.admin.AdminSignInFormController;
import LibraryManagementSystem.controller.admin.AdminUserManagementFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import LibraryManagementSystem.service.ServiceFactory;
import LibraryManagementSystem.service.custom.DeleteService;
import LibraryManagementSystem.util.Navigation;
import LibraryManagementSystem.util.SendMail;
import LibraryManagementSystem.util.StyleUtil;

import javax.mail.MessagingException;
import java.io.IOException;

public class DeleteConfirmationFormController {

    @FXML
    private Pane closePane;

    @FXML
    private Pane confirmPane;

    @FXML
    private ImageView imgClose;

    @FXML
    private Label lblConfirm;

    public static String objectName;
    public static int id;

    DeleteService deleteService =
            (DeleteService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.DELETE);

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        Navigation.closePopUpPane();
    }

    @FXML
    void btnCloseOnMouseEntered(MouseEvent event) {
        StyleUtil.closeIconBtnSelected(closePane, imgClose);
    }

    @FXML
    void btnCloseOnMouseExited(MouseEvent event) {
        StyleUtil.closeIconBtnUnselected(closePane, imgClose);
    }

    @FXML
    void btnConfirmOnMouseEntered(MouseEvent event) {
        StyleUtil.addUpdateConfirmReturnBtnSelected(confirmPane, lblConfirm);
    }

    @FXML
    void btnConfirmOnMouseExited(MouseEvent event) {
        StyleUtil.addUpdateConfirmReturnBtnUnselected(confirmPane, lblConfirm);
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) throws IOException {
        switch (objectName) {
            case "admin":
                if (deleteService.deleteAdmin(id)) {
                    Navigation.close(event);
                    Navigation.switchNavigation("adminSignInGlobalForm.fxml", event);
                    sendMail();
                } else System.out.println("Unable to Delete Admin!");
                break;
            case "book":
                if (deleteService.deleteBook(id)) {
                    AdminBookManagementFormController.getInstance().allBookId();
                    Navigation.closePopUpPane();
                } else System.out.println("Unable to Delete Book!");
                break;
            case "branch":
                if (deleteService.deleteBranch(id)) {
                    AdminBranchManagementFormController.getInstance().allBranchId();
                    Navigation.closePopUpPane();
                } else System.out.println("Unable to Delete Branch!");
                break;
            case "user":
                if (deleteService.deleteUser(id)) {
                    AdminUserManagementFormController.getInstance().allUserId();
                    Navigation.closePopUpPane();
                } else System.out.println("Unable to Delete User!");
                break;
        }
    }

    private void sendMail() {
        try {
            String email = AdminSignInFormController.admin.getEmail();
            String subject = "Your Beehive Account has been Deleted!";
            String body = "Dear "+
                    AdminSignInFormController.admin.getName().getFirstName() +" "+
                    AdminSignInFormController.admin.getName().getLastName() +",\n" +
                    "We acknowledge that you have initiated the deletion of your account.\n" +
                    "If you have any further inquiries or require assistance, please feel free to reach out to us.\n" +
                    "\n" +
                    "Thank you.\n" +
                    "\n" +
                    "Kind regards,\n" +
                    "Beehive Library Management";

            String[] detail = {email, subject, body};
            SendMail.sendMail(detail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}