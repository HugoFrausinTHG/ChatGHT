package com.thg.accelerator23.connectn.MaxMinImplementation;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thg.accelerator23.connectn.Analysis.boardAnalyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxMinImplementation {
    private int maxDepth = 3;
    public List<boardNode> boardsChecked = new ArrayList<>();
    public Map<String, boardNode> boardsCheckedMap = new HashMap<>();
    public Counter maximizerCounter;

    public MaxMinImplementation(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int calculateMove(Board StartingBoard, Counter counter){
//Make sure node use for scoring is not the node's counter.
        this.maximizerCounter = counter;

        int[] allowedMoves = boardAnalyser.allowedColumns(StartingBoard);
        int[] optimalMove = {-1,-1};
        boardNode parentNode = new boardNode("-1",StartingBoard,"-1",counter.getOther());

        for(int moveIndex=0; moveIndex<allowedMoves.length; moveIndex++){
            boardNode childNode = parentNode.createChild(allowedMoves[moveIndex]);
            int childScore = checkDepthLevel(childNode);
            childNode.setScore(childScore);
            boardsCheckedMap.put(childNode.getBoardId(), childNode);
            System.out.println("Move: "+ allowedMoves[moveIndex] + " Score:" + childScore);
            optimalMove = (optimalMove[1]<childScore)? new int[]{allowedMoves[moveIndex], childScore} : optimalMove;
        }
        System.out.println("Chosen move: "+ optimalMove[0] + " Score:" + optimalMove[1]);
        return optimalMove[0];
    }

    public int checkNodeScore(boardNode node){
        boolean depthReached = node.getBoardId().length() == maxDepth;
        if(depthReached){
            int score = BoardScorer.score(node,maximizerCounter);

//            System.out.println("Depth Reached with node: "+ node.boardId + " Score: " + score+" Counter: " + maximizerCounter);
            return score;
        }
        return checkDepthLevel(node);
    }

    public int checkDepthLevel(boardNode node){
        int[] allowedMoves = boardAnalyser.allowedColumns(node.getBoard());
        int[] childrenScores = new int[allowedMoves.length];

        for(int moveIndex = 0; moveIndex < allowedMoves.length; moveIndex++ ){
            boardNode childNode = node.createChild(allowedMoves[moveIndex]);
            int nodeScore = checkNodeScore(childNode);
            childNode.setScore(nodeScore);
            childrenScores[moveIndex] = nodeScore;
            boardsChecked.add(childNode);
            boardsCheckedMap.put(childNode.getBoardId(), childNode);
        }
        int chosenScore = getOptimalPath(childrenScores,node.counterTurn);
//        System.out.println("Board: "+ node.getBoardId()+" Chosen score: " + chosenScore + " Counter: "+ node.counterTurn);
        return chosenScore;
    }

    public int getOptimalPath(int[] childrenScores, Counter counter){
        return (counter != this.maximizerCounter)? maximizer(childrenScores): minimizer(childrenScores);
    }

    public static int maximizer(int[] scores){
        int maxScore = scores[0];
        for(int score : scores){
            maxScore = (maxScore<score)? score:maxScore;
        }
        return maxScore;
    }
    public static int minimizer(int[] scores){
        int minScore = scores[0];
        for(int score : scores){
            minScore = (minScore<score)? minScore:score;
        }
        return minScore;
    }
}