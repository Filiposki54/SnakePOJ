package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Enums.Difficulty;
import sample.Enums.Direction;

import java.io.IOException;
import java.util.Random;

public class Game extends Main{

    Random random = new Random();

    static protected Difficulty difficulty = Difficulty.EASY;

    static private Duration speed = Duration.seconds(0.0);

    static protected KeyCode UpKey = KeyCode.W;
    static protected KeyCode DownKey = KeyCode.S;
    static protected KeyCode LeftKey = KeyCode.A;
    static protected KeyCode RightKey = KeyCode.D;

    int extraPoints = 0;

    int blockSize = 25;
    Direction direction;

    boolean moved = false;
    boolean running = false;


   public int width = 28 * blockSize;
   public int height = 16 * blockSize;

    private final Timeline timeline = new Timeline();

    ObservableList<Node> snake;

    int points = 0;

    public Parent game(){
        //zmiana trudnosci
        switch (difficulty){
            case EASY:
                speed = Duration.seconds(0.2);
                break;
            case MEDIUM:
                speed = Duration.seconds(0.06);
                break;
            case HARD:
                speed = Duration.seconds(0.03);
                break;
        }

        Pane root = new Pane();
        root.setPrefSize(width,height);
        Label score = new Label("Score :" + points);

        //inicjacja snake'a
        Group snakeBody = new Group();
        snake = snakeBody.getChildren();
        //stworzenie jedzenia
        Rectangle food = new Rectangle(blockSize,blockSize);
        food.setFill(Color.BLUE);
        food.setTranslateX((int) (Math.random() * width) /blockSize*blockSize);
        food.setTranslateY((int) (Math.random() * height) /blockSize*blockSize);

        //Tworzenie pojedyńczych klatek gry
        KeyFrame frame = new KeyFrame(speed, event -> {
            if(!running)
                return;
            boolean toRemove = snake.size()>1;
            Node head = toRemove ? snake.remove(snake.size()-1): snake.get(0);


            double tailX = head.getTranslateX();
            double tailY = head.getTranslateY();

            //zmiana kierunku
            switch (direction){
                case UP:
                    head.setTranslateX(snake.get(0).getTranslateX());
                    head.setTranslateY(snake.get(0).getTranslateY()-blockSize);
                    break;
                case DOWN:
                    head.setTranslateX(snake.get(0).getTranslateX());
                    head.setTranslateY(snake.get(0).getTranslateY()+blockSize);
                    break;
                case LEFT:
                    head.setTranslateX(snake.get(0).getTranslateX()-blockSize);
                    head.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    head.setTranslateX(snake.get(0).getTranslateX()+blockSize);
                    head.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            moved = true;
            if(toRemove)
                snake.add(0,head);

            //Przegrana przez wjechanie w ciało
            for (Node rect: snake){
                if(rect != head && head.getTranslateX() == rect.getTranslateX()
                        && head.getTranslateY() == rect.getTranslateY()){
                    score.setText("Score :" + 0);
                    restartGame();
                    break;
                }
            }
            //Przegrana przez wjechanie w ścianę
            if(head.getTranslateX()<0 || head.getTranslateX()>=width
                    || head.getTranslateY()<0 || head.getTranslateY() >= height){
                score.setText("Score :" + 0);
                restartGame();
            }

            //Zebranie jedzenia
            if(head.getTranslateX() == food.getTranslateX()
                    && head.getTranslateY() == food.getTranslateY()){
                food.setTranslateX((int) (Math.random() * width) /blockSize*blockSize);
                food.setTranslateY((int) (Math.random() * height) /blockSize*blockSize);


                points ++;
                points+=extraPoints;

                //losowy kolor i extra score
                int randomColor = random.nextInt(4);
                switch (randomColor){
                    case 0:
                        food.setFill(Color.BLUE);
                        break;
                    case 1:
                        food.setFill(Color.RED);
                        break;
                    case 2:
                        food.setFill(Color.GREEN);
                        extraPoints = 2;
                        break;
                    case 3:
                        food.setFill(Color.PURPLE);
                        extraPoints = 4;
                        break;
                }

                //Dodaje rectangle do snake'a
                Rectangle rect = new Rectangle(blockSize,blockSize);
                rect.setTranslateX(tailX);
                rect.setTranslateY(tailY);
                snake.add(rect);
                score.setText("Score :" + points);
            }

        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        root.getChildren().addAll(food,snakeBody,score);
        return root;
    }

    private void restartGame(){
        points = 0;
        stopGame();
        startGame();
    }

    private void stopGame(){
        running = false;
        timeline.stop();
        snake.clear();
    }
    private void startGame(){
        direction = Direction.RIGHT;
        Rectangle head = new Rectangle(blockSize,blockSize);
        snake.add(head);
        timeline.play();
        running = true;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(game());
        //Detekcja klawiszy
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == UpKey) {
                direction = Direction.UP;
            }
            if (key.getCode() == LeftKey) {
                direction = Direction.LEFT;
            }
            if (key.getCode() == DownKey) {
                direction = Direction.DOWN;
            }
            if (key.getCode() == RightKey) {
                direction = Direction.RIGHT;
            }

        });
        stage.setScene(scene);
        stage.setTitle("snake");
        stage.setResizable(false);
        stage.show();
        startGame();
    }

}
