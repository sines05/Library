package LibraryManagementSystem.controller.user;

import LibraryManagementSystem.dto.TransactionDto;
import LibraryManagementSystem.service.ServiceFactory;
import LibraryManagementSystem.service.custom.DashboardService;
import LibraryManagementSystem.util.Navigation;
import LibraryManagementSystem.util.StyleUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserDashboardFormController implements Initializable {

    @FXML
    private Pane availableBookPane;

    @FXML
    private Pane borrowedBookPane;

    @FXML
    private Pane pieChartPane;

    @FXML
    private Pane returnedBookPane;

    @FXML
    private Text textQuote;

    @FXML
    private PieChart pieChart;

    DashboardService dashboardService =
            (DashboardService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.DASHBOARD);

    @FXML
    void btnAvailableBookOnAction(ActionEvent event) throws IOException {
        UserGlobalFormController.getInstance().btnBooksOnAction(event);
        Navigation.switchPaging(
                UserGlobalFormController.getInstance().pagingPane, "userBorrowBooksForm.fxml");
    }

    @FXML
    void btnAvailableBookOnMouseEntered(MouseEvent event) {
        StyleUtil.userDashboardButtonsSelected(availableBookPane);
    }

    @FXML
    void btnAvailableBookOnMouseExited(MouseEvent event) {
        StyleUtil.userDashboardButtonsUnselected(availableBookPane);
    }

    @FXML
    void btnBorrowedBookOnAction(ActionEvent event) throws IOException {
        UserGlobalFormController.getInstance().btnCatalogOnAction(event);
        Navigation.switchPaging(
                UserGlobalFormController.getInstance().pagingPane, "userBorrowedBooksForm.fxml");
    }

    @FXML
    void btnBorrowedBookOnMouseEntered(MouseEvent event) {
        StyleUtil.userDashboardButtonsSelected(borrowedBookPane);
    }

    @FXML
    void btnBorrowedBookOnMouseExited(MouseEvent event) {
        StyleUtil.userDashboardButtonsUnselected(borrowedBookPane);
    }

    @FXML
    void btnReturnedBookOnAction(ActionEvent event) throws IOException {
        UserGlobalFormController.getInstance().btnCatalogOnAction(event);
        Navigation.switchPaging(
                UserGlobalFormController.getInstance().pagingPane, "userReturnedBooksForm.fxml");
    }

    @FXML
    void btnReturnedBookOnMouseEntered(MouseEvent event) {
        StyleUtil.userDashboardButtonsSelected(returnedBookPane);
    }

    @FXML
    void btnReturnedBookOnMouseExited(MouseEvent event) {
        StyleUtil.userDashboardButtonsUnselected(returnedBookPane);
    }

    private void setPieChart() {
        ObservableList<PieChart.Data> pieChartData = addPieChartData();

        pieChart = new PieChart(pieChartData); /* Create a pie chart with the data */

        pieChart.getData().get(0).getNode().setStyle(
                "-fx-pie-color:#151619 ;" +
                        "-fx-border-color: transaparent");
        pieChart.getData().get(1).getNode().setStyle(
                "-fx-pie-color: #FFEB8C;" +
                        "-fx-border-color: transaparent");
        pieChart.setLabelLineLength(0);
        pieChart.setLabelsVisible(false);
        pieChart.setLegendVisible(false);
        pieChart.setClockwise(true);
        pieChart.setPrefSize(350, 350);

        pieChartPane.getChildren().add(pieChart);
    }

    /** Add data for the Pie Chart */
    private ObservableList<PieChart.Data> addPieChartData() {
        int totalBorrowedBookCount = 0;
        int totalReturnedBookCount = 0;
        List<TransactionDto> allTransactions = dashboardService.getAllTransactions();
        for (TransactionDto dto : allTransactions) {
            if (dto.getUser().equals(UserSignInFormController.user)) {
                if (dto.getTransactionType().equals("borrow")) {
                    totalBorrowedBookCount += 1;
                }
                if (dto.getTransactionType().equals("return")) {
                    totalReturnedBookCount += 1;
                }
            }
        }
        return FXCollections.observableArrayList(
                new PieChart.Data("Total Borrowed Books", totalBorrowedBookCount),
                new PieChart.Data("Total Returned Books", totalReturnedBookCount)
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPieChart();
    }
}
