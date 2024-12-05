package LibraryManagementSystem.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import LibraryManagementSystem.dto.BookDto;
import LibraryManagementSystem.service.ServiceFactory;
import LibraryManagementSystem.service.custom.BookService;
import LibraryManagementSystem.util.Navigation;
import LibraryManagementSystem.util.StyleUtil;
import LibraryManagementSystem.controller.QRCodeGenerator;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewBookPopUpFormController implements Initializable {

    @FXML
    private Pane closePane;

    @FXML
    private Pane exitPane;

    @FXML
    private ImageView imgExit;

    @FXML
    private Label lblClose;

    @FXML
    private Label lblId;

    @FXML
    private Label lblLanguage;

    @FXML
    private Label lblName;

    @FXML
    private Label lblSavedBy;

    @FXML
    private Label lblType;
    @FXML
    private Label Author;

    @FXML
    private ImageView imgQRCode;

    BookService bookService =
            (BookService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.BOOK);


    @FXML
    void btnCloseOnAction(ActionEvent event) {
        Navigation.closePopUpPane();
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        Navigation.closePopUpPane();
    }

    @FXML
    void btnExitOnMouseEntered(MouseEvent event) {
        StyleUtil.closeIconBtnSelected(exitPane, imgExit);
    }

    @FXML
    void btnExitOnMouseExited(MouseEvent event) {
        StyleUtil.closeIconBtnUnselected(exitPane, imgExit);
    }

    @FXML
    void btnCloseOnMouseEntered(MouseEvent event) {
        StyleUtil.addUpdateConfirmReturnBtnSelected(closePane, lblClose);
    }

    @FXML
    void btnCloseOnMouseExited(MouseEvent event) {
        StyleUtil.addUpdateConfirmReturnBtnUnselected(closePane, lblClose);
    }

    /*public void setData() {
        BookDto bookDto = bookService
                .getBookData(AdminBookManagementBarFormController.bookId);

        lblId.setText(String.valueOf(bookDto.getId()));
        lblName.setText(bookDto.getName());
        lblType.setText(bookDto.getType());
        lblLanguage.setText(bookDto.getLanguage());
        Author.setText(bookDto.getAuthor());
        lblSavedBy.setText(
                bookDto.getAdmin().getName().getFirstName() + " " +
                        bookDto.getAdmin().getName().getLastName()
        );
        try {
            String bookInfo = "ID: " + bookDto.getId() + "\n" +
                    "Name: " + bookDto.getName() + "\n" +
                    "Type: " + bookDto.getType() + "\n" +
                    "Language: " + bookDto.getLanguage() + "\n" +
                    "Author:" + bookDto.getAuthor();

            Image qrImage = QRCodeGenerator.generateQRCodeImage(bookInfo, 150, 150);
            imgQRCode.setImage(qrImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public void setData() {
        BookDto bookDto = bookService
                .getBookData(AdminBookManagementBarFormController.bookId);

        lblId.setText(String.valueOf(bookDto.getId()));
        lblName.setText(bookDto.getName());
        lblType.setText(bookDto.getType());
        lblLanguage.setText(bookDto.getLanguage());
        Author.setText(bookDto.getAuthor());
        lblSavedBy.setText(
                bookDto.getAdmin().getName().getFirstName() + " " +
                        bookDto.getAdmin().getName().getLastName()
        );

        try {
            // Tạo URL Google Books
            String bookName = bookDto.getName().replace(" ", "+");
            String googleBooksLink = "https://www.google.com/search?q=" + bookName + "+site:books.google.com";

            // Sinh mã QR từ URL
            Image qrImage = QRCodeGenerator.generateQRCodeImage(googleBooksLink, 150, 150);
            imgQRCode.setImage(qrImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }
}
