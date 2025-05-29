package controller;

import domain.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import util.FXUtil;


public class OperationController {

    @FXML private Canvas treeCanvas;
    @FXML private RadioButton bstRadio;
    @FXML private RadioButton avlRadio;
    @FXML private Label balanceStatusLabel;
    @FXML private ToggleGroup treeTypeGroup;

    private BST bstTree = new BST();
    private AVL avlTree = new AVL();
    private Tree currentTree;

    private double zoomActual = 1.0;
    private double zoomStep = 0.1;// es el minimo antes que 0

    @FXML
    private ScrollPane scrollPane;
    @FXML
    public void initialize() {
        // SE INICIALIZA EN BST pero puedo variarlo con avl
        currentTree = new BST();
        bstRadio.setSelected(true);


        scrollPane.setOnScroll(event -> {
            if (event.isControlDown()) {
                if (event.getDeltaY() > 0) {
                    zoomActual += zoomStep;
                } else {
                    zoomActual -= zoomStep;
                }
                if (zoomActual < 0.1) zoomActual = 0.1;

                treeCanvas.setScaleX(zoomActual);
                treeCanvas.setScaleY(zoomActual);

                scrollPane.layout();
                event.consume();
            }
        });

        drawTree();
    }

    @FXML
    public void choiceTree() {
        if (bstRadio.isSelected()) {
            currentTree = bstTree;
        } else {
            currentTree = avlTree;
        }
        drawTree();
    }

    @FXML
    public void addBotton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Please enter a value:");
        dialog.showAndWait().ifPresent(value -> {
            try {
                currentTree.add(Integer.parseInt(value));
                drawTree();
            } catch (NumberFormatException e) {
                showAlert("Invalid number!");
            }
        });
    }

    @FXML
    public void removeBotton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter value to remove:");
        dialog.showAndWait().ifPresent(value -> {
            try {
                try {
                    currentTree.remove(Integer.parseInt(value));
                } catch (TreeException e) {
                    throw new RuntimeException(e);
                }
                drawTree();
            } catch (NumberFormatException e) {
                showAlert("Invalid number!");
            }
        });
    }

    @FXML
    public void containsBotton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter value to search:");
        dialog.showAndWait().ifPresent(value -> {
            try {
                boolean found = currentTree.contains(Integer.parseInt(value));
                showAlert("Value " + (found ? "found." : "not found."));
            } catch (NumberFormatException e) {
                showAlert("Invalid number!");
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void heightBotton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter node value:");
        dialog.showAndWait().ifPresent(value -> {
            try {
                int height = currentTree.height(Integer.parseInt(value));
                showAlert("Height of node " + value + " is: " + height);
            } catch (NumberFormatException e) {
                showAlert("Invalid input!");
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void treeHeightBotton() throws TreeException {
        int height = currentTree.height();
        showAlert("Tree height is: " + height);
    }

    @FXML
    public void handleRandomize() {
        currentTree.clear();
        for (int i = 0; i < 7; i++) {
            currentTree.add((int) (Math.random() * 100));
        }
        drawTree();
    }

    private void drawTree() {
        GraphicsContext gc = treeCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, treeCanvas.getWidth(), treeCanvas.getHeight());

        double canvasWidth = 800;
        double canvasHeight = 600;
        treeCanvas.setWidth(canvasWidth);
        treeCanvas.setHeight(canvasHeight);

        FXUtil.drawBTreeNodes(gc, (BTreeNode) currentTree.getRoot(), canvasWidth / 2, 40, canvasWidth / 4);
        balanceStatusLabel.setText(currentTree.isBalanced() ? "Balanced ✓" : "Not Balanced ✗");
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
