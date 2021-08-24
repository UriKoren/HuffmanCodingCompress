public class HuffmanTree {
    HuffmanNode root;
    String path = "";
    boolean isExist = false;
    boolean isNYT = false;
    byte l;
    HuffmanNode NYT = null;
    HuffmanNode[] nodesArrAppearances;
    int[] arrIndex = new int[256];

    public HuffmanTree() {
        root = new HuffmanNode();
        root.type = "NYT";
        nodesArrAppearances = new HuffmanNode[513];
        for (int i = 0; i < nodesArrAppearances.length; i++) {
            nodesArrAppearances[i] = new HuffmanNode();
        }
    }

    public void insert(byte letter, HuffmanNode root) {
        String str = "";
        HuffmanNode currentNode = null;
        String strEmpty = "";
        int indexToArr = 0;
        if (letter <= 0) {
            indexToArr = letter * -1;
        } else {
            indexToArr = letter + 128;
        }
        HuffmanNode nodeContainLetter = null;
        HuffmanNode NYT = null;
        this.isExist = false;
        if (arrIndex[indexToArr] == 1) {
            for (int i = 0; i < nodesArrAppearances.length; i++) {
                if (nodesArrAppearances[i].letter == letter && nodesArrAppearances[i].type == "letter") {
                    nodeContainLetter = nodesArrAppearances[i];
                    this.isExist = true;
                    getStrToCurrentLetter(letter, root, strEmpty);
                    currentNode = nodeContainLetter;
                    break;
                }
            }
        } else {
            this.isExist = false;
            if (root.type != "NYT") {
                findNYTNode(root, str);
            } else {
                this.NYT = root;
            }
            NYT = this.NYT;
            arrIndex[indexToArr]++;
            currentNode = addNYT(NYT, letter);
        }


        while (currentNode.parent != null) {
            HuffmanNode maxIndexNode = findMaxIndexInBlock(currentNode);
            if (currentNode.index < maxIndexNode.index) {
                swap(currentNode, maxIndexNode);
            }
            currentNode.appearances++;
            currentNode = currentNode.parent;

        }
        currentNode.appearances++;
    }

    public void getStrToCurrentLetter(byte letter, HuffmanNode root2, String str1) {
        if (root2 == null) {
            return;
        }
        getStrToCurrentLetter(letter, root2.left, str1 + "0");
        getStrToCurrentLetter(letter, root2.right, str1 + "1");
        if (root2.letter == letter && root2.type == "letter") {
            path = str1;
        }
    }
    public void swap(HuffmanNode currentNode, HuffmanNode maxIndexNode) {
        HuffmanNode tmp1 = currentNode;
        HuffmanNode tmp2 = maxIndexNode;
        int tmpIndex = tmp1.index;
        tmp1.index = tmp2.index;
        tmp2.index = tmpIndex;
        HuffmanNode tmp3 = currentNode.parent;
        HuffmanNode tmp4 = maxIndexNode.parent;
        if (tmp3 == tmp4) {
            if (tmp3.left == tmp1) {
                tmp3.left = tmp2;
                tmp3.right = tmp1;
            } else {
                tmp3.right = tmp2;
                tmp3.left = tmp1;
            }
        } else {
            if (tmp3.left == tmp1) {
                tmp3.left = tmp2;
            } else {
                tmp3.right = tmp2;
            }

            if (tmp4.left == tmp2) {
                tmp4.left = tmp1;
            } else {
                tmp4.right = tmp1;
            }
        }
        tmp1.parent = tmp4;
        tmp2.parent = tmp3;
    }

    private HuffmanNode findMaxIndexInBlock(HuffmanNode currentNode) {
        HuffmanNode maxNodeInBlock = currentNode;

        for (int i = 1; i < nodesArrAppearances.length; i++) {
            if (nodesArrAppearances[i].appearances == currentNode.appearances && nodesArrAppearances[i].type != "" && currentNode.parent != nodesArrAppearances[i]) {
                if (nodesArrAppearances[i].index > maxNodeInBlock.index) {
                    maxNodeInBlock = nodesArrAppearances[i];
                }
            }
        }
        return maxNodeInBlock;
    }

    private HuffmanNode addNYT(HuffmanNode NYT, byte letter) {
        HuffmanNode left = new HuffmanNode();
        HuffmanNode right = new HuffmanNode();
        left.type = "NYT";
        left.index = NYT.index - 2;
        left.parent = NYT;
        nodesArrAppearances[left.index] = left;

        right.appearances = 0;
        right.index = NYT.index - 1;
        right.letter = letter;
        right.type = "letter";
        right.parent = NYT;
        nodesArrAppearances[right.index] = right;

        NYT.type = "total";
        NYT.left = left;
        NYT.right = right;
        NYT.appearances = 0;
        return right;
    }

    public void findNYTNode(HuffmanNode root, String str) {

        if (root == null) {
            return;

        }
        findNYTNode(root.left, str + "0");
        findNYTNode(root.right, str + "1");

        if (root.type == "NYT") {
            path = str;
            NYT = root;

        }
    }

    public String traversalTree(HuffmanNode root, String s) {
        int i = 0;
        String temp = "";
        while (root.type != "letter" && root.type != "NYT") {
            if (s.charAt(i) == '1') {
                root = root.right;
                temp += "1";
            } else {
                root = root.left;
                temp += "0";
            }
            i++;
        }
        if (root.type == "NYT") {
            isNYT = true;
        } else {
            l = root.letter;
            isNYT = false;
        }
        return temp;
    }
}