package com.thg.accelerator23.connectn.ai.chatght;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator20.connectn.ai.lewisivinson.AIvinson;

public class ChatGHT extends Player {
  private Counter myCounter;
  public ChatGHT(Counter counter) {
    //TODO: fill in your name here
    super(counter, ChatGHT.class.getName());
    this.myCounter = counter;
  }

  @Override
  public int makeMove(Board board) {
    System.out.println("I am going to ask Lewis' bot for help");
    Player betterBot = new AIvinson(myCounter);
    return betterBot.makeMove(board);
  }
}
