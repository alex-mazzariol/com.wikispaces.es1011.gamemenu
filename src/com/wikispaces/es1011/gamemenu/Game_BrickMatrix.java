package com.wikispaces.es1011.gamemenu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

public class Game_BrickMatrix {

	//TODO Cancellare questa classe
	//private Brick[][] brickMatrix;
	private int matrixRow, matrixColumn;
	private int brickCount;
	protected Bitmap brick1, brick2, brick3, brick4;
	private Game_Status gs;

	public Game_BrickMatrix(int canvasW, int canvasH, SurfaceView sw,
			int brickNum, Game_Status gs) {
		/*this.gs = gs;
		this.brickCount = brickNum;

		brick1 = BitmapFactory.decodeResource(sw.getResources(),
				R.drawable.game_brick1);
		brick2 = BitmapFactory.decodeResource(sw.getResources(),
				R.drawable.game_brick2);
		brick3 = BitmapFactory.decodeResource(sw.getResources(),
				R.drawable.game_brick3);
		brick4 = BitmapFactory.decodeResource(sw.getResources(),
				R.drawable.game_brick4);

		brick1 = Bitmap.createScaledBitmap(brick1, canvasW / 10, canvasH / 20,
				true);
		brick2 = Bitmap.createScaledBitmap(brick2, canvasW / 10, canvasH / 20,
				true);
		brick3 = Bitmap.createScaledBitmap(brick3, canvasW / 10, canvasH / 20,
				true);
		brick4 = Bitmap.createScaledBitmap(brick4, canvasW / 10, canvasH / 20,
				true);

		matrixColumn = (canvasW / brick1.getWidth());
		matrixRow = brickNum / matrixColumn;
		brickMatrix = new Brick[matrixColumn][matrixRow];
		double random = new java.util.Random().nextInt(10);
		for (int j = 0; j < matrixRow; j++) {
			for (int i = 0; i < matrixColumn; i++) {
				if (random > 7.5) {
					brickMatrix[i][j] = new Brick(i * brick1.getWidth(), i
							* brick1.getWidth() + brick1.getWidth(), j
							* brick1.getHeight(), j * brick1.getHeight()
							+ brick1.getHeight(), brick1);
				} else if (random > 5.0) {
					brickMatrix[i][j] = new Brick(i * brick1.getWidth(), i
							* brick1.getWidth() + brick1.getWidth(), j
							* brick1.getHeight(), j * brick1.getHeight()
							+ brick1.getHeight(), brick2);
				} else if (random > 2.5) {
					brickMatrix[i][j] = new Brick(i * brick1.getWidth(), i
							* brick1.getWidth() + brick1.getWidth(), j
							* brick1.getHeight(), j * brick1.getHeight()
							+ brick1.getHeight(), brick3);
				} else {
					brickMatrix[i][j] = new Brick(i * brick1.getWidth(), i
							* brick1.getWidth() + brick1.getWidth(), j
							* brick1.getHeight(), j * brick1.getHeight()
							+ brick1.getHeight(), brick4);
				}
				random = new java.util.Random().nextInt(10);
			}
		}*/
	}
/*
	public Brick getBrick(int row, int column) {
		return brickMatrix[row][column];
	}

	public int getRow() {
		return matrixRow;
	}

	public int getColumn() {
		return matrixColumn;
	}

	public int getBrickCount() {
		return brickCount;
	}

	public void decreaseBrickCount() {
		brickCount--;
	}

	public void Update(long GameTime) {
	}

	public void draw(Canvas canvas) {
		for (int j = 0; j < matrixRow; j++) {
			for (int i = 0; i < matrixColumn; i++) {
				if (this.getBrick(i, j).isVisible())
					canvas.drawBitmap(getBrick(i, j).getColor(), getBrick(i, j)
							.getBox(), getBrick(i, j).getBox(), null);
			}
		}
	}

	
	public class Brick {

		
		private Bitmap color;

		public Brick(int Xmin, int Xmax, int Ymin, int Ymax, Bitmap color) {
			this.color = color;
			gs.setBrickVisible(true);
			gs.setBrickXmin(Xmin);
			gs.setBrickXmax(Xmax);
			gs.setBrickYmin(Ymin);
			gs.setBrickYmax(Ymax);
			gs.setBrickBox(new Rect(Xmin, Ymin, Xmax, Ymax));
		}

		public Bitmap getColor() {
			return color;
		}

		public boolean isVisible() {
			return gs.isBrickVisible();
		}

		public void setVisible(boolean brickVisible) {
			gs.setBrickVisible(brickVisible);
		}
		
		public Rect getBox() {
			return gs.getBrickBox();
		}
		public void setBox(Rect rect) {
			gs.setBrickBox(rect);
		}
		
	}*/
}