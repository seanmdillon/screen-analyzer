import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class JInternalFrameDemo extends JFrame
{
    static JInternalFrameDemo jifd;
    static ArrayList<Boolean> moveInternalFrames;
    
    JButton openButton;
    JButton repaintButton;
    JLayeredPane desktop;
    ArrayList<JInternalFrame> internalFrames;
    //JInternalFrame internalFrame;
    public JInternalFrameDemo() 
    {
        super("Internal Frame Demo");
        getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
        internalFrames = new ArrayList<JInternalFrame>();
        moveInternalFrames = new ArrayList<Boolean>();
        //setUndecorated(true);
        setSize(500,400);
        setBackground(new Color(0,0,0,0));
        openButton = new JButton("Open");
        repaintButton = new JButton("Repaint");
        JPanel p = new JPanel();
        p.setBackground(new Color(0,0,0,0.1f));
        p.add(openButton);
        p.add(repaintButton);
        add(p, BorderLayout.SOUTH);
        p.setBackground(new Color(0,0,0,0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openButton.addActionListener(new OpenListener());
        repaintButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jifd.repaint();}});
        desktop = new JDesktopPane();
        desktop.setOpaque(false);
        desktop.setBackground(new Color(0,0,0,0.1f));
        add(desktop, BorderLayout.CENTER);
    }
    class OpenListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            int s = (internalFrames.size()-1)>-1?internalFrames.size()-1:0;
            internalFrames.add(null);
            moveInternalFrames.add(new Boolean(false));
        	if ((internalFrames.get(s)==null))
            {
        		internalFrames.set(internalFrames.indexOf(null),new JInternalFrame("Internal Frame "+(s+1),true,true,true,true));// = new JInternalFrame("Internal Frame", true, true, true, true);
                internalFrames.get(s).setBounds(50, 50, 200, 100);
                desktop.add(internalFrames.get(s));
                internalFrames.get(s).setVisible(true);
                moveInternalFrames.set(s,true);
                jifd.repaint();
            }
        }
    }
    public static void main(String args[])
    {
        jifd = new JInternalFrameDemo();
        jifd.setVisible(true);
        while(true){jifd.repaint();try{Thread.sleep(25);}catch(Exception foff){}}
    }
}