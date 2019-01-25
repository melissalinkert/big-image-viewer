package viewer;

import java.io.IOException;
import loci.formats.FormatException;
import loci.formats.gui.BufferedImageReader;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class TileLoader {

  private BufferedImageReader reader;
  private int x, y;
  private int sizeX, sizeY;
  private int tileX, tileY;

  public TileLoader(String filename, int x, int y) {
    reader = new BufferedImageReader();
    try {
      reader.setId(filename);
      reader.setSeries(0);
      sizeX = reader.getSizeX();
      sizeY = reader.getSizeY();
    }
    catch (FormatException | IOException e) {
      sizeX = 0;
      sizeY = 0;
    }
    this.x = 0;
    this.y = 0;
    tileX = (int) Math.min(x, sizeX);
    tileY = (int) Math.min(y, sizeY);
  }

  public void adjustX(int xIncrement) {
    setX(x + xIncrement);
  }

  public void adjustY(int yIncrement) {
    setY(y + yIncrement);
  }

  public Image loadTile() {
    try {
      return SwingFXUtils.toFXImage(reader.openImage(0, x, y, tileX, tileY), null);
    }
    catch (FormatException | IOException e) {
      return null;
    }
  }

  public void setX(int newX) {
    if (newX >= 0 && newX + tileX <= sizeX) {
      x = newX;
    }
  }

  public void setY(int newY) {
    if (newY >= 0 && newY + tileY <= sizeY) {
      y = newY;
    }
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

}
