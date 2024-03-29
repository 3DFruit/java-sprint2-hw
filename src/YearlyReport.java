import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    int year;
    ArrayList<YearItemData> monthData;

    YearlyReport(int year, String data){
        this.year = year;
        monthData = new ArrayList<>();
        if (data != null) {
            String[] items = data.split("\n");
            for(int i = 1; i < items.length; i++) {
                String[] fields = items[i].split(",");
                YearItemData itemData = new YearItemData(
                        Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[1]),
                        Boolean.parseBoolean(fields[2])
                );
                this.monthData.add(itemData);
            }
        }
    }

    public int getAmountForMonth(int month,  boolean isExpense){
        int amount = 0;
        for (YearItemData yearItemData : monthData){
            if (yearItemData.month == month && yearItemData.isExpense == isExpense) {
                amount = yearItemData.amount;
                break;
            }
        }
        return amount;
    }

    public HashMap<Integer,Integer> getProfitByMonths(){
        HashMap<Integer,Integer> profit = new HashMap<>();
        for (YearItemData yearItemData : monthData){
            int month = yearItemData.month;
            if (profit.containsKey(month)){
                if (yearItemData.isExpense){
                    profit.put(month, profit.get(month) - yearItemData.amount);
                }
                else profit.put(month, profit.get(month) + yearItemData.amount);
            }
            else {
                profit.put(month, (yearItemData.isExpense ? -yearItemData.amount: yearItemData.amount));
            }
        }
        return profit;
    }

    public double getAverageAmount(boolean isExpense){
        int amount = 0;
        for (YearItemData yearItemData : monthData){
            if (yearItemData.isExpense == isExpense) {
                amount += yearItemData.amount;
            }
        }
        return amount / (monthData.size()/2.0);
    }
}
