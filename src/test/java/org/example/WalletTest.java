package org.example;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    private static Wallet testWallet;

    @BeforeAll
    static void setupClass(){
        Wallet.openConnection();
        testWallet = new Wallet();
    }

    @BeforeEach
    void setupMethod(){
        testWallet.setOwner("Joko");

        testWallet.addMoney(5000, false);
        testWallet.addMoney(10000, false);
        testWallet.addMoney(500, true);
        testWallet.addMoney(200, true);
        testWallet.addMoney(20000, false);

        testWallet.addCards("Visa");
        testWallet.addCards("Debit");
        testWallet.addCards("Credit");
        testWallet.addCards("ID");
    }

    @Test
    void testSetOwner() {
        assertAll(
                ()->assertEquals("Joko", testWallet.getOwner()),
                ()->assertNotNull(testWallet.getOwner(),
                        "The owner should be Joko"),
                ()->assertTrue(testWallet.getOwner().contains("Joko"),
                        "The expected value not fulfilled")
        );
    }


   @Test
   void testAddCards(){
        testWallet.addCards("Visa");
        testWallet.addCards("Company Card");

        assertAll(
                ()->assertNotNull(testWallet.getCards(),
                        "There should be cards in the wallet"),
                ()->assertTrue(testWallet.getCards().contains("Credit"),
                        "The wallet doesn't have that kind of card, " +
                                "card failed to be added"),
                ()->assertEquals(5, testWallet.getCards().size(),
                        "The wallet should be contains 5 cards"),
                ()->assertArrayEquals(new String[]{"Debit", "Credit", "Visa", "Company Card", "ID"},
                        testWallet.getCards().stream().map(String::toString).toArray(),
                        "Doesn't match")
        );
   }

   @Test
   void testRemoveCard(){
        testWallet.removeCard("Visa");

        assertAll(
                ()->assertFalse(testWallet.getCards().contains("Visa"),
                        "The visa card should be not in Cards"),
                ()->assertEquals(3, testWallet.getCards().size(),
                        "The size of Cards contained should be 3"),
                ()->assertArrayEquals(new String[]{"Debit", "Credit", "ID"},
                        testWallet.getCards().stream().map(String::toString).toArray(),
                        "Doesn't match")
        );
   }

   @Test
   void testEmptyInput(){

        testWallet.setOwner("");
        testWallet.addCards(null);
        testWallet.addCards("");

        assertAll(
                ()->assertNull(testWallet.getOwner(),
                        "The wallet shouldn't accept empty string or null value"),
                ()->assertEquals(4, testWallet.getCards().size(),
                        "The wallet shouldn't accept empty string or null value")
        );
   }

   @Test
    void testAddMoney(){

        assertAll(
                ()->assertEquals(35700, testWallet.totalMoney(),
                        "The total money should be 35700"),
                ()->assertNotNull(testWallet.getBills(),
                        "The bills shouldn't be null"),
                ()->assertNotNull(testWallet.getCoins(),
                        "The coins shouldn't be null"),
                ()->assertEquals(3, testWallet.getBills().size(),
                        "The bills should be contain 3 bills"),
                ()->assertEquals(2, testWallet.getCoins().size(),
                        "The coins should be contain 2 coins"),
                ()->assertArrayEquals(new int[]{5000, 10000, 20000},
                        testWallet.getBills().stream().mapToInt(Integer::intValue).toArray(),
                        "Doesn't match"),
                ()->assertArrayEquals(new int[]{500, 200},
                        testWallet.getCoins().stream().mapToInt(Integer::intValue).toArray(),
                        "Doesn't match")
        );
   }

   @Test
   void testWithdrawMoney(){

       testWallet.withdrawMoney(5000);

       assertAll(
               ()->assertEquals(30700, testWallet.totalMoney(),
                       "The total money should be 30700"),
               ()->assertEquals(30000, testWallet.totalBills(),
                       "The total bills should be 30000"),
               ()->assertArrayEquals(new int[]{10000, 20000},
                       testWallet.getBills().stream().mapToInt(Integer::intValue).toArray(),
                       "Doesn't match"),
               ()->assertTrue(testWallet.withdrawMoney(10000),
                       "The process should be executed, withdrawing 10000"),
               ()->assertTrue(testWallet.withdrawMoney(5000),
                       "There's no bills with 5000 value"),
               ()->assertTrue(testWallet.withdrawMoney(100),
                       "There's no coins with 100 value"),
               ()->assertTrue(testWallet.withdrawMoney(500),
                       "The process should be executed, withdrawing 500"),
               ()->assertTrue(testWallet.withdrawMoney(20200),
                       "The process should be executed, withdrawing 20200 " +
                               "with 20000 bills and 200 coins"),
               ()->assertEquals(0, testWallet.totalMoney(),
                       "The total money should be zero")
       );
   }

    @AfterEach
    void tearDownTest(){
        System.out.println("The owner of the wallet is " + testWallet.getOwner() +
                " with total money of " + testWallet.totalMoney() +
                " and total cards contained is " + testWallet.getCards().size());
        testWallet.clearWallet();
    }

    @AfterAll
    static void cleanup(){
        Wallet.closeConnection();
    }
}