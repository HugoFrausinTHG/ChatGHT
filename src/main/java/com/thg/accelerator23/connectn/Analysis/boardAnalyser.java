package com.thg.accelerator23.connectn.Analysis;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thg.accelerator23.connectn.BooleanInt.BooleanInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class boardAnalyser{
    public GameConfig config;
    public boardAnalyser(Board board){
     this.config = board.getConfig();
    }
    public static BooleanInt doesColumnHaveSpace(Board board, int column) {
        for (int lowestFreeRow = 0; lowestFreeRow < board.getConfig().getHeight(); ++lowestFreeRow) {
            if (board.getCounterPlacements()[column][lowestFreeRow] == null) {
                return new BooleanInt(true,lowestFreeRow);
            }
        }
        return new BooleanInt(false,-1);
    }
    public static int[] allowedColumns(Board board) {
        List<Integer> allowedColumns = new ArrayList<>();
        for(int column = 0; column < board.getConfig().getWidth(); column++){
            boolean isColumnAllowed = doesColumnHaveSpace(board,column).getaBoolean();
            if (isColumnAllowed){allowedColumns.add(column);}
        }
        return allowedColumns.stream().mapToInt(i -> i).toArray();
    }
    public static Board makeFutureBoard(Board board, Counter counter, int move){
        Counter[][] counterPlacements = DeepCopy(board.getCounterPlacements());
        int rowPosition = doesColumnHaveSpace(board,move).getAnInt();
        counterPlacements[move][rowPosition] = counter;
        return new Board(counterPlacements, board.getConfig());
    }
    public static Board makeBoardState(Board board, Counter counter, int[] moves){
        Counter[][] counterPlacements = DeepCopy(board.getCounterPlacements());
        Board newBoard = new Board(counterPlacements, board.getConfig());
        Counter nextCounter = counter;
        for(int move :moves){
            newBoard = makeFutureBoard(newBoard,nextCounter,move);
            nextCounter = nextCounter.getOther();
        }
        return newBoard;
    }

    public static Board fillBoardColumn(Board board, int column,Counter counter){
        Counter[][] counterPlacementsCopy = DeepCopy(board.getCounterPlacements());
        for(int row =0 ; row<board.getConfig().getHeight(); row++){
            counterPlacementsCopy[column][row] = counter;
        }
        return new Board(counterPlacementsCopy,board.getConfig());
    }

    public static <T> T[][] DeepCopy(T[][] matrix) {
        return (T[][]) Arrays.stream(matrix).map((el) -> {
            return (Object[]) el.clone();}).toArray(($) -> {
                return (Object[][]) matrix.clone();});
    }


}