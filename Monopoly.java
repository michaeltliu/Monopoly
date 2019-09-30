/*
 * To Do List
 * Community chest and chance
 * Build on property only if monopoly is owned
 * Real rent and house values
 * Add get out of jail free card
 */

package game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Monopoly extends Applet implements MouseListener, KeyListener
{
	private int[] dice;
	private int doublesCount;
	private int[] x;
	private int[] y;
	private Random gen;
	private int playerPos1;
	private int playerPos2;
	private int money1;
	private int money2;
	private ArrayList<Integer> properties1;
	private ArrayList<Integer> properties2;
	private ArrayList<Integer> mortgagedProperties1;
	private ArrayList<Integer> mortgagedProperties2;
	private int turn;
	private boolean rollBuffer;
	private boolean endBuffer;
	private int[] prices;
	private String[] names;
	private int[][] rentCosts;
	private int[][] colorGroups;
	private boolean community;
	private boolean chance;
	private boolean displayJail;
	private boolean jail1;
	private boolean jail2;
	private boolean property;
	private boolean rent;
	private boolean tax;
	private boolean build;
	private boolean trade;
	private boolean debt;
	private boolean mortgage;
	private boolean unmortgage;
	private boolean sellHouse;
	private int pendingSellLoc;
	private int pendingMortgageLoc;
	private int pendingUnmortgageLoc;
	private int pendingBuildLoc;
	private int[] houses;
	private ArrayList<Integer> pendingOffer1;
	private ArrayList<Integer> pendingOffer2;
	private int pendingMoney1;
	private int pendingMoney2;
	private boolean moneySelected1;
	private boolean moneySelected2;
	
	public void init()
	{
		addKeyListener(this);
		addMouseListener(this);
		setSize(661, 661);
		
		dice = new int[2];
		doublesCount = 0;
		x = new int[] {600,540,480,420,360,300,240,180,120,60,0,0,0,0,0,0,0,0,0,0,
				0,60,120,180,240,300,360,420,480,540,600,600,600,600,600,600,600,600,600,600};
		y = new int[] {645,645,645,645,645,645,645,645,645,645,
				645,585,525,465,405,345,285,225,165,105,45,45,45,45,45,45,45,45,45,45,45,
				105,165,225,285,345,405,465,525,585};
		gen = new Random();
		playerPos1 = 0;
		playerPos2 = 0;
		money1 = 1500;
		money2 = 1500;
		properties1 = new ArrayList<Integer>();
		properties2 = new ArrayList<Integer>();
		mortgagedProperties1 = new ArrayList<Integer>();
		mortgagedProperties2 = new ArrayList<Integer>();
		turn = 1;
		rollBuffer = true;
		endBuffer = false;
		prices = new int[] {0,60,0,60,200,200,100,0,100,120,0,140,150,140,160,200,180,0,180,200,
				0,220,0,220,240,200,260,260,150,280,0,300,300,0,320,200,0,350,100,400};
		names = new String[] {"Go", "Mediter", "Commun", "Baltic", "Tax", "Railroad", "Oriental", 
				"Chance", "Vermont", "Connect", "Jail", "St. Charles", "Electric", "States", "Virginia",
				"Railroad", "St. James", "Commun", "Tennessee", "New York", "Free Park", "Kentucky",
				"Chance", "Indiana", "Illinois", "Railroad", "Atlantic", "Ventnor", "Water", "Marvin",
				"Go To Jail", "Pacific", "NC Ave.", "Commun", "Penn Ave.", "Railroad", "Chance", 
				"Park Place", "Tax", "Boardwalk"};
		colorGroups = new int[][] {new int[] {1,3}, new int[] {6,8,9}, new int[] {11,13,14},
			new int[] {16,18,19}, new int[] {21,23,24}, new int[] {26,27,29}, new int[] {31,32,34},
			new int[] {37,39}
		};
		rentCosts = new int[][] {new int[] {0},
				new int[] {2,10,30,90,160,250}, new int[] {0},
				new int[] {4,20,60,180,320,450}, new int[] {0},
				new int[] {25,50,100,200}, new int[] {6,30,90,270,400,550},
				new int[] {0}, new int[] {6,30,90,270,400,550},
				new int[] {8,40,100,300,450,600}, new int[] {0},
				new int[] {10,50,150,450,625,750}, new int[] {4,10},
				new int[] {10,50,150,450,625,750}, new int[] {12,60,180,500,700,900},
				new int[] {25,50,100,200}, new int[] {14,70,200,550,750,950},
				new int[] {0}, new int[] {14,70,200,550,750,950},
				new int[] {16,80,220,600,800,1000}, new int[] {0},
				new int[] {18,90,250,700,875,1050}, new int[] {0},
				new int[] {18,90,250,700,875,1050}, new int[] {20,100,300,750,925,1100},
				new int[] {0}, new int[] {22,110,330,800,975,1150},
				new int[] {22,110,330,800,975,1150}, new int[] {4,10},
				new int[] {24,120,360,850,1025,1200}, new int[] {0},
				new int[] {26,130,390,900,1050,1250}, new int[] {26,130,390,900,1050,1250},
				new int[] {0}, new int[] {28,150,450,1000,1200,1400},
				new int[] {25,50,100,200}, new int[] {0},
				new int[] {35,175,500,1100,1300,1500}, new int[] {0},
				new int[] {50,200,600,1400,1700,2000}};
		community = false;
		chance = false;
		jail1 = false;
		jail2 = false;
		property = false;
		rent = false;
		tax = false;
		build = false;
		trade = false;
		debt = false;
		mortgage = false;
		unmortgage = false;
		sellHouse = false;
		pendingBuildLoc = -1;
		pendingSellLoc = -1;
		pendingMortgageLoc = -1;
		pendingUnmortgageLoc = -1;
		houses = new int[40];
		pendingOffer1 = new ArrayList<Integer>();
		pendingOffer2 = new ArrayList<Integer>();
		pendingMoney1 = 0;
		pendingMoney2 = 0;
		moneySelected1 = false;
		moneySelected2 = false;
	}
	
	public void paint(Graphics g) {
		for (int i = 0; i < 11; i++)
		{
			g.drawRect(60*i, 0, 60, 60);
			g.drawRect(60*i, 600, 60, 60);
			g.drawRect(0, 60*i, 60, 60);
			g.drawRect(600, 60*i, 60, 60);
		}
		
		g.setColor(Color.pink);
		for (int i : properties1)
			g.fillRect(x[i] + 1, y[i] - 44, 58, 58);
		for (int i : mortgagedProperties1)
			g.fillRect(x[i] + 1, y[i] - 44, 29, 29);
		
		
		g.setColor(new Color(145, 175, 255));
		for (int i : properties2)
			g.fillRect(x[i] + 1, y[i] - 44, 58, 58);
		for (int i : mortgagedProperties2)
			g.fillRect(x[i] + 1, y[i] - 44, 29, 29);
		
		g.setColor(Color.red);
		for (int i = 0; i < 40; i++)
		{
			for (int j = 0; j < houses[i]; j++) {
				g.drawLine(x[i] + 45, y[i] - 5*j, x[i] + 55, y[i] - 5*j);
			}
		}
		
		g.setColor(Color.black);
		for (int i = 0; i < 10; i++)
			g.drawString("$" + Integer.toString(prices[i]), 600-60*i, 645);
		for (int i = 10; i < 20; i++)
			g.drawString("$" + Integer.toString(prices[i]), 0, 645-60*(i-10));
		for (int i = 20; i < 30; i++)
			g.drawString("$" + Integer.toString(prices[i]), 60*(i-20), 45);
		for (int i = 30; i < 40; i++)
			g.drawString("$" + Integer.toString(prices[i]), 600, 45 + 60*(i-30));
		
		g.drawOval(x[playerPos1], y[playerPos1], 15, 15);
		g.setColor(Color.magenta);
		g.fillOval(x[playerPos1], y[playerPos1], 15, 15);
		g.setColor(Color.black);
		g.drawOval(x[playerPos2], y[playerPos2], 15, 15);
		g.setColor(Color.blue);
		g.fillOval(x[playerPos2], y[playerPos2], 15, 15);
		
		g.setColor(Color.black);
		g.drawString(names[0], 600, 615); g.drawString(names[1], 540, 615);
		g.drawString(names[2], 480, 615); g.drawString(names[3], 420, 615);
		g.drawString(names[4], 360, 615); g.drawString(names[5], 300, 615);
		g.drawString(names[6], 240, 615); g.drawString(names[7], 180, 615);
		g.drawString(names[8], 120, 615); g.drawString(names[9], 60, 615);
		g.drawString(names[10], 0, 615); g.drawString(names[11], 0, 555);
		g.drawString(names[12], 0, 495); g.drawString(names[13], 0, 435);
		g.drawString(names[14], 0, 375); g.drawString(names[15], 0, 315);
		g.drawString(names[16], 0, 255); g.drawString(names[17], 0, 195);
		g.drawString(names[18], 0, 135); g.drawString(names[19], 0, 75);
		g.drawString(names[20], 0, 15); g.drawString(names[21], 60, 15);
		g.drawString(names[22], 120, 15); g.drawString(names[23], 180, 15);
		g.drawString(names[24], 240, 15); g.drawString(names[25], 300, 15);
		g.drawString(names[26], 360, 15); g.drawString(names[27], 420, 15);
		g.drawString(names[28], 480, 15); g.drawString(names[29], 540, 15);
		g.drawString(names[30], 600, 15); g.drawString(names[31], 600, 75);
		g.drawString(names[32], 600, 135); g.drawString(names[33], 600, 195);
		g.drawString(names[34], 600, 255); g.drawString(names[35], 600, 315);
		g.drawString(names[36], 600, 375); g.drawString(names[37], 600, 435);
		g.drawString(names[38], 600, 495); g.drawString(names[39], 600, 555);
		
		g.drawString("Player " + Integer.toString(turn) + " turn", 298, 100);
		
		g.drawString("Player 1 money: " + money1, 75, 75);
		g.drawString("Player 2 money: " + money2, 75, 90);
		
		if (!(community || chance || property || rent || build || tax || trade || debt ||
				mortgage || sellHouse || unmortgage || displayJail))
		{
			g.drawRect(275, 375, 50, 50);
			g.drawRect(335, 375, 50, 50);
			
			g.drawString(Integer.toString(dice[0]), 280, 390);
			g.drawString(Integer.toString(dice[1]), 340, 390);
			
			if (rollBuffer)
				g.setColor(Color.green);
			
			g.drawRect(285, 315, 90, 40);
			g.drawString("Roll Dice", 308, 335);
			
			g.setColor(Color.red);
			if (!endBuffer)
				g.setColor(Color.BLACK);
			
			g.drawRect(285, 450, 90, 25);
			g.drawString("End Turn", 305, 465);
			
			g.setColor(Color.BLACK);
			g.drawRect(75, 560, 90, 25);
			g.drawString("Build", 105, 575);
			
			g.drawRect(180, 560, 90, 25);
			g.drawString("Trade", 210, 575);
			
			g.drawRect(285, 560, 90, 25);
			g.drawString("Mortgage", 311, 575);
			
			g.drawRect(390, 560, 90, 25);
			g.drawString("Sell", 420, 575);
			
			g.drawRect(495, 560, 90, 25);
			g.drawString("Unmortgage", 510, 575);
		}
		
		else if (community)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(126, 198, 212));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Community", 310, 215);
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Okay", 317, 415);
		}
		else if (chance)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(249, 163, 61));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Chance", 310, 215);
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Okay", 317, 415);
		}
		else if (displayJail)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(189, 189, 189));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Jail for player " + Integer.toString(turn), 300, 215);
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Pay $50 bail", 310, 415);
			
			g.drawRect(225, 400, 60, 30);
			g.drawString("Roll for doubles", 235, 415);
			
			g.drawRect(375, 400, 60, 30);
			g.drawString("Use get out card", 385, 415);
		}
		else if (property)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(189, 189, 189));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Property", 310, 215);
			if (turn == 1)
			{
				g.drawString(names[playerPos1], 310, 300);
				g.drawString("$" + Integer.toString(prices[playerPos1]), 310, 315);
			}
			else if (turn == 2) 
			{
				g.drawString(names[playerPos2], 310, 300);
				g.drawString("$" + Integer.toString(prices[playerPos2]), 310, 315);
			}
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Buy", 318, 415);
			g.drawRect(300, 440, 60, 30);
			g.drawString("Leave", 316, 455);
		}
		else if (rent)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(189, 189, 189));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Rent", 310, 215);
			
			int payment = 0;
			if (turn == 1)
			{
				payment = rentCosts[playerPos1][houses[playerPos1]];
				money1 -= payment;
				money2 += payment;
			}
			else if (turn == 2)
			{
				payment = rentCosts[playerPos2][houses[playerPos2]];
				money2 -= payment;
				money1 -= payment;
			}
			
			g.drawString("Your balance has been reduced by " + "$" + Integer.toString(payment), 
					300, 300);
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Okay", 318, 415);
		}
		else if (tax)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(189, 189, 189));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Taxes", 310, 215);
			
			int payment = 0;
			if (turn == 1)
			{
				if (playerPos1 == 4)
					payment = 200;
				else if (playerPos1 == 38)
					payment = 100;
				money1 -= payment;
			}
			else if (turn == 2)
			{
				if (playerPos2 == 4)
					payment = 200;
				else if (playerPos2 == 38)
					payment = 100;
				money2 -= payment;
			}
			g.drawString("Your balance has been reduced by " + "$" + Integer.toString(payment), 
					300, 300);
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Okay", 318, 415);
		}
		else if (build)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(189, 189, 189));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Build", 310, 215);
			
			g.drawString("Click the property where you would like to build.", 202, 250);
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Finish", 316, 415);
			
			if (pendingBuildLoc >= 0 && houses[pendingBuildLoc] <= 4)
			{
				if ((turn == 1 && properties1.contains(pendingBuildLoc)) ||
						turn == 2 && properties2.contains(pendingBuildLoc))
				{
					g.drawRect(260, 335, 140, 30);
					g.drawString("Confirm Build Location", 269, 353);
					g.drawString("Are you sure you want to upgrade " + names[pendingBuildLoc], 210, 305);
					g.drawString("for $100?", 305, 320);
				}
			}
		}
		else if (trade)
		{
			g.drawRect(180, 120, 300, 420);
			g.setColor(new Color(189, 189, 189));
			g.fillRect(181, 121, 299, 419);
			g.setColor(Color.black);
			g.drawString("Trade", 310, 155);
			
			g.drawRect(300, 460, 60, 30);
			g.drawString("Okay", 318, 475);
			
			g.drawString("Player 1 offer", 200, 190);
			g.drawString("Player 2 offer", 360, 190);
			g.drawRect(210, 460, 60, 30);
			g.drawRect(390, 460, 60, 30);
			g.drawString("$" + Integer.toString(pendingMoney1), 215, 475);
			g.drawString("$" + Integer.toString(pendingMoney2), 395, 475);
			
			for (int i = 0; i < pendingOffer1.size(); i++)
			{
				g.drawString(names[pendingOffer1.get(i)], 200, 215 + 25 * i);
			}
			
			for (int i = 0; i < pendingOffer2.size(); i++)
			{
				g.drawString(names[pendingOffer2.get(i)], 360, 215 + 25 * i);
			}
		}
		else if (debt)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(189, 189, 189));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Debt", 310, 205);
			
			if (money1 < 0)
				g.drawString("Player 1 owes $" + Integer.toString(-money1), 300, 350);
			else if (money2 < 0)
				g.drawString("Player 2 owes $" + Integer.toString(-money2), 300, 350);
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Okay", 318, 415);
		}
		
		else if (mortgage)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(189, 189, 189));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Mortgage", 310, 215);
			
			g.drawString("Click the property that you would like to mortgage.", 202, 250);
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Finish", 316, 415);
			
			if (pendingMortgageLoc >= 0)
			{
				if ((turn == 1 && properties1.contains(pendingMortgageLoc)) ||
						turn == 2 && properties2.contains(pendingMortgageLoc))
				{
					g.drawRect(260, 335, 140, 30);
					g.drawString("Confirm Mortgage Location", 269, 353);
					g.drawString("Are you sure you want to mortgage " + names[pendingMortgageLoc], 210, 305);
					g.drawString("for $" + Integer.toString(prices[pendingMortgageLoc]/2) + "?", 305, 320);
				}
			}
		}
		
		else if (unmortgage)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(189, 189, 189));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Unmortgage", 310, 215);
			
			g.drawString("Click the property that you would like to unmortgage.", 202, 250);
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Finish", 316, 415);
			
			if (pendingUnmortgageLoc >= 0)
			{
				if ((turn == 1 && mortgagedProperties1.contains(pendingUnmortgageLoc)) ||
						turn == 2 && mortgagedProperties2.contains(pendingUnmortgageLoc))
				{
					g.drawRect(260, 335, 140, 30);
					g.drawString("Confirm Unmortgage Location", 269, 353);
					g.drawString("Are you sure you want to unmortgage " + names[pendingUnmortgageLoc], 210, 305);
					g.drawString("for $" + Integer.toString(prices[pendingUnmortgageLoc]/2) + "?", 305, 320);
				}
			}
		}
		
		else if (sellHouse)
		{
			g.drawRect(180, 180, 300, 300);
			g.setColor(new Color(189, 189, 189));
			g.fillRect(181, 181, 299, 299);
			g.setColor(Color.black);
			g.drawString("Sell Houses", 310, 215);
			
			g.drawString("Click the property where you would like to sell houses.", 202, 250);
			
			g.drawRect(300, 400, 60, 30);
			g.drawString("Finish", 316, 415);
			
			if (pendingSellLoc >= 0 && houses[pendingSellLoc] > 0)
			{
				if ((turn == 1 && properties1.contains(pendingSellLoc)) ||
						turn == 2 && properties2.contains(pendingSellLoc))
				{
					g.drawRect(260, 335, 140, 30);
					g.drawString("Confirm Sell Location", 269, 353);
					g.drawString("Are you sure you want to sell on " + names[pendingSellLoc], 210, 305);
					g.drawString("for $100?", 305, 320);
				}
			}
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		int xcoord = arg0.getX();
		int ycoord = arg0.getY();
		
		if (!(community || chance || property || rent || build || tax || trade || debt || 
				mortgage || sellHouse || unmortgage || displayJail))
		{
			if (xcoord >= 285 && xcoord <= 375 && ycoord >= 315 && ycoord <= 355 &&	rollBuffer)
			{
				rollDice();
			}
			
			else if (xcoord >= 285 && xcoord <= 375 && ycoord >= 450 && ycoord <= 475 && endBuffer)
			{
				endTurn();
			}
			
			else if (xcoord >= 75 && xcoord <= 165 && ycoord >= 560 && ycoord <= 585)
				build = true;
			
			else if (xcoord >= 180 && xcoord <= 270 && ycoord >= 560 && ycoord <= 585)
				trade = true;
			
			else if (xcoord >= 285 && xcoord <= 375 && ycoord >= 560 && ycoord <= 585)
				mortgage = true;
			
			else if (xcoord >= 390 && xcoord <= 480 && ycoord >= 560 && ycoord <= 585)
				sellHouse = true;
			
			else if (xcoord >= 495 && xcoord <= 585 && ycoord >= 560 && ycoord <= 585)
				unmortgage = true;
		}
		
		else if (displayJail)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
			{
				if (turn == 1)
				{
					money1 -= 50;
					displayJail = false;
				}
				else if (turn == 2)
				{
					money2 -= 50;
					displayJail = false;
				}
			}
			else if (xcoord >= 225 && xcoord <= 285 && ycoord >= 400 && ycoord <= 430)
			{
				int rand1 = gen.nextInt(5);
				int rand2 = gen.nextInt(5);
				if (rand1 == rand2)
				{
					if (turn == 1)
						jail1 = false;
					else if (turn == 2)
						jail2 = false;
				}
				displayJail = false;
			}
			else if (xcoord >= 375 && xcoord <= 435 && ycoord >= 400 && ycoord <= 430)
			{
				// use get out of jail free card
			}
			
		}
		else if (community)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
				community = false;
		}
		else if (chance)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
				chance = false;
		}
		else if (property)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
			{
				if (turn == 1)
				{
					money1 -= prices[playerPos1];
					properties1.add(playerPos1);
				}
				else if (turn == 2)
				{
					money2 -= prices[playerPos2];
					properties2.add(playerPos2);
				}
				property = false;
			}
			else if (xcoord >= 300 && xcoord <= 360 && ycoord >= 440 && ycoord <= 470)
				property = false;
		}
		else if (rent)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
				rent = false;
		}
		else if (tax)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
				tax = false;
		}
		else if (build)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
				build = false;
			else if (xcoord >= 260 && xcoord <= 400 && ycoord >= 335 && ycoord <= 365 && 
					pendingBuildLoc >= 0 && houses[pendingBuildLoc] <= 4)
			{
				if (turn == 1)
				{
					if (properties1.contains(pendingBuildLoc))
					{
						for (int i : othersInColorGroup(pendingBuildLoc))
						{
							if (!properties1.contains(i)) break;
						}
						houses[pendingBuildLoc] += 1;
						pendingBuildLoc = -1;
						money1 -= 100;
						//and properties1.contains the other properties
					}
				}
				else if (turn == 2 && properties2.contains(pendingBuildLoc)) //in the same color group
				{
					houses[pendingBuildLoc] += 1;
					pendingBuildLoc = -1;
					if (turn == 1)
						money1 -= 100;
					else if (turn == 2)
						money2 -= 100;
				}
			}
			else
			{
				for (int i = 0; i < 40; i++)
				{
					if (xcoord >= x[i] && xcoord <= x[i] + 60 && ycoord <= y[i] + 15 && ycoord >= y[i] - 45)
						pendingBuildLoc = i;
				}
			}
		}
		else if (trade)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 460 && ycoord <= 490)
			{
				for (int i : pendingOffer1)
				{
					properties2.add(i);
					for (int j = 0; j < properties1.size(); j++)
					{
						if (properties1.get(j) == i)
							properties1.remove(j);
					}
				}
				for (int i : pendingOffer2)
				{
					properties1.add(i);
					for (int j = 0; j < properties2.size(); j++)
					{
						if (properties2.get(j) == i)
							properties2.remove(j);
					}
				}
				money1 += pendingMoney2;
				money2 += pendingMoney1;
				money1 -= pendingMoney1;
				money2 -= pendingMoney2;
				
				pendingOffer1.clear();
				pendingOffer2.clear();
				pendingMoney1 = 0;
				pendingMoney2 = 0;
				trade = false;
			}
			else if (xcoord >= 210 && xcoord <= 270 && ycoord >= 460 && ycoord <= 490)
			{
				moneySelected1 = true;
				moneySelected2 = false;
			}
			else if (xcoord >= 390 && xcoord <= 450 && ycoord >= 460 && ycoord <= 490)
			{
				moneySelected2 = true;
				moneySelected1 = false;
			}
			else
			{
				for (int i = 0; i < 40; i++)
				{
					if (xcoord >= x[i] && xcoord <= x[i] + 60 && ycoord <= y[i] + 15 && ycoord >= y[i] - 45) {
						if (properties1.contains(i))
							pendingOffer1.add(i);
						else if (properties2.contains(i))
							pendingOffer2.add(i);
					}
				}
			}
		}
		else if (debt)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
				debt = false;
		}
		
		else if (mortgage)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
			{
				mortgage = false;
				pendingMortgageLoc = -1;
			}
			else if (xcoord >= 260 && xcoord <= 400 && ycoord >= 335 && ycoord <= 365 && 
					pendingMortgageLoc >= 0)
			{
				if ((turn == 1 && properties1.contains(pendingMortgageLoc)))
				{
					money1 += 0.5*prices[pendingMortgageLoc];
					properties1.remove(properties1.indexOf(pendingMortgageLoc));
					mortgagedProperties1.add(pendingMortgageLoc);
				}
				else if ((turn == 2 && properties2.contains(pendingMortgageLoc)))
				{
					money2 += 0.5*prices[pendingMortgageLoc];
					properties2.remove(properties2.indexOf(pendingMortgageLoc));
					mortgagedProperties2.add(pendingMortgageLoc);
				}
				
				pendingMortgageLoc = -1;
			}
			else
			{
				for (int i = 0; i < 40; i++)
				{
					if (xcoord >= x[i] && xcoord <= x[i] + 60 && ycoord <= y[i] + 15 && ycoord >= y[i] - 45)
						pendingMortgageLoc = i;
				}
			}
		}
		
		else if (unmortgage)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
			{
				unmortgage = false;
				pendingUnmortgageLoc = -1;
			}
			else if (xcoord >= 260 && xcoord <= 400 && ycoord >= 335 && ycoord <= 365 && 
					pendingUnmortgageLoc >= 0)
			{
				if ((turn == 1 && mortgagedProperties1.contains(pendingUnmortgageLoc)))
				{
					money1 -= 0.5*prices[pendingUnmortgageLoc];
					mortgagedProperties1.remove(mortgagedProperties1.indexOf(pendingUnmortgageLoc));
					properties1.add(pendingUnmortgageLoc);
				}
				else if ((turn == 2 && mortgagedProperties2.contains(pendingUnmortgageLoc)))
				{
					money2 -= 0.5*prices[pendingUnmortgageLoc];
					mortgagedProperties2.remove(mortgagedProperties2.indexOf(pendingUnmortgageLoc));
					properties2.add(pendingUnmortgageLoc);
				}
				
				pendingUnmortgageLoc = -1;
			}
			else
			{
				for (int i = 0; i < 40; i++)
				{
					if (xcoord >= x[i] && xcoord <= x[i] + 60 && ycoord <= y[i] + 15 && ycoord >= y[i] - 45)
						pendingUnmortgageLoc = i;
				}
			}
		}
		
		else if (sellHouse)
		{
			if (xcoord >= 300 && xcoord <= 360 && ycoord >= 400 && ycoord <= 430)
			{
				sellHouse = false;
				pendingSellLoc = -1;
			}
			else if (xcoord >= 260 && xcoord <= 400 && ycoord >= 335 && ycoord <= 365 && 
					pendingSellLoc >= 0)
			{
				if ((turn == 1 && properties1.contains(pendingSellLoc)) || 
						turn == 2 && properties2.contains(pendingSellLoc))
				{
					houses[pendingSellLoc] -= 1;
					pendingSellLoc = -1;
					if (turn == 1)
						money1 += 50;
					else if (turn == 2)
						money2 += 50;
				}
			}
			else
			{
				for (int i = 0; i < 40; i++)
				{
					if (xcoord >= x[i] && xcoord <= x[i] + 60 && ycoord <= y[i] + 15 && ycoord >= y[i] - 45)
						pendingSellLoc = i;
				}
			}
		}
		
		repaint();
	}
	
	private int[] othersInColorGroup(int location) {
		// TODO Auto-generated method stub
		for (int[] a : colorGroups)
		{
			for (int i : a)
			{
				if (i == location)
					return a;
			}
		}
		return new int[] {420};
	}

	private void endTurn() {
		checkMoney();
		
		if (!debt)
		{
			if (turn == 1)
			{
				turn = 2;
				if (jail2)
					displayJail = true;
			}
			else if (turn == 2)
			{
				turn = 1;
				if (jail1)
					displayJail = true;
			}
			endBuffer = false;
			rollBuffer = true;
		}
	}

	public void checkMoney()
	{
		if (money1 < 0 || money2 < 0)
			debt = true;
	}
	
	public void rollDice()
	{
		int dice1 = 1 + gen.nextInt(5);
		int dice2 = 1 + gen.nextInt(5);
		
		dice[0] = dice1;
		dice[1] = dice2;
		
		if (turn == 1)
		{
			playerPos1 += dice1;
			playerPos1 += dice2;
			if (playerPos1 >= 40)
				money1 += 200;
			
			playerPos1 %= 40;
		}
		else if (turn == 2)
		{
			playerPos2 += dice1;
			playerPos2 += dice2;
			if (playerPos2 >= 40)
				money2 += 200;
			
			playerPos2 %= 40;
		}
		
		if (turn == 1)
		{
			for (int i : properties2)
			{
				if (i == playerPos1)
					rent = true;
			}
			
			if (!rent)
			{
				if (playerPos1 == 2 || playerPos1 == 17 || playerPos1 == 33)
					community = true;
				else if (playerPos1 == 7 || playerPos1 == 22 || playerPos1 == 36)
					chance = true;
				else if (playerPos1 == 30) {
					playerPos1 = 10;
					displayJail = true;
					jail1 = true; }
				else if (playerPos1 == 4 || playerPos1 == 38)
					tax = true;
				else if (playerPos1 == 10 || playerPos1 == 20 || playerPos1 == 0) {}
				else
					property = true;
				
				for (int j : properties1)
				{
					if (j == playerPos1)
						property = false;
				}
			}
		}
		
		else if (turn == 2)
		{
			for (int i : properties1)
			{
				if (i == playerPos2)
					rent = true;
			}
			if (!rent)
			{
				if (playerPos2 == 2 || playerPos2 == 17 || playerPos2 == 33)
					community = true;
				else if (playerPos2 == 7 || playerPos2 == 22 || playerPos2 == 36)
					chance = true;
				else if (playerPos2 == 30) {
					playerPos2 = 10;
					displayJail = true;
					jail2 = true; }
				else if (playerPos2 == 4 || playerPos2 == 38)
					tax = true;
				else if (playerPos2 == 10 || playerPos2 == 20  || playerPos2 == 0)
					{}
				else
					property = true;
				
				for (int j : properties2)
				{
					if (j == playerPos2)
						property = false;
				}
			}
		}
		
		if (dice1 != dice2)
		{
			endBuffer = true;
			rollBuffer = false;
		}
		else if (dice1 == dice2)
		{
			doublesCount ++;
		}
		
		if (doublesCount == 3)
		{
			if (turn == 1)
			{
				jail1 = true;
				playerPos1 = 10;
			}
			else if (turn == 2)
			{
				jail2 = true;
				playerPos2 = 10;
			}
			
			doublesCount = 0;
		}
	}
	
	public void keyPressed(KeyEvent arg0) 
	{
		if (moneySelected1)
		{
			if (arg0.getKeyCode() == KeyEvent.VK_0 && 10 * pendingMoney1 <= money1)
				pendingMoney1 *= 10;
			else if (arg0.getKeyCode() == KeyEvent.VK_1 && 10 * pendingMoney1 + 1 <= money1){
				pendingMoney1 *= 10;
				pendingMoney1 += 1;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_2 && 10 * pendingMoney1 + 2 <= money1){
				pendingMoney1 *= 10;
				pendingMoney1 += 2;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_3 && 10 * pendingMoney1 + 3 <= money1){
				pendingMoney1 *= 10;
				pendingMoney1 += 3;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_4 && 10 * pendingMoney1 + 4 <= money1){
				pendingMoney1 *= 10;
				pendingMoney1 += 4;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_5 && 10 * pendingMoney1 + 5 <= money1){
				pendingMoney1 *= 10;
				pendingMoney1 += 5;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_6 && 10 * pendingMoney1 + 6 <= money1){
				pendingMoney1 *= 10;
				pendingMoney1 += 6;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_7 && 10 * pendingMoney1 + 7 <= money1){
				pendingMoney1 *= 10;
				pendingMoney1 += 7;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_8 && 10 * pendingMoney1 + 8 <= money1){
				pendingMoney1 *= 10;
				pendingMoney1 += 8;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_9 && 10 * pendingMoney1 + 9 <= money1){
				pendingMoney1 *= 10;
				pendingMoney1 += 9;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				pendingMoney1 /= 10;
			}
			
		}
		else if (moneySelected2)
		{
			if (arg0.getKeyCode() == KeyEvent.VK_0 && 10 * pendingMoney2 <= money2)
				pendingMoney2*= 10;
			else if (arg0.getKeyCode() == KeyEvent.VK_1 && 10 * pendingMoney2 + 1 <= money2){
				pendingMoney2 *= 10;
				pendingMoney2 += 1;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_2 && 10 * pendingMoney2 + 2 <= money2){
				pendingMoney2 *= 10;
				pendingMoney2 += 2;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_3 && 10 * pendingMoney2 + 3 <= money2){
				pendingMoney2 *= 10;
				pendingMoney2 += 3;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_4 && 10 * pendingMoney2 + 4 <= money2){
				pendingMoney2 *= 10;
				pendingMoney2 += 4;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_5 && 10 * pendingMoney2 + 5 <= money2){
				pendingMoney2 *= 10;
				pendingMoney2 += 5;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_6 && 10 * pendingMoney2 + 6 <= money2){
				pendingMoney2 *= 10;
				pendingMoney2 += 6;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_7 && 10 * pendingMoney2 + 7 <= money2){
				pendingMoney2 *= 10;
				pendingMoney2 += 7;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_8 && 10 * pendingMoney2 + 8 <= money2){
				pendingMoney2 *= 10;
				pendingMoney2 += 8;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_9 && 10 * pendingMoney2 + 9 <= money2){
				pendingMoney2 *= 10;
				pendingMoney2 += 9;
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				pendingMoney2 /= 10;
			}
		}
		repaint();
	}
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	public void keyReleased(KeyEvent arg0) {}
}