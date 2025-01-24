package com.thg.accelerator23.connectn.ai.chatght;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.Analysis.boardAnalyser;
import com.thg.accelerator23.connectn.MaxMinImplementation.MaxMinImplementation;

import java.util.Arrays;

public class ChatGHT extends Player {
    public boolean randomPlay = false;
    public ChatGHT(Counter counter) {
      //TODO: fill in your name here
      super(counter, ChatGHT.class.getName());
    }

    @Override
    public int makeMove(Board board) {
      if(randomPlay) {
        return makeRandomMove(board);
      }
      return bestGuess(board);
    }
    public int bestGuess(Board board) {
      MaxMinImplementation maxMin = new MaxMinImplementation(6);
      return maxMin.calculateMove(board,this.getCounter());
    }
    public int makeRandomMove(Board board) {
      boardAnalyser analyser = new boardAnalyser(board);
      int[] columns = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
      int[] allowedColumns = Arrays.stream(columns).filter(column-> analyser.doesColumnHaveSpace(board,column).getaBoolean()).toArray();
      int randomIndex = (int)(Math.random()*allowedColumns.length);
      return allowedColumns[randomIndex];
    }
  }
