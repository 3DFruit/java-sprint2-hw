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

    public int getIncomeForMonth(int month){
        int income = 0;
        for (YearItemData yearItemData : monthData){
            if (yearItemData.month == month && !yearItemData.isExpense) {
                income = yearItemData.amount;
                break;
            }
        }
        return income;
    }

    public int getExpenseForMonth(int month){
        int expense = 0;
        for (YearItemData yearItemData : monthData){
            if (yearItemData.month == month && yearItemData.isExpense) {
                expense = yearItemData.amount;
                break;
            }
        }
        return expense;
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

    public double getAverageIncome(){
        int income = 0;
        for (YearItemData yearItemData : monthData){
            if (!yearItemData.isExpense) {
                income += yearItemData.amount;
            }
        }
        return income / 12.0;
    }

    public double getAverageExpense(){
        int expense = 0;
        for (YearItemData yearItemData : monthData){
            if (yearItemData.isExpense) {
                expense += yearItemData.amount;
            }
        }
        return expense / 12.0;
    }
}
