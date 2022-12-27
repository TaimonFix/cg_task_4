import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUIForm extends JFrame {
    private JPanel panelMain;
    private JPanel panelOriginalImage;
    private JPanel panelCompressedImage;
    private JButton buttonOutputImage;
    private JButton buttonCompress;
    private JButton buttonInputImage;
    private JLabel labelSizeOriginal;
    private JLabel labelSizeCompressed;

    BufferedImage originalImage;
    BufferedImage compressImage;

    File inputFile;
    File outputFile;
    String stream;

    public GUIForm() {
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.pack();
        this.setLocation(10,10);
        this.setSize(1300, 600);

        buttonInputImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChoose = new JFileChooser();
                fileChoose.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int ret = fileChoose.showOpenDialog(GUIForm.this);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    inputFile = fileChoose.getSelectedFile();
                    ((ImagePanel) panelOriginalImage).setFile(inputFile);
                    panelOriginalImage.repaint();
                }
                Double size = (double) (inputFile.length() / 1024);
                labelSizeOriginal.setText(String.format("%f Kb", size));
            }
        });


        buttonCompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputFile != null) {
                    try {
                        Compression compression = new Compression();
                        originalImage = ImageIO.read(inputFile);
                        compression.setOriginalImage(originalImage);
                        compression.compress();
                        compressImage = compression.getCompressedImage();
                        ((ImagePanel) panelCompressedImage).setBufferedImage(compressImage);
                        panelCompressedImage.repaint();
                        stream = inputFile.getName().substring(inputFile.getName().indexOf(".") + 1);

                        ImageIO.write(compressImage, stream, new File("compressImage." + stream) );
                        outputFile = new File("compressImage." + stream);

                        Double size = (double) (outputFile.length() / 1024);
                        labelSizeCompressed.setText(String.format("%f Kb", size));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        buttonOutputImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputFile = new File("ImageCompress." + stream);
                try {
                    ImageIO.write(compressImage, stream, outputFile);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        });
    }

    private void createUIComponents() {
        panelOriginalImage = new ImagePanel();
        panelCompressedImage = new ImagePanel();
    }
}
