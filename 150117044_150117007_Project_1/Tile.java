import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Tile extends Main{
	private int id;
	private String type;
	private String property;

	
	public Tile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tile(int id, String type, String property) {
		this.id = id;
		this.type = type;
		this.property = property;
	}
	
	//for getImage we used if-else if so that each tile defined unique.
	public Image getImage() {
		
		Image image = null;
		
		if (this.type.equals("Starter") && this.property.equals("Vertical")) {
			
			image = new Image("images/starter-vertical.gif",100,100,false , false);
			
	
		} else if (this.type.equals("Starter") && this.property.equals("Horizontal")) {
			
			image = new Image("images/starter-horizontal.gif",100,100,false,false);
			
		} else if(this.type.equals("End") && this.property.equals("Vertical")) {
			
			image = new Image("images/end-vertical.gif",100,100,false,false);
		
			
		} else if(this.type.equals("End") && this.property.equals("Horizontal")) {
			
			image = new Image("images/end-horizontal.gif",100,100,false,false);
			
		} else if(this.type.equals("Empty") && this.property.equals("none")) {
			
			image = new Image("images/empty-none.gif",100,100,false,false);
			
		} else if(this.type.equals("Empty") && this.property.equals("Free")) {
			
			image = new Image("images/empty-free.gif",100,100,false,false);
			
		} else if(this.type.equals("Pipe") && this.property.equals("Vertical")) {
			
			image = new Image("images/pipe-vertical.gif",100,100,false,false);
			
		} else if(this.type.equals("Pipe") && this.property.equals("Horizontal")) {
			
			image = new Image("images/pipe-horizontal.gif",100,100,false,false);
			
		} else if(this.type.equals("Pipe") && this.property.equals("00")) {
			
			image = new Image("images/curved00.gif",100,100,false,false);
			
		} else if(this.type.equals("Pipe") && this.property.equals("01")) {
			
			image = new Image("images/curved01.gif",100,100,false,false);
			
		} else if(this.type.equals("Pipe") && this.property.equals("10")) {
			
			image = new Image("images/curved10.gif",100,100,false,false);
			
		} else if(this.type.equals("Pipe") && this.property.equals("11")) {
			
			image = new Image("images/curved11.gif",100,100,false,false);
			
		} else if(this.type.equals("PipeStatic") && this.property.equals("Horizontal")) {
			
			image = new Image("images/pipestatic-horizontal.gif",100,100,false,false);
			
		} else if(this.type.equals("PipeStatic") && this.property.equals("Vertical")) {
			
			image = new Image("images/pipestatic-vertical.gif",100,100,false,false);
			
		}
	    else if(this.type.equals("PipeStatic") && this.property.equals("00")) {
		
		    image = new Image("images/pipe_00_static.png",100,100,false,false);
		
	    } else if(this.type.equals("PipeStatic") && this.property.equals("01")) {
		
		    image = new Image("images/pipe_01_static.png",100,100,false,false);
		
	    }
	    else if(this.type.equals("PipeStatic") && this.property.equals("10")) {
			
			image = new Image("images/pipe_10_static.png",100,100,false,false);
			
		} else if(this.type.equals("PipeStatic") && this.property.equals("11")) {
			
			image = new Image("images/pipe_11_static.png",100,100,false,false);
			
		}
		
		return image;
	}
	//getter and setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

public void start(Stage primaryStage) {

}


public static void main(String[] args) {
	launch(args);
}

}	