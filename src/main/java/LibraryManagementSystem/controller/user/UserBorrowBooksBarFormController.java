package LibraryManagementSystem.controller.user;

import LibraryManagementSystem.dto.BookDto;
import LibraryManagementSystem.service.ServiceFactory;
import LibraryManagementSystem.service.custom.BookService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class UserBorrowBooksBarFormController {

    @FXML
    private Label lblAvailability;

    @FXML
    private Label lblId;

    @FXML
    private Label lblLanguage;

    @FXML
    private Label lblName;

    @FXML
    private Label lblType;

    @FXML
    private CheckBox checkBox;

    @FXML
            private Label lblAuthor;

    BookService bookService =
            (BookService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.BOOK);

    @FXML
    void checkBoxOnAction(ActionEvent event) {
        if (checkBox.isSelected()) {
            UserBorrowBooksFormController.getInstance().borrowedBooks
                    .add(bookService.getBookData(Integer.parseInt(lblId.getText())));
        } else {
            UserBorrowBooksFormController.getInstance().borrowedBooks
                    .remove(bookService.getBookData(Integer.parseInt(lblId.getText())));
        }
    }

    public void setData(int id) {
        checkBox.setVisible(true);
        BookDto bookDto = bookService.getBookData(id);

        lblId.setText(String.valueOf(bookDto.getId()));
        lblName.setText(bookDto.getName());
        lblType.setText(bookDto.getType());
        lblLanguage.setText(bookDto.getLanguage());
        lblAvailability.setText(String.valueOf(bookDto.getStatus())); // Hiển thị số lượng sách hiện có
        lblAuthor.setText(bookDto.getAuthor());

        if (bookDto.getQuantity() <= 0) {
            checkBox.setVisible(false); // Ẩn checkbox nếu sách không có sẵn
            lblAvailability.setText("Unavailable"); // Hiển thị trạng thái không có sách
        }

        if (bookDto.getStatus().equals("Unavailable")) checkBox.setVisible(false);

    }


}
