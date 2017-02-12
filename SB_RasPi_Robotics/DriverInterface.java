package SB_RasPi_Robotics;

import SB_RasPi_Robotics.CodeBase.RobotControlManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DriverInterface extends KeyAdapter
{
	private Component component;
	private int deltaX;
	private int deltaY;
	private static RobotControlManager robotControlManager;
	private static JLabel speedLabel;
	private static JLabel steeringLabel;
	private static JLabel distanceLabel;



	public DriverInterface(Component component, int deltaX, int deltaY)
	{
		robotControlManager = new RobotControlManager();
		this.component = component;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		
	}

	//  Move the component to its new location. The component will stop
	//  moving when it reaches the bounds of its container

	
	@Override
	public void keyPressed(KeyEvent e)
	{
		
		/*
		Key event options
		*/
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			robotControlManager.steerLeftOrRight(10);
			steeringLabel.setText("Steering: " + robotControlManager.getSteerValue());
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			robotControlManager.steerLeftOrRight(-10);
			steeringLabel.setText("Steering: " + robotControlManager.getSteerValue());

		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			robotControlManager.increaseMotorSpeed(10);
			speedLabel.setText("Motor: " + robotControlManager.getMotorSpeed());
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			robotControlManager.increaseMotorSpeed(-10);
			speedLabel.setText("Motor: " + robotControlManager.getMotorSpeed());

		} else if (e.getKeyCode() == KeyEvent.VK_SPACE){
			robotControlManager.setMotorSpeed(0);
			speedLabel.setText("Motor: " + robotControlManager.getMotorSpeed());
			

		} else if (e.getKeyCode() == KeyEvent.VK_1){
			robotControlManager.increaseHeadlights();
			
            
        } else if (e.getKeyCode() == KeyEvent.VK_2){
            robotControlManager.decreaseHeadlights();
            
        } else if (e.getKeyCode() == KeyEvent.VK_A){
            robotControlManager.steerLeftOrRight(100);
            steeringLabel.setText("Steering: " + robotControlManager.getSteerValue());
            
        } else if (e.getKeyCode() == KeyEvent.VK_D){
            robotControlManager.steerLeftOrRight(-100);
            steeringLabel.setText("Steering: " + robotControlManager.getSteerValue());

		} 
		
	}
	
	

	private static JButton addMotionSupport(Component component)
	{
		KeyListener keyListener = new DriverInterface(component, 3, 3);
		component.addKeyListener(keyListener);
		component.setFocusable(true);

		return new JButton("LEFT");
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				//  Create a panel with a component to be moved

				JPanel content = new JPanel();
				content.setLayout(null);
	
				speedLabel = new JLabel("0");
				steeringLabel = new JLabel("0");
				distanceLabel = new JLabel("d:");


				JLabel component = new JLabel( new ColorIcon(Color.BLUE, 40, 40) );
				component.setSize( component.getPreferredSize() );
				component.setLocation(1000, 1000);
								content.add(component);
				
			
				JButton left = addMotionSupport( component );

				JFrame.setDefaultLookAndFeelDecorated(true);
				JFrame frame = new JFrame("Motion With Key Listener");
				frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
				frame.add( content );
				frame.add(left, BorderLayout.SOUTH);
				frame.add(speedLabel,BorderLayout.WEST);
				frame.add(steeringLabel,BorderLayout.EAST);
				frame.add(distanceLabel,BorderLayout.NORTH);

				frame.setSize(200, 200);
				frame.setLocationByPlatform( true );
				frame.setVisible(true);
			}
		});
	}

	static class ColorIcon implements Icon
	{
		private Color color;
		private int width;
		private int height;

		public ColorIcon(Color color, int width, int height)
		{
			this.color = color;
			this.width = width;
			this.height = height;
		}

		public int getIconWidth()
		{
			return width;
		}

		public int getIconHeight()
		{
			return height;
		}

		public void paintIcon(Component c, Graphics g, int x, int y)
		{
			g.setColor(color);
			g.fillRect(x, y, width, height);
		}
	}
}
