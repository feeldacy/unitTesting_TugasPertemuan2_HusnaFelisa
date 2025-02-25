package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    Wallet testWallet = new Wallet();

    @Test
    void assignedOwnerChecker() {
        testWallet.setOwner("Joko");
        assertAll(
                ()->assertEquals("Joko", testWallet.getOwner()),
                ()->assertNotNull(testWallet.getOwner(),
                        "Owner not assigned"),
                ()->assertTrue(testWallet.getOwner().contains("Joko"),
                        "The expected value not fulfilled")
        );
    }


   @Test
   void cardsChecker(){
        testWallet.addCards("Credit Card");

        assertAll(
                ()->assertNotNull(testWallet.getCards(),
                        "Cards failed added to the wallet"),
                ()->assertTrue(testWallet.getCards().contains("Credit Card"),
                        "The wallet doesn't have that kind of card"),
                ()->assertEquals(1, testWallet.getCards().size(),
                        "Number of contains doesn't match with the expected value")
        );
   }

   @Test
   void emptyChecker(){

        testWallet.setOwner("");
        testWallet.addCards(null);
        testWallet.addCards("");

        assertAll(
                ()->assertNull(testWallet.getOwner(),
                        "The wallet has an owner"), // this should return passed
                ()->assertNull(testWallet.getCards().get(1),
                        "The wallet contain a card"), // this should return passed
                ()->assertEquals(1, testWallet.getCards().size(),
                        "The result not equal with the expected value") // this should return passed
        );

       // The methods should be modified so when there's a null/empty input, it shouldn't be proceeded
   }

   @Test
    void moneyChecker(){

        testWallet.addMoney(5000, false);
        testWallet.addMoney(10000, false);
        testWallet.addMoney(500, true);
        testWallet.addMoney(200, true);
        testWallet.addMoney(20000, false);

        assertAll(
                ()->assertEquals(35700, testWallet.totalMoney(),
                        "The total money is not equals"),
                ()->assertNotNull(testWallet.getBills(),
                        "The bills not added up"),
                ()->assertNotNull(testWallet.getCoins(),
                        "The coins not added up"),
                ()->assertArrayEquals(new int[]{5000, 10000, 20000},
                        testWallet.getBills().stream().mapToInt(Integer::intValue).toArray(),
                        "Doesn't match"),
                ()->assertArrayEquals(new int[]{500, 200},
                        testWallet.getCoins().stream().mapToInt(Integer::intValue).toArray(),
                        "Doesn't match")
        );
   }

   @Test
   void withdrawChecker(){

       testWallet.addMoney(5000, false);
       testWallet.addMoney(10000, false);
       testWallet.addMoney(500, true);
       testWallet.addMoney(200, true);
       testWallet.addMoney(20000, false);

       testWallet.withdrawMoney(5000);

       assertAll(
               ()->assertEquals(30700, testWallet.totalMoney()),
               ()->assertEquals(30000, testWallet.totalBills()),
               ()->assertArrayEquals(new int[]{10000, 20000},
                       testWallet.getBills().stream().mapToInt(Integer::intValue).toArray(),
                       "Doesn't match"),
               ()->assertTrue(testWallet.withdrawMoney(10000)),
               ()->assertTrue(testWallet.withdrawMoney(5000)),
               ()->assertTrue(testWallet.withdrawMoney(100)),
               ()->assertTrue(testWallet.withdrawMoney(500))
       );
   }
}