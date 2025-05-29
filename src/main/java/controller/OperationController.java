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
    @FXML private ScrollPane scrollPane;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button containsButton;
    @FXML private Button heightButton;
    @FXML private Button treeHeightButton;
    @FXML private Button randomizeButton;

    private ToggleGroup treeTypeGroup;

    private BST bstTree = new BST();
    private AVL avlTree = new AVL();
    private Tree currentTree;

    private double zoomActual = 1.0;
    private final double zoomStep = 0.1;

    @FXML
    public void initialize() {
        // Grupo manual de RadioButtons
        treeTypeGroup = new ToggleGroup();
        bstRadio.setToggleGroup(treeTypeGroup);
        avlRadio.setToggleGroup(treeTypeGroup);

        // Valor inicial
        currentTree = bstTree;
        bstRadio.setSelected(true);


        treeCanvas.setOnScroll(event -> {
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
        currentTree = bstRadio.isSelected() ? bstTree : avlTree;
        drawTree();
    }

    @FXML
    public void addButton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter value to add:");
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
    public void removeButton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter value to remove:");
        dialog.showAndWait().ifPresent(value -> {
            try {
                currentTree.remove(Integer.parseInt(value));
                drawTree();
            } catch (NumberFormatException e) {
                showAlert("Invalid number!");
            } catch (TreeException e) {
                showAlert("Error: " + e.getMessage());
            }
        });
    }

    @FXML
    public void containsButton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter value to search:");
        dialog.showAndWait().ifPresent(value -> {
            try {
                boolean found = currentTree.contains(Integer.parseInt(value));
                showAlert("Value " + (found ? "found." : "not found."));
            } catch (NumberFormatException e) {
                showAlert("Invalid number!");
            } catch (TreeException e) {
                showAlert("Error: " + e.getMessage());
            }
        });
    }

    @FXML
    public void heightButton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter node value:");
        dialog.showAndWait().ifPresent(value -> {
            try {
                int height = currentTree.height(Integer.parseInt(value));
                showAlert("Height of node " + value + " is: " + height);
            } catch (NumberFormatException e) {
                showAlert("Invalid input!");
            } catch (TreeException e) {
                showAlert("Error: " + e.getMessage());
            }
        });
    }

    @FXML
    public void treeHeightButton() {
        try {
            int height = currentTree.height();
            showAlert("Tree height is: " + height);
        } catch (TreeException e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    @FXML
    public void handleRandomize() {
        currentTree.clear();
        for (int i = 0; i < 10; i++) {
            currentTree.add((int) (Math.random() * 100));
        }
        drawTree();
    }

    private void drawTree() {
        GraphicsContext gc = treeCanvas.getGraphicsContext2D();

        gc.clearRect(0, 0, treeCanvas.getWidth(), treeCanvas.getHeight());

        double canvasWidth = 600;
        double canvasHeight = 600;
        treeCanvas.setWidth(canvasWidth);
        treeCanvas.setHeight(canvasHeight);

        if (currentTree.getRoot() != null) {
            FXUtil.drawBTreeNodes(gc, (BTreeNode) currentTree.getRoot(), canvasWidth / 2, 35, canvasWidth / 4);
        }

        balanceStatusLabel.setText(currentTree.isBalanced() ? "Balanced ✓" : "Not Balanced ✗");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
