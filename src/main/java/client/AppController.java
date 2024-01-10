package client;

import java.util.List;
import java.util.ArrayList;
import client.CreateRecipeScene.*;
import client.RecipeDetailScene.*;
import client.RecipeListScene.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import javax.sound.sampled.*;

// Controller class for managing interactions between views and models in the app
public class AppController {
    // Main stage of app
    private Stage stage;
    // All other references in views
    private RecipeListView recipeListView; // => Rlv
    private VBox recipeListContainer;
    private Scene recipeListScene;
    private RecipeDetailView recipeDetailView; // => Rdv
    private Scene recipeDetailScene;
    private CreateRecipeView createRecipeView; // => Crv
    private Scene createRecipeScene;
    
    private static final Double windowWidth = 500D;
    private static final Double windowHeight = 500D;

    public AppController(RecipeListView recipeListView, RecipeDetailView recipeDetailView, CreateRecipeView createRecipeView, Stage stage) {
        this.recipeListView = recipeListView;
        this.recipeListContainer = this.recipeListView.getRecipeListContainer();
        this.recipeDetailView = recipeDetailView;
        this.createRecipeView = createRecipeView;

        this.recipeListScene = new Scene(new ScrollPane(recipeListView.getBorderPane()), windowWidth, windowHeight);
        this.recipeDetailScene = new Scene(new ScrollPane(recipeDetailView.getBorderPane()), windowWidth, windowHeight);
        this.createRecipeScene = new Scene(new ScrollPane(createRecipeView.getBorderPane()), windowWidth, windowHeight);
        // this.recipeListScene = new Scene(recipeListView.getBorderPane(), windowWidth, windowHeight);
        // this.recipeDetailScene = new Scene(recipeDetailView.getBorderPane(), windowWidth, windowHeight);
        // this.createRecipeScene = new Scene(createRecipeView.getBorderPane(), windowWidth, windowHeight);
        
        this.stage = stage;

        init();
       }

    public AppController() {
        // constructor for testing
        this.recipeListView = new RecipeListView(true);
        this.recipeListContainer = recipeListView.getRecipeListContainer();
    }

    private void init() {
        changeToRecipeListScene();
    }

    public void initRecipeList(List<Recipe> recipeList) {
        for (Recipe recipe : recipeList) {
            RecipeListItem recipeListItem = new RecipeListItem(recipe);
            recipeListItem.setOnMouseClicked(e -> {
                // the next time to render the detail of this recipe, this recipe would be existing
                this.changeToRecipeDetailScene(recipe, false);
            });
            recipeListContainer.getChildren().add(0, recipeListItem);
        }
    }

    public List<Recipe> getRecipeList() {
        // use this function to test
        ObservableList<Node> recipeItemsList = this.recipeListContainer.getChildren();
        List<Recipe> recipeList = new ArrayList<>();
        for (Node c : recipeItemsList) {
            if (c instanceof RecipeListItem) {
                recipeList.add(((RecipeListItem)c).getRecipe());
            }
        }
        return recipeList;
    }

    public void changeToCreateRecipeScene() {
        this.stage.setScene(createRecipeScene);
        this.stage.setTitle("Create New Recipe");
    }

    // Removes recipe from recipe list
    public void removeRecipeFromRecipeList(Recipe recipe) {
        int indexOfRecipeToRemove = 0;
        ObservableList<Node> recipeListItems = recipeListContainer.getChildren();
        for (; indexOfRecipeToRemove < recipeListItems.size(); indexOfRecipeToRemove++) {
            if (recipeListItems.get(indexOfRecipeToRemove) instanceof RecipeListItem) {
                if (((RecipeListItem)recipeListItems.get(indexOfRecipeToRemove)).getRecipe() == recipe) break;
            }
        }
        recipeListContainer.getChildren().remove(indexOfRecipeToRemove);
        System.out.println("After deleting a recipe, the size of the recipe list is now " + recipeListContainer.getChildren().size());
    }

    // Changes scene to recipe list view scence
    public void changeToRecipeListScene() {
        // Sanity check
        if (stage != null && recipeListScene != null) {
            // Set scene
            stage.setScene(recipeListScene);
            stage.setTitle("Your Recipes");
        }
            
    }

    // Changes scene to recipe detail view based on the recipe selected
    public void changeToRecipeDetailScene(Recipe recipe, boolean isNewRecipe) {
        // Check if recipe is new
        if (isNewRecipe) {
            this.recipeDetailView.renderNewRecipe(recipe);
        } else { // render existing recipe
            this.recipeDetailView.renderExistingRecipe(recipe);
        }
        this.stage.setScene(recipeDetailScene);
        this.stage.setTitle(recipe.getTitle());
    }

    // Adds new recipe to recipe list view
    public void addNewRecipeToList(Recipe recipe) {
        RecipeListItem recipeListItem = new RecipeListItem(recipe);
        recipeListItem.setOnMouseClicked(e -> {
            // the next time to render the detail of this recipe, this recipe would be existing
            this.changeToRecipeDetailScene(recipe, false);
        });
        recipeListContainer.getChildren().add(0, recipeListItem);
        changeToRecipeListScene();
    }

    public Stage getStage() {
        // for testing purposes
        return stage;
    }
}


/*
generated recipes are automatically in edit mode
Recipe original: null
Recipe updating: {values from GPT}

In DetailView: 
boolean isNewRecipe;
boolean inEditMode; init false for existing recipe, true for new recipe
    if true: edit/save button says save, recipe body is TextField
    if false: edit/save button says edit, recipe body is Text

Back button actions:
    if the Recipe original is not null:
        simply exit to list view
    if the Recipe original is null:
        this means the current recipe is newly generated and is placed on the recipe list
        execute delete logic on that recipe
        exit to list view
Edit button actions:
    on

https://piazza.com/class/lmy9axhgowe53s/post/174
10. Should the user be able to edit the generated recipe, or does editing only entail making changes to their prompt?
The user should be able to edit the generated recipe. They won’t be able to edit their prompt (meal type or ingredients).

take the user from add recipe straight to recipe detail page

From e2e story
After editng and clicking save, the app stays in the detail view
this means: the app stays in detail view unless user click back or delete

Caitlin clicks the "Save" button to add it to her collection. 
The new recipe now takes the top spot in her list
This means clicking save after new recipe generation exits to the list view

https://piazza.com/class/lmy9axhgowe53s/post/164
1. Once Caitlyn makes a recipe, what happens if she does not want to save it?
She can just exit out of the detailed view.

2.If Caitlyn is editing a recipe and doesn’t want to save her changes, what should she do?
She can just exit out of the edit view.

3. Before recipes are deleted, should there be a confirmation message/interface for the user?
Sure, that would be a good way to handle deletion in case the user accidentally clicks on delete.
*/