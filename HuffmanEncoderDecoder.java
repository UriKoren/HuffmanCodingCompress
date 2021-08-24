import java.io.*;
import java.nio.file.Files;

public class HuffmanEncoderDecoder implements Compressor {
    HuffmanTree huffman;
    String codeDecode;
    byte[] byteFileGlobal;

    public HuffmanEncoderDecoder() {
    }

    @Override
    public void Compress(String[] input_names, String[] output_names) {
        File inFile = new File(input_names[0]);
        File outFile = new File(output_names[0]);
        byte[] byteFile = getFileInBytes(inFile);
        String code = "";
        String tmpCode = "";
        huffman = new HuffmanTree();
        MyFrame.progressBar.setValue(20);
        for (int i = 0; i < byteFile.length; i++) {
            huffman.insert(byteFile[i], huffman.root);
            code += huffman.path;
            if (!huffman.isExist) {
                tmpCode += Integer.toBinaryString(byteFile[i] - 1);
                if (tmpCode.length() > 8)
                    tmpCode = tmpCode.substring(tmpCode.length() - 8);
                if (tmpCode.length() < 8) {
                    for (int j = 0; j < 8 - tmpCode.length(); j++) {
                        code += "0";
                    }
                }
                code += tmpCode;
                tmpCode = "";
            }
        }
        codeDecode = code;
        byteFileGlobal = byteFile;
        MyFrame.progressBar.setValue(35);
        writeToFile(outFile, code);
        MyFrame.progressBar.setValue(50);
    }

    public byte[] getFileInBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            System.out.println("Can't read file!");
            return null;
        }
    }

    public void writeToFile(File f, String s) {
        try {
            FileOutputStream outputStream = new FileOutputStream(f);
            String r = s + "00000000".substring(s.length() % 8);
            for (int i = 0, len = s.length(); i < len; i += 8) {
                outputStream.write((byte) Integer.parseInt(r.substring(i, i + 8), 2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Decompress(String[] input_names, String[] output_names) {
        MyFrame.progressBar.setValue(70);
        File encoded = new File(input_names[0]);
        File decoded = new File(output_names[0]);
        byte num2 = 0;
        byte[] finalOutput = new byte[byteFileGlobal.length];
        int i = 1;
        String eightbits;
        eightbits = codeDecode.substring(0, 8);
        byte num = (byte) Integer.parseInt(eightbits, 2);
        num += 1;
        finalOutput[0] = num;
        HuffmanTree huffmanDec = new HuffmanTree();
        huffmanDec.insert(num, huffmanDec.root);
        MyFrame.progressBar.setValue(80);
        codeDecode = codeDecode.substring(8);
        String temp = "";
        String temp2 = "";
        while (codeDecode.length() > 0) {
            temp = huffmanDec.traversalTree(huffmanDec.root, codeDecode);
            if (huffmanDec.isNYT) {
                temp2 = codeDecode.substring(temp.length(), 8 + temp.length());
                num2 = (byte) Integer.parseInt(temp2, 2);
                num2 += 1;
                finalOutput[i] = num2;
                codeDecode = codeDecode.substring(8 + temp.length());
                huffmanDec.insert(num2, huffmanDec.root);
            } else {
                finalOutput[i] = huffmanDec.l;
                codeDecode = codeDecode.substring(temp.length());
                huffmanDec.insert(huffmanDec.l, huffmanDec.root);
            }
            i++;
            num2 = 0;
        }
        MyFrame.progressBar.setValue(90);
        try (FileOutputStream stream = new FileOutputStream(decoded)) {
            stream.write(finalOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyFrame.progressBar.setValue(100);
    }

    @Override
    public byte[] CompressWithArray(String[] input_names, String[] output_names) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] DecompressWithArray(String[] input_names, String[] output_names) {
        // TODO Auto-generated method stub
        return null;
    }

}