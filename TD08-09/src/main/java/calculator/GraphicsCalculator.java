package calculator;

import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.SyncFailedException;
import java.util.EmptyStackException;
import java.util.Objects;

public class GraphicsCalculator extends Application {
    Tokenizer tok = new Tokenizer();
    Label lab = new Label();

    String alphabet = "0123456789.+-*/()cC=";
    boolean restart = false;

    @Override
    public void start(Stage stage) {
        stage.show();
        lab.setMinHeight(30);
        stage.setTitle("Calculatrice Graphique");
        stage.setHeight(275);
        stage.setWidth(210);
        Scene scene = new Scene(new VBox(
                new HBox(lab),
                new HBox(b('7'), b('8'), b('9'), b('+')),
                new HBox(b('4'), b('5'), b('6'), b('-')),
                new HBox(b('1'), b('2'), b('3'), b('*')),
                new HBox(b('0'), b('.'), b('C'), b('/')),
                new HBox(b('('), b(')'), b('$'), b('='))
        ));
        scene.setOnKeyTyped(e -> handlekey(e));
        stage.setScene(scene);
    }

    private Button b(char c){
        Button retB = new Button(String.valueOf(c));
        retB.setMinSize(50, 40);
        retB.setOnAction(value -> update(c));
        return retB;
    }

    private void handlekey(KeyEvent e){
        update(e.getCharacter().toCharArray()[0]);
    }

    private void update(char c){
        if(alphabet.indexOf(c) != -1){
            if (restart){
                lab.setText("");
                restart = false;
            }
            lab.setText(lab.getText()+c);
            if(c == '='){
                if(Objects.equals(lab.getText(), "=")) lab.setText("0=");
                try {
                    tok.readString(lab.getText());
                    System.out.println(lab.getText());
                    lab.setText(String.valueOf(tok.calc.getResult()));
                }
                catch(EmptyStackException e) {
                    lab.setText("EXPRESSION ERROR");
                    tok.calc.commandInit();
                }
                tok.calc.commandInit();
                restart = true;
            }
            if(c == 'C' || c=='c'){
                lab.setText("");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
