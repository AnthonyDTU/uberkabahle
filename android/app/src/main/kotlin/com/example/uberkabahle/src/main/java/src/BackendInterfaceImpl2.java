package com.example.uberkabahle.src.main.java.src;

import com.example.uberkabahle.src.main.java.src.Interfaces.Move;
import com.example.uberkabahle.src.main.java.src.Interfaces.Table;
import com.example.uberkabahle.src.main.java.src.Interfaces.comm.BackendInterface;

public class BackendInterfaceImpl2 implements BackendInterface {
    Table table;
    Algorithm algorithm;
    Match match;
    Move move;

    StringBuilder retMove = new StringBuilder();

    public void initStartTable(String cardsString){
        table = new TableIO();
        move = new Mover(table);
        algorithm = new Algorithm(table);
        table.initStartTable(cardsString);
    }

    public String getNextMove(){
        match = algorithm.checkForAnyMatch();

        if (match.match){

            //Cards from second implementation
            retMove.append(getType(match.getFromCard().getType())).append(match.getFromCard().getValue() + 1).append(",");
            retMove.append(getType(match.getToCard().getType())).append(match.getToCard().getValue() + 1).append(",");

            //Piles from first implementation
            retMove.append(String.valueOf(match.getFromPile())).append(",");
            retMove.append(String.valueOf(match.getToPile())).append(",");
            retMove.append(String.valueOf(match.complexIndex)).append(",");
            retMove.append(String.valueOf(match.getComplexFinalFoundationPile())).append(";");

        }
        else {
            retMove.append('0');
        }
        if (match.noNextInput){
            move.moveCard_OrPile(match);
            getNextMove();
        }


        return String.valueOf(retMove);
    }

    private char getType(int type){
        switch (type){
            case 0:
                return 'K';
            case 1:
                return 'H';
            case 2:
                return 'R';
            case 3:
                return 'S';
        }

        return 'Z';
    }

    public String updateTable(String cardsString){
        retMove = new StringBuilder();
        if (match.noNextInput){
            return null;
        }
        else {
            String[] cardSplit = cardsString.split(",");
            Card cardDif;

            for (int i = 0; i < cardSplit.length; i++){
                if (!cardSplit[i].equals("e")){
                    if (i == 7){
                        Card tmpCard = null;
                        if (!table.getPlayerDeck_FaceUp().isEmpty()){
                            tmpCard = table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1);
                        }
                        Card tmpCard1 = table.stringToCardConverter(cardSplit[i]);
                        if (table.getPlayerDeck_FaceUp().isEmpty() || (tmpCard.getValue() != tmpCard1.getValue() ||
                                tmpCard.getType() != tmpCard1.getType()) && match.fromPile == tmpCard.getBelongToPile()){
                            cardDif = table.stringToCardConverter(cardSplit[i]);
                            System.out.println("#### Pile 11 card difference detected ####");
                            cardDif.setFaceUp(true);
                            match.nextPlayerCard = cardDif;
                            move.moveCard_OrPile(match);
                            table.printTable();
                            return String.valueOf(cardDif.getValue());
                        }
                    }
                    else {
                        Card tmpCard = table.getAllPiles().get(i).get(table.getAllPiles().get(i).size() - 1);
                        Card tmpCard1 = table.stringToCardConverter(cardSplit[i]);
                        if ((tmpCard.getValue() != tmpCard1.getValue() || tmpCard.getType() != tmpCard1.getType()) &&
                                match.fromPile == tmpCard.getBelongToPile()){
                            System.out.println("#### Tableau card difference detected ####");
                            cardDif = table.stringToCardConverter(cardSplit[i]);
                            cardDif.setFaceUp(true);
                            match.nextPlayerCard = cardDif;
                            move.moveCard_OrPile(match);
                            table.printTable();
                            return String.valueOf(cardDif.getValue());
                        }
                    }
                }
            }

            return "No board difference";
        }
    }
}
