package gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import facade.HomeworkTwoFacade;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import gui.control.BallComponent;
import gui.control.BarComponent;
import gui.control.BrickComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.brick.Brick;
import logic.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gui.BreakoutFactory.*;


public class BreakoutGameApp extends GameApplication {
    private HomeworkTwoFacade hw2;
    private List<Entity> brickEntityList;

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
    protected void initGame() {
        Entity bar = newBar(300, 700,BreakoutType.BAR);
        getGameWorld().addEntity(bar);
        Entity ball = newBall(bar.getCenter().getX(),bar.getY()-10);
        getGameWorld().addEntity(ball);
        Entity wall = newWall();
        getGameWorld().addEntity(wall);

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
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left Bar") {
            @Override
            protected void onAction() {
                getBarComponent().left();
                getGameState().increment("pixelsMoved", -5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Impulse Ball") {
            @Override
            protected void onActionBegin() {
                if(isPresentBall() && !hw2.isGameOver()) {
                    impulseBall(getGameWorld().getSingleton(BreakoutType.BALL).get());
                }
            }
        }, KeyCode.SPACE);

        input.addAction(new UserAction("New Level") {
            @Override
            protected void onActionBegin() {
                if(hw2.getCurrentLevel().isPlayableLevel()){
                    removeEntitiesFromGameWorld();
                    brickEntityList = new ArrayList<>();
                }
                Level level = hw2.newLevelWithBricksFull("Level",30,0.5,0,1);
                hw2.setCurrentLevel(level);
                addHitteablesToEntities();
                addEntitiesToGameWorld();
                //setUInumberOfBalls(hw2.getBallsLeft());
                //System.out.println("Mesa por defecto seteada");
            }

        }, KeyCode.N);
    }

    @Override
    protected void initPhysics() {
        //getPhysicsWorld().setGravity(0, 0);

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
            }

            @Override
            protected void onCollision(Entity ball, Entity brick) {
                brick.getComponent(BrickComponent.class).hitBrick();
                if (brick.getComponent(BrickComponent.class).isDestroyed()) {
                    brick.removeFromWorld();
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
                }
                if(hw2.isGameOver()){
                    Font largeFont = new Font("gameFont",40);
                    Text gameOverText = getUIFactory().newText("GAME OVER");
                    gameOverText.setTranslateX(30);
                    gameOverText.setTranslateY(200);
                    gameOverText.setFont(largeFont);
                    //panel.getChildren().add(gameOverText);
                }
                if(!isPresentBall() && !hw2.isGameOver()) {
                    Entity newBall = newBall(getBarEntity().getCenter().getX(), getBarEntity().getY()-10);
                    getGameWorld().addEntity(newBall);
                }


            }
        });
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        textPixels.textProperty().bind(getGameState().intProperty("pixelsMoved").asString());

        getGameScene().addUINode(textPixels); // add to the scene graph
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved",0);
    }

    private void addHitteablesToEntities(){
        List<Brick> brickList = hw2.getBricks();
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
            getGameWorld().addEntity(entity);
        }
    }

    private void removeEntitiesFromGameWorld(){
        for(Entity entity : brickEntityList){
            entity.removeFromWorld();
        }
    }

    private void impulseBall(Entity ball){
        PhysicsComponent physicsComponent = ball.getComponent(PhysicsComponent.class);
        //physicsComponent.setOnPhysicsInitialized(() -> physicsComponent.setLinearVelocity(-50, -1000));
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
        getGameState().setValue("actualBalls", value);
    }

    /**
     * Set an score to GUI.
     * @param value
     */
    private void setUIgameScore(int value){
        getGameState().setValue("gameScore", value);
    }




    public static void main(String[] args) {
        launch(args);
    }

}

