package ai;

public class Main {
    public static void main(String[] args){
        SlidingPuzzle puzzle = new SlidingPuzzle();
        System.out.println(puzzle); // puzzle = puzzle.toString()
        puzzle.move(SlidingPuzzle.RIGHT);
        System.out.println(puzzle);
        puzzle.move(SlidingPuzzle.RIGHT);
        System.out.println(puzzle);
        puzzle.move(SlidingPuzzle.RIGHT);
        System.out.println(puzzle);
    }
}
