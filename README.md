# Blackjack

### A Java program that simulates one of the most popular casino card games in the world

### **Objective:**

>To get a higher total in the numbered value of the cards in your hand than the dealer, 
without going over a count of 21.

### **How to Play:**

- A player will interact with the program by first making a bet with their available amount of betting chips. Minimum bet starts at $5.
- Two cards will then be dealt to both the player (face up) and the dealer (first face up, second face down), in alternating order.
- The player must decide whether to ***"Stand"***, ***"Hit"***, or ***"Double"***.
  - **"Stand"** means to stop at the current count with no additional cards being drawn.
  - **"Hit"** means to draw an additional card. If the player's count goes over 21, the player has "bust" and automatically loses the round.
  - **"Double"** means to "double down", which means you would like to increase your wager by the equivalent amount to your original bet.
- When the player stands and completes their hand (without busting), the dealer's face down card will be revealed.
  - If the dealer's cards show a count of 17 to 21, the dealer stands.
  - If the dealer's cards show a count of 16 or less, the dealer will continue to draw cards until either the count is 17 to 21, or the dealer busts, whichever comes first.
- **Round Result:**
  - If the player has a higher final count than the dealer or the dealer busts, then the player wins and an amount of chips equal to the player's wager will be rewarded.
  - If the player has a final count equal to the dealer's, then the round is considered a tie or a "push".

### **Card Values:**
- Cards 2 through 10 have a numbered value equal to its face value.
- Face cards (Jacks, Queens, and Kings) have a numbered value of 10.
- Aces can be either 1 or 11, depending on the situation and whichever is more beneficial for your current hand.
- Card suits do not affect the game.

### **Vegas Rules:**
- Blackjack (10 or face card + Ace) pays out 3:2.
- Dealer stands on a count of 17 to 21.
- Dealer hits on soft 17.

### **Rationale for Project:**
I created this project because I am genuinely fascinated by this game and believe that this is one of the few casino games out there that exercise sound mathematics and logic. There is a well-documented "Basic Strategy" that has been developed over many years based on card probabilities. If a player were to follow closely to this strategy, they could significantly reduce the house edge to 0.5% (vs. ~5% in roulette, for example), making it one of the fairest casino games in existence. 

### **User Stories**
- As a user, I want to be able to change my bets each round (i.e. bets for each round of play is independent).
- As a user, I want to be able to make the decision to hit (i.e. draw additional cards to my hand) or stand (i.e. do not draw any more cards). 
- As a user, I want to be able to view my balance after the current round is complete.
- As a user, I want to be able to play a new round after the current round is complete.
- As a user, I want to be able to double down on my bets on hands where I believe I have a strong chance of beating the dealer.