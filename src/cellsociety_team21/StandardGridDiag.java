package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.org.apache.bcel.internal.generic.NEW;

import javafx.scene.paint.Paint;

public class StandardGridDiag extends StandardGrid {

	public StandardGridDiag(int row, int col, ArrayList<String> initStates, double cellSize,
			HashMap<String, Paint> colors, String gameType) {
		super(row, col, initStates, cellSize, colors, gameType);
		assignNeighborsDiag();
	}
	
	public StandardGridDiag(){
		super();
	}

	private void assignNeighborsDiag(){
		for (Point myPoint : getCellMap().keySet()){
			int x = (int) myPoint.getX();
			int y = (int) myPoint.getY();
			if (getCellMap().containsKey(new Point(x-1, y-1))){
				getCellMap().get(myPoint).addNeighbor("northwest",getCellMap().get(new Point(x-1, y-1)));
			}
			if (getCellMap().containsKey(new Point(x-1, y+1))){
				getCellMap().get(myPoint).addNeighbor("northeast",getCellMap().get(new Point(x-1, y+1)));
			}
			if (getCellMap().containsKey(new Point(x+1, y-1))){
				getCellMap().get(myPoint).addNeighbor("southwest",getCellMap().get(new Point(x+1, y-1)));
			}
			if (getCellMap().containsKey(new Point(x+1, y+1))){
				getCellMap().get(myPoint).addNeighbor("southeast",getCellMap().get(new Point(x+1, y+1)));
			}
			/*for (int i = (int) myPoint.getX() - 1; i < (int) myPoint.getX() + 2; i+=2){
				for (int j = (int) myPoint.getY() - 1; j < (int) myPoint.getY() + 2; j+=2){
					Point neighborsPoint = new Point(i, j);
					if (getCellMap().containsKey(neighborsPoint)){
						getCellMap().get(myPoint).addNeighbor(getCellMap().get(neighborsPoint));
					}
				}
			}*/
		}
	}
}
