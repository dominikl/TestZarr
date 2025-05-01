package biongff.testzarr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

public class Utils {

    /**
     * Generates a grayscale image with the specified text written at a random position.
     * The background is black (0) and the text is white (255).
     *
     * @param width The width of the image
     * @param height The height of the image
     * @param text The text to write on the image
     * @return byte array containing the grayscale image data
     */
    public static byte[] generateGreyscaleImageWithText(int width, int height, String text) {
        // Create buffered image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = image.createGraphics();

        // Set rendering hints for better text quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        // Fill background with black
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Set up text properties
        g2d.setColor(Color.BLACK);
        int fontSize = Math.min(width, height) / 20; // Scale font size based on image dimensions
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));

        // Get text dimensions
        int textWidth = g2d.getFontMetrics().stringWidth(text);
        int textHeight = g2d.getFontMetrics().getHeight();

        // Generate random position for text, ensuring it fits within the image
        Random random = new Random();
        int x = random.nextInt(width - textWidth);
        int y = random.nextInt(height - textHeight) + g2d.getFontMetrics().getAscent();

        // Draw the text
        g2d.drawString(text, x, y);
        g2d.dispose();

        // Get the underlying byte array
        byte[] imageData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        return imageData;
    }

    /**
     * Converts a byte array of grayscale image data back to a BufferedImage.
     * 
     * @param data The byte array containing grayscale image data
     * @param width The width of the image
     * @param height The height of the image
     * @return A BufferedImage representing the grayscale data
     */
    public static BufferedImage byteArrayToImage(byte[] data, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        image.getRaster().setDataElements(0, 0, width, height, data);
        return image;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create test image
            int width = 512;
            int height = 256;
            byte[] imageData = generateGreyscaleImageWithText(width, height, "Test Image");
            BufferedImage image = byteArrayToImage(imageData, width, height);

            // Create display panel
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, this);
                }
                
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(width, height);
                }
            };

            // Create and show frame
            JFrame frame = new JFrame("Image Preview");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
