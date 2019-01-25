package org.openmicroscopy.viewer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ImageViewer extends Application {

  private static final int DISPLAY_WIDTH = 1024;
  private static final int DISPLAY_HEIGHT = 680;

  private TileLoader tileLoader;

  public void initializeTileLoader(String filename) {
    tileLoader = new TileLoader(filename, DISPLAY_WIDTH - 100, DISPLAY_HEIGHT - 100);
  }

  @Override
  public void start(Stage primaryStage) {
    initializeTileLoader(getParameters().getRaw().get(0));

    Label pixelsLabel = new Label("Pixels to move on arrow key press");
    TextField pixelsToMove = new TextField("100");
    Label pixelLocation = new Label("X: Y:");

    ImageView viewport = new ImageView(tileLoader.loadTile());
    viewport.setOnMouseMoved(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        pixelLocation.setText(String.format("X: %d Y: %d",
          (int) event.getX() + tileLoader.getX(),
          (int) event.getY() + tileLoader.getY()));
      }
    });

    StackPane viewportPane = new StackPane();
    viewportPane.setBorder(new Border(new BorderStroke(
      Paint.valueOf("0xff0000"), BorderStrokeStyle.SOLID,
      CornerRadii.EMPTY, BorderStroke.THIN)));
    viewportPane.getChildren().add(viewport);

    GridPane root = new GridPane();
    root.setAlignment(Pos.CENTER);
    root.setHgap(10);
    root.setVgap(10);
    root.setPadding(new Insets(10, 10, 10, 10));

    root.add(pixelsLabel, 0, 0, 1, 1);
    root.add(pixelsToMove, 1, 0, 1, 1);
    root.add(pixelLocation, 0, 1, 2, 1);
    root.add(viewportPane, 0, 2, 2, 1);

    Scene scene = new Scene(root, DISPLAY_WIDTH, DISPLAY_HEIGHT + 100);
    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
          int pix = 0;
          try {
            pix = Integer.parseInt(pixelsToMove.getText());
          }
          catch (NumberFormatException e) { }
          if (pix <= 0) {
            return;
          }

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
