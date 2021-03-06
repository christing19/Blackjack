# Blackjack

### A Java program that simulates one of the most popular casino card games in the world

### **Objective:**

>To get a higher total in the numbered value of the cards in your hand than the dealer, 
without going over a count of 21.

### **Target Audience:**
- Anyone who would like to learn about one of the most popular casino card games in the world
- This is also a good training ground for gameplay before heading out to the casino and gambling with real dollars!

### **How to Play:**

- A player will interact with the program by first making a bet with their available amount of betting chips. Minimum bet starts at $1.
- Two cards will then be dealt to both the player (face up) and the dealer (first face up, second face down), in alternating order.
- The player must decide whether to ***"Stand"***, ***"Hit"***, or ***"Double"***.
  - **"Stand"** means to stop at the current count with no additional cards being drawn.
  - **"Hit"** means to draw an additional card. If the player's count goes over 21, the player has "bust" and automatically loses the round.
  - **"Double"** means to "double down", which means you would like to increase your wager by the equivalent amount to your original bet, and take one additional card.
- When the player stands and completes their hand (without busting), the dealer's face down card will be revealed.
  - If the dealer's cards show a count within the range of 17 to 21, the dealer stands.
  - If the dealer's cards show a count of 16 or less, the dealer will continue to draw cards until either the count is within the range of 17 to 21, or the dealer busts, whichever comes first.
  
  
- **Round Result:**
  - If the player has a higher final count than the dealer or the dealer busts, then the player wins and an amount of chips equal to the player's wager will be rewarded.
  - If the player has a final count equal to the dealer's, then the round is considered a tie or a "push".

### **Card Values:**
- Cards 2 through 10 have a numbered value equal to its face value.
- Face cards (Jacks, Queens, and Kings) have a numbered value of 10.
- Aces can be either 1 or 11, depending on the situation and whichever is more beneficial for your current hand.
- Card suits do not affect the game.

### **Rules:**
- Blackjack (10 or face card + Ace) pays out 3:2.
- Dealer stands on a count of within the range of 17 to 21.
- Dealer stands on soft 17.



### **Rationale for Project:**
I created this project because I am genuinely fascinated by this game and believe that this is one of the few casino games out there that requires knowledge of probability and logic. There is a well-documented "Basic Strategy" that has been developed over the years based on card probabilities. If a player were to follow closely to this strategy, they could significantly reduce the house edge to 0.5% (vs. ~5% in roulette, for example), making it one of the fairest casino games in existence.


