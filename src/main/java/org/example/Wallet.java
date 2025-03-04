package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Wallet {

    private String owner;
    private HashSet<String> cards;

    private ArrayList<Integer> bills;
    private ArrayList<Integer> coins;

    public Wallet(){
        this.cards = new HashSet<>();
        this.bills = new ArrayList<>();
        this.coins = new ArrayList<>();
    }

    public void setOwner(String owner) {
        if (owner.isEmpty() || owner.trim().isEmpty()){
            throw new IllegalArgumentException("The Owner can't be null or empty.");
        } else {
            this.owner = owner;
        }
    }

    public void addCards(String card){
        if (card.isEmpty() || card.trim().isEmpty()){
            throw new IllegalArgumentException("The card can't be null or empty.");
        } else {
            cards.add(card);
        }
    }

    public boolean removeCard(String card) {
        return cards.remove(card);
    }

    public void addMoney(Integer nominal, boolean isCoin){
        if (nominal > 0){
            if (isCoin){
                coins.add(nominal);
            } else {
                bills.add(nominal);
            }
        }
    }

    public Integer totalMoney(){
        return totalBills() + totalCoins();
    }

    public Integer totalBills(){
        return bills.stream().mapToInt(Integer::intValue).sum();
    }

    public Integer totalCoins(){
        return coins.stream().mapToInt(Integer::intValue).sum();
    }

    public boolean withdrawMoney(int amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }

        if (amount > totalMoney()) {
            System.out.println("You don't have that much money!!");
            return false;
        }

        int remainingAmount = amount;

        ArrayList<Integer> tempBills = new ArrayList<>(bills);
        ArrayList<Integer> tempCoins = new ArrayList<>(coins);

        tempBills.sort(Collections.reverseOrder());
        tempCoins.sort(Collections.reverseOrder());

        ArrayList<Integer> toRemoveBills = new ArrayList<>();
        ArrayList<Integer> toRemoveCoins = new ArrayList<>();

        for (Integer bill : tempBills) {
            if (remainingAmount >= bill) {
                remainingAmount -= bill;
                toRemoveBills.add(bill);
                if (remainingAmount == 0) break;
            }
        }

        if (remainingAmount > 0) {
            for (Integer coin : tempCoins) {
                if (remainingAmount >= coin) {
                    remainingAmount -= coin;
                    toRemoveCoins.add(coin);
                    if (remainingAmount == 0) break;
                }
            }
        }

        if (remainingAmount > 0) {
            System.out.println("You don't have the exact amount of money to withdraw.");
            return false;
        }

        bills.removeAll(toRemoveBills);
        coins.removeAll(toRemoveCoins);

        System.out.println("Successfully withdraw " + amount + " money");
        return true;
    }


    public void clearWallet(){
        cards.clear();
        bills.clear();
        coins.clear();
    }

    public ArrayList<Integer> getBills() {
        return bills;
    }

    public ArrayList<Integer> getCoins() {
        return coins;
    }

    public String getOwner() {
        return owner;
    }

    public HashSet<String> getCards() {
        return cards;
    }

    public static void openConnection(){
        System.out.println("Connected to the server....");
    }

    public static void closeConnection(){
        System.out.println("Close connection....");
    }

}
