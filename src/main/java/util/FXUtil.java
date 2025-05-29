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
        String css = HelloApplication.class.getResource("dialog.css").toExternalForm();
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
        String css = HelloApplication.class.getResource("dialog.css").toExternalForm();
        dialogPane.getStylesheets().add(css);
        Optional<ButtonType> result = myalert.showAndWait();
        //if((result.isPresent())&&(result.get()== ButtonType.OK)) {
        if((result.isPresent())&&(result.get()== buttonTypeYes))
            return "YES";
        else return "NO";
    }

    // permite dibujar-> es mas de diseño que logica
    public static void drawBTreeNodes(GraphicsContext bTree, BTreeNode node, double posX, double posY, double spaceDispo) {
        if (node == null) return;
        // primero el nodo( OVAL)
        bTree.setFill(Color.LIGHTCORAL);
        // LAS POSICIONES FUERON LAS MAS CERCANAS QUE PUDE LOGRAR A PROBAR
        bTree.fillOval(posX - 15, posY - 15, 30, 30);
        bTree.setStroke(Color.BLACK);
        // linea de borde: solo para diseño, iguales que las del relleno para que concuerden
        bTree.strokeOval(posX - 15, posY - 15, 30, 30);

        // el valor del node
        bTree.setFill(Color.BLACK);
        bTree.setFont(new Font(15));
        // los valores de las posiciones para que queden alineados son esos
        bTree.fillText(String.valueOf(node.data), posX - 6, posY + 5);

        //  número segun el recorrido
        if (node.counterTranversal != 0) {
            bTree.setFont(new Font(10));
            bTree.fillText(String.valueOf(node.counterTranversal), posX - 5, posY + 25);
        }
        // recursividad para dibujar ambos lados
        if (node.left != null) {
            bTree.strokeLine(posX, posY + 15, posX - spaceDispo, posY + 50 - 15);
            drawBTreeNodes(bTree, node.left, posX - spaceDispo, posY + 50, spaceDispo / 2);
        }

        if (node.right != null) {
            bTree.strokeLine(posX, posY + 15, posX + spaceDispo, posY + 50 - 15);
            drawBTreeNodes(bTree, node.right, posX + spaceDispo, posY + 50, spaceDispo / 2);
        }
    }
}
