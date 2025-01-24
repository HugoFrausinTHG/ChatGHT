package com.thg.accelerator23.connectn.MaxMinImplementation;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thg.accelerator23.connectn.Analysis.boardAnalyser;
import com.thg.accelerator23.connectn.Analysis.BuiltInAnalysis.BoardAnalyser;
import com.thg.accelerator23.connectn.Analysis.BuiltInAnalysis.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MaxMinImplementation {
    private int maxDepth = 10;
    public List<boardNode> boardsChecked = new ArrayList<>();
    public Map<String, boardNode> boardsCheckedMap = new HashMap<>();
    public Counter maximizerCounter;
    public long startTimeMillis;
    public int maxDepthSearchTime = 700;
    public long cutTimeMillis = 9000;
    public boolean maxDepthUpdated = false;


    public MaxMinImplementation(int maxDepth) {
        this.maxDepth = maxDepth;
        startTimeMillis = System.currentTimeMillis();
    }

    public int calculateMove(Board StartingBoard, Counter counter) {
//Make sure node use for scoring is not the node's counter.
        this.maximizerCounter = counter;

        int[] allowedMoves = boardAnalyser.allowedColumns(StartingBoard);
        int[] optimalMove = {-1, -1000};

        boardNode parentNode = new boardNode("-1", StartingBoard, "-1", counter.getOther());

        for (int moveIndex = 0; moveIndex < allowedMoves.length; moveIndex++) {
            boardNode childNode = parentNode.createChild(allowedMoves[moveIndex]);
            int childScore = checkDepthLevel(childNode);
            childNode.setScore(childScore);
            boardsCheckedMap.put(childNode.getBoardId(), childNode);
            System.out.println("Move: " + allowedMoves[moveIndex] + " Score:" + childScore);
            optimalMove = (optimalMove[1] < childScore) ? new int[]{allowedMoves[moveIndex], childScore} : optimalMove;
        }
        System.out.println("Chosen move: " + optimalMove[0] + " Score:" + optimalMove[1]);

        return optimalMove[0];
    }

    public void updateMaxDepth(int currentDepth) {
        maxDepth = currentDepth;
        maxDepthUpdated = true;
    }

    public int checkNodeScore(boardNode node) {
        BoardAnalyser analysis = new BoardAnalyser(node.getBoard().getConfig());
        GameState gameStatus = analysis.calculateGameState(node.getBoard());

        int currentDepth = node.boardId.length();

        boolean maxDepthTimeReached = System.currentTimeMillis() - startTimeMillis > maxDepthSearchTime;
        if (!maxDepthUpdated && maxDepthTimeReached) {
            updateMaxDepth(currentDepth);
            System.out.println(node.getBoardId());
        }
        boolean timedOut = System.currentTimeMillis() - startTimeMillis > cutTimeMillis;

        boolean depthReached = currentDepth == maxDepth;

        if (depthReached || gameStatus.getWinner() != null || timedOut) {
            int score = BoardScorer.score(node, maximizerCounter, gameStatus);
            return score;
        }
        return checkDepthLevel(node);
    }

    public int checkDepthLevel(boardNode node) {
        int[] allowedMoves = boardAnalyser.allowedColumns(node.getBoard());
        int[] childrenScores = new int[allowedMoves.length];

        for (int moveIndex = 0; moveIndex < allowedMoves.length; moveIndex++) {
            boardNode childNode = node.createChild(allowedMoves[moveIndex]);
            int nodeScore = checkNodeScore(childNode);
            childNode.setScore(nodeScore);
            childrenScores[moveIndex] = nodeScore;
            boardsChecked.add(childNode);
            boardsCheckedMap.put(childNode.getBoardId(), childNode);
        }
        int chosenScore = getOptimalPath(childrenScores, node.counterTurn);
//        System.out.println("Board: "+ node.getBoardId()+" Chosen score: " + chosenScore + " Counter: "+ node.counterTurn);
        return chosenScore;
    }

    public int getOptimalPath(int[] childrenScores, Counter counter) {
        return (counter != this.maximizerCounter) ? maximizer(childrenScores) : minimizer(childrenScores);
    }

    public static int maximizer(int[] scores) {
        int maxScore = scores[0];
        for (int score : scores) {
            maxScore = (maxScore < score) ? score : maxScore;
        }
        return maxScore;
    }

    public static int minimizer(int[] scores) {
        int minScore = scores[0];
        for (int score : scores) {
            minScore = (minScore < score) ? minScore : score;
        }
        return minScore;
    }
}