import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class MyFrame extends JFrame implements ActionListener {
    HuffmanEncoderDecoder huffman = new HuffmanEncoderDecoder();
    String[] input = new String[1];
    String[] output = new String[1];
    String[] newOut = new String[1];
    String path;
    JButton choosefBtn = new JButton("Choose File");
    JButton compressBtn = new JButton("Compress");
    JButton decompressBtn = new JButton("Decompress");
    JButton exitBtn = new JButton("Exit");

    JLabel compressedLabel = new JLabel("Compressed successfully!", JLabel.CENTER);
    JLabel decompressedLabel = new JLabel("Decompressed successfully!", JLabel.CENTER);
    JLabel waitLabel = new JLabel("Wait until finished", JLabel.CENTER);
    JLabel compressingLabel = new JLabel("Compressing...", JLabel.CENTER);
    JLabel decompressingLabel = new JLabel("Decompressing...", JLabel.CENTER);

    Dimension d = new Dimension(300, 210);
    boolean ischoosen = false;
    boolean isStarted = false;
    boolean isFinishedComp = false;
    boolean isFinishedDecomp = false;
    public static JProgressBar progressBar = new JProgressBar(0, 100);

    public static File file;
    public MyFrame() {
        choosefBtn.addActionListener(this);
        choosefBtn.setPreferredSize(d);
        choosefBtn.setFont(new Font("Aerial", Font.PLAIN, 40));

        compressBtn.addActionListener(this);
        compressBtn.setPreferredSize(d);
        compressBtn.setFont(new Font("Aerial", Font.PLAIN, 40));

        decompressBtn.addActionListener(this);
        decompressBtn.setPreferredSize(d);
        decompressBtn.setFont(new Font("Aerial", Font.PLAIN, 40));

        exitBtn.addActionListener(this);
        exitBtn.setPreferredSize(d);
        exitBtn.setFont(new Font("Aerial", Font.PLAIN, 40));

        this.setTitle("HUFFMAN");
        this.getContentPane().setBackground(Color.decode("#23465d"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(3500, 3500);
        this.setVisible(true);

        compressedLabel.setVisible(false);
        compressedLabel.setFont(new Font("Aerial", Font.PLAIN, 40));
        compressedLabel.setForeground(Color.white);
        compressedLabel.setOpaque(true);
        compressedLabel.setBackground(Color.BLUE);

        decompressedLabel.setVisible(false);
        decompressedLabel.setFont(new Font("Aerial", Font.PLAIN, 40));
        decompressedLabel.setForeground(Color.white);
        decompressedLabel.setOpaque(true);
        decompressedLabel.setBackground(Color.BLUE.darker().darker());


        waitLabel.setVisible(false);
        waitLabel.setFont(new Font("Aerial", Font.PLAIN, 40));
        waitLabel.setForeground(Color.RED);


        compressingLabel.setVisible(false);
        compressingLabel.setFont(new Font("Aerial", Font.PLAIN, 40));
        compressingLabel.setForeground(Color.white);
        compressingLabel.setOpaque(true);
        compressingLabel.setBackground(Color.CYAN.darker());

        decompressingLabel.setVisible(false);
        decompressingLabel.setFont(new Font("Aerial", Font.PLAIN, 40));
        decompressingLabel.setForeground(Color.white);
        decompressingLabel.setOpaque(true);
        decompressingLabel.setBackground(Color.blue.darker());


        progressBar.setBackground(Color.DARK_GRAY);

        this.add(choosefBtn);
        this.add(compressBtn);
        this.add(decompressBtn);
        this.add(exitBtn);

        this.add(compressingLabel);
        this.add(compressedLabel);
        this.add(decompressingLabel);
        this.add(decompressedLabel);
        this.add(waitLabel);
        this.add(progressBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == choosefBtn) {
            if (isStarted && !isFinishedDecomp) {
                waitLabel.setVisible(true);
            } else {
                JFileChooser fileChooser = new JFileChooser();
                int response = fileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    ischoosen = true;
                    this.compressingLabel.setVisible(false);
                    this.compressedLabel.setVisible(false);
                    this.decompressingLabel.setVisible(false);
                    this.decompressedLabel.setVisible(false);
                }
            }
        } else if (e.getSource() == compressBtn) {
            if ((!isStarted || isFinishedDecomp) && ischoosen) {
                input[0] = this.file.getAbsolutePath();
                path = input[0];
                String extension="";
                int i = path.lastIndexOf('.');
                if (i > 0) { extension = path.substring(i+1);
                    path=path.substring(0,i);
                }
                output[0] =path + "Encoded" + "." +extension;
                this.compressingLabel.setVisible(true);
                huffman.Compress(input, output);
                this.compressedLabel.setVisible(true);
                isFinishedComp = true;
                isStarted = true;
            }
        } else if (e.getSource() == decompressBtn) {
            if (isFinishedComp && isStarted) {
               String path2=input[0];
                String extension="";
                int i = path2.lastIndexOf('.');
                if (i > 0) { extension = path2.substring(i+1);
                    path2=path2.substring(0,i);
                }
                newOut[0] = path + "Decoded" + "." + extension;
                this.waitLabel.setVisible(false);
                this.decompressingLabel.setVisible(true);
                huffman.Decompress(output, newOut);
                isFinishedDecomp = true;
                isStarted = false;
                ischoosen = false;
                this.decompressedLabel.setVisible(true);
            }
        } else if (e.getSource() == exitBtn) {
            System.exit(1);
        }
    }
}



