package com.thoughtworks;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        // 以下是执行交易的交易员和发生的一系列交易,请为以下八个查询方法提供实现。
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 1.找出2011年的所有交易并按交易额排序(从低到高)
        System.out.println(get2011Transactions(transactions));

        // 2.交易员都在哪些不同的􏱜城市工作过
        System.out.println(getTradersCity(transactions));

        // 3.查找所有来自于剑桥的交易员，并按姓名排序
        System.out.println(getCambridgeTraders(transactions));

        // 4.返回所有交易员的姓名字符串，按字母顺序排序
        System.out.println(getTradersName(transactions));

        // 5.有没有交易员是在米兰工作的
        System.out.println(hasMilanTrader(transactions));

        // 6.返回交易员是剑桥的所有交易的交易额
        System.out.println(getCambridgeTransactionsValue(transactions));

        // 7.所有交易中，最高的交易额是多少
        System.out.println(getMaxTransactionValue(transactions));

        // 8.返回交易额最小的交易
        System.out.println(getMinTransaction(transactions));
    }


    public static List<Transaction> get2011Transactions(List<Transaction> transactions) {
        Stream<Transaction> transactionStream = transactions.stream();
        Stream<Transaction> filterStream = transactionStream.filter(transaction -> transaction.getYear() == 2011);
        Stream<Transaction> valueSorted = filterStream.sorted(Comparator.comparing(Transaction::getValue));
        return valueSorted.collect(Collectors.toList());
    }

    public static List<String> getTradersCity(List<Transaction> transactions) {
        Set<Trader> traders = new LinkedHashSet<>();
        for (Transaction transaction : transactions) {
            traders.add(transaction.getTrader());
        }
        Set<String> cities = new LinkedHashSet<>();
        for (Trader trader : traders) {
            cities.add(trader.getCity());
        }
        return new ArrayList<>(cities);
    }

    public static List<Trader> getCambridgeTraders(List<Transaction> transactions) {
        Set<Trader> traders = new LinkedHashSet<>();
        for (Transaction transaction : transactions) {
            traders.add(transaction.getTrader());
        }
        Stream<Trader> traderStream = traders.stream();
        Stream<Trader> formCambridgeTraders = traderStream.filter((trader -> trader.getCity().equals("Cambridge")));
        Stream<Trader> formCambridgeSorted = formCambridgeTraders.sorted(Comparator.comparing(Trader::getName));
        return formCambridgeSorted.collect(Collectors.toList());
    }

    public static List<String> getTradersName(List<Transaction> transactions) {
        Set<String> tradersName = new LinkedHashSet<>();
        for (Transaction transaction : transactions) {
            tradersName.add(transaction.getTrader().getName());
        }
        Stream<String> traderStream = tradersName.stream();
        Stream<String> nameSorted = traderStream.sorted();
        return nameSorted.collect(Collectors.toList());
    }

    public static boolean hasMilanTrader(List<Transaction> transactions) {
        Stream<Transaction> stream = transactions.stream();
        Stream<Transaction> formMilan = stream.filter(transaction -> transaction.getTrader().getCity().equals("Milan"));
        return formMilan.count() != 0;
    }

    public static List<Integer> getCambridgeTransactionsValue(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return null;
        }
        Stream<Transaction> stream = transactions.stream();
        Stream<Transaction> formMambridge = stream.filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"));
        Stream<Integer> valuesStream = formMambridge.map(Transaction::getValue);
        return valuesStream.collect(Collectors.toList());
    }

    public static int getMaxTransactionValue(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return -1;
        }
        return transactions.stream().max(Comparator.comparingInt(Transaction::getValue)).map(Transaction::getValue).orElse(-1);
    }

    public static Transaction getMinTransaction(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return null;
        }
        return transactions.stream().min(Comparator.comparingDouble(Transaction::getValue)).orElse(null);
    }
}
