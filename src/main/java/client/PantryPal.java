package client;
import client.RecipeListScene.*;
import client.RecipeDetailScene.*;
import client.CreateRecipeScene.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PantryPal extends Application {
    private Stage stage;
    private Scene recipeListScene;
    private Scene recipeDetailScene;
    private Scene CreateRecipeScene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        RecipeListView recipeListView = new RecipeListView();
        RecipeDetailView recipeDetailView = new RecipeDetailView();
        CreateRecipeView createRecipeView = new CreateRecipeView();
        Model model = new Model();


        this.recipeListScene = new Scene(recipeListView.getBorderPane(), 500, 500);
        this.recipeDetailScene = new Scene(recipeDetailView.getBorderPane(), 500, 500);
        this.CreateRecipeScene = new Scene(createRecipeView.getBorderPane(), 500, 500);

        AppController controller = new AppController(recipeListView, recipeListScene, 
                                                    recipeDetailView, recipeDetailScene, 
                                                    createRecipeView, CreateRecipeScene, 
                                                    stage, model);
        primaryStage.setScene(recipeListScene);
        primaryStage.setTitle("Your Recipes");
        primaryStage.show();
    }
}