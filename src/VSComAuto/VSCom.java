package VSComAuto;

import GUI.Position;
import GridManagement.Grid;

import java.util.Collections;

public class VSCom {
        private GridManagement.Grid Grid;

    public VSCom(Grid Grid) {
        this.Grid = Grid;
    }

    public Position chooseMove() {
        Collections.shuffle(Grid.getValidMoves());
        return Grid.getValidMoves().get(0);
    }
}
