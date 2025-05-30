package util;

import domain.BTreeNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.converter.IntegerStringConverter;
import ucr.lab.HelloApplication;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class FXUtil {

    public static void loadPage(String className, String page, BorderPane bp) {
        try {
            Class cl = Class.forName(className);
            FXMLLoader fxmlLoader = new FXMLLoader(cl.getResource(page));
            cl.getResource("bp");
            bp.setCenter(fxmlLoader.load());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static TextFormatter<Integer> getTextFormatterInteger() {
        return new TextFormatter<>(new IntegerStringConverter(), 0,
                change -> (change.getControlNewText().matches("\\d*")) ? change : null);
    }

    public static Alert alert(String title, String headerText){
        Alert myalert = new Alert(Alert.AlertType.INFORMATION);
        myalert.setTitle(title);
        myalert.setHeaderText(headerText);
        DialogPane dialogPane = myalert.getDialogPane();
        String css = Objects.requireNonNull(HelloApplication.class.getResource("/ucr/lab/stylesheet.css")).toExternalForm();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("myDialog");
        return myalert;
    }

    public static Alert informationDialog(String title) {
        Alert myalert = new Alert(Alert.AlertType.NONE);
        myalert.setAlertType(Alert.AlertType.INFORMATION);
        myalert.setTitle(title);
        myalert.setHeaderText(null);
        return myalert;
    }

    public static Alert confirmationDialog(String title) {
        Alert myalert = new Alert(Alert.AlertType.NONE);
        myalert.setAlertType(Alert.AlertType.CONFIRMATION);
        myalert.setTitle(title);
        myalert.setHeaderText(null);
        return myalert;
    }


    public static TextInputDialog dialog(String title, String headerText){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        //String css = HelloApplication.class.getResource("moderna.css").toExternalForm();
        //dialog.getEditor().getStylesheets().add(css);
        return dialog;
    }

    public static String alertYesNo(String title, String headerText, String contextText){
        Alert myalert = new Alert(Alert.AlertType.CONFIRMATION);
        myalert.setTitle(title);
        myalert.setHeaderText(headerText);
        myalert.setContentText(contextText);
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        myalert.getDialogPane().getButtonTypes().clear(); //quita los botones defaults
        myalert.getDialogPane().getButtonTypes().add(buttonTypeYes);
        myalert.getDialogPane().getButtonTypes().add(buttonTypeNo);
        //dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        DialogPane dialogPane = myalert.getDialogPane();
        String css = Objects.requireNonNull(HelloApplication.class.getResource("dialog.css")).toExternalForm();
        dialogPane.getStylesheets().add(css);
        Optional<ButtonType> result = myalert.showAndWait();
        //if((result.isPresent())&&(result.get()== ButtonType.OK)) {
        if((result.isPresent())&&(result.get()== buttonTypeYes))
            return "YES";
        else return "NO";
    }

    public static void drawTreeNode(GraphicsContext gc, BTreeNode node, double x, double y, double horizontalSpacing) {
        if (node == null) return;

        double radius = 15; // Radio para el nodo
        double diameter = radius * 2;// terminaria siendo 30


        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(x - radius, y - radius, diameter, diameter);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - radius, y - radius, diameter, diameter);

        // Dibuja el valor del nodo
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(15));
        gc.fillText(String.valueOf(node.data), x - 7, y + 5);

        // Dibuja counterTranversal si es distinto de 0
        if (node.counterTranversal != 0) {
            gc.setFont(new Font(10));
            gc.fillText(String.valueOf(node.counterTranversal), x - 7, y + 25);
        }

        double verticalSpacing = 60;

        // Dibuja líneas y dibuja hijos recursivamente
        if (node.left != null) {
            gc.strokeLine(x, y + radius, x - horizontalSpacing, y + verticalSpacing - radius);
            drawTreeNode(gc, node.left, x - horizontalSpacing, y + verticalSpacing, horizontalSpacing / 2);
        }

        if (node.right != null) {
            gc.strokeLine(x, y + radius, x + horizontalSpacing, y + verticalSpacing - radius);
            drawTreeNode(gc, node.right, x + horizontalSpacing, y + verticalSpacing, horizontalSpacing / 2);
        }
    }

    public static void drawLevelLines(GraphicsContext gc, int treeHeight, double rootY, double verticalSpacing, double canvasWidth) {
        gc.setStroke(Color.GRAY);
        gc.setFill(Color.DARKGRAY);
        gc.setFont(new Font(12));
        gc.setLineDashes(4); // Línea punteada

        for (int level = 0; level < treeHeight; level++) {
            double y = rootY + level * verticalSpacing;
            gc.strokeLine(0, y, canvasWidth, y);
            gc.fillText("Nivel " + level, 5, y - 5); // Texto ligeramente arriba
        }

        gc.setLineDashes(0); // Restablece a línea sólida
    }
}
