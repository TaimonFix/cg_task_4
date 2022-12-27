import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImagePanel extends JPanel {

    BufferedImage bufferedImage;

    public ImagePanel() {
    }

    public ImagePanel(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (bufferedImage != null) {
            g.drawImage(bufferedImage, 10, 50, null);
        }
    }

    public void setFile(File file) {
        try {
            this.bufferedImage = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
