import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Arrays;
import javax.swing.*;

class ISHPanel extends JPanel
{
	public ISHPanel()
	{
		setLayout(null);
		setOpaque(false);
		setFocusable(false);
		JButton button = new JButton("lock");
		button.setFocusable(false);
		button.addActionListener(new Listener());
		add(button);
		button.setBounds(1840,20,60,60);
	}
	private void drawShadowString(Graphics g,String str, int x, int y, Color textColor, int size, int offset)
	{
		/*myBuffer*/g.setFont(new Font("Comic Sans MS",Font.PLAIN,size));
		/*myBuffer*/g.setColor(Color.BLACK);
		/*myBuffer*/g.drawString(str,x+offset,y+offset);
		/*myBuffer*/g.setColor(textColor);
		/*myBuffer*/g.drawString(str,x,y);
		/*myBuffer*/g.setColor(new Color(0,0,0,0.1f));
	}
	public void paintComponent(final Graphics g)
	{
		//myBuffer.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
		super.paintComponent(g);
		/*myBuffer*/g.setColor(new Color(0,0,0,0.1f));
		/*myBuffer*/g.fillRect(0, 0, InvisibleScreenHog.startWidth, InvisibleScreenHog.startHeight);
        /*myBuffer*/g.setColor(Color.BLUE);
        /*myBuffer*/g.fillRect(0,0,1920,1130);
        /*myBuffer*/g.clearRect(5,5,1910,1120);
        drawShadowString(g,"TESTING",100,100,Color.BLUE,90,5);
        Thread t = new Thread(new Runnable(){public void run(){repeatablePaintComponent(g);}});
    }
	
	private void repeatablePaintComponent(Graphics g)
	{
		//g.fillRect(0, 0, getWidth(),getHeight());
		Color original = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(800,800,50,50);
		g.setColor(original);
	}
	
	private static class Listener implements ActionListener
	{
    	private String[] getImportantDetails(ActionEvent ae)
    	{
    		String[] details = new String[]{((""+ae).contains(""))?"yes":"no",};
    		return details;
    	}
    	public boolean _contains(String[] container,String contain)
    	{
    		if(Arrays.asList(container).contains(contain))
    			return true;
    		return false;
    	}
		public void actionPerformed(ActionEvent ae)
        {
        	System.out.println("aeevent : "+ae);
        }
	}
}

public class InvisibleScreenHog extends JFrame implements KeyListener,MouseMotionListener
{
	public static boolean shiftPressed = false;
	private static int startX = 0;
	private static int startY = 23;
	public static int startWidth = 1920;
	public static int startHeight = 1130;
	final static InvisibleScreenHog ish = new InvisibleScreenHog();
	private static ISHPanel ishpanel;
	private static int ax;
	private static int ay;
	
	public InvisibleScreenHog()
    {
        super("Shouldn't see this");
        setSize(startWidth,startHeight);
        setLocation(startX,startY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        addMouseMotionListener(this);
    }
    
    private static class Listener implements AWTEventListener 
    {
    	private String[] getImportantDetails(AWTEvent awte)
    	{
    		String[] details = new String[]{((""+awte).contains("on javax.swing.JButton"))?"button":"no button",((""+awte).contains("MOUSE_CLICKED"))?"mouseclick":"no mouseclick",((""+awte).contains("MOUSE_ENTERED"))?"mouseentry":"no mouseentry",((""+awte).contains("MOUSE_EXITED"))?"mouseexit":"no mouseexit"};
    		return details;
    	}
    	public boolean _contains(String[] container,String contain)
    	{
    		if(Arrays.asList(container).contains(contain))
    			return true;
    		return false;
    	}
    	public void eventDispatched(AWTEvent awte) 
        {
            //System.out.println("awtevent, mouseinfo : "+MouseInfo.getPointerInfo().getLocation() + " | ");
    		if(_contains(getImportantDetails(awte),"no button")&&_contains(getImportantDetails(awte),"mouseexit")&&!shiftPressed)
    		{
    			System.out.println("Mouse left screen");
    		}
        }
    }

    public static void main(String[] args)
    {
    	Toolkit.getDefaultToolkit().addAWTEventListener(new Listener(), AWTEvent.MOUSE_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
        ishpanel = new ISHPanel();
        ishpanel.setBackground(new Color(0,0,0,0.1f));
        ish.setBackground(new Color(0,0,0,0f));
        ish.setContentPane(ishpanel);
        ish.setUndecorated(true);
        ish.setAlwaysOnTop(false);
        ish.setResizable(false);
        Thread shiftresizer = new Thread
        (
        	new Runnable()
        	{
        		int shiftdoubleclickforreset = 20;
        		boolean doubleshifthelper = true;
        		public void run()
        		{
        			System.out.println("[thread t] omg letsago");
        			while(true)
        			{
        				ax = MouseInfo.getPointerInfo().getLocation().x;
        				ay = MouseInfo.getPointerInfo().getLocation().y;
        				if(InvisibleScreenHog.shiftPressed==true)
        				{
            				if(shiftdoubleclickforreset>0)
            					shiftdoubleclickforreset--;
            				if(shiftdoubleclickforreset==20)
            					doubleshifthelper = true;
        					if((shiftdoubleclickforreset<20)&&!(doubleshifthelper==false))
        					{
        						ish.setBounds(ish.getX(),ish.getY(),ax>ish.getX()+100?ax-ish.getX():100,ay>ish.getY()+100?ay-ish.getY():100);
        					}
        				}
        				else
        				{
        					if(shiftdoubleclickforreset<20)
        					{
        						shiftdoubleclickforreset++;
        						doubleshifthelper=false;
        					}
        					else
        						doubleshifthelper=true;
        				}
        				if(shiftdoubleclickforreset<20&&!doubleshifthelper)
        				{
        					if(InvisibleScreenHog.shiftPressed==true)
        					{
        						ish.setBounds(startX,startY,startWidth,startHeight);
        						try{Thread.sleep(500);}catch(Exception f){f.printStackTrace();}
        					}
        				}
    					try{Thread.sleep(5);}catch(Exception f){f.printStackTrace();}
        			}
        		}
        	}
        );
        ish.setVisible(true);
        shiftresizer.start();
    }
	public void keyPressed(KeyEvent ke) { if(ke.getKeyCode()==KeyEvent.VK_SHIFT) { shiftPressed=true; } }
	public void keyReleased(KeyEvent ke) { if(ke.getKeyCode()==KeyEvent.VK_SHIFT) { shiftPressed=false; } }
	public void keyTyped(KeyEvent ke) 
	{}
	public void mouseDragged(MouseEvent me) 
	{}
	public void mouseMoved(MouseEvent me) 
	{
//		ishpanel.updateInternalFrameLocation();
//		System.out.println(ishpanel.dragdefender.getBounds());
		Color temp = ish.getGraphics().getColor();
		ish.getGraphics().clearRect(ax-40, ay-60, 90, 90);
		ish.getGraphics().setColor(new Color(0,0,0,0f));
		ish.getGraphics().fillRect(ax-20, ay-33, 40, 40);
		ish.getGraphics().setColor(temp);
	}
}