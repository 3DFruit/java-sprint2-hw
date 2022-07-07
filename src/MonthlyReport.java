
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

    public int getSummaryAmount(boolean isExpense) {
        int amount = 0;
        for (MonthItemData monthItemData : itemData){
            if (monthItemData.isExpense == isExpense) amount +=  monthItemData.getAmount();
        }
        return amount;
    }    

    public MonthItemData findHighestAmountItem(boolean isExpense){
        MonthItemData highestAmountItem = new MonthItemData();
        int highestAmount = 0;
        for (MonthItemData item : itemData){
            if (item.isExpense == isExpense){
                int amount = item.getAmount();
                if (highestAmount < amount){
                    highestAmount = amount;
                    highestAmountItem = item;
                }
            }
        }
        return highestAmountItem;
    }
}
