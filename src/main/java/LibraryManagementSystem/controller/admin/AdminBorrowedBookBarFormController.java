package LibraryManagementSystem.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import LibraryManagementSystem.dto.TransactionDto;
import LibraryManagementSystem.service.ServiceFactory;
import LibraryManagementSystem.service.custom.TransactionService;
import LibraryManagementSystem.util.Navigation;
import LibraryManagementSystem.util.StyleUtil;

import java.io.IOException;

public class AdminBorrowedBookBarFormController {

    @FXML
    private ImageView imgView;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblBorrowedDate;

    @FXML
    private Label lblDueDate;

    @FXML
    private Label lblId;

    @FXML
    private Label lblUserId;

    public static int transactionId;

    TransactionService transactionService =
            (TransactionService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.TRANSACTION);

    @FXML
    void imgViewOnMouseClicked(MouseEvent event) throws IOException {
        transactionId = Integer.parseInt(lblId.getText());
        Navigation.imgPopUpBackground("adminBorrowedBooksViewPopUpForm.fxml");
    }

    @FXML
    void imgViewOnMouseEntered(MouseEvent event) {
        StyleUtil.viewImgSelected(imgView);
    }

    @FXML
    void imgViewOnMouseExited(MouseEvent event) {
        StyleUtil.viewImgUnselected(imgView);
    }

    public void setData(int id) {
        TransactionDto transactionDto = transactionService.getTransactionData(id);

        lblId.setText(String.valueOf(transactionDto.getId()));
        lblUserId.setText(String.valueOf(transactionDto.getUser().getId()));
        lblAmount.setText("0" + transactionDto.getBookQty());
        lblDueDate.setText(transactionDto.getDueDate());

        String[] split = String.valueOf(transactionDto.getDateAndTime()).split(" ");
        lblBorrowedDate.setText(split[0]);
    }

}
