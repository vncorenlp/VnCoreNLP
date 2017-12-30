package vn.corenlp.wordsegmenter;

/**
 * @author DatQuocNguyen
 */

public class Node {
    private FWObject condition;
    private String conclusion;
    private Node exceptNode;
    private Node ifnotNode;
    private Node fatherNode;
    private int depth;

    public Node(FWObject inCondition, String inConclusion, Node inFatherNode, Node inExceptNode,
                Node inIfnotNode, int inDepth) {
        this.condition = inCondition;
        this.conclusion = inConclusion;
        this.fatherNode = inFatherNode;
        this.exceptNode = inExceptNode;
        this.ifnotNode = inIfnotNode;
        this.depth = inDepth;
    }

    public void setIfnotNode(Node node) {
        this.ifnotNode = node;
    }

    public void setExceptNode(Node node) {
        this.exceptNode = node;
    }

    public void setFatherNode(Node node) {
        this.fatherNode = node;
    }

    public int countNodes() {
        int count = 1;
        if (exceptNode != null) {
            count += exceptNode.countNodes();
        }
        if (ifnotNode != null) {
            count += ifnotNode.countNodes();
        }
        return count;
    }

    public boolean satisfy(FWObject object) {
        boolean check = true;
        for (int i = 0; i < 10; i++) {
            String key = condition.getContext()[i];
            if (key != null) {
                if (!key.equals(object.getContext()[i])) {
                    check = false;
                    break;
                }
            }
        }
        return check;
    }

    public FWObject getCondition() {
        return condition;
    }

    public String getConclusion() {
        return conclusion;
    }

    public Node getExceptNode() {
        return exceptNode;
    }

    public Node getIfnotNode() {
        return ifnotNode;
    }

    public Node getFatherNode() {
        return fatherNode;
    }

    public int getDepth() {
        return depth;
    }
}
