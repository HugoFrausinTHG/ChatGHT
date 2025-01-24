package com.thg.accelerator23.connectn.ai.chatght;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.Analysis.boardAnalyser;
import com.thg.accelerator23.connectn.MaxMinImplementation.MaxMinImplementation;

import java.util.Arrays;

public class ChatGHT extends Player {
    public boolean randomPlay = true;
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
        String[] comments = {
                "No thoughts, just vibes",
                "All chill, no spill",
                "Tranquility on repeat",
                "Taking it slow, enjoying the flow",
                "Brain off, bliss on",
                "Mind at ease, heart at peace",
                "Coasting on cloud nine",
                "Drifting in the breeze",
                "Gentle waves, endless days",
                "No rush, just hush",
                "Peaceful mind, happy soul",
                "Sunshine and slow time",
                "Watch the clouds, let worries go",
                "Floating like a daydream",
                "Relax, release, recharge"
        };
      int randomIndex2 = (int)(Math.random()*comments.length);

      System.out.println(comments[randomIndex2]);
      return allowedColumns[randomIndex];
    }
  }
