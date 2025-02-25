package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Wallet {

    private String owner;
    private ArrayList<String> cards;

    private ArrayList<Integer> bills;
    private ArrayList<Integer> coins;

    public Wallet(){
        this.cards = new ArrayList<>();
        this.bills = new ArrayList<>();
        this.coins = new ArrayList<>();
    }

    public String setOwner(String owner) {
        this.owner = owner;
        return getOwner();
    }

    public void addCards(String card){
        cards.add(card);
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public boolean removeCard(String card) {
        return cards.remove(card);
    }

    public void addMoney(Integer nominal, boolean isCoin){
        if (isCoin){
            coins.add(nominal);
        } else {
            bills.add(nominal);
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
        if (amount > totalMoney()) {
            System.out.println("You don't have that much money!!");
            return false;
        }

        int remainingAmount = amount;

        ArrayList<Integer> allMoney = new ArrayList<>();

        allMoney.addAll(bills);
        allMoney.addAll(coins);
        allMoney.sort(Collections.reverseOrder());

        ArrayList<Integer> toRemove = new ArrayList<>();
        for (Integer money : allMoney) {
            if (remainingAmount >= money) {
                remainingAmount -= money;
                toRemove.add(money);
                if (remainingAmount == 0) break;
            }
        }

        if (remainingAmount > 0) {
            System.out.println("You don't have the exact amount of money to withdraw.");
            return false;
        }

        bills.removeAll(toRemove);
        coins.removeAll(toRemove);

        System.out.println("Successfully withdraw " + amount + " money");
        return true;
    }

    public ArrayList<Integer> getBills() {
        return bills;
    }

    public ArrayList<Integer> getCoins() {
        return coins;
    }

    public static void main(String[] args) {
        Wallet dompetJoko = new Wallet();
        dompetJoko.setOwner("Joko");
        dompetJoko.addCards("Credit Card");
        dompetJoko.addCards("Debit Card");

        dompetJoko.addMoney(5000, false);
        dompetJoko.addMoney(1000, false);
        dompetJoko.addMoney(500, true);
        dompetJoko.addMoney(200, true);

        System.out.println(dompetJoko.getCards());
        System.out.println(dompetJoko.getOwner());
        System.out.println("Total Money: " + dompetJoko.totalMoney());

        dompetJoko.withdrawMoney(5700);

        System.out.println("Remaining Money: " + dompetJoko.totalMoney());
    }
}
