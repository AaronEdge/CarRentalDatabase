import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// code imported from http://tech.chitgoks.com/2013/07/08/how-to-create-confirm-dialog-window-in-java-fx-2/

public class AlertPromptDialog extends Stage {

    private static final int WIDTH_DEFAULT = 200;

    private static Label label;
    private static AlertPromptDialog popup;
    private static int result;
    private static Image img;

    public static final int NO = 0;
    public static final int YES = 1;

    public AlertPromptDialog() {
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);

        img = new Image(AlertPromptDialog.class.getResourceAsStream("/Assets/ic_info_outline_black_24dp_1x.png"));

        addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    AlertPromptDialog.this.close();
                } 
            }
        });

        label = new Label();
        label.setWrapText(true);
        label.setGraphicTextGap(20);

        Button ybutton = new Button("Yes");
        ybutton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                result = YES;
                AlertPromptDialog.this.close();
            }
        });

        Button nbutton = new Button("No");
        nbutton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                result = NO;
                AlertPromptDialog.this.close();
            }
        });

        BorderPane borderPane = new BorderPane();

        BorderPane dropShadowPane = new BorderPane();
        dropShadowPane.getStyleClass().add("content");
        dropShadowPane.setTop(label);

        HBox hbox = new HBox();
        hbox.setSpacing(15);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(ybutton);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(nbutton);

        dropShadowPane.setBottom(hbox);

        borderPane.setCenter(dropShadowPane);

        Scene scene = new Scene(borderPane);                       
        scene.getStylesheets().add("alert.css");
        scene.setFill(Color.TRANSPARENT);
        setScene(scene);
    }

    public static int show(Stage owner, String msg) {
        if (popup == null) {
            popup = new AlertPromptDialog();
        }

        label.setText(msg);
        label.setGraphic(new ImageView(img));
        label.setAlignment(Pos.CENTER);
        

        // calculate width of string
        final Text text = new Text(msg);
        text.snapshot(null, null);
        // + 40 because there is padding 10 left and right and there are 2 containers now
        // + 20 because there is text gap between icon and messge
        int width = (int) text.getLayoutBounds().getWidth();

        if (width + img.getWidth() < WIDTH_DEFAULT)
            width = WIDTH_DEFAULT;

        int height = 100;

        popup.setWidth(width);
        popup.setHeight(height);

        // make sure this stage is centered on top of its owner
        popup.setX(owner.getX() + (owner.getWidth() / 2 - popup.getWidth() / 2));
        popup.setY(owner.getY() + (owner.getHeight() / 2 - popup.getHeight() / 2));

        popup.showAndWait();

        return result;
    }

}