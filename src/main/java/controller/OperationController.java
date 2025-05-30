package controller;

import domain.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

import static util.FXUtil.drawTreeNode;
import static util.Utility.Random;
import static util.Utility.random;

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

    private final BST bTree = new BST();
    private final AVL aTree = new AVL();

    private double zoomActual = 1.0;
    private final double zoomStep = 0.1;

    @FXML
    public void initialize() {
        ToggleGroup group = new ToggleGroup();
        avlRadio.setToggleGroup(group);
        bstRadio.setToggleGroup(group);

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
    }

    @FXML
    public void choiceTree() {
        // Redibuja el árbol seleccionado
        drawBTreeNodes();
    }

    @FXML
    public void addButton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter value to add:");
        dialog.showAndWait().ifPresent(value -> {
            try {
                int val = Integer.parseInt(value);
                if (bstRadio.isSelected()) {
                    bTree.add(val);
                } else if (avlRadio.isSelected()) {
                    aTree.add(val);
                }
                drawBTreeNodes();
            } catch (NumberFormatException e) {
                showAlert("Invalid number!");
            } catch (TreeException e) {
                showAlert("Error!");
            }
        });
    }

    @FXML
    public void removeButton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter value to remove:");
        dialog.showAndWait().ifPresent(value -> {
            try {
                int val = Integer.parseInt(value);
                if (bstRadio.isSelected()) {
                    bTree.remove(val);
                } else if (avlRadio.isSelected()) {
                    aTree.remove(val);
                }
                drawBTreeNodes();
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
                int val = Integer.parseInt(value);
                boolean found = false;
                if (bstRadio.isSelected()) {
                    found = bTree.contains(val);
                } else if (avlRadio.isSelected()) {
                    found = aTree.contains(val);
                }
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
                int val = Integer.parseInt(value);
                int height = -1;
                if (bstRadio.isSelected()) {
                    height = bTree.height(val);
                } else if (avlRadio.isSelected()) {
                    height = aTree.height(val);
                }
                showAlert("Height of node " + val + " is: " + height);
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
            int height = -1;
            if (bstRadio.isSelected()) {
                height = bTree.height();
            } else if (avlRadio.isSelected()) {
                height = aTree.height();
            }
            showAlert("Tree height is: " + height);
        } catch (TreeException e) {
            showAlert("Error: " + e.getMessage());
        }
    }
    @FXML
    public void handleRandomize() throws TreeException {
        randomize(30);

        drawBTreeNodes();
    }

    private void randomize(int n) throws TreeException {
        if (bstRadio.isSelected()) {
            bTree.root = null; // reinicia raíz BST
            for (int i = 0; i < n; i++) {
                int value = Random(0, 50);
                bTree.add(value);
            }
        } else if (avlRadio.isSelected()) {
            aTree.root = null; // reinicia raíz AVL
            for (int i = 0; i < n; i++) {
                int value = random(50) + 1;
                aTree.add(value);
            }
        }
        drawBTreeNodes();
    }

    private void drawBTreeNodes() {
        GraphicsContext treeGraphic = treeCanvas.getGraphicsContext2D();
        treeGraphic.clearRect(0, 0, treeCanvas.getWidth(), treeCanvas.getHeight());

        if (bstRadio.isSelected()) {
            // Dibuja el árbol BST
            if (bTree.root != null) {
                drawTreeNode(treeGraphic, bTree.root, treeCanvas.getWidth() / 2, 40, treeCanvas.getWidth() / 4);
            }
            balanceStatusLabel.setText(bTree.isBalanced() ? "Balanced ✓" : "Not Balanced ✗");
        } else if (avlRadio.isSelected()) {
            // Dibuja el árbol AVL
            if (aTree.root != null) {
                drawTreeNode(treeGraphic, aTree.root, treeCanvas.getWidth() / 2, 40, treeCanvas.getWidth() / 4);
            }
            balanceStatusLabel.setText(aTree.isBalanced() ? "Balanced ✓" : "Not Balanced ✗");
        } else {
            balanceStatusLabel.setText("No tree selected");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
