package com.wikispaces.es1011.gamemenu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.SurfaceView;


public class game_BrickMatrix extends game_Sprite2D {

    private Brick[][] brickMatrix;
    private int matrixRow, matrixColumn;
    private int brickCount;
    protected Bitmap brick1, brick2, brick3 ,brick4;



    public game_BrickMatrix(int canvasW, int canvasH, SurfaceView sw, int brickNum) {
        super(canvasW, canvasH);
        this.brickCount = brickNum;
        
        brick1 = BitmapFactory.decodeResource(sw.getResources(), R.drawable.game_brick1);
        brick2 = BitmapFactory.decodeResource(sw.getResources(), R.drawable.game_brick2);
        brick3 = BitmapFactory.decodeResource(sw.getResources(), R.drawable.game_brick3);
        brick4 = BitmapFactory.decodeResource(sw.getResources(), R.drawable.game_brick4);


        brick1 = Bitmap.createScaledBitmap(brick1, CanvasW/10, CanvasH/20, true);
        brick2 = Bitmap.createScaledBitmap(brick2, CanvasW/10, CanvasH/20, true);
        brick3 = Bitmap.createScaledBitmap(brick3, CanvasW/10, CanvasH/20, true);
        brick4 = Bitmap.createScaledBitmap(brick4, CanvasW/10, CanvasH/20, true);

        matrixColumn = (canvasW/brick1.getWidth());
        matrixRow =  brickNum/matrixColumn;
        brickMatrix = new Brick[matrixColumn][matrixRow];

        double random = new java.util.Random().nextInt(10);

        for (int j = 0; j <matrixRow ; j++) {
            for (int i = 0; i < matrixColumn; i++) {
                brickMatrix[i][j] = new Brick(canvasW, canvasH);

                if (random > 7.5) {
                    brickMatrix[i][j].init(brick1, brick1.getWidth(), brick1.getHeight(), i * brick1.getWidth() , j * brick1.getHeight());
                    brickMatrix[i][j].initBound(brickMatrix[i][j]);
                } else if (random > 5.0) {
                    brickMatrix[i][j].init(brick2, brick1.getWidth(), brick1.getHeight(), i * brick1.getWidth() , j * brick1.getHeight());
                    brickMatrix[i][j].initBound(brickMatrix[i][j]);
                } else if (random > 2.5) {
                    brickMatrix[i][j].init(brick3, brick1.getWidth(), brick1.getHeight(), i * brick1.getWidth() , j * brick1.getHeight());
                    brickMatrix[i][j].initBound(brickMatrix[i][j]);
                } else {
                    brickMatrix[i][j].init(brick4, brick1.getWidth(), brick1.getHeight(), i * brick1.getWidth() , j * brick1.getHeight());
                    brickMatrix[i][j].initBound(brickMatrix[i][j]);
                }
                random = new java.util.Random().nextInt(10);
            }
        }
    }

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

    public class Brick extends game_Sprite2D {

    private boolean visible = true;
    public int Xmin, Xmax, Ymin, Ymax;
    private Rect box;

    public Brick(int CanvasW, int CanvasH) {
        super(CanvasW, CanvasH);

    }
    public void initBound(Brick br){
    box  = new Rect(br.getXPos(),br.getYPos(),br.getXPos() + brick1.getWidth(),br.getYPos() +  brick1.getHeight());}

    public void Update(long GameTime, boolean visible) {
        this.visible = visible;
        if (!visible) {
            box.set(-1,-1,-1,-1);
        }
    }

    public boolean isVisible() {
        return visible;
    }
    public Rect getBox(){
    return box;}
}
}
