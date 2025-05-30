package controller;

import domain.AVL;
import domain.BST;
import domain.Tree;
import domain.TreeException;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import util.FXUtil;
import util.Utility;

public class GraphicController {
    @FXML
    private Canvas treeCanvas;
    @FXML
    private RadioButton btBST;
    @FXML
    private Label label;
    @FXML
    private ToggleGroup group;
    @FXML
    private RadioButton btAVL;
    private final BST bTree = new BST();
    private final AVL aTree = new AVL();
    private Alert alert;
    private Alert infoAlert;

    @FXML
    public void initialize() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Graphic BST/AVL Tree -  Error");
        infoAlert = FXUtil.informationDialog("Graphic BST/AVL Tree -  Information");
    }

    @FXML
    public void avlOnAction() {
        label.setText("AVL Tree");
        try {
            drawBTreeNodes(aTree);
        } catch (TreeException e) {
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    public void bstOnAction() {
        label.setText("BST Tree");
        try {
            drawBTreeNodes(bTree);
        } catch (TreeException e) {
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    public void handleRandomize() {
        if (group.getSelectedToggle() != null) {
            try {
                if (btAVL.isSelected()) {
                    aTree.clear();
                    for (int i = 0; i < 30; i++)
                        aTree.add(Utility.Random(0, 50));
                    drawBTreeNodes(aTree);
                } else {
                    bTree.clear();
                    for (int i = 0; i < 30; i++)
                        bTree.add(Utility.Random(0, 50));
                    drawBTreeNodes(bTree);
                }
                label.setText("Randomize Tree");
            } catch (TreeException e) {
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            alert.setContentText("Seleccione el tipo de árbol.");
            alert.show();
        }
    }

    @FXML
    public void handleLevels() {
        Tree tree = getTree();
        if (tree != null) {
            try {
                GraphicsContext treeGraphic = treeCanvas.getGraphicsContext2D();
                int height = tree.height()+1;
                FXUtil.drawLevelLines(treeGraphic, height, 70, 60, treeCanvas.getWidth());
            }catch (TreeException e) {
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            alert.setContentText("Seleccione el tipo de árbol.");
            alert.show();
        }
    }

    @FXML
    public void handleIsBalanced() {
        Tree tree = getTree();
        if (tree != null)
            label.setText(tree.isBalanced()
                    ? "Is Balanced"
                    : "Not Balanced"
            );
        else {
            alert.setContentText("Seleccione el tipo de árbol.");
            alert.show();
        }
    }

    @FXML
    public void handleTourInfo() {
        Tree tree = getTree();
        if (tree != null) {
            infoAlert.setContentText("Tour Info:\n" + tree);
            infoAlert.show();
        } else {
            alert.setContentText("Seleccione el tipo de árbol.");
            alert.show();
        }
    }

    private void drawBTreeNodes(Tree tree) throws TreeException {
        // graphicContext:forma de "dibujar" es como un objeto
        GraphicsContext treeGraphic = treeCanvas.getGraphicsContext2D();
        if (tree.isEmpty())
            treeGraphic.clearRect(0, 0, treeCanvas.getWidth(), treeCanvas.getHeight());
        else { //ajustar el tamaño del canvas a la altura del arbol
            int height = tree.height()+1;
            treeCanvas.setHeight(Math.max(tree.height()*80, 400));
            treeCanvas.setWidth(Math.max(Math.pow(2, height)*10,785));
            treeGraphic.clearRect(0, 0, treeCanvas.getWidth(), treeCanvas.getHeight());
            FXUtil.drawTreeNode(treeGraphic, tree.getRoot(), treeCanvas.getWidth()/2, 35, treeCanvas.getWidth()/4);
        }
    }

    private Tree getTree(){
        if (btAVL.isSelected())
            return aTree;
        if (btBST.isSelected())
            return bTree;
        return null;
    }
}
