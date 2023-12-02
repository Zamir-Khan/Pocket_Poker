package UTD.cs.edu.pocket_poker;

import static UTD.cs.edu.pocket_poker.HostActivity.MESSAGE_READ;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import utd.cs.edu.pokect_poker.R;

public class TableActivity extends AppCompatActivity {

    private static final int MESSAGE_SEND_GAME_STATE = 1;

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private HostClientCommunication communicationThread;
    boolean condition = false;

    private Deck deck;
    private HostManager hostManager;
    private List<Player> players;

    private TextView P1Hand;
    private TextView P2Hand;


    private List<Card> initialCards = new ArrayList<>();
    private GamePhase initialPhase = GamePhase.PRE_FLOP;
    private GameState gameState;

    private ImageView[] hostCardImageViews;
    private ImageView[] clientCardImageViews;

    private final boolean isHostTurn = true; // Initial turn for the host player
    private final List<Card> hostCards = new ArrayList<>();
    private final List<Card> clientCards = new ArrayList<>();


    private static final String TAG = "TableActivity";

    // Define the maximum number of cards in a player's hand
    private static final int MAX_CARDS_IN_HAND = 5;


    private List<String> currentGameState = new ArrayList<>();

    private TextView gameStateTextView;


    // Variable to keep track of the round
    private int round = 0;

    public class Player {
        private final String name;
        private final List<Card> hand;

        public Player(String name) {
            this.name = name;
            this.hand = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public List<Card> getHand() {
            return hand;
        }

        public void addCard(Card card) {
            hand.add(card);
        }

        // Adjust the clearHand method
        public void clearHand() {
            hand.clear();
        }

        @Override
        public String toString() {
            // Your existing implementation for representing the player as a string
            // ...
            return "";
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        // **** Creating necessary class instances for gameplay
        deck = new Deck();
        deck.shuffle();

        communicationThread = new HostClientCommunication();
        communicationThread.start();

        // Initialize HostManager
        hostManager = new HostManager();

        // Create GameState with the initial cards and an initial game phase


        initialCards.addAll(hostCards);
        initialCards.addAll(clientCards);

        // Correct the initialization of gameState
        gameState = new GameState(initialCards, initialPhase);

        // Correct the method call
        updateCardsOnScreen(gameState.getCards());

        wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = wifiP2pManager.initialize(this, getMainLooper(), null);
        discoverPeers();
        communicationThread = new HostClientCommunication();
        communicationThread.start();

        gameStateTextView = findViewById(R.id.gameStateTextView);

        communicationThread = new HostClientCommunication();
        communicationThread.start();

        // Simulate dealing cards for the host (you may have a different mechanism)
        dealCardsForHost();


        // Deal initial cards to players
        hostCards.add(deck.dealCard());
        hostCards.add(deck.dealCard());
        hostCards.add(deck.dealCard());

        clientCards.add(deck.dealCard());
        clientCards.add(deck.dealCard());
        clientCards.add(deck.dealCard());

        // Now you can use gameState.getCards() to get the cards and update the UI
        updateCardsOnScreen(gameState.getCards());


        // Create players list

        try {
            if (players == null || players.isEmpty()) {
        players = new ArrayList<>();
        for(int i = 1; i < 3; i++){
            String str = Integer.toString(i);
            players.add(new Player("Player_" + str));
        }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Log the exception or show a Toast to help identify the issue
            Toast.makeText(TableActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
        }


        // **** View instances to manipulate gameplay
        Button callButton = findViewById(R.id.callBtn);
        Button raiseButton = findViewById(R.id.raiseBtn);
        Button foldButton = findViewById(R.id.foldBtn);

       // TextView P1Hand = findViewById(R.id.P1Hand);    // Display player 1 hand
        // TextView P2Hand = findViewById(R.id.P2Hand);    // Display player 2 hand

        P1Hand = findViewById(R.id.P1Hand);
        P2Hand = findViewById(R.id.P2Hand);


        TextView test = findViewById(R.id.Test);
        TextView P1Name = findViewById(R.id.PlayerName1);
        TextView P2Name = findViewById(R.id.PlayerName2);
        P1Name.setText(players.get(0).getName());
        P2Name.setText(players.get(1).getName());

        ImageView p1c1 = findViewById(R.id.p1card1);
        ImageView p1c2 = findViewById(R.id.p1card2);
        ImageView p1c3 = findViewById(R.id.p1card3);
        ImageView p1c4 = findViewById(R.id.p1card4);
        ImageView p1c5 = findViewById(R.id.p1card5);

        ImageView p2c1 = findViewById(R.id.p2card1);
        ImageView p2c2 = findViewById(R.id.p2card2);
        ImageView p2c3 = findViewById(R.id.p2card3);
        ImageView p2c4 = findViewById(R.id.p2card4);
        ImageView p2c5 = findViewById(R.id.p2card5);

        Drawable[] P1card = new Drawable[5];
        Drawable[] P2card = new Drawable[5];

        // **** Action for Call button
        // (currently serving as debugger for Players)
        callButton.setOnClickListener(new View.OnClickListener() {
            int round = 0;
            @Override
            public void onClick(View view) {
                hostManager.handleClientRequest("call",round);
                try {
                if (round < 5) {

                    try {
                        if (deck == null) {
                            // Initialize the deck if it's null
                            deck = new Deck();
                            deck.shuffle();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        // Log the exception or show a Toast to help identify the issue
                        Toast.makeText(TableActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                    Card dealtCard1 = deck.dealCard();
                    Card dealtCard2 = deck.dealCard();


                    // Deal cards to host and client
                    dealCardToPlayer(players.get(0), p1c1);
                    dealCardToPlayer(players.get(1), p2c1);

                    // Displaying hands
                    updateHandDisplay();

                    // Update turn display
                    test.setText("Turn for: " + players.get(playerTurn(players, round)).getName());

                    round++;






                    String cardname1 = dealtCard1.toString();
                    int resourceID1 = getResources().getIdentifier(cardname1, "drawable", getPackageName());

                    String cardname2 = dealtCard2.toString();
                    int resourceID2 = getResources().getIdentifier(cardname2, "drawable", getPackageName());

                    if (resourceID1 != 0 && resourceID2 != 0) {
                        switch (round) {
                            case 0:
                                // Deal the face down card
                                P1card[0] = getResources().getDrawable(resourceID1);
                                P2card[0] = getResources().getDrawable(resourceID2);
                                //p1c1.setImageDrawable(P1card[0]);
                                //p2c1.setImageDrawable(P2card[0]);
                                p1c1.setImageDrawable(getDrawable(R.drawable.card_back));
                                p2c1.setImageDrawable(getDrawable(R.drawable.card_back));
                                // ------------------------------------------------------

                                // Deal the second card (face up)
                                dealtCard1 = deck.dealCard();
                                dealtCard2 = deck.dealCard();
                                players.get(0).addCard(dealtCard1);
                                players.get(1).addCard(dealtCard2);

                                cardname1 = dealtCard1.toString();
                                resourceID1 = getResources().getIdentifier(cardname1, "drawable", getPackageName());
                                cardname2 = dealtCard2.toString();
                                resourceID2 = getResources().getIdentifier(cardname2, "drawable", getPackageName());

                                P1card[1] = getResources().getDrawable(resourceID1);
                                P2card[1] = getResources().getDrawable(resourceID2);
                                p1c2.setImageDrawable(P1card[1]);
                                p2c2.setImageDrawable(P2card[1]);

                                round++;
                                break;
                            case 2:
                                P1card[2] = getResources().getDrawable(resourceID1);
                                P2card[2] = getResources().getDrawable(resourceID2);
                                p1c3.setImageDrawable(P1card[2]);
                                p2c3.setImageDrawable(P2card[2]);
                                break;
                            case 3:
                                P1card[3] = getResources().getDrawable(resourceID1);
                                P2card[3] = getResources().getDrawable(resourceID2);
                                p1c4.setImageDrawable(P1card[3]);
                                p2c4.setImageDrawable(P2card[3]);
                                break;
                            case 4:
                                P1card[4] = getResources().getDrawable(resourceID1);
                                P2card[4] = getResources().getDrawable(resourceID2);
                                p1c5.setImageDrawable(P1card[4]);
                                p2c5.setImageDrawable(P2card[4]);
                                break;
                        }
                    }

                    P1Hand.setText(players.get(0).toString());     // Displaying Player hand
                    P2Hand.setText(players.get(1).toString());
                    int turn = playerTurn(players, round);
                    test.setText("Turn for: " + players.get(playerTurn(players, round)).getName());
                    round ++;
                }
                else
                    condition = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    // Log the exception or show a Toast to help identify the issue
                    Toast.makeText(TableActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }

        });



                // **** Action for Raise button
        // (currently serving as debugger for Player 2)
        raiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(condition){
                    List<Player> winners = getWinner(players);
                    StringBuilder strBuild = new StringBuilder("Winners: \n");
                    for (int i = 0; i < winners.size(); i++) {
                        strBuild.append(winners.get(i).getName())
                                .append(", Hand: ")
                                .append(getRankNames(getResult(winners.get(i))))
                                .append("\n");
                    }
                    test.setText(strBuild.toString());
                }
            }
        });

        // **** Action for Fold button
        foldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (Player player : players) {
                    player.clearHand();
                }

                round = 0;
                condition = false;

                // Clear card image views
                clearCardImageViews();

                // Update UI
                updateHandDisplay();
                test.setText("Fold Action");

            }
        });
    }


    private void discoverPeers() {
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Discovery initiation successful
                // You can listen for peers using a BroadcastReceiver
            }

            @Override
            public void onFailure(int reasonCode) {
                // Discovery initiation failed
                Toast.makeText(TableActivity.this, "Discovery failed. Reason: " + reasonCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Handle WiFi Direct connection info
    private final WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            if (info.groupFormed && info.isGroupOwner) {

                Message message = handler.obtainMessage(MESSAGE_SEND_GAME_STATE, gameState);
                handler.sendMessage(message);
            }
        }
    };


    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MESSAGE_READ) {
                byte[] readBuff = (byte[]) msg.obj;
                // Deserialize the received data into a GameState object
                GameState receivedState = deserializeGameState(readBuff, msg.arg1);

                // Update UI or game logic based on the received state
                updateGameState(receivedState);

                GameState gameState = new GameState(initialCards, initialPhase);

                List<Card> cards = gameState.getCards();
                List<String> cardNames = new ArrayList<>();

                for (Card card : cards) {
                    cardNames.add(card.toString()); // Replace with your actual method to get the card name
                }
                Log.d("GameState", "Client: Updated game state - " + cardNames.toString());
                updateCardsOnScreen(gameState.getCards());
            }
            return true;
        }
    });
    private GameState deserializeGameState(byte[] data, int length) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data, 0, length);
             ObjectInput in = new ObjectInputStream(bis)) {
            return (GameState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateGameState(GameState gameState) {
        // Implement logic to update the UI or game state in TableActivity
        // For example, update the cards on the screen based on the received state

        updateCardsOnScreen(gameState.getCards());

        // Example: Update other UI elements
        gameStateTextView.setText("Current game state: " + gameState.getGameStateInfo());


    }
    private void updateCardsOnScreen(List<Card> cards) {

        Drawable[] cardDrawables = new Drawable[cards.size()];

        for (int i = 0; i < cards.size(); i++) {
            cardDrawables[i] = cardToDrawable(cards.get(i));
        }

        ImageView p1c1 = findViewById(R.id.p1card1);
        ImageView p1c2 = findViewById(R.id.p1card2);
        ImageView p1c3 = findViewById(R.id.p1card3);
        ImageView p1c4 = findViewById(R.id.p1card4);
        ImageView p1c5 = findViewById(R.id.p1card5);

        ImageView p2c1 = findViewById(R.id.p2card1);
        ImageView p2c2 = findViewById(R.id.p2card2);
        ImageView p2c3 = findViewById(R.id.p2card3);
        ImageView p2c4 = findViewById(R.id.p2card4);
        ImageView p2c5 = findViewById(R.id.p2card5);

        for (int i = 0; i < cardDrawables.length; i++) {
            if (i < 5) {
                // Update player 1 cards
                switch (i) {
                    case 0:
                        p1c1.setImageDrawable(cardDrawables[i]);
                        break;
                    case 1:
                        p1c2.setImageDrawable(cardDrawables[i]);
                        break;
                    case 2:
                        p1c3.setImageDrawable(cardDrawables[i]);
                        break;
                    case 3:
                        p1c4.setImageDrawable(cardDrawables[i]);
                        break;
                    case 4:
                        p1c5.setImageDrawable(cardDrawables[i]);
                        break;
                }
            } else {
                // Update player 2 cards
                int player2Index = i - 5;
                switch (player2Index) {
                    case 0:
                        p2c1.setImageDrawable(cardDrawables[i]);
                        break;
                    case 1:
                        p2c2.setImageDrawable(cardDrawables[i]);
                        break;
                    case 2:
                        p2c3.setImageDrawable(cardDrawables[i]);
                        break;
                    case 3:
                        p2c4.setImageDrawable(cardDrawables[i]);
                        break;
                    case 4:
                        p2c5.setImageDrawable(cardDrawables[i]);
                        break;
                }
            }
        }

    }

    private Drawable cardToDrawable(Card card) {
        String cardName = card.getRank().toString().toLowerCase() + "_" + card.getSuit().toString().toLowerCase();

        // Get the resource ID for the drawable based on the card name
        int resourceId = getResources().getIdentifier(cardName, "drawable", getPackageName());

        // Return the Drawable
        return getResources().getDrawable(resourceId);
    }

    private int getCardResourceID(Card card) {

        String cardName = card.getRank() + "_" + card.getSuit(); // Assuming getRank() and getSuit() return the rank and suit of the card
        return getResources().getIdentifier(cardName.toLowerCase(), "drawable", getPackageName());
    }

    // Method to simulate dealing cards for the host
    private void dealCardsForHost() {
        List<String> initialHand = new ArrayList<>();
        for (int i = 0; i < MAX_CARDS_IN_HAND; i++) {
            initialHand.add("Card" + (i + 1));
        }

        // Update the local game state and UI
        updateGameState(initialHand);
    }

    private void updateGameState(List<String> newGameState) {
        List<String> cardNames = new ArrayList<>();

        for (String card : newGameState) {
            cardNames.add(card);
        }
    }

    public class HostManager {
        // ... Existing HostManager code ...

        public void handleClientRequest(String request, int round) {
            if (request.equals("call")) {
                handleCallRequest(request, round);
            } else if (request.equals("fold")) {
                handleFoldRequest();
            }
            // Add more handlers for other client requests
        }

        private void handleCallRequest(String request, int round) {
            // Implement logic to handle the call request
            // For example, update the game state, handle the call amount, etc.
            // ...

            // Send updates to all clients
            if (request.equals("call")) {
                handleCallRequest(request,round);
            } else if (request.equals("fold")) {
                // Handle fold request
                // Update game state and send updates to all clients
                // ...
                sendUpdatedGameStateToClients();
            }
        }

        private void handleFoldRequest() {
            // Implement logic to handle the fold request
            // For example, mark the player as folded, update the game state, etc.
            // ...

            // Send updates to all clients
            sendUpdatedGameStateToClients();
        }}

    private void handleCallRequest(int round) {
    }

    private void sendUpdatedGameStateToClients() {
    }

    // Communication thread for sending and receiving game state messages
    private class HostClientCommunication extends Thread {

        private static final int SERVER_PORT = 8888;

        @Override
        public void run() {
            try {
                // Host (server) side
                ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
                Socket clientSocket = serverSocket.accept();

                // Send initial game state to client
                List<String> initialGameState = getInitialGameState();
                sendGameStateToClient(clientSocket, getInitialGameState());
                Log.d("GameState", "Host: Sent initial game state to client - " + initialGameState.toString());

                // Simulate receiving a game state message from the client
                List<String> receivedGameState = receiveGameStateFromClient(clientSocket);
                updateGameState(receivedGameState);
                Log.d("GameState", "Host: Received game state from client - " + receivedGameState.toString());

                // Simulate sending a game state message to the client
                List<String> newGameState = getUpdatedGameState();
                sendGameStateToClient(clientSocket, newGameState);
                Log.d("GameState", "Host: Sent updated game state to client - " + newGameState.toString());


                // Close sockets
                clientSocket.close();
                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        }


    private void sendGameStateToClient(Socket clientSocket, List<String> gameState) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {
            outputStream.writeObject(gameState);
        }
    }

    private List<String> receiveGameStateFromClient(Socket clientSocket) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            return (List<String>) inputStream.readObject();
        }
    }

    private List<String> getInitialGameState() {
        // Replace with your logic to initialize the game state
        Deck deck = new Deck(); // Assuming you have a Deck class

        // Draw two cards for each player (assuming you have two players)
        List<String> initialGameState = new ArrayList<>();
        for (int player = 1; player <= 2; player++) {
            // Draw the face-down card
            Card faceDownCard = deck.dealCard();
            initialGameState.add(faceDownCard.toString()); // Assuming you have a toString method in your Card class

            // Draw the face-up card
            Card faceUpCard = deck.dealCard();
            initialGameState.add(faceUpCard.toString()); // Assuming you have a toString method in your Card class
        }

        return initialGameState;
    }

    private List<String> getUpdatedGameState() {
        // Replace with your logic to update the game state
        Deck deck = new Deck(); // Assuming you have a Deck class

        // Assuming currentGameState has the format [Player1_FaceDown, Player1_FaceUp, Player2_FaceDown, Player2_FaceUp]

        // Identify the player whose turn it is to receive a new face-up card
        int currentPlayer = (currentGameState.size() / 2) + 1; // Assuming each player has two cards

        // Draw a new face-up card for the current player
        Card newFaceUpCard = deck.dealCard();
        String newFaceUpCardString = newFaceUpCard.toString(); // Assuming you have a toString method in your Card class

        // Update the game state with the new face-up card
        int indexOfFaceUpCard = (currentPlayer - 1) * 2 + 1; // Assuming 0-based indexing
        currentGameState.set(indexOfFaceUpCard, newFaceUpCardString);

        return currentGameState;
    }



    // Helper method to deal a card to a player and update the UI
    private void dealCardToPlayer(Player player, ImageView cardImageView) {
        Card dealtCard = deck.dealCard();
        player.addCard(dealtCard);

        String cardName = dealtCard.toString();
        int resourceID = getResources().getIdentifier(cardName, "drawable", getPackageName());

        if (resourceID != 0) {
            // Set the image drawable for the cardImageView
            cardImageView.setImageDrawable(getResources().getDrawable(resourceID));

            // If the player is the host, reveal all cards; if the player is the client, hide the first card
            if (player == players.get(0)) {
                cardImageView.setVisibility(View.VISIBLE);
            } else {
                // For the client, hide the first card (index 0)
                if (isHostTurn) {
                    cardImageView.setVisibility(View.VISIBLE);
                } else {
                    cardImageView.setImageDrawable(getDrawable(R.drawable.card_back));
                    cardImageView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    // Helper method to update the hand display text views
    private void updateHandDisplay() {
        P1Hand.setText(players.get(0).toString());
        P2Hand.setText(players.get(1).toString());
    }

    // Helper method to clear card image views
    private void clearCardImageViews() {
        for (ImageView imageView : hostCardImageViews) {
            imageView.setImageDrawable(getDrawable(R.drawable.card_back));
        }

        for (ImageView imageView : clientCardImageViews) {
            imageView.setImageDrawable(getDrawable(R.drawable.card_back));
        }
    }

    public static int playerTurn(List<Player> players, int round){
        List<Integer> cardRanks = new ArrayList<>();
        int returnVal = 0;
        for(int i = 0; i < players.size(); i++){
            cardRanks.add(players.get(i).getHand().get(players.get(i).getHand().size() - 1).getPoint());
        }

        if(round == 1){
            int min = Collections.min(cardRanks);

            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getHand().get(players.get(i).getHand().size() - 1).getPoint() == min){
                    returnVal = i;
                    break;
                }
            }
        }

        else{
            int max = Collections.max(cardRanks);

            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getHand().get(players.get(i).getHand().size() - 1).getPoint() == max){
                    returnVal = i;
                    break;
                }
            }
        }

        return returnVal;
    }

    public static int getResult(Player player){
        List<Card> cardList = player.getHand();

        //list of suits
        String suit_01 = cardList.get(0).getSuit();
        String suit_02 = cardList.get(1).getSuit();
        String suit_03 = cardList.get(2).getSuit();
        String suit_04 = cardList.get(3).getSuit();
        String suit_05 = cardList.get(4).getSuit();

        //list of ranks
        String rank_01 = cardList.get(0).getRank();
        String rank_02 = cardList.get(1).getRank();
        String rank_03 = cardList.get(2).getRank();
        String rank_04 = cardList.get(3).getRank();
        String rank_05 = cardList.get(4).getRank();

        //list of points
        int[] pts = new int[5];
        for(int i = 0; i < 5; i++){
            pts[i] = cardList.get(i).getPoint();
        }
        int[] sorted_pts = sort(pts);

        //checking whether all the suits are same
        if((suit_01.equals(suit_02)) && (suit_01.equals(suit_03)) && (suit_01.equals(suit_04)) && (suit_01.equals(suit_05))){
            int total_Point = 0;
            for(int i = 0; i < 5; i++){
                total_Point += cardList.get(i).getPoint();
            }

            //Royal Flush
            if(total_Point == 55){
                return 10;
            }

            //Straight Flush
            else if ((sorted_pts[4] - sorted_pts[3] == 1) && (sorted_pts[3] - sorted_pts[2] == 1) && (sorted_pts[2] - sorted_pts[1] == 1) &&
                    (sorted_pts[1] - sorted_pts[0] == 1)){
                return 9;
            }

            //Flush
            else{
                return 6;
            }
        }

        //checking for Four Of A Kind
        if(((rank_01.equals(rank_02)) && (rank_01.equals(rank_03)) && (rank_01.equals(rank_04))) ||
                ((rank_01.equals(rank_02)) && (rank_01.equals(rank_04)) && (rank_01.equals(rank_05))) ||
                ((rank_01.equals(rank_02)) && (rank_01.equals(rank_03)) && (rank_01.equals(rank_05))) ||
                ((rank_02.equals(rank_03)) && (rank_02.equals(rank_04)) && (rank_02.equals(rank_05))) ||
                ((rank_01.equals(rank_05)) && (rank_01.equals(rank_03)) && (rank_01.equals(rank_04)))){
            return 8;
        }

        //checking for full house
        if(((rank_01.equals(rank_02)) && (rank_01.equals(rank_03)) && (rank_05.equals(rank_04))) ||
                ((rank_01.equals(rank_02)) && (rank_01.equals(rank_04)) && (rank_05.equals(rank_03))) ||
                ((rank_01.equals(rank_02)) && (rank_01.equals(rank_05)) && (rank_03.equals(rank_04))) ||
                ((rank_01.equals(rank_03)) && (rank_01.equals(rank_04)) && (rank_05.equals(rank_02))) ||
                ((rank_01.equals(rank_05)) && (rank_01.equals(rank_03)) && (rank_02.equals(rank_04))) ||
                ((rank_01.equals(rank_04)) && (rank_01.equals(rank_05)) && (rank_02.equals(rank_03))) ||
                ((rank_02.equals(rank_03)) && (rank_02.equals(rank_04)) && (rank_05.equals(rank_01))) ||
                ((rank_02.equals(rank_03)) && (rank_02.equals(rank_05)) && (rank_01.equals(rank_04))) ||
                ((rank_02.equals(rank_04)) && (rank_02.equals(rank_05)) && (rank_01.equals(rank_03))) ||
                ((rank_03.equals(rank_04)) && (rank_05.equals(rank_03)) && (rank_01.equals(rank_02)))){
            return 7;
        }

        //checking for simple straight
        if ((sorted_pts[4] - sorted_pts[3] == 1) && (sorted_pts[3] - sorted_pts[2] == 1) &&
                (sorted_pts[2] - sorted_pts[1] == 1) && (sorted_pts[1] - sorted_pts[0] == 1)){
            return 5;
        }

        //checking for 3-of-a-kind
        if(((rank_01.equals(rank_02)) && (rank_01.equals(rank_03)))||
                ((rank_01.equals(rank_02)) && (rank_01.equals(rank_04))) || ((rank_01.equals(rank_02)) && (rank_01.equals(rank_05))) ||
                ((rank_01.equals(rank_03)) && (rank_01.equals(rank_04))) || ((rank_01.equals(rank_05)) && (rank_01.equals(rank_03))) ||
                ((rank_01.equals(rank_04)) && (rank_01.equals(rank_05))) || ((rank_02.equals(rank_03)) && (rank_02.equals(rank_04))) ||
                ((rank_02.equals(rank_03)) && (rank_02.equals(rank_05))) || ((rank_02.equals(rank_04)) && (rank_02.equals(rank_05))) ||
                ((rank_03.equals(rank_04)) && (rank_05.equals(rank_03)))){
            return 4;
        }

        //checking for two pairs
        if(((rank_01.equals(rank_02)) && (rank_03.equals(rank_04))) || ((rank_01.equals(rank_02)) && (rank_03.equals(rank_05))) ||
                ((rank_01.equals(rank_02)) && (rank_04.equals(rank_05))) || ((rank_01.equals(rank_03)) && (rank_02.equals(rank_04))) ||
                ((rank_01.equals(rank_03)) && (rank_02.equals(rank_05))) || ((rank_01.equals(rank_03)) && (rank_04.equals(rank_05))) ||
                ((rank_01.equals(rank_04)) && (rank_02.equals(rank_03))) || ((rank_01.equals(rank_04)) && (rank_02.equals(rank_05))) ||
                ((rank_01.equals(rank_04)) && (rank_03.equals(rank_05))) || ((rank_01.equals(rank_05)) && (rank_02.equals(rank_03))) ||
                ((rank_01.equals(rank_05)) && (rank_02.equals(rank_04))) || ((rank_01.equals(rank_05)) && (rank_03.equals(rank_04))) ||
                ((rank_02.equals(rank_03)) && (rank_04.equals(rank_05))) || ((rank_02.equals(rank_04)) && (rank_03.equals(rank_05))) ||
                ((rank_02.equals(rank_05)) && (rank_03.equals(rank_04)))){
            return 3;
        }

        //checking for a pair
        if((rank_01.equals(rank_02)) || (rank_01.equals(rank_03)) || (rank_01.equals(rank_04)) || (rank_01.equals(rank_05)) ||
                (rank_02.equals(rank_03)) || (rank_02.equals(rank_04)) || (rank_02.equals(rank_05)) || (rank_03.equals(rank_04)) ||
                (rank_03.equals(rank_05)) || (rank_04.equals(rank_05))){
            return 2;
        }

        //doesn't fall into any category
        return 1;
    }

    //Determines winner based on their hand
    public static List<Player> getWinner(List<Player> players){
        List<Integer> player_points = new ArrayList<>();

        //getting all the results
        for(int i = 0; i < players.size(); i++){
            player_points.add(getResult(players.get(i)));
        }

        //getting the winners point
        Integer max = Collections.max(player_points);

        List<Player> winners = new ArrayList<>();

        //adding whoever matches the winning point to the list
        for(int i = 0; i < players.size(); i++){
            if(player_points.get(i) == max){
                winners.add(players.get(i));
            }
        }

        //Breaking ties if all the players have same winning hand by highest card
        if(winners.size() == players.size()){
            winners.clear();

            //getting the indexes of players holding the highest card
            List<Integer> indexes = getHighestCard(players);
            for(int i = 0; i < indexes.size(); i++){
                winners.add(players.get(indexes.get(i)));
            }
        }

        return winners;
    }

    //Breaking ties with highest card
    public static List<Integer> getHighestCard(List<Player> players){
        //reusable arrayList to store all the card's rank points of a single player
        List<Integer> max = new ArrayList<>();

        //list to store the maximum rank number of each player
        List<Integer> plMax = new ArrayList<>();


        for(int i = 0; i < players.size(); i++){
            for(int j = 0; j < 5; j++){
                max.add(players.get(i).getHand().get(j).getPoint());
            }
            int maximum = Collections.max(max);
            plMax.add(maximum);
            max.clear();
        }

        //listing the indexes of the players holding the highest card
        List<Integer> indexes = new ArrayList<>();
        int maximum = Collections.max(plMax);
        for(int i = 0; i < players.size(); i++){
            if(plMax.get(i) == maximum){
                indexes.add(i);
            }
        }
        return indexes;
    }

    //Getting specific names for the players' hands
    public static String getRankNames(int num){
        String str = "";

        switch (num){
            case 1:
                str = "High Card";
                break;
            case 2:
                str = "A Pair";
                break;
            case 3:
                str = "Two Pairs";
                break;
            case 4:
                str = "Three of A Kind";
                break;
            case 5:
                str = "Straight";
                break;
            case 6:
                str = "Flush";
                break;
            case 7:
                str = "Full House";
                break;
            case 8:
                str = "Four of A Kind";
                break;
            case 9:
                str = "Straight Flush";
                break;
            case 10:
                str = "Royal Flush";
                break;
        }
        return str;
    }

    // Standard insertion sort
    public static int[] sort(int[] arr)
    {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        return arr;
    }
}


