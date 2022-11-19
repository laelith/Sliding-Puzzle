package ai;

import sac.State;
import sac.StateFunction;

public class MisplacedTilesHeuristic extends StateFunction {

    @Override
    public double calculate (State state) {
        SlidingPuzzle puzzle = (SlidingPuzzle) state;
        return puzzle.getMisplacedPuzzlePieceNumber();
    }
}
