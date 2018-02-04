package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import entities.MyShip;
import entities.EvilHead;
import items.VolButt;

@SuppressWarnings("serial")
public class Console extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	static String out = "";    

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Console frame = new Console();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
		
	public Console() {
		setUndecorated(true);
        pack();
        setTitle("Game Console");
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		setTextField(new JTextField());
		getTextField().setFont(getTextField().getFont().deriveFont(14f));
		
		
		String[] commands = new String[17];
		commands[0] = "help";
		commands[1] = "cls";
		commands[2] = "refresh";
		commands[3] = "pause";
		commands[4] = "easy";
		commands[5] = "med";
		commands[6] = "hard";
		commands[7] = "exit";
		commands[8] = "voloff";
		commands[9] = "volon";
		commands[10] = "god";
		commands[11] = "dog";
		commands[12] = "stats";
		commands[13] = "restart";
		commands[14] = "level2";
		commands[15] = "level3";
		commands[16] = "manual";


		getTextField().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				int key = e.getKeyCode();
				
				if (key == KeyEvent.VK_ENTER && textArea.getText().trim().length() == 0) {
					textArea.append(getTextField().getText().toUpperCase() + "\n");
					getTextField().setText("");
					getTextField().requestFocusInWindow();
					
					
					if (commands[0].trim().equalsIgnoreCase(textArea.getText().trim())){
							textArea.append("********HERE IS A LIST OF ALL AVAILABLE COMMANDS:*********" + "\n");
							textArea.append("HELP - show this list" + "\n");
							textArea.append("EASY - restart/resume (if not in a game), or switch to EASY" + "\n");
							textArea.append("MED - restart/resume (if not in a game), or switch to MEDIUM" + "\n");
							textArea.append("HARD - restart/resume (if not in a game), or switch to HARD" + "\n");
							textArea.append("RESTART - restart the game, even if a game is running" + "\n");
							textArea.append("PAUSE - game pause" + "\n");
							textArea.append("GOD - enable Godmode" + "\n");
							textArea.append("DOG - disable Godmode" + "\n");
							textArea.append("LEVEL2 - skip to Level 2" + "\n");
							textArea.append("LEVEL3 - skip to Level 3" + "\n");
							textArea.append("VOLOFF - stop game music" + "\n");
							textArea.append("VOLON - play game music" + "\n");
							textArea.append("STATS - show multiple game stats" + "\n");
							textArea.append("MANUAL - show the manual" + "\n");
							textArea.append("CLS - close console" + "\n");
							textArea.append("EXIT - exit game" + "\n");
							return;
					}
					
					if (commands[1].trim().equalsIgnoreCase(textArea.getText().trim())){
						Board.consoleON = false;
						dispose();
						textArea.append("********Closing...*********" + "\n");
						return;
					}
					
					
					if (commands[2].trim().equalsIgnoreCase(textArea.getText().trim())){
						
						// Create operating system process from arpe.bat file command
						ProcessBuilder pb = new ProcessBuilder("arpe.bat");  
			 
						pb.redirectErrorStream();
						// Starts a new process using the attributes of this process builder                            
						Process p = null;
						try {
							p = pb.start();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}				
							 
						BufferedReader br = new BufferedReader (new InputStreamReader(p.getInputStream()));
			 
						// String out is used to store output of this command(process)
						                                          		    	     
			 
						while(true)
						{
							String l=null;
							try 
							{
								l = br.readLine();
							} 
							catch (IOException ex) {}
							if(l==null)
								break;
							out+="\n"+l;
						}
			 
						// A compiled representation of a regular expression
						Pattern pattern = 
			Pattern.compile(".*\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b"); 
			 
						/* An engine that performs match operations on a character sequence by interpreting a Pattern */
						Matcher match = pattern.matcher(out);			
			 
						out="";
						String pLoc;
						
						
						if(!(match.find()))        // In case no IP address Found in out
							out="No IP found!";
			 
						else
						{
			 
							/* Returns the input subsequence matched by the previous match in this case IP of our interface */
							pLoc = match.group();  
			                    
							out+= pLoc;
							while(match.find())	 
							{
								pLoc = match.group();	// Returns the IP of other hosts
								out+=pLoc + "\n";
							}
							try 
							{
								br.close();
							} 
							catch (IOException ex) {}
						}
						
						textArea.append("\n" + "********List of all local IPs:*********" + "\n" + "\n");
						textArea.append(out);	
						
						return;
					}
					
					
					if (commands[3].trim().equalsIgnoreCase(textArea.getText().trim())){
						if(Board.ingame == true){
							Board.timerEasy.stop();
							Board.timerMedium.stop();
							Board.timerHard.stop();
							Board.bgMusic.stop();
							Board.roar.stop();
							textArea.append("********Game was paused!*********" + "\n");
						}
						if(Board.ingame == false){
							textArea.append("***********WARNING: Not in a game!*********");
						}
						return;
					}
					
					
					if (commands[4].trim().equalsIgnoreCase(textArea.getText().trim())){
						Board.timerMedium.stop();
		        		Board.timerHard.stop();
		        		Board.timerEasy.start();
		        		Board.bgMusic.loop();
		        		textArea.append("********Game difficulty: EASY!*********" + "\n");
		            	if(Board.aliens.isEmpty()){
		            		Board.roar.loop();
		            	}
		            	if(Board.ingame == false){
		            		Board.bg1 = Toolkit.getDefaultToolkit().createImage("tenor.gif");
		            		Board.ingame = true;
		            		Board.lifeEvilHead = 3;
		            		Board.lifeMyShip = 3;
			    	        
		            		setPreferredSize(new Dimension(1310, 1040));

		            		Board.myShip = new MyShip(40, 180);
		            		Board.myShip.isVisible();
			    	        
		            		Board.evilHead = new EvilHead(640, 180);
		            		Board.evilHead.isVisible();
		            		Board.evilHead.AIOnMedium();
			    	        
		            		Board.volButt = new VolButt(940, 15);
		            		Board.volButt.isVisible();

		            		Board.initAliens();
		            		Board.initGifts();
		            		Board.initBoss();
		            		Board.initHealth();
			    	        
		            		Board.timerMedium.stop();
		            		Board.timerHard.stop();;
		            		Board.timerEasy.restart();
		            		Board.gameWon.play();
		            		Board.gameLost.stop();
		            		Board.bgMusic.loop();
		            		Board.roar.stop();
		            		return;
					}
		            	
		            	return;
				}
					
					if (commands[5].trim().equalsIgnoreCase(textArea.getText().trim())){
						Board.timerEasy.stop();
		        		Board.timerHard.stop();
		        		Board.timerMedium.start();
		        		Board.bgMusic.loop();
		        		textArea.append("********Game difficulty: MEDIUM!*********" + "\n");
		            	if(Board.aliens.isEmpty()){
		            		Board.roar.loop();
		            	}
		            	if(Board.ingame == false){
		            		Board.bg1 = Toolkit.getDefaultToolkit().createImage("tenor.gif");
		            		Board.ingame = true;
		            		Board.lifeEvilHead = 3;
		            		Board.lifeMyShip = 3;
			    	        
		            		setPreferredSize(new Dimension(1310, 1040));

		            		Board.myShip = new MyShip(40, 180);
		            		Board.myShip.isVisible();
			    	        
		            		Board.evilHead = new EvilHead(640, 180);
		            		Board.evilHead.isVisible();
		            		Board.evilHead.AIOnMedium();
			    	        
		            		Board.volButt = new VolButt(940, 15);
		            		Board.volButt.isVisible();

		            		Board.initAliens();
		            		Board.initGifts();
		            		Board.initBoss();
		            		Board.initHealth();
			    	        
		            		Board.timerEasy.stop();
		            		Board.timerHard.stop();;
		            		Board.timerMedium.restart();
		            		Board.gameWon.play();
		            		Board.gameLost.stop();
		            		Board.bgMusic.loop();
		            		Board.roar.stop();
		            		return;
		            	}
		            	return;
					}
					
					if (commands[6].trim().equalsIgnoreCase(textArea.getText().trim())){
						Board.timerMedium.stop();
		        		Board.timerEasy.stop();
		        		Board.timerHard.start();
		        		Board.bgMusic.loop();
		        		textArea.append("********Game difficulty: HARD!*********" + "\n");
		            	if(Board.aliens.isEmpty()){
		            		//Attack.loop();
		            		Board.roar.loop();
		            	}
		            	if(Board.ingame == false){
		            		Board.bg1 = Toolkit.getDefaultToolkit().createImage("tenor.gif");
		            		Board.ingame = true;
		            		Board.lifeEvilHead = 3;
		            		Board.lifeMyShip = 3;
			    	        
		            		setPreferredSize(new Dimension(1310, 1040));

		            		Board.myShip = new MyShip(40, 180);
		            		Board.myShip.isVisible();
			    	        
		            		Board.evilHead = new EvilHead(640, 180);
		            		Board.evilHead.isVisible();
		            		Board.evilHead.AIOnMedium();
			    	        
		            		Board.volButt = new VolButt(940, 15);
		            		Board.volButt.isVisible();

		            		Board.initAliens();
		            		Board.initGifts();
		            		Board.initBoss();
		            		Board.initHealth();
			    	        
		            		Board.timerEasy.stop();
		            		Board.timerMedium.stop();;
		            		Board.timerHard.restart();
		            		Board.gameWon.play();
		            		Board.gameLost.stop();
		            		Board.bgMusic.loop();
			    	        //Attack.stop();
		            		Board.roar.stop();
		            		return;
		            	}
		            	return;
					}
					
					
					if (commands[7].trim().equalsIgnoreCase(textArea.getText().trim())){
						System.exit(0);
						textArea.append("********Exiting...*********" + "\n");
						return;
					}
					
					if (commands[8].trim().equalsIgnoreCase(textArea.getText().trim())){
						if(Board.timerEasy.isRunning() == true || 
								Board.timerMedium.isRunning() == true ||
								Board.timerHard.isRunning() == true){
							Board.bgMusic.stop();
							textArea.append("********MUSIC IS OFF*********" + "\n");
							return;
						}
						if(Board.ingame == false || (Board.timerEasy.isRunning() == false && 
								Board.timerMedium.isRunning() == false &&
								Board.timerHard.isRunning() == false)){
							textArea.append("********WARNING: Not in a game!*********" + "\n");
							return;
						}
						
						return;
					}
					
					if (commands[9].trim().equalsIgnoreCase(textArea.getText().trim())){
						if(Board.timerEasy.isRunning() == true || 
								Board.timerMedium.isRunning() == true ||
								Board.timerHard.isRunning() == true){
							Board.bgMusic.loop();
							textArea.append("********MUSIC IS ON*********" + "\n");
							return;
						}
						if(Board.ingame == false || (Board.timerEasy.isRunning() == false && 
								Board.timerMedium.isRunning() == false &&
								Board.timerHard.isRunning() == false)){
							textArea.append("********WARNING: Not in a game!*********" + "\n");
							return;
						}
						return;
					}
					
					if (commands[10].trim().equalsIgnoreCase(textArea.getText().trim())){
						if((Board.timerEasy.isRunning() == true || 
								Board.timerMedium.isRunning() == true ||
								Board.timerHard.isRunning() == true) && Board.lifeMyShip >= 3){
							Board.god = true;
							Board.lifeMyShip = -999;
							textArea.append("********GODMODE ON*********" + "\n");
							return;
						}
						if(Board.ingame == true && Board.lifeMyShip < 3){
							textArea.append("***********Already in GODMODE!*********" + "\n");
							return;
						}
						if(Board.ingame == false || (Board.timerEasy.isRunning() == false && 
								Board.timerMedium.isRunning() == false &&
								Board.timerHard.isRunning() == false)){
							textArea.append("********WARNING: Not in a game!*********" + "\n");
							return;
						}
						return;
					}
					
					if (commands[11].trim().equalsIgnoreCase(textArea.getText().trim())){
						if((Board.timerEasy.isRunning() == true || 
								Board.timerMedium.isRunning() == true ||
								Board.timerHard.isRunning() == true) && Board.lifeMyShip < 3){
							Board.god = false;
							Board.lifeMyShip = 3;
							textArea.append("********GODMODE OFF*********" + "\n");
							return;
						}
						if(Board.ingame == true && Board.lifeMyShip >= 3){
							textArea.append("***********Not in a GODMODE!*********" + "\n");
							return;
						}
						if(Board.ingame == false || (Board.timerEasy.isRunning() == false && 
								Board.timerMedium.isRunning() == false &&
								Board.timerHard.isRunning() == false)){
							textArea.append("********WARNING: Not in a game!*********" + "\n");
							return;
						}
						return;
					}
					
					if (commands[12].trim().equalsIgnoreCase(textArea.getText().trim())){
					 if(Board.ingame == true){
						if(Board.aliens.size() > 0){
							if(Board.timerEasy.isRunning() == true){
								textArea.append("Level: 1" + "\n");
								textArea.append("Difficulty: Easy" + "\n");
								textArea.append("Aliens killed: " + (-(Board.aliens.size() - 54)) + "\n");
								textArea.append("Healthpacks left: " + Board.healthpack.size() + "\n");
								if(Board.lifeMyShip <= 4){
									textArea.append("Lifestats: Healthy");
								}
								if(Board.lifeMyShip == 5){
									textArea.append("Lifestats: Injured");
								}
								if(Board.lifeMyShip == 6){
									textArea.append("Lifestats: Critical");
								}
								if(Board.lifeMyShip < 3){
									textArea.append("\n" + "Godmode: " + "ON");
								}
								if(Board.lifeMyShip == 3){
									textArea.append("\n" + "Godmode: " + "OFF");
								}

								return;
							}
							
							if(Board.timerMedium.isRunning() == true){
								textArea.append("Level: 1" + "\n");
								textArea.append("Difficulty: Medium" + "\n");
								textArea.append("Aliens killed: " + (-(Board.aliens.size() - 54)) + "\n");
								textArea.append("Healthpacks left: " + Board.healthpack.size() + "\n");
								if(Board.lifeMyShip <= 4){
									textArea.append("Lifestats: Healthy");
								}
								if(Board.lifeMyShip == 5){
									textArea.append("Lifestats: Injured");
								}
								if(Board.lifeMyShip == 6){
									textArea.append("Lifestats: Critical");
								}
								if(Board.lifeMyShip < 3){
									textArea.append("\n" + "Godmode: " + "ON");
								}
								if(Board.lifeMyShip == 3){
									textArea.append("\n" + "Godmode: " + "OFF");
								}
								return;
							}
							
							if(Board.timerHard.isRunning() == true){
								textArea.append("Level: 1" + "\n");
								textArea.append("Difficulty: Hard" + "\n");
								textArea.append("Aliens killed: " + (-(Board.aliens.size() - 54)) + "\n");
								textArea.append("Healthpacks left: " + Board.healthpack.size() + "\n");
								if(Board.lifeMyShip <= 4){
									textArea.append("Lifestats: Healthy");
								}
								if(Board.lifeMyShip == 5){
									textArea.append("Lifestats: Injured");
								}
								if(Board.lifeMyShip == 6){
									textArea.append("Lifestats: Critical");
								}
								if(Board.lifeMyShip < 3){
									textArea.append("\n" + "Godmode: " + "ON");
								}
								if(Board.lifeMyShip == 3){
									textArea.append("\n" + "Godmode: " + "OFF");
								}
								return;
							}
						}
						
						if(Board.aliens.isEmpty() && Board.dragons.size() > 0){
							if(Board.timerEasy.isRunning() == true){
								textArea.append("Level: 2" + "\n");
								textArea.append("Difficulty: Easy" + "\n");
								textArea.append("Dragons killed: " + (-(Board.dragons.size() - 30)) + "\n");
								textArea.append("Healthpacks left: " + Board.healthpack.size() + "\n");
								if(Board.lifeMyShip <= 4){
									textArea.append("Lifestats: Healthy");
								}
								if(Board.lifeMyShip == 5){
									textArea.append("Lifestats: Injured");
								}
								if(Board.lifeMyShip == 6){
									textArea.append("Lifestats: Critical");
								}
								if(Board.lifeMyShip < 3){
									textArea.append("\n" + "Godmode: " + "ON");
								}
								if(Board.lifeMyShip == 3){
									textArea.append("\n" + "Godmode: " + "OFF");
								}
								return;
							}
							
							if(Board.timerMedium.isRunning() == true){
								textArea.append("Level: 2" + "\n");
								textArea.append("Difficulty: Medium" + "\n");
								textArea.append("Dragons killed: " + (-(Board.dragons.size() - 30)) + "\n");
								textArea.append("Healthpacks left: " + Board.healthpack.size() + "\n");
								if(Board.lifeMyShip <= 4){
									textArea.append("Lifestats: Healthy");
								}
								if(Board.lifeMyShip == 5){
									textArea.append("Lifestats: Injured");
								}
								if(Board.lifeMyShip == 6){
									textArea.append("Lifestats: Critical");
								}
								if(Board.lifeMyShip < 3){
									textArea.append("\n" + "Godmode: " + "ON");
								}
								if(Board.lifeMyShip == 3){
									textArea.append("\n" + "Godmode: " + "OFF");
								}
								return;
							}
							
							if(Board.timerHard.isRunning() == true){
								textArea.append("Level: 2" + "\n");
								textArea.append("Difficulty: Hard" + "\n");
								textArea.append("Dragons killed: " + (-(Board.dragons.size() - 30)) + "\n");
								textArea.append("Healthpacks left: " + Board.healthpack.size() + "\n");
								if(Board.lifeMyShip <= 4){
									textArea.append("Lifestats: Healthy");
								}
								if(Board.lifeMyShip == 5){
									textArea.append("Lifestats: Injured");
								}
								if(Board.lifeMyShip == 6){
									textArea.append("Lifestats: Critical");
								}
								if(Board.lifeMyShip < 3){
									textArea.append("\n" + "Godmode: " + "ON");
								}
								if(Board.lifeMyShip == 3){
									textArea.append("\n" + "Godmode: " + "OFF");
								}
								return;
							}
						}
						
						if(Board.aliens.isEmpty() && Board.dragons.isEmpty()){
							if(Board.timerEasy.isRunning() == true){
								textArea.append("Level: 3" + "\n");
								textArea.append("Difficulty: Easy" + "\n");
								textArea.append("Healthpacks left: " + Board.healthpack.size() + "\n");
								if(Board.lifeMyShip <= 4){
									textArea.append("Lifestats: Healthy");
								}
								if(Board.lifeMyShip == 5){
									textArea.append("Lifestats: Injured");
								}
								if(Board.lifeMyShip == 6){
									textArea.append("Lifestats: Critical");
								}
								if(Board.lifeMyShip < 3){
									textArea.append("\n" + "Godmode: " + "ON");
								}
								if(Board.lifeMyShip == 3){
									textArea.append("\n" + "Godmode: " + "OFF");
								}
								return;
							}
							
							if(Board.timerMedium.isRunning() == true){
								textArea.append("Level: 3" + "\n");
								textArea.append("Difficulty: Medium" + "\n");
								textArea.append("Healthpacks left: " + Board.healthpack.size() + "\n");
								if(Board.lifeMyShip <= 4){
									textArea.append("Lifestats: Healthy");
								}
								if(Board.lifeMyShip == 5){
									textArea.append("Lifestats: Injured");
								}
								if(Board.lifeMyShip == 6){
									textArea.append("Lifestats: Critical");
								}
								if(Board.lifeMyShip < 3){
									textArea.append("\n" + "Godmode: " + "ON");
								}
								if(Board.lifeMyShip == 3){
									textArea.append("\n" + "Godmode: " + "OFF");
								}
								return;
							}
							
							if(Board.timerHard.isRunning() == true){
								textArea.append("Level: 3" + "\n");
								textArea.append("Difficulty: Hard" + "\n");
								textArea.append("Healthpacks left: " + Board.healthpack.size() + "\n");
								if(Board.lifeMyShip <= 4){
									textArea.append("Lifestats: Healthy");
								}
								if(Board.lifeMyShip == 5){
									textArea.append("Lifestats: Injured");
								}
								if(Board.lifeMyShip == 6){
									textArea.append("Lifestats: Critical");
								}
								if(Board.lifeMyShip < 3){
									textArea.append("\n" + "Godmode: " + "ON");
								}
								if(Board.lifeMyShip == 3){
									textArea.append("\n" + "Godmode: " + "OFF");
								}
								return;
							}
						
						 }
								
						}
					 
					 	if(Board.ingame == false || (Board.timerEasy.isRunning() == false && 
					 			Board.timerMedium.isRunning() == false && 
					 			Board.timerHard.isRunning() == false)){
							textArea.append("***********WARNING: Not in a game!*********");
						}
					 	return;
					}
					
					
					if (commands[13].trim().equalsIgnoreCase(textArea.getText().trim())){
						
							textArea.append("********Game was restarted!*********" + "\n");
							Board.god = false;
		            	    setFocusable(true);
			    	        Board.bg1 = Toolkit.getDefaultToolkit().createImage("tenor.gif");
			    	        Board.ingame = true;
			    	        Board.lifeEvilHead = 3;
			    	        Board.lifeMyShip = 3;
			    	        
			    	        setPreferredSize(new Dimension(1310, 1040));

			    	        Board.myShip = new MyShip(40, 180);
			    	        Board.myShip.isVisible();
			    	        
			    	        Board.evilHead = new EvilHead(640, 180);
			    	        Board.evilHead.isVisible();
			    	        Board.evilHead.AIOnEasy();
			    	        
			    	        Board.volButt = new VolButt(940, 15);
			    	        Board.volButt.isVisible();

			    	        Board.initAliens();
			    	        Board.initGifts();
			    	        Board.initBoss();
			    	        Board.initHealth();
			    	        
			    	        Board.timerHard.stop();
			    	        Board.timerMedium.stop();
			    	        Board.timerEasy.restart();
			    	        Board.gameWon.play();
			    	        Board.gameLost.stop();
			    	        Board.bgMusic.loop();
			    	        Board.roar.stop();
		            		return;
		            	
					}
					
								
					
					if (commands[14].trim().equalsIgnoreCase(textArea.getText().trim())){
						
						if(Board.ingame == true && Board.aliens.size() > 0){
							
							textArea.append("********Level 2 was loaded!*********" + "\n");
							
							
							if (Board.aliens.size() > 0) {
								Board.aliens.clear();
							}
							
							
							return;
						}
						
						if (Board.aliens.size() == 0 && Board.dragons.size() > 0 && Board.ingame == true) {
							
							textArea.append("***********Already in Level 2!*********" + "\n");
							return;
						}
						
						if (Board.dragons.size() == 0 && Board.ingame == true) {
							
							textArea.append("***********Level change not allowed!*********" + "\n");
							return;
						}
						
						if(Board.ingame == false){
							
							textArea.append("***********WARNING: Not in a game!*********");
							return;
						}
						
						return;
					}
					
					if (commands[15].trim().equalsIgnoreCase(textArea.getText().trim())){
						
						
						if(Board.ingame == true && (Board.aliens.size() > 0 || Board.dragons.size() > 0)){
						
							Board.aliens.clear();
							Board.dragons.clear();
							textArea.append("********Level 3 was loaded!*********" + "\n");							
							return;
						}
						
						if (Board.dragons.size() == 0 && Board.ingame == true) {
							
							textArea.append("***********Already in Level 3!*********" + "\n");
							return;
						}
					
						
						if(Board.ingame == false){
							
							textArea.append("***********WARNING: Not in a game!*********");
						}
						
						return;
					}
					
					if (commands[16].trim().equalsIgnoreCase(textArea.getText().trim())){
						
						Manual readme = new Manual(); 
	    				
						readme.setVisible(true);
						Board.manualON = true;
	                    
	                    	if(Board.manualON == true){
	                    		
	                    		Board.manualON = false;
	                    		
	                    }			    			
		    			
					}
					
										
					else{
						textArea.append("********No such command!*********" + "\n");
					}

				}
				else {
					textArea.setText("");
					return;
				}
				
			}
		});
		getTextField().setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addComponent(getTextField(), GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)));

		textArea = new JTextArea();
		textArea.setFont(textArea.getFont().deriveFont(14f));
        textArea.setBackground(Color.DARK_GRAY);
		textArea.setForeground(Color.WHITE);
		textArea.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		textArea.setEditable(false);
		textArea.setText("***************WELCOME TO THE GAME CONSOLE!***********\n*********TYPE HELP TO LIST ALL AVAILABLE COMMANDS!*********" + "\n");
		scrollPane.setViewportView(textArea);
		contentPane.setLayout(gl_contentPane);
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
}