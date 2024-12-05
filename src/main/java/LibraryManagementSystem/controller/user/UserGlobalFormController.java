package LibraryManagementSystem.controller.user;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import LibraryManagementSystem.util.DateTimeUtil;
import LibraryManagementSystem.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserGlobalFormController implements Initializable {

    @FXML
    private Pane booksPane;

    @FXML
    private Pane catalogPane;

    @FXML
    private Pane dashboardPane;

    @FXML
    private ImageView imgBooks;

    @FXML
    private ImageView imgCatalog;

    @FXML
    private ImageView imgDashboard;

    @FXML
    private ImageView imgLogOut;

    @FXML
    public ImageView imgTransparent;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Pane logOutPane;

    @FXML
    public Pane pagingPane;

    @FXML
    public Pane popUpLargePane;

    @FXML
    public Pane popUpPane;

    @FXML
    public Pane settingsPane;

    private static UserGlobalFormController controller;

    public UserGlobalFormController() {
        controller = this;
    }

    public static UserGlobalFormController getInstance() {
        return controller;
    }

    private boolean dashboardButtonSelected = false;
    private boolean catalogButtonSelected = false;
    private boolean booksButtonSelected = false;
    private boolean logOutButtonSelected = false;

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        buttonUnSelected();
        dashboardButtonSelected = true;
        unSelectedButtons();
        selectedButton(dashboardPane, imgDashboard, "dashboardIconWhite.png");
        Navigation.switchPaging(pagingPane, "userDashboardForm.fxml");
    }

    @FXML
    void btnCatalogOnAction(ActionEvent event) throws IOException {
        buttonUnSelected();
        catalogButtonSelected = true;
        unSelectedButtons();
        selectedButton(catalogPane, imgCatalog, "catalogIconWhite.png");
        Navigation.switchPaging(pagingPane, "userBorrowedBooksForm.fxml");
    }

    @FXML
    void btnBooksOnAction(ActionEvent event) throws IOException {
        buttonUnSelected();
        booksButtonSelected = true;
        unSelectedButtons();
        selectedButton(booksPane, imgBooks, "booksIconWhite.png");
        Navigation.switchPaging(pagingPane, "userBorrowBooksForm.fxml");
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        selectedButton(logOutPane, imgLogOut, "logOutIconWhite.png");
        Navigation.close(event);
        Navigation.switchNavigation("userSignInGlobalForm.fxml", event);
    }

    @FXML
    void btnDashboardOnMouseEntered(MouseEvent event) {
        if(!dashboardButtonSelected) allBtnHoverCss(dashboardPane,
                imgDashboard, "dashboardIconBlack.png");
    }

    @FXML
    void btnDashboardOnMouseExited(MouseEvent event) {
        if(!dashboardButtonSelected) btnUnselected(dashboardPane,
                imgDashboard, "dashboardIconBlack.png");
    }

    @FXML
    void btnCatalogOnMouseEntered(MouseEvent event) {
        if(!catalogButtonSelected) allBtnHoverCss(catalogPane,
                imgCatalog, "catalogIconBlack.png");
    }

    @FXML
    void btnCatalogOnMouseExited(MouseEvent event) {
        if(!catalogButtonSelected) btnUnselected(catalogPane,
                imgCatalog, "catalogIconBlack.png");
    }

    @FXML
    void btnBooksOnMouseEntered(MouseEvent event) {
        if(!booksButtonSelected) allBtnHoverCss(booksPane,
                imgBooks, "booksIconBlack.png");
    }

    @FXML
    void btnBooksOnMouseExited(MouseEvent event) {
        if(!booksButtonSelected) btnUnselected(booksPane,
                imgBooks, "booksIconBlack.png");
    }

    @FXML
    void btnLogOutOnMouseEntered(MouseEvent event) {
        if(!logOutButtonSelected) allBtnHoverCss(logOutPane,
                imgLogOut, "logOutIconBlack.png");
    }

    @FXML
    void btnLogOutOnMouseExited(MouseEvent event) {
        if(!logOutButtonSelected) btnUnselected(logOutPane,
                imgLogOut, "logOutIconBlack.png");
    }

    @FXML
    void btnSettingsOnAction(ActionEvent event) throws IOException {
        if (UserGlobalFormController.getInstance().settingsPane.isVisible()) {
            Navigation.closeUserSettingsPane();
        } else {
            Navigation.imgPopUpBackground("userSettingsPopUpForm.fxml");
        }
    }

    private void buttonUnSelected() {
        dashboardButtonSelected = false;
        catalogButtonSelected = false;
        booksButtonSelected = false;
        logOutButtonSelected = false;
    }

    private void unSelectedButtons() {
        btnUnselected(dashboardPane, imgDashboard, "dashboardIconBlack.png");
        btnUnselected(catalogPane, imgCatalog, "catalogIconBlack.png");
        btnUnselected(booksPane, imgBooks, "booksIconBlack.png");
    }

    private void selectedButton(Pane pane, ImageView imageView, String path) {
        btnSelected(pane, imageView, path);
    }

    void btnSelected(Pane pane, ImageView imageView, String path) {
        pane.setStyle(
                "-fx-background-color: black;" +
                        "-fx-background-radius: 1px;");
        imageView.setImage(new Image("assests/icon/" + path));
    }

    void btnUnselected(Pane pane, ImageView imageView, String path) {
        pane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-radius: 1px;");
        imageView.setImage(new Image("assests/icon/" + path));
    }

    void allBtnHoverCss(Pane pane, ImageView imageView, String path){
        pane.setStyle("-fx-background-color: #D7D7D7;" +
                "-fx-background-radius: 1px;");
        imageView.setImage(new Image("assests/icon/" + path));
    }

    private void loadDashboardForm() {
        try {
            Navigation.switchPaging(pagingPane, "userDashboardForm.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUserName() {
        lblUser.setText(UserSignInFormController.user.getName());
    }

    private void updateClock() {
        lblTime.setText(DateTimeUtil.timeNow());
    }

    private void setTimeLine() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateClock()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void initializeValues() {
        dashboardButtonSelected = true;
        btnSelected(dashboardPane, imgDashboard, "dashboardIconWhite.png");
        pagingPane.setVisible(true);
        setUserName();
        setTimeLine();
        lblDate.setText(DateTimeUtil.dateNowFormatted());
        loadDashboardForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeValues();
    }

}
