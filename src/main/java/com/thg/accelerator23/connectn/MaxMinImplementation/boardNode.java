package com.thg.accelerator23.connectn.MaxMinImplementation;

import com.thehutgroup.accelerator.connectn.AI.Analisys.boardAnalyser;
import com.thehutgroup.accelerator.connectn.AI.BooleanInt.BooleanInt;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;

import java.util.Arrays;

public class boardNode{
    public String parentId;
    public String boardId;
    public Board board;
    public BooleanInt score;
    public Counter counterTurn;

    public boardNode(String boardId, Board board, String parentId,Counter counterTurn){
        this.boardId = boardId;
        this.parentId = parentId;
        this.board = board;
        this.score = new BooleanInt(false,-1);
        this.counterTurn = counterTurn;
    }


    public void setScore(int score) {
        this.score = new BooleanInt(true,score);
    }
    public boardNode createChild(int move){
        Counter nextCounter = this.counterTurn.getOther();
        Board childBoard = boardAnalyser.makeFutureBoard(this.board,nextCounter,move);
        String childId = this.produceChildId(String.valueOf(move));
        return new boardNode(childId, childBoard, this.boardId, nextCounter);
    }

    public String produceChildId(String move){
        return  (this.boardId.equals("-1"))? move : this.boardId+move;
    }

    public String toString(){
        String boardStringRep = "\n";
        int boardWidth = this.getBoard().getConfig().getWidth();
        Counter[][] boardCounters = this.getBoard().getCounterPlacements();
        for(int column = 0; column < boardWidth; column++){
                String columnString = Arrays.toString(boardCounters[column]);
                boardStringRep+= "Column: " + column + " " +columnString+"\n";
        }

        return "\nCounter: "+this.counterTurn+" ParentId: " + this.parentId + ", BoardId: " + this.boardId + ", Score: " + this.score.getAnInt() + "\nBoard:"+boardStringRep;
    }
    public String getParentId(){
        return this.parentId;
    }
    public Board getBoard() {
        return board;
    }
    public BooleanInt getScore() {
        return score;
    }
    public String getBoardId() {
        return boardId;
    }


}