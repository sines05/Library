package LibraryManagementSystem.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import LibraryManagementSystem.util.Navigation;
import LibraryManagementSystem.util.RegExPatterns;
import LibraryManagementSystem.util.SendMail;
import LibraryManagementSystem.util.Utility;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminOtpFormController implements Initializable {

    @FXML
    private Pane backPane;

    @FXML
    private Label lblBack;

    @FXML
    private Label lblVerify;

    @FXML
    private Label lblOtpAlert;

    @FXML
    private TextField txtOtp;

    @FXML
    private Pane verifyPane;

    private String otp;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(
                AdminSignInGlobalFormController.getInstance().signInSignUpPane, "adminForgotPasswordForm.fxml");
    }

    @FXML
    void btnBackOnMouseEntered(MouseEvent event) {

    }

    @FXML
    void btnBackOnMouseExited(MouseEvent event) {

    }

    @FXML
    void btnVerifyOnAction(ActionEvent event) throws IOException {
        if (validateOtp()) {
            if (txtOtp.getText().equals(otp)) {
                Navigation.switchPaging(AdminSignInGlobalFormController
                        .getInstance().signInSignUpPane, "adminResetPasswordForm.fxml");
            }
        }
        txtOtp.clear();
    }

    private boolean validateOtp() {
        if (RegExPatterns.otpPattern(txtOtp.getText())) {
            lblOtpAlert.setText("Wrong OTP! Try again!");
            return false;
        }
        return true;
    }

    @FXML
    void txtOtpOnKeyPressed(KeyEvent event) {
        if (RegExPatterns.otpPattern(txtOtp.getText())) {
            lblOtpAlert.setText("Wrong OTP! Try again!");
        } else lblOtpAlert.setText(" ");
    }

    @FXML
    void btnVerifyOnMouseEntered(MouseEvent event) {

    }

    @FXML
    void btnVerifyOnMouseExited(MouseEvent event) {

    }

    @FXML
    void txtOtpOnAction(ActionEvent event) throws IOException {
        btnVerifyOnAction(event);
    }

    private void sendOTPMail() {
        otp = Utility.generateOTP();
        try {
            String email = AdminForgotPasswordFormController.admin.getEmail();
            String subject = "Beehive: OTP Verification";
            String body = otp + " is your OTP verification code.";

            String[] detail = {email, subject, body};
            SendMail.sendMail(detail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendOTPMail();
    }

}
