package gui;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.ui.InGamePanel;
import facade.HomeworkTwoFacade;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import gui.control.BallComponent;
import gui.control.BarComponent;
import gui.control.BrickComponent;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.brick.Brick;
import logic.level.Level;
import logic.level.RealLevel;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.sun.glass.ui.Cursor.setVisible;
import static gui.BreakoutFactory.*;


public class BreakoutGameApp extends GameApplication {
    private HomeworkTwoFacade hw2;
    private List<Entity> brickEntityList;
    private boolean initialBallCond = true;
    private InGamePanel panel;

    private int WIDTH = 1200;
    private int HEIGHT = 800;

    private BarComponent getBarComponent() {
        return getGameWorld().getSingleton(BreakoutType.BAR).get().getComponent(BarComponent.class);
    }


    private Entity getBarEntity() {
        return getGameWorld().getSingleton(BreakoutType.BAR).get();
    }


    private Entity getBallEntity() {
        return getGameWorld().getSingleton(BreakoutType.BALL).get();
    }

    private BrickComponent getBrickComponent(Entity brick){
        return brick.getComponent(BrickComponent.class);
    }

    private boolean isPresentBall() {
        return getGameWorld().getSingleton(BreakoutType.BALL).isPresent();
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(WIDTH);
        gameSettings.setHeight(HEIGHT);
        gameSettings.setTitle("Basic Breakout Game App");
        gameSettings.setVersion("0.1");
    }

    @Override
    protected void preInit() {
        String backgroundMusic = "background.mpeg";
        getAudioPlayer().loopBGM(backgroundMusic);
        getAudioPlayer().setGlobalMusicVolume(backgroundMusic.equals("background.mpeg")? 0.5:0.2);
        getAudioPlayer().setGlobalSoundVolume(1.5);
    }

    @Override
    protected void initGame() {
        Entity bar = newBar(300, 700,BreakoutType.BAR);
        getGameWorld().addEntity(bar);
        Entity ball = newBall(bar.getCenter().getX(),bar.getY()-10);
        getGameWorld().addEntity(ball);
        Entity wall = newWall();
        getGameWorld().addEntity(wall);
        Entity background = newBackground();
        getGameWorld().addEntity(background);

        hw2 = new HomeworkTwoFacade();
        setUInumberOfBalls(hw2.getBallsLeft());
        brickEntityList = new ArrayList<>();
    }

    @Override
    protected void initInput() {
        Input input = getInput();

        input.addAction(new UserAction("Move Right Bar") {
            @Override
            protected void onAction() {
                getBarComponent().right();
                getGameState().increment("pixelsMoved", +5);
                if(initialBallCond) {
                    repositionBall(getBallEntity());
                }
            }
            @Override
            protected void onActionEnd() {
                if(initialBallCond) {
                    repositionBall(getBallEntity());
                }
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left Bar") {
            @Override
            protected void onAction() {
                getBarComponent().left();
                getGameState().increment("pixelsMoved", -5);
                if(initialBallCond) {
                    repositionBall(getBallEntity());
                }
            }
            @Override
            protected void onActionEnd() {
                if(initialBallCond) {
                    repositionBall(getBallEntity());
                }
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Impulse Ball") {
            @Override
            protected void onActionBegin() {
                if(isPresentBall() && Math.abs(getBallEntity().getY() - (getBarEntity().getY()-30)) <= 2 && !hw2.isGameOver()) {
                    initialBallCond = false;
                    impulseBall(getBallEntity());
                }
            }

        }, KeyCode.SPACE);

        input.addAction(new UserAction("New Level") {
            @Override
            protected void onActionBegin() {
                int seed = (int)(System.currentTimeMillis());
                if(!hw2.getCurrentLevel().isPlayableLevel() && !hw2.isGameOver()){
                    brickEntityList = new ArrayList<>();
                    Level level = hw2.newLevelWithBricksFull("Level",30, 0.5, 0.5,seed);
                    hw2.setCurrentLevel(level);
                    addHitteablesToEntities();
                    addEntitiesToGameWorld();
                    setUInumberOfBalls(hw2.getBallsLeft());
                    incUIlevelsLeft(+1);
                    System.out.println("Nuevo Primer Nivel");
                }
                else {
                    Level newLevel = hw2.newLevelWithBricksFull("nextLevel", 30, 0.3, 0.5, seed);
                    hw2.addPlayingLevel(newLevel);
                    incUIlevelsLeft(+1);
                    System.out.println("Añadiste un nuevo Nivel");
                }
            }

        }, KeyCode.N);

        getInput().addAction(new UserAction("Open/Close Panel") {
            @Override
            protected void onActionBegin() {
                if (panel.isOpen())
                    panel.close();
                else
                    panel.open();
            }
        }, KeyCode.TAB);

        getInput().addAction(new UserAction("Restart Game") {
            @Override
            protected void onActionBegin() {
                if(hw2.isGameOver() || hw2.winner()){
                    startNewGame();
                    initialBallCond = true;
                }
            }
        }, KeyCode.ENTER);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(BreakoutType.BAR,BreakoutType.BALL) {
            @Override
            protected void onCollisionBegin(Entity bar, Entity ball) {
                changeBallVelocity(ball,1.3);
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(BreakoutType.BALL, BreakoutType.BRICK) {
            @Override
            protected void onCollisionBegin(Entity ball, Entity brick) {
                changeBallVelocity(ball,0.7);
                showEffect((ball.getX()+brick.getX())/2,(ball.getY()+brick.getY())/2);
            }

            @Override
            protected void onCollision(Entity ball, Entity brick) {
                getBrickComponent(brick).hitBrick();
                boolean isDestroyed = getBrickComponent(brick).isDestroyed();
                if(getBrickComponent(brick).isWoodenComponent()){
                    getAudioPlayer().playSound(isDestroyed?  "r2d2.wav":"woodBrickSound.wav");
                    if(getBrickComponent(brick).remainingHits() == 1) {
                        brick.setView(FXGL.getAssetLoader().loadTexture("woodenBrickDestroyed.png", 100, 30));
                    }
                }
                if(getBrickComponent(brick).isMetalComponent()){
                    getAudioPlayer().playSound(isDestroyed?  "r2d2.wav":"metalBrickSound.wav");
                    if(getBrickComponent(brick).remainingHits() == 3) {
                        brick.setView(FXGL.getAssetLoader().loadTexture("metalBrickDestroyed.png", 100, 30));
                    }
                }
                if(getBrickComponent(brick).isGlassComponent()) {
                    getAudioPlayer().playSound("glassBrickSound.wav");
                }
                if (isDestroyed) {
                    if(getBrickComponent(brick).isMetalComponent()){
                        Entity newBallMetal = newBall(getBarEntity().getCenter().getX(),getBarEntity().getY()-10);
                         getGameWorld().addEntity(newBallMetal);
                        impulseBall(newBallMetal);
                        System.out.println("Ganaste una bola");
                    }
                    setUIgameScore(hw2.getCurrentPoints());
                    setUIlevelScore(hw2.getAcumLevelPoints());
                    brick.removeFromWorld();
                    if(hw2.getCurrentPoints() == hw2.getAcumLevelPoints() && !brick.getComponent(BrickComponent.class).isMetalComponent()) {
                        removeEntitiesFromGameWorld();
                        incUIlevelsPlayed();
                        incUIlevelsLeft(-1);
                        brickEntityList.clear();
                        addHitteablesToEntities();
                        addEntitiesToGameWorld();
                        initialBallCond = true;
                        repositionBall(getBallEntity());
                    }
                    if(hw2.winner()){
                        getAudioPlayer().playSound("tada.wav");
                        Font largeFont = new Font("Winner",50);
                        Text winnerText = new Text("CONGRATULATIONS YOU WIN!");
                        winnerText.setTranslateX(250);
                        winnerText.setTranslateY(350);
                        winnerText.setFont(largeFont);
                        getGameScene().addUINode(winnerText);
                        Font largeFontRestart = new Font("gameOver",25);
                        Text gameWin = new Text("Press ENTER to restart Game...");
                        gameWin.setTranslateX(425);
                        gameWin.setTranslateY(400);
                        gameWin.setFont(largeFontRestart);
                        getGameScene().addUINode(gameWin);
                    }
                }
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(BreakoutType.BALL,BreakoutType.WALL) {
            @Override
            protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxBall, HitBox boxWall) {
                //changeBallVelocity(ball,1);
                if (boxWall.getName().equals("BOT")) {
                    ball.removeFromWorld();
                    hw2.dropBall();
                    setUInumberOfBalls(hw2.getBallsLeft());
                    System.out.println("Bola perdida");
                }
                if(hw2.isGameOver()){
                    getAudioPlayer().playSound("gameover.wav");
                    Font largeFont = new Font("gameOver",100);
                    Text gameOverText = new Text("GAME OVER");
                    gameOverText.setTranslateX(300);
                    gameOverText.setTranslateY(350);
                    gameOverText.setFont(largeFont);
                    getGameScene().addUINode(gameOverText);

                    Font largeFontRestart = new Font("gameOver",25);
                    Text gameRestart = new Text("Press ENTER to restart Game...");
                    gameRestart.setTranslateX(425);
                    gameRestart.setTranslateY(400);
                    gameRestart.setFont(largeFontRestart);
                    getGameScene().addUINode(gameRestart);

                }

                if(!isPresentBall() && !hw2.isGameOver()) {
                    Entity newBall = newBall(getBarEntity().getCenter().getX(), getBarEntity().getY()-10);
                    getGameWorld().addEntity(newBall);
                    initialBallCond = true;
                }
            }
        });
    }

    @Override
    protected void initUI() {
        panel = new InGamePanel();
        Font largeFont = new Font("gameFont",25);

        Text textScore = getUIFactory().newText("Actual Score:");
        textScore.setTranslateX(30);
        textScore.setTranslateY(50);
        textScore.setFont(largeFont);

        Text score = new Text();
        score.setTranslateX(220);
        score.setTranslateY(50);
        score.setFont(largeFont);

        Text textBalls = getUIFactory().newText("Balls Left:");
        textBalls.setTranslateX(30);
        textBalls.setTranslateY(150);
        textBalls.setFont(largeFont);

        Text balls = new Text();
        balls.setTranslateX(220);
        balls.setTranslateY(150);
        balls.setFont(largeFont);

        Text textLevelPoints = getUIFactory().newText("Levels Score:");
        textLevelPoints.setTranslateX(30);
        textLevelPoints.setTranslateY(100);
        textLevelPoints.setFont(largeFont);

        Text levelPoints = new Text();
        levelPoints.setTranslateX(220);
        levelPoints.setTranslateY(100);
        levelPoints.setFont(largeFont);

        Text textLevelsPlayed = getUIFactory().newText("Levels Played:");
        textLevelsPlayed.setTranslateX(30);
        textLevelsPlayed.setTranslateY(200);
        textLevelsPlayed.setFont(largeFont);

        Text levelsPlayed = new Text();
        levelsPlayed.setTranslateX(220);
        levelsPlayed.setTranslateY(200);
        levelsPlayed.setFont(largeFont);

        Text textLevelsLeft = getUIFactory().newText("Levels Left:");
        textLevelsLeft.setTranslateX(30);
        textLevelsLeft.setTranslateY(250);
        textLevelsLeft.setFont(largeFont);

        Text levelsLeft = new Text();
        levelsLeft.setTranslateX(220);
        levelsLeft.setTranslateY(250);
        levelsLeft.setFont(largeFont);

        score.textProperty().bind(getGameState().intProperty("actualScore").asString());
        balls.textProperty().bind(getGameState().intProperty("ballsLeft").asString());
        levelPoints.textProperty().bind(getGameState().intProperty("levelScore").asString());
        levelsPlayed.textProperty().bind(getGameState().intProperty("levelsPlayed").asString());
        levelsLeft.textProperty().bind(getGameState().intProperty("levelsLeft").asString());


        VBox menu0 = new VBox(50);
        menu0.setTranslateX(100);
        menu0.setTranslateY(500);

        VBox menu1 = new VBox(10);
        menu1.setTranslateX(100);
        menu1.setTranslateY(300);

        Label nameLevel = new Label("Level name:");
        TextField textFieldName = new TextField ();
        menu1.getChildren().addAll(nameLevel, textFieldName);

        Label numberOfBricks = new Label("Number of Bricks:");
        TextField textFieldNumberBricks = new TextField ();
        menu1.getChildren().addAll(numberOfBricks, textFieldNumberBricks);

        Label probOfGlass = new Label("Prob of Glass:");
        TextField textFieldGlass = new TextField ();
        menu1.getChildren().addAll(probOfGlass, textFieldGlass);

        Label probOfMetal = new Label("Prob of Metal:");
        TextField textFieldMetal = new TextField ();
        menu1.getChildren().addAll(probOfMetal, textFieldMetal);


        Font fontCustom = Font.font(20);
        Button btnCreateLevel = getUIFactory().newButton("Create!");
        btnCreateLevel.setFont(fontCustom);
        btnCreateLevel.setOnAction(event -> {
            if(!textFieldName.getText().isEmpty() && !textFieldNumberBricks.getText().isEmpty() && !textFieldGlass.getText().isEmpty() && !textFieldMetal.getText().isEmpty()) {
                Level customLevel = hw2.newLevelWithBricksFull(textFieldName.getText(), Integer.parseInt(textFieldNumberBricks.getText()), Double.parseDouble(textFieldGlass.getText()), Double.parseDouble(textFieldMetal.getText()), 1);
                if (!hw2.getCurrentLevel().isPlayableLevel() && !hw2.isGameOver()) {
                    brickEntityList = new ArrayList<>();
                    hw2.setCurrentLevel(customLevel);
                    addHitteablesToEntities();
                    addEntitiesToGameWorld();
                    setUInumberOfBalls(hw2.getBallsLeft());
                    incUIlevelsLeft(+1);
                } else {
                    hw2.addPlayingLevel(customLevel);
                    incUIlevelsLeft(+1);
                }
            }
        });

        Font font = Font.font(20);
        Button btnNewLevel = getUIFactory().newButton("NEW LEVEL");
        btnNewLevel.setFont(font);
        btnNewLevel.setOnMouseClicked(event -> {
            changeMenus(menu1,menu0);
        });

        Button btnBack = getUIFactory().newButton("BACK");
        btnBack.setFont(font);
        btnBack.setOnMouseClicked(event -> {
            changeMenus(menu0,menu1);
        });

        menu0.getChildren().addAll(btnNewLevel);
        menu1.getChildren().addAll(btnCreateLevel,btnBack);

        Rectangle bg = new Rectangle();

        panel.getChildren().addAll(bg,menu0);

        panel.getChildren().add(score);
        panel.getChildren().add(textScore);
        panel.getChildren().add(textBalls);
        panel.getChildren().add(balls);
        panel.getChildren().add(levelPoints);
        panel.getChildren().add(textLevelPoints);
        panel.getChildren().add(textLevelsPlayed);
        panel.getChildren().add(levelsPlayed);
        panel.getChildren().add(textLevelsLeft);
        panel.getChildren().add(levelsLeft);

        getGameScene().addUINode(panel);
    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved",0);
        vars.put("actualScore",0);
        vars.put("ballsLeft",0);
        vars.put("levelsPlayed",0);
        vars.put("levelsLeft",0);
        vars.put("levelScore",0);

    }

    private void changeMenus(VBox fromMenu, VBox toMenu) {
        panel.getChildren().add(fromMenu);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), toMenu);
        tt.setToX(fromMenu.getTranslateX() - 400);

        TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), fromMenu);
        tt1.setToX(toMenu.getTranslateX());

        tt.play();
        tt1.play();

        tt.setOnFinished(event1 -> {
            panel.getChildren().remove(toMenu);
        });
    }
    private void addHitteablesToEntities(){
        List<Brick> brickList = hw2.getBricks();
        Collections.shuffle(brickList);
        int i = 1; int j = 1;
        int x = 1; int y = 1;
        for(Brick brick : brickList) {
            if(Math.abs(x-WIDTH) < 200){
                i = 1;
                j += 1;
            }
            x = i * 102;
            y = j * 32;
            brickEntityList.add(newBrick(x, y, brick));
            i+=1;
        }
    }

    private void addEntitiesToGameWorld(){
        for(Entity entity : brickEntityList){
            BrickComponent brickComp = entity.getComponent(BrickComponent.class);
            if(brickComp.isMetalComponent()) {
                entity.setView(FXGL.getAssetLoader().loadTexture("metalBrick.png", 100, 30));
            }
            if(brickComp.isWoodenComponent()) {
                entity.setView(FXGL.getAssetLoader().loadTexture("woodenBrick.png", 100, 30));
            }
            if(brickComp.isGlassComponent()) {
                entity.setView(FXGL.getAssetLoader().loadTexture("glassBrickDestroyed.png", 100, 30));
            }
            getGameWorld().addEntity(entity);
        }
    }

    private void removeEntitiesFromGameWorld(){
        for(Entity entity : brickEntityList){
            entity.removeFromWorld();
        }
    }

    private void repositionBall(Entity ball){
        Point2D position = new Point2D(getBarEntity().getCenter().getX(),getBarEntity().getY()-30);
        ball.getComponent(PhysicsComponent.class).reposition(position);
        ball.getComponent(PhysicsComponent.class).setLinearVelocity(0,0);
    }


    private void impulseBall(Entity ball){
        PhysicsComponent physicsComponent = ball.getComponent(PhysicsComponent.class);
        physicsComponent.setLinearVelocity(-50, -1000);
    }

    private void changeBallVelocity(Entity ball, double velocity){
        PhysicsComponent physicsComponent = ball.getComponent(PhysicsComponent.class);
        double velocityX = physicsComponent.getVelocityX();
        double velocityY = physicsComponent.getVelocityY();
        physicsComponent.setLinearVelocity(velocityX*velocity,velocityY*velocity);
    }

    /**
     * Set a number of balls to GUI.
     * @param value
     */
    private void setUInumberOfBalls(int value){
        getGameState().setValue("ballsLeft", value);
    }

    /**
     * Set an score to GUI.
     * @param value
     */
    private void setUIgameScore(int value){
        getGameState().setValue("actualScore", value);
    }

    /**
     * Set an score to GUI.
     * @param value
     */
    private void setUIlevelScore(int value){
        getGameState().setValue("levelScore", value);
    }

    /**
     * Set an score to GUI.
     *
     */
    private void incUIlevelsPlayed(){
        getGameState().increment("levelsPlayed", +1);
    }

    /**
     * Set an score to GUI.
     * @param value
     */
    private void incUIlevelsLeft(int value){
        getGameState().increment("levelsLeft", value);

    }

    /**
     * Show an effect that disappear in 0.1 seconds. Use when ball hit bricks entities.
     * @param x Position in x axis to show effect.
     * @param y Position in y axis to show efect.
     */
    private void showEffect(double x, double y){
        Entity explosion = newFireEffect(x,y);
        getGameWorld().addEntity(explosion);
        getMasterTimer().runOnceAfter(explosion::removeFromWorld, Duration.seconds(0.3));
    }


    public static void main(String[] args) {
        launch(args);
    }

}

