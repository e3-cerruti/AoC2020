package com.e3civichigh.aoc.y2020.day24;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class DrawingPanel extends JPanel {
    private final int[][] board;
    //mouse variables here
    //Point mPt = new Point(0,0);

    public DrawingPanel(int[][] board) {
        this.board = board;
        setBackground(com.e3civichigh.aoc.y2020.day24.hexgame.COLOURBACK);

        MyMouseListener ml = new MyMouseListener();
        addMouseListener(ml);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        super.paintComponent(g2);
        //draw grid
        for (int i = 0; i < com.e3civichigh.aoc.y2020.day24.Day24.BSIZE; i++) {
            for (int j = 0; j < com.e3civichigh.aoc.y2020.day24.Day24.BSIZE; j++) {
                hexmech.drawHex(i, j, g2);
            }
        }
        //fill in hexes
        for (int i = 0; i < com.e3civichigh.aoc.y2020.day24.Day24.BSIZE; i++) {
            for (int j = 0; j < com.e3civichigh.aoc.y2020.day24.Day24.BSIZE; j++) {
                //if (board[i][j] < 0) hexmech.fillHex(i,j,COLOURONE,-board[i][j],g2);
                //if (board[i][j] > 0) hexmech.fillHex(i,j,COLOURTWO, board[i][j],g2);
                hexmech.fillHex(i, j, board[i][j], g2);
            }
        }

        //g.setColor(Color.RED);
        //g.drawLine(mPt.x,mPt.y, mPt.x,mPt.y);
    }

    class MyMouseListener extends MouseAdapter {    //inner class inside DrawingPanel
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            //mPt.x = x;
            //mPt.y = y;
            Point p = new Point(hexmech.pxtoHex(e.getX(), e.getY()));
            if (p.x < 0 || p.y < 0 || p.x >= com.e3civichigh.aoc.y2020.day24.Day24.BSIZE || p.y >= com.e3civichigh.aoc.y2020.day24.Day24.BSIZE)
                return;

            //DEBUG: colour in the hex which is supposedly the one clicked on
            //clear the whole screen first.
            /* for (int i=0;i<BSIZE;i++) {
                for (int j=0;j<BSIZE;j++) {
                    board[i][j]=EMPTY;
                }
            } */

            //What do you want to do when a hexagon is clicked?
            board[p.x][p.y] = (int) 'X';
            repaint();
        }
    } //end of MyMouseListener class
} // end of DrawingPanel class
