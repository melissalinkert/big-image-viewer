package org.openmicroscopy.viewer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ImageViewer extends Application {

  private static final int DISPLAY_WIDTH = 1024;
  private static final int DISPLAY_HEIGHT = 680;

  private TileLoader tileLoader;

  public void initializeTileLoader(String filename) {
    tileLoader = new TileLoader(filename, DISPLAY_WIDTH, DISPLAY_HEIGHT);
  }

  @Override
  public void start(Stage primaryStage) {
    initializeTileLoader(getParameters().getRaw().get(0));

    Label pixelsLabel = new Label("Pixels to move on arrow key press");
    TextField pixelsToMove = new TextField("100");

    ImageView viewport = new ImageView(tileLoader.loadTile());

    AnchorPane root = new AnchorPane();
    AnchorPane.setTopAnchor(pixelsLabel, 10.0);
    AnchorPane.setLeftAnchor(pixelsLabel, 100.0);
    AnchorPane.setTopAnchor(pixelsToMove, 10.0);
    AnchorPane.setRightAnchor(pixelsToMove, 100.0);
    AnchorPane.setBottomAnchor(viewport, 10.0);
    AnchorPane.setLeftAnchor(viewport, 10.0);
    AnchorPane.setRightAnchor(viewport, 10.0);
    root.getChildren().addAll(pixelsLabel, pixelsToMove, viewport);

    Scene scene = new Scene(root, DISPLAY_WIDTH, DISPLAY_HEIGHT + 100);
    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
          int pix = Integer.parseInt(pixelsToMove.getText());
          KeyCode code = event.getCode();
          if (code == KeyCode.LEFT) {
            tileLoader.adjustX(-1 * pix);
          }
          else if (code == KeyCode.RIGHT) {
            tileLoader.adjustX(pix);
          }
          else if (code == KeyCode.UP) {
            tileLoader.adjustY(-1 * pix);
          }
          else if (code == KeyCode.DOWN) {
            tileLoader.adjustY(pix);
          }
        }
        viewport.setImage(tileLoader.loadTile());
      }
    });

    primaryStage.setTitle("Big Image Viewer");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
