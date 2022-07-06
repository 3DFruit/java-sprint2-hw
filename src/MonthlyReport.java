
import java.util.ArrayList;

public class MonthlyReport {
    int month;

    ArrayList<MonthItemData> itemData;

    public MonthlyReport(int month, String data) {
        this.month = month;
        itemData = new ArrayList<>();
        if (data != null) {
            String[] items = data.split("\n");
            for(int i = 1; i < items.length; i++) {
                String[] fields = items[i].split(",");
                MonthItemData itemData = new MonthItemData(
                        fields[0],
                        Boolean.parseBoolean(fields[1]),
                        Integer.parseInt(fields[2]),
                        Integer.parseInt(fields[3])
                );
                this.itemData.add(itemData);
            }
        }
    }

    public int getSummaryIncome() {
        int income = 0;
        for (MonthItemData monthItemData : itemData){
            if (!monthItemData.isExpense) income +=  monthItemData.getAmount();
        }
        return income;
    }

    public int getSummaryExpense() {
        int expense = 0;
        for (MonthItemData monthItemData : itemData){
            if (monthItemData.isExpense) expense +=  monthItemData.getAmount();
        }
        return expense;
    }

    public MonthItemData findMostProfitableItem(){
        MonthItemData mostProfitableItem = new MonthItemData();
        int biggestProfit = 0;
        for (MonthItemData item : itemData){
            if (!item.isExpense){
                int profit = item.getAmount();
                if (biggestProfit < profit){
                    biggestProfit = profit;
                    mostProfitableItem = item;
                }
            }
        }
        return mostProfitableItem;
    }

    public MonthItemData findMostExpensiveItem(){
        MonthItemData mostExpensiveItem = new MonthItemData();
        int biggestExpense = 0;
        for (MonthItemData item : itemData){
            if (item.isExpense){
                int profit = item.getAmount();
                if (biggestExpense < profit){
                    biggestExpense = profit;
                    mostExpensiveItem = item;
                }
            }
        }
        return mostExpensiveItem;
    }
}
