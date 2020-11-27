//Sueda Bilen 150117044
//Edanur Ozturk 150117007

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
//this class for executing the game board
public class Main extends Application {
	Stage window;
	Scene sceneFirst,scene;
	//for marker variables 
	public static int moveNumber = 0;
	public static int levelNumber;
	public Button moves;
	public Button levelname;
	public Button exitGame;
	public Button playSound;
	public Button stopSound;
	//starting and placing lists
	private ArrayList<Tile> tilesStart = new ArrayList<>();
	private Tile[][] tiles = new Tile[4][4];
	public int i,j;
	//variables for coordinates of dragged tiles
	private double tilePressedX;
	private double tilePressedY;
	private double tileReleasedX;
	private double tileReleasedY;
	//to hold pressed and released tiles on pane
	public static Dragboard db;
	public static ClipboardContent clipboard, cb;
	private boolean win = false;
	private FileReader file = null; // Declare and initialize file reader
   
	//level names String array
	private static String[] levelFileNames = { "level1.txt", "level2.txt", "level3.txt", "level4.txt", "level5.txt" };
	
	
	public void start(Stage primaryStage){
		//opening the first scene to user
		window=primaryStage;
		Button buttonStart= new Button("START GAME");
		buttonStart.setStyle("-fx-font-size: 4em;");
		buttonStart.setOnAction(e -> window.setScene(scene));
		Button buttonQuit = new Button("Quit");
		buttonQuit.setStyle("-fx-font-size: 3em;");
		buttonQuit.setOnAction(e -> System.exit(0));
		
		VBox vboxFirst = new VBox();
		vboxFirst.setPadding(new Insets(200,50,50,100));
		vboxFirst.getChildren().addAll(buttonStart,buttonQuit);
		vboxFirst.setAlignment(Pos.CENTER);
		sceneFirst = new Scene(vboxFirst,710,500);
	            System.out.print("Which level you want to go?");
		        Scanner levelinput = new Scanner(System.in);
		        String level = levelinput.next();
		        levelNumber = Integer.parseInt(level);
			try {	
				URL path = Main.class.getResource(levelFileNames[levelNumber-1]);
				File f = new File(path.getFile());
				file = new FileReader(f);
			} catch (FileNotFoundException e) { // if level files cannot be found then Throws an exception
				System.out.println("The file was not found, maybe the file is not in the same directory?");
				System.exit(0);
			} catch (NullPointerException e) {
				System.out.println("The file was not found, maybe the file is not in the same directory?");
				System.exit(1);
			}
		win = false;
		
		// Create a scanner to read files
				Scanner input = new Scanner(file);
				
				//Read file input 
				while(input.hasNext()) {
					String line = input.nextLine();
					   if (!line.equals("")) {
						   String[] parsedStr = line.split(",");
						   int id = Integer.parseInt(parsedStr[0]);
						   tilesStart.add(new Tile(id , parsedStr[1] , parsedStr[2]));
				}
			}
				//Turn one dimensional tilesStart array to 
			    //two dimensional tiles array to make easy to draw gridPane.
				int iter = 0;
			    for(i = 0 ;i < 4 ; i++) {
					for(j = 0 ; j < 4 ; j++) {
					    tiles[i][j] = tilesStart.get(iter);
					    iter++;
					}
				}   
			input.close();	
			
			   GridPane pane = new GridPane();
			   
			//Add tiles to the grid pane
			//to put images to our pane,we used ImageView 
			ArrayList<ImageView> draggable = new ArrayList<>();
			for(i = 0; i < 4 ; i++) {
	            for (j = 0; j < 4 ; j++) {
			    ImageView imgView = new ImageView(tiles[i][j].getImage());
			    imgView.setX(100);
				imgView.setY(100);
				pane.add(imgView , j,  i);
				draggable.add(imgView);
	            }
			}
			
		
		    ArrayList<Tile> change = new ArrayList<>();
			
			for(i = 0; i < draggable.size() ; i++) {
				change.clear();
				
				ImageView source = (ImageView) draggable.get(i);
				ImageView target = (ImageView) draggable.get(i);
				
			       if(tilesStart.get(i).getType().equals("PipeStatic")
				   ||tilesStart.get(i).getType().equals("Starter")
	     		   ||tilesStart.get(i).getType().equals("End")) {
				}
				//drag detected-you pressed on an image!
				else {
				    source.setOnDragDetected((MouseEvent event) -> {
				    	
				    db = source.startDragAndDrop(TransferMode.MOVE);
					clipboard = new ClipboardContent();
					clipboard.putImage(source.getImage());
					db.setContent(clipboard);
					moves.setText("Moves: " + Integer.toString(++moveNumber));
					//get locations of pressed image
					tilePressedX = event.getSceneX();
					tilePressedY = event.getSceneY();
					
					event.consume();
					
				});
				 //Start drag
				target.setOnDragOver((DragEvent event) -> {
					if (event.getGestureSource() != target && event.getDragboard().hasImage()) {
						event.acceptTransferModes(TransferMode.MOVE);
					}
					event.consume();
				});

				target.setOnDragEntered((DragEvent event) -> {
					
					if (event.getGestureSource() != target && event.getDragboard().hasImage()) {
					}
					event.consume();
				});

				target.setOnDragExited((DragEvent event) -> {
					target.setStyle("");

					event.consume();
				});
				
                //when the mouse button is released
				target.setOnDragDropped((DragEvent event) -> {
					cb = new ClipboardContent();
					cb.putImage(target.getImage());

					boolean success = false;
					if (db.hasImage()) {
						target.setImage(db.getImage());

						success = true;
					}
					//get the locations of released image
					tileReleasedX = event.getSceneX();
					tileReleasedY = event.getSceneY();
					//find which tile is pressed and add it to change arraylist
					Tile pressed = findTile(tilePressedX,tilePressedY,tiles);
					change.add(pressed);
					//find which tile is released and add it to change arraylist
					Tile released = findTile(tileReleasedX,tileReleasedY,tiles);
					change.add(released);
					//swap the content of change arraylist
					tiles = change(pressed,released,tiles);
					event.setDropCompleted(success);
					event.consume();
					
				});
				
                //drag is done
				source.setOnDragDone((DragEvent event) -> {
					if (event.getTransferMode() == TransferMode.MOVE)
						source.setImage(cb.getImage());
                    win = win(tiles,levelNumber);
			       	
					
			       	if(win) {
			       		pane.getChildren().clear();
			       		Label winSituation = new Label("Congratulations!!You completed level " + levelNumber + " !!");
			       	    winSituation.setAlignment(Pos.CENTER);
			       	    winSituation.setFont(Font.font("Helvetica", FontPosture.ITALIC, 30));
			       	    winSituation.setTextFill(Color.BLUEVIOLET);
			       	    pane.getChildren().add(winSituation);
			       	    window.show();
						}
				
				});
				 
				 
				}
			       
			   }
		
			
	
			
		    Pane holder = new Pane();
	        pane.setStyle("-fx-background-color: black;");
			holder.getChildren().add(pane);
	     	//To show level and move numbers. 
			levelname = new Button("Level: " + Integer.toString(levelNumber));
			levelname.setStyle("-fx-background-color: #3CB371;-fx-font-size: 3em;");
			moves = new Button("Moves: " + Integer.toString(moveNumber));
		    moves.setStyle("-fx-background-color: #5F9EA0;-fx-font-size: 3em; ");
		    //If the user wants to quit the game,click the button.
		    exitGame= new Button("Quit");
		    exitGame.setStyle("-fx-background-color: #3CB371;-fx-font-size: 3em; ");
		    exitGame.setOnAction(e-> System.exit(0));
		    //to set sound to the game
		    playSound = new Button("Play");
		    playSound.setStyle("-fx-background-color: #5F9EA0;-fx-font-size: 3em; ");
		    stopSound = new Button("Stop");
		    stopSound.setStyle("-fx-background-color: #5F9EA0;-fx-font-size: 3em; ");
		    File musicfile = new File("gamesong.mp3");
		    AudioClip audioClip = new AudioClip(musicfile.toURI().toString());
		    playSound.setOnAction(e -> {
		      audioClip.stop();
		      audioClip.setCycleCount(2);
		      audioClip.play();
		    });
		    stopSound.setOnAction(e -> audioClip.stop());
		//option buttons adding
		HBox markers=new HBox();
		markers.getChildren().addAll(moves,levelname,playSound,stopSound,exitGame);
		VBox bigPane =new VBox();
		//all panes are added this bigPane
		bigPane.getChildren().addAll(markers,holder);
        //show the bigPane
      	scene = new Scene(bigPane, 710, 500);
		 
		window.setScene(sceneFirst);
		window.show();
	    	
		}
	    
         
			
					
//which position mouse touched and which member of the array is here
	//this func's aim is to find Tile from the location of touched place.
	public static Tile findTile(double x,double y,Tile[][] find) {
		Tile found = null;
		//we have a marker above our gridpane.
		//so our gridpane starts at 70 for y value.
		if(x <= 100 && y <= 170) {
			found = find[0][0];
		}
		if(x <= 100 & y <= 270 && y>= 170) {
			found = find[1][0];
		}
		if(x <= 100 & y <= 370 && y >= 270) {
			found = find[2][0];
		}
		if(x <= 100 & y <= 470 && y >= 370) {
			found = find[3][0];
		}
		if(x >= 100 && x <= 200 && y <= 170) {
			found = find[0][1];
		}
		if(x >= 100 && x <= 200 && y >= 170 && y <= 270) {
			found = find[1][1];
		}
		if(x >= 100 && x <= 200 && y >= 270 && y <= 370) {
			found = find[2][1];
		}
		if(x >= 100 && x <= 200 && y >= 370 && y <= 470) {
			found = find[3][1];
		}
		if(x >= 200 && x <= 300 && y <= 170 ) {
			found = find[0][2];
		}
		if(x >= 200 && x <= 300 && y >= 170 && y <= 270) {
			found = find[1][2];
		}
		if(x >= 200 && x <= 300 && y >= 270 && y <= 370) {
			found = find[2][2];
		}
		if(x >= 200 && x <= 300 && y >= 370 && y <= 470) {
			found = find[3][2];
		}
		if(x >= 300 && x <= 400 && y <= 170) {
			found = find[0][3];
		}
		if(x >= 300 && x <= 400 && y >= 170 && y <= 270) {
			found = find[1][3];
		}
		if(x >= 300 && x <= 400 && y >= 270 && y <= 370) {
			found = find[2][3];
		}
		if(x >= 300 && x <= 400 && y >= 370 && y <= 470) {
			found = find[3][3];
		}
		
		return found;
	}

	//to swap contents of clicked tiles
	public Tile[][] change(Tile pressed,Tile released ,Tile[][] tileArray){
		Tile temp = pressed;
		for(i=0;i<4;i++) {
			for(j=0;j<4;j++) {
		if(tileArray[i][j].equals(pressed)) {
			tileArray[i][j] = released;
		}
		else if(tileArray[i][j].equals(released)) {
			tileArray[i][j] = temp;
		       }
		    }
		}
	
		return tileArray;
	}
	
	//to check win
	public boolean win(Tile[][] winTiles,int levelNum) {
		if(levelNum == 0 || levelNum == 1 || levelNum == 2) {
			if(winTiles[0][0].getType().equals("Starter")&&winTiles[0][0].getProperty().equals("Vertical")
			   &&winTiles[1][0].getType().equals("Pipe")&&winTiles[1][0].getProperty().equals("Vertical")
			   &&winTiles[2][0].getType().equals("Pipe")&&winTiles[2][0].getProperty().equals("Vertical")
			   &&winTiles[3][0].getType().equals("Pipe")&&winTiles[3][0].getProperty().equals("01")
			   &&winTiles[3][1].getType().equals("Pipe")&&winTiles[3][1].getProperty().equals("Horizontal")
			   &&winTiles[3][2].getType().equals("PipeStatic")&&winTiles[3][2].getProperty().equals("Horizontal")
			   &&winTiles[3][3].getType().equals("End")&&winTiles[3][3].getProperty().equals("Horizontal")
			   ) {
				win = true;
			}
		}
		else if(levelNum == 3) {
			if(winTiles[0][0].getType().equals("Starter")&&winTiles[0][0].getProperty().equals("Vertical")
			   &&winTiles[1][0].getType().equals("PipeStatic")&&winTiles[1][0].getProperty().equals("Vertical")
			   &&winTiles[2][0].getType().equals("Pipe")&&winTiles[2][0].getProperty().equals("01")
			   &&winTiles[2][1].getType().equals("Pipe")&&winTiles[2][1].getProperty().equals("Horizontal")
			   &&winTiles[2][2].getType().equals("Pipe")&&winTiles[2][2].getProperty().equals("Horizontal")
			   &&winTiles[2][3].getType().equals("Pipe")&&winTiles[2][3].getProperty().equals("00")
			   &&winTiles[1][3].getType().equals("End")&&winTiles[1][3].getProperty().equals("Vertical")
			) {
				win = true;
			}
		}
		else if(levelNum == 4) {
			if(winTiles[0][0].getType().equals("Starter")&&winTiles[0][0].getProperty().equals("Vertical")
			   &&winTiles[1][0].getType().equals("Pipe")&&winTiles[1][0].getProperty().equals("Vertical")
			   &&winTiles[2][0].getType().equals("PipeStatic")&&winTiles[2][0].getProperty().equals("01")
			   &&winTiles[2][1].getType().equals("Pipe")&&winTiles[2][1].getProperty().equals("Horizontal")
			   &&winTiles[2][2].getType().equals("Pipe")&&winTiles[2][2].getProperty().equals("Horizontal")
			   &&winTiles[2][3].getType().equals("Pipe")&&winTiles[2][3].getProperty().equals("00")
			   &&winTiles[1][3].getType().equals("End")&&winTiles[1][3].getProperty().equals("Vertical")
			) {
				win = true;
			}
		}
		else { 
			win = false;
		}
		return win;
	}
	
	
public static void main(String[] args) {
	launch(args);
	}




}
		