package LibraryManagementSystem.controller.user;

import LibraryManagementSystem.controller.user.UserGlobalFormController;
import LibraryManagementSystem.controller.user.UserSignInFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import LibraryManagementSystem.dto.TransactionDto;
import LibraryManagementSystem.service.ServiceFactory;
import LibraryManagementSystem.service.custom.TransactionService;
import LibraryManagementSystem.util.Navigation;
import LibraryManagementSystem.util.RegExPatterns;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserBorrowedBooksFormController implements Initializable {

    @FXML
    private Pane ReturnedBooksPane;

    @FXML
    private Label lblReturnedBooks;

    @FXML
    private Label lblSearchAlert;

    @FXML
    private Pane searchPane;

    @FXML
    private TextField txtSearch;

    @FXML
    private VBox vBoxBorrowedBooks;

    private List<TransactionDto> list;

    TransactionService transactionService =
            (TransactionService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.TRANSACTION);

    private static UserBorrowedBooksFormController controller;

    public UserBorrowedBooksFormController() {
        controller = this;
    }

    public static UserBorrowedBooksFormController getInstance() {
        return controller;
    }

    @FXML
    void btnReturnedBooksOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(
                UserGlobalFormController.getInstance().pagingPane, "userReturnedBooksForm.fxml");
    }

    @FXML
    void btnReturnedBooksOnMouseEntered(MouseEvent event) {

    }

    @FXML
    void btnReturnedBooksOnMouseExited(MouseEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws IOException {
        if (validateSearch()) {
            for (TransactionDto dto : list) {
                if (txtSearch.getText().equals(String.valueOf(dto.getId()))) {
                    if (dto.getTransactionType().equals("borrow") &&
                            dto.getUser().equals(UserSignInFormController.user)) {
                        UserBorrowedBooksBarFormController.transactionId = dto.getId();
                        Navigation.imgPopUpBackground("userReturnBookConfirmPopUpForm.fxml");
                        lblSearchAlert.setText(" ");
                        txtSearch.clear();
                        return;
                    }
                }
            }
        }
        txtSearch.clear();
    }

    private boolean validateSearch() {
        if (validateId()) {
            lblSearchAlert.setText("Invalid Id!!");
            return false;
        }
        return true;
    }

    public boolean validateId() {
        return RegExPatterns.idPattern(txtSearch.getText());
    }

    @FXML
    void txtSearchOnMouseMoved(MouseEvent event) {
        lblSearchAlert.setText(" ");
    }

    public void allBorrowedTransactionId() {
        vBoxBorrowedBooks.getChildren().clear();
        list = transactionService.getTransactionAllId();
        if (list == null) return;

        for (TransactionDto dto : list) {
            if (dto.getTransactionType().equals("borrow") &&
                    dto.getUser().equals(UserSignInFormController.user)
            ) loadDataTable(dto.getId());
        }
    }

    private void loadDataTable(int id) {
        try {
            FXMLLoader loader = new FXMLLoader(UserBorrowedBooksFormController.class.getResource("/view/userBorrowedBooksBarForm.fxml"));
            Parent root = loader.load();
            UserBorrowedBooksBarFormController controller = loader.getController();
            controller.setData(id);
            vBoxBorrowedBooks.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allBorrowedTransactionId();
    }

}
