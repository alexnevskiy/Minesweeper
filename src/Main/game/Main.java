package Main.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Main extends Application {
    Button playButton;  //  Кнопки в меню
    Button exitButton;
    Button restartButton;
    Button menuButton;
    Button endButton;
    Button stepButton;

    public static Stage window = new Stage();

    public static Pane gameLayout;
    public static Scene gameScene;
    public static Scene mainScene;

    //private Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    String buttonStyle = "-fx-padding: 8 15 15 15;\n" +
            "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
            "    -fx-background-radius: 8;\n" +
            "    -fx-background-color: \n" +
            "        linear-gradient(from 0% 93% to 0% 100%, #13A32B 0%, #105C19 100%),\n" +
            "        #469D24,\n" +
            "        #64D83A,\n" +
            "        radial-gradient(center 50% 50%, radius 100%, #3CAA3C, #1FFF20);\n" +
            "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
            "    -fx-font-weight: bold;\n" +
            "    -fx-text-fill: #395306;" +
            "    -fx-font-size: 4.0em;";

    String labelStyle = "    -fx-background-radius: 8;\n" +
            "    -fx-background-color: \n" +
            "        linear-gradient(from 0% 93% to 0% 100%, #000099 0%, #000099 100%),\n" +
            "        #000099,\n" +
            "        #000099,\n" +
            "        radial-gradient(center 50% 50%, radius 100%, #000000, #0000ff);\n" +
            "    -fx-font-weight: bold;\n" +
            "    -fx-text-fill: #ffffff;" +
            "    -fx-font-size: 2.0em;";

    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;

        primaryStage.setTitle("Minesweeper");  //  Название окна
        playButton = new Button("Играть");  //  Название кнопок
        exitButton = new Button("Выход");
        restartButton = new Button("Начать заново");
        menuButton = new Button("В главное меню");
        endButton = new Button("Выйти в главное меню");
        stepButton = new Button("Следующий шаг");

        TextField width = new TextField();
        width.setMaxWidth(125);
        TextField height = new TextField();
        height.setMaxWidth(125);
        TextField mines = new TextField();
        mines.setMaxWidth(125);

        Label labelWidth = new Label("Введите ширину поля");
        Label labelHeight = new Label("Введите высоту поля");
        Label labelMines = new Label("Введите количество мин");

        playButton.setStyle(buttonStyle);
        exitButton.setStyle(buttonStyle);
        restartButton.setStyle(buttonStyle);
        menuButton.setStyle(buttonStyle);
        endButton.setStyle(buttonStyle);
        stepButton.setStyle(buttonStyle);
        width.setStyle(buttonStyle);
        height.setStyle(buttonStyle);
        mines.setStyle(buttonStyle);
        labelWidth.setStyle(labelStyle);
        labelHeight.setStyle(labelStyle);
        labelMines.setStyle(labelStyle);

        primaryStage.setOnCloseRequest(e -> System.exit(0));  //  Закрывается окно при нажатии на крестик

        StackPane.setMargin(playButton, new Insets(0, 800, 300, 0));
        StackPane.setMargin(exitButton, new Insets(300, 800, 0, 0));
        StackPane.setMargin(restartButton, new Insets(0, 0, 150, 0));
        StackPane.setMargin(menuButton, new Insets(0, 0, -150, 0));
        StackPane.setMargin(endButton, new Insets(0, 0, 0, 0));
        StackPane.setMargin(stepButton, new Insets(0, -800, 300, 0));
        StackPane.setMargin(width, new Insets(0, -800, 350, 0));
        StackPane.setMargin(height, new Insets(0, -800, 0, 0));
        StackPane.setMargin(mines, new Insets(350, -800, 0, 0));
        StackPane.setMargin(labelWidth, new Insets(0, -800, 500, 0));
        StackPane.setMargin(labelHeight, new Insets(0, -800, 150, 0));
        StackPane.setMargin(labelMines, new Insets(200, -800, 0, 0));

        Image background = new Image(new FileInputStream("./images/BackgroundMenu.jpg"), 1280.0, 720.0, true, true);
        Image backgroundGame = new Image(new FileInputStream("./images/BackgroundGame.jpg"));
        ImageView backgroundMenu = new ImageView(background);
        ImageView backgroundGameView = new ImageView(backgroundGame);

        StackPane layout = new StackPane(backgroundMenu, playButton, exitButton, width, height, mines,
                labelWidth, labelHeight, labelMines);
        mainScene = new Scene(layout, 1280, 720);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        playButton.setOnAction(e -> {
            int widthNumber = Integer.parseInt(width.getText());
            int heightNumber = Integer.parseInt(height.getText());
            int minesNumber = Integer.parseInt(mines.getText());
            if (widthNumber > 999 || heightNumber > 999 || minesNumber > widthNumber * heightNumber) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Вы ввели неверные данные");
                alert.showAndWait();
            } else {
                stepButton.setLayoutX(500.0);
                stepButton.setLayoutY(100.0);
                gameLayout = new Pane(stepButton);
                window.setScene(gameScene = new Scene(gameLayout, 1280, 720));
                Game game = new Game();
                game.start(widthNumber, heightNumber, minesNumber);
            }
        });

        exitButton.setOnAction(e -> window.close());
        restartButton.setOnAction(e -> {
            gameLayout = new Pane(backgroundGameView);
            window.setScene(gameScene = new Scene(gameLayout, 1280, 720));
//            Game game = new Game();
//            try {
//                game.start();
//            } catch (FileNotFoundException ex) {
//                ex.printStackTrace();
//            }
        });
        menuButton.setOnAction(e -> window.setScene(mainScene));
        endButton.setOnAction(e -> window.setScene(mainScene));
        stepButton.setOnAction(e -> window.setScene(mainScene));
    }
}