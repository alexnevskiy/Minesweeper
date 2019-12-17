package Main.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {
    Button playButton;  //  Кнопки в меню
    Button exitButton;
    Button restartButton;
    Button returnButton;
    static Button stepButton;
    CheckBox solve;
    CheckBox step;

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

    String gameStyle = "    -fx-background-radius: 8;\n" +
            "    -fx-background-color: \n" +
            "        linear-gradient(from 0% 93% to 0% 100%, #000099 0%, #000099 100%),\n" +
            "        #000099,\n" +
            "        #000099,\n" +
            "        radial-gradient(center 50% 50%, radius 100%, #000000, #0000ff);\n" +
            "    -fx-font-weight: bold;\n" +
            "    -fx-text-fill: #ffffff;" +
            "    -fx-font-size: 1.0em;";

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
        returnButton = new Button("Выйти");
        stepButton = new Button("Шаг");
        solve = new CheckBox("Запустить решатель");
        step = new CheckBox("Пошаговый решатель");

        TextField width = new TextField();
        width.setMaxWidth(125);
        TextField height = new TextField();
        height.setMaxWidth(125);
        TextField mines = new TextField();
        mines.setMaxWidth(125);
        solve.setSelected(true);
        step.setSelected(false);

        Label labelWidth = new Label("Введите ширину поля");
        Label labelHeight = new Label("Введите высоту поля");
        Label labelMines = new Label("Введите количество мин");

        playButton.setStyle(buttonStyle);
        exitButton.setStyle(buttonStyle);
        restartButton.setStyle(gameStyle);
        returnButton.setStyle(gameStyle);
        stepButton.setStyle(gameStyle);
        width.setStyle(buttonStyle);
        height.setStyle(buttonStyle);
        mines.setStyle(buttonStyle);
        labelWidth.setStyle(labelStyle);
        labelHeight.setStyle(labelStyle);
        labelMines.setStyle(labelStyle);
        solve.setStyle(labelStyle);
        step.setStyle(labelStyle);

        primaryStage.setOnCloseRequest(e -> System.exit(0));  //  Закрывается окно при нажатии на крестик

        StackPane.setMargin(playButton, new Insets(0, 800, 300, 0));
        StackPane.setMargin(exitButton, new Insets(300, 800, 0, 0));
        StackPane.setMargin(restartButton, new Insets(0, 0, 150, 0));
        StackPane.setMargin(returnButton, new Insets(0, -800, 300, 0));
        StackPane.setMargin(width, new Insets(0, -800, 350, 0));
        StackPane.setMargin(height, new Insets(0, -800, 0, 0));
        StackPane.setMargin(mines, new Insets(350, -800, 0, 0));
        StackPane.setMargin(labelWidth, new Insets(0, -800, 500, 0));
        StackPane.setMargin(labelHeight, new Insets(0, -800, 150, 0));
        StackPane.setMargin(labelMines, new Insets(200, -800, 0, 0));
        StackPane.setMargin(solve, new Insets(0, 300, 350, 0));
        StackPane.setMargin(step, new Insets(0, 275, 250, 0));

        Image background = new Image(new FileInputStream("./images/BackgroundMenu.jpg"), 1280.0, 720.0, true, true);
        ImageView backgroundMenu = new ImageView(background);

        StackPane layout = new StackPane(backgroundMenu, playButton, exitButton, width, height, mines,
                labelWidth, labelHeight, labelMines, solve, step);
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
                returnButton.setLayoutX(0);
                returnButton.setLayoutY(heightNumber * 30);
                restartButton.setLayoutX(widthNumber * 30 - 102);
                restartButton.setLayoutY(heightNumber * 30);
                stepButton.setLayoutX(widthNumber * 30 / 2 - 20);
                stepButton.setLayoutY(heightNumber * 30);
                if (step.isSelected()) {
                    gameLayout = new Pane(returnButton, restartButton, stepButton);
                } else {
                    gameLayout = new Pane(returnButton, restartButton);
                }
                window.setScene(gameScene = new Scene(gameLayout, widthNumber * 30, heightNumber * 30 + 25));
                Game game = new Game();
                game.solve = solve.isSelected();
                game.step = step.isSelected();
                game.start(widthNumber, heightNumber, minesNumber);
            }
        });

        exitButton.setOnAction(e -> window.close());
        restartButton.setOnAction(e -> {
            int widthNumber = Integer.parseInt(width.getText());
            int heightNumber = Integer.parseInt(height.getText());
            int minesNumber = Integer.parseInt(mines.getText());
            returnButton.setLayoutX(0);
            returnButton.setLayoutY(heightNumber * 30);
            restartButton.setLayoutX(widthNumber * 30 - 102);
            restartButton.setLayoutY(heightNumber * 30);
            stepButton.setLayoutX(widthNumber * 30 / 2 - 20);
            stepButton.setLayoutY(heightNumber * 30);
            if (step.isSelected()) {
                gameLayout = new Pane(returnButton, restartButton, stepButton);
            } else {
                gameLayout = new Pane(returnButton, restartButton);
            }
            window.setScene(gameScene = new Scene(gameLayout, widthNumber * 30, heightNumber * 30 + 25));
            Game game = new Game();
            game.solve = solve.isSelected();
            game.step = step.isSelected();
            game.start(widthNumber, heightNumber, minesNumber);
        });
        returnButton.setOnAction(e -> window.setScene(mainScene));
    }
}