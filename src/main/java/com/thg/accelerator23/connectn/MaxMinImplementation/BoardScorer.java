package com.thg.accelerator23.connectn.MaxMinImplementation;

import com.thehutgroup.accelerator.connectn.analysis.BoardAnalyser;
import com.thehutgroup.accelerator.connectn.analysis.GameState;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thg.accelerator23.connectn.BooleanInt.BooleanInt;

import java.util.Arrays;


public class BoardScorer{
    public static int winBonus = 1000;
    public static int loseBonus = -1000;
    public static int centerBenefitBonus = 10;
    public static int score(boardNode node,Counter counter){
        BoardAnalyser analysis = new BoardAnalyser(node.getBoard().getConfig());
        int score = 0;
        GameState gameStatus = analysis.calculateGameState(node.getBoard());
        BooleanInt won = canWin(gameStatus,counter);
        if(won.getaBoolean()){
            score+=won.getAnInt();
        }
        BooleanInt lost = canLose(gameStatus,counter);
        if(lost.getaBoolean()){
            score+=lost.getAnInt();
        }
        BooleanInt inCenterColumn = centreColumn(node,counter);
        if(inCenterColumn.getaBoolean()){
            score+=inCenterColumn.getAnInt();
        }
        return score;
    }
    public static int getLastMove(String nodeId){
        String lastMoveString = nodeId.substring(nodeId.length()-1);
        return Integer.parseInt(lastMoveString);
    }
    public static BooleanInt canWin(GameState gameState,Counter counter){
        return new BooleanInt(isWon(gameState,counter),winBonus);
    }
    public static boolean isWon(GameState gameState,Counter counter){
        boolean won = gameState.getMaxInARowByCounter().get(counter) == 4;
        return won;
    }
    public static BooleanInt canLose(GameState gameState, Counter counter){

        return new BooleanInt(isWon(gameState, counter.getOther()),loseBonus);
    }
    public static BooleanInt centreColumn(boardNode node,Counter measuringCounter){
        Board board = node.getBoard();

        int centerColumnIndex = board.getConfig().getWidth()/2 ;
        Counter[] centerColum = board.getCounterPlacements()[centerColumnIndex];

        int numOfCounterInCenter = Arrays.stream(centerColum).filter(counter->counter == measuringCounter).mapToInt(counter->1).sum();
        boolean counterInCenter = numOfCounterInCenter >0;

        return new BooleanInt(counterInCenter,centerBenefitBonus*numOfCounterInCenter);
    }
}
