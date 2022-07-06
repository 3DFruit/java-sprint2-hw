public class MonthItemData {
    String itemName;
    boolean isExpense;
    int quantity;
    int sumOfOne;

    public MonthItemData() {
    }

    public MonthItemData(String itemName, boolean isExpense, int quantity, int sumOfOne) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
    }

    public int getAmount(){
        return this.quantity * this.sumOfOne;
    }
}
