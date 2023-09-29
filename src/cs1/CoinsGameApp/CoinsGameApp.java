
package cs1.CoinsGameApp;

import  cs1.app.*;

public class CoinsGameApp 
{
    //drawing row of circle
    void drawCirclesRow(double startX, double startY, double radius, int n )
    {
        //initialising position of X and Y
        double curX = startX;
        double curY = startY;
        
        int count = 1;
        
        while( count <= n )
        {
            canvas.drawCircle( curX, curY, radius, "AntiqueWhite" );
            
            canvas.drawText( curX, curY, count, "black" );
            
            //updating current X position
            curX = curX + 2 * radius;
            
            count = count + 1;
        }
    }
    
    void drawGameBoard( int topCoins, int botCoins, double radius )
    {
        //setting two rows at the middle of the height
        double topX = radius;
        double topY = (canvas.getHeight() - radius) / 2;
        
        double botX = topX;
        double botY = topY + 2 * radius;
        
        drawCirclesRow( topX, topY, radius, topCoins );
        drawCirclesRow( botX, botY, radius, botCoins );
    }
    
    int computeRow( double touchY, int numTaps )
    {
        //determining the row selected based on where the cursor touches
        double canvasHeight = canvas.getHeight();
        if( numTaps == 1 && touchY <= (canvasHeight/2) )
        {
            return 1;
        }
        else if( numTaps == 1 && touchY > (canvasHeight/2) )
        {
            return 2;
        }
        //row number is 3 if the number of taps is 2
        else
        {
            return 3;
        }
    }
    
    double computeCoinRadius( int topCoins, int botCoins )
    {
        double screenWidth = canvas.getWidth();
        double coinRadius = 0;
        
        //calculating the radius on the basis of number of coins
        if( topCoins > botCoins )
        {
            coinRadius = screenWidth / ( 2 * topCoins );
        }
        else if( botCoins > topCoins )
        {
            coinRadius = screenWidth /( 2 * botCoins );
        }
        return coinRadius;
    }
    
    void playCoinsGame()
    {
        //getting random number of coins between 10 and 20
        int topCoins = canvas.getRandomInt( 10, 20 );
        int botCoins = canvas.getRandomInt( 10, 20 );
        
        double screenX = canvas.getWidth() / 2;
        double screenY = canvas.getHeight() / 4;
        
        double errorX = canvas.getWidth() / 2;
        double errorY = canvas.getHeight() / 8;
        
        double coinRadius = computeCoinRadius( topCoins, botCoins );
        
        int curPlayer = 1;
        
        while( topCoins > 0 && botCoins > 0 )
        {
            canvas.clear();
            drawGameBoard( topCoins, botCoins, coinRadius );
            
            if( curPlayer == 1 )
            {
                canvas.drawText( screenX, screenY, "BLUE Player's move", "blue" );
                canvas.sleep( 0.5 );
                curPlayer = curPlayer + 1;
            }
            else if( curPlayer == 2 )
            {
                canvas.drawText( screenX, screenY, "RED Player's move", "red" );
                canvas.sleep( 0.5 );
                curPlayer = curPlayer - 1;
            }
            
            Touch touch = canvas.waitForTouch();
            double touchX = touch.getX();
            double touchY = touch.getY();
            int numTaps = touch.getTaps();
            
            int row = computeRow( touchY, numTaps );
            int numCoins = ( int ) ( touchX / ( 2 * coinRadius )) + 1 ;
            
            //error message when number of coins is larger than available ones
            if( (row == 1 && numCoins > topCoins ) || (row == 2 && numCoins > botCoins)) 
            {
                canvas.drawText( errorX, errorY, "Pick a smaller number", "black" );
                canvas.sleep( 1 ) ;
            }
            //error message when number of coins is larger than available on one of the rows
            else if( row == 3 && ( numCoins > topCoins || numCoins > botCoins ) )
            {
                canvas.drawText( errorX, errorY, "Pick a valid number", "black" );
                canvas.sleep( 1 ) ;
            }
            //updating the coins
            else if( row == 1 && numCoins <= 5 )
            {
                topCoins = topCoins - numCoins;
            }
            else if( row == 2 && numCoins <= 5 )
            {
                botCoins = botCoins - numCoins;
            }
            else if( row == 3 && numCoins <= 5 )
            {
                topCoins = topCoins - numCoins;
                botCoins = botCoins - numCoins;
            }
            else
            {
                canvas.drawText( errorX, errorY, "Pick a valid number", "black" );
                canvas.sleep( 1 );
            }
        }
        while( topCoins == 0 || botCoins == 0 )
        {
            canvas.clear();
            //congratulation message
            if( curPlayer == 2 )
            {
                canvas.drawText( screenX, screenY, "Congratulations, Blue Player Wins!", "blue" );
            }
            else if( curPlayer == 1 )
            {
                canvas.drawText( screenX, screenY, "Congratulations, Red Player Wins!", "red" );
            }
            canvas.sleep( 0.5 );
        }
    }
    
    public void run()
    {
        canvas.setLandscape();
        //drawCirclesRow( 20, 80, 20, 5 );
        //drawCirclesRow( 50, 200, 15, 8 );
        
        //drawGameBoard( 10, 12, 9.2 );
        //drawGameBoard( 9, 4, 15.5 );
        
        //System.out.println("radius = " + computeCoinRadius(11, 10)); 
        //System.out.println("radius = " + computeCoinRadius(12, 14)); 
        
        playCoinsGame(); 
    }
}