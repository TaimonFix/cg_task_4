import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

public class Compression {
    public Compression() {
    }

    public BufferedImage originalImage, compressedImage;

    public void setOriginalImage(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }

    public BufferedImage getCompressedImage() {
        return compressedImage;
    }

    public void compress() {
        Raster raster = originalImage.getRaster();
        DataBufferByte bufferByte = (DataBufferByte) raster.getDataBuffer();
        byte[] source = bufferByte.getData();

        RLE rle = new RLE();
        rle.setOriginalImage(source);
        rle.compressImage();
        rle.decompressImage();

        byte[] bytes = rle.getCompressImage();

        compressedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
        DataBufferByte bufferByte1 = new DataBufferByte(bytes, bytes.length);
        Raster raster1 = Raster.createRaster(raster.getSampleModel(), bufferByte1, new Point());
        compressedImage.setData(raster1);
    }
}
