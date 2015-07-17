//Cameron D.
//originally copied from somewhere
import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

@SuppressWarnings("serial")
class StringyPanel extends JPanel
{
	public Graphics2D g2d;
	public StringyPanel()
	{
		setBackground(Color.RED);
	}
	public void paintComponent(Graphics g) 
	{
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawString("W8 w0t",50,50);
    }
}

public class Main 
{
    static int red;
    static int green;
    static int blue;
    static int xCoordWhereColorWasFound;
    static int yCoordWhereColorWasFound;
    static Color color;
    static Robot robot;
    static JFrame frame;
    private static int[] outputColors(int x,int y, boolean wait,boolean output)
    {
    	try
    	{
    		if(wait)Thread.sleep(500);
        	color = robot.getPixelColor(x,y);
    		red = color.getRed();
    		green = color.getGreen();
    		blue = color.getBlue();
        	if(output)System.out.print("Red: " + red+", Green: " + green+", Blue: " + blue);
        	if(wait)System.out.print("\n");
        	if(wait)Thread.sleep(500);
        	return new int[]{red,green,blue};
    	}
    	catch(Exception uwot)
    	{
    		uwot.printStackTrace();
    	}
    	return new int[]{-1,-1,-1};
    }
    private static void checkForColor(int r,int g,int b)
    {
    	int[] oml = new int[]{r,g,b};
    	int failures = 0;
    	for(int x=0; x<=1920; x++)
    	{
    		for(int y=0; y<=1200; y++)
    		{
    			if(Arrays.equals(outputColors(x,y,false,true),oml))
    			{
    				System.out.println(" (WE GOT IT AT x: "+x+" y: "+y+" failures: "+failures+")");
    				xCoordWhereColorWasFound = x;
    				yCoordWhereColorWasFound = y;
    				return;
    			}
    			else
    			{
    				System.out.print(" (we didn't got it, checked "+x+" "+y+")\n");
    				failures++;
    			}
    		}
    	}
    }
    private static boolean rangeOfColorConsistent(int x1,int y1,int x2,int y2)
    {
    	//int numPixelsInRange = (x2-x1) * (y2-y1); //30,30 to 60,60 would be 30 by 30 blocks which should be 900
    	int[] check = new int[3];
    	outputColors(x1,y1,false,false);
    	check[0] = red;
    	check[1] = green;
    	check[2] = blue;
    	for(int x=x1; x<=x2-1; x++)
    	{
    		for(int y=y1; y<=y2-1; y++)
    		{
    			outputColors(x,y,false,false);
    			if(!((check[0]==red)&&(check[1]==green)&&(check[2]==blue)))
    			{
    				System.out.println("found discontinuity when comparing colors: "+(check[0]==red)+(check[1]==green)+(check[2]==blue)+x+" "+y);
    				return false;
    			}
    		}
    	}
    	return true;
    }
    private static void customForeverCheckColorRange()
    {
    	
    }
//    private static void recordInstancesOfColor(int r,int g,int b,int x1,int y1,int x2,int y2)
//    {
//    	int[][]
//    }
	public static void main(String[] args) 
    {
        frame = new JFrame("DISTRACTION");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setLocation(3,35);
        frame.setContentPane(new StringyPanel());
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.setVisible(true);
        try
        {
        	robot = new Robot();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	System.exit(1);
        }
        checkForColor(255,0,0);
        System.out.println("x: "+xCoordWhereColorWasFound+" y: "+yCoordWhereColorWasFound);
        outputColors(xCoordWhereColorWasFound,yCoordWhereColorWasFound,true,true);
    }
}