/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hello.fx;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import jdk.dynalink.Operation;

public class HelloWorld extends Application {

    private static int col, row;  //dimentions
    private static double myMatrix[][];
    private static double tempMatrix[][];
    private static TextField inputField[][];
    private static int result;
    private static Button minusB, plusB, inverseB,
            multiplyB, nMultiplyB, nDivisionB,
            getValueB, showMatrix, transposing,
            newMatrix;
    private static Pane choosePanel[] = new Pane[8];
    private static int lastCol, lastRow;
    private static Stage stage;
    private static boolean flag;

    /*
     * Methods:
     * 1- private static void getDimension() 
     *    for taking matrix's dimensitions
     * 2- private static void setElements(double matrix [][], String title )
     *    for filling matrix's elements  
     * 3- private static void checkTextField (JTextField field [][] ) 
     *     For setting spaced fields as zeors
     * 4- private void ChooseOperation () for choising an operation to be applied
     *    to the matrix
     * 5- private void actionPerformed(ActionEvent e) 
     *    Output methods:
     * 6- Matrix () - constructor 
     *    for invoking program's process
     * 7 - public void actionPerformed(ActionEvent e)
     *    for setting buttons performance
     * 8 - private static void showMatrix(double [][] matrix, String title )
     *    for showing the matrix (matrix) with the title (title)
     * 9 - private static void matrixPlusMatrix ()
     *    Do a plusing operation of matrix with other matrix
     * 10 - private static void matrixMinusMatrix ()
     *    Do a subtracting operation of matrix and other matrix
     * 11 - private static void multiplyByMatrix ()
     *    Do a multiplication operation between matrix and other matrix 
     * 12 - private static void guiMultliplyByScaler ()
     *    Do a multiplication operation between matrix and a nnumber 
     *    by using multliplyByScaler method and show that for the user   
     * 13 - private static double [][] multliplyByScaler (double [][] matrix , double x)
     *    but only inner the program
     * 14 - private static void divideByScaler ()
     *      Do a dividing operation of matrix and other matrix
     * 15 - private static void guiGetValue ()
     *      getting determinaiton's values by using getValue method
     *      and show it to the user 
     * 16 - private static double getValue (double [][] matrix)
     *      return determination's values but only inner the program
     * 17 - private static void guiTransporter (double [][] matrix)
     *      getting determination's values by using transporter method 
     *      and show it the user
     * 18 - private static double [][] transporter (double [][] matrix)
     *      return determinations's transporter but only inner the program
     * 19 - getMinor return the minor of cofactors in order to get 
     *      inversing matrix
     * 20 - private static void swap (double [] res1, double [] res2)
     *      for swaping two rows in order using to to get determination's value 
     * 21 - private static void  inverse  ()
     *      Do inversing operation for determinations
     * 22-  private static void newMatrix ()
     *      Enable the user to enter a new matrix and use the program's 
     *      features on it
     * 23-  public static void main (String [] args)
     *      For invoking the program
     */
 
    //prompting for matrix's dimensions
    private static void getDimension() {
        TextField lField = new TextField(); //lenght field 
        TextField wField = new TextField(); //col field

        
        VBox v1 = new VBox();
        HBox h1 = new HBox();
        HBox h2 = new HBox();
        HBox spacer = new HBox();
        HBox spacer2 = new HBox();
        Button btn1 = new Button("OK");
        Button btn2 = new Button("Cancel");
       
        h1.getChildren().add(new Label("Rows:"));
        h1.getChildren().add(lField);
       
        h2.getChildren().add(new Label("Cols:"));
        h2.getChildren().add(wField);
        
        spacer.getChildren().addAll(h1, h2);
        spacer.setSpacing(15);

        spacer2.getChildren().addAll(btn1, btn2);

        v1.getChildren().addAll(new Label("Enter Dimensitions"), spacer, spacer2);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(v1);
        Scene scene = new Scene(borderPane, 600, 200);
        
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(scene);
        secondaryStage.show();

               //save last dimensions
        lastCol = col;
        lastRow = row;

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (wField.getText().equals("")) {
                    col = 0;
                } else {
                    if (isInt(wField.getText())) {
                        col = Integer.parseInt(wField.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong Dimensions");
                        col = lastCol;
                        row = lastRow;
                        secondaryStage.close();
                        return;
                    }

                    if (isInt(lField.getText())) {
                        row = Integer.parseInt(lField.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong Dimensions");
                        col = lastCol;
                        row = lastRow;
                        secondaryStage.close();
                        return;
                    }

                }
                if (col < 1 || row < 1) {
                    JOptionPane.showConfirmDialog(null, "You entered wrong dimensions",
                            "Error", JOptionPane.PLAIN_MESSAGE);
                    col = lastCol;
                    row = lastRow;

                } else {
                    tempMatrix = myMatrix;
                    myMatrix = new double[row][col];
                    if (!setElements(myMatrix, "Fill your new matrix")) //filling the new matrix
                    {
                        //backup
                        // myMatrix = tempMatrix;       
                    }
                }
                secondaryStage.close();
            }
        });

        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                col = lastCol;
                row = lastRow;
                secondaryStage.close();

            }
        });

  
    }//end get Dimension

    //setting a matrix's elementis
    private static boolean setElements(double matrix[][], String title) {
        int temp, temp1;             //temprature variable
          
        Pane choosePanel[] = new Pane[row + 2];
        choosePanel[0] = new HBox();
        choosePanel[0].getChildren().add(new Label(title));
        choosePanel[choosePanel.length - 1] = new HBox();
        choosePanel[choosePanel.length - 1].getChildren().add(new Label("consider space field as zeros"));
        inputField = new TextField[matrix.length][matrix[0].length];
  
        TextField c[] = new TextField[matrix[0].length];
        Button btn2 = new Button("NO");
        Button btn1 = new Button("Yes");
        HBox h3 = new HBox();
        h3.getChildren().addAll(btn1, btn2);

        //lenght loop
        for (temp = 1; temp <= matrix.length; temp++) {
            choosePanel[temp] = new HBox();

            for (temp1 = 0; temp1 < matrix[0].length; temp1++) {
                inputField[temp - 1][temp1] = new TextField();
                c[temp1] = inputField[temp - 1][temp1];
          

            }//end col loop
            choosePanel[temp].getChildren().addAll(c);
            
        }//end row loop

        VBox v = new VBox();
        v.getChildren().addAll(choosePanel);
        v.setSpacing(10);

        VBox v1 = new VBox();
        v1.getChildren().addAll(v, h3);
        v1.setSpacing(10);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(v1);
        Scene scene = new Scene(borderPane);
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(scene);
        secondaryStage.show();

    
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            String tempString;

        
            @Override
            public void handle(ActionEvent event) {

                checkTextField(inputField);
                for (int temp = 0; temp < matrix.length; temp++) {
                    for (int temp1 = 0; temp1 < matrix[0].length; temp1++) {
                        tempString = inputField[temp][temp1].getText();

                        if (isDouble(tempString)) {
                            matrix[temp][temp1] = Double.parseDouble(inputField[temp][temp1].getText());
                        } else {
                            JOptionPane.showMessageDialog(null, "You entered wrong elements");

                            //backup
                            col = lastCol;
                            row = lastRow;
                            flag = false;
                           secondaryStage.close();
                            return;
                        }
                    }
                }
                flag = true;
                secondaryStage.close();

                return;

            }
        });


        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                flag = false;
                secondaryStage.close();
                // return flag;
            }
        });

        return flag;
    }//end get Inputs

    //for setting spaced fields as zeros
    private static void checkTextField(TextField field[][]) {
        for (int temp = 0; temp < field.length; temp++) {
            for (int temp1 = 0; temp1 < field[0].length; temp1++) {
                if (field[temp][temp1].getText().equals("")) {
                    field[temp][temp1].setText("0");
                }
            }
        }
    }//end reset
    
     HBox h1 = new HBox();
        HBox h2 = new HBox();
        HBox h3 = new HBox();
        VBox v1 = new VBox();
    
    private void ChooseOperation() {
       
        showMatrix = new Button("Show Matrix");
        showMatrix.setPrefSize(175, 35);
        showMatrix.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showMatrix(myMatrix, "Your Matrix");
              
            }
        });
       
       
        h1.getChildren().add(showMatrix);

        plusB = new Button("Plusing with Matrix");
        plusB.setPrefSize(175, 35);
        plusB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    matrixPlusMatrix();
                                             
                } catch (InterruptedException ex) {
                    Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        h1.getChildren().add(plusB);

        minusB = new Button("Subtracting with Matrix");
        minusB.setPrefSize(175, 35);
        minusB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                matrixMinusMatrix();
            }
        });
        
        h1.getChildren().add(minusB);

        multiplyB = new Button("Multiplying by matrix");
        multiplyB.setPrefSize(175, 35);
        multiplyB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                multiplyByMatrix();
            }
        });
   
        h2.getChildren().add(multiplyB);

        nMultiplyB = new Button("Multiplying by scaler");
        nMultiplyB.setPrefSize(175, 35);
        // nMultiplyB.addActionListener((ActionListener) this);
        h2.getChildren().add(nMultiplyB);

        nDivisionB = new Button("Dividing by scaler");
        nDivisionB.setPrefSize(175, 35);
        nDivisionB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                divideByScaler();
            }
        });
   
        h2.getChildren().add(nDivisionB);

        

        if (col == row) {
            getValueB = new Button("GET Value");
            getValueB.setPrefSize(175, 35);
            getValueB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showMatrix(myMatrix, "Your Matrix");
                }
            });
        }

        newMatrix = new Button("New Matrix");
        newMatrix.setPrefSize(275, 35);
        newMatrix.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newMatrix();
            }
        });
     
        h3.getChildren().add(newMatrix);

        v1.getChildren().addAll(h1, h2, h3);
        Scene scene = new Scene(v1, 500, 600);
        stage.setScene(scene);
        stage.show();
    }

   
    private static void showMatrix(double[][] matrix, String title) {
        int temp, temp1;             //temprature variable

        Pane choosePanel[] = new Pane[matrix.length + 1];
        choosePanel[0] = new HBox();
        
        Label l = new Label(title);
        l.setFont(Font.font("Arial", FontWeight.BOLD, 14));       
        choosePanel[0].getChildren().add(l);
        
        Button btn = new Button("OK");
        
        Label tempWidget[] = new Label[matrix[0].length];

        for (temp = 1; temp <= matrix.length; temp++) {
            choosePanel[temp] = new HBox();

            for (temp1 = 0; temp1 < matrix[0].length; temp1++) {
                if (matrix[temp - 1][temp1] == -0) {
                    matrix[temp - 1][temp1] = 0;
                }
                tempWidget[temp1] = new Label(String.format("%.2f", matrix[temp - 1][temp1]) + "    ");
                          
            }//end col loop
            if (matrix[0].length < 2) {
                choosePanel[temp].getChildren().add(tempWidget[temp1 - 1]);
            } else {
                choosePanel[temp].getChildren().addAll(tempWidget);
            }
        }//end row loop

        VBox v = new VBox();
        v.getChildren().addAll(choosePanel);
        v.setSpacing(20);

        VBox v1 = new VBox();
        v1.getChildren().addAll(v, btn);
        v1.setSpacing(15);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(v1);
        
        Scene scene = new Scene(borderPane);
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(scene);
        secondaryStage.show();

        if (col == 0 || row == 0) {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        }

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                secondaryStage.close();
            }
        });
 
    }//end show Matrix

    private static  void matrixPlusMatrix() throws InterruptedException {
        if (myMatrix.length < 1) {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        } else {

            double m2[][] = new double[row][col];
            double sum[][] = new double[row][col];
            
            boolean flag = setElements(m2, "Fill Aditional Matrix");
        
            
            if (flag) {

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        sum[i][j] = myMatrix[i][j] + m2[i][j];
                    }
                }
                     
            }
           
        }//end else checking

    

}//end plus matrix


    
private static void matrixMinusMatrix() {

        if (myMatrix.length < 1) {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        } else {
            double m2[][] = new double[row][col];
            double sub[][] = new double[row][col];
            double temp[][] = new double[row][col];

            if (setElements(m2, "Fill Subtracting Matrix")) {

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        temp[i][j] = (-1 * m2[i][j]);
                        sub[i][j] = myMatrix[i][j] + temp[i][j];
                    }
                }
                showMatrix(sub, "Subtracting Result");
            }
        }//end else of checking
    }

    private static void multiplyByMatrix() {

        TextField wField = new TextField(); //col field
        int col2 = 0;
        double m2[][], results[][];
        int sum;

        if (myMatrix.length < 1) {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        } else {

            //design input line
            Pane choosePanel[] = new Pane[2];
            choosePanel[0] = new Pane();
            choosePanel[1] = new Pane();

            choosePanel[0].getChildren().add(new Label("Enter Dimensitions"));

            choosePanel[1].getChildren().add(new Label("Rows:"));
            choosePanel[1].getChildren().add(new Label("" + col));
            // choosePanel[1].add(Box.createHorizontalStrut(15)); // a spacer
            choosePanel[1].getChildren().add(new Label("Cols:"));
            choosePanel[1].getChildren().add(wField);

            result = JOptionPane.showConfirmDialog(null, choosePanel,
                    null, JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == 0) {
                if (wField.getText().equals("")) {
                    col2 = 0;
                } else {
                    if (isInt(wField.getText())) {
                        col2 = Integer.parseInt(wField.getText());

                    }
                }

                m2 = new double[col][col2];
                results = new double[row][col2];
                if (setElements(m2, "Fill multiplying matrix")) {

                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col2; j++) {
                            sum = 0;
                            for (int k = 0; k < col; k++) {
                                sum += myMatrix[i][k] * m2[k][j];
                            }

                            results[i][j] = sum;

                        }
                    }

                    showMatrix(results, "Mulitiplication Result");
                }//elements checking
            }//dimensions checking
            else {
                return;
            }
        }//end else of checking
    }//end multiply by matrix method

    private static void guiMultliplyByScaler() {

        double[][] results = new double[row][col];
        double x;
        String tempString;

        if (myMatrix.length < 1) {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
            return;
        }

        tempString = JOptionPane.showInputDialog(null,
                "Enter the scaler number for multiplying");

        if (tempString == null) //cancel option
        {
            return;
        } else if (!tempString.equals("")) {
            x = Double.parseDouble(tempString);
        } else {
            JOptionPane.showMessageDialog(null, "You haven't entered a scaler");
            return;
        }
        results = multliplyByScaler(myMatrix, x);
        showMatrix(results, "Multiplication Result");

    }//end multiply by number

    private static double[][] multliplyByScaler(double[][] matrix, double x) {

        double[][] results = new double[row][col];
        int i, j;

        for (i = 0; i < matrix.length; i++) {
            for (j = 0; j < matrix[0].length; j++) {
                results[i][j] = x * matrix[i][j];
            }
        }
        return results;
    }//end multiply by number

    private static void divideByScaler() {
        double[][] results = new double[row][col];
        int i, j;
        double x;
        String tempString;

        if (myMatrix.length < 1) {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
            return;
        }

        //prompting for the scaler
        tempString = JOptionPane.showInputDialog("Enter the scaler number for dividing");

        if (tempString == null) //cancel option
        {
            return;
        } else if (!tempString.equals("")) {
            x = Double.parseDouble(tempString);
        } else {
            JOptionPane.showMessageDialog(null, "You haven't entered a scaler");
            return;
        }

        if (x == 0) {
            JOptionPane.showMessageDialog(null, "Excuse me we can't divid by 0");
            return;
        }

        for (i = 0; i < row; i++) {
            for (j = 0; j < col; j++) {
                results[i][j] = myMatrix[i][j] / x;
            }
        }
        showMatrix(results, "Dividing Result");

    }//end dividing by number
  
    private static boolean isInt(String str) {
        int temp;
        if (str.length() == '0') {
            return false;
        }

        for (temp = 0; temp < str.length(); temp++) {
            if (str.charAt(temp) != '+' && str.charAt(temp) != '-'
                    && !Character.isDigit(str.charAt(temp))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDouble(String str) {
        int temp;
        if (str.length() == '0') {
            return false;
        }

        for (temp = 0; temp < str.length(); temp++) {
            if (str.charAt(temp) != '+' && str.charAt(temp) != '-'
                    && str.charAt(temp) != '.'
                    && !Character.isDigit(str.charAt(temp))) {
                return false;
            }
        }
        return true;
    }

    private static void newMatrix() {
        getDimension();
    }

    public static void main(String[] args) {     
        launch(args);

    }

    @Override
        public void start(Stage stage) throws Exception {
        col = row = 0;
        this.stage = stage;
        myMatrix = new double[0][0];
        ChooseOperation();
    }

}//end class

