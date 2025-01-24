package com.thg.accelerator23.connectn.MaxMinImplementation;

import com.thg.accelerator23.connectn.Analysis.BuiltInAnalysis.GameState;
import com.thg.accelerator23.connectn.Analysis.BuiltInAnalysis.BoardAnalyser;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thg.accelerator23.connectn.BooleanInt.BooleanInt;

import java.util.Arrays;


public class BoardScorer{
    public static int winBonus = 1000;
    public static int loseBonus = -999;
    public static int centerBenefitBonus = 10;
    public static int score(boardNode node,Counter counter,GameState gameStatus){
        int score = 0;
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
    public static BooleanInt canWin(GameState gameState,Counter counter){
        return new BooleanInt(isWon(gameState,counter),winBonus);
    }
    public static boolean isWon(GameState gameState,Counter counter){
        boolean won = gameState.getWinner() == counter;
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