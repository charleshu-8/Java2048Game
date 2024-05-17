package edu.wm.cs.cs301.game2048;

import java.util.Arrays;
import java.util.Random;

public class State implements GameState {
	//Purpose: Hashing function
	//Input: N/A
	//Output: Object instance as hash code integer
	//Performs hashing algorithm on board attribute w/ 31 prime base
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		return result;
	}

	//Purpose: Object equality comparison
	//Input: Given object instance
	//Output: True/false if equal
	//Given some object, function will determine if it is equal to object method is invoked upon of class State
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof State)) {
			return false;
		}
		State other = (State) obj;
		return Arrays.deepEquals(board, other.board);
	}

	//Board attribute; 2D array construct
	private int[][] board;

	//Purpose: Constructor w/ pre-existing object
	//Input: GameState object
	//Output: N/A
	//Given some GameState object, generate an instance of State w/ contents from given object via deep copy
	public State(GameState original) {
		this.board = new int[4][4];
		for (int xCoordinate = 0; xCoordinate < 4; xCoordinate++) {
			for (int yCoordinate = 0; yCoordinate < 4; yCoordinate++) {
				//Assign tile values via getValue method to extract int values over reference assignment
				this.board[xCoordinate][yCoordinate] = original.getValue(xCoordinate, yCoordinate);
			}
		}
	}

	//Purpose: Default constructor
	//Input: N/A
	//Output: N/A
	//Initialize board attribute for later use
	public State() {
		this.board = new int[4][4];
	}

	//Purpose: Getter for tile value
	//Input: Desired tile's coordinates
	//Output: Int value at tile
	//Given coordinates for tile, function will value assigned to tile
	@Override
	public int getValue(int xCoordinate, int yCoordinate) {
		return this.board[xCoordinate][yCoordinate];
	}

	//Purpose: Setter for tile value
	//Input: Tile coordinates and value for tile
	//Output: N/A
	//Given tile coordinates and desired value, assign value to array position in board attribute
	@Override
	public void setValue(int xCoordinate, int yCoordinate, int value) {
		this.board[xCoordinate][yCoordinate] = value;
	}

	//Purpose: Set all tiles to 0
	//Input: N/A
	//Output: N/A
	//Set all cells in 2D array to 0
	@Override
	public void setEmptyBoard() {
		for (int xCoordinate = 0; xCoordinate < 4; xCoordinate++) {
			for (int yCoordinate = 0; yCoordinate < 4; yCoordinate++) {
				this.board[xCoordinate][yCoordinate] = 0;
			}
		}
	}

	//Purpose: Randomly add new tile to board
	//Input: N/A
	//Output: True/false on success of operation
	//Will select a random tile via java.util's random class and place either a 2 or 4 randomly 
	@Override
	public boolean addTile() {
		Random random = new Random();
		Boolean hasAddedTile = false;
		
		if (this.isFull()) {
			return false;
		}
		do {
			int randXCoord = random.nextInt(4);
			int randYCoord = random.nextInt(4);
			int randValue = random.nextInt(2);
			if (this.board[randXCoord][randYCoord] == 0) {
				this.board[randXCoord][randYCoord] = (int) Math.pow(2, randValue + 1);
				hasAddedTile = true;
			}
		} while (!hasAddedTile);
		
		return true;
	}

	//Purpose: Checks if board is full (no 0 tiles remain)
	//Input: N/A
	//Output: True/false on whether board is full
	//Traverses 2D array to search for 0 value tile; if found, return false, else true
	@Override
	public boolean isFull() {
		for (int xCoordinate = 0; xCoordinate < 4; xCoordinate++) {
			for (int yCoordinate = 0; yCoordinate < 4; yCoordinate++) {
				if (this.board[xCoordinate][yCoordinate] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	//Purpose: Check entire board for mergeable tiles when full
	//Input: N/A
	//Output: True/false if mergeable tiles exist
	//Check every space of board and its neighbors to see if merges are possible
	@Override
	public boolean canMerge() {
		//First check corner cases; 2 comparisons each
		//Upper left check
		if (this.board[0][0] == this.board[1][0] || this.board[0][0] == this.board[0][1]) {
			return true;
		}
		//Upper right check
		if (this.board[3][0] == this.board[2][0] || this.board[3][0] == this.board[3][1]) {
			return true;
		}
		//Lower left check
		if (this.board[0][3] == this.board[0][2] || this.board[0][3] == this.board[1][3]) {
			return true;
		}
		//Lower right check
		if (this.board[3][3] == this.board[3][2] || this.board[3][3] == this.board[2][3]) {
			return true;
		}
		//Then check side cases; 3 comparisons each
		//Upper middle check
		for (int xCoordinate = 1; xCoordinate < 3; xCoordinate++) {
			if (this.board[xCoordinate][0] == this.board[xCoordinate - 1][0] || this.board[xCoordinate][0] == this.board[xCoordinate][1] || this.board[xCoordinate][0] == this.board[xCoordinate + 1][0]) {
				return true;
			}
		}
		//Lower middle check
		for (int xCoordinate = 1; xCoordinate < 3; xCoordinate++) {
			if (this.board[xCoordinate][3] == this.board[xCoordinate - 1][3] || this.board[xCoordinate][3] == this.board[xCoordinate][2] || this.board[xCoordinate][3] == this.board[xCoordinate + 1][3]) {
				return true;
			}
		}
		//Left middle check
		for (int yCoordinate = 1; yCoordinate < 3; yCoordinate++) {
			if (this.board[0][yCoordinate] == this.board[0][yCoordinate - 1] || this.board[0][yCoordinate] == this.board[1][yCoordinate] || this.board[0][yCoordinate] == this.board[0][yCoordinate + 1]) {
				return true;
			}
		}
		//Right middle check
		for (int yCoordinate = 1; yCoordinate < 3; yCoordinate++) {
			if (this.board[3][yCoordinate] == this.board[3][yCoordinate - 1] || this.board[3][yCoordinate] == this.board[2][yCoordinate] || this.board[3][yCoordinate] == this.board[3][yCoordinate + 1]) {
				return true;
			}
		}
		//Finally check center tiles; 4 comparisons each
		//Center check
		for (int xCoordindate = 1; xCoordindate < 3; xCoordindate++) {
			for (int yCoordindate = 1; yCoordindate < 3; yCoordindate++) {
				if (this.board[xCoordindate][yCoordindate] == this.board[xCoordindate + 1][yCoordindate] || this.board[xCoordindate][yCoordindate] == this.board[xCoordindate - 1][yCoordindate] || 
					this.board[xCoordindate][yCoordindate] == this.board[xCoordindate][yCoordindate + 1] || this.board[xCoordindate][yCoordindate] == this.board[xCoordindate][yCoordindate - 1]) {
					return true;
				}
			}
		}
		return false;
	}

	//Purpose: Check if 2048 or greater tile exists
	//Input: N/A
	//Output: True/false on whether game-winning tile exists
	//Traverse entire 2D array for tile that is greater than or equal to value 2048
	@Override
	public boolean reachedThreshold() {
		for (int xCoordinate = 0; xCoordinate < 4; xCoordinate++) {
			for (int yCoordinate = 0; yCoordinate < 4; yCoordinate++) {
				if (this.board[xCoordinate][yCoordinate] >= 2048) {
					return true;
				}
			}
		}
		return false;
	}

	//Purpose: Helper function for tile movement
	//Input: Direction to move tiles
	//Output: N/A
	//Given direction to move, function will traverse all tiles that can travel that direction and determine the amount of empty tiles that exist before potential collision
	//Once determined, the function will move the possible tiles are much as possible to ensure that all tiles are now clustered along that direction
	private void moveTile(char direction) {
		//Left or right; share y-axis traversal
		if (direction == 'l' || direction == 'r') {
			for (int yCoordinate = 0; yCoordinate < 4; yCoordinate++) {
				//Left
				if (direction == 'l') {
					for (int xCoordinate = 1; xCoordinate < 4; xCoordinate++) {
						if (this.board[xCoordinate][yCoordinate] != 0) {
							int coordOffset = 0;
							int tempX = xCoordinate - 1;
							boolean hasEmptyTiles = true;
							//Check amount of empty tiles that can be traversed in leftward direction
							while (hasEmptyTiles) {
								if (this.board[tempX][yCoordinate] == 0) {
									coordOffset++;
									tempX--;
									if (tempX == -1) {
										hasEmptyTiles = false;
									}
								}
								else {
									hasEmptyTiles = false;
								}
							}
							//If there are empty tiles, move across those tiles
							if (coordOffset > 0) {
								this.board[xCoordinate - coordOffset][yCoordinate] = this.board[xCoordinate][yCoordinate];
								this.board[xCoordinate][yCoordinate] = 0;
							}
						}
					}
				}
				//Right
				else {
					for (int xCoordinate = 2; xCoordinate > -1; xCoordinate--) {
						if (this.board[xCoordinate][yCoordinate] != 0) {
							int coordOffset = 0;
							int tempX = xCoordinate + 1;
							boolean hasEmptyTiles = true;
							//Check amount of empty tiles that can be traversed in rightward direction
							while (hasEmptyTiles) {
								if (this.board[tempX][yCoordinate] == 0) {
									coordOffset++;
									tempX++;
									if (tempX == 4) {
										hasEmptyTiles = false;
									}
								}
								else {
									hasEmptyTiles = false;
								}
							}
							//If there are empty tiles, move across those tiles
							if (coordOffset > 0) {
								this.board[xCoordinate + coordOffset][yCoordinate] = this.board[xCoordinate][yCoordinate];
								this.board[xCoordinate][yCoordinate] = 0;
							}
						}
					}
				}
			}
		}
		//Up or down; share x-axis traversal
		else {
			for (int xCoordinate = 0; xCoordinate < 4; xCoordinate++) {
				//Up
				if (direction == 'u') {
					for (int yCoordinate = 1; yCoordinate < 4; yCoordinate++) {
						if (this.board[xCoordinate][yCoordinate] != 0) {
							int coordOffset = 0;
							int tempY = yCoordinate - 1;
							boolean hasEmptyTiles = true;
							//Check amount of empty tiles that can be traversed in upward direction
							while (hasEmptyTiles) {
								if (this.board[xCoordinate][tempY] == 0) {
									coordOffset++;
									tempY--;
									if (tempY == -1) {
										hasEmptyTiles = false;
									}
								}
								else {
									hasEmptyTiles = false;
								}
							}
							//If there are empty tiles, move across those tiles
							if (coordOffset > 0) {
								this.board[xCoordinate][yCoordinate - coordOffset] = this.board[xCoordinate][yCoordinate];
								this.board[xCoordinate][yCoordinate] = 0;
							}
						}
					}
				}
				//Down
				else {
					for (int yCoordinate = 2; yCoordinate > -1; yCoordinate--) {
						if (this.board[xCoordinate][yCoordinate] != 0) {
							int coordOffset = 0;
							int tempY = yCoordinate + 1;
							boolean hasEmptyTiles = true;
							//Check amount of empty tiles that can be traversed in downward direction
							while (hasEmptyTiles) {
								if (this.board[xCoordinate][tempY] == 0) {
									coordOffset++;
									tempY++;
									if (tempY == 4) {
										hasEmptyTiles = false;
									}
								}
								else {
									hasEmptyTiles = false;
								}
							}
							//If there are empty tiles, move across those tiles
							if (coordOffset > 0) {
								this.board[xCoordinate][yCoordinate + coordOffset] = this.board[xCoordinate][yCoordinate];
								this.board[xCoordinate][yCoordinate] = 0;
							}
						}
					}
				}
			}
		}
	}
	
	//Purpose: Move tiles left
	//Input: N/A
	//Output: Score from tile combinations during leftward movement
	//Given state of board and possible leftward movement, tiles will be pushed leftward as possible, combined if possible, then moved leftward again as possible
	//This is to optimize movement functions to prevent array indexing errors and possible double tile merges during a single turn
	@Override
	public int left() {
		//Should follow move->merge->move behavior; compartmentalizing actions should minimize weird interactions/bugs
		int totalPoints = 0;
		//move
		moveTile('l');
		//merge
		for (int yCoordinate = 0; yCoordinate < 4; yCoordinate++) {
			for (int xCoordinate = 1; xCoordinate < 4; xCoordinate++) {
				if (this.board[xCoordinate][yCoordinate] != 0) {
					if (this.board[xCoordinate][yCoordinate] == this.board[xCoordinate - 1][yCoordinate]) {
						this.board[xCoordinate - 1][yCoordinate] = this.board[xCoordinate][yCoordinate] * 2;
						totalPoints += this.board[xCoordinate][yCoordinate] * 2;
						this.board[xCoordinate][yCoordinate] = 0;
					}
				}
			}
		}
		//move
		moveTile('l');
		return totalPoints;
	}

	//Purpose: Move tiles right
	//Input: N/A
	//Output: Score from tile combinations during rightward movement
	//Given state of board and possible rightward movement, tiles will be pushed rightward as possible, combined if possible, then moved rightward again as possible
	//This is to optimize movement functions to prevent array indexing errors and possible double tile merges during a single turn
	@Override
	public int right() {
		//Will mimic most code from left movement function except now along positive x-axis
		int totalPoints = 0;
		moveTile('r');
		for (int yCoordinate = 0; yCoordinate < 4; yCoordinate++) {
			for (int xCoordinate = 2; xCoordinate > -1; xCoordinate--) {
				if (this.board[xCoordinate][yCoordinate] != 0) {
						if (this.board[xCoordinate][yCoordinate] == this.board[xCoordinate + 1][yCoordinate]) {
							this.board[xCoordinate + 1][yCoordinate] = this.board[xCoordinate][yCoordinate] * 2;
							totalPoints += this.board[xCoordinate][yCoordinate] * 2;
							this.board[xCoordinate][yCoordinate] = 0;
						}
				}
			}
		}
		moveTile('r');
		return totalPoints;
	}

	//Purpose: Move tiles down
	//Input: N/A
	//Output: Score from tile combinations during downward movement
	//Given state of board and possible downward movement, tiles will be pushed downward as possible, combined if possible, then moved downward again as possible
	//This is to optimize movement functions to prevent array indexing errors and possible double tile merges during a single turn
	@Override
	public int down() {
		//Will mimic most code from left movement function except now along negative y-axis
		int totalPoints = 0;
		moveTile('d');
		for (int xCoordinate = 0; xCoordinate < 4; xCoordinate++) {
			for (int yCoordinate = 2; yCoordinate > -1; yCoordinate--) {
				if (this.board[xCoordinate][yCoordinate] != 0) {
					if (this.board[xCoordinate][yCoordinate] == this.board[xCoordinate][yCoordinate + 1]) {
						this.board[xCoordinate][yCoordinate + 1] = this.board[xCoordinate][yCoordinate] * 2;
						totalPoints += this.board[xCoordinate][yCoordinate] * 2;
						this.board[xCoordinate][yCoordinate] = 0;
					}
				}
			}
		}				
		moveTile('d');
		return totalPoints;
	}

	//Purpose: Move tiles up
	//Input: N/A
	//Output: Score from tile combinations during upward movement
	//Given state of board and possible upward movement, tiles will be pushed upward as possible, combined if possible, then moved upward again as possible
	//This is to optimize movement functions to prevent array indexing errors and possible double tile merges during a single turn
	@Override
	public int up() {
		//Will mimic most code from left movement function except now along positive y-axis
		int totalPoints = 0;
		moveTile('u');
		for (int xCoordinate = 0; xCoordinate < 4; xCoordinate++) {
			for (int yCoordinate = 1; yCoordinate < 4; yCoordinate++) {
				if (this.board[xCoordinate][yCoordinate] != 0) {
					if (this.board[xCoordinate][yCoordinate] == this.board[xCoordinate][yCoordinate - 1]) {
						this.board[xCoordinate][yCoordinate - 1] = this.board[xCoordinate][yCoordinate] * 2;
						totalPoints += this.board[xCoordinate][yCoordinate] * 2;
						this.board[xCoordinate][yCoordinate] = 0;
					}
				}
			}
		}
		moveTile('u');
		return totalPoints;
	}

}
