package LibraryManagementSystem.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import LibraryManagementSystem.dto.TransactionDto;
import LibraryManagementSystem.entity.TransactionDetail;
import LibraryManagementSystem.service.ServiceFactory;
import LibraryManagementSystem.service.custom.TransactionService;
import LibraryManagementSystem.util.Navigation;
import LibraryManagementSystem.util.StyleUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminBorrowedBooksViewPopUpFormController implements Initializable {

    @FXML
    private Pane closePane;

    @FXML
    private Label lblClose;

    @FXML
    private Label lblDueDate;

    @FXML
    private Label lblId;

    @FXML
    private Label lblTotalBooks;

    @FXML
    private VBox vBox;

    TransactionService transactionService =
            (TransactionService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.TRANSACTION);

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        Navigation.closePopUpLargePane();
    }

    @FXML
    void btnCloseOnMouseEntered(MouseEvent event) {
        StyleUtil.addUpdateConfirmReturnBtnSelected(closePane, lblClose);
    }

    @FXML
    void btnCloseOnMouseExited(MouseEvent event) {
        StyleUtil.addUpdateConfirmReturnBtnUnselected(closePane, lblClose);
    }

    public void allBorrowedBookId() {
        setData();
        TransactionDto dto = transactionService
                .getTransactionData(AdminBorrowedBookBarFormController.transactionId);

        List<TransactionDetail> transactionDetails = dto.getTransactionDetails();

        vBox.getChildren().clear();
        if (transactionDetails == null) return;

        for (TransactionDetail transactionDetail : transactionDetails) {
            loadDataTable(transactionDetail.getBook().getId());
        }
    }

    private void loadDataTable(int id) {
        try {
            FXMLLoader loader = new FXMLLoader(AdminBorrowedBooksViewPopUpFormController.class.getResource("/view/adminBorrowedBooksViewPopUpBarForm.fxml"));
            Parent root = loader.load();
            AdminBorrowedBooksViewPopUpBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setData() {
        TransactionDto transactionDto = transactionService
                .getTransactionData(AdminBorrowedBookBarFormController.transactionId);

        lblDueDate.setText(transactionDto.getDueDate());
        lblId.setText(String.valueOf(transactionDto.getId()));
        lblTotalBooks.setText(String.valueOf(transactionDto.getBookQty()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allBorrowedBookId();
    }

}
