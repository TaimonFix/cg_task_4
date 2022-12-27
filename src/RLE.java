import java.io.ByteArrayOutputStream;

public class RLE {
    public RLE() {
    }

    byte[] originalImage;
    byte[] compressedImage;

    public void setOriginalImage(byte[] originalImage) {
        this.originalImage = originalImage;
    }

    public byte[] getCompressImage() {
        return compressedImage;
    }

    public void decompressImage() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        byte value, count;
        int n = originalImage.length;

        for (int i = 0; i < n; i++) {
            count = (byte) (originalImage[i] & (0x40 - 1));

            value = originalImage[++i];
            for (int j = 0; j < count; j++) {
                stream.write(value);
            }
            compressedImage = stream.toByteArray();

        }
    }
    public void compressImage() {

        byte count = 1, prev = originalImage[1], current;
        int n = originalImage.length;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        for (int i = 1; i < n; i++) {
            current = originalImage[i];
            if(current == prev && count < 63) {
                count++;
            } else {
                count = (byte) (count | 0xc0);

                stream.write(count);
                stream.write(prev);

                count = 1;
            }
            prev = current;
        }
        count = (byte) (count | 0xc0);

        stream.write(count);
        stream.write(prev);

        originalImage = stream.toByteArray();
    }


}
